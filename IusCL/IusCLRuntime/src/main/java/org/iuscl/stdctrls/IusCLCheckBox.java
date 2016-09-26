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
public class IusCLCheckBox extends IusCLButtonControl {

	public enum IusCLCheckBoxState { cbUnchecked, cbChecked, cbGrayed };
	
	public enum IusCLLeftRight { taLeftJustify, taRightJustify };

	/* Properties */
	private IusCLLeftRight alignment = IusCLLeftRight.taLeftJustify;
	private Boolean allowGrayed = false;
	private IusCLCheckBoxState state = IusCLCheckBoxState.cbUnchecked;

	/* **************************************************************************************************** */
	public IusCLCheckBox(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		defineProperty("Alignment", IusCLPropertyType.ptEnum, "taLeftJustify", IusCLLeftRight.taLeftJustify);
		defineProperty("AllowGrayed", IusCLPropertyType.ptBoolean, "false");
		defineProperty("Checked", IusCLPropertyType.ptBoolean, "false");
		defineProperty("State", IusCLPropertyType.ptEnum, "cbUnchecked", IusCLCheckBoxState.cbUnchecked);
		
		/* Events */

		/* Create */
		swtButton = new Button(this.getFormSwtComposite(), SWT.CHECK);
		createWnd(swtButton);
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.getProperty("Width").setDefaultValue("97");
		setWidth(97);
	}

	/* **************************************************************************************************** */
	@Override
	protected void reCreate() {
		super.reCreate();
		
		swtButton.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent swtSelectionEvent) {

				switch(state) {
				case cbChecked:
					setState(IusCLCheckBoxState.cbUnchecked);
					break;
				case cbGrayed:
					setState(IusCLCheckBoxState.cbChecked);
					break;
				case cbUnchecked:
					if (allowGrayed == true) {
						setState(IusCLCheckBoxState.cbGrayed);
					}
					else {
						setState(IusCLCheckBoxState.cbChecked);
					}
					break;
				}
			}
		});
	}

	/* **************************************************************************************************** */
	public IusCLLeftRight getAlignment() {
		return alignment;
	}

	/* **************************************************************************************************** */
	public void setAlignment(IusCLLeftRight alignment) {
		this.alignment = alignment;
		
		switch(alignment) {
		case taLeftJustify:
			swtButton.setAlignment(SWT.LEFT);
			break;
		case taRightJustify:
			swtButton.setAlignment(SWT.RIGHT);
			break;
		}
	}

	public Boolean getAllowGrayed() {
		return allowGrayed;
	}

	/* **************************************************************************************************** */
	public void setAllowGrayed(Boolean allowGrayed) {
		this.allowGrayed = allowGrayed;
		
		if (allowGrayed == false) {
			if (state == IusCLCheckBoxState.cbGrayed) {
				setState(IusCLCheckBoxState.cbUnchecked);
			}
		}
	}

	public IusCLCheckBoxState getState() {
		return state;
	}

	/* **************************************************************************************************** */
	public void setState(IusCLCheckBoxState state) {
		this.state = state;
		
		switch(state) {
		case cbChecked:
			setChecked(true);
			swtButton.setGrayed(false);
			swtButton.setSelection(true);
			break;
		case cbGrayed:
			setAllowGrayed(true);
			setChecked(false);
			swtButton.setGrayed(true);
			swtButton.setSelection(true);
			break;
		case cbUnchecked:
			setChecked(false);
			swtButton.setGrayed(false);
			swtButton.setSelection(false);
			break;
		}
	}

	public Boolean getChecked() {
		return checked;
	}

	/* **************************************************************************************************** */
	public void setChecked(Boolean checked) {

		this.checked = checked;
		
		if (checked == true) {
			if (state != IusCLCheckBoxState.cbChecked) {
				setState(IusCLCheckBoxState.cbChecked);
			}
		}
		else {
			if (state == IusCLCheckBoxState.cbChecked) {
				setState(IusCLCheckBoxState.cbUnchecked);
			}
		}
	}
	
}
