/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.stdctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;

/* **************************************************************************************************** */
public class IusCLLabel extends IusCLWinControl {

	public enum IusCLAlignment { taLeftJustify, taRightJustify, taCenter };

	/* SWT */
	private Label swtLabel = null;

	/* Properties */
	private IusCLAlignment alignment = IusCLAlignment.taLeftJustify;
	
	/* **************************************************************************************************** */
	public IusCLLabel(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Alignment", IusCLPropertyType.ptEnum, "taLeftJustify", IusCLAlignment.taLeftJustify);
		defineProperty("AutoSize", IusCLPropertyType.ptBoolean, "true");
		//this.getProperties().get("AutoSize").setDefaultValue("true");
		
		/* Events */

		/* Create */
		swtLabel = new Label(this.getFormSwtComposite(), SWT.WRAP);
		createWnd(swtLabel);
	}

	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();
		
		this.getProperties().get("TabStop").setDefaultValue("false");
		this.setTabStop(false);
		
		this.setAutoSize(true);
	}

	/* **************************************************************************************************** */
	@Override
	public void setCaption(String caption) {
		
		super.setCaption(caption);
		swtLabel.setText(caption);
		doAutoSizeParentAlignControls();
	}

	public IusCLAlignment getAlignment() {
		return alignment;
	}

	/* **************************************************************************************************** */
	public void setAlignment(IusCLAlignment alignment) {
		this.alignment = alignment;
		
		switch(alignment) {
		case taCenter:
			swtLabel.setAlignment(SWT.CENTER);
			break;
		case taLeftJustify:
			swtLabel.setAlignment(SWT.LEFT);
			break;
		case taRightJustify:
			swtLabel.setAlignment(SWT.RIGHT);
			break;
		}
	}

	/* **************************************************************************************************** */
	@Override
	public Boolean getAutoSize() {
		
		return super.getAutoSize();
	}

	/* **************************************************************************************************** */
	@Override
	public void setAutoSize(Boolean autoSize) {
		
		super.setAutoSize(autoSize);
	}

}
