/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.packages;

import org.iuscl.system.IusCLObject;
import org.iuscl.types.IusCLPoint;
import org.iuscl.types.IusCLSize;
import org.iuscl.web.IusCLFireFox;
import org.iuscl.web.IusCLInternetExplorer;
import org.iuscl.web.IusCLWebBrowser;
import org.iuscl.web.IusCLWebKit;

/* **************************************************************************************************** */
public class IusCLDesignPackageWeb extends IusCLDesignPackage {

	protected static String pnWeb = "Web";

	/* **************************************************************************************************** */
	public IusCLDesignPackageWeb() {
		super();

		String res = "resources/designintf/images/";

		/* Components */

		defineDesignComponentInfo(IusCLInternetExplorer.class.getCanonicalName(),
				"IusCLInternetExplorer", pnWeb, res + "IusCLDesignInternetExplorer.gif", "IE");

		defineDesignComponentInfo(IusCLFireFox.class.getCanonicalName(),
				"IusCLFireFox", pnWeb, res + "IusCLDesignFireFox.gif", "FireFox");

		defineDesignComponentInfo(IusCLWebKit.class.getCanonicalName(),
				"IusCLWebKit", pnWeb, res + "IusCLDesignWebKit.gif", "WebKit");

		/* Events */
		
		this.setCodeTemplatesFile("IusCLCodeTemplatesWeb.txt");
		
		defineEventInfo("IusCLWebBrowserNavigateEvent", "IusCLWebBrowserNavigateEvent", null, 
				IusCLObject.class.getCanonicalName());

		defineEventInfo("IusCLWebBrowserOpenWindowEvent", "IusCLWebBrowserOpenWindowEvent", null, 
				IusCLObject.class.getCanonicalName(),
				IusCLWebBrowser.class.getCanonicalName(),
				IusCLPoint.class.getCanonicalName(),
				IusCLSize.class.getCanonicalName());

	}
}
