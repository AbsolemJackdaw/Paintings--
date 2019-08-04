package subaraki.paintings.mod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.paintings.config.ConfigurationHandler;

public class CPacketNotifyTemplate implements IMessage {

	public String server_template = ""; 

	public CPacketNotifyTemplate() {
	}

	public CPacketNotifyTemplate(String server_template) {
		this.server_template = server_template;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.server_template = ByteBufUtils.readUTF8String(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, server_template);

	}

	public static class CPacketNotifyTemplateHandler implements IMessageHandler<CPacketNotifyTemplate, IMessage>
	{

		@Override
		public IMessage onMessage(CPacketNotifyTemplate message, MessageContext ctx) {

			Minecraft.getMinecraft().addScheduledTask(() -> {

				processPacket(message);

			});

			return null;
		}

		private void processPacket(CPacketNotifyTemplate message) {
			String client_template = ConfigurationHandler.instance.texture;
			String server_template = message.server_template;

			
			if(!client_template.equals(server_template))
				Minecraft.getMinecraft().player.sendMessage(
						new TextComponentString(
								String.format("The Server is using %s for their paintings."
										+   "\nYou are using %s."
										+   "\nConcider adjusting your config for best results !" ,
										server_template.toUpperCase(), client_template.toUpperCase()))
						.setStyle(new Style()
								.setBold(false)
								.setColor(TextFormatting.GOLD)));			
		}

	}

}
