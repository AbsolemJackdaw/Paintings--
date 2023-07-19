package subaraki.paintings.utils;

import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.Comparator;


public class ArtComparator implements Comparator<PaintingVariant> {

    @Override
    public int compare(PaintingVariant a, PaintingVariant b) {
        if (a.getHeight() > b.getHeight()) return -1;
        if (a.getHeight() < b.getHeight()) return 1;
        return b.getWidth() - a.getWidth();
    }
}

