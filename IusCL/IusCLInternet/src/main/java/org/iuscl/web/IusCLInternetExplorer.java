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
public class IusCLInternetExplorer extends IusCLWebBrowser {

	/* **************************************************************************************************** */
	public IusCLInternetExplorer(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		
		/* Events */

		/* Create */
		swtBrowser = new Browser(this.getFormSwtComposite(), SWT.NONE);
		createWnd(swtBrowser);
		
		String html = "<html><title>Editor</title>"
			       + "<body  contenteditable='true'>"
			       + " <h2>All the Page is ditable!!!!!</h2>" 
			       + "<p>Heres a typical paragraph element</p>" 
			       + "<ol><li>and now a list</li>" 
			       + "<li>with only</li>" 
			       + "<li>three items</li>" 
			       + "</ol></body></html>";

		
		//swtBrowser.setUrl("about:blank");
		
		swtBrowser.setText(html);
		
		boolean result = swtBrowser.execute("document.body.innerHTML;");
	        if (!result) {
	          /*
	           * Script may fail or may not be supported on certain
	           * platforms.
	           */
	          System.out.println("Script was not executed.");
	          return;
	        }
	        String value = (String) swtBrowser.getData("query");
	        System.out.println("Node value: " + value);
		
		//System.out.println(swtBrowser.)

		/* Default */
	}
}
