package subaraki.paintings.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import subaraki.paintings.network.supplier.ScreenSupplier;
import subaraki.paintings.utils.ClientReferences;

import java.util.List;

public class ProcessClientPacket {

//    public static void updatePainting(int entityID, PaintingVariant variant) {
//        Entity entity = ClientReferences.getClientPlayer().level().getEntity(entityID);
//        if (entity instanceof Painting painting) {
//            subaraki.paintings.Paintings.UTILITY.setArt(painting, variant);
//            Paintings.UTILITY.updatePaintingBoundingBox(painting);
//        }
//    }

    public static void openScreen(BlockPos pos, Direction face, List<PaintingVariant> paintings, ScreenSupplier screen) {
        ClientReferences.openPaintingScreen(screen.make(paintings, pos, face));
    }
}
