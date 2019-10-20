package subaraki.paintings.packet.client;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;
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

    public CPacketPainting(PaintingEntity painting, ResourceLocation[] resLocs) {

        this.entityID = painting.getEntityId();
        resLocNames = new String[resLocs.length];
        int index = 0;
        for (ResourceLocation resLoc : resLocs)
            resLocNames[index++] = resLoc.toString();
    }

    public CPacketPainting(PacketBuffer buf) {

        decode(buf);
    }

    @Override
    public void encode(PacketBuffer buf) {

        buf.writeInt(entityID);

        buf.writeInt(resLocNames.length);

        for (String path : resLocNames)
            buf.writeString(path);

    }

    @Override
    public void decode(PacketBuffer buf) {

        entityID = buf.readInt();

        resLocNames = new String[buf.readInt()];

        for (int i = 0; i < resLocNames.length; i++)
            resLocNames[i] = buf.readString();
    }

    @Override
    public void handle(Supplier<Context> context) {

        context.get().enqueueWork(() -> {
            if (resLocNames.length == 1) // we know what painting to set
            {
                Entity entity = ClientReferences.getClientPlayer().world.getEntityByID(entityID);
                if (entity instanceof PaintingEntity) {
                    PaintingEntity painting = (PaintingEntity) entity;
                    PaintingType type = ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(resLocNames[0]));
                    Paintings.utility.setArt(painting, type);
                    Paintings.utility.updatePaintingBoundingBox(painting);

                }
            } else // we need to open the painting gui to select a painting
            {
                PaintingType[] types = new PaintingType[resLocNames.length];
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
