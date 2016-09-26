/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TabFolder;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLParentControl;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLNotifyEvent;
import org.iuscl.types.IusCLSize;

/* **************************************************************************************************** */
public class IusCLPageControl extends IusCLParentControl {

	/* SWT */
	private TabFolder swtTabFolder = null;
	
	/* Properties */
	private Integer activePageIndex = 0;
	private IusCLImageList images = null;
	
	/* Events */
	private IusCLNotifyEvent onChange = null;
	
	/* Fields */
	private ArrayList<IusCLTabSheet> pages = new ArrayList<IusCLTabSheet>();

	/* **************************************************************************************************** */
	public IusCLPageControl(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("ActivePageIndex", IusCLPropertyType.ptInteger, "0");
		defineProperty("Images", IusCLPropertyType.ptComponent, "", IusCLImageList.class);

		this.removeProperty("Caption");

		/* Events */
		defineProperty("OnChange", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		
		/* Create */
		swtTabFolder = new TabFolder(this.getFormSwtComposite(), SWT.NONE);
		createWnd(swtTabFolder);
		
		swtTabFolder.setLayout(null);
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.getProperty("Height").setDefaultValue("193");
		this.setHeight(193);
		this.getProperty("Width").setDefaultValue("289");
		this.setWidth(289);

		swtTabFolder.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent swtSelectionEvent) {

				if (pages.size() == 0) {
					/* SWT inconsistency */
					return;
				}
					
				activePageIndex = swtTabFolder.getSelectionIndex();
				if (IusCLEvent.isDefinedEvent(onChange)) {
					
					onChange.invoke(IusCLPageControl.this);
				}
			}
		});
	}

	/* **************************************************************************************************** */
	@Override
	public void selectSubComponent(IusCLComponent subComponent) {
		
		setActivePage((IusCLTabSheet)subComponent);
	}

	/* **************************************************************************************************** */
	public ArrayList<IusCLTabSheet> getPages() {
		return pages;
	}

	/* **************************************************************************************************** */
	@Override
	protected void updateBounds(Integer aLeft, Integer aTop, Integer aWidth, Integer aHeight) {

		IusCLSize parentDelta = new IusCLSize(aWidth - width, aHeight - height);
		
		super.updateBounds(aLeft, aTop, aWidth, aHeight);

		if ((parentDelta.getWidth() != 0) || (parentDelta.getHeight() != 0)) {
			
			for (int index = 0; index < pages.size(); index++) {
				
				pages.get(index).doAlignControls(parentDelta);
			}
		}
	}

	public Integer getActivePageIndex() {
		return activePageIndex;
	}

	/* **************************************************************************************************** */
	public void setActivePageIndex(Integer activePageIndex) {
		this.activePageIndex = activePageIndex;
		
		swtTabFolder.setSelection(activePageIndex);
	}

	/* **************************************************************************************************** */
	public IusCLTabSheet getActivePage() {
		
		return pages.get(activePageIndex);
	}

	/* **************************************************************************************************** */
	public void setActivePage(IusCLTabSheet tabSheet) {
		
		setActivePageIndex(pages.indexOf(tabSheet));
	}
	
	/* **************************************************************************************************** */
	public Integer getPageCount() {
		
		return pages.size();
	}

	public IusCLNotifyEvent getOnChange() {
		return onChange;
	}

	public void setOnChange(IusCLNotifyEvent onChange) {
		this.onChange = onChange;
	}
	
	public IusCLImageList getImages() {
		return images;
	}

	/* **************************************************************************************************** */
	public void setImages(IusCLImageList images) {
		this.images = images;
		
		for (int index = 0; index < pages.size(); index++) {
			
			IusCLTabSheet tabSheet = (IusCLTabSheet)pages.get(index);
			tabSheet.setImageIndex(tabSheet.getImageIndex());
		}
	}
}
