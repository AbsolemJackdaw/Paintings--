package subaraki.paintings.network.client;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import subaraki.paintings.gui.PaintingScreen;
import subaraki.paintings.network.IPacketBase;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.network.ProcessClientPacket;

import java.util.Arrays;
import java.util.function.Supplier;

public class CPacketPaintingScreen implements IPacketBase {
    private String[] resLocNames;
    private BlockPos pos;
    private Direction face;

    public CPacketPaintingScreen() {
    }

    public CPacketPaintingScreen(BlockPos pos, Direction face, ResourceLocation[] resLocs) {
        this.pos = pos;
        this.face = face;
        resLocNames = Arrays.stream(resLocs).map(ResourceLocation::toString).toArray(String[]::new);
    }

    public CPacketPaintingScreen(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUtf(face.getName());
        buf.writeInt(resLocNames.length);
        Arrays.stream(resLocNames).forEach(buf::writeUtf);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.face = Direction.byName(buf.readUtf());
        resLocNames = new String[buf.readInt()];
        for (int i = 0; i < resLocNames.length; i++)
            resLocNames[i] = buf.readUtf();
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ProcessClientPacket.openScreen(pos, face, resLocNames, PaintingScreen::new);
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {
        NetworkHandler.NETWORK.registerMessage(id, CPacketPaintingScreen.class, CPacketPaintingScreen::encode, CPacketPaintingScreen::new, CPacketPaintingScreen::handle);

    }
}
