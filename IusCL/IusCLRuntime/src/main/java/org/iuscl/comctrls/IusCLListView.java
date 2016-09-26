/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLMultiSelectListControl;

/* **************************************************************************************************** */
public class IusCLListView extends IusCLMultiSelectListControl {

//	AllocBy
//	BoundingRect
//	Checkboxes
//	Column
//	ColumnClick
//	DropTarget
//	FlatScrollBars
//	FullDrag
//	HotTrack
//	HotTrackStyles
//	HoverTime
//	IconOptions
//	ItemFocused
//	LargeImages
//	OwnerData
//	OwnerDraw
//	ReadOnly
//	ShowWorkAreas
//	SortType
//	StateImages
//	ViewOrigin
//	ViewStyle
//	VisibleRowCount
//	WorkAreas
	
	/* SWT */
	private Table swtTable = null;

	/* Properties */
	private IusCLListColumns columns = new IusCLListColumns(this);
	private Boolean showColumnHeaders = true; 
	private Boolean rowSelect = true;
	private Boolean hideSelection = true;
	private Boolean gridLines = false;
	
	private IusCLImageList headerImages = null;
	private IusCLImageList smallImages = null;
	
	/* Events */

	/* Fields */
	private IusCLListItems listItems = new IusCLListItems(this); 
	
	/* **************************************************************************************************** */
	public IusCLListView(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		defineProperty("Columns", IusCLPropertyType.ptCollection, "", false);
		
		defineProperty("ShowColumnHeaders", IusCLPropertyType.ptBoolean, "true");
		defineProperty("RowSelect", IusCLPropertyType.ptBoolean, "true");
		defineProperty("HideSelection", IusCLPropertyType.ptBoolean, "true");
		defineProperty("GridLines", IusCLPropertyType.ptBoolean, "false");
		
		defineProperty("HeaderImages", IusCLPropertyType.ptComponent, "", IusCLImageList.class);
		defineProperty("SmallImages", IusCLPropertyType.ptComponent, "", IusCLImageList.class);

		/* Collection */
		this.setSubCollection(columns);
		columns.setPropertyName("Columns");

		/* Events */

		/* Fields */
		
		/* Create */
		createWnd(createSwtControl());
	}

