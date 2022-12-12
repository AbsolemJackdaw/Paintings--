package subaraki.paintings.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public interface IPaintingGUI {
    List<Renderable> getRenderablesWithCast();

    Optional<AbstractWidget> optionalAbstractWidget(int index);

    void sendPacket(ResourceLocation variantName, int entityID);
}
