package subaraki.paintings.packet.server;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.packet.IPacketBase;
import subaraki.paintings.packet.NetworkHandler;
import subaraki.paintings.packet.client.CPacketPainting;

public class SPacketPainting implements IPacketBase {

    public SPacketPainting() {

    }

    private PaintingType type;
    private int entityID;

    public SPacketPainting(PaintingType type, int entityID) {

        this.type = type;
        this.entityID = entityID;
    }

    public SPacketPainting(PacketBuffer buff) {

        decode(buff);
    }

    @Override
    public void encode(PacketBuffer buf) {

        buf.writeString(this.type.getRegistryName().toString());
        buf.writeInt(entityID);
    }

    @Override
    public void decode(PacketBuffer buf) {

        String name = buf.readString(256);
        this.type = ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(name));
        this.entityID = buf.readInt();
    }

    @Override
    public void handle(Supplier<Context> context) {

        context.get().enqueueWork(() -> {
            World world = context.get().getSender().world;
            Entity entity = world.getEntityByID(this.entityID);

            if (entity instanceof PaintingEntity) {
                PaintingEntity painting = (PaintingEntity) entity;

                Paintings.utility.setArt(painting, type);
                Paintings.utility.updatePaintingBoundingBox(painting);

                ServerPlayerEntity playerMP = context.get().getSender();
                NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with((() -> playerMP)),
                        new CPacketPainting(painting, new ResourceLocation[] { type.getRegistryName() }));
            }
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, SPacketPainting.class, SPacketPainting::encode, SPacketPainting::new, SPacketPainting::handle);
    }
}