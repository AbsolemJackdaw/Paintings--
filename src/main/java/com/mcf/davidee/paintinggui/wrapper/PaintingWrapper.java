package com.mcf.davidee.paintinggui.wrapper;

import java.util.HashMap;

public class PaintingWrapper {

	public static HashMap<String, PaintingWrapper> PAINTINGS = new HashMap<String, PaintingWrapper>();


	public PaintingWrapper append() {
		PAINTINGS.put(this.getTitle(), this);
		return this;
	}

	public static final PaintingWrapper DEFAULT = new PaintingWrapper("default", 16, 16, 0, 0).append();

	private int x,y,u,v;
	private String title = "";


	public PaintingWrapper(String name, int x, int y , int u, int v) {
		this.x=x;
		this.y=y;
		this.u=u;
		this.v=v;
		this.title = name;
	}

	public String getTitle() {
		return title;
	}

	/**offset x*/
	public int getU() {
		return u;
	}
	/**offset y*/
	public int getV() {
		return v;
	}
	/**size x*/
	public int getX() {
		return x;
	}
	/**size y*/
	public int getY() {
		return y;
	}
}
