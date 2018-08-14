package subaraki.paintings.mod.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.paintings.mod.entity.EntityNewPainting;

public class CPacketSyncPaintingData implements IMessage{

	private int entityID;
	private BlockPos position;
	private EnumFacing facing;
	private String title;
	private UUID uniqueID;


	public CPacketSyncPaintingData() {
	}

	public CPacketSyncPaintingData(EntityNewPainting painting) {
		this.entityID = painting.getEntityId();
		this.position = painting.getHangingPosition();
		this.facing = painting.facingDirection;
		this.title = painting.art;
		this.uniqueID = painting.getUniqueID();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();

		position = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

		facing = EnumFacing.getHorizontal(buf.readByte());

		title = ByteBufUtils.readUTF8String(buf);

		uniqueID = new UUID(buf.readLong(), buf.readLong());

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);

		buf.writeInt(position.getX());
		buf.writeInt(position.getY());
		buf.writeInt(position.getZ());

		buf.writeByte(facing.getHorizontalIndex());

		ByteBufUtils.writeUTF8String(buf, title);

		buf.writeLong(uniqueID.getMostSignificantBits());
		buf.writeLong(uniqueID.getLeastSignificantBits());
	}

	public static class CPacketSyncPaintingDataHandler implements IMessageHandler<CPacketSyncPaintingData, IMessage>{

		@Override
		public IMessage onMessage(CPacketSyncPaintingData message, MessageContext ctx) {

			Minecraft.getMinecraft().addScheduledTask( ()-> {
				process(message);
			});

			return null;
		}

		private void process(CPacketSyncPaintingData message) {
			Entity e = Minecraft.getMinecraft().world.getEntityByID(message.entityID);
			if(e instanceof EntityNewPainting)
			{
				EntityNewPainting enp = (EntityNewPainting)e;
				
				enp.facingDirection = message.facing;
				enp.art = message.title;
				enp.setPosition(message.position.getX(), message.position.getY(), message.position.getZ());
				enp.rotationYaw = message.facing.getHorizontalAngle();
			}
			
		}
	}
}