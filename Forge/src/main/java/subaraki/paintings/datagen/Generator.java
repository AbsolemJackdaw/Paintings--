package subaraki.paintings.datagen;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.paintings.Paintings;
import subaraki.paintings.utils.PaintingEntry;
import subaraki.paintings.utils.PaintingPackReader;

@Mod.EventBusSubscriber(modid = Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Generator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(event.includeServer(), new PaintingVariantTagsProvider(generator, Paintings.MODID, event.getExistingFileHelper()) {
            @Override
            protected void addTags() {
                for (PaintingEntry entry : PaintingPackReader.PAINTINGS)
                    this.tag(PaintingVariantTags.PLACEABLE).add(ResourceKey.create(Registry.PAINTING_VARIANT_REGISTRY, entry.getResLoc()));
            }
        });
    }
}
