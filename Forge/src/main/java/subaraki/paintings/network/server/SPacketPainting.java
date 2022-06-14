package subaraki.paintings.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.paintings.network.IPacketBase;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.network.ProcessServerPacket;
import subaraki.paintings.network.client.CPacketPainting;

import java.util.function.Supplier;

public class SPacketPainting implements IPacketBase {

    private PaintingVariant type;
    private int entityID;

    public SPacketPainting() {

    }

    public SPacketPainting(PaintingVariant type, int entityID) {

        this.type = type;
        this.entityID = entityID;
    }

    public SPacketPainting(FriendlyByteBuf buff) {

        decode(buff);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(ForgeRegistries.PAINTING_VARIANTS.getKey(type).toString());
        buf.writeInt(entityID);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        String name = buf.readUtf(256);
        this.type = ForgeRegistries.PAINTING_VARIANTS.getValue(new ResourceLocation(name));
        this.entityID = buf.readInt();
    }

    @Override
    public void handle(Supplier<Context> context) {

        context.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = context.get().getSender();
            if (serverPlayer != null) {
                ResourceLocation registryName = ForgeRegistries.PAINTING_VARIANTS.getKey(type);
                ProcessServerPacket.handle(serverPlayer.level, serverPlayer, this.entityID, this.type,
                        (painting, player) -> NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with((() -> player)),
                                new CPacketPainting(painting, new ResourceLocation[]{registryName})));
            }
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, SPacketPainting.class, SPacketPainting::encode, SPacketPainting::new, SPacketPainting::handle);
    }
}