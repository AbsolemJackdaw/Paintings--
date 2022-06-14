package subaraki.paintings.event;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
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
                        PaintingVariant original = painting.getVariant().value();
                        PaintingVariant firstMatch = null;
                        PaintingVariant newArt = null;

                        // it is important to sort the paintings from big to small so all same size
                        // paintings will be next to one another
                        //Motive[] validArtsArray = ForgeRegistries.PAINTING_TYPES.getValues().toArray(new Motive[0]);
                        //Arrays.sort(validArtsArray, new ArtComparator());
                        List<PaintingVariant> validArtsArray = Registry.PAINTING_VARIANT.stream().sorted(PaintingUtility.ART_COMPARATOR).toList();

                        boolean takeNext = false;
                        for (PaintingVariant type : validArtsArray) {

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

                        // if the paintinglist is done with (very last entry) then takeNext is set to true, but wont reloop to set the next painting
                        // we do that here
                        // newArt is null && takeNext is still set to true
                        if (newArt == null && takeNext)
                            newArt = firstMatch;

                        ((IPaintingAccessor) painting).callSetVariant(Holder.direct(newArt));

                        syncpacketSupplier.send(painting, serverPlayer);
                    }
                }
            }
    }
}
