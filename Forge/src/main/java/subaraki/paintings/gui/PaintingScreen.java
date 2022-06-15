package subaraki.paintings.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.network.server.SPacketPainting;

import java.util.List;
import java.util.Optional;

public class PaintingScreen extends CommonPaintingScreen {


    public PaintingScreen(PaintingVariant[] types, int entityID) {
        super(types, entityID);
    }

    @Override
    public Optional<AbstractWidget> optionalAbstractWidget(int index) {
        Widget w = renderables.get(index);
        if (w instanceof AbstractWidget aw)
            return Optional.of(aw);
        return Optional.empty();
    }

    @Override
    public void sendPacket(ResourceLocation variantName, int entityID) {
        NetworkHandler.NETWORK.sendToServer(new SPacketPainting(variantName, entityID));

    }

    @Override
    public List<Widget> getRenderablesWithCast() {
        return renderables;
    }
}
