package subaraki.paintings.mod.client.proxy;

import net.minecraft.entity.item.EntityPainting;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import subaraki.paintings.mod.client.RenderPaintingLate;
import subaraki.paintings.mod.server.proxy.CommonProxy;

public class ClientProxy
extends CommonProxy
{
	public void registerRenderInformation()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityPainting.class, RenderPaintingLate::new);
	}
}
