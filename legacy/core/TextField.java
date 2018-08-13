package com.mcf.davidee.guilib.core;

import com.mcf.davidee.guilib.focusable.FocusableWidget;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

/**
 * 
 * Abstract representation of a minecraft textfield.
 * This is pretty much a copy of the vanilla textfield, 
 * so it support highlighting/copying/pasting text.
 *
 */
public abstract class TextField extends FocusableWidget {

	public interface CharacterFilter {
		public String filter(String s);

		public boolean isAllowedCharacter(char c);
	}

	protected String text;
	protected int maxLength = 32;
	protected boolean focused;
	protected int cursorCounter, cursorPosition, charOffset, selectionEnd;
	protected int color;

	protected CharacterFilter filter;

	public TextField(int width, int height, CharacterFilter filter) {
		super(width, height);

		this.text = "";
		this.filter = filter;
		this.color = 0xffffff;
	}

	protected abstract int getDrawX();

	protected abstract int getDrawY();

	public abstract int getInternalWidth();

	protected abstract void drawBackground();

	@Override
	public void draw(int mx, int my) {
		drawBackground();

		int j = cursorPosition - charOffset;
		int k = selectionEnd - charOffset;
		String s = mc.fontRenderer.trimStringToWidth(text.substring(charOffset), getInternalWidth());
		boolean flag = j >= 0 && j <= s.length();
		boolean cursor = focused && this.cursorCounter / 6 % 2 == 0 && flag;
		int l = getDrawX();
		int i1 = getDrawY();
		int j1 = l;

		if (k > s.length())
			k = s.length();

		if (s.length() > 0) {
			String s1 = flag ? s.substring(0, j) : s;
			j1 = mc.fontRenderer.drawStringWithShadow(s1, l, i1, color);
		}

		boolean flag2 = this.cursorPosition < text.length() || text.length() >= maxLength;
		int k1 = j1;

		if (!flag)
			k1 = j > 0 ? l + this.width : l;
		else if (flag2) {
			k1 = j1 - 1;
			--j1;
		}
		if (s.length() > 0 && flag && j < s.length())
			mc.fontRenderer.drawStringWithShadow(s.substring(j), j1, i1, color);
		if (cursor) {
			if (flag2)
				Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + mc.fontRenderer.FONT_HEIGHT, -3092272);
			else
				mc.fontRenderer.drawStringWithShadow("_", k1, i1, color);
		}
		if (k != j) {
			int l1 = l + mc.fontRenderer.getStringWidth(s.substring(0, k));
			drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + mc.fontRenderer.FONT_HEIGHT);
		}

	}

	protected void drawCursorVertical(int startX, int startY, int endX, int endY) {
		int temp;
		if (startX < endX) {
			temp = startX;
			startX = endX;
			endX = temp;
		}
		if (startY < endY) {
			temp = startY;
			startY = endY;
			endY = temp;
		}
		
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)startX, (double)endY, 0.0D).endVertex();
        bufferbuilder.pos((double)endX, (double)endY, 0.0D).endVertex();
        bufferbuilder.pos((double)endX, (double)startY, 0.0D).endVertex();
        bufferbuilder.pos((double)startX, (double)startY, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
	}

	@Override
	public boolean click(int mx, int my) {
		return inBounds(mx, my);
	}

	@Override
	public void handleClick(int mx, int my) {
		int pos = mx - x;
		pos -= Math.abs(getInternalWidth() - width) / 2;

		String s = mc.fontRenderer.trimStringToWidth(
				text.substring(charOffset), getWidth());
		setCursorPosition(mc.fontRenderer.trimStringToWidth(s, pos).length()
				+ charOffset);
	}

	@Override
	public void update() {
		++cursorCounter;
	}

	@Override
	public void focusGained() {
		cursorCounter = 0;
		focused = true;
	}

	@Override
	public void focusLost() {
		focused = false;
	}

	public String getText() {
		return text;
	}

	public void setMaxLength(int length) {
		maxLength = length;

		if (text.length() > length)
			text = text.substring(0, length);
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getSelectedtext() {
		int start = cursorPosition < selectionEnd ? cursorPosition : selectionEnd;
		int end = cursorPosition < selectionEnd ? selectionEnd : cursorPosition;
		return text.substring(start, end);
	}

	public void setText(String str) {
		text = (str.length() > maxLength) ? str.substring(0, maxLength) : str;
		setCursorPosition(text.length());
	}

	public void moveCursorBy(int offs) {
		setCursorPosition(selectionEnd + offs);
	}

	public void writeText(String str) {
		String s1 = "";
		str = filter.filter(str);
		int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition
				: this.selectionEnd;
		int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd
				: this.cursorPosition;
		int k = maxLength - text.length() - (i - this.selectionEnd);

		if (this.text.length() > 0)
			s1 = s1 + this.text.substring(0, i);

		int l;

		if (k < str.length()) {
			s1 = s1 + str.substring(0, k);
			l = k;
		} else {
			s1 = s1 + str;
			l = str.length();
		}

		if (text.length() > 0 && j < text.length())
			s1 = s1 + this.text.substring(j);

		text = s1;
		moveCursorBy(i - this.selectionEnd + l);
	}

	public void deleteFromCursor(int amt) {
		if (text.length() > 0) {
			if (selectionEnd != cursorPosition)
				writeText("");
			else {
				boolean flag = amt < 0;
				int j = flag ? this.cursorPosition + amt : this.cursorPosition;
				int k = flag ? this.cursorPosition : this.cursorPosition + amt;
				String s = "";
				if (j >= 0)
					s = this.text.substring(0, j);
				if (k < this.text.length())
					s = s + this.text.substring(k);
				this.text = s;
				if (flag)
					moveCursorBy(amt);
			}
		}
	}

	public void setCursorPosition(int index) {
		cursorPosition = MathHelper.clamp(index, 0, text.length());
		setSelectionPos(this.cursorPosition);
	}

	public void setSelectionPos(int index) {
		index = MathHelper.clamp(index, 0, text.length());
		selectionEnd = index;

		if (charOffset > index)
			charOffset = index;

		int width = this.getInternalWidth();
		String s = mc.fontRenderer.trimStringToWidth(text.substring(charOffset), width);
		int pos = s.length() + charOffset;

		if (index == charOffset)
			charOffset -= mc.fontRenderer.trimStringToWidth(this.text, width, true).length();
		if (index > pos)
			charOffset += index - 1;
		else if (index <= charOffset)
			charOffset = index;

		charOffset = MathHelper.clamp(charOffset, 0, text.length());
	}

	public boolean keyTyped(char par1, int par2) {
		if (focused) {
			switch (par1) {
			case 1:
				setCursorPosition(text.length());
				setSelectionPos(0);
				return true;
			case 3:
				GuiScreen.setClipboardString(getSelectedtext());
				return true;
			case 22:
				writeText(GuiScreen.getClipboardString());
				return true;
			case 24:
				GuiScreen.setClipboardString(this.getSelectedtext());
				writeText("");
				return true;
			default:
				switch (par2) {
				case 14:
					deleteFromCursor(-1);
					return true;
				case 199:
					this.setSelectionPos(0);
					this.setCursorPosition(0);
					return true;
				case 203:
					if (GuiScreen.isShiftKeyDown())
						setSelectionPos(selectionEnd - 1);
					else
						moveCursorBy(-1);
					return true;
				case 205:
					if (GuiScreen.isShiftKeyDown())
						setSelectionPos(selectionEnd + 1);
					else
						moveCursorBy(1);
					return true;
				case 207:
					if (GuiScreen.isShiftKeyDown())
						setSelectionPos(text.length());
					else
						setCursorPosition(text.length());
					return true;
				case 211:
					deleteFromCursor(1);
					return true;
				default:
					if (filter.isAllowedCharacter(par1)) {
						writeText(Character.toString(par1));
						return true;
					}
					return false;
				}
			}
		}
		return false;
	}

}
