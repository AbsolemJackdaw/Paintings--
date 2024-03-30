package subaraki.paintings.event;

import net.minecraft.world.entity.decoration.Painting;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import subaraki.paintings.Paintings;

@Mod.EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
