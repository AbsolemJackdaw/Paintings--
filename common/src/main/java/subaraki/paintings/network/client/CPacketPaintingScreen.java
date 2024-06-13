package subaraki.paintings.network.client;

import commonnetwork.CommonNetworkMod;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import subaraki.paintings.gui.PaintingScreen;
import subaraki.paintings.network.IPacketBase;
import subaraki.paintings.network.ProcessClientPacket;

import java.util.Arrays;

public class CPacketPaintingScreen implements IPacketBase<CPacketPaintingScreen> {
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
    public void handle(PacketContext<CPacketPaintingScreen> context) {
        var msg = context.message();
        ProcessClientPacket.openScreen(msg.pos, msg.face, msg.resLocNames, PaintingScreen::new);
    }

    @Override
    public void register(ResourceLocation resLoc) {
        CommonNetworkMod.registerPacket(resLoc, CPacketPaintingScreen.class, CPacketPaintingScreen::encode, CPacketPaintingScreen::new, this::handle);
    }
}
