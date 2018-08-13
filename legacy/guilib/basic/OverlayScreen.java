package com.mcf.davidee.guilib.basic;

public abstract class OverlayScreen extends BasicScreen {

	protected BasicScreen bg;

	public OverlayScreen(BasicScreen bg) {
		super(bg);

		this.bg = bg;
	}

	@Override
	public void drawBackground() {
		bg.drawScreen(-1, -1, 0);
	}

	@Override
	protected void revalidateGui() {
		bg.width = width;
		bg.height = height;
		bg.revalidateGui();
	}

	@Override
	protected void reopenedGui() { }

}
