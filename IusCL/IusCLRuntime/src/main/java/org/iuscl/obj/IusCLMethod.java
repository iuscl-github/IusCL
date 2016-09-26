/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.obj;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.iuscl.system.IusCLLog;

/* **************************************************************************************************** */
public class IusCLMethod {
	
	private Class<?> objectClass = null;
	private String methodName = null;
	private IusCLParam[] invokeParams = null;
	
	/* **************************************************************************************************** */
	public IusCLMethod(Class<?> objectClass, String methodName, IusCLParam... invokeParams) {
		
		this.objectClass = objectClass;
		this.methodName = methodName;
		this.invokeParams = invokeParams;
	}
	
	/* **************************************************************************************************** */
	public IusCLMethod(String methodName, IusCLParam... invokeParams) {
		
		this.methodName = methodName;
		this.invokeParams = invokeParams;
	}
	
	/* **************************************************************************************************** */
	public Object invokeMethod(Object objectInstance) {
		
		Class<?> invokeObjectClass = null;
		
		if (this.objectClass == null) {
			invokeObjectClass = objectInstance.getClass();
		}
		else {
			invokeObjectClass = this.objectClass;
		}
		
		Boolean hasParams = true;
		
		if (invokeParams != null) {
			if (invokeParams.length == 0) {
				hasParams = false;
			}
		}
		else {
			hasParams = false;
		}
		
		Object res = null;
		
		try {
			if (hasParams == true) {
				Class<?>[] parameterTypes = new Class<?>[invokeParams.length];
				Object[] parameterValues = new Object[invokeParams.length];
		
				int index = 0;
				for(IusCLParam param : invokeParams) {
					parameterTypes[index] = param.getParameterType();
					parameterValues[index] = param.getParameterValue();
					index++;
				}
				Method method = invokeObjectClass.getMethod(methodName, parameterTypes);
				res = method.invoke(objectInstance, parameterValues);
			}
			else {
				Method method = invokeObjectClass.getMethod(methodName);
				res = method.invoke(objectInstance);
			}
		}
		catch (IllegalAccessException illegalAccessException) {

			IusCLLog.logError("IllegalAccessException in Reflection" +
					"\n\nInvoked method: " + methodName +
					"\nFrom class: " + objectInstance.getClass().getSimpleName(), 
					illegalAccessException);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			
			IusCLLog.logError("NoSuchMethodException in Reflection" +
					"\n\nInvoked method: " + methodName +
					"\nFrom class: " + objectInstance.getClass().getSimpleName(), 
					noSuchMethodException);
		}
		catch (InvocationTargetException invocationTargetException) {
			
			IusCLLog.logError("InvocationTargetException in Reflection" +
					"\n\nInvoked method: " + methodName +
					"\nFrom class: " + objectInstance.getClass().getSimpleName() + 
					"\nException cause: " + invocationTargetException.getCause().toString(), 
					invocationTargetException);
		}

		return res;
	}
	
}
