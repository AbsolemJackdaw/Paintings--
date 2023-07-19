package subaraki.paintings.network;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.level.Level;
import subaraki.paintings.Paintings;
import subaraki.paintings.network.supplier.SyncpacketSupplier;

public class ProcessServerPacket {
    public static void handle(Level level, ServerPlayer player, int entityId, ResourceLocation paintingName, SyncpacketSupplier packet) {
        Entity entity = level.getEntity(entityId);
        if (entity instanceof Painting painting) {
            PaintingVariant variant = BuiltInRegistries.PAINTING_VARIANT.get(paintingName);
            subaraki.paintings.Paintings.UTILITY.setArt(painting, variant);
            Paintings.UTILITY.updatePaintingBoundingBox(painting);
            packet.send(painting, player);
        }
    }
}
