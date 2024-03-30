package subaraki.paintings.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.PaintingVariantTags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import subaraki.paintings.Paintings;
import subaraki.paintings.utils.PaintingEntry;
import subaraki.paintings.utils.PaintingPackReader;

@Mod.EventBusSubscriber(modid = Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Generator {

    // Apologise if this doesn't work. I've never used Forge's data generators before, but it compiles.
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(event.includeServer(), new PaintingVariantTagsProvider(
                event.getGenerator().getPackOutput(),
                event.getLookupProvider(),
                Paintings.MODID,
                event.getExistingFileHelper()
        ) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
                for (PaintingEntry entry : PaintingPackReader.PAINTINGS)
                    this.tag(PaintingVariantTags.PLACEABLE).add(ResourceKey.create(Registries.PAINTING_VARIANT, entry.getResLoc()));
            }
        });
    }
}
