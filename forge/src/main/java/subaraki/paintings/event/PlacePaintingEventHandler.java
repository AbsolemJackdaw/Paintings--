package subaraki.paintings.event;


import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.client.CPacketPaintingUpdate;
import subaraki.paintings.network.supplier.PlacementPacketSupplier;

@Mod.EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlacePaintingEventHandler {

    @SubscribeEvent
    public static void onPaintingPlaced(PlayerInteractEvent.RightClickBlock event) {

        PlacementPacketSupplier SENDER = (serverPlayer, painting, names) -> NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with((() -> serverPlayer)), new CPacketPaintingScreen(event.getPos(), event.getFace(), names));
        event.setCanceled(ProcessPlacementEvent.processPlacementEvent(event.getItemStack(), event.getEntity(), event.getFace(), event.getPos(), event.getLevel(), SENDER));
    }
}
