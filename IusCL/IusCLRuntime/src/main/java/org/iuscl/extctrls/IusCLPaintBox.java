/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.extctrls;

import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLGraphicControl;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLNotifyEvent;
import org.iuscl.graphics.IusCLColor;

/* **************************************************************************************************** */
public class IusCLPaintBox extends IusCLGraphicControl {

	/* Properties */
	
	/* Events */
	private IusCLNotifyEvent onPaint = null;
	
	/* Fields */
	
	/* **************************************************************************************************** */
	public IusCLPaintBox(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		this.removeProperty("Caption");
		
		this.removeProperty("Enabled");

//		this.removeProperty("Font");
//		this.removeProperty("ParentFont");
//		IusCLFont.removeFontProperties(this, "Font");
		
		/* Events */
		defineProperty("OnPaint", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);

		/* Create */
		createWnd(swtCanvas);
	}

	/* **************************************************************************************************** */
	@Override
	protected void paint() {
		
		if (isInDesignMode == true) {
			
			drawDesignBox();
			return;
		}
		
		if (IusCLEvent.isDefinedEvent(onPaint)) {

			onPaint.invoke(IusCLPaintBox.this);
		}
	}
	
	/* **************************************************************************************************** */
	@Override
	public void setColor(IusCLColor color) {
		super.setColor(color);
		
		paint();
	}

	/* **************************************************************************************************** */
	public IusCLNotifyEvent getOnPaint() {
		return onPaint;
	}

	public void setOnPaint(IusCLNotifyEvent onPaint) {
		this.onPaint = onPaint;
	}

}
