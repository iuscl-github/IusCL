/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.classes;

/* **************************************************************************************************** */
public class IusCLCollectionItem extends IusCLPersistent {

	private IusCLCollection collection = null;
	
	private Object tag = null;
	
	/* **************************************************************************************************** */
	public IusCLCollectionItem(IusCLCollection collection) {

		this(collection, null);
	}

	/* **************************************************************************************************** */
	public IusCLCollectionItem(IusCLCollection collection, Integer index) {

		this.collection = collection;
		
		if (index == null) {
		
			this.collection.getItems().add(this);
		}
		else {
			
			this.collection.getItems().add(index, this);
		}
	}

	/* **************************************************************************************************** */
	public IusCLCollection getCollection() {
		return collection;
	}
	
	/* **************************************************************************************************** */
	public void delete() {
		
		Integer index = collection.indexOf(this);
		collection.delete(index);
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

}
