package subaraki.paintings.network;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import subaraki.paintings.network.client.CPacketPaintingUpdate;

public class ServerNetwork {

    public static void registerPayloadHandler() {

        ServerPlayNetworking.registerGlobalReceiver(NetworkHandler.SPACKETPAINTING_TYPE, (packet, context) -> {
            if (context.player() instanceof ServerPlayer serverPlayer && context.player().level() instanceof ServerLevel level) {
                level.getServer().execute(() -> ProcessServerPacket.handle(level, serverPlayer, packet.pos(), packet.direction(), packet.painting(), (painting, player) -> {
                    sendAround(serverPlayer, new CPacketPaintingUpdate(packet.painting(), painting.getId()));
                }));
            }
        });
    }

    private static void sendAround(Player player, CustomPacketPayload packet) {
        for (ServerPlayer playerTrackingThisPlayer : PlayerLookup.tracking(player)) {
            ServerPlayNetworking.send(playerTrackingThisPlayer, packet);
        }
    }
}
