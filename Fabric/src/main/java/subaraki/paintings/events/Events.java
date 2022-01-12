package subaraki.paintings.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import subaraki.paintings.event.ProcessInteractEvent;
import subaraki.paintings.event.ProcessPlacementEvent;
import subaraki.paintings.network.ClientNetwork;
import subaraki.paintings.network.supplier.PlacementPacketSupplier;

import java.util.Arrays;

public class Events {

    private static boolean equalSizes(Motive a, Motive b) {
        return a.getWidth() == b.getWidth() && a.getHeight() == b.getHeight();
    }

    private static boolean equalNames(Motive a, Motive b) {
        return Registry.MOTIVE.getKey(a).equals(Registry.MOTIVE.getKey(b));
    }

    public static void events() {
        UseEntityCallback.EVENT.register((player, world, hand, target, hitResult) -> {
            ProcessInteractEvent.processInteractPainting(player, target, hand);
            return InteractionResult.PASS;
        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof Painting painting) {
                subaraki.paintings.Paintings.UTILITY.updatePaintingBoundingBox(painting);
            }
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            PlacementPacketSupplier SENDER = (serverPlayer, painting, names) -> {
                FriendlyByteBuf buf = PacketByteBufs.create();
                buf.writeInt(painting.getId());
                buf.writeInt(names.length);
                Arrays.stream(names).forEach(rl -> buf.writeUtf(rl.toString()));
                ServerPlayNetworking.send(serverPlayer, ClientNetwork.CLIENT_PACKET, buf);
            };
            return ProcessPlacementEvent.processPlacementEvent(player.getItemInHand(hand), player, hitResult.getDirection(), hitResult.getBlockPos(), world, SENDER) ?
                    InteractionResult.SUCCESS : InteractionResult.PASS;
        });
    }
}
