package subaraki.paintings.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class ServerNetwork {

    public static void registerServerPackets() {
        //Handles when server packet is received on server
        ServerPlayNetworking.registerGlobalReceiver(PacketId.CHANNEL, (server, serverPlayer, handler, buf, responseSender) -> {
            byte FORGE_PACKET_ID = buf.readByte();//FORGE PACKET COMPAT
            if (FORGE_PACKET_ID == PacketId.SPACKET) {//FORGE PACKET COMPAT
                String name = buf.readUtf();
                PaintingVariant type = Registry.PAINTING_VARIANT.get(new ResourceLocation(name));
                int entityId = buf.readInt();

                server.execute(() ->
                        ProcessServerPacket.handle(serverPlayer.level, serverPlayer, entityId, type, (painting, p) -> {
                            FriendlyByteBuf byteBuf = ClientNetwork.cPacket(entityId, new String[]{Registry.PAINTING_VARIANT.getKey(type).toString()});
                            for (ServerPlayer tracking : PlayerLookup.tracking(serverPlayer)) {
                                ServerPlayNetworking.send(tracking, PacketId.CHANNEL, byteBuf);
                            }
                            ServerPlayNetworking.send(serverPlayer, PacketId.CHANNEL, byteBuf);
                        })
                );
            }
        });
    }

    public static FriendlyByteBuf sPacket(int entityId, PaintingVariant motive) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeByte(PacketId.SPACKET); //FORGE PACKET COMPAT
        buf.writeUtf(Registry.PAINTING_VARIANT.getKey(motive).toString());
        buf.writeInt(entityId);
        return buf;
    }
}
