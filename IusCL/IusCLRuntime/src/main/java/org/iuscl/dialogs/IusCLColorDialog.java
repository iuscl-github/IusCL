/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.dialogs;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Dialog;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLColor.IusCLStandardColors;

/* **************************************************************************************************** */
public class IusCLColorDialog extends IusCLCommonDialog {

	/* SWT */
	private ColorDialog swtColorDialog = null; 

	/* Properties */
	private String title = "";
	private IusCLColor color = IusCLColor.getStandardColor(IusCLStandardColors.clBlack);

	/* **************************************************************************************************** */
	public IusCLColorDialog(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Title", IusCLPropertyType.ptString, "Color");
		defineProperty("Color", IusCLPropertyType.ptColor, "clBlack", IusCLStandardColors.clBlack);
		
		/* Events */
		
		/* Create */
		swtColorDialog = new ColorDialog(this.findForm().getSwtShell());
	}
	
	/* **************************************************************************************************** */
	@Override
	public Boolean execute() {

		RGB returnRGB = swtColorDialog.open();
		
		if (returnRGB != null) {
			
			this.color = new IusCLColor(returnRGB.red, returnRGB.green, returnRGB.blue);
			
			return true;
		}
		
		return false;
	}
	
	/* **************************************************************************************************** */
	@Override
	public Dialog getSwtDialog() {
		
		return swtColorDialog;
	}

	public String getTitle() {
		return title;
	}

	/* **************************************************************************************************** */
	public void setTitle(String title) {
		this.title = title;
		
		swtColorDialog.setText(title);
	}

	public IusCLColor getColor() {
		return color;
	}

	/* **************************************************************************************************** */
	public void setColor(IusCLColor color) {
		this.color = color;
		
		swtColorDialog.setRGB(new RGB(color.getRed(), color.getGreen(), color.getBlue()));
	}

}
