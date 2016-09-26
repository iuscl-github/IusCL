/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.buttons;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.graphics.IusCLGraphic;

/* **************************************************************************************************** */
public class IusCLSpeedButton extends IusCLCustomButton {

	/* Properties */
	private Boolean down = false;
	
	/* **************************************************************************************************** */
	public IusCLSpeedButton(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Down", IusCLPropertyType.ptBoolean, "false");
		
		/* TODO GroupIndex */
		//defineProperty("GroupIndex", IusCLPropertyType.ptInteger, "0");

		/* Create */
		swtButton = new Button(this.getFormSwtComposite(), SWT.TOGGLE);
		createWnd(swtButton);
	}

	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		Integer defaultHeight = getHeight();
		this.getProperty("Width").setDefaultValue(defaultHeight.toString());
		setWidth(defaultHeight);
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void reCreate() {
		super.reCreate();
		
		swtButton.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent swtSelectionEvent) {
				
				if (swtButton.getSelection() == true) {
					down = true;
					state = IusCLButtonState.bsDown;
				}
				else {
					down = false;
					state = IusCLButtonState.bsUp;
				}
			}
		});
	}

	/* **************************************************************************************************** */
	public Boolean getDown() {
		
		return down;
	}

	/* **************************************************************************************************** */
	public void setDown(Boolean down) {
		
		this.down = down;
		
		if (down == true) {
			swtButton.setSelection(true);
			state = IusCLButtonState.bsDown;
		}
		else {
			swtButton.setSelection(false);
			state = IusCLButtonState.bsUp;
		}
	}

//	public Integer getGroupIndex() {
//		return groupIndex;
//	}
//
//	public void setGroupIndex(Integer groupIndex) {
//		this.groupIndex = groupIndex;
//	}
	
	/* **************************************************************************************************** */
	@Override
	public IusCLGraphic getGraphic() {
		
		loadGraphic();
		return super.getGraphic();
	}

}
