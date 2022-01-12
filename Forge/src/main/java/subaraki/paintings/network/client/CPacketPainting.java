package subaraki.paintings.network.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraftforge.network.NetworkEvent.Context;
import subaraki.paintings.gui.PaintingScreen;
import subaraki.paintings.network.IPacketBase;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.network.ProcessClientPacket;

import java.util.Arrays;
import java.util.function.Supplier;

public class CPacketPainting implements IPacketBase {

    private int entityID;
    private String[] resLocNames;

    //needed for packet init
    public CPacketPainting() {

    }

    public CPacketPainting(Painting painting, ResourceLocation[] resLocs) {
        this.entityID = painting.getId();
        resLocNames = Arrays.stream(resLocs).map(ResourceLocation::toString).toArray(String[]::new);
    }

    public CPacketPainting(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityID);
        buf.writeInt(resLocNames.length);
        Arrays.stream(resLocNames).forEach(buf::writeUtf);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        entityID = buf.readInt();
        resLocNames = new String[buf.readInt()];
        for (int i = 0; i < resLocNames.length; i++)
            resLocNames[i] = buf.readUtf();
    }

    @Override
    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            ProcessClientPacket.handle(this.entityID, resLocNames, PaintingScreen::new);
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {
        NetworkHandler.NETWORK.registerMessage(id, CPacketPainting.class, CPacketPainting::encode, CPacketPainting::new, CPacketPainting::handle);
    }

}
