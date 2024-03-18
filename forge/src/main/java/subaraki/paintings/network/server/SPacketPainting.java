package subaraki.paintings.network.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import subaraki.paintings.network.IPacketBase;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.network.ProcessServerPacket;
import subaraki.paintings.network.client.CPacketPaintingUpdate;

import java.util.function.Supplier;

public class SPacketPainting implements IPacketBase {

    private ResourceLocation variantName;
    private BlockPos pos;
    private Direction face;

    public SPacketPainting() {

    }

    public SPacketPainting(ResourceLocation name, BlockPos pos, Direction face) {
        this.variantName = name;
        this.pos = pos;
        this.face = face;
    }

    public SPacketPainting(FriendlyByteBuf buff) {

        decode(buff);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.variantName.toString());
        buf.writeBlockPos(this.pos);
        buf.writeUtf(this.face.getName());
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.variantName = new ResourceLocation(buf.readUtf(256));
        this.pos = buf.readBlockPos();
        this.face = Direction.byName(buf.readUtf(256));
    }

    @Override
    public void handle(Supplier<Context> context) {

        context.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = context.get().getSender();
            if (serverPlayer != null) {
                ProcessServerPacket.handle(serverPlayer.level(), serverPlayer, this.pos, this.face, this.variantName,
                        (painting, player) -> NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with((() -> player)),
                                new CPacketPaintingUpdate(painting, variantName)));
            }
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, SPacketPainting.class, SPacketPainting::encode, SPacketPainting::new, SPacketPainting::handle);
    }
}