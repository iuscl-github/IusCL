/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.help;

import org.iuscl.forms.IusCLForm;
import org.iuscl.sysctrls.IusCLZip;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLProgressMonitor;

/* **************************************************************************************************** */
public class IusCLHelpPluginAll {

	/* **************************************************************************************************** */
	public static void main(String[] args) {

		class IusCLDocProgressMonitor extends IusCLProgressMonitor {
			/* **************************************************************************************************** */
			@Override
			public void setPosition(Integer position) {
				super.setPosition(position);
				
				System.out.println(getPositionAsPercent());
			}
		}

		if (args.length == 0) {
			
			System.out.println("No home folder as argument");
			/* JVM bug */
			System.exit(0);
		}
		
		String homeFolder = args[0];
				
		IusCLFileUtils.deleteFolderIncludingName(homeFolder + "\\IusCLHelp\\help-dest\\doc");
		IusCLFileUtils.deleteFolderIncludingName(homeFolder + "\\IusCLHelp\\help-dest\\help");
//		IusCLFileUtils.deleteFolderContent(homeFolder + "\\IusCLHelp\\_sf\\project-web\\iuscl\\htdocs\\help");

		
		IusCLHelpUserGuide helpUserGuide = new IusCLHelpUserGuide(homeFolder);
		helpUserGuide.generate("userguide", "IusCLHelpUserGuide.txt");

		IusCLHelpGettingStarted helpGettingStarted = new IusCLHelpGettingStarted(homeFolder);
		helpGettingStarted.generate("gettingstarted", "IusCLHelpGettingStarted.txt");

		IusCLHelpConcepts helpConcepts = new IusCLHelpConcepts(homeFolder);
		helpConcepts.generate("concepts", "IusCLHelpConcepts.txt");

		IusCLHelpReference helpReference = new IusCLHelpReference(homeFolder);
		helpReference.generate("reference", "IusCLHelpReference.txt");
		
		IusCLHelpTasks helpTasks = new IusCLHelpTasks(homeFolder);
		helpTasks.generate("tasks", "IusCLHelpTasks.txt");

		IusCLHelpSamples helpSamples = new IusCLHelpSamples(homeFolder);
		helpSamples.generate("samples", "IusCLHelpSamples.txt");
		
		
		String helpDestFolder = homeFolder + "\\IusCLHelp\\help-dest\\";
		String pluginFolder = homeFolder + "\\IusCLPlugin\\";
		
		/* TOCS, INDEXES */
		IusCLFileUtils.copyFolderContent(helpDestFolder + "help\\_indexes", pluginFolder + "help\\_indexes");
		IusCLFileUtils.copyFolderContent(helpDestFolder + "help\\_tocs", pluginFolder + "help\\_tocs");
		
		/* DOC */
		System.out.println("Start zipping...");
		IusCLForm form = new IusCLForm();
		IusCLZip zip = new IusCLZip(form);
		zip.setZipFileName(pluginFolder + "doc.zip");
		
		IusCLDocProgressMonitor docProgressMonitor = new IusCLDocProgressMonitor();
		zip.addFolderContent(helpDestFolder + "doc", docProgressMonitor, null);
		System.out.println("All done.");
	}

}
