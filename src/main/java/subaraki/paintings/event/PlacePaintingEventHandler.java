package subaraki.paintings.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.paintings.mod.ConfigData;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.packet.NetworkHandler;
import subaraki.paintings.packet.client.CPacketPainting;
import subaraki.paintings.util.ArtComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EventBusSubscriber(modid = Paintings.MODID, bus = Bus.FORGE)
public class PlacePaintingEventHandler {

    @SubscribeEvent
    public static void onPaintingPlaced(PlayerInteractEvent.RightClickBlock event)
    {

        if (!ConfigData.use_selection_gui)
            return;

        ItemStack itemStack = event.getItemStack();

        if (itemStack.getItem() == Items.PAINTING)
        {

            Player player = event.getPlayer();
            Direction face = event.getFace();
            BlockPos blockpos = event.getPos();
            BlockPos actualPos = blockpos.relative(face);
            Level world = event.getWorld();

            boolean flag = false;

            flag = face.getAxis().isHorizontal();

            if (flag && player.mayUseItemAt(actualPos, face, itemStack))
            {

                Painting painting = new Painting(world, actualPos, face);
                painting.setYRot(face.toYRot());
                // Set position updates bounding box
                painting.setPos(actualPos.getX(), blockpos.getY(), actualPos.getZ());

                if (painting.survives())
                {
                    event.getPlayer().swing(InteractionHand.MAIN_HAND); // recreate the animation of placing down an item

                    if (!event.getPlayer().isCreative())
                        itemStack.shrink(1);

                    if (!event.getWorld().isClientSide())
                    {

                        ServerPlayer playerMP = (ServerPlayer) event.getPlayer();

                        painting.playPlacementSound();
                        event.getWorld().addFreshEntity(painting);

                        Motive originalArt = painting.motive;

                        List<Motive> validArts = new ArrayList<Motive>(); // list of paintings placeable at current location
                        for (ResourceLocation resLoc : ForgeRegistries.PAINTING_TYPES.getKeys())
                        {
                            Motive art = ForgeRegistries.PAINTING_TYPES.getValue(resLoc);

                            painting.motive = art;

                            // update the bounding box of the painting to make sure the simulation of
                            // placing down a painting
                            // happens correctly. Omiting this will result in overlapping paintings
                            Paintings.UTILITY.updatePaintingBoundingBox(painting);

                            // simulate placing down a painting. if possible, add it to a list of paintings
                            // that are possible to place at this location
                            if (painting.survives())
                            {

                                if (ConfigData.use_vanilla_only)
                                {
                                    if (art.equals(Motive.KEBAB) || art.equals(Motive.AZTEC) || art.equals(Motive.ALBAN) || art.equals(Motive.AZTEC2)
                                            || art.equals(Motive.BOMB) || art.equals(Motive.PLANT) || art.equals(Motive.WASTELAND) || art.equals(Motive.POOL)
                                            || art.equals(Motive.COURBET) || art.equals(Motive.SEA) || art.equals(Motive.SUNSET) || art.equals(Motive.CREEBET)
                                            || art.equals(Motive.WANDERER) || art.equals(Motive.GRAHAM) || art.equals(Motive.MATCH) || art.equals(Motive.BUST)
                                            || art.equals(Motive.STAGE) || art.equals(Motive.VOID) || art.equals(Motive.SKULL_AND_ROSES)
                                            || art.equals(Motive.WITHER) || art.equals(Motive.FIGHTERS) || art.equals(Motive.POINTER)
                                            || art.equals(Motive.PIGSCENE) || art.equals(Motive.BURNING_SKULL) || art.equals(Motive.SKELETON)
                                            || art.equals(Motive.DONKEY_KONG))
                                    {
                                        validArts.add(art);
                                    }
                                }
                                else
                                    validArts.add(art);
                            }

                        }
                        // reset the art of the painting to the one registered before
                        painting.motive = originalArt;

                        Paintings.UTILITY.updatePaintingBoundingBox(painting); // reset bounding box

                        Motive[] validArtsArray = validArts.toArray(new Motive[0]);
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
