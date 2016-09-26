/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.controls;

import org.iuscl.classes.IusCLComponent;

/* **************************************************************************************************** */
public class IusCLMultiSelectListControl extends IusCLListControl {

	/* Properties */
	private Boolean multiSelect = true;
	
	/* Events */

	/* **************************************************************************************************** */
	public IusCLMultiSelectListControl(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("MultiSelect", IusCLPropertyType.ptBoolean, "true");
		
		/* Events */
	}
	
	public Boolean getMultiSelect() {
		return multiSelect;
	}

	/* **************************************************************************************************** */
	public void setMultiSelect(Boolean multiSelect) {

		if (this.multiSelect != multiSelect) {
			
			this.multiSelect = multiSelect;
			
			reCreateWnd();		
		}
	}

	/* **************************************************************************************************** */
	public Integer getSelCount() {
		
		return 0;
	}

	/* **************************************************************************************************** */
	public Boolean getSelection(Integer index) {
		
		return false;
	}

	/* **************************************************************************************************** */
	public void setSelection(Integer index, Boolean selected) {
		/* Nothing */
	}

	/* **************************************************************************************************** */
	public void setSelection(Integer index, Integer length, Boolean selected) {
		/* Nothing */
	}

	/* **************************************************************************************************** */
	public Integer getFirstVisibleIndex() {
		
		return 0;
	}

	/* **************************************************************************************************** */
	public void setFirstVisibleIndex(Integer topIndex) {
		/* Nothing */
	}

}
