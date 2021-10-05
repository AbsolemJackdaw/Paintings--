package subaraki.paintings.mod;

import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import subaraki.paintings.packet.NetworkHandler;
import subaraki.paintings.util.PaintingUtility;
import subaraki.paintings.util.json.PaintingPackReader;

@Mod(Paintings.MODID)
@EventBusSubscriber(modid = Paintings.MODID, bus = Bus.MOD)
public class Paintings {

    public static final String MODID = "paintings";
    public static final Logger LOG = LogManager.getLogger(MODID);
    public static final PaintingUtility UTILITY = new PaintingUtility();

    /** call init here, to read json files before any event is launched. */
    static {
        new PaintingPackReader().init();
    }

    public Paintings() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::modConfig);
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigData.SERVER_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigData.CLIENT_SPEC);
    }

    @SubscribeEvent
    public static void registerPaintings(RegistryEvent.Register<Motive> event) {

        PaintingPackReader.registerToMinecraft(event);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        new NetworkHandler();
    }

    public void modConfig(ModConfigEvent event) {

        ModConfig config = event.getConfig();
        if (config.getSpec() == ConfigData.CLIENT_SPEC)
            ConfigData.refreshClient();
        else if (config.getSpec() == ConfigData.SERVER_SPEC)
            ConfigData.refreshServer();
    }
}
