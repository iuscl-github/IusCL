/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.stdctrls;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;
import org.iuscl.events.IusCLNotifyEvent;

/* **************************************************************************************************** */
public class IusCLEditControl extends IusCLWinControl {

	/* SWT */
	protected Text swtText = null;
	protected ModifyListener swtModifyListener = null;

	/* Properties */
	private IusCLBorderStyle borderStyle = IusCLBorderStyle.bsSingle;
	private Boolean readOnly = false;
	private Integer maxLength = 0;

	/* Events */
	private IusCLNotifyEvent onChange = null;

	/* Fields */
	
	/* **************************************************************************************************** */
	public IusCLEditControl(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		this.removeProperty("Caption");
		
		defineProperty("BorderStyle", IusCLPropertyType.ptEnum, "bsSingle", IusCLBorderStyle.bsSingle);
		defineProperty("ReadOnly", IusCLPropertyType.ptBoolean, "false");
		defineProperty("MaxLength", IusCLPropertyType.ptInteger, "0");

		/* Events */
		defineProperty("OnChange", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
	}

	/* **************************************************************************************************** */
	@Override
	public void reCreateWnd() {

		if (this.getIsLoading()) {
			/* recreate at the end of loading */
			return;
		}

		String theText = this.getText();
		
		super.reCreateWnd();

		this.setText(theText);
	}

	/* **************************************************************************************************** */
	public void clear() {
		
		setText("");
	}

	/* **************************************************************************************************** */
	public IusCLBorderStyle getBorderStyle() {
		
		return borderStyle;
	}

	/* **************************************************************************************************** */
	public void setBorderStyle(IusCLBorderStyle borderStyle) {
		
		if (this.borderStyle != borderStyle) {
		
			this.borderStyle = borderStyle;
			
			reCreateWnd();		
		}
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public IusCLNotifyEvent getOnChange() {
		return onChange;
	}

	/* **************************************************************************************************** */
	@Override
	public String getText() {
		
		return swtText.getText();
	}

	/* **************************************************************************************************** */
	@Override
	public void setText(String text) {
		
		swtText.setText(text);
	}
	
	/* **************************************************************************************************** */
	public void clearSelection() {
		
		swtText.clearSelection();
	}

	/* **************************************************************************************************** */
	public void selectAll() {
		
		swtText.selectAll();
	}

	/* **************************************************************************************************** */
	public void copyToClipboard() {
		
		swtText.copy();
	}

	/* **************************************************************************************************** */
	public void cutToClipboard() {
		
		swtText.cut();
	}

	/* **************************************************************************************************** */
	public void pasteFromClipboard() {
		
		swtText.paste();
	}

	/* **************************************************************************************************** */
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
		
		if (readOnly == true) {
			
			swtText.setEditable(false);	
		}
		else {

			swtText.setEditable(true);	
		}
	}

	/* **************************************************************************************************** */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
		
		swtText.setTextLimit(maxLength);
	}

	/* **************************************************************************************************** */
	public Integer getSelStart() {

		return swtText.getSelection().x;
	}

	/* **************************************************************************************************** */
	public void setSelStart(Integer selStart) {
		
		int selEnd = getSelStart() + getSelLength();
		if (selEnd < selStart) {

			selEnd = selStart;
		}
		swtText.setSelection(selStart, selEnd);
		swtText.showSelection();
	}

	/* **************************************************************************************************** */
	public Integer getSelLength() {

		return swtText.getSelection().y - getSelStart();
	}

	/* **************************************************************************************************** */
	public void setSelLength(Integer selLength) {
		
		int start = getSelStart();
		swtText.setSelection(start, start + selLength);
		swtText.showSelection();
	}

	/* **************************************************************************************************** */
	public String getSelText() {

		return swtText.getSelectionText();
	}

	/* **************************************************************************************************** */
	public void setSelText(String selText) {
		
		int start = getSelStart();
		swtText.insert(selText);
		setSelStart(start);
		setSelLength(selText.length());
		swtText.showSelection();
	}

	/* **************************************************************************************************** */
	public Integer getCaretPos() {

		return swtText.getCaretPosition();
	}

	/* **************************************************************************************************** */
	public void showSelection() {

		swtText.showSelection();
	}

	/* **************************************************************************************************** */
	public void setOnChange(IusCLNotifyEvent onChange) {
		this.onChange = onChange;
		
		swtText.removeModifyListener(swtModifyListener);
		if (this.getOnChange() != null) {
			
			swtText.addModifyListener(swtModifyListener);
		}
	}

}
