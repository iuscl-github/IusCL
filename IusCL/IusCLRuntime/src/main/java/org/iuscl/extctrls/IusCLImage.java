/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.extctrls;

import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLGraphicControl;
import org.iuscl.graphics.IusCLCanvas;
import org.iuscl.graphics.IusCLGraphic;
import org.iuscl.graphics.IusCLPicture;
import org.iuscl.sysutils.IusCLGraphUtils;
import org.iuscl.types.IusCLRectangle;

/* **************************************************************************************************** */
public class IusCLImage extends IusCLGraphicControl {

	/* Properties */
	private IusCLPicture picture = null;
	private Boolean center = false;
	private Boolean proportional = false;
	private Boolean stretch = false;

	/* Events */
	
	/* Fields */
	
	/* **************************************************************************************************** */
	public IusCLImage(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Center", IusCLPropertyType.ptBoolean, "false");
		defineProperty("Proportional", IusCLPropertyType.ptBoolean, "false");
		defineProperty("Stretch", IusCLPropertyType.ptBoolean, "false");

		defineProperty("Picture", IusCLPropertyType.ptPicture, "");

		/* Events */

		/* Create */

		/* TODO Much faster with SWT.NO_BACKGROUND but problems on tab page  */
		
//		swtCanvas.dispose();
//		swtCanvas = new Canvas(this.getFormSwtComposite(), SWT.NONE | SWT.NO_BACKGROUND);
//		setCanvas(new IusCLCanvas(swtCanvas));
		
		createWnd(swtCanvas);
	}

	/* **************************************************************************************************** */
	@Override
	protected void paint() {
		
		if (IusCLGraphUtils.isEmptyPicture(picture)) {
			if (isInDesignMode == true) {
				
				drawDesignBox();
			}
			return;
		}
		
		IusCLCanvas canvas = this.getCanvas();
		
		IusCLRectangle rectangle = null;
		IusCLGraphic graphic = picture.getGraphic();
		int pictureWidth = graphic.getWidth();
		int pictureHeight = graphic.getHeight();
		int canvasWidth = this.getWidth();
		int canvasHeight = this.getHeight();
		
		if (stretch == true) {
			
			rectangle = new IusCLRectangle(0, 0, canvasWidth, canvasHeight);
			canvas.stretchDraw(rectangle, graphic);
			return;
		}
		
		if (proportional == true) {
			
			if ((pictureWidth > canvasWidth) || (pictureHeight > canvasHeight)) {
				
				double imageProportion = (double)((double)pictureWidth / (double)pictureHeight);
				double canvasProportion = (double)((double)canvasWidth / (double)canvasHeight);

				int newLeft = 0;
				int newTop = 0;
				int newWidth = canvasWidth;
				int newHeight = canvasHeight;

				if (imageProportion > canvasProportion) {
					
					double reduceProportion = (double)((double)canvasWidth / (double)pictureWidth);
					newHeight = (int)(pictureHeight * reduceProportion);
					if (center == true) {
						newTop = (canvasHeight - newHeight) / 2;
					}
				}
				else {
					double reduceProportion = (double)((double)canvasHeight / (double)pictureHeight);
					newWidth = (int)(pictureWidth * reduceProportion);
					if (center == true) {
						newLeft = (canvasWidth - newWidth) / 2;
					}
				}
				
				rectangle = new IusCLRectangle(newLeft, newTop, newWidth, newHeight);
				canvas.stretchDraw(rectangle, graphic);
				
				return;
			}
		}

		if (center == true) {
			
			int x = (canvasWidth - pictureWidth) / 2;
			int y = (canvasHeight - pictureHeight) / 2;
			canvas.draw(x, y, graphic);
			return;
		}
		
		canvas.draw(0, 0, graphic);
	}

	/* **************************************************************************************************** */
	public IusCLPicture getPicture() {
		
		if (picture != null) {

			picture.setNotify(this, "setPicture");
		}
		return picture;
	}

	/* **************************************************************************************************** */
	public void setPicture(IusCLPicture picture) {
		this.picture = picture;

		repaint();
	}

	public Boolean getCenter() {
		return center;
	}

	/* **************************************************************************************************** */
	public void setCenter(Boolean center) {
		
		if (this.center != center) {
			this.center = center;
			
			repaint();
		}
	}

	public Boolean getProportional() {
		return proportional;
	}

	/* **************************************************************************************************** */
	public void setProportional(Boolean proportional) {

		if (this.proportional != proportional) {
			this.proportional = proportional;
			
			repaint();
		}
	}

	public Boolean getStretch() {
		return stretch;
	}

	/* **************************************************************************************************** */
	public void setStretch(Boolean stretch) {

		if (this.stretch != stretch) {
			this.stretch = stretch;
			
			repaint();
		}
	}

}
