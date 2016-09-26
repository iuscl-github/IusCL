/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ProgressBar;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;
import org.iuscl.graphics.IusCLFont;

/* **************************************************************************************************** */
public class IusCLProgressBar extends IusCLWinControl {

	public enum IusCLProgressBarOrientation { pbHorizontal, pbVertical };

	public enum IusCLProgressBarState { psNormal, psError, psPause };

	/* SWT */
	private ProgressBar swtProgressBar = null;

	/* Properties */
	private Integer max = 100;
	private Integer min = 0;
	private	IusCLProgressBarOrientation orientation = IusCLProgressBarOrientation.pbHorizontal;
	private	IusCLProgressBarState state = IusCLProgressBarState.psNormal;
	private Integer position = 0;
	private Boolean smooth = false;
	private Integer step = 10;
	private Boolean indeterminate = false;
	
	/* Events */


	/* **************************************************************************************************** */
	public IusCLProgressBar(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Max", IusCLPropertyType.ptInteger, "100");
		defineProperty("Min", IusCLPropertyType.ptInteger, "0");
		defineProperty("Orientation", IusCLPropertyType.ptEnum, "pbHorizontal", IusCLProgressBarOrientation.pbHorizontal);
		defineProperty("State", IusCLPropertyType.ptEnum, "psNormal", IusCLProgressBarState.psNormal);
		defineProperty("Position", IusCLPropertyType.ptInteger, "0");
		defineProperty("Smooth", IusCLPropertyType.ptBoolean, "false");
		defineProperty("Step", IusCLPropertyType.ptInteger, "10");
		defineProperty("Indeterminate", IusCLPropertyType.ptBoolean, "false");
		
		/* Events */

		/* Create */
		createWnd(createSwtControl());
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();
		
		this.getProperties().get("TabStop").setDefaultValue("false");
		this.setTabStop(false);
		this.removeProperty("Caption");
		this.removeProperty("Font");
		this.removeProperty("ParentFont");
		IusCLFont.removeFontProperties(this, "Font");
	}

	/* **************************************************************************************************** */
	@Override
	protected Control createSwtControl() {

		int swtCreateParams = SWT.NONE;

		switch (orientation) {
		case pbHorizontal:
			swtCreateParams = SWT.HORIZONTAL;
			break;
		case pbVertical:
			swtCreateParams = SWT.VERTICAL;
			break;
		}
		
		if (smooth == true) {
			
			swtCreateParams = swtCreateParams | SWT.SMOOTH;
		}

		if (indeterminate == true) {
			
			swtCreateParams = swtCreateParams | SWT.INDETERMINATE;
		}

		swtProgressBar = new ProgressBar(this.getFormSwtComposite(), swtCreateParams);
		
		return swtProgressBar;
	}

	public Integer getMax() {
		return max;
	}

	/* **************************************************************************************************** */
	public void setMax(Integer max) {
		this.max = max;
		
		swtProgressBar.setMaximum(max);
	}

	public Integer getMin() {
		return min;
	}

	/* **************************************************************************************************** */
	public void setMin(Integer min) {
		this.min = min;
		
		swtProgressBar.setMinimum(min);
	}

	public IusCLProgressBarOrientation getOrientation() {
		return orientation;
	}

	/* **************************************************************************************************** */
	public void setOrientation(IusCLProgressBarOrientation orientation) {
		
		if (this.orientation != orientation) {
			
			this.orientation = orientation;
			
			Integer height = this.getHeight();
			Integer width = this.getWidth();

			this.setWidth(height);	
			this.setHeight(width);

			reCreateWnd();
		}
	}

	public IusCLProgressBarState getState() {
		return state;
	}

	/* **************************************************************************************************** */
	public void setState(IusCLProgressBarState state) {
		this.state = state;
		
		switch (state) {
		case psError:
			swtProgressBar.setState(SWT.ERROR);
			break;
		case psNormal:
			swtProgressBar.setState(SWT.NORMAL);
			break;
		case psPause:
			swtProgressBar.setState(SWT.PAUSE);
			break;
		}
	}

	public Integer getPosition() {
		return position;
	}

	/* **************************************************************************************************** */
	public void setPosition(Integer position) {
		this.position = position;
		
		swtProgressBar.setSelection(position);
	}

	public Boolean getSmooth() {
		return smooth;
	}

	/* **************************************************************************************************** */
	public void setSmooth(Boolean smooth) {
		
		if (this.smooth != smooth) {
			
			this.smooth = smooth;
			reCreateWnd();
		}
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Boolean getIndeterminate() {
		return indeterminate;
	}

	/* **************************************************************************************************** */
	public void setIndeterminate(Boolean indeterminate) {

		if (this.indeterminate != indeterminate) {
			
			this.indeterminate = indeterminate;
			reCreateWnd();
		}
	}

	/* **************************************************************************************************** */
	public void stepIt() {
		
		stepBy(step);
	}

	/* **************************************************************************************************** */
	public void stepBy(Integer delta) {
		
		int newPos = position + delta;
		
		if (newPos < max) {
			
			setPosition(newPos);
		}
		else {

			setPosition(max);
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void setParentFont(Boolean parentFont) {
		/* Intentionally nothing */
	}

}
