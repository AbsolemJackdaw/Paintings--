package subaraki.paintings.event;

import java.util.Arrays;

import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.paintings.mod.ConfigData;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.util.ArtComparator;

public class PaintingInteractEvent {

    @SubscribeEvent
    public void interact(EntityInteractSpecific event) {

        if (ConfigData.cycle_paintings)
            if (event.getTarget() instanceof PaintingEntity) {
                if (event.getHand().equals(Hand.MAIN_HAND)) {
                    if (event.getPlayer() != null && event.getPlayer().getHeldItemMainhand().getItem().equals(Items.PAINTING)) {
                        PaintingEntity painting = (PaintingEntity) event.getTarget();
                        PaintingType original = painting.art;

                        PaintingType firstMatch = null;
                        PaintingType newArt = null;

                        // it is important to sort the paintings from big to small so all same size
                        // paintings will be next to one another
                        PaintingType[] validArtsArray = ForgeRegistries.PAINTING_TYPES.getValues().toArray(new PaintingType[0]);
                        Arrays.sort(validArtsArray, new ArtComparator());

                        boolean takeNext = false;
                        for (PaintingType type : validArtsArray) {

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

                        Paintings.utility.setArt(painting, newArt);
                    }
                }
            }
    }

    private boolean equalSizes(PaintingType a, PaintingType b) {

        return a.getWidth() == b.getWidth() && a.getHeight() == b.getHeight();
    }

    private boolean equalNames(PaintingType a, PaintingType b) {

        return a.getRegistryName().equals(b.getRegistryName());
    }
}
