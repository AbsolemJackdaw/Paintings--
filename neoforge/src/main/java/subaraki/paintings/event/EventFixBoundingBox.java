package subaraki.paintings.event;

import net.minecraft.world.entity.decoration.painting.Painting;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import subaraki.paintings.Paintings;

@EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID)
public class EventFixBoundingBox {

    // quick hook to fix paintings not having the correct bounding box when reloading
    // a world, and thus overlapping with other newly placed paintings
    @SubscribeEvent
    public static void spawnEvent(EntityJoinLevelEvent event) {

        if (event.getEntity() instanceof Painting painting) {
            Paintings.UTILITY.updatePaintingBoundingBox(painting);
        }
    }
}
