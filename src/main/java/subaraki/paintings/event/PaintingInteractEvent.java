package subaraki.paintings.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.paintings.mod.ConfigData;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.util.PaintingUtility;

import java.util.List;

@EventBusSubscriber(modid = Paintings.MODID, bus = Bus.FORGE)
public class PaintingInteractEvent {

    @SubscribeEvent
    public static void interact(EntityInteractSpecific event) {

        if (ConfigData.cycle_paintings)
            if (event.getTarget() instanceof Painting) {
                if (event.getHand().equals(InteractionHand.MAIN_HAND)) {
                    if (event.getPlayer() != null && event.getPlayer().getMainHandItem().getItem().equals(Items.PAINTING)) {
                        Painting painting = (Painting) event.getTarget();
                        Motive original = painting.motive;

                        Motive firstMatch = null;
                        Motive newArt = null;

                        // it is important to sort the paintings from big to small so all same size
                        // paintings will be next to one another
                        //Motive[] validArtsArray = ForgeRegistries.PAINTING_TYPES.getValues().toArray(new Motive[0]);
                        //Arrays.sort(validArtsArray, new ArtComparator());
                        List<Motive> validArtsArray = ForgeRegistries.PAINTING_TYPES.getValues().stream().sorted(PaintingUtility.ART_COMPARATOR).toList();

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

    private static boolean equalSizes(Motive a, Motive b) {

        return a.getWidth() == b.getWidth() && a.getHeight() == b.getHeight();
    }

    private static boolean equalNames(Motive a, Motive b) {

        return a.getRegistryName().equals(b.getRegistryName());
    }
}
