/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import java.util.ArrayList;

import org.iuscl.classes.IusCLPersistent;

/* **************************************************************************************************** */
public class IusCLParagraphAttributes extends IusCLPersistent {

	public enum IusCLParagraphAlignment { paLeftJustify, paRightJustify, paCenter };

	public enum IusCLNumberingStyle { nsNone, nsBullet };

	private IusCLParagraphAlignment alignment = IusCLParagraphAlignment.paLeftJustify;

	private Boolean justified = false;
	
	private Integer firstIndent = 0;

	private Integer leftIndent = 0;
	private Integer rightIndent = 0;
	
	private ArrayList<Integer> tab = new ArrayList<Integer>();
	private Integer tabCount = 0;
	
	private IusCLNumberingStyle numbering = IusCLNumberingStyle.nsNone;

	/* **************************************************************************************************** */
	public IusCLParagraphAttributes() {
		super();
		
		/* Properties */
		
		/* Events */
	}

	/* **************************************************************************************************** */
	public void emptyAttributes() {
		
		alignment = null;

		justified = null;
		
		firstIndent = null;

		leftIndent = null;
		rightIndent = null;
		
		tab = null;
		tabCount = null;
		
		numbering = null;
	}

	/* **************************************************************************************************** */
	public void loadFromParagraphAttributes(IusCLParagraphAttributes paragraphAttributes) {
		
		alignment = paragraphAttributes.getAlignment();

		justified = paragraphAttributes.getJustified();
		
		firstIndent = paragraphAttributes.getFirstIndent();

		leftIndent = paragraphAttributes.getLeftIndent();
		rightIndent = paragraphAttributes.rightIndent;
		
		tab = paragraphAttributes.getTab();
		tabCount = paragraphAttributes.getTabCount();
		
		numbering = paragraphAttributes.numbering;
	}

	/* **************************************************************************************************** */
	public IusCLParagraphAlignment getAlignment() {
		return alignment;
	}

	/* **************************************************************************************************** */
	public void setAlignment(IusCLParagraphAlignment alignment) {
		this.alignment = alignment;
		
		invokeNotify();
	}

	public Boolean getJustified() {
		return justified;
	}

	public void setJustified(Boolean justified) {
		this.justified = justified;
		
		invokeNotify();
	}

	public Integer getFirstIndent() {
		return firstIndent;
	}

	/* **************************************************************************************************** */
	public void setFirstIndent(Integer firstIndent) {
		this.firstIndent = firstIndent;
		
		invokeNotify();
	}

	public Integer getLeftIndent() {
		return leftIndent;
	}

	/* **************************************************************************************************** */
	public void setLeftIndent(Integer leftIndent) {
		this.leftIndent = leftIndent;
		
		invokeNotify();
	}

	public Integer getRightIndent() {
		return rightIndent;
	}

	/* **************************************************************************************************** */
	public void setRightIndent(Integer rightIndent) {
		this.rightIndent = rightIndent;
		
		invokeNotify();
	}

	public ArrayList<Integer> getTab() {
		return tab;
	}

	public Integer getTabCount() {
		return tabCount;
	}

	/* **************************************************************************************************** */
	public void setTabCount(Integer tabCount) {
		this.tabCount = tabCount;
		
		invokeNotify();
	}

	public IusCLNumberingStyle getNumbering() {
		return numbering;
	}

	/* **************************************************************************************************** */
	public void setNumbering(IusCLNumberingStyle numbering) {
		this.numbering = numbering;
		
		invokeNotify();
	}
	
}
