/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.obj;

import java.lang.reflect.Constructor;

import org.iuscl.system.IusCLLog;

/* **************************************************************************************************** */
public class IusCLConstructor {

	private Class<?> clasz = null;
	private IusCLParam[] constructorParams = null;

	/* **************************************************************************************************** */
	public IusCLConstructor(Class<?> clasz, IusCLParam... constructorParams) {
		
		this.clasz = clasz;
		this.constructorParams = constructorParams;
	}

	/* **************************************************************************************************** */
	public IusCLConstructor(String className, IusCLParam... constructorParams) {

		this.clasz = IusCLObjUtils.classForName(className);
		this.constructorParams = constructorParams;
	}

	/* **************************************************************************************************** */
	public Object invokeConstructor() {

		Boolean hasParams = true;
		
		if (constructorParams != null) {
			if (constructorParams.length == 0) {
				hasParams = false;
			}
		}
		else {
			hasParams = false;
		}
		
		Object res = null;
		
		try {
			if (hasParams == true) {
				Class<?>[] constructorParamTypes = new Class<?>[constructorParams.length];
				Object[] constructorParamValues = new Object[constructorParams.length];

				int index = 0;
				for(IusCLParam constructorParam : constructorParams) {
					constructorParamTypes[index] = constructorParam.getParameterType();
					constructorParamValues[index] = constructorParam.getParameterValue();
					index++;
				}		

				Constructor<?> constructor = clasz.getConstructor(constructorParamTypes);
				res = constructor.newInstance(constructorParamValues);			
			}
			else {
				Constructor<?> constructor = clasz.getConstructor();
				res = constructor.newInstance();			
			}
		}
		catch (Exception exception) {
			IusCLLog.logError("Error in invokeConstructor, for class = " + clasz.getCanonicalName(), exception);
		}
		
		return res;
	}
	
}
