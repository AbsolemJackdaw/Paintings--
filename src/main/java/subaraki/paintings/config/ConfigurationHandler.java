package subaraki.paintings.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {
    public static ConfigurationHandler instance = new ConfigurationHandler();

    public String texture = "gibea";
    public String background_texture = "minecraft:blocks/planks_oak";
    public static String[] BUILTIN_PATTERNS = {"vanilla", "gibea", "sphax", "insane", "tinypics", "mediumpics", "new_insane", "massive"};

    public void loadConfig(File file) {
        Configuration config = new Configuration(file);
        config.load();
        loadSettings(config);
        config.save();
    }

    private void loadSettings(Configuration config) {


        config.addCustomCategoryComment("Painting Mode", "The layout pattern of your art texture. "
                + "\n Built-in patterns include vanilla, gibea, sphax, insane, tinypics, mediumpics, new_insane, and massive. "
                + "\n"
                + "\n Use of a custom pattern is possible by including a JSON file in the mod file under : "
                + "\n src/main/resources/assets/subaraki/patterns. Make sure to include a texture of the same name");

        this.texture = config.getString("name", "Painting Mode", BUILTIN_PATTERNS[0], "Texture").toLowerCase();

        config.addCustomCategoryComment("Painting Background", "define what texture is used as the painting background. default is oak planks. use texture localization to define texture.");

        this.background_texture = config.getString("background texture", "Painting Background", "minecraft:blocks/planks_oak", "").toLowerCase();
    }
}
