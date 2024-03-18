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

public class CPacketPaintingUpdate implements IPacketBase {

    private int entityID;
    private String resLocName;

    //needed for packet init
    public CPacketPaintingUpdate() {

    }

    public CPacketPaintingUpdate(Painting painting, ResourceLocation resLoc) {
        this.entityID = painting.getId();
        resLocName = resLoc.toString();
    }

    public CPacketPaintingUpdate(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityID);
        buf.writeUtf(resLocName);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        entityID = buf.readInt();
        resLocName = buf.readUtf();
    }

    @Override
    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            ProcessClientPacket.updatePainting(this.entityID, resLocName);
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {
        NetworkHandler.NETWORK.registerMessage(id, CPacketPaintingUpdate.class, CPacketPaintingUpdate::encode, CPacketPaintingUpdate::new, CPacketPaintingUpdate::handle);
    }

}
