package subaraki.paintings.network.client;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.network.NetworkHandler;

import java.util.List;

public record CPacketPaintingScreen(List<PaintingVariant> paintings, BlockPos pos,
                                    Direction direction) implements CustomPacketPayload {
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return NetworkHandler.CPACKETSCREEN_TYPE;
    }
}

