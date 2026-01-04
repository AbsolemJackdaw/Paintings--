package subaraki.paintings.network.supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import subaraki.paintings.gui.PaintingScreen;

import java.util.List;

@FunctionalInterface
public interface ScreenSupplier {
    PaintingScreen make(List<PaintingVariant> paintings, BlockPos pos, Direction face);
}
