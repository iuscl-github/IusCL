/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.types;

/* **************************************************************************************************** */
public class IusCLMargins {

	private Integer left = 0;
	private Integer right = 0;
	private Integer top = 0;
	private Integer bottom = 0;
	
	public IusCLMargins(Integer left, Integer right, Integer top, Integer bottom) {
		super();
		
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	/* **************************************************************************************************** */
	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getRight() {
		return right;
	}

	public void setRight(Integer right) {
		this.right = right;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getBottom() {
		return bottom;
	}

	public void setBottom(Integer bottom) {
		this.bottom = bottom;
	}

}
