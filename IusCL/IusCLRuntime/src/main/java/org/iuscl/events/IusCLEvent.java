/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.events;

import org.iuscl.obj.IusCLObjUtils;
import org.iuscl.obj.IusCLParam;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLEvent {

	private Object eventDeclaringInstance = null;
	private String eventMethodName = null;

	/* **************************************************************************************************** */
	protected Object invoke(IusCLParam... invokeParams) {
		
		return IusCLObjUtils.invokeMethod(eventDeclaringInstance, eventMethodName, invokeParams);
	}

	/* **************************************************************************************************** */
	public static Boolean isDefinedEvent(IusCLEvent event) {
		
		if (event == null) {
			
			return false;
		}
		
		if (event.getEventDeclaringInstance() == null) {
			
			return false;
		}
		
		if (IusCLStrUtils.isNotNullNotEmpty(event.getEventMethodName()) == false) {
			
			return false;
		}
		
		return true;
	}

	public Object getEventDeclaringInstance() {
		return eventDeclaringInstance;
	}

	public void setEventDeclaringInstance(Object eventDeclaringInstance) {
		this.eventDeclaringInstance = eventDeclaringInstance;
	}

	public String getEventMethodName() {
		return eventMethodName;
	}

	public void setEventMethodName(String eventMethodName) {
		this.eventMethodName = eventMethodName;
	}
}
