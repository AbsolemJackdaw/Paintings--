package subaraki.paintings.network.supplier;

import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.gui.CommonPaintingScreen;

@FunctionalInterface
public interface ScreenSupplier {
    CommonPaintingScreen make(PaintingVariant[] motive, int entityID);
}
