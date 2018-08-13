package subaraki.paintings.mod.entity;

import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import subaraki.paintings.mod.Paintings;

public class EntityHandler {

	public EntityHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> e){

		e.getRegistry().register(EntityEntryBuilder.create()
				.entity(EntityNewPainting.class)
				.id(new ResourceLocation(Paintings.MODID, "paintingwrapper_entity"), 0)
				.name("paintingwrapper_entity")
				.tracker(256, 4, false)
				.build());
	}
}
