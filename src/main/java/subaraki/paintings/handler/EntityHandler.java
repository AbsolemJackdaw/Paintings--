package subaraki.paintings.handler;

import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import subaraki.paintings.entity.EntityPaintingNew;
import subaraki.paintings.mod.MorePaintings;

public class EntityHandler {

	public EntityHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		init();
	}

	private void init()
	{
		EntityRegistry.instance().registerModEntity(new ResourceLocation(MorePaintings.MODID,"painting_more"), EntityPaintingNew.class, "paintingmore", 1, MorePaintings.INSTANCE, 256, 20, false);
	}

	@SubscribeEvent
	public void interact(PlayerInteractEvent.RightClickBlock event) {
		if(event.getEntityPlayer().getHeldItem(event.getHand()).getItem().equals(Items.PAINTING))
		{

			if (event.getFace() != EnumFacing.DOWN && event.getFace() != EnumFacing.UP && event.getEntityPlayer().canPlayerEdit(event.getPos(), event.getFace(), event.getItemStack()))
			{
				EntityPaintingNew paintingNew = new EntityPaintingNew(event.getWorld(), event.getPos().offset(event.getFace()), event.getFace());

				if (paintingNew != null && paintingNew.onValidSurface())
				{
					if (!event.getWorld().isRemote)
					{
						event.getWorld().spawnEntity(paintingNew);
					}
				}
				event.setUseItem(Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public void spawnpainting(EntityJoinWorldEvent event)
	{
		//		if(event.getEntity() instanceof EntityPainting)
		//		{
		//			EntityPainting painting = (EntityPainting)event.getEntity();
		//			System.out.println(painting.getPosition());
		//			EntityPaintingNew paintingNew = new EntityPaintingNew(painting.world, painting.getPosition(), painting.facingDirection);
		//
		//			painting.setDead();
		//
		//			if (paintingNew != null && paintingNew.onValidSurface())
		//				if (!event.getWorld().isRemote)
		//				{
		//					event.getWorld().spawnEntity(paintingNew);
		//				}
		//		}
	}
}	