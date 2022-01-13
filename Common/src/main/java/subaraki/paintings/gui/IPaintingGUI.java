package subaraki.paintings.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.world.entity.decoration.Motive;

import java.util.List;
import java.util.Optional;

public interface IPaintingGUI {
    List<Widget> getRenderablesWithCast();

    Optional<AbstractWidget> optionalAbstractWidget(int index);

    void sendPacket(Motive motive, int entityID);
}