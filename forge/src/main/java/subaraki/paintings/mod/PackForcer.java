package subaraki.paintings.mod;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.paintings.Paintings;
import subaraki.paintings.utils.PaintingPackReader;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PackForcer implements RepositorySource {

    private static final FileFilter RESOURCEPACK_FILTER = file ->  (file.isFile() || file.isDirectory()) && PaintingPackReader.FORCE_LOAD.contains(file.toPath());

    @SubscribeEvent
    public static void loadedPackEvent(AddPackFindersEvent event) {
        if (event.getPackType().equals(PackType.CLIENT_RESOURCES))
            event.addRepositorySource(new PackForcer());
    }

    @Override
    public void loadPacks(Consumer<Pack> packConsumer) {
        File dir = new File("./resourcepacks");
        if (dir.isDirectory()) {
            File[] fashionPacks = dir.listFiles(RESOURCEPACK_FILTER);
            if (fashionPacks != null)
                for (File directory : fashionPacks) {
                    Pack zipPack = Pack.create(
                            "file/" + directory.getName(),
                            Component.literal(directory.getName()),
                            true,
                            id -> new FilePackResources(directory.getName(), directory, false),
                            new Pack.Info(Component.empty(), 12, FeatureFlagSet.of()),
                            PackType.CLIENT_RESOURCES,
                            Pack.Position.TOP,
                            false,
                            PackSource.DEFAULT
                    );
                    Pack folderPack = Pack.create(
                            "file/" + directory.getName(),
                            Component.literal(directory.getName()),
                            true,
                            id -> new PathPackResources(directory.getName(), directory.toPath(), false),
                            new Pack.Info(Component.empty(), 12, FeatureFlagSet.of()),
                            PackType.CLIENT_RESOURCES,
                            Pack.Position.TOP,
                            false,
                            PackSource.DEFAULT
                    );

                    if (zipPack != null) {
                        packConsumer.accept(zipPack);
                    } else if (folderPack != null) {
                        packConsumer.accept(folderPack);
                    }
                }
        }
    }
}
