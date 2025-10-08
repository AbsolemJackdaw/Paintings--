package subaraki.paintings.network;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import subaraki.paintings.Paintings;
import subaraki.paintings.gui.PaintingScreen;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.client.CPacketPaintingUpdate;
import subaraki.paintings.network.server.SPacketPainting;

@EventBusSubscriber(modid = Paintings.MODID)
public class NetworkRegistry {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar NETWORK = event.registrar("1");
        NETWORK.playToClient(NetworkHandler.CPACKETSCREEN_TYPE, NetworkHandler.CPACKETSCREEN_CODEC, PayloadHandler::handleCPacketScreen);
        NETWORK.playToClient(NetworkHandler.CPACKETSYNC_TYPE, NetworkHandler.CPACKETSYNC_CODEC, PayloadHandler::handleCPacketSync);
        NETWORK.playToServer(NetworkHandler.SPACKETPAINTING_TYPE, NetworkHandler.SPACKETPAINTING_CODEC, PayloadHandler::handleSPacketUpdate);
    }

    private static class PayloadHandler {
        private static void handleCPacketScreen(final CPacketPaintingScreen packet, IPayloadContext context) {
            context.enqueueWork(() -> ProcessClientPacket.openScreen(packet.pos(), packet.direction(), packet.paintings(), PaintingScreen::new));
        }

        private static void handleSPacketUpdate(final SPacketPainting packet, IPayloadContext context) {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                var level = serverPlayer.level();
                context.enqueueWork(() -> ProcessServerPacket.handle(level, serverPlayer, packet.pos(), packet.direction(), packet.painting(), (painting, player) -> {
                    PacketDistributor.sendToPlayersTrackingEntity(serverPlayer, new CPacketPaintingUpdate(packet.painting(), painting.getId()));
                }));
            }
        }

        private static void handleCPacketSync(final CPacketPaintingUpdate packet, IPayloadContext context) {
            //context.enqueueWork(() -> ProcessClientPacket.updatePainting(packet.entityId(), packet.painting()));
        }
    }
}
