package subaraki.paintings.mod.entity;

import javax.annotation.Nullable;

import com.mcf.davidee.paintinggui.wrapper.PaintingWrapper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityNewPainting extends EntityHanging implements IEntityAdditionalSpawnData{

	public String art = PaintingWrapper.DEFAULT.getTitle();

	public EntityNewPainting(World worldIn)
	{
		super(worldIn);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(Items.PAINTING, 1);
	}
	
	public void updateBB() {
		this.updateFacingWithBoundingBox(facingDirection);
	}

	/**override and remove update bounding box to prevent an OBO. it pops certain paintings one block up*/
	@Override
	public void setPosition(double x, double y, double z) {
		this.hangingPosition = new BlockPos(x, y, z);
		//this.updateBoundingBox();
		this.isAirBorne = true;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setString("Motive", this.art);
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		String motive = compound.getString("Motive");

		if (PaintingWrapper.PAINTINGS.containsKey(motive))
		{
			this.art = motive;
		}

		super.readEntityFromNBT(compound);

	}

	public int getWidthPixels()
	{
		return PaintingWrapper.PAINTINGS.get(art).getX();
	}

	public int getHeightPixels()
	{
		return PaintingWrapper.PAINTINGS.get(art).getY();
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

	@Override
	public void writeSpawnData(ByteBuf buffer) {

		ByteBufUtils.writeUTF8String(buffer, art);
		buffer.writeByte(facingDirection.getHorizontalIndex());
		buffer.writeFloat(rotationYaw);
		buffer.writeDouble(hangingPosition.getX());
		buffer.writeDouble(hangingPosition.getY());
		buffer.writeDouble(hangingPosition.getZ());
		
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		
		if(additionalData.isReadable())
		{
			String arttitle =  ByteBufUtils.readUTF8String(additionalData);
			art = PaintingWrapper.PAINTINGS.containsKey(arttitle) ? arttitle : PaintingWrapper.DEFAULT.getTitle();
			
			facingDirection = EnumFacing.getHorizontal(additionalData.readByte());
			rotationYaw = additionalData.readFloat();
			setPosition(additionalData.readDouble(), additionalData.readDouble(), additionalData.readDouble());
			updateBB();
		}
	}

	@Override
	protected void updateBoundingBox() {
		if (facingDirection != null) {

			double hangX = (double) getHangingPosition().getX() + 0.5D;
			double hangY = (double) getHangingPosition().getY() + 0.5D;
			double hangZ = (double) getHangingPosition().getZ() + 0.5D;
			double offsetWidth = getWidthPixels() % 32 == 0 ? 0.5D : 0.0D;
			double offsetHeight = getHeightPixels() % 32 == 0 ? 0.5D : 0.0D;
			hangX = hangX - (double) facingDirection.getFrontOffsetX() * 0.46875D;
			hangZ = hangZ - (double) facingDirection.getFrontOffsetZ() * 0.46875D;
			hangY += offsetHeight;
			EnumFacing enumfacing = facingDirection.rotateYCCW();
			hangX = hangX + offsetWidth * (double) enumfacing.getFrontOffsetX();
			hangZ = hangZ + offsetWidth * (double) enumfacing.getFrontOffsetZ();
			posX = hangX;
			posY = hangY;
			posZ = hangZ;
			double widthX = (double) getWidthPixels();
			double height = (double) getHeightPixels();
			double widthZ = (double) getWidthPixels();

			if (facingDirection.getAxis() == EnumFacing.Axis.Z) {
				widthZ = 1.0D;
			} else {
				widthX = 1.0D;
			}

			widthX = widthX / 32.0D;
			height = height / 32.0D;
			widthZ = widthZ / 32.0D;
			setEntityBoundingBox(new AxisAlignedBB(hangX - widthX, hangY - height, hangZ - widthZ, hangX + widthX, hangY + height, hangZ + widthZ));
		}
	}
}
