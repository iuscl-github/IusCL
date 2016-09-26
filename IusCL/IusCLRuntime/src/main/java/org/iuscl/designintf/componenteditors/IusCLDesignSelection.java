/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.componenteditors;

import org.iuscl.classes.IusCLCollection;
import org.iuscl.classes.IusCLCollectionItem;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLPersistent;

/* **************************************************************************************************** */
public class IusCLDesignSelection {

	private IusCLComponent designComponent = null;
	private IusCLCollection designCollection = null;
	private IusCLCollectionItem designCollectionItem = null;

	/* **************************************************************************************************** */
	public IusCLDesignSelection(IusCLComponent designComponent, IusCLCollection designCollection, IusCLCollectionItem designCollectionItem) {

		this.designComponent = designComponent;
		this.designCollection = designCollection;
		this.designCollectionItem = designCollectionItem;
	}

	/* **************************************************************************************************** */
	public IusCLPersistent findPersistent() {

		if (designCollectionItem != null) {
			
			return designCollectionItem;
		}
		else if (designCollection != null) {
			
			return designCollection;
		}
		else {
			
			return designComponent;
		}
	}

	/* **************************************************************************************************** */
	public IusCLComponent getDesignComponent() {
		return designComponent;
	}

	public void setDesignComponent(IusCLComponent designComponent) {
		this.designComponent = designComponent;
	}

	public IusCLCollection getDesignCollection() {
		return designCollection;
	}

	public void setDesignCollection(IusCLCollection designCollection) {
		this.designCollection = designCollection;
	}

	public IusCLCollectionItem getDesignCollectionItem() {
		return designCollectionItem;
	}

	public void setDesignCollectionItem(IusCLCollectionItem designCollectionItem) {
		this.designCollectionItem = designCollectionItem;
	}

}
