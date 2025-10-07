package subaraki.paintings.mod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import subaraki.paintings.events.Events;
import subaraki.paintings.network.FabricNetwork;
import subaraki.paintings.util.ModConfig;

public class Paintings implements ModInitializer {

    public static ModConfig config;

    @Override
    public void onInitialize() {
        FabricNetwork.registerPackets();
        FabricNetwork.registerServer();
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        Events.register();
    }
}
