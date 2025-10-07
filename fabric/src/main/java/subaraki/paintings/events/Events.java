package subaraki.paintings.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Painting;
import subaraki.paintings.event.ProcessPlacementEvent;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.supplier.PlacementPacketSupplier;

public class Events {

    public static void register() {
//        UseEntityCallback.EVENT.register((player, world, hand, target, hitResult) -> {
//            SyncpacketSupplier packetSupplier = (painting, serverPlayer) -> {
//                var packet = new CPacketPaintingUpdate(painting, BuiltInRegistries.PAINTING_VARIANT.getKey(painting.getVariant().value()));
//                //send to self
//                Network.getNetworkHandler().sendToClient(packet, serverPlayer);
//                for (ServerPlayer tracking : PlayerLookup.tracking(serverPlayer)) {
//                    //send to tracking
//                    Network.getNetworkHandler().sendToClient(packet, tracking);
//                }
//            };
//            return ProcessInteractEvent.processInteractPainting(player, target, hand, packetSupplier) ? InteractionResult.SUCCESS : InteractionResult.PASS;
//        });


        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof Painting painting) {
                subaraki.paintings.Paintings.UTILITY.updatePaintingBoundingBox(painting);
            }
        });


        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            System.out.println(world.isClientSide());
            if (!world.isClientSide()) {
                PlacementPacketSupplier preparePacket = (serverPlayer, painting, paintings) -> {
                    ServerPlayNetworking.send(serverPlayer, new CPacketPaintingScreen(paintings, hitResult.getBlockPos(), hitResult.getDirection()));
                };
                return ProcessPlacementEvent.processPlacementEvent(player.getItemInHand(hand), player, hitResult.getDirection(), hitResult.getBlockPos(), world, preparePacket) ?
                        InteractionResult.SUCCESS : InteractionResult.PASS;
            } else
                return InteractionResult.PASS;

        });
    }
}
