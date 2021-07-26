package subaraki.paintings.packet.client;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.packet.IPacketBase;
import subaraki.paintings.packet.NetworkHandler;
import subaraki.paintings.util.ClientReferences;

public class CPacketPainting implements IPacketBase {

    public CPacketPainting() {

    }

    private int entityID;
    private String resLocNames[];

    public CPacketPainting(Painting painting, ResourceLocation[] resLocs) {

        this.entityID = painting.getId();
        resLocNames = new String[resLocs.length];
        int index = 0;
        for (ResourceLocation resLoc : resLocs)
            resLocNames[index++] = resLoc.toString();
    }

    public CPacketPainting(FriendlyByteBuf buf) {

        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {

        buf.writeInt(entityID);

        buf.writeInt(resLocNames.length);

        for (String path : resLocNames)
            buf.writeUtf(path);

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
            if (resLocNames.length == 1) // we know what painting to set
            {
                Entity entity = ClientReferences.getClientPlayer().level.getEntity(entityID);
                if (entity instanceof Painting) {
                    Painting painting = (Painting) entity;
                    Motive type = ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(resLocNames[0]));
                    Paintings.UTILITY.setArt(painting, type);
                    Paintings.UTILITY.updatePaintingBoundingBox(painting);

                }
            } else // we need to open the painting gui to select a painting
            {
                Motive[] types = new Motive[resLocNames.length];
                int dex = 0;
                for (String path : resLocNames)
                    types[dex++] = ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(path));
                ClientReferences.openPaintingScreen(types, this.entityID);
            }
        });

        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, CPacketPainting.class, CPacketPainting::encode, CPacketPainting::new, CPacketPainting::handle);
    }

}
