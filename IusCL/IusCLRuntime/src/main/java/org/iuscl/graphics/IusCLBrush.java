/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.graphics;

import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;
import org.iuscl.sysutils.IusCLGraphUtils;

/* **************************************************************************************************** */
public class IusCLBrush extends IusCLGraphicsObject {

	public enum IusCLBrushStyle { bsSolid, bsClear, bsHorizontal, bsVertical, bsFDiagonal, 
		bsBDiagonal, bsCross, bsDiagCross }
	
	private IusCLPicture picture = new IusCLPicture();
	private IusCLColor color = new IusCLColor();

	private IusCLBrushStyle style = IusCLBrushStyle.bsSolid;

	/* **************************************************************************************************** */
	public IusCLBrush(IusCLCanvas canvas) {

		this.canvas = canvas;
		
		setColor(color);
		setStyle(style);
	}

	public IusCLPicture getPicture() {
		return picture;
	}
	
	/* **************************************************************************************************** */
	public void setPicture(IusCLPicture picture) {
		this.picture = picture;
		
		if (!IusCLGraphUtils.isEmptyPicture(picture)) {

			canvas.getGC().setBackgroundPattern(new Pattern(Display.getDefault(), picture.getGraphic().getSwtImage()));
		}
	}

	public IusCLColor getColor() {
		return color;
	}

	/* **************************************************************************************************** */
	public void setColor(IusCLColor color) {
		this.color = color;
		
		canvas.getGC().setBackground(color.getAsSwtColor());
	}

	public IusCLBrushStyle getStyle() {
		return style;
	}

	/* **************************************************************************************************** */
	public void setStyle(IusCLBrushStyle style) {
		this.style = style;
		
		switch (style) {
		case bsBDiagonal:
			//canvas.getGC()
			break;
		case bsClear:
			//canvas.getGC()
			break;
		case bsCross:
			//canvas.getGC()
			break;
		case bsDiagCross:
			//canvas.getGC()
			break;
		case bsFDiagonal:
			//canvas.getGC()
			break;
		case bsHorizontal:
			//canvas.getGC()
			break;
		case bsSolid:
			//canvas.getGC()
			break;
		case bsVertical:
			//canvas.getGC()
			break;
		}
	}
	
}
