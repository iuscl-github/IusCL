/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.stdctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.iuscl.classes.IusCLComponent;

/* **************************************************************************************************** */
public class IusCLRadioButton extends IusCLButtonControl {

	/* Properties */

	/* **************************************************************************************************** */
	public IusCLRadioButton(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		defineProperty("Checked", IusCLPropertyType.ptBoolean, "false");

		/* Create */
		swtButton = new Button(this.getFormSwtComposite(), SWT.RADIO);
		createWnd(swtButton);
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.getProperty("Width").setDefaultValue("113");
		setWidth(113);
	}

	/* **************************************************************************************************** */
	@Override
	protected void reCreate() {
		super.reCreate();
		
		swtButton.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent swtSelectionEvent) {

				checked = swtButton.getSelection();
			}
		});
	}

	public Boolean getChecked() {
		return checked;
	}

	/* **************************************************************************************************** */
	public void setChecked(Boolean checked) {

		this.checked = checked;
		
		if (checked == true) {
			swtButton.setSelection(true);
		}
		else {
			swtButton.setSelection(false);
		}
	}
}
