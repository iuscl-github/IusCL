/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.stdctrls;

import org.eclipse.swt.widgets.ScrollBar;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLStrings;

/* **************************************************************************************************** */
public class IusCLMultiLineEditControl extends IusCLEditControl {

	public enum IusCLScrollStyle { ssNone, ssHorizontal, ssVertical, ssBoth };

	/* Properties */
	private Boolean wordWrap = false;
	private IusCLScrollStyle scrollBars = IusCLScrollStyle.ssBoth;
	
	/* Fields */
	protected ScrollBar swtVerticalScrollBar = null;
	protected ScrollBar swtHorizontalScrollBar = null;


	/* **************************************************************************************************** */
	public IusCLMultiLineEditControl(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Lines", IusCLPropertyType.ptStrings, "");
		defineProperty("WordWrap", IusCLPropertyType.ptBoolean, "false");
		defineProperty("ScrollBars", IusCLPropertyType.ptEnum, "ssBoth", IusCLScrollStyle.ssBoth);

		/* Events */
		
	}

	/* **************************************************************************************************** */
	public IusCLStrings getLines() {

		IusCLStrings lines = new IusCLStrings();
		lines.setText(swtText.getText());
		lines.setNotify(this, "setLines");
		return lines;
	}

	/* **************************************************************************************************** */
	public void setLines(IusCLStrings lines) {

		swtText.setText(lines.getText());
	}

	public Boolean getWordWrap() {
		return wordWrap;
	}

	/* **************************************************************************************************** */
	public void setWordWrap(Boolean wordWrap) {
		
		if (this.getWordWrap() != wordWrap) {
			
			this.wordWrap = wordWrap;

			reCreateWnd();
		}
	}

	public IusCLScrollStyle getScrollBars() {
		return scrollBars;
	}

	/* **************************************************************************************************** */
	public void setScrollBars(IusCLScrollStyle scrollBars) {

		this.scrollBars = scrollBars;

		if (swtVerticalScrollBar != null) {

			switch(scrollBars) {
			case ssBoth:
				swtVerticalScrollBar.setVisible(true);
				break;
			case ssHorizontal:
				swtVerticalScrollBar.setVisible(false);
				break;
			case ssNone:
				swtVerticalScrollBar.setVisible(false);
				break;
			case ssVertical:
				swtVerticalScrollBar.setVisible(true);
				break;
			}
		}

		if (swtHorizontalScrollBar != null) {

			switch(scrollBars) {
			case ssBoth:
				swtHorizontalScrollBar.setVisible(true);
				break;
			case ssHorizontal:
				swtHorizontalScrollBar.setVisible(true);
				break;
			case ssNone:
				swtHorizontalScrollBar.setVisible(false);
				break;
			case ssVertical:
				swtHorizontalScrollBar.setVisible(false);
				break;
			}
		}
		
		this.getSwtControl().redraw();
	}
	
	/* **************************************************************************************************** */
	public Integer getCaretLine() {
		
		return swtText.getCaretLineNumber();
	}
	
	/* **************************************************************************************************** */
	public Integer getFirstVisibleLine() {
		
		return swtText.getTopIndex();
	}

	/* **************************************************************************************************** */
	public void setFirstVisibleLine(Integer topLine) {
		
		swtText.setTopIndex(topLine);
	}
	
}
