/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allcomponents.iuscl;

import org.iuscl.forms.IusCLApplication;

/* **************************************************************************************************** */
public class AllComponents {

	/* Program */
	public static void main(String[] args) {

		/* Initialize the application */
		IusCLApplication.initialize();
		IusCLApplication.setTitle("All Components");

		/* Splash screen */
		FormSplash formSplash = new FormSplash();
		formSplash.show();
		
		/* Auto-create forms */
		FormMain formMain = new FormMain();

		formSplash.release();
		
		/* The main form */
		IusCLApplication.setMainForm(formMain);

		/* Application loop */
		IusCLApplication.run();
	}
}