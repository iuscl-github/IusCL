/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.packages;

/* **************************************************************************************************** */
public class IusCLDesignComponentInfo {

	private String name;

	private String palettePage;
	
	private String paletteImage;

	private String paletteCaption;

	private String componentClass;
	
	private String componentEditorClass;

	/* **************************************************************************************************** */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPalettePage() {
		return palettePage;
	}

	public void setPalettePage(String palettePage) {
		this.palettePage = palettePage;
	}

	public String getPaletteImage() {
		return paletteImage;
	}

	public void setPaletteImage(String paletteImage) {
		this.paletteImage = paletteImage;
	}

	public String getPaletteCaption() {
		return paletteCaption;
	}

	public void setPaletteCaption(String paletteCaption) {
		this.paletteCaption = paletteCaption;
	}

	public String getComponentClass() {
		return componentClass;
	}

	public void setComponentClass(String componentClass) {
		this.componentClass = componentClass;
	}

	public String getComponentEditorClass() {
		return componentEditorClass;
	}

	public void setComponentEditorClass(String componentEditorClass) {
		this.componentEditorClass = componentEditorClass;
	}
	
}
