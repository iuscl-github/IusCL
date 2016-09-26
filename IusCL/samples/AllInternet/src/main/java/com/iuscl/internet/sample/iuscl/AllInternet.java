/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package com.iuscl.internet.sample.iuscl;

import org.iuscl.forms.IusCLApplication;

/** IusCL application class */
public class AllInternet {

	/*
	 * Program
	 */
	public static void main(String[] args) {

		/*
		 * Initialize the application
		 */
		IusCLApplication.initialize();

		/*
		 * Auto-create forms
		 */
		FormMain formMain = new FormMain();
		
		/*
		 * The main form
		 */
		IusCLApplication.setMainForm(formMain);

		/*
		 * Application loop
		 */
		IusCLApplication.run();
	}
}