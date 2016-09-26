/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.stdctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLStrings;
import org.iuscl.controls.IusCLListControl;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLNotifyEvent;

/* **************************************************************************************************** */
public class IusCLComboBox extends IusCLListControl {

	public enum IusCLComboBoxStyle { csDropDown, csSimple, csDropDownList };
		//, csOwnerDrawFixed, csOwnerDrawVariable};
	
//	AutoCloseUp
//	AutoComplete
//	AutoDropDown
//	CharCase
//	ItemHeight
	
	/* SWT */
	private Combo swtCombo = null;
	private ModifyListener swtModifyListener = null;

	/* Properties */
	private IusCLStrings items = new IusCLStrings();
	private IusCLComboBoxStyle style = IusCLComboBoxStyle.csDropDownList;
	private Integer dropDownCount = 8;
	private Integer maxLength = Combo.LIMIT;

	/* Events */
	private IusCLNotifyEvent onChange = null;

	/* Fields */
	private Integer systemHeight = 0;

	/* **************************************************************************************************** */
	public IusCLComboBox(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Items", IusCLPropertyType.ptStrings, "");
		defineProperty("Style", IusCLPropertyType.ptEnum, "csDropDownList", IusCLComboBoxStyle.csDropDownList);
		defineProperty("DropDownCount", IusCLPropertyType.ptInteger, "8");
		defineProperty("MaxLength", IusCLPropertyType.ptInteger, Integer.toString(Combo.LIMIT));
		
		/* Events */
		defineProperty("OnChange", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		
		/* Create */
		createWnd(createSwtControl());
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.getProperty("Width").setDefaultValue("145");
		setWidth(145);
		
		systemHeight = this.getHeight();
		
		swtModifyListener = new ModifyListener() {
			/* **************************************************************************************************** */
			@Override
			public void modifyText(ModifyEvent modifyEvent) {
				
				IusCLNotifyEvent onChangeEvent = IusCLComboBox.this.getOnChange();
				if (IusCLEvent.isDefinedEvent(onChangeEvent)) {

					onChangeEvent.invoke(IusCLComboBox.this);
				}
			}
		};
	}

	/* **************************************************************************************************** */
	@Override
	protected Control createSwtControl() {

		int swtCreateParams = SWT.NONE;
		
		if (this.getBorderStyle() == IusCLBorderStyle.bsSingle) {
			
			swtCreateParams = swtCreateParams | SWT.BORDER;
		}

		switch (style) {
		case csDropDown:
			swtCreateParams = swtCreateParams | SWT.READ_ONLY; 
			break;
		case csDropDownList:
			swtCreateParams = swtCreateParams | SWT.DROP_DOWN;
			break;
		case csSimple:
			swtCreateParams = swtCreateParams | SWT.SIMPLE; 
			break;
		}
		
		swtCombo = new Combo(this.getFormSwtComposite(), swtCreateParams);
	
		swtCombo.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent swtSelectionEvent) {
				
				itemIndex = swtCombo.getSelectionIndex();
			}
			/* **************************************************************************************************** */
			@Override
			public void widgetDefaultSelected(SelectionEvent swtSelectionEvent) {
				/* When enter leaves the combo.. */
			}
		});
		
		return swtCombo;
	}

	public IusCLStrings getItems() {
		return items;
	}

	/* **************************************************************************************************** */
	public void setItems(IusCLStrings items) {
		
		this.items = items;
		items.setNotify(this, "setItems");
		itemIndex = -1;

		swtCombo.removeAll();
		for (int index  = 0; index < items.size(); index++) {
			
			swtCombo.add(items.get(index));
		}
	}

	public IusCLComboBoxStyle getStyle() {
		return style;
	}

	/* **************************************************************************************************** */
	public void setStyle(IusCLComboBoxStyle style) {

		if (this.style != style) {
			
			this.style = style;
			
			reCreateWnd();		
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void setHeight(Integer height) {
		
		if ((style == IusCLComboBoxStyle.csDropDown) || (style == IusCLComboBoxStyle.csDropDownList)
				&& (systemHeight > 0)) {

			super.setHeight(systemHeight);
			return;
		}

		super.setHeight(height);
	}

	/* **************************************************************************************************** */
	@Override
	public Integer getHeight() {

		if ((style == IusCLComboBoxStyle.csDropDown) || (style == IusCLComboBoxStyle.csDropDownList)
				&& (systemHeight > 0)) {

			this.height = systemHeight;
		}
		
		return super.getHeight();
	}

	public Integer getDropDownCount() {
		return dropDownCount;
	}

	/* **************************************************************************************************** */
	public void setDropDownCount(Integer dropDownCount) {
		this.dropDownCount = dropDownCount;
		
		swtCombo.setVisibleItemCount(dropDownCount);
	}

	/* **************************************************************************************************** */
	public Boolean getDroppedDown() {
		
		return swtCombo.getListVisible();
	}

	/* **************************************************************************************************** */
	public void setDroppedDown(Boolean droppedDown) {
		
		swtCombo.setListVisible(droppedDown);
	}

	/* **************************************************************************************************** */
	@Override
	public void setItemIndex(Integer itemIndex) {
		super.setItemIndex(itemIndex);
		
		swtCombo.select(itemIndex);
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	/* **************************************************************************************************** */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
		
		swtCombo.setTextLimit(maxLength);
	}

	/* **************************************************************************************************** */
	public Integer getSelStart() {
		
		return swtCombo.getSelection().x;
	}

	/* **************************************************************************************************** */
	public void setSelStart(Integer selStart) {
		
		swtCombo.setSelection(new Point(selStart, swtCombo.getSelection().y));
	}

	/* **************************************************************************************************** */
	public Integer getSelLength() {
		
		return swtCombo.getSelection().y - getSelStart();
	}

	/* **************************************************************************************************** */
	public void setSelLength(Integer selLength) {
		
		swtCombo.setSelection(new Point(getSelStart(), getSelStart() + selLength));
	}

	/* **************************************************************************************************** */
	@Override
	public String getText() {

		return swtCombo.getText();
	}

	/* **************************************************************************************************** */
	@Override
	public void setText(String text) {
		
		swtCombo.setText(text);
	}
	
	/* **************************************************************************************************** */
	public String getSelText() {

		return swtCombo.getText().substring(swtCombo.getSelection().x, swtCombo.getSelection().y);
	}

	/* **************************************************************************************************** */
	public void setSelText(String selText) {
		
		int x = swtCombo.getSelection().x;
		
		String newText = swtCombo.getText().substring(0, x);
		newText = newText + selText;
		newText = newText + swtCombo.getText().substring(swtCombo.getSelection().y);
		
		swtCombo.setText(newText);

		swtCombo.setSelection(new Point(x, x + selText.length()));
	}

	public IusCLNotifyEvent getOnChange() {
		return onChange;
	}

	/* **************************************************************************************************** */
	public void setOnChange(IusCLNotifyEvent onChange) {
		this.onChange = onChange;
		
		swtCombo.removeModifyListener(swtModifyListener);
		if (this.getOnChange() != null) {
			
			swtCombo.addModifyListener(swtModifyListener);
		}
	}
	
}
