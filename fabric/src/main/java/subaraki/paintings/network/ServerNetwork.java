package subaraki.paintings.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
                BlockPos pos = buf.readBlockPos();
                Direction face = Direction.byName(buf.readUtf());

                server.execute(() ->
                        ProcessServerPacket.handle(serverPlayer.level(), serverPlayer, pos, face, new ResourceLocation(name), (painting, p) -> {
                            FriendlyByteBuf byteBuf = ClientNetwork.cPacketUpdate(painting.getId(), name);
                            for (ServerPlayer tracking : PlayerLookup.tracking(serverPlayer)) {
                                ServerPlayNetworking.send(tracking, PacketId.CHANNEL, byteBuf);
                            }
                            ServerPlayNetworking.send(serverPlayer, PacketId.CHANNEL, byteBuf);
                        })
                );
            }
        });
    }

    public static FriendlyByteBuf sPacket(ResourceLocation variantName, BlockPos pos, Direction face) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeByte(PacketId.SPACKET); //FORGE PACKET COMPAT
        buf.writeUtf(variantName.toString());
        buf.writeBlockPos(pos);
        buf.writeUtf(face.getName());
        return buf;
    }
}
