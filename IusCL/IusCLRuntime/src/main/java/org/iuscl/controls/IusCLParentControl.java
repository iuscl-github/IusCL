/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.controls;

import java.util.ArrayList;

import org.iuscl.classes.IusCLComponent;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLFont;

/* **************************************************************************************************** */
public class IusCLParentControl extends IusCLWinControl {

	/* Fields */
	private ArrayList<IusCLControl> controls = new ArrayList<IusCLControl>();

	/* **************************************************************************************************** */
	public IusCLParentControl(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		
		/* Events */
	}

	/* **************************************************************************************************** */
	@Override
	public void free() {
		
		/* free also the child controls */
		for (int index = controls.size() - 1; index >= 0; index--) {
			controls.get(index).free();
		}

		super.free();
	}

	/* **************************************************************************************************** */
	public ArrayList<IusCLControl> getControls() {
		return controls;
	}

	/* **************************************************************************************************** */
	@Override
	public void setColor(IusCLColor color) {
		super.setColor(color);
		
		parentColorChanged();
	}

	/* **************************************************************************************************** */
	@Override
	public void setParentColor(Boolean parentColor) {
		super.setParentColor(parentColor);
		
		parentColorChanged();
	}

	/* **************************************************************************************************** */
	protected void parentColorChanged() {

		for (int index = 0; index < controls.size(); index++) {
			
			IusCLControl childControl = controls.get(index);
			childControl.setParentColor(childControl.getParentColor());
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void setShowHint(Boolean showHint) {
		super.setShowHint(showHint);
		
		parentShowHintChanged();
	}

	/* **************************************************************************************************** */
	@Override
	public void setParentShowHint(Boolean parentShowHint) {
		super.setParentShowHint(parentShowHint);
		
		parentShowHintChanged();
	}

	/* **************************************************************************************************** */
	private void parentShowHintChanged() {

		for (int index = 0; index < controls.size(); index++) {
			
			IusCLControl childControl = controls.get(index);
			childControl.setParentShowHint(childControl.getParentShowHint());
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void setFont(IusCLFont font) {
		super.setFont(font);
		
		parentFontChanged();
	}

	/* **************************************************************************************************** */
	@Override
	public void setParentFont(Boolean parentFont) {
		super.setParentFont(parentFont);
		
		parentFontChanged();
	}

	/* **************************************************************************************************** */
	private void parentFontChanged() {

		for (int index = 0; index < controls.size(); index++) {
			
			IusCLControl childControl = controls.get(index);
			childControl.setParentFont(childControl.getParentFont());
		}
	}

}
