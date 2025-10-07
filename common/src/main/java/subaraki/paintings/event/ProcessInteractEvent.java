//package subaraki.paintings.event;
//
//import net.minecraft.core.Holder;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.decoration.Painting;
//import net.minecraft.world.entity.decoration.PaintingVariant;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.Items;
//import subaraki.paintings.mixin.IPaintingAccessor;
//import subaraki.paintings.network.supplier.SyncpacketSupplier;
//import subaraki.paintings.utils.PaintingUtility;
//import subaraki.paintings.utils.Services;
//
//import java.util.List;
//
//public class ProcessInteractEvent {
//
//    private static boolean equalSizes(PaintingVariant a, PaintingVariant b) {
//        return a.width() == b.width() && a.height() == b.height();
//    }
//
//    public static boolean processInteractPainting(Player player, Entity target, InteractionHand hand, SyncpacketSupplier syncpacketSupplier) {
//        var reg = player.level().registryAccess().registry(Registries.PAINTING_VARIANT).get(); //TODO orElse with custom error supplier
//        if (Services.CONFIG.getCyclePaintings())
//            if (target instanceof Painting painting) {
//                if (hand.equals(InteractionHand.MAIN_HAND)) {
//                    if (player instanceof ServerPlayer serverPlayer && serverPlayer.getItemInHand(hand).getItem().equals(Items.PAINTING)) {
//                        Holder<PaintingVariant> original = painting.getVariant();
//                        Holder<PaintingVariant> firstMatch = null;
//                        Holder<PaintingVariant> newArt = null;
//
//                        // it is important to sort the paintings from big to small so all same size
//                        // paintings will be next to one another
//                        List<ResourceLocation> validArtsArray = reg.keySet().stream().filter(paintingRegistryName -> PaintingUtility.ART_COMPARATOR.compare(reg.get(paintingRegistryName), original.value()) == 0).toList();
//
//                        boolean takeNext = false;
//                        for (ResourceLocation registryName : validArtsArray) {
//                            var variant = reg.get(registryName);
//                            var regEntry = reg.getResourceKey(variant);
//
//                            if (equalSizes(original.value(), variant) && regEntry.isPresent()) {
//                                if (firstMatch == null) {
//                                    firstMatch = reg.getHolderOrThrow(regEntry.get());
//                                }
//                                if (takeNext) {
//                                    newArt = reg.getHolderOrThrow(regEntry.get());
//                                    break;
//                                }
//                                if (reg.getKey(original.value()).equals(reg.getKey(variant))) {
//                                    takeNext = true;
//                                }
//
//                            } else if (takeNext) { // if the next one isn't of same size, loop back
//                                newArt = firstMatch;
//                                break;
//                            }
//                        }
//
//                        // if the paintinglist is done with (very last entry) then takeNext is set to true, but wont reloop to set the next painting
//                        // we do that here
//                        // newArt is null && takeNext is still set to true
//                        if (newArt == null && takeNext)
//                            newArt = firstMatch;
//
//                        if (newArt != null) { //newart shouldn't be null here, but with the new painting system, we can never be too sure
//                            ((IPaintingAccessor) painting).callSetVariant(newArt);
//                            syncpacketSupplier.send(painting, serverPlayer);
//                            return true;
//                        }
//
//                    }
//                }
//            }
//        return false;
//    }
//}
