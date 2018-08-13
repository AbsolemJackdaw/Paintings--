package com.mcf.davidee.guilib.vanilla.sliders;

import com.mcf.davidee.guilib.core.Slider;
import com.mcf.davidee.guilib.vanilla.SliderVanilla;

import net.minecraft.util.math.MathHelper;

/**
 * 
 * A Vanilla-style Integer slider that supports the mouse wheel.
 *
 */
public class IntSlider extends SliderVanilla {
	
	protected final int minVal, maxVal;
	protected final String nameFormat;
	protected boolean hover;

	
	/**
	 * @param nameFormat Format string, used as a parameter to String.format
	 */
	public IntSlider(int width, int height, String nameFormat, int val, int minVal, int maxVal) {
		super(width, height, getFloatValue(val,minVal,maxVal), null);
		
		this.format = new IntSliderFormat();
		this.nameFormat = nameFormat;
		this.minVal = minVal;
		this.maxVal = maxVal;
	}
	
	public IntSlider(String name, int val, int minVal, int maxVal) {
		this(150, 20, name, val, minVal, maxVal);
	}
	
	@Override
	public void draw(int mx, int my){
		hover = inBounds(mx,my);
		super.draw(mx, my);
	}
	
	@Override
	public boolean mouseWheel(int delta){
		if (hover && !dragging){
			value = getFloatValue(getIntValue()+(int)Math.signum(delta),minVal,maxVal);
			return true;
		}
		return false;
	}
	
	public static float getFloatValue(int val, int min, int max){
		val = MathHelper.clamp(val, min, max);
		return (float)(val-min) / (max-min);
	}
	
	public void setIntValue(int val) {
		value = MathHelper.clamp(getFloatValue(val, minVal, maxVal), 0, 1);
	}
	
	public int getIntValue(){
		return Math.round(value*(maxVal-minVal)+minVal);
	}
	
	protected class IntSliderFormat implements SliderFormat{
		public String format(Slider slider) {
			return String.format(nameFormat, getIntValue());
		}
		
	}
	
}
