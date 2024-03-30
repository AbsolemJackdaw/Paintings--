package subaraki.paintings.event;


import commonnetwork.api.Network;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.supplier.PlacementPacketSupplier;

@Mod.EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlacePaintingEventHandler {

    @SubscribeEvent
    public static void onPaintingPlaced(PlayerInteractEvent.RightClickBlock event) {
        PlacementPacketSupplier packetSupplier = (serverPlayer, painting, names) -> {
            var packet = new CPacketPaintingScreen(event.getPos(), event.getFace(), names);
            Network.getNetworkHandler().sendToClient(packet, serverPlayer);
        };
        event.setCanceled(ProcessPlacementEvent.processPlacementEvent(event.getItemStack(), event.getEntity(), event.getFace(), event.getPos(), event.getLevel(), packetSupplier));
    }
}
