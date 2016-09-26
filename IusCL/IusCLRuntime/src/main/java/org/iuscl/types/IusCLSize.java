/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.types;

/* **************************************************************************************************** */
public class IusCLSize {

	private Integer width = 0;
	private Integer height = 0;

	/* **************************************************************************************************** */
	public IusCLSize() {
		super();
	}

	/* **************************************************************************************************** */
	public IusCLSize(Integer width, Integer height) {
		super();
		this.width = width;
		this.height = height;
	}

	/* **************************************************************************************************** */
	public void setWidthHeight(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}

	/* **************************************************************************************************** */
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