/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.obj;

import org.iuscl.system.IusCLLog;

/* **************************************************************************************************** */
public class IusCLObjUtils {

	/* **************************************************************************************************** */
	public static Class<?> classForName(String className) {

		Class<?> clasz = null;
		try {
			
			clasz = Class.forName(className);
		}
		catch (Exception exception) {
			
			IusCLLog.logError("Exception in classForName\nClass name = " + className +
				"\nException = " + exception.toString(), exception);
		}

		return clasz;
	}
	
	
	/* **************************************************************************************************** */
	public static Object invokeConstructor(String className, IusCLParam... constructorParams) {

		IusCLConstructor constructor = new IusCLConstructor(className, constructorParams);
		return constructor.invokeConstructor();
	}

	/* **************************************************************************************************** */
	public static Object invokeConstructor(Class<?> clazz, IusCLParam... constructorParams) {

		IusCLConstructor constructor = new IusCLConstructor(clazz, constructorParams);
		return constructor.invokeConstructor();
	}

	/* **************************************************************************************************** */
	public static Object invokeStaticMethod(String className, String staticMethodName, IusCLParam... invokeParams) {

		IusCLStaticMethod staticMethod = new IusCLStaticMethod(className, staticMethodName, invokeParams);
		return staticMethod.invokeStatic();
	}

	/* **************************************************************************************************** */
	public static Object invokeMethod(Object objectInstance, String methodName, IusCLParam... invokeParams) {
		return invokeMethod(objectInstance, objectInstance.getClass(), methodName, invokeParams);
	}

	/* **************************************************************************************************** */
	private static Object invokeMethod(Object objectInstance, Class<?> objectClass, String methodName, 
			IusCLParam... invokeParams) {

		IusCLMethod method = new IusCLMethod(objectClass, methodName, invokeParams);
		return method.invokeMethod(objectInstance);
	}

	/* **************************************************************************************************** */
	public static Object invokeFieldConstructor(Object declaringInstance, String fieldName, IusCLParam... constructorParams) {

		IusCLFieldConstructor fieldConstructor = new IusCLFieldConstructor(fieldName, constructorParams);
		return fieldConstructor.invokeFieldConstructor(declaringInstance);
	}

	/* **************************************************************************************************** */
	public static Boolean equalInstances(Object oneInstance, Object anotherInstance) {
		
		Boolean result = false;
		if (oneInstance == null) {
			
			if (anotherInstance == null) {
				
				result = true;
			}
		}
		else {
			if (oneInstance.equals(anotherInstance)) {
				
				result = true;
			}
		}
		
		return result;
	}

}
