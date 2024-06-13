package subaraki.paintings.network.client;

import commonnetwork.api.Network;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Painting;
import subaraki.paintings.network.IPacketBase;
import subaraki.paintings.network.ProcessClientPacket;

public class CPacketPaintingUpdate implements IPacketBase<CPacketPaintingUpdate> {

    private int entityID;
    private String resLocName;

    //needed for packet init
    public CPacketPaintingUpdate() {
    }

    public CPacketPaintingUpdate(Painting painting, ResourceLocation resLoc) {
        this.entityID = painting.getId();
        resLocName = resLoc.toString();
    }

    private CPacketPaintingUpdate(FriendlyByteBuf buf) {
        decode(buf);
    }

    private CPacketPaintingUpdate(PacketContext<CPacketPaintingUpdate> context) {
        handle(context);
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
    public void handle(PacketContext<CPacketPaintingUpdate> context) {
        Minecraft.getInstance().execute(() -> {
            var msg = context.message();
            ProcessClientPacket.updatePainting(msg.entityID, msg.resLocName);
        });
    }

    @Override
    public void register(ResourceLocation resLoc) {
        Network.registerPacket(resLoc, CPacketPaintingUpdate.class, CPacketPaintingUpdate::encode, CPacketPaintingUpdate::new, this::handle);
    }
}
