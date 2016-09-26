/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.obj;

/* **************************************************************************************************** */
public class IusCLParam {

	private Class<?> parameterType;
	private Object parameterValue;

	/* **************************************************************************************************** */
	public IusCLParam(Class<?> parameterType, Object parameterValue) {
		super();
		this.parameterType = parameterType;
		this.parameterValue = parameterValue;
	}

	/* **************************************************************************************************** */
	public Class<?> getParameterType() {
		return parameterType;
	}

	public Object getParameterValue() {
		return parameterValue;
	}

}
