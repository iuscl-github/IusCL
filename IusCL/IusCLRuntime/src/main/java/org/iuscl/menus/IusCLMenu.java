/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.menus;

import org.eclipse.swt.widgets.Menu;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.comctrls.IusCLImageList;

/* **************************************************************************************************** */
public class IusCLMenu extends IusCLComponent {

	/* SWT */
	protected Menu swtMenu = null;

	/* Properties */
	private IusCLImageList images = null;
	
	/* Events */

	/* Fields */

	/* **************************************************************************************************** */
	public IusCLMenu(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Images", IusCLPropertyType.ptComponent, "", IusCLImageList.class);
		
		/* Events */
	}

	/* **************************************************************************************************** */
	public Menu getSwtMenu() {
		return swtMenu;
	}

	public void setSwtMenu(Menu swtMenu) {
		this.swtMenu = swtMenu;
	}

	/* **************************************************************************************************** */
	public Integer getItemCount() {
		
		return this.getChildren().size();
	}

	/* **************************************************************************************************** */
	public IusCLMenuItem getItem(Integer index) {
		
		return (IusCLMenuItem)this.getChildren().get(index);
	}

	public IusCLImageList getImages() {
		return images;
	}

	/* **************************************************************************************************** */
	public void setImages(IusCLImageList images) {
		this.images = images;
		
		for (int index = 0; index < this.getItemCount(); index++) {

			IusCLMenuItem menuItem = getItem(index);
			menuItem.setImageIndex(menuItem.getImageIndex());
			recursivePutImageIndex(menuItem);
		}
	}

	/* **************************************************************************************************** */
	private void recursivePutImageIndex(IusCLMenuItem menuItem) {

		for (int index = 0; index < menuItem.getItemCount(); index++) {
			
			IusCLMenuItem subMenuItem = menuItem.getItem(index);
			subMenuItem.setImageIndex(subMenuItem.getImageIndex());
			recursivePutImageIndex(subMenuItem);
		}
	}
}
