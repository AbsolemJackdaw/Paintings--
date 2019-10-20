package subaraki.paintings.event;

import net.minecraftforge.common.MinecraftForge;

public class EventRegistry {

    public EventRegistry() {

        MinecraftForge.EVENT_BUS.register(new PlacePaintingEventHandler());
        MinecraftForge.EVENT_BUS.register(new EventFixBoundingBox());
        MinecraftForge.EVENT_BUS.register(new PaintingInteractEvent());

    }
}