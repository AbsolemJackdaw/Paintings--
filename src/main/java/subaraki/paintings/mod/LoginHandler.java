package subaraki.paintings.mod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.network.CPacketNotifyTemplate;
import subaraki.paintings.mod.network.NetworkHandler;

public class LoginHandler {

	public LoginHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void PlayerLoggedIn(PlayerLoggedInEvent evt)
	{
		if(evt.player != null)
		{
			EntityPlayer player  = evt.player;

			if(player instanceof EntityPlayerMP)
			{
				NetworkHandler.NETWORK.sendTo(new CPacketNotifyTemplate(ConfigurationHandler.instance.texture), (EntityPlayerMP)player );
			}
		}
	}
}
