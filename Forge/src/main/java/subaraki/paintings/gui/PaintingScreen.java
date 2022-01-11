package subaraki.paintings.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.world.entity.decoration.Motive;
import subaraki.paintings.packet.NetworkHandler;
import subaraki.paintings.packet.server.SPacketPainting;

import java.util.Optional;

public class PaintingScreen extends CommonPaintingScreen {


    public PaintingScreen(Motive[] types, int entityID) {
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
    public void sendPacket(Motive motive, int entityID) {
        NetworkHandler.NETWORK.sendToServer(new SPacketPainting(motive, entityID));

    }
}
