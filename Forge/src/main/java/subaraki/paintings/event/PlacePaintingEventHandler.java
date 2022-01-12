package subaraki.paintings.event;


import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import subaraki.paintings.network.SendForPlacement;
import subaraki.paintings.packet.NetworkHandler;
import subaraki.paintings.packet.client.CPacketPainting;

@Mod.EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlacePaintingEventHandler {

    @SubscribeEvent
    public static void onPaintingPlaced(PlayerInteractEvent.RightClickBlock event) {

        SendForPlacement SENDER = (serverPlayer, painting, names) -> NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with((() -> serverPlayer)), new CPacketPainting(painting, names));
        event.setCanceled(ProcessPlacementEvent.processPlacementEvent(event.getItemStack(), event.getPlayer(), event.getFace(), event.getPos(), event.getWorld(), SENDER));
    }
}
