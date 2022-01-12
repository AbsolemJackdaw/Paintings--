package subaraki.paintings.packet.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.paintings.network.ProcessServerPacket;
import subaraki.paintings.packet.IPacketBase;
import subaraki.paintings.packet.NetworkHandler;
import subaraki.paintings.packet.client.CPacketPainting;

import java.util.function.Supplier;

public class SPacketPainting implements IPacketBase {

    private Motive type;
    private int entityID;

    public SPacketPainting() {

    }

    public SPacketPainting(Motive type, int entityID) {

        this.type = type;
        this.entityID = entityID;
    }

    public SPacketPainting(FriendlyByteBuf buff) {

        decode(buff);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {

        buf.writeUtf(this.type.getRegistryName().toString());
        buf.writeInt(entityID);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {

        String name = buf.readUtf(256);
        this.type = ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(name));
        this.entityID = buf.readInt();
    }

    @Override
    public void handle(Supplier<Context> context) {

        context.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = context.get().getSender();
            if (serverPlayer != null)
                ProcessServerPacket.handle(serverPlayer.level, serverPlayer, this.entityID, this.type, (painting, player) -> NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with((() -> player)),
                        new CPacketPainting(painting, new ResourceLocation[]{type.getRegistryName()})));
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, SPacketPainting.class, SPacketPainting::encode, SPacketPainting::new, SPacketPainting::handle);
    }
}