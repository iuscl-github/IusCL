/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.packages;

import org.iuscl.rss.IusCLNewsFeeds;

/* **************************************************************************************************** */
public class IusCLDesignPackageRSS extends IusCLDesignPackage {

	protected static String pnInternet = "Internet";

	/* **************************************************************************************************** */
	public IusCLDesignPackageRSS() {
		super();

		String res = "resources/designintf/images/";

		/* Components */
		defineDesignComponentInfo(IusCLNewsFeeds.class.getCanonicalName(),
				"IusCLNewsFeeds", pnInternet, res + "IusCLDesignNewsFeeds.gif", "Feeds");
	}

}
