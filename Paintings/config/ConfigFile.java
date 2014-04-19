package Paintings.config;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigFile
{
  public static ConfigFile instance = new ConfigFile();
  public boolean Gibea = true;
  public boolean Sphax = false;
  public boolean Insane = false;
  public boolean TinyPics = false;
  public boolean NewInsane = false;

  public void loadConfig(File file)
  {
    Configuration config = new Configuration(file);
    config.load();
    loadSettings(config);
    config.save();
  }
  
  private void loadSettings(Configuration config)
  {
    config.addCustomCategoryComment("1-PaintingsMode", "Only set one Value to True !");
    
    this.Gibea = config.get("1-PaintingsMode", "Gibea", true).getBoolean(true);
    this.Sphax = config.get("1-PaintingsMode", "Sphax", false).getBoolean(false);
    this.Insane = config.get("1-PaintingsMode", "Insane", false).getBoolean(false);
    this.TinyPics = config.get("1-PaintingsMode", "TinyPics", false).getBoolean(false);
    this.NewInsane = config.get("1-PaintingsMode", "NewInsane", false).getBoolean(false);

  }
}
