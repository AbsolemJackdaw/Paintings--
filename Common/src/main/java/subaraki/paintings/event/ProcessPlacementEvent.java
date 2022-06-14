package subaraki.paintings.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import subaraki.paintings.Paintings;
import subaraki.paintings.mixin.IPaintingAccessor;
import subaraki.paintings.network.supplier.PlacementPacketSupplier;
import subaraki.paintings.utils.CommonConfig;
import subaraki.paintings.utils.PaintingUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProcessPlacementEvent {

    public static boolean processPlacementEvent(ItemStack itemStack, Player player, Direction face, BlockPos blockPos, Level level, PlacementPacketSupplier send) {
        if (!CommonConfig.use_selection_gui)
            return false;

        if (itemStack.getItem() == Items.PAINTING) {

            BlockPos actualPos = blockPos.relative(face);

            boolean flag = face.getAxis().isHorizontal();

            if (flag && player.mayUseItemAt(actualPos, face, itemStack)) {

                Optional<Painting> optPainting = Painting.create(level, actualPos, face);
                optPainting.ifPresent(painting -> {
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

                            PaintingVariant originalArt = painting.getVariant().value();

                            List<PaintingVariant> validArts = new ArrayList<>(); // list of paintings placeable at current location

                            Registry.PAINTING_VARIANT.stream().forEach(art -> {
                                ((IPaintingAccessor) painting).callSetVariant(Holder.direct(art));

                                // update the bounding box of the painting to make sure the simulation of
                                // placing down a painting
                                // happens correctly. Omitting this will result in overlapping paintings
                                Paintings.UTILITY.updatePaintingBoundingBox(painting);

                                // simulate placing down a painting. if possible, add it to a list of paintings
                                // that are possible to place at this location
                                if (painting.survives()) {
                                    if (CommonConfig.use_vanilla_only) {
                                        if (art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.KEBAB)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.AZTEC)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.ALBAN)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.AZTEC2))
                                                || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.BOMB)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.PLANT)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.WASTELAND)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.POOL))
                                                || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.COURBET)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.SEA)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.SUNSET)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.CREEBET))
                                                || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.WANDERER)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.GRAHAM)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.MATCH)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.BUST))
                                                || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.STAGE)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.VOID)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.SKULL_AND_ROSES))
                                                || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.WITHER)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.FIGHTERS)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.POINTER))
                                                || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.PIGSCENE)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.BURNING_SKULL)) || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.SKELETON))
                                                || art.equals(Registry.PAINTING_VARIANT.get(PaintingVariants.DONKEY_KONG))) {
                                            validArts.add(art);
                                        }
                                    } else
                                        validArts.add(art);
                                }

                            });
                            // reset the art of the painting to the one registered before
                            ((IPaintingAccessor) painting).callSetVariant(Holder.direct(originalArt));

                            Paintings.UTILITY.updatePaintingBoundingBox(painting); // reset bounding box

                            // PaintingVariants[] validArtsArray = validArts.toArray(new PaintingVariants[0]);
                            // sort paintings from high to low, and from big to small
                            // Arrays.sort(validArtsArray, new ArtComparator());
                            List<PaintingVariant> validArtsArray = validArts.stream().sorted(PaintingUtility.ART_COMPARATOR).toList();

                            ResourceLocation[] names = new ResourceLocation[validArtsArray.size()];
                            for (PaintingVariant m : validArtsArray) {
                                names[validArtsArray.indexOf(m)] = Registry.PAINTING_VARIANT.getKey(m);
                            }
                            send.send((ServerPlayer) player, painting, names);
                        }
                    }
                });

                return true;
            }
        }
        return false;
    }

}
