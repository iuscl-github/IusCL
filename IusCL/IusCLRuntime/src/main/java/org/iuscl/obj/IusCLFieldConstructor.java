/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.obj;

import java.lang.reflect.Field;

import org.iuscl.system.IusCLLog;

/* **************************************************************************************************** */
public class IusCLFieldConstructor {

	private String fieldName = null;
	private IusCLParam[] constructorParams = null;

	/* **************************************************************************************************** */
	public IusCLFieldConstructor(String fieldName, IusCLParam... constructorParams) {
		
		this.fieldName = fieldName;
		this.constructorParams = constructorParams;
	}

	/* **************************************************************************************************** */
	public Object invokeFieldConstructor(Object declaringInstance) {

		Object fieldInstance = null;

		try {
			Field field = declaringInstance.getClass().getField(fieldName);

			fieldInstance = IusCLObjUtils.invokeConstructor(field.getType().getCanonicalName(), constructorParams);
			field.set(declaringInstance, fieldInstance);
		}
		catch (Exception exception) {
			IusCLLog.logError("Error in invokeFieldConstructor, for the field name: " + fieldName, exception);
		}
		
		return fieldInstance;
	}

}