	/* **************************************************************************************************** */
	@Override
	protected Control createSwtControl() {

		int swtCreateParams = SWT.NONE;
		
		if (this.getBorderStyle() == IusCLBorderStyle.bsSingle) {
			
			swtCreateParams = swtCreateParams | SWT.BORDER;
		}
		
		if (this.getMultiSelect() == true) {
			
			swtCreateParams = swtCreateParams | SWT.MULTI;
		}
		else {

			swtCreateParams = swtCreateParams | SWT.SINGLE;
		}

		if (rowSelect == true) {
			
			swtCreateParams = swtCreateParams | SWT.FULL_SELECTION;
		}

		if (hideSelection == true) {
			
			swtCreateParams = swtCreateParams | SWT.HIDE_SELECTION;
		}

		swtTable = new Table(this.getFormSwtComposite(), swtCreateParams);
		
		swtTable.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent swtSelectionEvent) {
				
				itemIndex = swtTable.getSelectionIndex();
			}
		});

		return swtTable;
	}

	/* **************************************************************************************************** */
	@Override
	public void reCreateWnd() {

		if (this.getIsLoading()) {
			/* recreate at the end of loading */
			return;
		}

		/* Save columns width */
		for (int index = 0; index < columns.getCount(); index++) {

			IusCLListColumn listColumn = columns.get(index);
			listColumn.setWidth(swtTable.getColumn(index).getWidth());
		}
		
		super.reCreateWnd();
		
		/* Columns */
		for (int index = 0; index < columns.getCount(); index++) {
			
			IusCLListColumn listColumn = columns.get(index);
			TableColumn swtTableColumn = new TableColumn(swtTable, SWT.NONE, index);
			listColumn.setSwtTableColumn(swtTableColumn);
			listColumn.assign();
		}
		
		/* Items */
		for (int index = 0; index < listItems.getCount(); index++) {
			
			IusCLListItem listItem = listItems.get(index);
			TableItem swtTableItem = new TableItem(swtTable, SWT.NONE, index);			
			listItem.setSwtTableItem(swtTableItem);
			listItem.assign();
			
			IusCLListSubItems listSubItems = listItem.getSubItems(); 
			
			for (int subIndex = 0; subIndex < listSubItems.size(); subIndex++) {
				
				listSubItems.setCaption(subIndex, listSubItems.getCaption(subIndex));
				listSubItems.setImageIndex(subIndex, listSubItems.getImageIndex(subIndex));
			}
		}
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.getProperty("Height").setDefaultValue("150");
		setHeight(150);
		this.getProperty("Width").setDefaultValue("250");
		setWidth(250);
	}

	/* **************************************************************************************************** */
	public IusCLListColumns getColumns() {
		return columns;
	}

	public void setColumns(IusCLListColumns columns) {
		this.columns = columns;
	}

	public IusCLListItems getListItems() {
		return listItems;
	}

	public Boolean getShowColumnHeaders() {
		return showColumnHeaders;
	}

	/* **************************************************************************************************** */
	public void setShowColumnHeaders(Boolean showColumnHeaders) {
		this.showColumnHeaders = showColumnHeaders;
		
		swtTable.setHeaderVisible(showColumnHeaders);
	}

	public IusCLImageList getHeaderImages() {
		return headerImages;
	}

	/* **************************************************************************************************** */
	public void setHeaderImages(IusCLImageList headerImages) {
		this.headerImages = headerImages;
		
		for (int index = 0; index < columns.getCount(); index++) {
			
			IusCLListColumn listColumn = (IusCLListColumn)columns.get(index);
			listColumn.setImageIndex(listColumn.getImageIndex());
		}
	}

	public IusCLImageList getSmallImages() {
		return smallImages;
	}

	/* **************************************************************************************************** */
	public void setSmallImages(IusCLImageList smallImages) {
		this.smallImages = smallImages;
	}

	public Boolean getRowSelect() {
		return rowSelect;
	}

	/* **************************************************************************************************** */
	public void setRowSelect(Boolean rowSelect) {
		
		if (this.rowSelect != rowSelect) {
			
			this.rowSelect = rowSelect;
			
			reCreateWnd();		
		}
	}

	public Boolean getHideSelection() {
		return hideSelection;
	}

	/* **************************************************************************************************** */
	public void setHideSelection(Boolean hideSelection) {
		
		if (this.hideSelection != hideSelection) {
			
			this.hideSelection = hideSelection;
			
			reCreateWnd();		
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void setItemIndex(Integer itemIndex) {
		super.setItemIndex(itemIndex);
		
		swtTable.select(itemIndex);
	}
	
	/* **************************************************************************************************** */
	@Override
	public Integer getSelCount() {

		return swtTable.getSelectionCount();
	}

	/* **************************************************************************************************** */
	@Override
	public Boolean getSelection(Integer index) {

		return swtTable.isSelected(index);
	}

	/* **************************************************************************************************** */
	@Override
	public void setSelection(Integer index, Boolean selected) {
		
		if (selected == true) {
			
			swtTable.select(index);
		}
		else {
			
			swtTable.deselect(index);
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void setSelection(Integer index, Integer length, Boolean selected) {

		if (selected == true) {
			
			swtTable.select(index, index + length);
		}
		else {
			
			swtTable.deselect(index, index + length);
		}
	}

	/* **************************************************************************************************** */
	@Override
	public Integer getFirstVisibleIndex() {

		return swtTable.getTopIndex();
	}

	/* **************************************************************************************************** */
	@Override
	public void setFirstVisibleIndex(Integer topIndex) {
		
		swtTable.setTopIndex(topIndex);
	}

	public Boolean getGridLines() {
		return gridLines;
	}

	/* **************************************************************************************************** */
	public void setGridLines(Boolean gridLines) {
		this.gridLines = gridLines;
		
		swtTable.setLinesVisible(gridLines);
	}

}
