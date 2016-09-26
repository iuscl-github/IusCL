/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allcomponents.iuscl;

import java.util.EnumSet;

import org.iuscl.comctrls.IusCLPageControl;
import org.iuscl.comctrls.IusCLTabSheet;
import org.iuscl.extctrls.IusCLBevel;
import org.iuscl.extctrls.IusCLImage;
import org.iuscl.extctrls.IusCLPaintBox;
import org.iuscl.extctrls.IusCLShape;
import org.iuscl.forms.IusCLForm;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLColor.IusCLStandardColors;
import org.iuscl.graphics.formats.IusCLGif;
import org.iuscl.stdctrls.IusCLLabel;
import org.iuscl.system.IusCLObject;
import org.iuscl.types.IusCLPoint;
import org.iuscl.stdctrls.IusCLButton;
import org.iuscl.dialogs.IusCLOpenPictureDialog;

/* **************************************************************************************************** */
public class FormIusCLGraphicControl extends IusCLForm {

	/* IusCL Components */
	public IusCLLabel label4;
	public IusCLPageControl pageControlGr;
	public IusCLTabSheet tabSheetBevel;
	public IusCLTabSheet tabSheet2;
	public IusCLBevel bevel1;
	public IusCLBevel bevel2;
	public IusCLTabSheet tabSheet1;
	public IusCLTabSheet tabSheet3;
	public IusCLImage image1;
	public IusCLImage image2;
	public IusCLPaintBox paintBox1;
	public IusCLPaintBox paintBox2;
	public IusCLShape shape1;

	public IusCLTabSheet tabSheet4;

	public IusCLButton button1;

	public IusCLImage image3;

	public IusCLImage image4;

	public IusCLImage image5;

	public IusCLOpenPictureDialog openPictureDialog1;

	/** paintBox2.OnPaint event implementation */
	public void paintBox2Paint(IusCLObject sender) {

		paintBox2.getCanvas().setPenPos(new IusCLPoint(1, 1));
		paintBox2.getCanvas().lineTo(paintBox2.getWidth() / 2, 30);
	}
	/** paintBox1.OnPaint event implementation */
	public void paintBox1Paint(IusCLObject sender) {

		paintBox1.getCanvas().getPen().setColor(new IusCLColor(IusCLStandardColors.clWhite));
		paintBox1.getCanvas().setPenPos(new IusCLPoint(1, 1));
		paintBox1.getCanvas().lineTo(paintBox1.getWidth() / 2, 100);
		
	}
	/** shape1.OnClick event implementation */
	public void shape1Click(IusCLObject sender) {

		System.out.println("shape1Click");
	}
	/** shape1.OnMouseDown event implementation */
	public void shape1MouseDown(IusCLObject sender, 
		IusCLMouseButton button, EnumSet<IusCLShiftState> shift, Integer x, Integer y) {

		System.out.println("shape1MouseDown");
	}
	/** shape1.OnContextPopup event implementation */
	public Boolean shape1ContextPopup(IusCLObject sender, Integer x, Integer y) {

		System.out.println("shape1ContextPopup");
		return true;
	}
	/** bevel2.OnMouseEnter event implementation */
	public void bevel2MouseEnter(IusCLObject sender) {

		System.out.println("bevel2MouseEnter");
	}
	/** shape1.OnMouseHover event implementation */
	public void shape1MouseHover(IusCLObject sender, Integer x, Integer y) {

		System.out.println("shape1MouseHover");
	}
	/** button1.OnClick event implementation */
	public void button1Click(IusCLObject sender) {

		if (openPictureDialog1.execute()) {
			
			String fileName = openPictureDialog1.getFileName();
			
			image3.getPicture().loadFromFile(fileName);
			
			IusCLGif gif = (IusCLGif) image3.getPicture().getGraphic();
			
			image4.getPicture().setGraphic(gif.get(1));
		}
	}

}