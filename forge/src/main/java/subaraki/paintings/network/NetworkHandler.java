package subaraki.paintings.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.client.CPacketPaintingUpdate;
import subaraki.paintings.network.server.SPacketPainting;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1.1.0";

    public static final SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder.named(PacketId.CHANNEL).clientAcceptedVersions(NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION::equals)).serverAcceptedVersions(NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION::equals)).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();

    public NetworkHandler() {
        new CPacketPaintingUpdate().encrypt(PacketId.CPACKETUPDATE);
        new CPacketPaintingScreen().encrypt(PacketId.CPACKETSCREEN);
        new SPacketPainting().encrypt(PacketId.SPACKET);
    }

}
