package subaraki.paintings.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.Paintings;
import subaraki.paintings.network.client.CPacketPaintingScreen;
import subaraki.paintings.network.client.CPacketPaintingUpdate;
import subaraki.paintings.network.server.SPacketPainting;

import java.util.function.Consumer;

public class NetworkHandler {

    public static Consumer<SPacketPainting> sendServerpacket;

    private static final ResourceLocation SPACKETPAINTING = ResourceLocation.fromNamespaceAndPath(Paintings.MODID, "s_painting");
    public static final CustomPacketPayload.Type<SPacketPainting> SPACKETPAINTING_TYPE = new CustomPacketPayload.Type<>(SPACKETPAINTING);
    public static StreamCodec<RegistryFriendlyByteBuf, SPacketPainting> SPACKETPAINTING_CODEC = StreamCodec.composite(
            PaintingVariant.DIRECT_STREAM_CODEC,
            SPacketPainting::painting,
            BlockPos.STREAM_CODEC,
            SPacketPainting::pos,
            Direction.STREAM_CODEC,
            SPacketPainting::direction,
            SPacketPainting::new);

    private static final ResourceLocation CPACKETSCREEN = ResourceLocation.fromNamespaceAndPath(Paintings.MODID, "c_screen");
    public static final CustomPacketPayload.Type<CPacketPaintingScreen> CPACKETSCREEN_TYPE = new CustomPacketPayload.Type<>(CPACKETSCREEN);
    public static StreamCodec<RegistryFriendlyByteBuf, CPacketPaintingScreen> CPACKETSCREEN_CODEC = StreamCodec.composite(
            PaintingVariant.DIRECT_STREAM_CODEC.apply(ByteBufCodecs.list()),
            CPacketPaintingScreen::paintings,
            BlockPos.STREAM_CODEC,
            CPacketPaintingScreen::pos,
            Direction.STREAM_CODEC,
            CPacketPaintingScreen::direction,
            CPacketPaintingScreen::new);

    private static final ResourceLocation CPACKETSYNC = ResourceLocation.fromNamespaceAndPath(Paintings.MODID, "c_sync");
    public static final CustomPacketPayload.Type<CPacketPaintingUpdate> CPACKETSYNC_TYPE = new CustomPacketPayload.Type<>(CPACKETSYNC);
    public static StreamCodec<RegistryFriendlyByteBuf, CPacketPaintingUpdate> CPACKETSYNC_CODEC = StreamCodec.composite(
            PaintingVariant.DIRECT_STREAM_CODEC,
            CPacketPaintingUpdate::painting,
            ByteBufCodecs.INT,
            CPacketPaintingUpdate::entityId,
            CPacketPaintingUpdate::new);
}
