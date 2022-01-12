package subaraki.paintings.network;

import net.minecraft.world.entity.decoration.Motive;
import subaraki.paintings.gui.CommonPaintingScreen;

@FunctionalInterface
public interface ScreenSupplier {
    CommonPaintingScreen make(Motive[] motive, int entityID);
}
