package subaraki.paintings.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import subaraki.paintings.network.client.CPacketPainting;
import subaraki.paintings.network.server.SPacketPainting;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(PacketId.CHANNEL,
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public NetworkHandler() {
        new CPacketPainting().encrypt(PacketId.CPACKET);
        new SPacketPainting().encrypt(PacketId.SPACKET);
    }
}
