package subaraki.paintings.network;

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

    public static void handle(int entityID, String[] possibleVariantNames, ScreenSupplier screen) {
        if (possibleVariantNames.length == 1) // we know what painting to set
        {
            Entity entity = ClientReferences.getClientPlayer().level.getEntity(entityID);
            if (entity instanceof Painting painting) {
                PaintingVariant type = BuiltInRegistries.PAINTING_VARIANT.get(new ResourceLocation(possibleVariantNames[0]));
                subaraki.paintings.Paintings.UTILITY.setArt(painting, type);
                Paintings.UTILITY.updatePaintingBoundingBox(painting);

            }
        } else // we need to open the painting gui to select a painting
        {
            PaintingVariant[] types = Arrays.stream(possibleVariantNames).map(path -> BuiltInRegistries.PAINTING_VARIANT.get(new ResourceLocation(path))).toArray(PaintingVariant[]::new);
            ClientReferences.openPaintingScreen(screen.make(types, entityID));
        }
    }
}
