package subaraki.paintings.network.server;

import commonnetwork.api.Dispatcher;
import commonnetwork.api.Network;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import subaraki.paintings.network.IPacketBase;
import subaraki.paintings.network.ProcessServerPacket;
import subaraki.paintings.network.client.CPacketPaintingUpdate;

public class SPacketPainting implements IPacketBase<SPacketPainting> {

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
    public void handle(PacketContext<SPacketPainting> context) {
        if (context.sender() instanceof ServerPlayer serverPlayer) {
            ProcessServerPacket.handle(serverPlayer.level(), serverPlayer, this.pos, this.face, this.variantName,
                    (painting, player) -> Dispatcher.sendToClientsInRange(new CPacketPaintingUpdate(painting, variantName), (ServerLevel) serverPlayer.level(), this.pos, 128.0D));
        }
    }

    @Override
    public void encrypt(ResourceLocation resLoc) {
        Network.registerPacket(resLoc, SPacketPainting.class, (clazz, buf) -> encode(buf), SPacketPainting::new, this::handle);
    }
}