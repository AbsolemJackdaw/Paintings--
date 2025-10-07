package subaraki.paintings.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PaintingInteractEvent {

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.EntityInteract event) {
//        if (event.getLevel() instanceof ServerLevel level)
//            ProcessInteractEvent.processInteractPainting(event.getEntity(), event.getTarget(), event.getHand(), (painting, player) -> {
//                var packet = new CPacketPaintingUpdate(painting, BuiltInRegistries.PAINTING_VARIANT.getKey(painting.getVariant().value()));
//                Network.getNetworkHandler().sendToClientsInRange(packet, level, event.getPos(), 128.0D);
//            });
    }
}
