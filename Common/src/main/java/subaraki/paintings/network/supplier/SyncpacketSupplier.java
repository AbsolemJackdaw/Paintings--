package subaraki.paintings.network.supplier;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.Painting;

@FunctionalInterface
public interface SyncpacketSupplier {
    void send(Painting painting, ServerPlayer player);
}
