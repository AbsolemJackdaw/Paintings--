package com.mcf.davidee.guilib.basic;

import com.mcf.davidee.guilib.core.Container;
import com.mcf.davidee.guilib.core.Scrollbar;
import com.mcf.davidee.guilib.core.Widget;
import com.mcf.davidee.guilib.focusable.FocusableWidget;

/**
 * 
 * A "Focused" version of a Container.
 * This container will always have a focused widget
 * as long as there is a focusable widget contained.
 *
 */
public class FocusedContainer extends Container {

	public FocusedContainer() {
		super();
	}

	public FocusedContainer(Scrollbar scrollbar, int shiftAmount, int extraScrollHeight) {
		super(scrollbar, shiftAmount, extraScrollHeight);
	}

	@Override
	public void setFocused(FocusableWidget f) {
		if (f != null)
			super.setFocused(f);
	}

	@Override
	public void addWidgets(Widget... arr) {
		super.addWidgets(arr);

		if (focusIndex == -1 && focusList.size() > 0) {
			focusIndex = 0;
			focusList.get(focusIndex).focusGained();
		}
	}
}
