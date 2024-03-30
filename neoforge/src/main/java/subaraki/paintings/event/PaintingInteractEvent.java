package subaraki.paintings.event;

import commonnetwork.api.Network;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import subaraki.paintings.network.client.CPacketPaintingUpdate;

@Mod.EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PaintingInteractEvent {

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.EntityInteract event) {
        if (event.getLevel() instanceof ServerLevel level)
            ProcessInteractEvent.processInteractPainting(event.getEntity(), event.getTarget(), event.getHand(), (painting, player) -> {
                var packet = new CPacketPaintingUpdate(painting, BuiltInRegistries.PAINTING_VARIANT.getKey(painting.getVariant().value()));
                Network.getNetworkHandler().sendToClientsInRange(packet, level, event.getPos(), 128.0D);
            });
    }
}
