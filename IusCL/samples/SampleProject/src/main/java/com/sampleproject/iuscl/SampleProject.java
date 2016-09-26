/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package com.sampleproject.iuscl;

import org.iuscl.forms.IusCLApplication;

/** IusCL application class */
public class SampleProject {

	/* Program */
	public static void main(String[] args) {

		/* Initialize the application */
		IusCLApplication.initialize();
		IusCLApplication.setTitle("Sample Project Title");

		/* Auto-create forms */
		MainForm mainForm = new MainForm();
		
		/* The main form */
		IusCLApplication.setMainForm(mainForm);

		/* Application loop */
		IusCLApplication.run();
	}
}