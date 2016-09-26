/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.controls;

import org.iuscl.classes.IusCLPersistent;

/* **************************************************************************************************** */
public class IusCLSizeConstraints extends IusCLPersistent {

	private Integer maxHeight = 0;
	private Integer maxWidth = 0;
	private Integer minHeight = 0;
	private Integer minWidth = 0;
	
	/* **************************************************************************************************** */
	public Integer getMaxHeight() {
		return maxHeight;
	}
	
	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}
	
	public Integer getMaxWidth() {
		return maxWidth;
	}
	
	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}
	
	public Integer getMinHeight() {
		return minHeight;
	}
	
	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}
	
	public Integer getMinWidth() {
		return minWidth;
	}
	
	public void setMinWidth(Integer minWidth) {
		this.minWidth = minWidth;
	}
}
