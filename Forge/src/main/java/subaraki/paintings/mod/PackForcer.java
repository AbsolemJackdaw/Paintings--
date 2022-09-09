package subaraki.paintings.mod;

import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
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
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PackForcer implements RepositorySource {

    private static final FileFilter RESOURCEPACK_FILTER = (file) -> {
        return (file.isFile() || file.isDirectory()) && PaintingPackReader.FORCE_LOAD.contains(file.toPath());// pack.isDirectory() && (new File(pack, "pack.mcmeta")).isFile() && (new File(pack, String.format("assets/%s/fashionpack.json", Fashion.MODID))).exists();
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
                for (File directory : fashionPacks) {
                    Pack zipPack = Pack.create("file/" + directory.getName(), true, () -> new FilePackResources(directory), packBuilder, Pack.Position.TOP, PackSource.DEFAULT);
                    Pack folderPack = Pack.create("file/" + directory.getName(), true, () -> new FolderPackResources(directory), packBuilder, Pack.Position.TOP, PackSource.DEFAULT);

                    if (zipPack != null) {
                        packConsumer.accept(zipPack);
                    } else if (folderPack != null) {
                        packConsumer.accept(folderPack);
                    }
                }
        }
    }
}
