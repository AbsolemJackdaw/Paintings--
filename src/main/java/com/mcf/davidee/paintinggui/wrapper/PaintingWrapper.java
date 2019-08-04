package com.mcf.davidee.paintinggui.wrapper;

import java.util.HashMap;

public class PaintingWrapper {

	public static HashMap<String, PaintingWrapper> PAINTINGS = new HashMap<String, PaintingWrapper>();


	public PaintingWrapper append() {
		PAINTINGS.put(this.getTitle(), this);
		return this;
	}

//	public static final PaintingWrapper DEFAULT = new PaintingWrapper("default", 16, 16, 0, 0).append();

	private int x,y,u,v;
	private String title = "";

	public static PaintingWrapper createDefault(String artName) {
		
		if(artName.isEmpty() || artName.equals("Default"))
		{
			return new PaintingWrapper("Default", 16, 16, 0, 0).append();
		}
		
		String parsName = artName.substring(3);
		String parseSizeX[] = parsName.split("x");
		int x = Integer.parseInt(parseSizeX[0]);
		String parseSizeY[] = parseSizeX[1].split("#");
		int y = Integer.parseInt(parseSizeY[0]);
		
		//same names get replaced. adding x and y adds a unique identifier for each type of painting
		return new PaintingWrapper("errored"+artName, x*16, y*16, 0, 0).append();
	}

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
