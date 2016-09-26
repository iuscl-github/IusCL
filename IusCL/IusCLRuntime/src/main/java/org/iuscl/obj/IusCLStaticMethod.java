/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.obj;

import org.iuscl.system.IusCLLog;

/* **************************************************************************************************** */
public class IusCLStaticMethod {

	private String className = null;
	private String staticMethodName = null;
	private IusCLParam[] invokeParams = null;
	
	/* **************************************************************************************************** */
	public IusCLStaticMethod(String className, String staticMethodName, IusCLParam... invokeParams) {
		
		this.className = className;
		this.staticMethodName = staticMethodName;
		this.invokeParams = invokeParams;
	}
	
	/* **************************************************************************************************** */
	public Object invokeStatic() {

		Object res = null;
		
		try {
			Class<?> staticClass = IusCLObjUtils.classForName(className);
			IusCLMethod method = new IusCLMethod(staticClass, staticMethodName, invokeParams);
			res = method.invokeMethod(null);
		}
		catch (Exception exception) {
			IusCLLog.logError("Error in invokeStaticMethod", exception);
		}

		return res;
	}
	
}
