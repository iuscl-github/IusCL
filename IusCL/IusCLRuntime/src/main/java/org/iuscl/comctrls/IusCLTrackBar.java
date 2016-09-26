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
import org.eclipse.swt.widgets.Scale;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLNotifyEvent;
import org.iuscl.graphics.IusCLFont;

/* **************************************************************************************************** */
public class IusCLTrackBar extends IusCLWinControl {

	public enum IusCLTrackBarOrientation { trHorizontal, trVertical };

	/*	
	Frequency
	SelEnd
	SelStart
	SliderVisible
	ThumbLength
	TickMarks
	TickStyle
	
	:(
	
	*/

	/* Properties */
	private Integer max = 10;
	private Integer min = 0;

	private Integer lineSize = 1;
	private Integer pageSize = 2;

	private	IusCLTrackBarOrientation orientation = IusCLTrackBarOrientation.trHorizontal;
	
	private Integer position = 0;
	
	/* Events */
	private IusCLNotifyEvent onChange = null;
	
	/* Fields */
	
	/* SWT */
	private Scale swtScale = null;

	/* **************************************************************************************************** */
	public IusCLTrackBar(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Max", IusCLPropertyType.ptInteger, "10");
		defineProperty("Min", IusCLPropertyType.ptInteger, "0");

		defineProperty("LineSize", IusCLPropertyType.ptInteger, "1");
		defineProperty("PageSize", IusCLPropertyType.ptInteger, "2");

		defineProperty("Orientation", IusCLPropertyType.ptEnum, "trHorizontal", IusCLTrackBarOrientation.trHorizontal);

		defineProperty("Position", IusCLPropertyType.ptInteger, "0");

		/* Events */
		defineProperty("OnChange", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);

		/* Create */
		createWnd(createSwtControl());
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();
		
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
		case trHorizontal:
			swtCreateParams = SWT.HORIZONTAL;
			break;
		case trVertical:
			swtCreateParams = SWT.VERTICAL;
			break;
		}

		swtScale = new Scale(this.getFormSwtComposite(), swtCreateParams);

		swtScale.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent swtSelectionEvent) {
				
				position = swtScale.getSelection();
				
				if (IusCLEvent.isDefinedEvent(onChange)) {
					
					onChange.invoke(IusCLTrackBar.this);
				}
			}
		});

		return swtScale;
	}

	public Integer getMax() {
		return max;
	}

	/* **************************************************************************************************** */
	public void setMax(Integer max) {
		this.max = max;
		
		swtScale.setMaximum(max);
	}

	public Integer getMin() {
		return min;
	}

	/* **************************************************************************************************** */
	public void setMin(Integer min) {
		this.min = min;
		
		swtScale.setMinimum(min);
	}

	public Integer getLineSize() {
		return lineSize;
	}

	/* **************************************************************************************************** */
	public void setLineSize(Integer lineSize) {
		this.lineSize = lineSize;
		
		swtScale.setIncrement(lineSize);
	}

	public Integer getPageSize() {
		return pageSize;
	}

	/* **************************************************************************************************** */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		
		swtScale.setPageIncrement(pageSize);
	}

	public IusCLTrackBarOrientation getOrientation() {
		return orientation;
	}

	/* **************************************************************************************************** */
	public void setOrientation(IusCLTrackBarOrientation orientation) {
		
		if (this.orientation != orientation) {
			
			this.orientation = orientation;
			
			Integer height = this.getHeight();
			Integer width = this.getWidth();

			this.setWidth(height);	
			this.setHeight(width);

			reCreateWnd();
		}
	}

	public Integer getPosition() {
		return position;
	}

	/* **************************************************************************************************** */
	public void setPosition(Integer position) {
		this.position = position;
		
		swtScale.setSelection(position);
	}

	public IusCLNotifyEvent getOnChange() {
		return onChange;
	}

	public void setOnChange(IusCLNotifyEvent onChange) {
		this.onChange = onChange;
	}

	/* **************************************************************************************************** */
	@Override
	public void setParentFont(Boolean parentFont) {
		/* Intentionally nothing */
	}

}
