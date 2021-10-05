package subaraki.paintings.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacketBase {

    void encode(FriendlyByteBuf buf);

    void decode(FriendlyByteBuf buf);

    void handle(Supplier<NetworkEvent.Context> context);

    void encrypt(int id);

}
