package subaraki.paintings.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import subaraki.paintings.Paintings;
import subaraki.paintings.network.SendForPlacement;
import subaraki.paintings.util.CommonConfig;
import subaraki.paintings.utils.PaintingUtility;

import java.util.ArrayList;
import java.util.List;

public class ProcessPlacementEvent {

    public static boolean processplacementEvent(ItemStack itemStack, Player player, Direction face, BlockPos blockPos, Level level, SendForPlacement send) {
        if (!CommonConfig.use_selection_gui)
            return false;

        if (itemStack.getItem() == Items.PAINTING) {

            BlockPos actualPos = blockPos.relative(face);

            boolean flag = face.getAxis().isHorizontal();

            if (flag && player.mayUseItemAt(actualPos, face, itemStack)) {

                Painting painting = new Painting(level, actualPos, face);
                painting.setYRot(face.toYRot());
                // Set position updates bounding box
                painting.setPos(actualPos.getX(), blockPos.getY(), actualPos.getZ());

                if (painting.survives()) {
                    player.swing(InteractionHand.MAIN_HAND); // recreate the animation of placing down an item

                    if (!player.isCreative())
                        itemStack.shrink(1);

                    if (!level.isClientSide()) {

                        painting.playPlacementSound();
                        level.addFreshEntity(painting);

                        Motive originalArt = painting.motive;

                        List<Motive> validArts = new ArrayList<>(); // list of paintings placeable at current location
                        Registry.MOTIVE.stream().forEach(art -> {
                            painting.motive = art;

                            // update the bounding box of the painting to make sure the simulation of
                            // placing down a painting
                            // happens correctly. Omiting this will result in overlapping paintings
                            subaraki.paintings.Paintings.UTILITY.updatePaintingBoundingBox(painting);

                            // simulate placing down a painting. if possible, add it to a list of paintings
                            // that are possible to place at this location
                            if (painting.survives()) {

                                if (CommonConfig.use_vanilla_only) {
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
                                } else
                                    validArts.add(art);
                            }

                        });
                        // reset the art of the painting to the one registered before
                        painting.motive = originalArt;

                        Paintings.UTILITY.updatePaintingBoundingBox(painting); // reset bounding box

                        // Motive[] validArtsArray = validArts.toArray(new Motive[0]);
                        // sort paintings from high to low, and from big to small
                        // Arrays.sort(validArtsArray, new ArtComparator());
                        List<Motive> validArtsArray = validArts.stream().sorted(PaintingUtility.ART_COMPARATOR).toList();

                        ResourceLocation[] names = new ResourceLocation[validArtsArray.size()];
                        for (Motive m : validArtsArray) {
                            names[validArtsArray.indexOf(m)] = Registry.MOTIVE.getKey(m);
                        }
                        send.away((ServerPlayer) player, painting, names);
                    }

                }
                return true;
            }
        }
        return false;
    }

}
