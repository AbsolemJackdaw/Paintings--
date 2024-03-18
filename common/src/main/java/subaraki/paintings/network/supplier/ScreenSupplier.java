package subaraki.paintings.network.supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.gui.CommonPaintingScreen;

@FunctionalInterface
public interface ScreenSupplier {
    CommonPaintingScreen make(PaintingVariant[] motive, BlockPos pos, Direction face);
}
