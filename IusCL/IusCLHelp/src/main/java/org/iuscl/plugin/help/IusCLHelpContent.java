/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.help;

import org.iuscl.classes.IusCLStrings;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLHelpContent {

	private String content = "";
	private String homeFolder = null;
	private String source = null;
	private String sourceFile = null;
	private String element = null;

	/* **************************************************************************************************** */
	public IusCLHelpContent(String homeFolder, String source, String sourceFile, String element) {
		super();
		this.homeFolder = homeFolder;
		this.source = source;
		this.sourceFile = sourceFile;
		this.element = element;
	}

	/* **************************************************************************************************** */
	private String replaceVariable(String variable) {
		
		if (!(variable.startsWith("$") || variable.startsWith("#"))) {
			return variable;
		}
		
		String var = element + "." + variable;
		String begin = "Begin " + var;
		String end = "End " + var;
		
		if (source.indexOf(begin) > 1) {
			
			String variableContent = IusCLStrUtils.subStringBetween(source, begin, end);
			
			if (variableContent.trim().startsWith("IusCLInclude:")) {
				
				String fileName = homeFolder + "\\" + variableContent.trim().substring("IusCLInclude:".length());
				
				if (IusCLFileUtils.fileExists(fileName)) {

					IusCLStrings lines = new IusCLStrings();
					lines.loadFromFile(fileName);
					
					if (IusCLFileUtils.extractFileExt(fileName, true).equalsIgnoreCase("html")) {
						
						String htmlText = lines.getText();
						
						int bodyStart = htmlText.indexOf("<body");
						bodyStart = htmlText.indexOf(">", bodyStart) + 1;
						
						int bodyEnd = htmlText.indexOf("</body>");
						
						
						htmlText = htmlText.substring(bodyStart, bodyEnd);
						
						return htmlText;
					}
					else {
						
						return lines.getText();
					}
				}
				else {
					
					return fileName + " - file not found.";
				}
			}
			else {
				
				return variableContent;	
			}
		}
		else {

			if (variable.startsWith("$")) {
				
				/* Mandatory */
				String notfound = "<i>" + var + " - not found.</i>";
				
				IusCLStrings sourceStrings = new IusCLStrings();
				sourceStrings.loadFromFile(sourceFile);
				sourceStrings.add("-");
				sourceStrings.add(begin);
				sourceStrings.add("");
				sourceStrings.add(notfound);
				sourceStrings.add("");
				sourceStrings.add(end);
				sourceStrings.saveToFile(sourceFile);
				
				return notfound;
			}
			else {
				/* User defined */
				return null;
			}
		}
	}

	/* **************************************************************************************************** */
	public void addUserDefined() {

		addContent("#contentTopText");

		for (int index = 0; index < 6; index++) {
			
			addCategoryOnBar("categoryLink" + index, "#categoryOnBar" + index);
			addCategoryHeader("categoryLink" + index, "#categoryName" + index);
			addContent("#contentText" + index);
		}
	}

	/* **************************************************************************************************** */
	public void addHTML(String html) {
		
		String variable = replaceVariable(html);
		content = content + "\n    " + variable;
	}

	/* **************************************************************************************************** */
	public void addTitle(String title) {

		String variable = replaceVariable(title);

		content = content + "\n    <div class=\"stitle\">" + variable + "</div><!-- category_on_bar -->";
	}

	/* **************************************************************************************************** */
	public void addTitleWithImage(String title, String imageFile) {

		String variable = replaceVariable(title);

		content = content + "\n    <div class=\"stitle\"><img src=\"../images/" + imageFile + 
			"\" width=\"24\" height=\"24\" alt=\"\">&nbsp;" + variable + "</div><!-- category_on_bar -->";
	}

	/* **************************************************************************************************** */
	public void addHierarchyOnBar(String hierarchyText) {

		String hierarchyOnBar = "    <div id=\"hierarchyonbar\" class=\"scategoryonbar\"><a href=\"javascript:ftogglerelative('hierarchyonbar', 'hierarchy');\">Hierarchy</a></div><!-- category_on_bar -->"; 
		content = content.replace("<!-- category_on_bar -->",  hierarchyOnBar); 

		IusCLHelpContent hierarchyContent = new IusCLHelpContent(homeFolder, source, sourceFile, element);
		hierarchyContent.addHeader("Hierarchy<br>&nbsp;<br>");
		hierarchyContent.addContent(hierarchyText);
		
		content = content + "\n    <div id=\"hierarchy\" class=\"shierarchy\"><div class=\"shierarchyshadow\"><div class=\"shierarchycontent\">" + hierarchyContent.getContent() + "</div></div></div>";
	}

	/* **************************************************************************************************** */
	public void addCategoryOnBar(String categoryLink, String categoryName) {

		String variable = replaceVariable(categoryName);
		if (variable == null) {
			return;
		}

		String categoryOnBar = "\n    <div class=\"scategoryonbar\"><a href=\"#" + categoryLink + "\">" + variable + "</a></div><!-- category_on_bar -->";
		
		content = content.replace("<!-- category_on_bar -->",  categoryOnBar);
	}

	/* **************************************************************************************************** */
	public void addCategoryHeader(String categoryLink, String categoryName) {

		String variable = replaceVariable(categoryName);
		if (variable == null) {
			return;
		}

		content = content + "\n    <div class=\"scategory\"><a name=\"" + categoryLink + "\"></a>" + variable + "</div>";
	}

	/* **************************************************************************************************** */
	public void addShortDescriptionWithBar(String description) {

		String variable = replaceVariable(description);
		if (variable == null) {
			return;
		}

		content = content + "\n    <div class=\"sdescription\">" + variable + "</div>";
	}

	/* **************************************************************************************************** */
	public void addHeader(String headerText) {

		String variable = replaceVariable(headerText);
		if (variable == null) {
			return;
		}

		content = content + "\n    <div class=\"sheader\">" + variable + "</div>";
	}

	/* **************************************************************************************************** */
	public void addContent(String contentText) {

		String variable = replaceVariable(contentText);
		if (variable == null) {
			return;
		}

		content = content + "\n    <div class=\"scontent\">" + variable + "</div>";
	}

	/* **************************************************************************************************** */
	public void addJavaCodeFunc(String javaCode) {

		String spart[] = javaCode.split("[ ,()]");
		
		for (int index = 0; index < spart.length; index++) {
			String par = spart[index];
			
			if (par.indexOf(".") > -1) {
				if (par.startsWith("java.")) {
					par = par.substring(par.lastIndexOf(".") + 1);
					javaCode = javaCode.replace(spart[index], par);
				}
				if (par.startsWith("org.iuscl.")) {
					par = par.substring(par.lastIndexOf(".") + 1);
					javaCode = javaCode.replace(spart[index], "<a href=\"" + par.replace("[]", "") + ".html\">" + par + "</a>");
				}
			}
		}
		
		javaCode = javaCode.replace(", ", ",<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		
		content = content + "\n    <div class=\"sjavacode\">" + javaCode + "</div>";
	}

	/* **************************************************************************************************** */
	public void addEmptyContentLine() {

		content = content + "\n    <div class=\"scontent\">&nbsp;</div>";
	}

	/* **************************************************************************************************** */
	public void addStartList(String listText) {

		String variable = replaceVariable(listText);
		if (variable == null) {
			return;
		}

		content = content + "\n    <div class=\"slist\">" + variable + "</div>" +
			"\n    <div class=\"slistbox\">";
	}

	/* **************************************************************************************************** */
	public void addEndList() {

		content = content + "\n    </div>";
	}

	/* **************************************************************************************************** */
	public void addSubItem(String subTitleText) {

		String variable = replaceVariable(subTitleText);
		if (variable == null) {
			return;
		}

		content = content + "\n        <div class=\"ssubtitle\">" + variable + "</div>";
	}

	/* **************************************************************************************************** */
	public void addStartSubItem(String subName, String subTitleText) {

		String variable = replaceVariable(subTitleText);
		if (variable == null) {
			return;
		}

		content = content + "\n        <div id=\"" + subName + "\" class=\"ssubtitle\"><a name=\"" + subName + 
				"\"></a><a href=\"javascript:ftoggleitemsubitem('" + subName + "', '" + subName + "_div');\">" + variable + "</div>" +
				"\n            <div class=\"ssubtitlecontent\" id=\"" + subName + "_div\" style=\"display: none;\">";

//		content = content + "\n        <div id=\"" + subName + "\" class=\"ssubtitle\"><a name=\"" + subName + 
//			"\"></a><a href=\"javascript:ftoggleitemsubitem('" + subName + "', '" + subName + "_div');\">" + variable + "</a></div>" +
//			"\n            <div class=\"ssubtitlecontent\" id=\"" + subName + "_div\" style=\"display: none;\">";
	}

	/* **************************************************************************************************** */
	public void addEndSubItem() {

		content = content + "\n            </div>";
	}

	/* **************************************************************************************************** */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
}
