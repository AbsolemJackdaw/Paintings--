package subaraki.paintings.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
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
import subaraki.paintings.utils.PaintingUtility;
import subaraki.paintings.utils.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProcessPlacementEvent {

    private static final List<ResourceKey<PaintingVariant>> vanillaPaintings = new ArrayList<>();

    static {
        vanillaPaintings.add(PaintingVariants.KEBAB);
        vanillaPaintings.add(PaintingVariants.AZTEC);
        vanillaPaintings.add(PaintingVariants.ALBAN);
        vanillaPaintings.add(PaintingVariants.AZTEC2);
        vanillaPaintings.add(PaintingVariants.BOMB);
        vanillaPaintings.add(PaintingVariants.PLANT);
        vanillaPaintings.add(PaintingVariants.WASTELAND);
        vanillaPaintings.add(PaintingVariants.POOL);
        vanillaPaintings.add(PaintingVariants.COURBET);
        vanillaPaintings.add(PaintingVariants.SEA);
        vanillaPaintings.add(PaintingVariants.SUNSET);
        vanillaPaintings.add(PaintingVariants.CREEBET);
        vanillaPaintings.add(PaintingVariants.WANDERER);
        vanillaPaintings.add(PaintingVariants.GRAHAM);
        vanillaPaintings.add(PaintingVariants.MATCH);
        vanillaPaintings.add(PaintingVariants.BUST);
        vanillaPaintings.add(PaintingVariants.STAGE);
        vanillaPaintings.add(PaintingVariants.VOID);
        vanillaPaintings.add(PaintingVariants.SKULL_AND_ROSES);
        vanillaPaintings.add(PaintingVariants.WITHER);
        vanillaPaintings.add(PaintingVariants.FIGHTERS);
        vanillaPaintings.add(PaintingVariants.POINTER);
        vanillaPaintings.add(PaintingVariants.PIGSCENE);
        vanillaPaintings.add(PaintingVariants.BURNING_SKULL);
        vanillaPaintings.add(PaintingVariants.SKELETON);
        vanillaPaintings.add(PaintingVariants.DONKEY_KONG);
        vanillaPaintings.add(PaintingVariants.EARTH);
        vanillaPaintings.add(PaintingVariants.WIND);
        vanillaPaintings.add(PaintingVariants.FIRE);
        vanillaPaintings.add(PaintingVariants.WATER);
    }

    public static boolean processPlacementEvent(ItemStack itemStack, Player player, Direction face, BlockPos blockPos, Level level, PlacementPacketSupplier send) {
        if (!Services.CONFIG.useSelectionGUI())
            return false;

        if (itemStack.getItem() == Items.PAINTING) {
            // Check if the item has a painting variant in its nbt.
            // If it does, don't perform painting++ behavior and default to vanilla painting placing behavior.
            CompoundTag tag = itemStack.getTag();
            if (tag != null && tag.contains("EntityTag", 10)) {
                CompoundTag entityTag = tag.getCompound("EntityTag");
                Optional<ResourceKey<PaintingVariant>> paintingVariantResourceKey = Painting.loadVariant(entityTag).flatMap(Holder::unwrapKey);
                if (paintingVariantResourceKey.isPresent()) {
                    PaintingVariant paintingVariant = BuiltInRegistries.PAINTING_VARIANT.get(paintingVariantResourceKey.get());
                    if (paintingVariant != null) {
                        return false;
                    }
                }
            }

            BlockPos actualPos = blockPos.relative(face);

            boolean flag = face.getAxis().isHorizontal();

            if (flag && player.mayUseItemAt(actualPos, face, itemStack)) {

                Optional<Painting> optPainting = Painting.create(level, actualPos, face);
                optPainting.ifPresent(paintingEntity -> { //if painting can be placed
                    paintingEntity.setYRot(face.toYRot());
                    // Set position updates bounding box
                    paintingEntity.setPos(actualPos.getX(), blockPos.getY(), actualPos.getZ());

                    if (paintingEntity.survives()) {
                        player.swing(InteractionHand.MAIN_HAND); // recreate the animation of placing down an item

                        if (!player.isCreative())
                            itemStack.shrink(1);

                        if (!level.isClientSide()) {

                            paintingEntity.playPlacementSound();
                            level.addFreshEntity(paintingEntity);

                            Holder<PaintingVariant> originalArt = paintingEntity.getVariant();

                            // list of paintings placeable at current location
                            //takes registry names
                            List<ResourceLocation> validArts = BuiltInRegistries.PAINTING_VARIANT.keySet().stream().filter(resourceLocation -> {
                                var variant = BuiltInRegistries.PAINTING_VARIANT.get(resourceLocation);
                                var regEntry = BuiltInRegistries.PAINTING_VARIANT.getResourceKey(variant);
                                if (regEntry.isPresent()) {
                                    ((IPaintingAccessor) paintingEntity).callSetVariant(BuiltInRegistries.PAINTING_VARIANT.getHolderOrThrow(regEntry.get()));
                                    return paintingEntity.survives() && (!Services.CONFIG.useVanillaOnly() || vanillaPaintings.contains(regEntry.get()));
                                }
                                return false;
                            }).toList();

                            // reset the art of the painting to the one registered before
                            ((IPaintingAccessor) paintingEntity).callSetVariant(originalArt);

                            Paintings.UTILITY.updatePaintingBoundingBox(paintingEntity); // reset bounding box

                            // sort paintings from high to low, and from big to small
                            List<PaintingVariant> sorted = (validArts.stream().map(BuiltInRegistries.PAINTING_VARIANT::get).sorted(PaintingUtility.ART_COMPARATOR)).toList();
                            //map resource<variant> to the registered resourcelocation
                            List<ResourceLocation> references = sorted.stream().map(BuiltInRegistries.PAINTING_VARIANT::getKey).toList();
                            //Send packet to open gui
                            send.send((ServerPlayer) player, paintingEntity, references.toArray(ResourceLocation[]::new));
                        }
                    }
                });

                return true;
            }
        }
        return false;
    }

}
