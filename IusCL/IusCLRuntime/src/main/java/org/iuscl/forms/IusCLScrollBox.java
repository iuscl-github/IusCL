/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLContainerControl;
import org.iuscl.controls.IusCLControl;
import org.iuscl.graphics.IusCLColor;

/* **************************************************************************************************** */
public class IusCLScrollBox extends IusCLContainerControl {

	/* SWT */
	private ScrolledComposite swtScrolledComposite = null;
	private Composite swtContainedComposite = null;
	
	/* Properties */
	private IusCLBorderStyle borderStyle = IusCLBorderStyle.bsSingle;

	/* Events */
	
	/* **************************************************************************************************** */
	public IusCLScrollBox(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		defineProperty("BorderStyle", IusCLPropertyType.ptEnum, "bsSingle", IusCLBorderStyle.bsSingle);

		/* Events */
		
		/* Create */
		createWnd(createSwtControl());
	}
	
	/* **************************************************************************************************** */
	@Override
	protected Control createSwtControl() {

		int swtCreateParams = SWT.H_SCROLL | SWT.V_SCROLL;
		
		if (borderStyle == IusCLBorderStyle.bsSingle) {
			
			swtCreateParams = swtCreateParams | SWT.BORDER;
		}

		swtScrolledComposite = new ScrolledComposite(this.getFormSwtComposite(), swtCreateParams);
		
		swtScrolledComposite.addControlListener(new ControlAdapter() {

			/* **************************************************************************************************** */
			@Override
			public void controlResized(ControlEvent swtControlEvent) {
				
				update();
			}
		});
		
		swtContainedComposite = new Composite(swtScrolledComposite, SWT.NONE);
		swtScrolledComposite.setContent(swtContainedComposite);
		
		/* Container to be scrolled */
		swtContainedComposite.setBounds(swtScrolledComposite.getClientArea());
		swtContainedComposite.setData(this);
		
		transferSwtListeners(swtContainedComposite, swtScrolledComposite);
		
		swtContainedComposite.addPaintListener(new PaintListener() {
			/* **************************************************************************************************** */
			@Override
			public void paintControl(PaintEvent swtPaintEvent) {
				
				update();
			}
		});
		
		return swtScrolledComposite;
	}

	/* **************************************************************************************************** */
	private void update() {
		
		int packWidth = 0;
		int packHeight = 0;
		for (int index = 0; index < IusCLScrollBox.this.getControls().size(); index++) {
			
			IusCLControl childControl = IusCLScrollBox.this.getControls().get(index);
			int controlRight = childControl.getLeft() + childControl.getWidth();
			if (packWidth < controlRight) {
				
				packWidth = controlRight;
			}
			int controlDown = childControl.getTop() + childControl.getHeight();
			if (packHeight < controlDown) {
				
				packHeight = controlDown;
			}
		}

		int visibleWidth = swtScrolledComposite.getClientArea().width;
		int visibleHeight = swtScrolledComposite.getClientArea().height;
		
		int newWidth = visibleWidth;
		int newHeight = visibleHeight;
		
		if (packWidth > visibleWidth) {
			
			newWidth = packWidth;
		}

		if (packHeight > visibleHeight) {
			
			newHeight = packHeight;
		}
		
		if ((swtContainedComposite.getBounds().width != newWidth) ||
				(swtContainedComposite.getBounds().height != newHeight)) {
			
			swtContainedComposite.setSize(newWidth, newHeight);
		}
		
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.getProperty("Height").setDefaultValue("185");
		setHeight(185);
		this.getProperty("Width").setDefaultValue("185");
		setWidth(185);
	}

	/* **************************************************************************************************** */
	@Override
	public Composite getSwtComposite() {
		return swtContainedComposite;
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
	public void setColor(IusCLColor color) {
		super.setColor(color);
		
		if (this.getParentColor() == true) {
			
			swtContainedComposite.setBackground(null);	
		}
		else {
			
			swtContainedComposite.setBackground(swtScrolledComposite.getBackground());
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void setParentColor(Boolean parentColor) {
		super.setParentColor(parentColor);
		
		if (parentColor == true) {
			
			swtContainedComposite.setBackground(null);	
		}
		else {
			
			swtContainedComposite.setBackground(swtScrolledComposite.getBackground());
		}
	}
	
}
