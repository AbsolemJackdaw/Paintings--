package subaraki.paintings.mod;

import subaraki.paintings.utils.IConfigHelper;

public class ConfigHelper implements IConfigHelper {
    @Override
    public boolean useVanillaOnly() {
        return ConfigData.SERVER.use_vanilla_only.get();
    }

    @Override
    public boolean useSelectionGUI() {
        return ConfigData.SERVER.use_selection_gui.get();
    }

    @Override
    public boolean getCyclePaintings() {
        return ConfigData.SERVER.cycle_paintings.get();
    }

    @Override
    public boolean showPaintingSize() {
        return ConfigData.CLIENT.show_painting_size.get();
    }
}
