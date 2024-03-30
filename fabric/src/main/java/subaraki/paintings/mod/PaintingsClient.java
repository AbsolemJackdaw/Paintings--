package subaraki.paintings.mod;

import subaraki.paintings.events.ClientEvents;


public class PaintingsClient implements net.fabricmc.api.ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //NetworkHandler.registerClientPackets();
        ClientEvents.fixBoundingBoxEvent();
    }
}
