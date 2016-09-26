/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.web;

import org.iuscl.events.IusCLEvent;
import org.iuscl.obj.IusCLParam;
import org.iuscl.system.IusCLObject;
import org.iuscl.types.IusCLPoint;
import org.iuscl.types.IusCLSize;

/* **************************************************************************************************** */
public class IusCLWebBrowserOpenWindowEvent extends IusCLEvent {

	/* **************************************************************************************************** */
	public IusCLWebBrowser invoke(IusCLObject sender, IusCLPoint location, IusCLSize size,
			Boolean menuBar, Boolean toolBar, Boolean addressBar, Boolean statusBar) {

		return (IusCLWebBrowser)super.invoke(
			new IusCLParam(IusCLObject.class, sender), 
			new IusCLParam(IusCLPoint.class, location),
			new IusCLParam(IusCLSize.class, size),
			new IusCLParam(Boolean.class, menuBar),
			new IusCLParam(Boolean.class, toolBar),
			new IusCLParam(Boolean.class, addressBar),
			new IusCLParam(Boolean.class, statusBar));
	}
}
