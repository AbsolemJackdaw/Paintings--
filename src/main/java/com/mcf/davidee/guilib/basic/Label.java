package com.mcf.davidee.guilib.basic;

import java.util.ArrayList;
import java.util.List;

import com.mcf.davidee.guilib.core.Widget;

import net.minecraft.client.Minecraft;

public class Label extends Widget {

	private String str;
	private int color, hoverColor;

	private List<Widget> tooltips;
	private boolean hover, center;
	private boolean shadow;
	private long hoverStart;

	public Label(String text, int color, int hoverColor, Widget... tooltips) {
		this(text, color, hoverColor, true, tooltips);
	}

	public Label(String text, Widget... tooltips) {
		this(text, 0xffffff, 0xffffff, true, tooltips);
	}

	public Label(String text, int color, int hoverColor, boolean center, Widget... tooltips) {
		super(getStringWidth(text), 11);

		this.center = center;
		this.str = text;
		this.color = color;
		this.hoverColor = hoverColor;
		this.shadow = true;
		this.tooltips = new ArrayList<Widget>();
		for (Widget w : tooltips)
			this.tooltips.add(w);
	}

	public Label(String text, boolean center, Widget... tooltips) {
		this(text, 0xffffff, 0xffffff, center, tooltips);
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setHoverColor(int hoverColor) {
		this.hoverColor = hoverColor;
	}

	public void setShadowedText(boolean useShadow) {
		this.shadow = useShadow;
	}

	public String getText() {
		return str;
	}

	public void setText(String text) {
		//Find the center
		if (center)
			this.x += width / 2;
		this.str = text;
		width = getStringWidth(text);
		if (center)
			this.x -= width / 2;
	}

	@Override
	public void draw(int mx, int my) {
		boolean newHover = inBounds(mx, my);

		if (newHover && !hover) {
			hoverStart = System.currentTimeMillis();
			//Label is designed for a single tooltip
			for (Widget w : tooltips)
				w.setPosition(mx + 3, y + height);
		}
		hover = newHover;
		
		if (shadow)
			mc.fontRenderer.drawStringWithShadow(str, x, y + 2, (hover) ? hoverColor : color);
		else
			mc.fontRenderer.drawString(str, x, y + 2, (hover) ? hoverColor : color);
	}

	@Override
	public List<Widget> getTooltips() {
		return (hover && System.currentTimeMillis() - hoverStart >= 500) ? tooltips : super.getTooltips();
	}

	@Override
	public boolean click(int mx, int my) {
		return false;
	}

	private static int getStringWidth(String text) {
		return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = (center) ? x - width / 2 : x;
		this.y = y;
	}

}
