/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.help;

import org.iuscl.classes.IusCLStrings;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLStrUtils;
import org.jdom.Element;

/* **************************************************************************************************** */
public class IusCLHelpChapter {

	protected String homeFolder;
//	private String helpFolder;
	
	protected String helpSrcFolder;
	protected String helpDestFolder;

//	protected String webDestFolder = helpFolder + "_sf\\project-web\\iuscl\\htdocs\\help\\";

	protected String templateText = null;
//	protected String webTemplateText = null;
	protected String srcFile = null; 
	protected String srcText = null;
	
	protected String destHRef = null;
	
	protected String tocsFolder;
	protected String tocsDestFolder;
	protected String indexesDestFolder;

	protected String srcImagesFolder = null;
	protected String destImagesFolder = null;
	//protected String webDestImagesFolder = null;

	/* **************************************************************************************************** */
	public IusCLHelpChapter(String homeFolder) {
		super();

		this.homeFolder = homeFolder;
		
		String helpFolder = homeFolder + "\\IusCLHelp\\";
		
		helpSrcFolder = helpFolder + "help-src\\";
		helpDestFolder = helpFolder + "help-dest\\doc\\help\\";

		tocsFolder = "help\\_tocs\\" ;
		tocsDestFolder = helpFolder + "help-dest\\help\\_tocs\\";
		indexesDestFolder = helpFolder + "help-dest\\help\\_indexes\\";
	}
	
	/* **************************************************************************************************** */
	protected void generate(String chapterFolderName, String srcFileName) {
		
		/* Template */
		String templateFile = helpSrcFolder + "\\html\\IusCLHelpPageTemplate.html";
		IusCLStrings templateStrings = new IusCLStrings();
		templateStrings.loadFromFile(templateFile);
		
		templateText = templateStrings.getText();

//		String webTemplateFile = helpSrcFolder + "\\html\\IusCLWebHelpPageTemplate.html"; 
//		IusCLStrings webTemplateStrings = new IusCLStrings();
//		webTemplateStrings.loadFromFile(webTemplateFile);
//		
//		webTemplateText = webTemplateStrings.getText();

		/* Source */
		srcFile = helpSrcFolder + "\\src\\" + srcFileName;

		IusCLStrings srcStrings = new IusCLStrings();
		srcStrings.loadFromFile(srcFile);
		
		srcText = srcStrings.getText();
		
		
		destImagesFolder = helpDestFolder + "images\\" + chapterFolderName + "\\" ;
		//webDestImagesFolder = webDestFolder + "images\\" + chapterFolderName + "\\" ;
		destHRef = "help\\" + chapterFolderName + "\\" ;

		//destFolder = destFolder + chapterFolderName + "\\";

		srcImagesFolder = helpSrcFolder + "images\\" + chapterFolderName + "\\" ;
		
		IusCLFileUtils.copyFolderContent(srcImagesFolder, destImagesFolder);
//		IusCLFileUtils.copyFolderContent(srcImagesFolder, webDestImagesFolder);
	}

	/* **************************************************************************************************** */
	protected void saveToFile(String content, String fileName) {

		String referenceText = IusCLStrUtils.replaceAllWholeWord(templateText, "<!-- content -->", content);
		IusCLStrings referenceContent = new IusCLStrings();
		referenceContent.setText(referenceText);
		referenceContent.saveToFile(helpDestFolder + fileName);
		
//		String webReferenceText = IusCLStrUtils.replaceAllWholeWord(webTemplateText, "<!-- content -->", content);
//		IusCLStrings webReferenceContent = new IusCLStrings();
//		webReferenceContent.setText(webReferenceText);
//		webReferenceContent.saveToFile(webDestFolder + fileName);
	}

	/* **************************************************************************************************** */
	protected void addTopic(Element tocElement, String topicName, String topicLabel) {

		Element topicElement = new Element("topic");
		topicElement.setAttribute("label", topicLabel);
		topicElement.setAttribute("href", "help/" + topicName + "/" + topicName + ".html");
		
		Element anchorElement = new Element("anchor");
		anchorElement.setAttribute("id", topicName);
		topicElement.addContent(anchorElement);
		
		tocElement.addContent(topicElement);
	}

}
