package com.mcf.davidee.paintinggui.gui;

import java.util.Comparator;

import com.mcf.davidee.paintinggui.wrapper.PaintingWrapper;


public class ArtComparator implements Comparator<PaintingWrapper> {
	
	@Override
	public int compare(PaintingWrapper a, PaintingWrapper b) {
		if (a.getY() > b.getY())
			return -1;
		if (a.getY() < b.getY())
			return 1;
		return b.getX() - a.getX();
	}
}

