package subaraki.paintings.util;

import net.minecraft.world.entity.decoration.Motive;

import java.util.Comparator;


public class ArtComparator implements Comparator<Motive> {
	
	@Override
	public int compare(Motive a, Motive b) {
		if (a.getHeight() > b.getHeight())
			return -1;
		if (a.getHeight() < b.getHeight())
			return 1;
		return b.getWidth() - a.getWidth();
	}
}

