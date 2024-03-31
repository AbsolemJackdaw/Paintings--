package subaraki.paintings.network;

import commonnetwork.networking.data.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IPacketBase<T> {

    void encode(FriendlyByteBuf buf);

    void decode(FriendlyByteBuf buf);

    void handle(PacketContext<T> context);

    void register(ResourceLocation res);

}
