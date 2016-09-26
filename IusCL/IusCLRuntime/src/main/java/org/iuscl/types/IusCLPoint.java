/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.types;

/* **************************************************************************************************** */
public class IusCLPoint {

	private Integer x = 0;
	private Integer y = 0;

	/* **************************************************************************************************** */
	public IusCLPoint() {
		super();
	}

	/* **************************************************************************************************** */
	public IusCLPoint(Integer x, Integer y) {
		super();
		this.x = x;
		this.y = y;
	}

	/* **************************************************************************************************** */
	public void setXY(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	/* **************************************************************************************************** */
	public Integer getX() {
		return x;
	}
	
	public void setX(Integer x) {
		this.x = x;
	}
	
	public Integer getY() {
		return y;
	}
	
	public void setY(Integer y) {
		this.y = y;
	}
	
}
