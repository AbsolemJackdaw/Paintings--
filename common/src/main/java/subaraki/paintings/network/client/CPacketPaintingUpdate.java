package subaraki.paintings.network.client;//package subaraki.paintings.network.client;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import subaraki.paintings.network.NetworkHandler;

public record CPacketPaintingUpdate(PaintingVariant painting, int entityId) implements CustomPacketPayload {
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkHandler.CPACKETSYNC_TYPE;
    }
}
