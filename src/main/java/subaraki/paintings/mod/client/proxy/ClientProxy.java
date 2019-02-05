package subaraki.paintings.mod.client.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.mod.entity.EntityNewPainting;
import subaraki.paintings.mod.entity.client.RenderPaintingLate;
import subaraki.paintings.mod.server.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityNewPainting.class, RenderPaintingLate::new);
	}

	@Override
	public JsonObject getPatternJson(String patternName) {

		// This is a client, use the good old-fashioned resource loader
		try {

			ResourceLocation loc = new ResourceLocation(Paintings.RESOURCE_DOMAIN, "patterns/" + ConfigurationHandler.instance.texture + ".json");
			InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			Gson gson = new Gson();
			JsonElement je = gson.fromJson(reader, JsonElement.class);
			return je.getAsJsonObject();

		} catch (IOException e) {
			Paintings.log.warn(e.getLocalizedMessage());
		} catch (IllegalStateException e) {
			Paintings.log.error(e.getLocalizedMessage());
		}

		return null;
	}
}