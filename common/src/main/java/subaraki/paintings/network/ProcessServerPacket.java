package subaraki.paintings.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.level.Level;
import subaraki.paintings.Paintings;
import subaraki.paintings.network.supplier.SyncpacketSupplier;

public class ProcessServerPacket {
    public static void handle(Level level, ServerPlayer player, BlockPos pos, Direction direction, PaintingVariant painting, SyncpacketSupplier packet) {
        BlockPos actualPos = pos.relative(direction);

        level.registryAccess().registry(Registries.PAINTING_VARIANT).ifPresent(registry -> {
            registry.getHolder(ResourceKey.create(Registries.PAINTING_VARIANT, painting.assetId())).ifPresent(holder -> {
                var entity = new Painting(level, actualPos, direction, holder);
                entity.setYRot(direction.toYRot());
                entity.setPos(actualPos.getX(), pos.getY(), actualPos.getZ());
                Paintings.UTILITY.updatePaintingBoundingBox(entity);
                level.addFreshEntity(entity);
                if (!player.isCreative())
                    player.getItemInHand(player.getUsedItemHand()).shrink(1);
                packet.send(entity, player);
            });
        });
    }
}
