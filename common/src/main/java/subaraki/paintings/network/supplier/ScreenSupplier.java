package subaraki.paintings.network.supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.gui.PaintingScreen;

@FunctionalInterface
public interface ScreenSupplier {
    PaintingScreen make(PaintingVariant[] motive, BlockPos pos, Direction face);
}
