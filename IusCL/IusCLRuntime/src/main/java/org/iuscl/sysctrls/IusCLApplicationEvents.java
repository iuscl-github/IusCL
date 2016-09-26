/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.sysctrls;

import org.iuscl.classes.IusCLComponent;
import org.iuscl.events.IusCLMessageEvent;
import org.iuscl.forms.IusCLApplication;

/* **************************************************************************************************** */
public class IusCLApplicationEvents extends IusCLComponent {

	/* Properties */
	private Boolean enabled = false;

	/* Events */
	private IusCLMessageEvent onMessage = null;
	
	/* Fields */

	/* **************************************************************************************************** */
	public IusCLApplicationEvents(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		defineProperty("Enabled", IusCLPropertyType.ptBoolean, "false");

		/* Events */
		defineProperty("OnMessage", IusCLPropertyType.ptEvent, null, IusCLMessageEvent.class);

	}

	/* **************************************************************************************************** */

	public Boolean getEnabled() {
		return enabled;
	}

	/* **************************************************************************************************** */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		
		if (IusCLApplication.getApplicationEvents().indexOf(this) == -1) {

			if (enabled == true) {
				
				IusCLApplication.getApplicationEvents().add(this);
			}
		}
		else {
			
			if (enabled == false) {
				
				IusCLApplication.getApplicationEvents().remove(this);
			}
		}
				
				
	}

	public IusCLMessageEvent getOnMessage() {
		return onMessage;
	}

	public void setOnMessage(IusCLMessageEvent onMessage) {
		this.onMessage = onMessage;
	}
	
}
