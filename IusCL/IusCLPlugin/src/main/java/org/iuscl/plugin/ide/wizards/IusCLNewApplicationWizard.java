/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.ide.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.iuscl.classes.IusCLStrings;
import org.iuscl.plugin.IusCLPlugin;
import org.iuscl.plugin.ide.IusCLDesignException;
import org.iuscl.plugin.ide.IusCLDesignIDE;
import org.iuscl.plugin.preferences.IusCLDesignOptions;
import org.iuscl.sysutils.IusCLFileSearchRec;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLNewApplicationWizard extends Wizard implements INewWizard {
	
	private IusCLNewApplicationWizardPage newApplicationWizardPage;
	private ISelection selection;
	
	private IFile javaFormFile;
	private IFile fmFormFile;

	/* **************************************************************************************************** */
	public IusCLNewApplicationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/* **************************************************************************************************** */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
		this.selection = selection;
		this.setWindowTitle("New IusCL Application");
		this.setDefaultPageImageDescriptor(ImageDescriptor.createFromURL(this.getClass().
				getResource("/resources/images/IusCLApplicationDesignWizardBanner.gif")));
	}
	
	/* **************************************************************************************************** */
	public void addPages() {
		
		newApplicationWizardPage = new IusCLNewApplicationWizardPage(selection);
		addPage(newApplicationWizardPage);
	}

	/* **************************************************************************************************** */
	public boolean performFinish() {

		final String projectName = newApplicationWizardPage.getProjectName();
		final String formName = newApplicationWizardPage.getFormName();
		final String packageName = newApplicationWizardPage.getPackageName();
		final String projectPath = newApplicationWizardPage.getProjectPath();


		final IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		/* Create the new project operation */
	    IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
	    	
	    	public void run(IProgressMonitor monitor) throws InvocationTargetException {

    			monitor.beginTask("Eclipse generate Java project...", 100);

    			String varFormName = formName.substring(0, 1).toLowerCase() + formName.substring(1); 
    			String formWidth = IusCLDesignOptions.getNewFormFormWidth();
    			String formHeight = IusCLDesignOptions.getNewFormFormHeight();
    			String formLeft = IusCLDesignOptions.getNewFormFormLeft();
    			String formTop = IusCLDesignOptions.getNewFormFormTop();

	    		try {

	    			/* Create the generic project in workspace */
	    			String projectFolder = projectPath;
	    			
	    			File file = new File(projectFolder);
	    			URI projectLocation = file.toURI();
	    			
	    			projectFolder = IusCLFileUtils.includeTrailingPathDelimiter(projectFolder);
	    			String ps = IusCLFileUtils.getPathDelimiter(); 
	    			

	    			final IProjectDescription projectDescription = ResourcesPlugin.getWorkspace().newProjectDescription(projectHandle.getName());
	    			projectDescription.setLocationURI(projectLocation);
	    			
	    			CreateProjectOperation createProjectOperation = new CreateProjectOperation(projectDescription, "New Project");
	    			PlatformUI.getWorkbench().getOperationSupport().getOperationHistory().execute(
	    					createProjectOperation, monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
	    			
	    			monitor.beginTask("Customize the project as IusCL application", 100);
	    			
	    		    /* We have the project created */
	    	    	IResource projectResource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(projectName));
	    	    	
	    	    	/* .project */
	    	    	monitor.subTask(".project");
	    	    	monitor.worked(10);
	    	    	
	    	    	String projectContents = IusCLDesignIDE.loadTextFromResource("IusCLDesignNewProject.project.xml");
	    	    	projectContents = projectContents.replace("${project}", projectName);
	    	    	IusCLStrUtils.saveStringToFile(projectContents, projectFolder + ".project");

	    	    	monitor.subTask("lib");
	    	    	monitor.worked(30);
	    			/* classes */
	    	    	IusCLFileUtils.createFolder(projectFolder + "classes");
	    			
	    			/* lib */
	    			String pluginFolder = IusCLPlugin.getPluginFolder();

	    	    	String libFolder = projectFolder + "lib" + ps + "iuscl-packages";
	    	    	IusCLFileUtils.createFolder(libFolder);
	    	    	
	    			String libPackagesFolder = pluginFolder + 
	    					ps + "dist" + ps + "application" + ps + "lib" + ps + "iuscl-packages";

	    			IusCLFileUtils.copyFolderContent(libPackagesFolder, libFolder);

	    			/* .classpath */
	    	    	monitor.subTask(".classpath");
	    	    	monitor.worked(10);
	    	    	
	    			String classpathContents = IusCLDesignIDE.loadTextFromResource("IusCLDesignNewProject.classpath.xml");
	    			
	    			IusCLFileSearchRec fileSearchRec = new IusCLFileSearchRec();
	    			fileSearchRec.setIncludeNamePattern(".jar");
	    			IusCLStrings list = IusCLFileUtils.findFiles(libPackagesFolder, fileSearchRec);
	    			
	    			for (int index = 0; index < list.size(); index++) {
	    				
	    				String lineFile = list.get(index).replace("\\", "/");
	    				String libLine = "\t<classpathentry kind=\"lib\" path=\"" + 
	    						lineFile.substring(lineFile.indexOf("lib/iuscl-packages")) + 
	    						"\"/>" + 
	    						IusCLStrUtils.sLineBreak();
	    				
	    				classpathContents = classpathContents.replace("</classpath>", libLine + "</classpath>");
	    			}

	    			IusCLStrUtils.saveStringToFile(classpathContents, projectFolder + ".classpath");

	    			/* dist */
	    	    	monitor.subTask("dist");
	    	    	monitor.worked(10);
	    	    	
	    	    	String distFolder = projectFolder + "dist";
	    	    	IusCLFileUtils.createFolder(distFolder);

	    			String buildContents = IusCLDesignIDE.loadTextFromResource("IusCLDesignNewProjectDeployAnt.build.xml.xml");
	    			IusCLStrUtils.saveStringToFile(buildContents, distFolder + ps + "build.xml");
	    			
	    			/* src */
	    	    	monitor.subTask("Main form");
	    	    	monitor.worked(30);

	    	    	String srcFolder = projectFolder + "src" + ps + "main" + ps + "java";
	    	    	IusCLFileUtils.createFolder(srcFolder);
	    	    	
	    			String[] splits = packageName.split("\\."); 
	    			String srcPath = "src/main/java";
	    	    	
	    			for (int index = 0; index < splits.length; index++) {
	    				
	    				String childName = splits[index];
	    				srcPath = srcPath + "/" + childName;
	    				srcFolder = srcFolder + ps + childName;
	    				IusCLFileUtils.createFolder(srcFolder);
	    			}

	    			srcPath = srcPath + "/" + "iuscl/";
	    			srcFolder = srcFolder + ps + "iuscl";
	    			IusCLFileUtils.createFolder(srcFolder);
	    			
	    			/* Java Form */
	    	    	String javaFormContents = IusCLDesignIDE.loadTextFromResource("IusCLDesignNewForm.java.java");
	    			javaFormContents = javaFormContents.replace("${package}", packageName + ".iuscl");
	    			javaFormContents = javaFormContents.replace("${formShortClassName}", formName);
	    			//javaFormContents = javaFormContents.replace("${formVar}", varFormName);
	    			javaFormContents = javaFormContents.replace("${parentFormCanonicalClassName}", "org.iuscl.forms.IusCLForm");
	    			javaFormContents = javaFormContents.replace("${parentFormShortClassName}", "IusCLForm");
	    			IusCLStrUtils.saveStringToFile(javaFormContents, srcFolder + ps + formName + ".java");

	    		    javaFormFile = projectHandle.getFile(srcPath + formName + ".java");
	    			
	    			/* FM Form */
	    	    	String dfmFormContents = IusCLDesignIDE.loadTextFromResource("IusCLDesignNewForm.iusclfm.xml");
	    			//dfmFormContents = dfmFormContents.replace("${form}", varFormName);

	    	    	dfmFormContents = dfmFormContents.replace("${name}", varFormName);
	    	    	
	    	    	dfmFormContents = dfmFormContents.replace("${caption}", formName + " Caption");
	    	    	
	    			dfmFormContents = dfmFormContents.replace("${height}", formHeight);
	    			dfmFormContents = dfmFormContents.replace("${left}", formLeft);
	    			dfmFormContents = dfmFormContents.replace("${width}", formWidth);
	    			dfmFormContents = dfmFormContents.replace("${top}", formTop);
	    	    	IusCLStrUtils.saveStringToFile(dfmFormContents, srcFolder + ps + formName + ".iusclfm");

	    	    	fmFormFile = projectHandle.getFile(srcPath + formName + ".iusclfm");
	    			
	    			/* Java Project */
	    			String javaProjectContents = IusCLDesignIDE.loadTextFromResource("IusCLDesignNewProject.java.java");
	    			javaProjectContents = javaProjectContents.replace("${package}", packageName + ".iuscl");
	    			javaProjectContents = javaProjectContents.replace("${project}", projectName);
	    			javaProjectContents = javaProjectContents.replace("${form}", formName);
	    			javaProjectContents = javaProjectContents.replace("${formVar}", varFormName);
	    			IusCLStrUtils.saveStringToFile(javaProjectContents, srcFolder + ps + projectName + ".java");
	    			
	    			/* launch */
	    			String launchContents = IusCLDesignIDE.loadTextFromResource("IusCLDesignNewProject.launch.xml");
	    			launchContents = launchContents.replace("${package}", packageName + ".iuscl");
	    			launchContents = launchContents.replace("${project}", projectName);
	    			String projectClassPath = "/" + projectName + "/src/main/java/" + projectName + ".java";
	    			launchContents = launchContents.replace("${path}", projectClassPath);
	    			IusCLStrUtils.saveStringToFile(launchContents, projectFolder + ps + projectName + ".launch");
	    			
	    			/* refresh */
	    	    	monitor.worked(5);
	    	    	monitor.subTask("refresh workspace");

	    			projectResource.refreshLocal(IResource.DEPTH_INFINITE, null);
	    			
	    			monitor.subTask("build");
	    		}
	    		catch (CoreException coreException) {
	    				
	    		   	IusCLDesignException.error("CoreException in wizard", coreException);
	    		}
	    		catch (ExecutionException executionException) {
	    			
	    			IusCLDesignException.error("ExecutionException in wizard", executionException);
	    		}
	    	}
	    };
	    
	    /* Run the new project creation operation */
	    try {
	    	
	    	getContainer().run(true, true, runnableWithProgress);
	    }
		catch (InterruptedException interruptedException) {
			
	    	IusCLDesignException.error("InterruptedException in wizard", interruptedException);
		}
		catch (InvocationTargetException invocationTargetException) {
			
	    	Throwable realException = invocationTargetException.getTargetException();
	    	IusCLDesignException.error("Invocation Target Exception", realException);
		} 

		/* Build project */
	    IusCLNewFormWizard.buildAndShowForm(projectHandle, javaFormFile, fmFormFile);

	    return true;
	}
}