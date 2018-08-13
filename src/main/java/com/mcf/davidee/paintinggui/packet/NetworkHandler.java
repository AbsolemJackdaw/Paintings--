package com.mcf.davidee.paintinggui.packet;


import com.mcf.davidee.paintinggui.packet.CPacketPainting.CPaintingMessageHandler;
import com.mcf.davidee.paintinggui.packet.SPacketPainting.SPaintingMessageHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	
	private static final String CHANNEL = "Paint_Select_Gui";
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
	
	public NetworkHandler() {
		NETWORK.registerMessage(SPaintingMessageHandler.class, SPacketPainting.class, 0, Side.SERVER);
		NETWORK.registerMessage(CPaintingMessageHandler.class, CPacketPainting.class, 1, Side.CLIENT);
	}
}
