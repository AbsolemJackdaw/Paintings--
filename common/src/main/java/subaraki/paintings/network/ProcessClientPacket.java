package subaraki.paintings.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.Paintings;
import subaraki.paintings.network.supplier.ScreenSupplier;
import subaraki.paintings.utils.ClientReferences;

import java.util.Arrays;

public class ProcessClientPacket {

    public static void updatePainting(int entityID, String variantName) {
        Entity entity = ClientReferences.getClientPlayer().level().getEntity(entityID);
        if (entity instanceof Painting painting) {
            PaintingVariant type = BuiltInRegistries.PAINTING_VARIANT.get(new ResourceLocation(variantName));
            subaraki.paintings.Paintings.UTILITY.setArt(painting, type);
            Paintings.UTILITY.updatePaintingBoundingBox(painting);
        }
    }

    public static void openScreen(BlockPos pos, Direction face, String[] possibleVariantNames, ScreenSupplier screen) {
        PaintingVariant[] types = Arrays.stream(possibleVariantNames).map(path -> BuiltInRegistries.PAINTING_VARIANT.get(new ResourceLocation(path))).toArray(PaintingVariant[]::new);
        ClientReferences.openPaintingScreen(screen.make(types, pos, face));
    }
}
