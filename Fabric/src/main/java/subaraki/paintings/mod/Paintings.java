package subaraki.paintings.mod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.events.Events;
import subaraki.paintings.network.ServerNetwork;
import subaraki.paintings.util.ModConfig;
import subaraki.paintings.utils.PaintingEntry;
import subaraki.paintings.utils.PaintingPackReader;

import static subaraki.paintings.Paintings.LOGGER;

public class Paintings implements ModInitializer {

    /* call init here, to read json files before any event is launched. */
    static {
        new PaintingPackReader().init();
    }

    public static ModConfig config;

    @Override
    public void onInitialize() {
        try {
            for (PaintingEntry entry : PaintingPackReader.PAINTINGS) {
                Registry.register(Registry.PAINTING_VARIANT, entry.getResLoc(), new PaintingVariant(entry.getSizeX(), entry.getSizeY()));
                LOGGER.info("Registered painting " + entry.getPaintingName());
            }
        } catch (ResourceLocationException e) {
            LOGGER.error("Skipping. Found Error: {}", e.getMessage());
        }
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        ServerNetwork.registerServerPackets();
        Events.events();
    }
}
