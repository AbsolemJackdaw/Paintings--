package subaraki.paintings.packet;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public interface IPacketBase {

    public void encode(PacketBuffer buf);

    public void decode(PacketBuffer buf);

    public void handle(Supplier<NetworkEvent.Context> context);

    public void encrypt(int id);

}
