/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.stdctrls;

import org.eclipse.swt.widgets.Button;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;

/* **************************************************************************************************** */
public class IusCLButtonControl extends IusCLWinControl {

	/* SWT */
	protected Button swtButton = null;

	/* Properties */
//	private Boolean clicksDisabled = false;
//	private Boolean wordWrap = false;
	protected Boolean checked = false;

	/* **************************************************************************************************** */
	public IusCLButtonControl(IusCLComponent aOwner) {
		super(aOwner);
	}

	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();
		
		this.removeProperty("OnDoubleClick");
	}

	/* **************************************************************************************************** */
	@Override
	public void setCaption(String caption) {
		
		super.setCaption(caption);
		swtButton.setText(this.getCaption());
	}

	/* **************************************************************************************************** */
//	public Boolean getClicksDisabled() {
//		return clicksDisabled;
//	}
//
//	public void setClicksDisabled(Boolean clicksDisabled) {
//		this.clicksDisabled = clicksDisabled;
//	}
//
//	public Boolean getWordWrap() {
//		return wordWrap;
//	}
//
//	public void setWordWrap(Boolean wordWrap) {
//		this.wordWrap = wordWrap;
//	}
}
