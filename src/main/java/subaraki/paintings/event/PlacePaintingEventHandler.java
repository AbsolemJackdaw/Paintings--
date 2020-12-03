package subaraki.paintings.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.paintings.mod.ConfigData;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.packet.NetworkHandler;
import subaraki.paintings.packet.client.CPacketPainting;
import subaraki.paintings.util.ArtComparator;

public class PlacePaintingEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPaintingPlaced(PlayerInteractEvent.RightClickBlock event)
    {

        if (!ConfigData.use_selection_gui)
            return;

        ItemStack itemStack = event.getItemStack();

        if (itemStack.getItem() == Items.PAINTING)
        {

            PlayerEntity player = event.getPlayer();
            Direction face = event.getFace();
            BlockPos blockpos = event.getPos();
            BlockPos actualPos = blockpos.offset(face);
            World world = event.getWorld();

            boolean flag = false;

            flag = face.getAxis().isHorizontal();

            if (flag && player.canPlayerEdit(actualPos, face, itemStack))
            {

                PaintingEntity painting = new PaintingEntity(world, actualPos, face);
                painting.rotationYaw = face.getHorizontalAngle();
                // Set position updates bounding box
                painting.setPosition(actualPos.getX(), blockpos.getY(), actualPos.getZ());

                if (painting.onValidSurface())
                {
                    event.getPlayer().swingArm(Hand.MAIN_HAND); // recreate the animation of placing down an item

                    if (!event.getPlayer().isCreative())
                        itemStack.shrink(1);

                    if (!event.getWorld().isRemote)
                    {

                        ServerPlayerEntity playerMP = (ServerPlayerEntity) event.getPlayer();

                        painting.playPlaceSound();
                        event.getWorld().addEntity(painting);

                        PaintingType originalArt = painting.art;

                        List<PaintingType> validArts = new ArrayList<PaintingType>(); // list of paintings placeable at current location
                        for (ResourceLocation resLoc : ForgeRegistries.PAINTING_TYPES.getKeys())
                        {
                            PaintingType art = ForgeRegistries.PAINTING_TYPES.getValue(resLoc);

                            painting.art = art;

                            // update the bounding box of the painting to make sure the simulation of
                            // placing down a painting
                            // happens correctly. Omiting this will result in overlapping paintings
                            Paintings.utility.updatePaintingBoundingBox(painting);

                            // simulate placing down a painting. if possible, add it to a list of paintings
                            // that are possible to place at this location
                            if (painting.onValidSurface())
                            {
                                
                                if (ConfigData.use_vanilla_only)
                                {
                                    if (art.equals(PaintingType.KEBAB) || art.equals(PaintingType.AZTEC) || art.equals(PaintingType.ALBAN)
                                            || art.equals(PaintingType.AZTEC2) || art.equals(PaintingType.BOMB) || art.equals(PaintingType.PLANT)
                                            || art.equals(PaintingType.WASTELAND) || art.equals(PaintingType.POOL) || art.equals(PaintingType.COURBET)
                                            || art.equals(PaintingType.SEA) || art.equals(PaintingType.SUNSET) || art.equals(PaintingType.CREEBET)
                                            || art.equals(PaintingType.WANDERER) || art.equals(PaintingType.GRAHAM) || art.equals(PaintingType.MATCH)
                                            || art.equals(PaintingType.BUST) || art.equals(PaintingType.STAGE) || art.equals(PaintingType.VOID)
                                            || art.equals(PaintingType.SKULL_AND_ROSES) || art.equals(PaintingType.WITHER) || art.equals(PaintingType.FIGHTERS)
                                            || art.equals(PaintingType.POINTER) || art.equals(PaintingType.PIGSCENE) || art.equals(PaintingType.BURNING_SKULL)
                                            || art.equals(PaintingType.SKELETON) || art.equals(PaintingType.DONKEY_KONG))
                                    {
                                        validArts.add(art);
                                    }
                                }else
                                    validArts.add(art);
                            }
                                
                        }
                        // reset the art of the painting to the one registered before
                        painting.art = originalArt;

                        Paintings.utility.updatePaintingBoundingBox(painting); // reset bounding box

                        PaintingType[] validArtsArray = validArts.toArray(new PaintingType[0]);
                        // sort paintings from high to low, and from big to small
                        Arrays.sort(validArtsArray, new ArtComparator());

                        ResourceLocation[] names = new ResourceLocation[validArtsArray.length];
                        for (int i = 0; i < validArtsArray.length; ++i)
                        {
                            names[i] = validArtsArray[i].getRegistryName();
                        }

                        // send to one player only, the player that needs his Gui opened !!
                        // this used to be send to all around, but then everyone got the gui opened
                        NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with((() -> playerMP)), new CPacketPainting(painting, names));

                    }

                }
                event.setCanceled(true);
            }
        }
    }
}
