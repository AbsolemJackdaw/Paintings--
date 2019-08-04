//package com.mcf.davidee.paintinggui.handler;
//
//import com.mcf.davidee.paintinggui.gui.PaintingSelectionContainer;
//import com.mcf.davidee.paintinggui.gui.PaintingSelectionScreen;
//
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.common.network.IGuiHandler;
//
//public class GuiHandler implements IGuiHandler {
//
//	@Override
//	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//		return new PaintingSelectionContainer();
//	}
//
//	@Override
//	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//		
//		return new PaintingSelectionScreen(art, ID);
//	}
//
//}
