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
public class IusCLHelpTasks extends IusCLHelpChapter {

	/* **************************************************************************************************** */
	public static void main(String[] args) {
		
		IusCLHelpTasks helpTasks = new IusCLHelpTasks(args[0]);
		
		helpTasks.generate("tasks", "IusCLHelpTasks.txt");
	}

	/* **************************************************************************************************** */
	public IusCLHelpTasks(String homeFolder) {

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
		tocElement.setAttribute("link_to", tocsFolder + "toc_userguide.xml#" + "tasks");
		tocElement.setAttribute("topic", destHRef + chapterFolderName + ".html");
		jdomTocDocument.setRootElement(tocElement);
		
		Element bugreportElement = new Element("topic");
		bugreportElement.setAttribute("label", "Bug reporting");
		bugreportElement.setAttribute("href", destHRef + "bugreport.html");
		tocElement.addContent(bugreportElement);

		/* Root */
		IusCLHelpContent tasksTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "tasksTopic");
		
		tasksTextContent.addTitle("Tasks");
		tasksTextContent.addShortDescriptionWithBar("Various tasks");
		
		tasksTextContent.addHeader("General");
		
		String tocContent = "&nbsp;<br>\n" + 
			"<a href=\"bugreport.html\"><b>Bug reporting</b></a><br>\n";
		
		tasksTextContent.addContent(tocContent);

		
		saveToFile(tasksTextContent.getContent(), chapterFolderName + "\\" + "tasks.html");
		
		/* Bug reporting */
		IusCLHelpContent bugreportTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "bugreportTopic");
		
		bugreportTextContent.addTitle("Bug reporting");
		bugreportTextContent.addShortDescriptionWithBar("How to open a defect");
		
		bugreportTextContent.addHeader("Procedure");
		bugreportTextContent.addContent("$bugreportingprocedure");

		saveToFile(bugreportTextContent.getContent(), chapterFolderName + "\\" + "bugreport.html");
		
		
		/* Save TOC */
		Format jdomSerializeFormat = Format.getRawFormat();
		jdomSerializeFormat.setIndent("  ");
		jdomSerializeFormat.setLineSeparator("\n");
		XMLOutputter jdomSerializer = new XMLOutputter(jdomSerializeFormat);
		IusCLStrings tocStrings = new IusCLStrings();
		tocStrings.setText(jdomSerializer.outputString(jdomTocDocument));
		tocStrings.saveToFile(tocsDestFolder + "toc_tasks.xml");
	}
}
