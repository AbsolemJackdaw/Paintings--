package com.mcf.davidee.paintinggui.proxy;

import com.mcf.davidee.paintinggui.gui.PaintingSelectionScreen;
import com.mcf.davidee.paintinggui.packet.CPacketPainting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends ServerProxy {

	@Override
	public EntityPlayer getClientPlayer() { 
		return Minecraft.getMinecraft().player; 
	}

	@Override
	public void displayPaintingSelectionScreen(CPacketPainting message){
		Minecraft mc =  Minecraft.getMinecraft();
		if (mc.currentScreen == null)
			mc.displayGuiScreen(new PaintingSelectionScreen(message.art, message.id));
	}
}
