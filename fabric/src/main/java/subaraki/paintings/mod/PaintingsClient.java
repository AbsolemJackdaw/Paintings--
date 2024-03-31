package subaraki.paintings.mod;

import subaraki.paintings.events.ClientEvents;
import subaraki.paintings.network.NetworkHandler;


public class PaintingsClient implements net.fabricmc.api.ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkHandler.registerServerPackets();
        NetworkHandler.registerClientPackets();
        ClientEvents.fixBoundingBoxEvent();
    }
}
