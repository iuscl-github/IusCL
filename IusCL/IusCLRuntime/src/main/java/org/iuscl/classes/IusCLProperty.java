/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.classes;

import org.iuscl.designintf.propertyeditors.IusCLDesignPropertyEditor;

/* **************************************************************************************************** */
public class IusCLProperty {

	private String name;
	private String propertyValueSetter;
	private String propertyValueGetter;
	
	private String type;
	private Boolean published;
	private String defaultValue;
	
	private Enum<?> enumeration;

	private Class<?> refClass;

	private IusCLDesignPropertyEditor propertyEditor;
	
	/* **************************************************************************************************** */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPropertyValueSetter() {
		return propertyValueSetter;
	}
	
	public void setPropertyValueSetter(String propertyValueSetter) {
		this.propertyValueSetter = propertyValueSetter;
	}
	
	public String getPropertyValueGetter() {
		return propertyValueGetter;
	}
	
	public void setPropertyValueGetter(String propertyValueGetter) {
		this.propertyValueGetter = propertyValueGetter;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Boolean getPublished() {
		return published;
	}
	
	public void setPublished(Boolean published) {
		this.published = published;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public Enum<?> getEnumeration() {
		return enumeration;
	}
	
	public void setEnumeration(Enum<?> enumeration) {
		this.enumeration = enumeration;
	}
	
	public IusCLDesignPropertyEditor getPropertyEditor() {
		return propertyEditor;
	}
	
	public void setPropertyEditor(IusCLDesignPropertyEditor propertyEditor) {
		this.propertyEditor = propertyEditor;
	}
	
	public Class<?> getRefClass() {
		return refClass;
	}
	
	public void setRefClass(Class<?> refClass) {
		this.refClass = refClass;
	}
}
