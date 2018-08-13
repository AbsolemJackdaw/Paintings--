package com.mcf.davidee.guilib.basic;

import java.util.List;

import com.mcf.davidee.guilib.core.Widget;

import net.minecraft.client.Minecraft;

public class MultiTooltip extends Widget {

	protected int color, txtColor;
	private List<String> text;

	public MultiTooltip(List<String> strings) {
		super(getMaxStringWidth(strings) + 4, strings.size() * 12);

		this.text = strings;
		this.zLevel = 1.0f;
		this.color = 0xff000000;
		this.txtColor = 0xffffff;
	}

	public MultiTooltip(List<String> strings, int color, int txtColor) {
		super(getMaxStringWidth(strings) + 4, strings.size() * 12);

		this.text = strings;
		this.zLevel = 1.0f;
		this.color = color;
		this.txtColor = txtColor;
	}

	@Override
	public void draw(int mx, int my) {
		drawRect(x, y, x + width, y + height, color);

		int textY = y + 2;
		for (String line : text) {
			mc.fontRenderer.drawStringWithShadow(line, x + 2, textY, txtColor);
			textY += 11;
		}
	}

	@Override
	public boolean click(int mx, int my) {
		return false;
	}

	public void setBackgroundColor(int color) {
		this.color = color;
	}

	public void setTextColor(int color) {
		this.txtColor = color;
	}

	public static int getMaxStringWidth(List<String> strings) {
		Minecraft mc = Minecraft.getMinecraft();
		int max = 0;
		for (String s : strings) {
			int width = mc.fontRenderer.getStringWidth(s);
			if (width > max)
				max = width;
		}
		return max;
	}

}
