/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.sysctrls;

import org.iuscl.classes.IusCLComponent;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLNotifyEvent;
import org.iuscl.forms.IusCLApplication;

/* **************************************************************************************************** */
public class IusCLTimer extends IusCLComponent {

	/* Properties */
	private Boolean enabled = false;
	private Integer interval = 1000;

	/* Events */
	private IusCLNotifyEvent onTimer = null;
	
	/* Fields */
	private Runnable swtTimer = null;

	/* **************************************************************************************************** */
	public IusCLTimer(IusCLComponent aOwner) {
		
		super(aOwner);

		/* Properties */
		defineProperty("Enabled", IusCLPropertyType.ptBoolean, "false");
		defineProperty("Interval", IusCLPropertyType.ptInteger, "1000");

		/* Events */
		defineProperty("OnTimer", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);

		/* Create */
	    swtTimer = new Runnable() {
	    	/* **************************************************************************************************** */
	    	public void run() {

	    		if (isInDesignMode == true) {
	    			
	    			return;
	    		}
	    		
				if (IusCLEvent.isDefinedEvent(onTimer)) {

					onTimer.invoke(IusCLTimer.this);
				}
				
				if (IusCLTimer.this.enabled == true) {
					
					IusCLApplication.getSwtDisplay().timerExec(interval, swtTimer);					
				}
	    	}
	    };
	}

	/* **************************************************************************************************** */
	public Boolean getEnabled() {
		return enabled;
	}

	/* **************************************************************************************************** */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		
		if (enabled == true) {
			
			IusCLApplication.getSwtDisplay().timerExec(interval, swtTimer);
		}
		else {
			
			IusCLApplication.getSwtDisplay().timerExec(-1, swtTimer);
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void free() {
		super.free();
		
		setEnabled(false);
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public IusCLNotifyEvent getOnTimer() {
		return onTimer;
	}

	public void setOnTimer(IusCLNotifyEvent onTimer) {
		this.onTimer = onTimer;
	}

}
