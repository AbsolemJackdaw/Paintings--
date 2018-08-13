package com.mcf.davidee.guilib.vanilla;

import com.mcf.davidee.guilib.core.TextField;

import net.minecraft.util.ChatAllowedCharacters;

/**
 * 
 * Vanilla GuiTextField in Widget form.
 *
 */
public class TextFieldVanilla extends TextField {

	private int outerColor, innerColor;

	public TextFieldVanilla(int width, int height, CharacterFilter filter) {
		super(width, height, filter);

		outerColor = -6250336;
		innerColor = -16777216;
	}

	public TextFieldVanilla(CharacterFilter filter) {
		this(200, 20, filter);
	}
	
	public TextFieldVanilla(int width, int height, int innerColor, int outerColor, CharacterFilter filter) {
		super(width, height, filter);
		
		this.outerColor = outerColor;
		this.innerColor = innerColor;
	}
	
	public void setInnerColor(int c) {
		this.innerColor = c;
	}

	public void setOuterColor(int c) {
		this.outerColor = c;
	}

	@Override
	protected int getDrawX() {
		return x + 4;
	}

	@Override
	protected int getDrawY() {
		return y + (height - 8) / 2;
	}

	@Override
	public int getInternalWidth() {
		return width - 8;
	}

	@Override
	protected void drawBackground() {
		drawRect(x - 1, y - 1, x + width + 1, y + height + 1, outerColor);
		drawRect(x, y, x + width, y + height, innerColor);
	}

	public static class NumberFilter implements CharacterFilter {
		public String filter(String s) {
			StringBuilder sb = new StringBuilder();
			for (char c : s.toCharArray())
				if (isAllowedCharacter(c))
					sb.append(c);
			return sb.toString();
		}

		public boolean isAllowedCharacter(char c) {
			return Character.isDigit(c);
		}

	}

	public static class VanillaFilter implements CharacterFilter {
		public String filter(String s) {
			return ChatAllowedCharacters.filterAllowedCharacters(s);
		}

		public boolean isAllowedCharacter(char c) {
			return ChatAllowedCharacters.isAllowedCharacter(c);
		}

	}

}
