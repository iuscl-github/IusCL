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
public class IusCLHelpConcepts extends IusCLHelpChapter {

	/* **************************************************************************************************** */
	public static void main(String[] args) {
		
		IusCLHelpConcepts helpConcepts = new IusCLHelpConcepts(args[0]);
		
		helpConcepts.generate("concepts", "IusCLHelpConcepts.txt");
	}
	
	/* **************************************************************************************************** */
	public IusCLHelpConcepts(String homeFolder) {

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
		tocElement.setAttribute("label", "Tasks");
		tocElement.setAttribute("link_to", tocsFolder + "toc_userguide.xml#" + "concepts");
		tocElement.setAttribute("topic", destHRef + chapterFolderName + ".html");
		jdomTocDocument.setRootElement(tocElement);
		
		Element whyandhowElement = new Element("topic");
		whyandhowElement.setAttribute("label", "IusCL - Why and How");
		whyandhowElement.setAttribute("href", destHRef + "whyandhow.html");
		tocElement.addContent(whyandhowElement);

		Element ctrcmpElement = new Element("topic");
		ctrcmpElement.setAttribute("label", "Controls, Components, Persistents");
		ctrcmpElement.setAttribute("href", destHRef + "ctrcmp.html");
		tocElement.addContent(ctrcmpElement);

		/* Root */
		IusCLHelpContent conceptsTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "conceptsTopic");
		
		conceptsTextContent.addTitle("Concepts");
		conceptsTextContent.addShortDescriptionWithBar("What's inside?");
		
		conceptsTextContent.addHeader("General concepts");
		
		String tocContent = "&nbsp;<br>\n" + 
			"<a href=\"whyandhow.html\"><b>IusCL - Why and How</b></a><br>\n" +
			"<a href=\"ctrcmp.html\"><b>Controls, Components, Persistents</b></a><br>\n";
		
		conceptsTextContent.addContent(tocContent);

		
		saveToFile(conceptsTextContent.getContent(), chapterFolderName + "\\" + "concepts.html");
		
		/* Why and how */
		IusCLHelpContent whyandhowTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "whyandhowTopic");
		
		whyandhowTextContent.addTitle("IusCL - Why and How");
		whyandhowTextContent.addShortDescriptionWithBar("Introduction to the idea of IusCL");
		
		whyandhowTextContent.addContent("$whyandhowContent");

		saveToFile(whyandhowTextContent.getContent(), chapterFolderName + "\\" + "whyandhow.html");
		
		/* Controls, Components, Persistents */
		IusCLHelpContent ctrcmpTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "ctrcmpTopic");
		
		ctrcmpTextContent.addTitle("Controls, Components, Persistents");
		ctrcmpTextContent.addShortDescriptionWithBar("Introduction to the IusCL hierarchy");
		
		ctrcmpTextContent.addContent("$ctrcmpContent");

		saveToFile(ctrcmpTextContent.getContent(), chapterFolderName + "\\" + "ctrcmp.html");
		
		/* Save TOC */
		Format jdomSerializeFormat = Format.getRawFormat();
		jdomSerializeFormat.setIndent("  ");
		jdomSerializeFormat.setLineSeparator("\n");
		XMLOutputter jdomSerializer = new XMLOutputter(jdomSerializeFormat);
		IusCLStrings tocStrings = new IusCLStrings();
		tocStrings.setText(jdomSerializer.outputString(jdomTocDocument));
		tocStrings.saveToFile(tocsDestFolder + "toc_concepts.xml");
	}
}
