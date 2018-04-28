package subaraki.paintings.entity;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import subaraki.paintings.fake_enum.Painting;

public class EntityPaintingNew extends EntityHanging implements IEntityAdditionalSpawnData{

	private Painting painting;

	public EntityPaintingNew(World worldIn) {
		super(worldIn);
	}

	public EntityPaintingNew(World worldIn, BlockPos pos, EnumFacing facing)
	{
		super(worldIn, pos);
		List<Painting> allPaintings = Lists.<Painting>newArrayList();
		int maxSize = 0;

		for (Painting ep : Painting.paintings.values())
		{
			painting = ep;
			this.updateFacingWithBoundingBox(facing);

			if (this.onValidSurface())
			{
				allPaintings.add(ep);
				int size = ep.getX() * ep.getY();

				if (size > maxSize)
				{
					maxSize = size;
				}
			}
		}

		if (!allPaintings.isEmpty())
		{
			Iterator<Painting> iterator = allPaintings.iterator();

			while (iterator.hasNext())
			{
				Painting ep = iterator.next();

				if (ep.getX() * ep.getY() < maxSize)
				{
					iterator.remove();
				}
			}

			this.painting = allPaintings.get(this.rand.nextInt(allPaintings.size()));

		}

		this.updateFacingWithBoundingBox(facing);
	}

	@SideOnly(Side.CLIENT)
	public EntityPaintingNew(World worldIn, BlockPos pos, EnumFacing facing, String title)
	{
		this(worldIn, pos, facing);

		this.painting = Painting.paintings.get(title);

		this.updateFacingWithBoundingBox(facing);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setString("Motive", this.painting.getName());
		super.writeEntityToNBT(compound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		String title = compound.getString("Motive");

		this.painting = Painting.paintings.get(title);

		super.readEntityFromNBT(compound);
	}

	public int getWidthPixels()
	{
		return this.painting.getX();
	}

	public int getHeightPixels()
	{
		return this.painting.getY();
	}

	public Painting getPainting() {
		return painting;
	}

	/**
	 * Called when this entity is broken. Entity parameter may be null.
	 */
	public void onBroken(@Nullable Entity brokenEntity)
	{
		if (this.world.getGameRules().getBoolean("doEntityDrops"))
		{
			this.playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0F, 1.0F);

			if (brokenEntity instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)brokenEntity;

				if (entityplayer.capabilities.isCreativeMode)
				{
					return;
				}
			}

			this.entityDropItem(new ItemStack(Items.PAINTING), 0.0F);
		}
	}

	public void playPlaceSound()
	{
		this.playSound(SoundEvents.ENTITY_PAINTING_PLACE, 1.0F, 1.0F);
	}

	/**
	 * Sets the location and Yaw/Pitch of an entity in the world
	 */
	public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch)
	{
		this.setPosition(x, y, z);
	}

	/**
	 * Sets a target for the client to interpolate towards over the next few ticks
	 */
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
	{
		BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
		this.setPosition((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
	}

	@Override
	public void writeSpawnData(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, painting.getName());

		buf.writeByte(this.getHorizontalFacing().getHorizontalIndex());

		new PacketBuffer(buf).writeBlockPos((this.hangingPosition));
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.painting = Painting.paintings.get(ByteBufUtils.readUTF8String(additionalData));

		EnumFacing facing = EnumFacing.getHorizontal(additionalData.readByte());

		BlockPos pos = new PacketBuffer(additionalData).readBlockPos();
		this.hangingPosition = pos;

		this.updateFacingWithBoundingBox(facing);
	}
}
