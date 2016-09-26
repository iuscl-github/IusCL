/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.forms;

import org.iuscl.system.IusCLObject;

/* **************************************************************************************************** */
public class IusCLBorderIcons extends IusCLObject {

	private Boolean systemMenu = true;
	private Boolean minimize = true;
	private Boolean maximize = true;
	private Boolean help = false;
	
	/* **************************************************************************************************** */
	public Boolean getSystemMenu() {
		return systemMenu;
	}
	
	public void setSystemMenu(Boolean systemMenu) {
		this.systemMenu = systemMenu;
		
		invokeNotify();
	}

	public Boolean getMinimize() {
		return minimize;
	}
	
	public void setMinimize(Boolean minimize) {
		this.minimize = minimize;
		
		invokeNotify();
	}
	
	public Boolean getMaximize() {
		return maximize;
	}
	
	public void setMaximize(Boolean maximize) {
		this.maximize = maximize;
		
		invokeNotify();
	}
	
	public Boolean getHelp() {
		return help;
	}
	
	public void setHelp(Boolean help) {
		this.help = help;
		
		invokeNotify();
	}
}
