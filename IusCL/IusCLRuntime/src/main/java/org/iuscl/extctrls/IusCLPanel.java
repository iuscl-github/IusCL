/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.extctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLContainerControl;
import org.iuscl.graphics.IusCLCanvas;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLColor.IusCLStandardColors;
import org.iuscl.sysutils.IusCLGraphUtils;
import org.iuscl.types.IusCLSize;

/* **************************************************************************************************** */
public class IusCLPanel extends IusCLContainerControl {

	public enum IusCLPanelBevel { bvNone, bvLowered, bvRaised, bvSpace };
	
	/* SWT */
	private Composite swtPanelComposite = null;
	
	/* Properties */
	private IusCLBorderStyle borderStyle = IusCLBorderStyle.bsNone;

	private IusCLPanelBevel bevelInner = IusCLPanelBevel.bvNone;
	private IusCLPanelBevel bevelOuter = IusCLPanelBevel.bvRaised;
	private Integer bevelWidth = 1;

	private Boolean systemBevelColors = true;

	/* Events */
	
	/* **************************************************************************************************** */
	public IusCLPanel(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		defineProperty("BorderStyle", IusCLPropertyType.ptEnum, "bsNone", IusCLBorderStyle.bsNone);

		defineProperty("BevelInner", IusCLPropertyType.ptEnum, "bvNone", IusCLPanelBevel.bvNone);
		defineProperty("BevelOuter", IusCLPropertyType.ptEnum, "bvRaised", IusCLPanelBevel.bvRaised);
		defineProperty("BevelWidth", IusCLPropertyType.ptInteger, "1");
		
		defineProperty("SystemBevelColors", IusCLPropertyType.ptBoolean, "true");

		/* Events */
		
		/* Create */
		createWnd(createSwtControl());
	}

	/* **************************************************************************************************** */
	@Override
	protected Control createSwtControl() {

		int swtCreateParams = SWT.NONE;
		
		if (borderStyle == IusCLBorderStyle.bsSingle) {
			
			swtCreateParams = swtCreateParams | SWT.BORDER;
		}

		swtPanelComposite = new Composite(this.getFormSwtComposite(), swtCreateParams);
		
		return swtPanelComposite;
	}

	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.removeProperty("Caption");
		
