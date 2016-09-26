/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.help;

import org.iuscl.classes.IusCLStrings;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.ProcessingInstruction;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/* **************************************************************************************************** */
public class IusCLHelpGettingStarted extends IusCLHelpChapter {

	/* **************************************************************************************************** */
	public static void main(String[] args) {
		
		IusCLHelpGettingStarted helpGettingStarted = new IusCLHelpGettingStarted(args[0]);
		
		helpGettingStarted.generate("gettingstarted", "IusCLHelpGettingStarted.txt");
	}
	
	/* **************************************************************************************************** */
	public IusCLHelpGettingStarted(String homeFolder) {

		super(homeFolder);
	}

	/* **************************************************************************************************** */
	@Override
	public void generate(String chapterFolderName, String srcFileName) {

		super.generate(chapterFolderName, srcFileName);
		
		Document jdomTocDocument = new Document();
		jdomTocDocument.addContent(new ProcessingInstruction("NLS", "TYPE=\"org.eclipse.help.toc\""));

		/* TOC */
		Element tocElement = new Element("toc");
		tocElement.setAttribute("label", "GettingStarted");
		tocElement.setAttribute("link_to", tocsFolder + "toc_userguide.xml#" + "gettingstarted");
		tocElement.setAttribute("topic", destHRef + chapterFolderName + ".html");
		jdomTocDocument.setRootElement(tocElement);
		
		Element installElement = new Element("topic");
		installElement.setAttribute("label", "Install and verify");
		installElement.setAttribute("href", destHRef + "installverify.html");
		tocElement.addContent(installElement);

		Element faqElement = new Element("topic");
		faqElement.setAttribute("label", "Before you ask");
		faqElement.setAttribute("href", destHRef + "beforeyouask.html");
		tocElement.addContent(faqElement);

		/* Root */
		IusCLHelpContent gettingstartedTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "gettingstartedTopic");
		
		gettingstartedTextContent.addTitle("Getting started");
		gettingstartedTextContent.addShortDescriptionWithBar("Here are some help topics recommended to read first");
		
		gettingstartedTextContent.addHeader("General getting started");
		
		String tocContent = "&nbsp;<br>\n" + 
				"<a href=\"installverify.html\"><b>Install and verify</b></a><br>\n" +
				"<a href=\"beforeyouask.html\"><b>Before you ask</b></a><br>\n";
		
		gettingstartedTextContent.addContent(tocContent);
		
		saveToFile(gettingstartedTextContent.getContent(), chapterFolderName + "\\" + "gettingstarted.html");
		
		/* Install and verify */
		IusCLHelpContent installverifyTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "installverifyTopic");
		
		installverifyTextContent.addTitle("Install and verify");
		installverifyTextContent.addShortDescriptionWithBar("The first installation and a quick verification of the plug-in and the application");
		
		installverifyTextContent.addContent("$installverifyContent");
		
		saveToFile(installverifyTextContent.getContent(), chapterFolderName + "\\" + "installverify.html");

		/* Before you ask */
		IusCLHelpContent beforeyouaskTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "beforeyouaskTopic");
		
		beforeyouaskTextContent.addTitle("Before you ask");
		beforeyouaskTextContent.addShortDescriptionWithBar("Frequently asked questions");
		
		beforeyouaskTextContent.addHeader("$question1");
		beforeyouaskTextContent.addContent("$answer1");

		saveToFile(beforeyouaskTextContent.getContent(), chapterFolderName + "\\" + "beforeyouask.html");
		
		/* Save TOC */
		Format jdomSerializeFormat = Format.getRawFormat();
		jdomSerializeFormat.setIndent("  ");
		jdomSerializeFormat.setLineSeparator("\n");
		XMLOutputter jdomSerializer = new XMLOutputter(jdomSerializeFormat);
		IusCLStrings tocStrings = new IusCLStrings();
		tocStrings.setText(jdomSerializer.outputString(jdomTocDocument));
		tocStrings.saveToFile(tocsDestFolder + "toc_gettingstarted.xml");
	}
}
