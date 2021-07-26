package subaraki.paintings.util;

import java.util.Comparator;

import net.minecraft.world.entity.decoration.Motive;


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

