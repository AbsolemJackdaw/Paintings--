package subaraki.paintings.mod;


import net.minecraft.ResourceLocationException;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
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

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigData.SERVER_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigData.CLIENT_SPEC);
    }

    @SubscribeEvent
    public static void registerPaintigns(RegisterEvent event) {
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

    private void commonSetup(final FMLCommonSetupEvent event) {
        new NetworkHandler();
    }
}
