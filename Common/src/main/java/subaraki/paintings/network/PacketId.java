package subaraki.paintings.network;

import net.minecraft.resources.ResourceLocation;
import subaraki.paintings.Paintings;

public class PacketId {

    public static final ResourceLocation CHANNEL = new ResourceLocation(Paintings.MODID, "paintingchannelnetwork");

    public static final byte CPACKET = 0;
    public static final byte SPACKET = 1;

}
