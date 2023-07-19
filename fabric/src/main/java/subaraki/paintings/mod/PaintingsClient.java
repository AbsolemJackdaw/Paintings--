package subaraki.paintings.mod;

import subaraki.paintings.events.ClientEvents;
import subaraki.paintings.network.ClientNetwork;


public class PaintingsClient implements net.fabricmc.api.ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetwork.registerClientPackets();
        ClientEvents.fixBoundingBoxEvent();
    }
}
