package Paintings;

import net.minecraft.entity.item.EntityPainting;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy
extends CommonProxy
{
	public void registerRenderInformation()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityPainting.class, RenderPaintingLate::new);
	}
}
