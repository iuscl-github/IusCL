/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.controls;

import org.iuscl.classes.IusCLComponent;

/* **************************************************************************************************** */
public class IusCLListControl extends IusCLWinControl {

	/* Properties */
	private IusCLBorderStyle borderStyle = IusCLBorderStyle.bsSingle;
	protected Integer itemIndex = -1;

	/* **************************************************************************************************** */
	public IusCLListControl(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("BorderStyle", IusCLPropertyType.ptEnum, "bsSingle", IusCLBorderStyle.bsSingle);
		
		removeProperty("Caption");

		/* Events */
	}

	public IusCLBorderStyle getBorderStyle() {
		return borderStyle;
	}

	/* **************************************************************************************************** */
	public void setBorderStyle(IusCLBorderStyle borderStyle) {
		
		if (this.borderStyle != borderStyle) {
			
			this.borderStyle = borderStyle;
			
			reCreateWnd();		
		}
	}

	public Integer getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}

}
