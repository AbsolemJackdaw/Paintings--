package subaraki.paintings.mod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.events.Events;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.util.ModConfig;
import subaraki.paintings.utils.PaintingEntry;
import subaraki.paintings.utils.PaintingPackReader;

import static subaraki.paintings.Paintings.LOGGER;

public class Paintings implements ModInitializer {

    public static ModConfig config;

    /* call init here, to read json files before any event is launched. */
    static {
        new PaintingPackReader().init();
    }

    @Override
    public void onInitialize() {
        try {
            for (PaintingEntry entry : PaintingPackReader.PAINTINGS) {
                Registry.register(BuiltInRegistries.PAINTING_VARIANT, entry.getResLoc(), new PaintingVariant(entry.getSizeX(), entry.getSizeY()));
                LOGGER.info("Registered painting " + entry.getPaintingName());
            }
        } catch (ResourceLocationException e) {
            LOGGER.error("Skipping. Found Error: {}", e.getMessage());
        }
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        NetworkHandler.registerPackets();
        Events.events();
    }
}
