package subaraki.paintings.mod;

import subaraki.paintings.utils.IConfigHelper;

public class ConfigHelper implements IConfigHelper {
    @Override
    public boolean useVanillaOnly() {
        return Paintings.config.use_vanilla_only;
    }

    @Override
    public boolean useSelectionGUI() {
        return Paintings.config.use_selection_gui;
    }

    @Override
    public boolean getCyclePaintings() {
        return Paintings.config.cycle_paintings;
    }

    @Override
    public boolean showPaintingSize() {
        return Paintings.config.show_painting_size;
    }
}
