/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allpdf.iuscl;

import org.iuscl.forms.IusCLApplication;

/** IusCL application class */
public class AllPdf {

	/* Program */
	public static void main(String[] args) {

		/* Initialize the application */
		IusCLApplication.initialize();

		/* Auto-create forms */
		FormMainPdf formMainPdf = new FormMainPdf();
		
		/* The main form */
		IusCLApplication.setMainForm(formMainPdf);

		/* Application loop */
		IusCLApplication.run();
	}
}