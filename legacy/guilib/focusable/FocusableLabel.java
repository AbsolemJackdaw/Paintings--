package com.mcf.davidee.guilib.focusable;

import java.util.ArrayList;
import java.util.List;

import com.mcf.davidee.guilib.core.Scrollbar.Shiftable;
import com.mcf.davidee.guilib.core.Widget;

import net.minecraft.client.Minecraft;

/**
 * 
 * A simple focusable label.
 *
 */
public class FocusableLabel extends FocusableWidget implements Shiftable {

	private String str;
	private int color, hoverColor, focusColor;
	private List<Widget> tooltips;
	private boolean hover, center, focused;
	private Object userData;
	
	public FocusableLabel(String text, int color, int hoverColor, int focusColor, Widget... tooltips) {
		this(text, color, hoverColor, focusColor, true, tooltips);
	}
	
	public FocusableLabel(String text, Widget... tooltips) {
		this(text,0xffffff,16777120, 0x22aaff, true, tooltips);
	}
	
	public FocusableLabel(String text, int color, int hoverColor, int focusColor, boolean center, Widget... tooltips) {
		super(getStringWidth(text), 11);
	
		this.center = center;
		this.str = text;
		this.color = color;
		this.hoverColor = hoverColor;
		this.focusColor = focusColor;
		this.tooltips = new ArrayList<Widget>();
		for (Widget w : tooltips)
			this.tooltips.add(w);
	}
	
	public FocusableLabel(String text, boolean center, Widget... tooltips) {
		this(text, 0xffffff, 16777120, 0x22aaff, center, tooltips);
	}
	
	public FocusableLabel(int x, int y, String text, Widget... tooltips) {
		this(text, 0xffffff, 16777120, 0x22aaff, true, tooltips);
		
		setPosition(x, y);
	}
	
	public void setColors(int color, int hoverColor, int focusColor) {
		this.color = color;
		this.hoverColor = hoverColor;
		this.focusColor = focusColor;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public void setHoverColor(int hoverColor) {
		this.hoverColor = hoverColor;
	}
	
	public void setFocusColor(int focusColor) {
		this.focusColor = focusColor;
	}
	
	public String getText() {
		return str;
	}
	
	public void setText(String text) {
		if (center)
			this.x += width/2;
		this.str = text;
		width = getStringWidth(text);
		if (center)
			this.x -= width/2;
	}
	
	public void setUserData(Object data) {
		userData = data;
	}
	
	public Object getUserData() {
		return userData;
	}

	@Override
	public void draw(int mx, int my) {
		boolean newHover = inBounds(mx,my);
		if (newHover && !hover) {
			for (Widget w : tooltips)
				w.setPosition(mx+3, y + height);
		}
		hover = newHover;
		if (focused)
			drawRect(x, y, x+width, y+height, 0x99999999);
		mc.fontRenderer.drawStringWithShadow(str, x, y + 2, (focused) ? focusColor : (hover) ? hoverColor : color);
	}
	
	@Override
	public List<Widget> getTooltips() {
		return (hover) ? tooltips : super.getTooltips();
	}

	@Override
	public boolean click(int mx, int my) {
		return inBounds(mx,my);
	}
	
	private static int getStringWidth(String text) {
		return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
	}
	
	@Override
	public void setPosition(int x, int y) {
		this.x = (center) ? x - width/2 : x;
		this.y = y;
	}

	@Override
	public void shiftY(int dy) {
		this.y += dy;
	}

	@Override
	public void focusGained() {
		focused = true;
	}

	@Override
	public void focusLost() {
		focused = false;
	}

}
