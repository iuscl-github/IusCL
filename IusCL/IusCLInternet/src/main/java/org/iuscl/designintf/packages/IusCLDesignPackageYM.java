/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.packages;

import org.iuscl.ym.IusCLYahooMessenger;

/* **************************************************************************************************** */
public class IusCLDesignPackageYM extends IusCLDesignPackage {

	protected static String pnInternet = "Internet";

	/* **************************************************************************************************** */
	public IusCLDesignPackageYM() {
		super();

		String res = "resources/designintf/images/";

		/* Components */
		defineDesignComponentInfo(IusCLYahooMessenger.class.getCanonicalName(),
				"IusCLYahooMessenger", pnInternet, res + "IusCLDesignYahooMessenger.gif", "Y!M");
	}

}
