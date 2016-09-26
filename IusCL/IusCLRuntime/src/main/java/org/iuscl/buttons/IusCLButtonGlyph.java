/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.buttons;

import org.iuscl.graphics.IusCLPicture;

/* **************************************************************************************************** */
public class IusCLButtonGlyph {

	private IusCLPicture up = null;
	private IusCLPicture disabled = null;
	private IusCLPicture clicked = null;
	private IusCLPicture down = null;
	
	/* **************************************************************************************************** */
	public IusCLPicture getUp() {
		return up;
	}
	public void setUp(IusCLPicture up) {
		this.up = up;
	}
	public IusCLPicture getDisabled() {
		return disabled;
	}
	public void setDisabled(IusCLPicture disabled) {
		this.disabled = disabled;
	}
	public IusCLPicture getClicked() {
		return clicked;
	}
	public void setClicked(IusCLPicture clicked) {
		this.clicked = clicked;
	}
	public IusCLPicture getDown() {
		return down;
	}
	public void setDown(IusCLPicture down) {
		this.down = down;
	}
}
