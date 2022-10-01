package subaraki.paintings.mod;

import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.paintings.Paintings;
import subaraki.paintings.utils.PaintingPackReader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PackForcer implements RepositorySource {

    private static final FileFilter RESOURCEPACK_FILTER = pack -> {
        return pack.isFile() && PaintingPackReader.FORCE_LOAD.contains(pack);// pack.isDirectory() && (new File(pack, "pack.mcmeta")).isFile() && (new File(pack, String.format("assets/%s/fashionpack.json", Fashion.MODID))).exists();
    };

    @SubscribeEvent
    public static void loadedPackEvent(AddPackFindersEvent event) {
        if (event.getPackType().equals(PackType.CLIENT_RESOURCES))
            event.addRepositorySource(new PackForcer());
    }

    @Override
    public void loadPacks(Consumer<Pack> packConsumer, Pack.PackConstructor packBuilder) {
        File dir = new File("./resourcepacks");
        if (dir.isDirectory()) {
            File[] fashionPacks = dir.listFiles(RESOURCEPACK_FILTER);
            if (fashionPacks != null)
                for (File zip : fashionPacks) {
                    Pack pack = Pack.create("file/" + zip.getName(), true, () -> new FilePackResources(zip), packBuilder, Pack.Position.TOP, PackSource.DEFAULT);

                    if (pack != null) packConsumer.accept(pack);
                }
        }
    }
}
