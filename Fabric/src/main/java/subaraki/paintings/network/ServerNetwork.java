package subaraki.paintings.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerNetwork {

    public static void registerServerPackets() {
        //Handles when server packet is received on server
        ServerPlayNetworking.registerGlobalReceiver(PacketId.CHANNEL, (server, serverPlayer, handler, buf, responseSender) -> {
            byte FORGE_PACKET_ID = buf.readByte();//FORGE PACKET COMPAT
            if (FORGE_PACKET_ID == PacketId.SPACKET) {//FORGE PACKET COMPAT
                String name = buf.readUtf();
                int entityId = buf.readInt();

                server.execute(() ->
                        ProcessServerPacket.handle(serverPlayer.level, serverPlayer, entityId, new ResourceLocation(name), (painting, p) -> {
                            FriendlyByteBuf byteBuf = ClientNetwork.cPacket(entityId, new String[]{name});
                            for (ServerPlayer tracking : PlayerLookup.tracking(serverPlayer)) {
                                ServerPlayNetworking.send(tracking, PacketId.CHANNEL, byteBuf);
                            }
                            ServerPlayNetworking.send(serverPlayer, PacketId.CHANNEL, byteBuf);
                        })
                );
            }
        });
    }

    public static FriendlyByteBuf sPacket(int entityId, ResourceLocation variantName) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeByte(PacketId.SPACKET); //FORGE PACKET COMPAT
        buf.writeUtf(variantName.toString());
        buf.writeInt(entityId);
        return buf;
    }
}
