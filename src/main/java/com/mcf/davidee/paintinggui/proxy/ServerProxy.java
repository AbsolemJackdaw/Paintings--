package com.mcf.davidee.paintinggui.proxy;

import com.mcf.davidee.paintinggui.packet.CPacketPainting;

import net.minecraft.entity.player.EntityPlayer;

public class ServerProxy {
	
	public EntityPlayer getClientPlayer() { 
		return null; 
	}
	
	public void displayPaintingSelectionScreen(CPacketPainting message){};

	public void processRayTracing(){};
}
