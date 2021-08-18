package subaraki.paintings.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacketBase {

    public void encode(FriendlyByteBuf buf);

    public void decode(FriendlyByteBuf buf);

    public void handle(Supplier<NetworkEvent.Context> context);

    public void encrypt(int id);

}
