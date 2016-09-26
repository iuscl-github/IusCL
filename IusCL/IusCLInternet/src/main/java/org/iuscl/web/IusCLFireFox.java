/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.web;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.iuscl.classes.IusCLComponent;

/* **************************************************************************************************** */
public class IusCLFireFox extends IusCLWebBrowser {

	/* **************************************************************************************************** */
	public IusCLFireFox(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		
		/* Events */

		/* Create */
		swtBrowser = new Browser(this.getFormSwtComposite(), SWT.MOZILLA);
//		create(swtBrowser);
		
		swtBrowser.setUrl("http://www.google.com/ncr");
		/* Default */
	}

}
