/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.web;

import org.iuscl.events.IusCLEvent;
import org.iuscl.obj.IusCLParam;
import org.iuscl.system.IusCLObject;

/* **************************************************************************************************** */
public class IusCLWebBrowserNavigateEvent extends IusCLEvent {

	/* **************************************************************************************************** */
	public Boolean invoke(IusCLObject sender, String location, Boolean thePage) {

		return (Boolean)super.invoke(
			new IusCLParam(IusCLObject.class, sender), 
			new IusCLParam(String.class, location),
			new IusCLParam(Boolean.class, thePage));
	}
}
