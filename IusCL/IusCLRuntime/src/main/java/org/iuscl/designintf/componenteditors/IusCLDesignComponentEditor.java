/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.componenteditors;

import org.eclipse.swt.widgets.MenuItem;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.system.IusCLObject;

/* **************************************************************************************************** */
public class IusCLDesignComponentEditor extends IusCLObject {

	private IusCLComponent component = null;
	private Boolean hasAdd = false;
	private Boolean hasOrder = false;

	/* **************************************************************************************************** */
	public IusCLDesignComponentEditorVerb getVerbSerializeAndBroadcastChange() {
		
		IusCLDesignComponentEditorVerb verb = new IusCLDesignComponentEditorVerb();
		verb.setMethodName("serializeAndBroadcastChange");

		return verb;
	}
	
	/* **************************************************************************************************** */
	public IusCLDesignComponentEditorVerb edit() {
		/* Execute the first verb */
		if (getVerbCount() > 0) {

			return executeVerb(0);
		}
		
		return null;
	}

	/* **************************************************************************************************** */
	public IusCLDesignComponentEditorVerb verbAdd() {
		return null;
	}

	/* **************************************************************************************************** */
	public IusCLDesignComponentEditorVerb verbOrder(int firstIndex, int secondIndex) {
		return null;
	}

	/* **************************************************************************************************** */
	public IusCLDesignComponentEditorVerb executeVerb(int index) {
		return null;
	}

	/* **************************************************************************************************** */
	public String getVerb(int index) {
		return null;
	}
	
	/* **************************************************************************************************** */
	public int getVerbCount() {
		return 0;
	}
	
	/* **************************************************************************************************** */
	public void prepareItem(int index, MenuItem swtMenuItem) {
		/*  */
	}

	/* **************************************************************************************************** */
	public Boolean getHasAdd() {
		return hasAdd;
	}

	public void setHasAdd(Boolean hasAdd) {
		this.hasAdd = hasAdd;
	}

	public IusCLComponent getComponent() {
		return component;
	}

	public void setComponent(IusCLComponent component) {
		this.component = component;
	}

	public Boolean getHasOrder() {
		return hasOrder;
	}

	public void setHasOrder(Boolean hasOrder) {
		this.hasOrder = hasOrder;
	}
	
}
