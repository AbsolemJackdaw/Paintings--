package subaraki.paintings.util;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "paintings")
public class ModConfig implements ConfigData {
    public boolean use_vanilla_only = false;
    public boolean use_selection_gui = true;
    public boolean cycle_paintings = false;
    public boolean show_painting_size = true;
}
