package subaraki.paintings.event;

import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = Bus.FORGE)
public class PaintingInteractEvent {

    @SubscribeEvent
    public static void interact(EntityInteractSpecific event) {
        ProcessInteractEvent.processInteractPainting(event.getPlayer(), event.getTarget(), event.getHand());
    }
}
