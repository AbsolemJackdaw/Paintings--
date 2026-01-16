package subaraki.paintings.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import subaraki.paintings.network.FabricNetwork;
import subaraki.paintings.network.NetworkHandler;


public class PaintingsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricNetwork.registerClient();
        NetworkHandler.sendServerpacket = ClientPlayNetworking::send;
    }
}
