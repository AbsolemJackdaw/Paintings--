package com.mcf.davidee.paintinggui.handler;

import com.mcf.davidee.paintinggui.mod.PaintingSelection;
import com.mcf.davidee.paintinggui.packet.CPacketPainting;
import com.mcf.davidee.paintinggui.packet.NetworkHandler;
import com.mcf.davidee.paintinggui.wrapper.PaintingWrapper;

import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import subaraki.paintings.mod.entity.EntityNewPainting;
import subaraki.paintings.mod.network.CPacketSyncPaintingData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlacePaintingEventHandler {

	public PlacePaintingEventHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPaintingPlaced(PlayerInteractEvent.RightClickBlock event){

		ItemStack itemStack = event.getItemStack();

		if(itemStack.getItem() == Items.PAINTING){

			EntityPlayer player = event.getEntityPlayer();
			EnumFacing face = event.getFace();
			BlockPos blockpos = event.getPos();
			BlockPos actualPos = blockpos.offset(face);
			World world = event.getWorld();

			boolean flag = false;

			for(EnumFacing facing : EnumFacing.HORIZONTALS)
				if(face.equals(facing))
					flag = true;

			if (flag && player.canPlayerEdit(actualPos, face, itemStack)){


				EntityNewPainting painting = new EntityNewPainting(world);
				painting.facingDirection = face;
				painting.rotationYaw = face.getHorizontalAngle();
				painting.setPosition(actualPos.getX(), blockpos.getY(), actualPos.getZ());
				painting.updateBB();

				if(painting.onValidSurface()){
					event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND); //recreate the animation of placing down an item

					if(!event.getEntityPlayer().isCreative())
						itemStack.shrink(1);

					if (!event.getWorld().isRemote){

						EntityPlayerMP playerMP = (EntityPlayerMP)event.getEntityPlayer();

						painting.playPlaceSound();
						event.getWorld().spawnEntity(painting);

						//communicate the painting's facing to client
						subaraki.paintings.mod.network.NetworkHandler.NETWORK.sendToAllAround(
								new CPacketSyncPaintingData(painting), 
								new TargetPoint(world.provider.getDimension(), blockpos.getX(),blockpos.getY(),blockpos.getZ(), 48));

						PaintingWrapper originalArt = PaintingWrapper.PAINTINGS.get(painting.art);

						List<PaintingWrapper> validArts = new ArrayList<PaintingWrapper>(); //list of paintings placeable at current location
						for(PaintingWrapper art : PaintingWrapper.PAINTINGS.values()){
							painting.art = art.getTitle();

							//update the bounding box of the painting to make sure the simulation of placing down a painting
							//happens correctly. Omiting this will result in overlapping paintings
							updatePaintingBoundingBox(painting);

							//simulate placing down a painting. if possible, add it to a list of paintings
							//that are possible to place at this location
							if (painting.onValidSurface())
								validArts.add(art);
						}
						//reset the art of the painting to the one registered before
						painting.art = originalArt.getTitle();

						updatePaintingBoundingBox(painting); // reset bounding box

						PaintingWrapper[] validArtsArray = validArts.toArray(new PaintingWrapper[0]);
						//sort paintings from high to low, and from big to small
						Arrays.sort(validArtsArray, PaintingSelection.ART_COMPARATOR);

						String[] names = new String[validArtsArray.length];
						for (int i =0; i < validArtsArray.length; ++i)
							names[i] = validArtsArray[i].getTitle();


						// send to one player only, the player that needs his Gui opened !!
						// this used to be send to all around, but then everyone got the gui opened
						if(player instanceof EntityPlayerMP)
							NetworkHandler.NETWORK.sendTo(new CPacketPainting(painting, names), (EntityPlayerMP) player);
					}

				}
				event.setCanceled(true);
			}
		}
	}

	//probably copied this from vanilla at some point ...
	private void updatePaintingBoundingBox(EntityHanging painting) {
		if (painting.facingDirection != null) {
			double hangX = (double) painting.getHangingPosition().getX() + 0.5D;
			double hangY = (double) painting.getHangingPosition().getY() + 0.5D;
			double hangZ = (double) painting.getHangingPosition().getZ() + 0.5D;
			double offsetWidth = painting.getWidthPixels() % 32 == 0 ? 0.5D : 0.0D;
			double offsetHeight = painting.getHeightPixels() % 32 == 0 ? 0.5D : 0.0D;
			hangX = hangX - (double) painting.facingDirection.getFrontOffsetX() * 0.46875D;
			hangZ = hangZ - (double) painting.facingDirection.getFrontOffsetZ() * 0.46875D;
			hangY = hangY + offsetHeight;
			EnumFacing enumfacing = painting.facingDirection.rotateYCCW();
			hangX = hangX + offsetWidth * (double) enumfacing.getFrontOffsetX();
			hangZ = hangZ + offsetWidth * (double) enumfacing.getFrontOffsetZ();
			painting.posX = hangX;
			painting.posY = hangY;
			painting.posZ = hangZ;
			double widthX = (double) painting.getWidthPixels();
			double height = (double) painting.getHeightPixels();
			double widthZ = (double) painting.getWidthPixels();

			if (painting.facingDirection.getAxis() == EnumFacing.Axis.Z) {
				widthZ = 1.0D;
			} else {
				widthX = 1.0D;
			}

			widthX = widthX / 32.0D;
			height = height / 32.0D;
			widthZ = widthZ / 32.0D;
			painting.setEntityBoundingBox(new AxisAlignedBB(hangX - widthX, hangY - height, hangZ - widthZ, hangX + widthX, hangY + height, hangZ + widthZ));
		}
	}
}
