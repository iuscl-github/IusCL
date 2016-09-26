/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.help;

import org.iuscl.classes.IusCLStrings;
import org.iuscl.sysutils.IusCLFileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.ProcessingInstruction;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/* **************************************************************************************************** */
public class IusCLHelpUserGuide extends IusCLHelpChapter {

	/* **************************************************************************************************** */
	public static void main(String[] args) {
		
		IusCLHelpUserGuide helpUserGuide = new IusCLHelpUserGuide(args[0]);
		
		helpUserGuide.generate("userguide", "IusCLHelpUserGuide.txt");
	}

	/* **************************************************************************************************** */
	public IusCLHelpUserGuide(String homeFolder) {

		super(homeFolder);
	}

	/* **************************************************************************************************** */
	@Override
	public void generate(String chapterFolderName, String srcFileName) {

		super.generate(chapterFolderName, srcFileName);
		
		/* Styles */
		IusCLFileUtils.copyFile(helpSrcFolder + "styles\\IusCLHelp.css", helpDestFolder + "styles\\IusCLHelp.css");

		/* Scripts */
		IusCLFileUtils.copyFile(helpSrcFolder + "scripts\\IusCLHelp.js", helpDestFolder + "scripts\\IusCLHelp.js");

		/* Source images */
		//IusCLFileUtils.copyFile(fileNameSrc, fileNameDest)
		
		Document jdomTocDocument = new Document();
		jdomTocDocument.addContent(new ProcessingInstruction("NLS", "TYPE=\"org.eclipse.help.toc\""));

		/* TOC */
		Element tocElement = new Element("toc");
		tocElement.setAttribute("label", "IusCL User Guide");
		tocElement.setAttribute("topic", destHRef + chapterFolderName + ".html");
		jdomTocDocument.setRootElement(tocElement);
		
		addTopic(tocElement, "gettingstarted", "Getting Started");
		addTopic(tocElement, "concepts", "Concepts");
		addTopic(tocElement, "tasks", "Tasks");
		addTopic(tocElement, "reference", "IusCL Object and Component Reference");
		addTopic(tocElement, "samples", "Samples");
		
		Element licenseElement = new Element("topic");
		licenseElement.setAttribute("label", "License");
		licenseElement.setAttribute("href", destHRef + "license.html");
		tocElement.addContent(licenseElement);

		/* Root */
		IusCLHelpContent userguideTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "userguideTopic");
		
		userguideTextContent.addTitle("IusCL User Guide");
		userguideTextContent.addShortDescriptionWithBar("This is the main help page");
		
		userguideTextContent.addHeader("Introduction");
		userguideTextContent.addContent("Welcome to IusCL!<br>" +
	    "Delphi7 style, Windows <b>Java SWT based Component Library</b> and <b>Eclipse Development Plug-in</b>");

		userguideTextContent.addHeader("Note");
		userguideTextContent.addContent("On a first phase, the Reference will not be complete on descriptions");

		userguideTextContent.addCategoryOnBar("toc", "TOC");
		userguideTextContent.addCategoryHeader("toc", "TOC");
		
		String tocContent = "&nbsp;<br>\n" + 
			"<a href=\"../gettingstarted/gettingstarted.html\"><b>Getting Started</b></a><br>\n" +
			"<a href=\"../concepts/concepts.html\"><b>Concepts</b></a><br>\n" +			
			"<a href=\"../tasks/tasks.html\"><b>Tasks</b></a><br>\n" +
			"<a href=\"../reference/reference.html\"><b>IusCL Object and Component Reference</b></a><br>\n" +
			"<a href=\"../samples/samples.html\"><b>Samples</b></a><br><br>\n" +
			"<a href=\"license.html\"><b>License</b></a><br>\n";
		userguideTextContent.addContent(tocContent);

		
		saveToFile(userguideTextContent.getContent(), chapterFolderName + "\\" + "userguide.html");
		
		/* License */
		IusCLHelpContent licenseTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "licenseTopic");
		
		licenseTextContent.addTitle("IusCL License");
		licenseTextContent.addShortDescriptionWithBar("License agreement information");
		
		licenseTextContent.addHeader("License");
		licenseTextContent.addContent("$license");
		
		saveToFile(licenseTextContent.getContent(), chapterFolderName + "\\" + "license.html");
		
		
		/* Save TOC */
		Format jdomSerializeFormat = Format.getRawFormat();
		jdomSerializeFormat.setIndent("  ");
		jdomSerializeFormat.setLineSeparator("\n");
		XMLOutputter jdomSerializer = new XMLOutputter(jdomSerializeFormat);
		IusCLStrings tocStrings = new IusCLStrings();
		tocStrings.setText(jdomSerializer.outputString(jdomTocDocument));
		tocStrings.saveToFile(tocsDestFolder + "toc_userguide.xml");
	}
}
