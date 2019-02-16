package subaraki.paintings.mod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import subaraki.paintings.mod.network.CPacketSyncPaintingData.CPacketSyncPaintingDataHandler;

public class NetworkHandler {
	
	private static final String CHANNEL = "PaintingsChannel";
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
	
	public NetworkHandler() {
		NETWORK.registerMessage(CPacketSyncPaintingDataHandler.class, CPacketSyncPaintingData.class, 0, Side.CLIENT);
	}
}
