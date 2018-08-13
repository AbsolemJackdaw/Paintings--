package com.mcf.davidee.paintinggui.packet;

import com.mcf.davidee.paintinggui.mod.PaintingSelection;
import com.mcf.davidee.paintinggui.wrapper.PaintingWrapper;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.paintings.mod.entity.EntityNewPainting;

public class CPacketPainting implements IMessage{

	// A default constructor is always required
	public CPacketPainting(){}

	public int id;
	public String[] art;
	public EnumFacing face;

	public CPacketPainting(EntityNewPainting painting, String[] data) {
		this.id = painting.getEntityId();
		art = data;
		face = painting.facingDirection;
	}

	@Override 
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeInt(art.length);
		for(int i = 0; i < art.length; i++)
			ByteBufUtils.writeUTF8String(buf, art[i]);
		
		buf.writeByte(face.getHorizontalIndex());
	}

	@Override 
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		String[] s = new String[buf.readInt()];
		for(int i = 0; i < s.length; i++){
			s[i] = ByteBufUtils.readUTF8String(buf);
		}
		art = s;
		face = EnumFacing.getHorizontal(buf.readByte());
	}

	public static class CPaintingMessageHandler implements IMessageHandler<CPacketPainting, IMessage> {
		@Override 
		public IMessage onMessage(CPacketPainting message, MessageContext ctx) {

			Minecraft.getMinecraft().addScheduledTask( () -> {
				if(message.id == -1) { //What painting is selected?
					PaintingSelection.proxy.processRayTracing();
				}
				else if (message.art.length == 1) { //Set Painting
					PaintingWrapper wrapper = getPaintingWrapper(message.art[0]);
					Entity e = Minecraft.getMinecraft().world.getEntityByID(message.id);
					if (e instanceof EntityNewPainting){
						EntityNewPainting painting = ((EntityNewPainting)e);
						painting.facingDirection = message.face;
						setPaintingArt(painting, wrapper);
					}
				}
				else { //Show art GUI
					PaintingSelection.proxy.displayPaintingSelectionScreen(message);
				}
			});

			return null;
		}

		protected PaintingWrapper getPaintingWrapper(String artName) {
			if(PaintingWrapper.PAINTINGS.containsKey(artName))
					return PaintingWrapper.PAINTINGS.get(artName);
			
			return PaintingWrapper.DEFAULT;
		}

		protected void setPaintingArt(EntityNewPainting p, PaintingWrapper art) {
			//forcing a boundingbox update by reading the data of the entity :

			NBTTagCompound tag = new NBTTagCompound();
			p.writeEntityToNBT(tag);
			//change art here, so it won't look like the painting moved
			tag.setString("Motive", art.getTitle()); 
			p.readEntityFromNBT(tag);
		}
	}
}
