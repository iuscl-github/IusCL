/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.types;

/* **************************************************************************************************** */
public class IusCLRectangle {

	private Integer left = 0;
	private Integer top = 0;
	private Integer width = 0;
	private Integer height = 0;
	
	public IusCLRectangle(Integer left, Integer top, Integer width, Integer height) {
		super();
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	/* **************************************************************************************************** */
	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}
	
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
