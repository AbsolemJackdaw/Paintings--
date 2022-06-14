package subaraki.paintings.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.level.Level;
import subaraki.paintings.Paintings;
import subaraki.paintings.network.supplier.SyncpacketSupplier;

public class ProcessServerPacket {
    public static void handle(Level level, ServerPlayer player, int entityId, PaintingVariant motive, SyncpacketSupplier packet) {
        Entity entity = level.getEntity(entityId);
        if (entity instanceof Painting painting) {
            subaraki.paintings.Paintings.UTILITY.setArt(painting, motive);
            Paintings.UTILITY.updatePaintingBoundingBox(painting);
            packet.send(painting, player);
        }
    }
}