		this.getProperty("Height").setDefaultValue("41");
		setHeight(41);
		this.getProperty("Width").setDefaultValue("185");
		setWidth(185);
	}

	/* **************************************************************************************************** */
	@Override
	protected void reCreate() {
		super.reCreate();

		swtPanelComposite.addPaintListener(new PaintListener() {
			/* **************************************************************************************************** */
			@Override
			public void paintControl(PaintEvent paintEvent) {

				Boolean drawOuter = (bevelOuter == IusCLPanelBevel.bvRaised) ||
					(bevelOuter == IusCLPanelBevel.bvLowered);

				Boolean drawInner = (bevelInner == IusCLPanelBevel.bvRaised) ||
					(bevelInner == IusCLPanelBevel.bvLowered);

				if (drawOuter || drawInner) {
					
					IusCLCanvas canvas = new IusCLCanvas(paintEvent.gc);
					
					IusCLColor colorHighlight = new IusCLColor(IusCLStandardColors.clBtnHighlight);
					IusCLColor colorShadow = new IusCLColor(IusCLStandardColors.clBtnShadow);
					
					if (systemBevelColors == false) {
						
						colorHighlight = IusCLGraphUtils.getHighlightColor(getColor());
						colorShadow = IusCLGraphUtils.getShadowColor(getColor());
					}

					IusCLColor color1 = colorHighlight;
					IusCLColor color2 = colorShadow;

					Integer bevelLeft = 0;
					Integer bevelTop = 0;
					Integer bevelWidth = swtPanelComposite.getClientArea().width - 1;
					Integer bevelHeight = swtPanelComposite.getClientArea().height - 1;
					Integer bevelBorder = IusCLPanel.this.bevelWidth;

					if (drawOuter) {

						if (bevelOuter == IusCLPanelBevel.bvLowered) {
							
							color1 = colorShadow;
							color2 = colorHighlight;
						}

						drawBevel(canvas, color1, color2, bevelLeft, bevelTop, bevelWidth, bevelHeight, bevelBorder);

						bevelLeft = bevelLeft + bevelBorder;
						bevelTop = bevelTop + bevelBorder;
						bevelWidth = bevelWidth - bevelBorder;
						bevelHeight = bevelHeight - bevelBorder;
					}
					
					if (drawInner) {

						color1 = colorHighlight;
						color2 = colorShadow;
						if (bevelInner == IusCLPanelBevel.bvLowered) {
							
							color1 = colorShadow;
							color2 = colorHighlight;
						}
						
						Integer borderWidth = getBorderWidth();
						
						bevelLeft = bevelLeft + borderWidth;
						bevelTop = bevelTop + borderWidth;
						bevelWidth = bevelWidth - borderWidth;
						bevelHeight = bevelHeight - borderWidth;
						
						drawBevel(canvas, color1, color2, bevelLeft, bevelTop, bevelWidth, bevelHeight, bevelBorder);
					}
				}
			}
		});
	}
	
	/* **************************************************************************************************** */
	private void drawBevel(IusCLCanvas canvas, IusCLColor color1, IusCLColor color2, 
		Integer bevelLeft, Integer bevelTop, Integer bevelWidth, Integer bevelHeight, Integer bevelBorder) {
		
		for (int border = 0; border < bevelBorder; border++) {
			
			canvas.getPen().setColor(color1);
			canvas.moveTo(bevelLeft, bevelTop);
			canvas.lineTo(bevelWidth - 1, bevelTop);
			canvas.moveTo(bevelLeft, bevelTop);
			canvas.lineTo(bevelLeft, bevelHeight);
			
			canvas.getPen().setColor(color2);
			canvas.moveTo(bevelLeft + 1, bevelHeight);
			canvas.lineTo(bevelWidth, bevelHeight);
			canvas.lineTo(bevelWidth, bevelTop);
			
			bevelLeft = bevelLeft + 1;
			bevelTop = bevelTop + 1;
			
			bevelWidth = bevelWidth - 1;
			bevelHeight = bevelHeight - 1;
		}
	}
	
	/* **************************************************************************************************** */
	@Override
	public Composite getSwtComposite() {
		return this.swtPanelComposite;
	}

	/* **************************************************************************************************** */
	public IusCLBorderStyle getBorderStyle() {
		return borderStyle;
	}

	/* **************************************************************************************************** */
	public void setBorderStyle(IusCLBorderStyle borderStyle) {

		if (this.borderStyle != borderStyle) {
			
			this.borderStyle = borderStyle;
			
			reCreateWnd();		
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void setBorderWidth(Integer borderWidth) {

		super.setBorderWidth(borderWidth);
		swtPanelComposite.redraw();
	}

	/* **************************************************************************************************** */
	@Override
	public Integer getContainerBorderWidth() {
		
		int borderWidth = super.getContainerBorderWidth();
		
		if (bevelInner != IusCLPanelBevel.bvNone) {
			borderWidth = borderWidth + bevelWidth;
		}
		if (bevelOuter != IusCLPanelBevel.bvNone) {
			borderWidth = borderWidth + bevelWidth;
		}
		
		return borderWidth;
	}

	public Integer getBevelWidth() {
		return bevelWidth;
	}

	/* **************************************************************************************************** */
	public void setBevelWidth(Integer bevelWidth) {

		this.bevelWidth = bevelWidth;
		
		doAlignControls(new IusCLSize(0, 0));
		swtPanelComposite.redraw();
	}

	public IusCLPanelBevel getBevelInner() {
		return bevelInner;
	}

	/* **************************************************************************************************** */
	public void setBevelInner(IusCLPanelBevel bevelInner) {
		
		Boolean needsClientResize = needsClientResize(bevelInner, this.bevelInner);
		
		this.bevelInner = bevelInner;
		
		swtPanelComposite.redraw();
		
		if (needsClientResize) {
			
			doAlignControls(new IusCLSize(0, 0));
		}
	}

	public IusCLPanelBevel getBevelOuter() {
		return bevelOuter;
	}

	/* **************************************************************************************************** */
	public void setBevelOuter(IusCLPanelBevel bevelOuter) {
		
		Boolean needsClientResize = needsClientResize(bevelOuter, this.bevelOuter);
		
		this.bevelOuter = bevelOuter;

		swtPanelComposite.redraw();
		
		if (needsClientResize) {
			
			doAlignControls(new IusCLSize(0, 0));
		}
	}

	public Boolean getSystemBevelColors() {
		return systemBevelColors;
	}

	public void setSystemBevelColors(Boolean systemBevelColors) {
		
		this.systemBevelColors = systemBevelColors;
		
		swtPanelComposite.redraw();
	}

	/* **************************************************************************************************** */
	private Boolean needsClientResize(IusCLPanelBevel panelBevel, IusCLPanelBevel oldPanelBevel) {
		
		if ((panelBevel == IusCLPanelBevel.bvNone) && (oldPanelBevel != IusCLPanelBevel.bvNone)) {
			return true;
		}

		if ((panelBevel != IusCLPanelBevel.bvNone) && (oldPanelBevel == IusCLPanelBevel.bvNone)) {
			return true;
		}

		return false;
	}
}
