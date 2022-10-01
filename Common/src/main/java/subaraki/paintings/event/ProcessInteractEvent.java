package subaraki.paintings.event;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import subaraki.paintings.mixin.IPaintingAccessor;
import subaraki.paintings.network.supplier.SyncpacketSupplier;
import subaraki.paintings.utils.CommonConfig;
import subaraki.paintings.utils.PaintingUtility;

import java.util.List;
import java.util.stream.Collectors;

public class ProcessInteractEvent {

    private static boolean equalSizes(PaintingVariant a, PaintingVariant b) {

        return a.getWidth() == b.getWidth() && a.getHeight() == b.getHeight();
    }

    private static boolean equalNames(PaintingVariant a, PaintingVariant b) {
        return Registry.PAINTING_VARIANT.getKey(a).equals(Registry.PAINTING_VARIANT.getKey(b));
    }

    public static void processInteractPainting(Player player, Entity target, InteractionHand hand, SyncpacketSupplier syncpacketSupplier) {
        if (CommonConfig.cycle_paintings)
            if (target instanceof Painting painting) {
                if (hand.equals(InteractionHand.MAIN_HAND)) {
                    if (player instanceof ServerPlayer serverPlayer && serverPlayer.getItemInHand(hand).getItem().equals(Items.PAINTING)) {
                        Holder<PaintingVariant> original = painting.getVariant();
                        Holder<PaintingVariant> firstMatch = null;
                        Holder<PaintingVariant> newArt = null;

                        // it is important to sort the paintings from big to small so all same size
                        // paintings will be next to one another
                        List<ResourceLocation> validArtsArray = Registry.PAINTING_VARIANT.keySet().stream().filter(paintingRegistryName -> {
                            return PaintingUtility.ART_COMPARATOR.compare(Registry.PAINTING_VARIANT.get(paintingRegistryName), original.value()) == 0;
                        }).toList();

                        boolean takeNext = false;
                        for (ResourceLocation registryName : validArtsArray) {
                            var variant = Registry.PAINTING_VARIANT.get(registryName);
                            var regEntry = Registry.PAINTING_VARIANT.getResourceKey(variant);

                            if (equalSizes(original.value(), variant) && regEntry.isPresent()) {
                                if (firstMatch == null) {
                                    firstMatch = Registry.PAINTING_VARIANT.getHolderOrThrow(regEntry.get());
                                }
                                if (takeNext) {
                                    newArt = Registry.PAINTING_VARIANT.getHolderOrThrow(regEntry.get());
                                    break;
                                }
                                if (equalNames(original.value(), variant)) {
                                    takeNext = true;
                                }

                            } else if (takeNext) { // if the next one isn't of same size, loop back
                                newArt = firstMatch;
                                break;
                            }
                        }

                        // if the paintinglist is done with (very last entry) then takeNext is set to true, but wont reloop to set the next painting
                        // we do that here
                        // newArt is null && takeNext is still set to true
                        if (newArt == null && takeNext)
                            newArt = firstMatch;

                        if (newArt != null) { //newart shouldn't be null here, but with the new painting system, we can never be too sure
                            ((IPaintingAccessor) painting).callSetVariant(newArt);
                            syncpacketSupplier.send(painting, serverPlayer);
                        }

                    }
                }
            }
    }
}
