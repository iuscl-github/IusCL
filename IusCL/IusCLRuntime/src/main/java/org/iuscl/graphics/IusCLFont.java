/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.iuscl.classes.IusCLOS;
import org.iuscl.classes.IusCLPersistent;
import org.iuscl.forms.IusCLApplication;
import org.iuscl.graphics.IusCLColor.IusCLStandardColors;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLFont extends IusCLPersistent {

	/* SWT */
	private Font swtFont = null;

	/* Properties */
	private IusCLColor color = new IusCLColor(IusCLStandardColors.clBlack);
	private IusCLFontStyle style = new IusCLFontStyle();
	private Integer size = null;
	private Integer height = null;
	private String name = null;
	
	/* Fields */
	
	/* **************************************************************************************************** */
	public IusCLFont() {
		super();
		
		setSwtFont(IusCLApplication.getSwtDisplay().getSystemFont());
		
		defineFontProperties(this, null, this);

		/*
		 * No 'create()' here!
		 * 
		 * Exception, a property of a persistent is in fact another persistent that has it's own
		 * properties, design choice to redeclare the properties on the first persistent
		 * 
		 * Declare the properties here only for 'assign()'
		 * 
		 * Font is the only case with this behavior so far, it is neither IusCLControl, to be
		 * a child, nor a 'persistent in a single line'
		 */
	}

	/* **************************************************************************************************** */
	public static void defineFontProperties(IusCLPersistent persistent, String fontPropertyName, IusCLFont defaultFont) {
		
		String prefix = "";
		if (IusCLStrUtils.isNotNullNotEmpty(fontPropertyName)) {
			
			prefix = fontPropertyName + ".";
		}
		
		persistent.defineProperty(prefix + "Style.Bold", IusCLPropertyType.ptBoolean, defaultFont.getStyle().getBold().toString());
		persistent.defineProperty(prefix + "Style.Italic", IusCLPropertyType.ptBoolean, defaultFont.getStyle().getItalic().toString());
		persistent.defineProperty(prefix + "Style.Underline", IusCLPropertyType.ptBoolean, defaultFont.getStyle().getUnderline().toString());
		persistent.defineProperty(prefix + "Style.StrikeOut", IusCLPropertyType.ptBoolean, defaultFont.getStyle().getStrikeOut().toString());

		persistent.defineProperty(prefix + "Size", IusCLPropertyType.ptInteger, defaultFont.getSize().toString());
		persistent.defineProperty(prefix + "Name", IusCLPropertyType.ptFontName, defaultFont.getName());
		
		persistent.defineProperty(prefix + "Color", IusCLPropertyType.ptColor, defaultFont.getColor().getAsString(), defaultFont.getColor().getAsStandardColor());
	}

	/* **************************************************************************************************** */
	public static void putFontPropertiesDefaultValues(IusCLPersistent persistent, String fontPropertyName, IusCLFont font) {
		
		String prefix = "";
		if (IusCLStrUtils.isNotNullNotEmpty(fontPropertyName)) {
			
			prefix = fontPropertyName + ".";
		}
		
		persistent.getProperty(prefix + "Style.Bold").setDefaultValue(font.getStyle().getBold().toString());
		persistent.getProperty(prefix + "Style.Italic").setDefaultValue(font.getStyle().getItalic().toString());
		persistent.getProperty(prefix + "Style.Underline").setDefaultValue(font.getStyle().getUnderline().toString());
		persistent.getProperty(prefix + "Style.StrikeOut").setDefaultValue(font.getStyle().getStrikeOut().toString());

		persistent.getProperty(prefix + "Size").setDefaultValue(font.getSize().toString());
		persistent.getProperty(prefix + "Name").setDefaultValue(font.getName());
		
		persistent.getProperty(prefix + "Color").setDefaultValue(font.getColor().getAsString());
	}

	/* **************************************************************************************************** */
	public static void removeFontProperties(IusCLPersistent persistent, String fontPropertyName) {
		
		String prefix = "";
		if (IusCLStrUtils.isNotNullNotEmpty(fontPropertyName)) {
			
			prefix = fontPropertyName + ".";
		}
		
		persistent.removeProperty(prefix + "Style.Bold");
		persistent.removeProperty(prefix + "Style.Italic");
		persistent.removeProperty(prefix + "Style.Underline");
		persistent.removeProperty(prefix + "Style.StrikeOut");

		persistent.removeProperty(prefix + "Size");
		persistent.removeProperty(prefix + "Name");
		
		persistent.removeProperty(prefix + "Color");
	}

	/* **************************************************************************************************** */
	private void updateSwtFont() {
		
		int swtFontStyle = SWT.NONE;
		
		if (style.getBold() == true) {
			
			swtFontStyle = swtFontStyle | SWT.BOLD;
		}
		if (style.getItalic() == true) {
			
			swtFontStyle = swtFontStyle | SWT.ITALIC;
		}
		
		FontData swtFontData = new FontData(name, size, swtFontStyle);
		
		IusCLOS.osIusCLFont_updateSwtFont(style, swtFontData);
//		if (style.getUnderline() == true) {
//			
//			swtFontData.data.lfUnderline = 1;
//		}
//		if (style.getStrikeOut() == true) {
//			
//			swtFontData.data.lfStrikeOut = 1;
//		}
		
		swtFont = new Font(IusCLApplication.getSwtDisplay(), swtFontData);

		invokeNotify();
	}

	/* **************************************************************************************************** */
	public IusCLFontStyle getStyle() {
		
		style.setNotify(this, "setStyle");
		
		return style;
	}

	/* **************************************************************************************************** */
	public void setStyle(IusCLFontStyle style) {
		this.style = style;

		updateSwtFont();
	}

	public Integer getSize() {
		return size;
	}

	/* **************************************************************************************************** */
	public void setSize(Integer size) {
		this.size = size;

		updateSwtFont();
	}

	public Font getSwtFont() {
		return swtFont;
	}

	/* **************************************************************************************************** */
	public void setSwtFont(Font swtFont) {
		this.swtFont = swtFont;
		
		if (swtFont != null) {

			FontData swtFontData = swtFont.getFontData()[0];
			
			size = swtFontData.getHeight();
			height = -1;
			name = swtFontData.getName();
			
			int swtFontStyle = swtFontData.getStyle();

			style = new IusCLFontStyle();

			if ((swtFontStyle & SWT.BOLD) == SWT.BOLD) {
				
				style.setBold(true);
			}
			if ((swtFontStyle & SWT.ITALIC) == SWT.ITALIC) {
				
				style.setItalic(true);
			}

			IusCLOS.osIusCLFont_setSwtFont(style, swtFontData);
//			if (swtFontData.data.lfUnderline == 1) {
//				
//				style.setUnderline(true);
//			}
//			if (swtFontData.data.lfStrikeOut == 1) {
//				
//				style.setStrikeOut(true);
//			}
		}
		//invokeNotify();
	}

	public IusCLColor getColor() {
		return color;
	}

	/* **************************************************************************************************** */
	public void setColor(IusCLColor color) {
		this.color = color;

		invokeNotify();
	}

	public Integer getHeight() {
		return height;
	}

	/* **************************************************************************************************** */
	public void setHeight(Integer height) {
		this.height = height;
		
		/* Another time */
	}

	public String getName() {
		return name;
	}

	/* **************************************************************************************************** */
	public void setName(String name) {
		this.name = name;

		updateSwtFont();
	}
}
