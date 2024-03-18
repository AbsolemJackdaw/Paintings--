package subaraki.paintings.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import subaraki.paintings.gui.PaintingScreen;

import java.util.Arrays;

public class ClientNetwork {


    public static void registerClientPackets() {
        //Handles when client packet is received on client
        ClientPlayNetworking.registerGlobalReceiver(PacketId.CHANNEL, (client, handler, buf, responseSender) -> {
            int FORGE_PACKET_ID = buf.readByte();//FORGE PACKET COMPAT
            if (FORGE_PACKET_ID == PacketId.CPACKETSCREEN) {//FORGE PACKET COMPAT
                BlockPos pos = buf.readBlockPos();
                Direction face = Direction.byName(buf.readUtf());

                String[] resLocNames = new String[buf.readInt()];
                for (int i = 0; i < resLocNames.length; i++) {
                    resLocNames[i] = buf.readUtf();
                }
                client.execute(() -> {
                    ProcessClientPacket.openScreen(pos, face, resLocNames, PaintingScreen::new);
                });
            } else if (FORGE_PACKET_ID == PacketId.CPACKETUPDATE) {
                ProcessClientPacket.updatePainting(buf.readInt(), buf.readUtf());
            }
        });
    }

    public static FriendlyByteBuf cPacketUpdate(int entityId, String name) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeByte((byte) PacketId.CPACKETUPDATE); //FORGE PACKET COMPAT
        buf.writeInt(entityId);
        buf.writeUtf(name);
        return buf;
    }

    public static FriendlyByteBuf cPacketScreen(BlockPos pos, Direction face, String[] names) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeByte((byte) PacketId.CPACKETSCREEN); //FORGE PACKET COMPAT
        buf.writeBlockPos(pos);
        buf.writeUtf(face.getName());
        buf.writeInt(names.length);
        Arrays.stream(names).forEach(buf::writeUtf);
        return buf;
    }
}
