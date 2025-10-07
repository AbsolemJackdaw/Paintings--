package subaraki.paintings.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import subaraki.paintings.gui.PaintingScreen;

public class ClientNetwork {

    public static void registerPayloadHandler() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkHandler.CPACKETSCREEN_TYPE, (packet, context) -> {
            context.client().execute(() -> ProcessClientPacket.openScreen(packet.pos(), packet.direction(), packet.paintings(), PaintingScreen::new));
        });

        ClientPlayNetworking.registerGlobalReceiver(NetworkHandler.CPACKETSYNC_TYPE, (packet, context) -> {
            context.client().execute(() -> ProcessClientPacket.updatePainting(packet.entityId(), packet.painting()));
        });
    }
}
