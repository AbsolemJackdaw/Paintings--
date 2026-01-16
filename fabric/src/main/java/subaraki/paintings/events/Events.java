package subaraki.paintings.events;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.InteractionResult;
import subaraki.paintings.event.ProcessPlacementEvent;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.supplier.PlacementPacketSupplier;

public class Events {

    public static void register() {

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
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
