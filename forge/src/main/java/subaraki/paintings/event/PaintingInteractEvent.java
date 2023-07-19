package subaraki.paintings.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.paintings.network.NetworkHandler;
import subaraki.paintings.network.client.CPacketPainting;

@EventBusSubscriber(modid = subaraki.paintings.Paintings.MODID, bus = Bus.FORGE)
public class PaintingInteractEvent {

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.EntityInteract event) {
        ProcessInteractEvent.processInteractPainting(event.getEntity(), event.getTarget(), event.getHand(),
                (painting, player) -> NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with((() -> player)),
                        new CPacketPainting(painting, new ResourceLocation[]{ForgeRegistries.PAINTING_VARIANTS.getKey(painting.getVariant().get())})));
    }
}
