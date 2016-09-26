/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.packages;

import java.util.ArrayList;

/* **************************************************************************************************** */
public class IusCLDesignPackage {

	protected static String pnStandard = "Standard";
	protected static String pnAdditional = "Additional";
	protected static String pnWindows = "Windows";
	protected static String pnSystem = "System";
	protected static String pnDialogs = "Dialogs";

	private String codeTemplatesFile = null;
	
	private ArrayList<IusCLDesignComponentInfo> designComponentInfos = 
		new ArrayList<IusCLDesignComponentInfo>();

	private ArrayList<IusCLDesignPropertyInfo> designPropertyInfos = 
		new ArrayList<IusCLDesignPropertyInfo>();
	
	private ArrayList<IusCLDesignEventInfo> designEventInfos = 
			new ArrayList<IusCLDesignEventInfo>();

	
	/* **************************************************************************************************** */
	public ArrayList<IusCLDesignComponentInfo> getDesignComponentInfos() {
		
		return designComponentInfos;
	}

	/* **************************************************************************************************** */
	public ArrayList<IusCLDesignPropertyInfo> getDesignPropertyInfos() {
		
		return designPropertyInfos;
	}

	/* **************************************************************************************************** */
	public ArrayList<IusCLDesignEventInfo> getDesignEventInfos() {
		
		return designEventInfos;
	}

	/* **************************************************************************************************** */
	public void defineDesignPropertyInfo(String name, String propertyEditorClass) {
		
		defineDesignPropertyInfo(name, propertyEditorClass, false, false);
	}

	/* **************************************************************************************************** */
	public void defineDesignPropertyInfo(String name, String propertyEditorClass,
			Boolean customDisplayValue, Boolean customDisplayPaint) {
		
		IusCLDesignPropertyInfo designPropertyInfo = new IusCLDesignPropertyInfo(); 
		
		designPropertyInfo.setName(name);
		designPropertyInfo.setPropertyEditorClass(propertyEditorClass);
		designPropertyInfo.setCustomDisplayValue(customDisplayValue);
		designPropertyInfo.setCustomDisplayPaint(customDisplayPaint);
		
		designPropertyInfos.add(designPropertyInfo);
	}

	/* **************************************************************************************************** */
	public void defineDesignComponentInfo(String componentClass, String name, 
			String palettePage, String paletteImage, String paletteCaption) {
		
		defineDesignComponentInfo(componentClass, name, palettePage, paletteImage, paletteCaption, null);
	}

	/* **************************************************************************************************** */
	public void defineDesignComponentInfo(String componentClass, String name, 
			String palettePage, String paletteImage, String paletteCaption,
			String componentEditorClass) {
		
		IusCLDesignComponentInfo designComponentInfo = new IusCLDesignComponentInfo(); 
		
		designComponentInfo.setName(name);
		designComponentInfo.setPalettePage(palettePage);
		designComponentInfo.setPaletteImage(paletteImage);
		designComponentInfo.setPaletteCaption(paletteCaption);
		designComponentInfo.setComponentClass(componentClass);
		
		designComponentInfo.setComponentEditorClass(componentEditorClass);
		
		designComponentInfos.add(designComponentInfo);
	}

	/* **************************************************************************************************** */
	public void defineEventInfo(String eventName, String codeTemplateName, String codeTemplateContent, 
			String... imports) {
		
		IusCLDesignEventInfo designEventInfo = new IusCLDesignEventInfo();
		
		designEventInfo.setEventName(eventName);
		designEventInfo.setCodeTemplateName(codeTemplateName);
		designEventInfo.setCodeTemplateContent(codeTemplateContent);
		
		for (int index = 0; index < imports.length; index++) {
			
			designEventInfo.getImports().add(imports[index]);
		}
		
		designEventInfos.add(designEventInfo);
	}

	public String getCodeTemplatesFile() {
		return codeTemplatesFile;
	}

	public void setCodeTemplatesFile(String codeTemplatesFile) {
		this.codeTemplatesFile = codeTemplatesFile;
	}


}
