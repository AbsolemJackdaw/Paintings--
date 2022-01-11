package subaraki.paintings.util;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "paintings")
public class ModConfig implements ConfigData {

    public boolean use_vanilla_only = false;
    public boolean use_selection_gui = true;
    public boolean cycle_paintings = false;
    public boolean show_painting_size = true;

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
        CommonConfig.use_vanilla_only = use_vanilla_only;
        CommonConfig.use_selection_gui = use_selection_gui;
        CommonConfig.cycle_paintings = cycle_paintings;
        CommonConfig.show_painting_size = show_painting_size;
    }
}
