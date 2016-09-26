/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.stdctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.graphics.IusCLColor;

/* **************************************************************************************************** */
public class IusCLButton extends IusCLButtonControl {

	/* Properties */

	/* **************************************************************************************************** */
	public IusCLButton(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		
		/* TODO Related to the form */
//		defineProperty("Default", IusCLPropertyType.ptBoolean, "false");
//		defineProperty("ModalResult", IusCLPropertyType.ptEnum, "mrNone", IusCLModalResult.mrNone);
		
		/* Events */

		/* Create */
		swtButton = new Button(this.getFormSwtComposite(), SWT.PUSH);
		createWnd(swtButton);
	}

	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();
		
		this.removeProperty("Color");
		this.removeProperty("ParentColor");

		this.getProperty("Width").setDefaultValue("75");
		setWidth(75);
	}

	/* **************************************************************************************************** */
	@Override
	public void setColor(IusCLColor color) {
		
		swtButton.setBackground(null);
	}

	/* **************************************************************************************************** */
	@Override
	public void setParentColor(Boolean parentColor) {
		
		swtButton.setBackground(null);
	}

}
