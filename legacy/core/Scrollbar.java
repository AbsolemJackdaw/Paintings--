package com.mcf.davidee.guilib.core;

import org.lwjgl.input.Mouse;

/**
 * 
 * Abstract representation of a scrollbar.
 *
 */
public abstract class Scrollbar extends Widget {

	public interface Shiftable {
		void shiftY(int dy);
	}

	protected int yClick;
	protected Container container;

	private int topY, bottomY;
	private int offset;

	public Scrollbar(int width) {
		super(width, 0);

		yClick = -1;
	}

	protected abstract void shiftChildren(int dy);

	protected abstract void drawBoundary(int x, int y, int width, int height);

	protected abstract void drawScrollbar(int x, int y, int width, int height);

	public void revalidate(int topY, int bottomY) {
		this.topY = topY;
		this.bottomY = bottomY;
		this.height = bottomY - topY;
		int heightDiff = getHeightDifference();
		if (offset != 0 && heightDiff <= 0)
			offset = 0;
		if (heightDiff > 0 && offset < -heightDiff)
			offset = -heightDiff;
		if (offset != 0)
			shiftChildren(offset);
	}

	public void onChildRemoved() {
		int heightDiff = getHeightDifference();
		if (offset != 0) {
			if (heightDiff <= 0) {
				shiftChildren(-offset);
				offset = 0;
			} else if (offset < -heightDiff) {
				shiftChildren(-heightDiff - offset);
				offset = -heightDiff;
			}
		}
	}

	public void setContainer(Container c) {
		this.container = c;
	}

	protected int getHeightDifference() {
		return container.getContentHeight() - (bottomY - topY);
	}
	
	protected int getLength() {
		if (container.getContentHeight() == 0)
			return 0;
		int length = (bottomY - topY) * (bottomY - topY) / container.getContentHeight();
		if (length < 32)
			length = 32;
		if (length > bottomY - topY - 8) // Prevent it from getting too big
			length = bottomY - topY - 8;
		return length;
	}
	

	@Override
	public void draw(int mx, int my) {
		int length = getLength();
		
		if (Mouse.isButtonDown(0)) {
			if (yClick == -1) {
				if (inBounds(mx, my)) {
					yClick = my;
				}
			} else {
				float scrollMultiplier = 1.0F;
				int diff = getHeightDifference();

				if (diff < 1)
					diff = 1;
				
				scrollMultiplier /= (bottomY - topY - length) / (float)diff;
				shift((int) ((yClick - my) * scrollMultiplier));
				yClick = my;
			}
		} else
			yClick = -1;
		
		drawBoundary(x, topY, width, height);

		int y = -offset * (bottomY - topY - length) / getHeightDifference() + (topY);
		if (y < topY)
			y = topY;

		drawScrollbar(x, y, width, length);
	}

	@Override
	public boolean click(int mx, int my) {
		return false;
	}

	@Override
	public boolean shouldRender(int topY, int bottomY) {
		return getHeightDifference() > 0;
	}

	/**
	 * Shifts this scrollbar relative to its size + contentHeight.
	 * 
	 * @param i Base pixels to shift.
	 */
	public void shiftRelative(int i) {
		int heightDiff = getHeightDifference();
		if (heightDiff > 0) {
			i *= 1 + heightDiff/(float)(bottomY-topY);
			//shift(i) inlined
			int dif = offset + i;
			if (dif > 0)
				dif = 0;
			if (dif < -heightDiff)
				dif = -heightDiff;
			int result = dif - offset;
			if (result != 0)
				shiftChildren(result);
			offset = dif;
		}
	}

	/**
	 * Shifts the scrollbar by i pixels.
	 * 
	 * @param i How many pixels to shift the scrollbar.
	 */
	public void shift(int i) {
		int heightDiff = getHeightDifference();
		if (heightDiff > 0) {
			int dif = offset + i;
			if (dif > 0)
				dif = 0;
			if (dif < -heightDiff)
				dif = -heightDiff;
			int result = dif - offset;
			if (result != 0)
				shiftChildren(result);
			offset = dif;
		}
	}

}
