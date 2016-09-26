/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.componenteditors;

import org.iuscl.obj.IusCLParam;

/* **************************************************************************************************** */
public class IusCLDesignComponentEditorVerb {

	private String methodName = null;
	private IusCLParam[] params = null;

	/* **************************************************************************************************** */
	public String getMethodName() {
		return methodName;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public IusCLParam[] getParams() {
		return params;
	}
	
	public void setParams(IusCLParam[] params) {
		this.params = params;
	}
}
