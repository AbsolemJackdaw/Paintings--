package subaraki.paintings.network;

import net.minecraft.resources.ResourceLocation;
import subaraki.paintings.Paintings;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.client.CPacketPaintingUpdate;
import subaraki.paintings.network.server.SPacketPainting;

public class NetworkHandler {

    public static void registerPackets() {
        new CPacketPaintingUpdate().register(new ResourceLocation(Paintings.MODID, "client_update"));
        new CPacketPaintingScreen().register(new ResourceLocation(Paintings.MODID, "client_screen"));
        new SPacketPainting().register(new ResourceLocation(Paintings.MODID, "server_update"));
    }
}
