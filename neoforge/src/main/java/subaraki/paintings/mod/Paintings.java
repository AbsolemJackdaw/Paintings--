package subaraki.paintings.mod;


import net.minecraft.ResourceLocationException;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.RegisterEvent;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.utils.PaintingEntry;
import subaraki.paintings.utils.PaintingPackReader;

import static subaraki.paintings.Paintings.LOGGER;

@Mod(subaraki.paintings.Paintings.MODID)
@Mod.EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Paintings {

    static {
        //call init here, to read json files before any event is launched.
        new PaintingPackReader().init();
    }

    public Paintings() {
        NetworkHandler.registerPackets();
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigData.SERVER_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigData.CLIENT_SPEC);
    }

    @SubscribeEvent
    public static void registerPaintings(RegisterEvent event) {
        event.register(Registries.PAINTING_VARIANT, registry -> {
            for (PaintingEntry entry : PaintingPackReader.PAINTINGS) {
                try {
                    registry.register(entry.getResLoc(), new PaintingVariant(entry.getSizeX(), entry.getSizeY()));
                    LOGGER.info("Registered variant " + entry.getPaintingName());
                } catch (ResourceLocationException e) {
                    LOGGER.error("Skipping. Found Error: {}", e.getMessage());
                }
            }
        });
    }
}
