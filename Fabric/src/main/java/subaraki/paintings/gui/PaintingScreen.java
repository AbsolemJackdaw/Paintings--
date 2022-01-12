package subaraki.paintings.gui;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.world.entity.decoration.Motive;
import subaraki.paintings.mixins.ScreenAccessor;
import subaraki.paintings.network.PacketId;
import subaraki.paintings.network.ServerNetwork;

import java.util.List;
import java.util.Optional;

public class PaintingScreen extends CommonPaintingScreen {

    public PaintingScreen(Motive[] types, int entityID) {
        super(types, entityID);
    }

    @Override
    public Optional<AbstractWidget> optionalAbstractWidget(int index) {
        Widget w = getRenderablesWithCast().get(index);
        if (w instanceof AbstractWidget aw)
            return Optional.of(aw);
        return Optional.empty();
    }

    @Override
    public void sendPacket(Motive motive, int entityId) {
        ClientPlayNetworking.send(PacketId.CHANNEL, ServerNetwork.sPacket(entityId, motive));
    }

    public List<Widget> getRenderablesWithCast() {
        return ((ScreenAccessor) this).getRenderables();
    }
}
