package subaraki.paintings.mod.client.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import subaraki.paintings.mod.entity.EntityNewPainting;
import subaraki.paintings.mod.entity.client.RenderPaintingLate;
import subaraki.paintings.mod.server.proxy.CommonProxy;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityNewPainting.class, RenderPaintingLate::new);
	}
}
