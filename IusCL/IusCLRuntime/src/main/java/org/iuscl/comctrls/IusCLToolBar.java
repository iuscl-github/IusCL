/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;

/* **************************************************************************************************** */
public class IusCLToolBar extends IusCLWinControl {

	/* SWT */
	private ToolBar swtToolBar = null;

	/* Properties */
	private IusCLToolButtons buttons = null;
	private IusCLImageList images = null;
	private IusCLImageList hotImages = null;
	private IusCLImageList disabledImages = null;

	/* Events */
	
	/* Fields */
	
	/* **************************************************************************************************** */
	public IusCLToolBar(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		buttons = new IusCLToolButtons(this);
		defineProperty("Buttons", IusCLPropertyType.ptCollection, "", false);
		defineProperty("Images", IusCLPropertyType.ptComponent, "", IusCLImageList.class);
		defineProperty("HotImages", IusCLPropertyType.ptComponent, "", IusCLImageList.class);
		defineProperty("DisabledImages", IusCLPropertyType.ptComponent, "", IusCLImageList.class);
		defineProperty("AutoSize", IusCLPropertyType.ptBoolean, "true");
		
		/* Events */
		
		/* Collection */
		this.setSubCollection(buttons);
		buttons.setPropertyName("Buttons");
		
		/* Create */
		swtToolBar = new ToolBar(this.getFormSwtComposite(), SWT.WRAP);
		createWnd(swtToolBar);
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();
		
		this.removeProperty("Caption");
		
		this.setAutoSize(true);
	}

//	/* **************************************************************************************************** */
//	@Override
//	public Composite getSwtComposite() {
//		return swtToolBar;
//	}

	/* **************************************************************************************************** */
	public IusCLToolButtons getButtons() {
		return buttons;
	}

	public void setButtons(IusCLToolButtons buttons) {
		this.buttons = buttons;
	}

	public IusCLImageList getImages() {
		return images;
	}

	/* **************************************************************************************************** */
	public void setImages(IusCLImageList images) {
		this.images = images;
		
		for (int index = 0; index < buttons.getCount(); index++) {
				
			IusCLToolButton toolButton = (IusCLToolButton)buttons.get(index);
			toolButton.setImageIndex(toolButton.getImageIndex());
		}
	}

	public IusCLImageList getHotImages() {
		return hotImages;
	}

	/* **************************************************************************************************** */
	public void setHotImages(IusCLImageList hotImages) {
		this.hotImages = hotImages;
		
		for (int index = 0; index < buttons.getCount(); index++) {
				
			IusCLToolButton toolButton = (IusCLToolButton)buttons.get(index);
			toolButton.setHotImageIndex(toolButton.getHotImageIndex());
		}
	}

	public IusCLImageList getDisabledImages() {
		return disabledImages;
	}

	/* **************************************************************************************************** */
	public void setDisabledImages(IusCLImageList disabledImages) {
		this.disabledImages = disabledImages;
		
		for (int index = 0; index < buttons.getCount(); index++) {
				
			IusCLToolButton toolButton = (IusCLToolButton)buttons.get(index);
			toolButton.setDisabledImageIndex(toolButton.getDisabledImageIndex());
		}
	}

	/* **************************************************************************************************** */
	@Override
	public Boolean getAutoSize() {
		
		return super.getAutoSize();
	}

	/* **************************************************************************************************** */
	@Override
	public void setAutoSize(Boolean autoSize) {
		
		super.setAutoSize(autoSize);
	}

	/* **************************************************************************************************** */
	@Override
	public void doAutoSizeParentAlignControls() {
		
		super.doAutoSizeParentAlignControls();
	}

}
