/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.iuscl.classes.IusCLCollectionItem;

/* **************************************************************************************************** */
public class IusCLWindowsStatusPanel extends IusCLCollectionItem {

	/* Properties */
	private String text = "";
	private Integer width = 50;
	private Integer imageIndex = -1;
	
	/* Events */

	/* Fields */
	IusCLWindowsStatusBar windowsStatusBar = null; 
			
	/* **************************************************************************************************** */
	public IusCLWindowsStatusPanel(IusCLWindowsStatusPanels statusPanels) {
		
		this(statusPanels, null);
	}

	/* **************************************************************************************************** */
	public IusCLWindowsStatusPanel(IusCLWindowsStatusPanels statusPanels, Integer index) {
		super(statusPanels);

		/* Properties */
		defineProperty("Text", IusCLPropertyType.ptString, "");
		defineProperty("Width", IusCLPropertyType.ptInteger, "50");
		defineProperty("ImageIndex", IusCLPropertyType.ptInteger, "-1");
		
		/* Events */

		/* Create */
		windowsStatusBar = statusPanels.getWindowsStatusBar();
		windowsStatusBar.update();
	}

	/* **************************************************************************************************** */
	public IusCLWindowsStatusPanels getWindowsStatusPanels() {
		
		return (IusCLWindowsStatusPanels)getCollection();
	}

	/* **************************************************************************************************** */
	@Override
	public String getDisplayName() {

		return text;
	}

	/* **************************************************************************************************** */
	public String getText() {
		
		return text;
	}

	/* **************************************************************************************************** */
	public void setText(String text) {
		this.text = text;

		windowsStatusBar.update();
	}
	
	/* **************************************************************************************************** */
	public Integer getWidth() {
		
		return width;
	}

	/* **************************************************************************************************** */
	public void setWidth(Integer width) {
		this.width = width;
		
		windowsStatusBar.update();
	}
	
	public Integer getImageIndex() {
		
		return imageIndex;
	}

	/* **************************************************************************************************** */
	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;

		windowsStatusBar.update();
	}

}
