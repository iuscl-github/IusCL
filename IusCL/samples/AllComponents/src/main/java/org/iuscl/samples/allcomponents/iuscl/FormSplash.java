/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allcomponents.iuscl;

import org.iuscl.forms.IusCLForm;
import org.iuscl.sysctrls.IusCLTimer;
import org.iuscl.system.IusCLObject;
import org.iuscl.extctrls.IusCLPanel;
import org.iuscl.extctrls.IusCLImage;
import org.iuscl.stdctrls.IusCLLabel;

/* **************************************************************************************************** */
public class FormSplash extends IusCLForm {

	/* IusCL Components */
	public IusCLTimer timerClose;
	public IusCLPanel panel1;

	public IusCLImage imageSplash;

	public IusCLLabel labelIusCL;

	/** timerClose.OnTimer event implementation */
	public void timerCloseTimer(IusCLObject sender) {

		FormSplash.this.close();
		System.out.println("timerCloseTimer");
	}
	/** form.OnShow event implementation */
	public void formShow(IusCLObject sender) {

		System.out.println("formShow");
		//timerClose.setEnabled(true);
	}

}