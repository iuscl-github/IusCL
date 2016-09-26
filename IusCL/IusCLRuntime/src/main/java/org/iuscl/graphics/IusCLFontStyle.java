/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.graphics;

import org.iuscl.system.IusCLObject;

/* **************************************************************************************************** */
public class IusCLFontStyle extends IusCLObject {

	private Boolean bold = false;
	private Boolean italic = false; 
	private Boolean underline = false; 
	private Boolean strikeOut = false;
	
	/* **************************************************************************************************** */
	public Boolean getBold() {
		return bold;
	}
	
	public void setBold(Boolean bold) {
		this.bold = bold;
		
		invokeNotify();
	}
	
	public Boolean getItalic() {
		return italic;
	}
	
	public void setItalic(Boolean italic) {
		this.italic = italic;
		
		invokeNotify();
	}
	
	public Boolean getUnderline() {
		return underline;
	}
	
	public void setUnderline(Boolean underline) {
		this.underline = underline;
		
		invokeNotify();
	}
	
	public Boolean getStrikeOut() {
		return strikeOut;
	}
	
	public void setStrikeOut(Boolean strikeOut) {
		this.strikeOut = strikeOut;
		
		invokeNotify();
	}
	
}
