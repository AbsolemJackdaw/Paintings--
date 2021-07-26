package subaraki.paintings.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

public interface IPacketBase {

    public void encode(FriendlyByteBuf buf);

    public void decode(FriendlyByteBuf buf);

    public void handle(Supplier<NetworkEvent.Context> context);

    public void encrypt(int id);

}
