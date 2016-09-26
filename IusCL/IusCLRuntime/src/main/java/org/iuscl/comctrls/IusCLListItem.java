/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.iuscl.classes.IusCLCollectionItem;

/* **************************************************************************************************** */
public class IusCLListItem extends IusCLCollectionItem {

	/* SWT */
	private TableItem swtTableItem = null;

	/* Properties */
	private String caption = "";
	private Integer imageIndex = -1;
	private IusCLListSubItems subItems = null;
	
	/* Events */

	/* **************************************************************************************************** */
	public IusCLListItem(IusCLListItems listItems) {
		
		this(listItems, null);
	}

	/* **************************************************************************************************** */
	public IusCLListItem(IusCLListItems listItems, Integer index) {
		super(listItems, index);
		
		/* Properties */
		defineProperty("Caption", IusCLPropertyType.ptString, "");
		defineProperty("ImageIndex", IusCLPropertyType.ptInteger, "-1");
		
		/* Events */
		
		/* Create */
		IusCLListView listView = listItems.getPropertyComponent();
		Table swtTable = (Table)listView.getSwtControl();

		subItems = new IusCLListSubItems(this);
		
		if (index == null) {

			swtTableItem = new TableItem(swtTable, SWT.NONE);
		}
		else {
			
			swtTableItem = new TableItem(swtTable, SWT.NONE, index);	
		}
		assign();
	}

	/* **************************************************************************************************** */
	public IusCLListItems getListItems() {
		
		return (IusCLListItems)getCollection();
	}

	/* **************************************************************************************************** */
	@Override
	public void free() {
		super.free();
		
		if (swtTableItem != null) {
			
			swtTableItem.dispose();
		}
	}

	/* **************************************************************************************************** */
	@Override
	public String getDisplayName() {

		return caption;
	}

	public String getCaption() {
		return caption;
	}

	/* **************************************************************************************************** */
	public void setCaption(String caption) {
		this.caption = caption;
		
		swtTableItem.setText(caption);
	}

	public Integer getImageIndex() {
		return imageIndex;
	}

	/* **************************************************************************************************** */
	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
		
		if (imageIndex == -1) {
			swtTableItem.setImage((Image)null);
			return;
		}
		
		IusCLListItems listItems = getListItems();
		IusCLListView listView = listItems.getPropertyComponent();

		if (swtTableItem != null) {
			
			IusCLImageList images = listView.getSmallImages();
			if (images != null) {
				Image swtImage = images.getAsSizedSwtImage(imageIndex);
				swtTableItem.setImage(swtImage);
			}
			else {
				swtTableItem.setImage((Image)null);
			}
		}
	}

	public IusCLListSubItems getSubItems() {
		return subItems;
	}

	public TableItem getSwtTableItem() {
		return swtTableItem;
	}

	public void setSwtTableItem(TableItem swtTableItem) {
		this.swtTableItem = swtTableItem;
	}

}
