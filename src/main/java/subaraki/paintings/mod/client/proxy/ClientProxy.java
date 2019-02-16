package subaraki.paintings.mod.client.proxy;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.mcf.davidee.paintinggui.gui.PaintingButton;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.IPaintingsProxy;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.mod.PaintingsPattern;
import subaraki.paintings.mod.entity.EntityNewPainting;
import subaraki.paintings.mod.entity.client.RenderPaintingLate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientProxy implements IPaintingsProxy {

	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityNewPainting.class, RenderPaintingLate::new);
	}

	public JsonObject getPatternFile(String patternName) {

		// This is a client, use the good old-fashioned resource loader
		try {
			return this.loadJsonResource(new ResourceLocation(Paintings.RESOURCE_DOMAIN, "patterns/" + ConfigurationHandler.instance.texture + ".json"));
		} catch (Exception e) {
			Paintings.log.error("Failed to read " + patternName + ".json. Make sure the pattern exists in an imported resource pack and is correctly identified in the configuration.\nSystem Message:\n" + e.getLocalizedMessage());
		}

		try {
			Paintings.log.info("Attempting to load vanilla pattern.");
			ConfigurationHandler.instance.texture = "vanilla";
			return this.loadJsonResource(new ResourceLocation(Paintings.RESOURCE_DOMAIN, "patterns/vanilla.json"));
		} catch (Exception e) {
			Paintings.log.fatal("Failed to load vanilla pattern.\n" + e.getLocalizedMessage());
		}

		return null;
	}

	private JsonObject loadJsonResource(ResourceLocation loc) throws Exception {
		InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		Gson gson = new Gson();
		JsonElement je = gson.fromJson(reader, JsonElement.class);
		return je.getAsJsonObject();
	}

	public void configurePaintingsGuiButtonTexture() {

		if (ConfigurationHandler.instance.texture.equals("vanilla")) {
			PaintingsPattern.instance.texture = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
		} else {
			PaintingsPattern.instance.texture = new ResourceLocation(Paintings.RESOURCE_DOMAIN, "art/" + ConfigurationHandler.instance.texture + ".png");
		}
		PaintingButton.KZ_WIDTH = PaintingsPattern.instance.getSize().width * 16;
		PaintingButton.KZ_HEIGHT = PaintingsPattern.instance.getSize().height * 16;

	}
}