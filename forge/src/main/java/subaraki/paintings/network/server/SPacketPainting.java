package subaraki.paintings.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import subaraki.paintings.network.IPacketBase;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.network.ProcessServerPacket;
import subaraki.paintings.network.client.CPacketPainting;

import java.util.function.Supplier;

public class SPacketPainting implements IPacketBase {

    private ResourceLocation variantName;
    private int entityID;

    public SPacketPainting() {

    }

    public SPacketPainting(ResourceLocation Name, int entityID) {

        this.variantName = Name;
        this.entityID = entityID;
    }

    public SPacketPainting(FriendlyByteBuf buff) {

        decode(buff);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(variantName.toString());
        buf.writeInt(entityID);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.variantName = new ResourceLocation(buf.readUtf(256));
        this.entityID = buf.readInt();
    }

    @Override
    public void handle(Supplier<Context> context) {

        context.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = context.get().getSender();
            if (serverPlayer != null) {
                ProcessServerPacket.handle(serverPlayer.level(), serverPlayer, this.entityID, this.variantName,
                        (painting, player) -> NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with((() -> player)),
                                new CPacketPainting(painting, new ResourceLocation[]{variantName})));
            }
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, SPacketPainting.class, SPacketPainting::encode, SPacketPainting::new, SPacketPainting::handle);
    }
}