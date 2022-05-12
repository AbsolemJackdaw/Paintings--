package subaraki.paintings.mod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Motive;
import subaraki.paintings.events.Events;
import subaraki.paintings.network.ServerNetwork;
import subaraki.paintings.util.ModConfig;
import subaraki.paintings.utils.CommonConfig;
import subaraki.paintings.utils.PaintingEntry;
import subaraki.paintings.utils.PaintingPackReader;

import static subaraki.paintings.Paintings.LOGGER;

public class Paintings implements ModInitializer {

    /* call init here, to read json files before any event is launched. */
    static {
        new PaintingPackReader().init();
    }

    @Override
    public void onInitialize() {
        try {
            for (PaintingEntry entry : PaintingPackReader.addedPaintings) {
                Registry.register(Registry.MOTIVE, entry.getResLoc(), new Motive(entry.getSizeX(), entry.getSizeY()));
                LOGGER.info("Registered painting " + entry.getRefName());
            }
        } catch (ResourceLocationException e) {
            LOGGER.error("Skipping. Found Error: {}", e.getMessage());
        }
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        AutoConfig.getConfigHolder(ModConfig.class).registerSaveListener((configHolder, config) -> {
            CommonConfig.use_vanilla_only = config.use_vanilla_only;
            CommonConfig.use_selection_gui = config.use_selection_gui;
            CommonConfig.cycle_paintings = config.cycle_paintings;
            CommonConfig.show_painting_size = config.show_painting_size;
            return InteractionResult.PASS;
        });
        ServerNetwork.registerServerPackets();
        Events.events();
    }
}
