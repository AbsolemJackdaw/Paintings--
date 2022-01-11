package subaraki.paintings.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.world.entity.decoration.Painting;
import subaraki.paintings.Paintings;

public class ClientEvents {

    public static void fixBoundingBoxEvent() {
        // quick hook to fix paintings not having the correct bounding box when reloading
        // a world, and thus overlapping with other newly placed paintings
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof Painting painting) {
                Paintings.UTILITY.updatePaintingBoundingBox(painting);
            }
        });
    }
}
