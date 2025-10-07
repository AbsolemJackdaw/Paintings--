package subaraki.paintings.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.List;
import java.util.Optional;

public interface IPaintingGUI {
    List<Renderable> getRenderablesWithCast();

    Optional<AbstractWidget> optionalAbstractWidget(int index);

    void sendPacket(PaintingVariant painting, BlockPos pos, Direction face);
}
