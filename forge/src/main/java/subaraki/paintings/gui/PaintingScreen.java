package subaraki.paintings.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.network.server.SPacketPainting;

import java.util.List;
import java.util.Optional;

public class PaintingScreen extends CommonPaintingScreen {


    public PaintingScreen(PaintingVariant[] types, BlockPos pos, Direction face) {
        super(types, pos, face);
    }

    @Override
    public Optional<AbstractWidget> optionalAbstractWidget(int index) {
        if (index < renderables.size()) {//if index is within of bounds
            Renderable w = renderables.get(index);
            if (w instanceof AbstractWidget aw)
                return Optional.of(aw);
        }
        return Optional.empty();
    }

    @Override
    public void sendPacket(ResourceLocation variantName, BlockPos pos, Direction face) {
        NetworkHandler.NETWORK.sendToServer(new SPacketPainting(variantName, pos, face));

    }

    @Override
    public List<Renderable> getRenderablesWithCast() {
        return renderables;
    }
}
