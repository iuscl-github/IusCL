/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.iuscl.classes.IusCLPersistent;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLColor.IusCLStandardColors;
import org.iuscl.graphics.IusCLFont;

/* **************************************************************************************************** */
public class IusCLTextAttributes extends IusCLPersistent {

	public enum IusCLUnderlineStyle { usSingle, usDouble, usError, usSquiggle, usLink };

	private String name = null;
	private Integer size = null;
	private Integer height = null;
	
	private Boolean bold = null;
	private Boolean italic = null;
	private Boolean underline = null;
	private Boolean strikeOut = null;
	
	private IusCLColor color = null;
	private IusCLColor highlightColor = null;
	private IusCLColor underlineColor = null;
	private IusCLColor strikeOutColor = null;
	
	private IusCLUnderlineStyle underlineStyle = null;
	
	/* **************************************************************************************************** */
	public IusCLTextAttributes() {
		super();

		IusCLFont defaultFont = new IusCLFont();
		this.loadFromFont(defaultFont);

		/* Properties */
		
		/* Events */
	}

	/* **************************************************************************************************** */
	public void emptyAttributes() {
		
		name = null;
		size = null;
		height = null;
		
		bold = null;
		italic = null;
		underline = null;
		strikeOut = null;
		
		color = null;
		highlightColor = null;
		underlineColor = null;
		strikeOutColor = null;
		
		underlineStyle = null;
	}

	/* **************************************************************************************************** */
	public void loadFromFont(IusCLFont font) {
		
		loadFromFont(font, true);
	}

	/* **************************************************************************************************** */
	public void loadFromFont(IusCLFont font, Boolean allAttributes) {
		
		name = font.getName();
		size = font.getSize();
		height = font.getHeight();
		
		bold = font.getStyle().getBold();
		italic = font.getStyle().getItalic();
		underline = font.getStyle().getUnderline();
		strikeOut = font.getStyle().getStrikeOut();
		
		color = font.getColor();
		
		if (allAttributes = true) {

			highlightColor = IusCLColor.getStandardColor(IusCLStandardColors.clWindow);
			underlineColor = color;
			strikeOutColor = color;
			
			underlineStyle = IusCLUnderlineStyle.usSingle;
		}
	}

	/* **************************************************************************************************** */
	public IusCLFont getAsFont() {
		
		return getAsFont(null);
	}

	/* **************************************************************************************************** */
	public IusCLFont getAsFont(IusCLFont defaultFont) {
		
		IusCLFont font = new IusCLFont();
		if (defaultFont != null) {
		
			font.setSwtFont(defaultFont.getSwtFont());
		}
		
		
		if (name != null) {
			
			font.setName(name);
		}
		if (size != null) {
			
			font.setSize(size);
		}
//		if (height != null) {
//			
//			font.setSize(height);
//		}
		
		
		if (bold != null) {
			
			font.getStyle().setBold(bold);
		}
		if (italic != null) {
			
			font.getStyle().setItalic(italic);
		}
		if (strikeOut != null) {
			
			font.getStyle().setStrikeOut(strikeOut);
		}
		if (underline != null) {
			
			font.getStyle().setUnderline(underline);
		}
		
		if (color != null) {
			
			font.setColor(color);
		}

		return font;
	}

	/* **************************************************************************************************** */
	public void loadFromTextAttributes(IusCLTextAttributes textAttributes) {
		
		name = textAttributes.getName();
		size = textAttributes.getSize();
		height = textAttributes.getHeight();
		
		bold = textAttributes.getBold();
		italic = textAttributes.getItalic();
		underline = textAttributes.getUnderline();
		strikeOut = textAttributes.getStrikeOut();
		
		color = textAttributes.getColor();
		
		highlightColor = textAttributes.getHighlightColor();
		underlineColor = textAttributes.getUnderlineColor();
		strikeOutColor = textAttributes.getStrikeOutColor();
		
		underlineStyle = textAttributes.getUnderlineStyle();
	}

	public IusCLColor getHighlightColor() {
		return highlightColor;
	}

	/* **************************************************************************************************** */
	public void setHighlightColor(IusCLColor highlightColor) {
		this.highlightColor = highlightColor;
		
		invokeNotify();
	}

	public String getName() {
		return name;
	}

	/* **************************************************************************************************** */
	public void setName(String name) {
		this.name = name;
		
		invokeNotify();
	}

	public Integer getSize() {
		return size;
	}

	/* **************************************************************************************************** */
	public void setSize(Integer size) {
		this.size = size;
		
		invokeNotify();
	}

	public Integer getHeight() {
		return height;
	}

	/* **************************************************************************************************** */
	public void setHeight(Integer height) {
		this.height = height;
		
		invokeNotify();
	}

	public Boolean getBold() {
		return bold;
	}

	/* **************************************************************************************************** */
	public void setBold(Boolean bold) {
		this.bold = bold;
		
		invokeNotify();
	}

	public Boolean getItalic() {
		return italic;
	}

	/* **************************************************************************************************** */
	public void setItalic(Boolean italic) {
		this.italic = italic;
		
		invokeNotify();
	}

	public Boolean getUnderline() {
		return underline;
	}

	/* **************************************************************************************************** */
	public void setUnderline(Boolean underline) {
		this.underline = underline;
		
		invokeNotify();
	}

	public Boolean getStrikeOut() {
		return strikeOut;
	}

	/* **************************************************************************************************** */
	public void setStrikeOut(Boolean strikeOut) {
		this.strikeOut = strikeOut;
		
		invokeNotify();
	}

	public IusCLColor getColor() {
		return color;
	}

	/* **************************************************************************************************** */
	public void setColor(IusCLColor color) {
		this.color = color;
		
		invokeNotify();
	}

	public IusCLColor getUnderlineColor() {
		return underlineColor;
	}

	/* **************************************************************************************************** */
	public void setUnderlineColor(IusCLColor underlineColor) {
		this.underlineColor = underlineColor;
		
		invokeNotify();
	}

	public IusCLColor getStrikeOutColor() {
		return strikeOutColor;
	}

	/* **************************************************************************************************** */
	public void setStrikeOutColor(IusCLColor strikeOutColor) {
		this.strikeOutColor = strikeOutColor;
		
		invokeNotify();
	}

	public IusCLUnderlineStyle getUnderlineStyle() {
		return underlineStyle;
	}

	/* **************************************************************************************************** */
	public void setUnderlineStyle(IusCLUnderlineStyle underlineStyle) {
		this.underlineStyle = underlineStyle;
		
		invokeNotify();
	}
}
