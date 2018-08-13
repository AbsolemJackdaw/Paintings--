package com.mcf.davidee.guilib.core;

/**
 * 
 * Abstract representation of a checkbox.
 *
 */
public abstract class Checkbox extends Widget {

	protected String str;
	protected boolean check;

	public Checkbox(int width, int height, String text) {
		super(width, height);

		this.str = text;
	}

	public Checkbox(int width, int height, String text, boolean checked) {
		this(width, height, text);

		this.check = checked;
	}

	@Override
	public boolean click(int mx, int my) {
		return inBounds(mx, my);
	}

	@Override
	public void handleClick(int mx, int my) {
		check = !check;
	}

	public boolean isChecked() {
		return check;
	}
	
	public void setChecked(boolean checked) {
		this.check = checked;
	}

}
