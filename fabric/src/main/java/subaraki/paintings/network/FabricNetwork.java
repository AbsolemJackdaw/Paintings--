package subaraki.paintings.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class FabricNetwork {

    public static void registerPackets() {
        PayloadTypeRegistry.playS2C().register(NetworkHandler.CPACKETSCREEN_TYPE, NetworkHandler.CPACKETSCREEN_CODEC);
        PayloadTypeRegistry.playS2C().register(NetworkHandler.CPACKETSYNC_TYPE, NetworkHandler.CPACKETSYNC_CODEC);
        PayloadTypeRegistry.playC2S().register(NetworkHandler.SPACKETPAINTING_TYPE, NetworkHandler.SPACKETPAINTING_CODEC);
    }

    public static void registerServer() {
        ServerNetwork.registerPayloadHandler();
    }

    public static void registerClient() {
        ClientNetwork.registerPayloadHandler();
    }
}
