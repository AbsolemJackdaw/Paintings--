package subaraki.paintings.gui;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.mixins.ScreenAccessor;
import subaraki.paintings.network.PacketId;
import subaraki.paintings.network.ServerNetwork;

import java.util.List;
import java.util.Optional;

public class PaintingScreen extends CommonPaintingScreen {

    public PaintingScreen(PaintingVariant[] types, BlockPos pos, Direction face) {
        super(types, pos, face);
    }

    @Override
    public Optional<AbstractWidget> optionalAbstractWidget(int index) {
        Renderable w = getRenderablesWithCast().get(index);
        if (w instanceof AbstractWidget aw) return Optional.of(aw);
        return Optional.empty();
    }

    @Override
    public void sendPacket(ResourceLocation variantName, BlockPos pos, Direction face) {
        ClientPlayNetworking.send(PacketId.CHANNEL, ServerNetwork.sPacket(variantName, pos, face));

    }

    public List<Renderable> getRenderablesWithCast() {
        return ((ScreenAccessor) this).getRenderables();
    }
}
