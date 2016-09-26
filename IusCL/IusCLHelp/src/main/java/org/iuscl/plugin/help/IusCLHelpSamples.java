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
public class IusCLHelpSamples extends IusCLHelpChapter {

	/* **************************************************************************************************** */
	public static void main(String[] args) {
		
		IusCLHelpSamples helpSamples = new IusCLHelpSamples(args[0]);
		
		helpSamples.generate("samples", "IusCLHelpSamples.txt");
	}

	/* **************************************************************************************************** */
	public IusCLHelpSamples(String homeFolder) {

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
		tocElement.setAttribute("label", "Samples");
		tocElement.setAttribute("link_to", tocsFolder + "toc_userguide.xml#" + "samples");
		tocElement.setAttribute("topic", destHRef + chapterFolderName + ".html");
		jdomTocDocument.setRootElement(tocElement);
		
		Element allcomponentsElement = new Element("topic");
		allcomponentsElement.setAttribute("label", "AllComponents");
		allcomponentsElement.setAttribute("href", destHRef + "allcomponents.html");
		tocElement.addContent(allcomponentsElement);

		Element allpdfElement = new Element("topic");
		allpdfElement.setAttribute("label", "AllPdf");
		allpdfElement.setAttribute("href", destHRef + "allpdf.html");
		tocElement.addContent(allpdfElement);

		/* Root */
		IusCLHelpContent samplesTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "samplesTopic");
		
		samplesTextContent.addTitle("Samples");
		samplesTextContent.addShortDescriptionWithBar("$shortDescription");
		
		samplesTextContent.addHeader("General samples");
		
		String tocContent = "&nbsp;<br>\n" + 
				"<a href=\"allcomponents.html\"><b>AllComponents</b></a><br>\n" +
				"<a href=\"allpdf.html\"><b>AllPdf</b></a><br>\n";
		
		samplesTextContent.addContent(tocContent);

		saveToFile(samplesTextContent.getContent(), chapterFolderName + "\\" + "samples.html");

		/* AllComponents */
		IusCLHelpContent allcomponentsTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "allComponentsTopic");
		
		allcomponentsTextContent.addTitle("AllComponents <i>sample project</i>");
		allcomponentsTextContent.addShortDescriptionWithBar("$shortDescription");
		
		allcomponentsTextContent.addHeader("About");
		allcomponentsTextContent.addContent("$about");

		saveToFile(allcomponentsTextContent.getContent(), chapterFolderName + "\\" + "allcomponents.html");

		/* AllPdf */
		IusCLHelpContent allpdfTextContent = new IusCLHelpContent(homeFolder, srcText, srcFile, "allPdfTopic");
		
		allpdfTextContent.addTitle("AllPdf <i>sample project</i>");
		allpdfTextContent.addShortDescriptionWithBar("$shortDescription");
		
		allpdfTextContent.addHeader("About");
		allpdfTextContent.addContent("$about");

		saveToFile(allpdfTextContent.getContent(), chapterFolderName + "\\" + "allpdf.html");

		/* Save TOC */
		Format jdomSerializeFormat = Format.getRawFormat();
		jdomSerializeFormat.setIndent("  ");
		jdomSerializeFormat.setLineSeparator("\n");
		XMLOutputter jdomSerializer = new XMLOutputter(jdomSerializeFormat);
		IusCLStrings tocStrings = new IusCLStrings();
		tocStrings.setText(jdomSerializer.outputString(jdomTocDocument));
		tocStrings.saveToFile(tocsDestFolder + "toc_samples.xml");
	}
}
