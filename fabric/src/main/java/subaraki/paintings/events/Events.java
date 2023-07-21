package subaraki.paintings.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Painting;
import subaraki.paintings.event.ProcessInteractEvent;
import subaraki.paintings.event.ProcessPlacementEvent;
import subaraki.paintings.network.ClientNetwork;
import subaraki.paintings.network.PacketId;
import subaraki.paintings.network.supplier.PlacementPacketSupplier;
import subaraki.paintings.network.supplier.SyncpacketSupplier;

public class Events {

    public static void events() {
        UseEntityCallback.EVENT.register((player, world, hand, target, hitResult) -> {

            SyncpacketSupplier packetSupplier = (painting, serverPlayer) -> {
                FriendlyByteBuf byteBuf = ClientNetwork.cPacket(painting.getId(), new String[]{BuiltInRegistries.PAINTING_VARIANT.getKey(painting.getVariant().value()).toString()});
                for (ServerPlayer tracking : PlayerLookup.tracking(serverPlayer)) {
                    ServerPlayNetworking.send(tracking, PacketId.CHANNEL, byteBuf);
                }
                ServerPlayNetworking.send(serverPlayer, PacketId.CHANNEL, byteBuf);
            };

            return ProcessInteractEvent.processInteractPainting(player, target, hand, packetSupplier) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        });


        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof Painting painting) {
                subaraki.paintings.Paintings.UTILITY.updatePaintingBoundingBox(painting);
            }
        });


        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            PlacementPacketSupplier SENDER = (serverPlayer, painting, names) -> {
                String[] simpleNames = new String[names.length];
                for (int i = 0; i < names.length; i++)
                    simpleNames[i] = names[i].toString();
                ServerPlayNetworking.send(serverPlayer, PacketId.CHANNEL, ClientNetwork.cPacket(painting.getId(), simpleNames));
            };
            return ProcessPlacementEvent.processPlacementEvent(player.getItemInHand(hand), player, hitResult.getDirection(), hitResult.getBlockPos(), world, SENDER) ?
                    InteractionResult.SUCCESS : InteractionResult.PASS;
        });
    }
}
