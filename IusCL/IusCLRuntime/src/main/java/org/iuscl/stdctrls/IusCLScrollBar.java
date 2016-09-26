/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.stdctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Slider;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLNotifyEvent;
import org.iuscl.events.IusCLScrollEvent;

/* **************************************************************************************************** */
public class IusCLScrollBar extends IusCLWinControl {

	public enum IusCLScrollBarKind { sbHorizontal, sbVertical };
	
	public enum IusCLScrollCode {scLineUp, scLineDown, scPageUp, scPageDown, scPosition, scTrack, 
		scTop, scBottom, scEndScroll};

	/* SWT */
	private Slider swtSlider = null;

	/* Properties */
	private IusCLScrollBarKind kind = IusCLScrollBarKind.sbHorizontal;

	private Integer smallChange = 1;
	private Integer largeChange = 1;
	
	private Integer max = 100;
	private Integer min = 0;

	private Integer position = 0;
	private Integer pageSize = 0;

	/* Events */
	private IusCLNotifyEvent onChange = null;
	private IusCLScrollEvent onScroll = null;

	/* **************************************************************************************************** */
	public IusCLScrollBar(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Kind", IusCLPropertyType.ptEnum, "sbHorizontal", IusCLScrollBarKind.sbHorizontal);
		
		defineProperty("SmallChange", IusCLPropertyType.ptInteger, "1");
		defineProperty("LargeChange", IusCLPropertyType.ptInteger, "1");
		
		defineProperty("Min", IusCLPropertyType.ptInteger, "0");
		defineProperty("Max", IusCLPropertyType.ptInteger, "100");
		
		defineProperty("Position", IusCLPropertyType.ptInteger, "0");
		defineProperty("PageSize", IusCLPropertyType.ptInteger, "0");
		
		/* Events */
		defineProperty("OnChange", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		defineProperty("OnScroll", IusCLPropertyType.ptEvent, null, IusCLScrollEvent.class);

		/* Create */
		createWnd(createSwtControl());
	}

	/* **************************************************************************************************** */
	@Override
	protected Control createSwtControl() {

		int swtCreateParams = SWT.NONE;

		switch (kind) { 
		case sbHorizontal:
			swtCreateParams = SWT.HORIZONTAL;
			break;
		case sbVertical:
			swtCreateParams = SWT.VERTICAL;
			break;
		}
		
		swtSlider = new Slider(this.getFormSwtComposite(), swtCreateParams);
		
		swtSlider.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent swtSelectionEvent) {
				
				position = swtSlider.getSelection();
				
				if (IusCLEvent.isDefinedEvent(onChange)) {
					
					onChange.invoke(IusCLScrollBar.this);
				}
				
				if (IusCLEvent.isDefinedEvent(onScroll)) {

					IusCLScrollCode scrollCode = IusCLScrollCode.scPosition;

					switch (swtSelectionEvent.detail) {
					case SWT.NONE:
						scrollCode = IusCLScrollCode.scEndScroll;
						break;
					case SWT.DRAG:
						scrollCode = IusCLScrollCode.scTrack;
						break;
					case SWT.HOME:
						scrollCode = IusCLScrollCode.scTop;
						break;
					case SWT.END:
						scrollCode = IusCLScrollCode.scBottom;
						break;
					case SWT.ARROW_DOWN:
						scrollCode = IusCLScrollCode.scLineDown;
						break;
					case SWT.ARROW_UP:
						scrollCode = IusCLScrollCode.scLineUp;
						break;
					case SWT.PAGE_DOWN:
						scrollCode = IusCLScrollCode.scPageDown;
						break;
					case SWT.PAGE_UP:
						scrollCode = IusCLScrollCode.scPageUp;
						break;
					}
					
					Integer newPosition = onScroll.invoke(IusCLScrollBar.this, 
							scrollCode, position);
					setPosition(newPosition);
				}
			}
		});

		return swtSlider;
	}
	
	/* **************************************************************************************************** */
	public IusCLScrollBarKind getKind() {
		return kind;
	}

	/* **************************************************************************************************** */
	public void setKind(IusCLScrollBarKind kind) {

		if (this.kind != kind) {
			
			this.kind = kind;
			
			Integer height = this.getHeight();
			Integer width = this.getWidth();

			this.setWidth(height);	
			this.setHeight(width);

			reCreateWnd();
		}
	}

	public Integer getSmallChange() {
		return smallChange;
	}

	/* **************************************************************************************************** */
	public void setSmallChange(Integer smallChange) {
		this.smallChange = smallChange;
		
		swtSlider.setIncrement(smallChange);
	}

	public Integer getLargeChange() {
		return largeChange;
	}

	/* **************************************************************************************************** */
	public void setLargeChange(Integer largeChange) {
		this.largeChange = largeChange;
		
		swtSlider.setPageIncrement(largeChange);
	}

	public Integer getMax() {
		return max;
	}

	/* **************************************************************************************************** */
	public void setMax(Integer max) {
		this.max = max;
		
		swtSlider.setMaximum(max);
	}

	public Integer getMin() {
		return min;
	}

	/* **************************************************************************************************** */
	public void setMin(Integer min) {
		this.min = min;
		
		swtSlider.setMinimum(min);
	}

	public Integer getPosition() {
		return position;
	}

	/* **************************************************************************************************** */
	public void setPosition(Integer position) {
		this.position = position;
		
		swtSlider.setSelection(position);
	}

	public Integer getPageSize() {
		return pageSize;
	}

	/* **************************************************************************************************** */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		
		swtSlider.setThumb(pageSize);
	}

	public IusCLNotifyEvent getOnChange() {
		return onChange;
	}

	public void setOnChange(IusCLNotifyEvent onChange) {
		this.onChange = onChange;
	}

	public IusCLScrollEvent getOnScroll() {
		return onScroll;
	}

	public void setOnScroll(IusCLScrollEvent onScroll) {
		this.onScroll = onScroll;
	}

	/* **************************************************************************************************** */
	public void setParams(Integer position, Integer min, Integer max) {
		
		setMin(min);
		setMax(max);
		setPosition(position);
	}

}
