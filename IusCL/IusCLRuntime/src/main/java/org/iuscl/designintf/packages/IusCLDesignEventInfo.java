/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.packages;

import java.util.ArrayList;

/* **************************************************************************************************** */
public class IusCLDesignEventInfo {

	private String eventName = null;
	private String codeTemplateContent = null;
	private String codeTemplateName = null;
	private ArrayList<String> imports = new ArrayList<String>();
	
	/* **************************************************************************************************** */
	public ArrayList<String> getImports() {
		return imports;
	}
	
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getCodeTemplateContent() {
		return codeTemplateContent;
	}

	public void setCodeTemplateContent(String codeTemplateContent) {
		this.codeTemplateContent = codeTemplateContent;
	}

	public String getCodeTemplateName() {
		return codeTemplateName;
	}

	public void setCodeTemplateName(String codeTemplateName) {
		this.codeTemplateName = codeTemplateName;
	}
}
