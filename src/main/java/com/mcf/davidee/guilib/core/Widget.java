package com.mcf.davidee.guilib.core;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * 
 * Widgets are the core of this library.
 * All controls should be a subclass of Widget.
 *
 */
public abstract class Widget extends Gui {

	protected Minecraft mc = Minecraft.getMinecraft();
	protected int x, y, width, height;
	protected boolean enabled;

	/**
	 * 
	 * @param width Widget of this widget
	 * @param height Height of this widget
	 */
	public Widget(int width, int height) {
		this.width = width;
		this.height = height;
		this.enabled = true;
	}
	
	/**
	 * 
	 * @param x Leftmost x of this widget
	 * @param y Topmost y of this widget
	 * @param width Widget of this widget
	 * @param height Height of this widget
	 */
	public Widget(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabled = true;
	}

	/**
	 * Draws this widget
	 * 
	 * @param mx Mouse-X
	 * @param my Mouse-Y
	 */
	public abstract void draw(int mx, int my);
	
	/**
	 * Called when the mouse is clicked.
	 * 
	 * @param mx Mouse-X
	 * @param my Mouse-Y
	 * @return Whether the control should handleClick
	 */
	public abstract boolean click(int mx, int my);

	/**
	 * Called when a call to click(mx, my) returns true.
	 * Handle the click event in this method.
	 * 
	 * @param mx Mouse-X
	 * @param my Mouse-Y
	 */
	public void handleClick(int mx, int my){ }
	
	/**
	 * Update this control (if necessary). 
	 */
	public void update(){ }
	
	/**
	 * Called when the mouse is released.
	 * 
	 * @param mx Mouse-X
	 * @param my Mouse-Y
	 */
	public void mouseReleased(int mx, int my){ }
	
	/**
	 * Called when a key is typed.
	 * 
	 * @param c Character typed (if any)
	 * @param code Keyboard.KEY_ code for this key
	 * @return Whether this widget has captured this keyboard event
	 */
	public boolean keyTyped(char c, int code) { 
		return false;
	}
	
	/**
	 * Called when the mouse wheel has moved.
	 * 
	 * @param delta Clamped difference, currently either +5 or -5
	 * @return Whether this widget has captured this mouse wheel event
	 */
	public boolean mouseWheel(int delta) { 
		return false;
	}

	/**
	 * Called when rendering to get tooltips.
	 * 
	 * @return Tooltips for this widget
	 */
	public List<Widget> getTooltips() {
		return Collections.emptyList();
	}

	/**
	 * Called to see if the specified coordinate is in bounds.
	 * 
	 * @param mx Mouse-X
	 * @param my Mouse-Y
	 * @return Whether the mouse is in the bounds of this widget
	 */
	public boolean inBounds(int mx, int my) {
		return mx >= x && my >= y && mx < x + width && my < y + height;
	}
	
	/**
	 * Called to see if this widget should render.
	 * 
	 * @param topY Top-Y of the screen
	 * @param bottomY Bottom-Y of the screen
	 * @return Whether or not this widget should be rendered
	 */
	public boolean shouldRender(int topY, int bottomY) {
		return  y + height >= topY && y <= bottomY;
	}

	/**
	 * Set the position of this widget.
	 * 
	 * @param x Left-most X
	 * @param y Top-most Y
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
