package subaraki.paintings.packet;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.packet.client.CPacketPainting;
import subaraki.paintings.packet.server.SPacketPainting;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(Paintings.MODID, "paintingchannelnetwork"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public NetworkHandler() {

        int messageID = 0;
        new CPacketPainting().encrypt(messageID++);
        new SPacketPainting().encrypt(messageID++);
    }
}
