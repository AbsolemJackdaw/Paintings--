package subaraki.paintings.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {
    public static ConfigurationHandler instance = new ConfigurationHandler();

    public String texture = "gibea";
    public String background_texture = "minecraft:blocks/planks_oak";
    
    public void loadConfig(File file) {
        Configuration config = new Configuration(file);
        config.load();
        loadSettings(config);
        config.save();
    }

    private void loadSettings(Configuration config) {
        config.addCustomCategoryComment("Painting Mode", "The layout pattern of your art texture. Already available are : \n extended, gibea, insane, new_insane, massive, mediumpics, sphax, tinypics. \nYou can also include your own json and texture and use that name to load your own custom pattern");

        this.texture = config.getString("name", "Painting Mode", "gibea", "Texture").toLowerCase();
        
        config.addCustomCategoryComment("Painting Background", "Define what texture is used as the painting background. \n default is oak planks.");
        
        this.background_texture = config.getString("background texture", "Painting Background", "minecraft:blocks/planks_oak", "").toLowerCase();
    }
}
