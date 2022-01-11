package subaraki.paintings.event;

import net.minecraft.core.Registry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import subaraki.paintings.Paintings;
import subaraki.paintings.util.CommonConfig;
import subaraki.paintings.utils.PaintingUtility;

import java.util.List;

public class ProcessInteractEvent {

    private static boolean equalSizes(Motive a, Motive b) {

        return a.getWidth() == b.getWidth() && a.getHeight() == b.getHeight();
    }

    private static boolean equalNames(Motive a, Motive b) {
        return Registry.MOTIVE.getKey(a).equals(Registry.MOTIVE.getKey(b));
    }

    public static void processInteractPainting(Player player, Entity target, InteractionHand hand) {
        if (CommonConfig.cycle_paintings)
            if (target instanceof Painting painting) {
                if (hand.equals(InteractionHand.MAIN_HAND)) {
                    if (player != null && player.getMainHandItem().getItem().equals(Items.PAINTING)) {
                        Motive original = painting.motive;
                        Motive firstMatch = null;
                        Motive newArt = null;

                        // it is important to sort the paintings from big to small so all same size
                        // paintings will be next to one another
                        //Motive[] validArtsArray = ForgeRegistries.PAINTING_TYPES.getValues().toArray(new Motive[0]);
                        //Arrays.sort(validArtsArray, new ArtComparator());
                        List<Motive> validArtsArray = Registry.MOTIVE.stream().sorted(PaintingUtility.ART_COMPARATOR).toList();

                        boolean takeNext = false;
                        for (Motive type : validArtsArray) {

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

                        Paintings.UTILITY.setArt(painting, newArt);
                    }
                }
            }
    }

}
