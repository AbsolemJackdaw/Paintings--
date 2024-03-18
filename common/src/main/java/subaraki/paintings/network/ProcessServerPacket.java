package subaraki.paintings.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.level.Level;
import subaraki.paintings.Paintings;
import subaraki.paintings.network.supplier.SyncpacketSupplier;

import java.util.Optional;

public class ProcessServerPacket {
    public static void handle(Level level, ServerPlayer player, BlockPos pos, Direction face, ResourceLocation paintingName, SyncpacketSupplier packet) {
        BlockPos actualPos = pos.relative(face);
        Optional<Painting> entity = Painting.create(level, actualPos, face);
        entity.ifPresent(painting -> {
            painting.setYRot(face.toYRot());
            // Set position updates bounding box
            painting.setPos(actualPos.getX(), pos.getY(), actualPos.getZ());
            PaintingVariant variant = BuiltInRegistries.PAINTING_VARIANT.get(paintingName);
            subaraki.paintings.Paintings.UTILITY.setArt(painting, variant);
            Paintings.UTILITY.updatePaintingBoundingBox(painting);
            level.addFreshEntity(painting);
            packet.send(painting, player);
        });
    }
}
