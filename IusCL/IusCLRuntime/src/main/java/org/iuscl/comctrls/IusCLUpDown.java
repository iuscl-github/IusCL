/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Spinner;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;

/* **************************************************************************************************** */
public class IusCLUpDown extends IusCLWinControl {

	/* SWT */
	private Spinner swtSpinner = null;
	
	/* Properties */
	private Integer max = 100;
	private Integer min = 0;
	private Integer position = 0;
	private Integer increment = 1;
	private Integer pageIncrement = 10;
	private Integer digits = 0;
	private Integer textLimit = Spinner.LIMIT;
	private Boolean wrap = false;

	/* Events */

	/*
	 * TODO IusCLUpDown Events
	 * 
	 * OnChanging
	 * OnChangingEx
	 * OnClick
	 * 
	 */
	
	/* **************************************************************************************************** */
	public IusCLUpDown(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Max", IusCLPropertyType.ptInteger, "100");
		defineProperty("Min", IusCLPropertyType.ptInteger, "0");
		defineProperty("Position", IusCLPropertyType.ptInteger, "0");
		defineProperty("Increment", IusCLPropertyType.ptInteger, "1");
		defineProperty("PageIncrement", IusCLPropertyType.ptInteger, "10");
		defineProperty("Digits", IusCLPropertyType.ptInteger, "0");
		defineProperty("TextLimit", IusCLPropertyType.ptInteger, Integer.toString(Spinner.LIMIT));
		defineProperty("Wrap", IusCLPropertyType.ptBoolean, "false");
		
		/* Events */

		/* Create */
		swtSpinner = new Spinner(this.getFormSwtComposite(), SWT.BORDER);
		createWnd(swtSpinner);
	}

	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();
		
		this.removeProperty("Caption");
		
		swtSpinner.addModifyListener(new ModifyListener() {
			/* **************************************************************************************************** */
			@Override
			public void modifyText(ModifyEvent swtModifyEvent) {

				IusCLUpDown.this.position = swtSpinner.getSelection();
				
				if (IusCLUpDown.this.wrap == true) {
					System.out.println(swtSpinner.getSelection());
	
					if (IusCLUpDown.this.position >= IusCLUpDown.this.max) {
						
						setPosition(IusCLUpDown.this.min);
					}
				}
			}
		});
	}

	public Integer getMax() {
		return max;
	}

	/* **************************************************************************************************** */
	public void setMax(Integer max) {
		this.max = max;
		
		swtSpinner.setMaximum(max);
	}

	public Integer getMin() {
		return min;
	}

	/* **************************************************************************************************** */
	public void setMin(Integer min) {
		this.min = min;
		
		swtSpinner.setMinimum(min);
	}

	public Integer getPosition() {
		return position;
	}

	/* **************************************************************************************************** */
	public void setPosition(Integer position) {
		this.position = position;
		
		swtSpinner.setSelection(position);
	}

	public Integer getIncrement() {
		return increment;
	}

	/* **************************************************************************************************** */
	public void setIncrement(Integer increment) {
		this.increment = increment;
		
		swtSpinner.setIncrement(increment);
	}

	public Integer getPageIncrement() {
		return pageIncrement;
	}

	/* **************************************************************************************************** */
	public void setPageIncrement(Integer pageIncrement) {
		this.pageIncrement = pageIncrement;
		
		swtSpinner.setPageIncrement(pageIncrement);
	}

	public Integer getDigits() {
		return digits;
	}

	/* **************************************************************************************************** */
	public void setDigits(Integer digits) {
		this.digits = digits;
		
		swtSpinner.setDigits(digits);
	}

	public Integer getTextLimit() {
		return textLimit;
	}

	/* **************************************************************************************************** */
	public void setTextLimit(Integer textLimit) {
		this.textLimit = textLimit;
		
		swtSpinner.setTextLimit(textLimit);
	}
	
	public Boolean getWrap() {
		return wrap;
	}

	public void setWrap(Boolean wrap) {
		this.wrap = wrap;
	}

	/* **************************************************************************************************** */
	@Override
	public String getText() {
		
		return swtSpinner.getText();
	}
	
}
