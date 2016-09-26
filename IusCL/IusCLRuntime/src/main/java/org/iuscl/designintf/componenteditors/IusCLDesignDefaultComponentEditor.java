/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.componenteditors;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLProperty;
import org.iuscl.classes.IusCLPersistent.IusCLPropertyType;
import org.iuscl.obj.IusCLParam;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLDesignDefaultComponentEditor extends IusCLDesignComponentEditor {

	/* **************************************************************************************************** */
	@Override
	public IusCLDesignComponentEditorVerb edit() {

		IusCLComponent component = getComponent();

		Boolean eventFound = false;

		/* Defined event */
		String eventName = editEventName();
		if (IusCLStrUtils.isNotNullNotEmpty(eventName)) {
			
			eventFound = true;
		}

		/* Preferred events */
		if (eventFound == false) {
			eventName = "OnCreate";
			if (hasEvent(eventName)) {
				eventFound = true;
			}
		}

		if (eventFound == false) {
			eventName = "OnChange";
			if (hasEvent(eventName)) {
				eventFound = true;
			}
		}

		if (eventFound == false) {
			eventName = "OnClick";
			if (hasEvent(eventName)) {
				eventFound = true;
			}
		}

		if (eventFound == false) {
			eventName = "OnCreate";
			if (hasEvent(eventName)) {
				eventFound = true;
			}
		}

		/* First event */
		if (eventFound == false) {
			
			/* Load lists */
			Vector<String> propertiesNamesVector = new Vector<String>(component.getProperties().keySet());
			Collections.sort(propertiesNamesVector);
			Iterator<String> propertiesNamesIterator = propertiesNamesVector.iterator();
		    while (propertiesNamesIterator.hasNext()) {
		    	
		    	/* Property */
		    	eventName = propertiesNamesIterator.next();

		    	String propertyType = component.getProperty(eventName).getType(); 

		    	if (component.getProperty(eventName).getPublished()) {
		    		/* Is published */
		    		if (propertyType.equalsIgnoreCase(IusCLPropertyType.ptEvent.name())) {
		    			/* Is published and is an event */
						eventFound = true;
						break;
		    		}
    			}
		    }
		}
		
		if (eventFound == true) {
			
			String eventValue = component.getPropertyValue(eventName);
			
			if (!IusCLStrUtils.isNotNullNotEmpty(eventValue)) {
				
				String componentName = component.getName();
				String eventFunctionName = componentName + eventName.substring(2);
				component.setPropertyValue(eventName, eventFunctionName);
			}	
			
			IusCLDesignComponentEditorVerb eventVerb = new IusCLDesignComponentEditorVerb();
			
			eventVerb.setMethodName("change");
			
			IusCLDesignSelection designSelection = new IusCLDesignSelection(component, null, null);
			
			IusCLParam[] params = new IusCLParam[3];
			params[0] = new IusCLParam(String.class, eventName);
			params[1] = new IusCLParam(String.class, eventValue);
			params[2] = new IusCLParam(IusCLDesignSelection.class, designSelection);
			
			eventVerb.setParams(params);
			
			return eventVerb;
		}
		
		return null;
	}

	/* **************************************************************************************************** */
	private Boolean hasEvent(String eventName) {

		if (getComponent().getProperties().containsKey(eventName) == true) {
			
			IusCLProperty eventProperty = getComponent().getProperty(eventName);
			if (IusCLStrUtils.equalValues(eventProperty.getType(), IusCLPropertyType.ptEvent.name())) {
				
				if (eventProperty.getPublished()) {
					
					return true;
				}
			}
		}
		
		return false;
	}

	/* **************************************************************************************************** */
	protected String editEventName() {

		return null;
	}
}
