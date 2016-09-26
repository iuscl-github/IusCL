/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.dialogs;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLFont;

/* **************************************************************************************************** */
public class IusCLFontDialog extends IusCLCommonDialog {

	public enum IusCLFontDialogDevice { fdScreen, fdPrinter, fdBoth };

	/* SWT */
	private FontDialog swtFontDialog = null;
	
	/* Properties */
	private IusCLFont font = new IusCLFont();
	private String title = "Font";
	
	/* Events */

	/* **************************************************************************************************** */
	public IusCLFontDialog(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		font.setNotify(this, "setFont");
		defineProperty("Font", IusCLPropertyType.ptFont, "(IusCLFont)");
		IusCLFont.defineFontProperties(this, "Font", font);

		defineProperty("Title", IusCLPropertyType.ptString, "Font");

		/* Events */
		
		/* Create */
		swtFontDialog = new FontDialog(this.findForm().getSwtShell());
	}

	/* **************************************************************************************************** */
	@Override
	public Boolean execute() {
		
		swtFontDialog.setText(title);
		
		RGB originalSwtRGB = null;
		if (font != null) {
			
			swtFontDialog.setFontList(font.getSwtFont().getFontData());
			originalSwtRGB = font.getColor().getAsSwtColor().getRGB();
			swtFontDialog.setRGB(originalSwtRGB);
		}

		Boolean result = false;
		FontData swtFontData = swtFontDialog.open();
		if (swtFontData != null) {
			
			Font swtFont = new Font(Display.getCurrent(), swtFontData);
			font.setSwtFont(swtFont);
			
			RGB modifiedSwtRGB = swtFontDialog.getRGB();
			if (modifiedSwtRGB != originalSwtRGB) {
				
				IusCLColor modifiedColor = new IusCLColor();
				modifiedColor.loadFromSwtColor(new Color(Display.getCurrent(), modifiedSwtRGB));
				font.setColor(modifiedColor);
			}
			
			result = true;
		}
		
		return result;
	}

	/* **************************************************************************************************** */
	@Override
	public Dialog getSwtDialog() {
		
		return swtFontDialog;
	}

	public IusCLFont getFont() {
		return font;
	}

	public void setFont(IusCLFont font) {
		this.font = font;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
