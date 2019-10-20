package subaraki.paintings.util;

import java.util.Comparator;

import net.minecraft.entity.item.PaintingType;


public class ArtComparator implements Comparator<PaintingType> {
	
	@Override
	public int compare(PaintingType a, PaintingType b) {
		if (a.getHeight() > b.getHeight())
			return -1;
		if (a.getHeight() < b.getHeight())
			return 1;
		return b.getWidth() - a.getWidth();
	}
}

