package subaraki.paintings.config;

import java.io.File;

import com.typesafe.config.ConfigIncluderFile;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;

public class ConfigurationHandler
{
	public static ConfigurationHandler instance = new ConfigurationHandler();

	public String texture = "gibea";

	public void loadConfig(File file)
	{
		Configuration config = new Configuration(file);
		config.load();
		loadSettings(config);
		config.save();
	}

	private void loadSettings(Configuration config)
	{
		config.addCustomCategoryComment("Painting Mode", "gibea, sphax, insane, tinypics or new_insane");

		String paintingTexture = config.getString("name", "Painting Mode", "gibea", "Texture").toLowerCase();

		switch (paintingTexture) {
		case "gibea": 
		case "sphax": 
		case "insane":
		case "tinypics":
		case "new_insane":
			texture = paintingTexture;
			break;

		default:
			FMLLog.bigWarning("The Value '"+paintingTexture+"' was not a valid String. Resorting to default");
			texture = "gibea";
			break;
		}
	}
}
