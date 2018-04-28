package subaraki.paintings.mod.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import subaraki.paintings.entity.EntityPaintingNew;
import subaraki.paintings.mod.client.RenderPaintingNew;

public class ClientProxy extends CommonProxy
{
	public void registerRenderInformation()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityPaintingNew.class, RenderPaintingNew::new);
	}
}
