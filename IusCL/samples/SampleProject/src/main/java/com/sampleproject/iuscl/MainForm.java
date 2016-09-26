/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package com.sampleproject.iuscl;

import org.iuscl.dialogs.IusCLDialogs;
import org.iuscl.dialogs.IusCLDialogs.IusCLMessageBoxFlags;
import org.iuscl.dialogs.IusCLDialogs.IusCLMessageBoxIcon;
import org.iuscl.forms.IusCLForm;
import org.iuscl.stdctrls.IusCLButton;
import org.iuscl.system.IusCLObject;

/** IusCL form class */
public class MainForm extends IusCLForm {

	/* IusCL Components */
	public IusCLButton firstButton;
	/** firstButton.OnClick event implementation */
	public void firstButtonClick(IusCLObject sender) {

		IusCLDialogs.messageBox("Hello World!", "Sample Project Message Box", IusCLMessageBoxIcon.mbiInformation, IusCLMessageBoxFlags.mbOK);
	}

}