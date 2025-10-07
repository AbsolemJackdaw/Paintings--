package subaraki.paintings.network.supplier;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.List;

@FunctionalInterface
public interface PlacementPacketSupplier {
    void send(ServerPlayer player, Painting painting, List<PaintingVariant> variants);
}
