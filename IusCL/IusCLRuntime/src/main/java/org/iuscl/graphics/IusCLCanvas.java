/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.graphics;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.iuscl.classes.IusCLPersistent;
import org.iuscl.types.IusCLPoint;
import org.iuscl.types.IusCLRectangle;
import org.iuscl.types.IusCLSize;

/* **************************************************************************************************** */
public class IusCLCanvas extends IusCLPersistent {

	/* Properties */
	private GC swtGC = null;
	private IusCLPoint penPos = new IusCLPoint(0, 0);
	private IusCLPen pen = null;
	private IusCLBrush brush = null;
	private IusCLFont font = null;

	/* **************************************************************************************************** */
	public IusCLCanvas(GC swtGC) {
		
		this.swtGC = swtGC; 
		
		pen = new IusCLPen(this);
		brush = new IusCLBrush(this);
		font = new IusCLFont();
		font.setSwtFont(swtGC.getFont());
	}

	/* **************************************************************************************************** */
	public IusCLCanvas(Drawable swtDrawable) {
		
		this(new GC(swtDrawable));
	}

	/* **************************************************************************************************** */
	public void moveTo(Integer x, Integer y) {
		
		penPos.setX(x);
		penPos.setY(y);
	}

	/* **************************************************************************************************** */
	public void lineTo(Integer x, Integer y) {

		swtGC.drawLine(penPos.getX(), penPos.getY(), x, y);
		
		penPos.setX(x);
		penPos.setY(y);
	}

	/* **************************************************************************************************** */
	public void draw(Integer x, Integer y, IusCLGraphic graphic) {
		
		swtGC.drawImage(graphic.getSwtImage(), x, y);
	}

	/* **************************************************************************************************** */
	public void stretchDraw(IusCLRectangle rect, IusCLGraphic graphic) {

		IusCLGraphic scaledGraphic = graphic.resizeGraphic(rect.getWidth(), rect.getHeight());
		swtGC.drawImage(scaledGraphic.getSwtImage(), rect.getLeft(), rect.getTop());
	}

	/* **************************************************************************************************** */
	public GC getGC() {
		
		return swtGC;
	}

	public IusCLPoint getPenPos() {
		return penPos;
	}

	public void setPenPos(IusCLPoint penPos) {
		this.penPos = penPos;
	}

	public IusCLPen getPen() {
		return pen;
	}

	public void setPen(IusCLPen pen) {
		this.pen = pen;
	}

	public IusCLBrush getBrush() {
		return brush;
	}

	public void setBrush(IusCLBrush brush) {
		this.brush = brush;
	}

	/* **************************************************************************************************** */
	public IusCLFont getFont() {
		
		font.setNotify(this, "setFont");
		
		return font;
	}

	/* **************************************************************************************************** */
	public void setFont(IusCLFont font) {

		this.font = font;

		swtGC.setFont(font.getSwtFont());
	}

	/* **************************************************************************************************** */
	public void roundRect(Integer X1, Integer Y1, Integer X2, Integer Y2, Integer X3, Integer Y3) {
		
		roundRect(X1, Y1, X2, Y2, X3, Y3, true);
	}

	/* **************************************************************************************************** */
	public void roundRect(Integer X1, Integer Y1, Integer X2, Integer Y2, Integer X3, Integer Y3,
			Boolean filled) {

		if (filled == true) {

			swtGC.fillRoundRectangle(X1, Y1, X2 - X1, Y2 - Y1, X3, Y3);
		}
		swtGC.drawRoundRectangle(X1, Y1, X2 - X1, Y2 - Y1, X3, Y3);
	}

	/* **************************************************************************************************** */
	public void textOut(Integer X, Integer Y, String text) {
		
		Color oldColor = swtGC.getForeground();
		swtGC.setForeground(font.getColor().getAsSwtColor());
		swtGC.drawString(text, X, Y, true);
		swtGC.setForeground(oldColor);

		//swtGC.fillPolygon(pointArray)
		//		swtGC.fillRoundRectangle(X1, Y1, X2 - X1, Y2 - Y1, X3, Y3);
	}

	/* **************************************************************************************************** */
	public IusCLSize textExtent(String text) {
		
		Point swtPoint = swtGC.textExtent(text);
		
		return new IusCLSize(swtPoint.x, swtPoint.y);
	}

}
