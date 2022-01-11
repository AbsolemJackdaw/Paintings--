package subaraki.paintings.event;

import net.minecraft.world.entity.decoration.Painting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import subaraki.paintings.Paintings;

@EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = Bus.FORGE)
public class EventFixBoundingBox {

    // quick hook to fix paintings not having the correct bounding box when reloading
    // a world, and thus overlapping with other newly placed paintings
    @SubscribeEvent
    public static void spawnEvent(EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof Painting painting) {
            Paintings.UTILITY.updatePaintingBoundingBox(painting);
        }
    }
}
