package subaraki.paintings.event;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.supplier.PlacementPacketSupplier;

@EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PlacePaintingEventHandler {

    @SubscribeEvent
    public static void onPaintingPlaced(PlayerInteractEvent.RightClickBlock event) {
        PlacementPacketSupplier preparePacket = (player, painting, variants) -> {
            PacketDistributor.sendToPlayer(player, new CPacketPaintingScreen(variants, event.getPos(), event.getFace()));
        };
        event.setCanceled(ProcessPlacementEvent.processPlacementEvent(event.getItemStack(), event.getEntity(), event.getFace(), event.getPos(), event.getLevel(), preparePacket));
    }
}
