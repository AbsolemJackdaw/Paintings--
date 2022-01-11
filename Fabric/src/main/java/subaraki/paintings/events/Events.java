package subaraki.paintings.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.network.ClientNetwork;
import subaraki.paintings.utils.PaintingUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Events {

    private static boolean equalSizes(Motive a, Motive b) {
        return a.getWidth() == b.getWidth() && a.getHeight() == b.getHeight();
    }

    private static boolean equalNames(Motive a, Motive b) {
        return Registry.MOTIVE.getKey(a).equals(Registry.MOTIVE.getKey(b));
    }

    public static void events() {
        UseEntityCallback.EVENT.register((player, world, hand, target, hitResult) -> {
            if (Paintings.config.cycle_paintings)
                if (target instanceof Painting painting && hand.equals(InteractionHand.MAIN_HAND)) {
                    if (player != null && player.getMainHandItem().getItem().equals(Items.PAINTING)) {
                        Motive original = painting.motive;

                        Motive firstMatch = null;
                        Motive newArt = null;

                        // it is important to sort the paintings from big to small so all same size
                        // paintings will be next to one another
                        List<Motive> validArtsList = Registry.MOTIVE.stream().sorted(PaintingUtility.ART_COMPARATOR).toList();
                        boolean takeNext = false;
                        for (Motive type : validArtsList) {
                            if (equalSizes(original, type)) {
                                if (firstMatch == null) {
                                    firstMatch = type;
                                }
                                if (takeNext) {
                                    newArt = type;
                                    break;
                                }
                                if (equalNames(original, type)) {
                                    takeNext = true;
                                }
                            } else if (takeNext) { // if the next one isn't of same size, loop back
                                newArt = firstMatch;
                                break;
                            }
                        }

                        // if the painting list is done with (very last entry) then takeNext is set to true, but wont reloop to set the next painting
                        // we do that here
                        // newArt is null && takeNext is still set to true
                        if (newArt == null && takeNext)
                            newArt = firstMatch;

                        subaraki.paintings.Paintings.UTILITY.setArt(painting, newArt);
                    }
                }
            return InteractionResult.PASS;
        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof Painting painting) {
                subaraki.paintings.Paintings.UTILITY.updatePaintingBoundingBox(painting);
            }
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            if (Paintings.config.use_selection_gui) {
                ItemStack itemStack = player.getItemInHand(hand);
                if (itemStack.getItem() == Items.PAINTING) {
                    Direction face = hitResult.getDirection();
                    BlockPos blockpos = hitResult.getBlockPos();
                    BlockPos actualPos = blockpos.relative(face);
                    boolean flag;
                    flag = face.getAxis().isHorizontal();
                    if (flag && player.mayUseItemAt(actualPos, face, itemStack)) {
                        Painting painting = new Painting(world, actualPos, face);
                        painting.setYRot(face.toYRot());
                        // Set position updates bounding box
                        painting.setPos(actualPos.getX(), blockpos.getY(), actualPos.getZ());
                        if (painting.survives()) {
                            player.swing(InteractionHand.MAIN_HAND); // recreate the animation of placing down an item
                            if (!player.isCreative())
                                itemStack.shrink(1);
                            if (!world.isClientSide()) {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                painting.playPlacementSound();
                                world.addFreshEntity(painting);
                                Motive originalArt = painting.motive;
                                List<Motive> validArts = new ArrayList<>(); // list of paintings placeable at current location
                                Registry.MOTIVE.stream().forEach(art -> {
                                    painting.motive = art;

                                    // update the bounding box of the painting to make sure the simulation of
                                    // placing down a painting
                                    // happens correctly. Omitting this will result in overlapping paintings
                                    subaraki.paintings.Paintings.UTILITY.updatePaintingBoundingBox(painting);

                                    // simulate placing down a painting. if possible, add it to a list of paintings
                                    // that are possible to place at this location
                                    if (painting.survives()) {
                                        if (Paintings.config.use_vanilla_only) {
                                            if (art.equals(Motive.KEBAB) || art.equals(Motive.AZTEC) || art.equals(Motive.ALBAN) || art.equals(Motive.AZTEC2)
                                                    || art.equals(Motive.BOMB) || art.equals(Motive.PLANT) || art.equals(Motive.WASTELAND) || art.equals(Motive.POOL)
                                                    || art.equals(Motive.COURBET) || art.equals(Motive.SEA) || art.equals(Motive.SUNSET) || art.equals(Motive.CREEBET)
                                                    || art.equals(Motive.WANDERER) || art.equals(Motive.GRAHAM) || art.equals(Motive.MATCH) || art.equals(Motive.BUST)
                                                    || art.equals(Motive.STAGE) || art.equals(Motive.VOID) || art.equals(Motive.SKULL_AND_ROSES)
                                                    || art.equals(Motive.WITHER) || art.equals(Motive.FIGHTERS) || art.equals(Motive.POINTER)
                                                    || art.equals(Motive.PIGSCENE) || art.equals(Motive.BURNING_SKULL) || art.equals(Motive.SKELETON)
                                                    || art.equals(Motive.DONKEY_KONG)) {
                                                validArts.add(art);
                                            }
                                        } else {
                                            validArts.add(art);
                                        }
                                    }
                                });
                                // reset the art of the painting to the one registered before
                                painting.motive = originalArt;

                                subaraki.paintings.Paintings.UTILITY.updatePaintingBoundingBox(painting); // reset bounding box

                                // sort paintings from high to low, and from big to small
                                List<Motive> validArtsArray = validArts.stream().sorted(PaintingUtility.ART_COMPARATOR).toList();

                                ResourceLocation[] names = new ResourceLocation[validArtsArray.size()];
                                for (Motive m : validArtsArray) {
                                    names[validArtsArray.indexOf(m)] = Registry.MOTIVE.getKey(m);
                                }

                                // send to one player only, the player that needs his Gui opened !!
                                // this used to be sent to all around, but then everyone got the gui opened
                                // Encodes needed data and sends to client
                                FriendlyByteBuf buf = PacketByteBufs.create();
                                buf.writeInt(painting.getId());
                                buf.writeInt(names.length);
                                Arrays.stream(names).forEach(rl -> buf.writeUtf(rl.toString()));
                                ServerPlayNetworking.send(serverPlayer, ClientNetwork.CLIENT_PACKET, buf);
                            }
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            return InteractionResult.PASS;
        });
    }
}
