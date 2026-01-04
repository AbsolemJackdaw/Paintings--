package subaraki.paintings.network.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import subaraki.paintings.network.NetworkHandler;

public record SPacketPainting(PaintingVariant painting, BlockPos pos,
                              Direction direction) implements CustomPacketPayload {
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkHandler.SPACKETPAINTING_TYPE;
    }
}