package Paintings;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.item.EntityPainting;

public class ClientProxy
  extends CommonProxy
{
  public void registerRenderInformation()
  {
    RenderingRegistry.registerEntityRenderingHandler(EntityPainting.class, new RenderPaintingLate());
  }
}
