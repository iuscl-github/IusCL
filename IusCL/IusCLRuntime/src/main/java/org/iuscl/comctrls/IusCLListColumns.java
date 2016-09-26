/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.iuscl.classes.IusCLCollection;

/* **************************************************************************************************** */
public class IusCLListColumns extends IusCLCollection {

	/* **************************************************************************************************** */
	public IusCLListColumns(IusCLListView listView) {
		super(listView);
	}

	/* **************************************************************************************************** */
	@Override
	public IusCLListView getPropertyComponent() {

		return (IusCLListView)super.getPropertyComponent();
	}

	/* **************************************************************************************************** */
	public IusCLListView getListView() {

		return getPropertyComponent();
	}

	/* **************************************************************************************************** */
	@Override
	public Class<?> getItemClass() {
		
		return IusCLListColumn.class;
	}

	/* **************************************************************************************************** */
	@Override
	public IusCLListColumn add() {
		
		IusCLListColumn listColumn = new IusCLListColumn(this);
		return listColumn;
	}

	/* **************************************************************************************************** */
	@Override
	public IusCLListColumn insert(int index) {

		IusCLListColumn listColumn = new IusCLListColumn(this, index);
		return listColumn;
	}

	/* **************************************************************************************************** */
	@Override
	public IusCLListColumn get(int index) {
		
		return (IusCLListColumn)super.get(index);
	}

	/* **************************************************************************************************** */
	public Integer indexOf(IusCLListColumn listColumn) {
		
		return super.indexOf(listColumn);
	}

}
