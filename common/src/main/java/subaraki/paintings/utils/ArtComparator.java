package subaraki.paintings.utils;


import net.minecraft.world.entity.decoration.painting.PaintingVariant;

import java.util.Comparator;


public class ArtComparator implements Comparator<PaintingVariant> {

    @Override
    public int compare(PaintingVariant a, PaintingVariant b) {
        if (a.height() > b.height()) return -1;
        if (a.height() < b.height()) return 1;
        return b.width() - a.width();
    }
}

