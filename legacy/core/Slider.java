package com.mcf.davidee.guilib.core;

import net.minecraft.util.math.MathHelper;

/**
 * 
 * Abstract representation of a minecraft slider.
 *
 */
public abstract class Slider extends Widget {

	public interface SliderFormat {
		String format(Slider slider);
	}

	protected SliderFormat format;
	protected float value;
	protected boolean dragging;

	public Slider(int width, int height, float value, SliderFormat format) {
		super(width, height);

		this.value = MathHelper.clamp(value, 0, 1);
		this.format = format;
	}

	@Override
	public boolean click(int mx, int my) {
		if (inBounds(mx, my)) {
			value = (float) (mx - (this.x + 4)) / (float) (this.width - 8);
			value = MathHelper.clamp(value, 0, 1);
			dragging = true;
			return true;
		}
		return false;
	}
	
	@Override
	public void handleClick(int mx, int my) {
		value = (float) (mx - (this.x + 4)) / (float) (this.width - 8);
		value = MathHelper.clamp(value, 0, 1);
		dragging = true;
	}

	@Override
	public void mouseReleased(int mx, int my) {
		dragging = false;
	}

	public float getValue() {
		return value;
	}

}
