package subaraki.paintings.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import subaraki.paintings.gui.PaintingScreen;

import java.util.Arrays;

public class ClientNetwork {


    public static void registerClientPackets() {
        //Handles when client packet is received on client
        ClientPlayNetworking.registerGlobalReceiver(PacketId.CHANNEL, (client, handler, buf, responseSender) -> {
            int FORGE_PACKET_ID = buf.readByte();//FORGE PACKET COMPAT
            if (FORGE_PACKET_ID == PacketId.CPACKET) {//FORGE PACKET COMPAT
                int entityId = buf.readInt();
                String[] resLocNames = new String[buf.readInt()];
                for (int i = 0; i < resLocNames.length; i++) {
                    resLocNames[i] = buf.readUtf();
                }
                client.execute(() -> {
                    ProcessClientPacket.handle(entityId, resLocNames, PaintingScreen::new);
                });
            }
        });
    }

    public static FriendlyByteBuf cPacket(int entityId, String[] names) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeByte((byte) PacketId.CPACKET); //FORGE PACKET COMPAT
        buf.writeInt(entityId);
        buf.writeInt(names.length);
        Arrays.stream(names).forEach(buf::writeUtf);
        return buf;
    }
}
