package com.mcf.davidee.paintinggui.packet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mcf.davidee.paintinggui.mod.PaintingSelection;
import com.mcf.davidee.paintinggui.wrapper.PaintingWrapper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.paintings.mod.entity.EntityNewPainting;

public class SPacketPainting implements IMessage{

	// A default constructor is always required
	public SPacketPainting(){}

	public int id;
	public String[] art;

	public SPacketPainting(int toSend, String[] data) {
		this.id = toSend;
		art = data;
	}

	@Override 
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeInt(art.length);
		for(int i = 0; i < art.length; i++)
			ByteBufUtils.writeUTF8String(buf, art[i]);
	}

	@Override 
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		String[] s = new String[buf.readInt()];
		for(int i = 0; i < s.length; i++){
			s[i] = ByteBufUtils.readUTF8String(buf);
		}
		art = s;
	}

	public static class SPaintingMessageHandler implements IMessageHandler<SPacketPainting, IMessage> {

		@Override 
		public IMessage onMessage(SPacketPainting message, MessageContext ctx) {
			((WorldServer)ctx.getServerHandler().player.world).addScheduledTask(() -> {
				handleServerSide(ctx.getServerHandler().player, message);
			});
			return null;
		}

		private void handleServerSide(EntityPlayerMP player, SPacketPainting packet){
			if (packet.art.length == 1) { //Set Painting
				PaintingWrapper art = getPaintingWrapper(packet.art[0]);
				Entity e = player.world.getEntityByID(packet.id);
				if (e instanceof EntityNewPainting) {
					EntityNewPainting painting = (EntityNewPainting)e;
					setPaintingArt(painting, art);
					NetworkHandler.NETWORK.sendToAllAround(new CPacketPainting(painting, new String[]{art.getTitle()}), 
							new TargetPoint(player.world.provider.getDimension(), painting.posX, painting.posY, painting.posZ, 48));
				}
				else
					player.sendMessage(new TextComponentString(PaintingSelection.COLOR + "Error - Could not locate painting"));
			}
			else { //Send possible paintings
				Entity e = player.world.getEntityByID(packet.id);
				if (e instanceof EntityNewPainting) {
					EntityNewPainting painting = (EntityNewPainting)e;
					PaintingWrapper origArt = PaintingWrapper.PAINTINGS.get(painting.art);

					List<PaintingWrapper> validArts = new ArrayList<PaintingWrapper>();
					for(PaintingWrapper art : PaintingWrapper.PAINTINGS.values()){
						setPaintingArt(painting, art);
						if (painting.onValidSurface())
							validArts.add(art);
					}
					PaintingWrapper[] validArtsArray = validArts.toArray(new PaintingWrapper[0]);
					Arrays.sort(validArtsArray, PaintingSelection.ART_COMPARATOR);
					String[] names = new String[validArtsArray.length];
					for (int i =0; i < validArtsArray.length; ++i)
						names[i] = validArtsArray[i].getTitle();

					NetworkHandler.NETWORK.sendTo(new CPacketPainting(painting, names), player);

					//Reset the art
					setPaintingArt(painting, origArt);
				}
				else
					player.sendMessage(new TextComponentString(PaintingSelection.COLOR + "cError - Could not locate painting"));
			}
		}

		protected PaintingWrapper getPaintingWrapper(String artName) {
			if(PaintingWrapper.PAINTINGS.containsKey(artName))
				return PaintingWrapper.PAINTINGS.get(artName);

			return PaintingWrapper.DEFAULT;
		}

		protected void setPaintingArt(EntityNewPainting p, PaintingWrapper art) {

			//force a boundingbox update by reading the data of the entity
			NBTTagCompound tag = new NBTTagCompound();
			p.writeEntityToNBT(tag);
			tag.setString("Motive", art.getTitle()); //change art here, so it won't look like the painting moved
			p.readEntityFromNBT(tag);
		}
	}
}