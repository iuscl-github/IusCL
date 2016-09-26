/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.classes;

import java.util.ArrayList;

/* **************************************************************************************************** */
public class IusCLCollection extends IusCLPersistent {

	private IusCLComponent propertyComponent = null;
	private String propertyName = null;
	private ArrayList<IusCLCollectionItem> items = new ArrayList<IusCLCollectionItem>();

	/* Properties */

	/* Events */

	/* **************************************************************************************************** */
	public IusCLCollection(IusCLComponent propertyComponent) {
		super();
		this.propertyComponent = propertyComponent;
	}

	/* **************************************************************************************************** */
	protected ArrayList<IusCLCollectionItem> getItems() {
		
		return items;
	}
	
	/* **************************************************************************************************** */
	public IusCLCollectionItem add() {
		
		IusCLCollectionItem collectionItem = new IusCLCollectionItem(this);
		return collectionItem;
	}

	/* **************************************************************************************************** */
	public IusCLCollectionItem insert(int index) {

		IusCLCollectionItem collectionItem = new IusCLCollectionItem(this, index);
		return collectionItem;
	}

	/* **************************************************************************************************** */
	public void delete(int index) {
		
		IusCLCollectionItem collectionItem = items.get(index);
		collectionItem.free();
		items.remove(index);
	}

	/* **************************************************************************************************** */
	public IusCLCollectionItem get(int index) {
		
		return items.get(index);
	}

	/* **************************************************************************************************** */
	public Integer indexOf(IusCLCollectionItem item) {
		
		return items.indexOf(item);
	}

	/* **************************************************************************************************** */
	public Integer getCount() {
		
		return items.size();
	}

	/* **************************************************************************************************** */
	public Class<?> getItemClass() {
		return IusCLCollectionItem.class;
	}

	public IusCLComponent getPropertyComponent() {
		return propertyComponent;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
}
