package com.mcf.davidee.paintinggui.mod;

import com.mcf.davidee.paintinggui.gui.ArtComparator;
import com.mcf.davidee.paintinggui.handler.PlacePaintingEventHandler;
import com.mcf.davidee.paintinggui.packet.NetworkHandler;
import com.mcf.davidee.paintinggui.proxy.ServerProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = "paintingselgui", name = "PaintingSelectionGui", version = "$version", dependencies = "after:guilib")
public class PaintingSelection {

	public static final String CHANNEL = "PaintingSelGui";
	public static final char COLOR = '\u00A7';

	public static final ArtComparator ART_COMPARATOR = new ArtComparator();

	@SidedProxy(clientSide="com.mcf.davidee.paintinggui.proxy.ClientProxy", serverSide="com.mcf.davidee.paintinggui.proxy.ServerProxy")
	public static ServerProxy proxy;

	@EventHandler 
	public void preInit(FMLPreInitializationEvent event) {
		new NetworkHandler();
		new PlacePaintingEventHandler();
	}
}