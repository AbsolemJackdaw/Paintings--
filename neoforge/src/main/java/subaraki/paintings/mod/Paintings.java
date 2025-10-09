package subaraki.paintings.mod;


import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.network.PacketDistributor;
import subaraki.paintings.network.NetworkHandler;

@Mod(subaraki.paintings.Paintings.MODID)
public class Paintings {

    public Paintings(ModContainer ctx) {
        ctx.registerConfig(ModConfig.Type.SERVER, ConfigData.SERVER_SPEC);
        ctx.registerConfig(ModConfig.Type.CLIENT, ConfigData.CLIENT_SPEC);
        NetworkHandler.sendServerpacket = PacketDistributor::sendToServer;
    }
}
