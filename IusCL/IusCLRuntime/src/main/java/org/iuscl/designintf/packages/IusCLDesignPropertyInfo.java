/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.packages;

/* **************************************************************************************************** */
public class IusCLDesignPropertyInfo {

	private String name;
	private String propertyEditorClass;
	
	private Boolean customDisplayValue = false;
	private Boolean customDisplayPaint = false;
	
	/* **************************************************************************************************** */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPropertyEditorClass() {
		return propertyEditorClass;
	}
	
	public void setPropertyEditorClass(String propertyEditorClass) {
		this.propertyEditorClass = propertyEditorClass;
	}

	public Boolean getCustomDisplayValue() {
		return customDisplayValue;
	}

	public void setCustomDisplayValue(Boolean customDisplayValue) {
		this.customDisplayValue = customDisplayValue;
	}

	public Boolean getCustomDisplayPaint() {
		return customDisplayPaint;
	}

	public void setCustomDisplayPaint(Boolean customDisplayPaint) {
		this.customDisplayPaint = customDisplayPaint;
	}
	
}
