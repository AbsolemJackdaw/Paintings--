package subaraki.paintings.events;

import commonnetwork.api.Network;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Painting;
import subaraki.paintings.event.ProcessInteractEvent;
import subaraki.paintings.event.ProcessPlacementEvent;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.client.CPacketPaintingUpdate;
import subaraki.paintings.network.supplier.PlacementPacketSupplier;
import subaraki.paintings.network.supplier.SyncpacketSupplier;

public class Events {

    public static void events() {
        UseEntityCallback.EVENT.register((player, world, hand, target, hitResult) -> {
            SyncpacketSupplier packetSupplier = (painting, serverPlayer) -> {
                var packet = new CPacketPaintingUpdate(painting, BuiltInRegistries.PAINTING_VARIANT.getKey(painting.getVariant().value()));
                //send to self
                Network.getNetworkHandler().sendToClient(packet, serverPlayer);
                for (ServerPlayer tracking : PlayerLookup.tracking(serverPlayer)) {
                    //send to tracking
                    Network.getNetworkHandler().sendToClient(packet, tracking);
                }
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
                var packet = new CPacketPaintingScreen(hitResult.getBlockPos(), hitResult.getDirection(), names);
                Network.getNetworkHandler().sendToClient(packet, serverPlayer);
            };
            return ProcessPlacementEvent.processPlacementEvent(player.getItemInHand(hand), player, hitResult.getDirection(), hitResult.getBlockPos(), world, SENDER) ?
                    InteractionResult.SUCCESS : InteractionResult.PASS;
        });
    }
}
