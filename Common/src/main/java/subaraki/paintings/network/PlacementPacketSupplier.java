package subaraki.paintings.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.Painting;

@FunctionalInterface
public interface PlacementPacketSupplier {
    void send(ServerPlayer player, Painting painting, ResourceLocation[] names);
}
