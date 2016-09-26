/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.forms;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLOS;
import org.iuscl.classes.IusCLPersistent;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLMessageEvent;
import org.iuscl.graphics.IusCLPicture;
import org.iuscl.obj.IusCLObjUtils;
import org.iuscl.obj.IusCLParam;
import org.iuscl.sysctrls.IusCLApplicationEvents;
import org.iuscl.system.IusCLLog;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLStrUtils;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

/* **************************************************************************************************** */
public class IusCLApplication extends IusCLComponent {

	private static Display swtDisplay = null;
	private static Shell swtApplicationShell = null;

	private static Integer hintPause = 500;
	
	private static String title = "IusCL Application";
	private static String initialDir = IusCLFileUtils.getCurrentFolder();
	private static IusCLPicture icon = null;

	private static Boolean run = false;
	
	
	private static Hashtable<String, String> fmFiles = new Hashtable<String, String>();
	
	private static ArrayList<String> refComponentNames = new ArrayList<String>();
	private static ArrayList<IusCLPersistent> refComponentDestinations = new ArrayList<IusCLPersistent>();
	private static ArrayList<String> refComponentPropertyNames = new ArrayList<String>();

	private static Boolean putNextCreatedFormInDesignMode = false;

	/* Application events */
	private static ArrayList<IusCLApplicationEvents> applicationEvents = new ArrayList<IusCLApplicationEvents>();

	/* Forms */
	private static ArrayList<IusCLForm> forms = new ArrayList<IusCLForm>();
	private static IusCLForm activeForm = null;
	private static IusCLForm mainForm = null; 
	
	/* **************************************************************************************************** */
	static {
		
		swtDisplay = Display.getCurrent();
		if (swtDisplay == null) {
			
			swtDisplay = Display.getDefault();
			if (swtDisplay == null) {
				
				swtDisplay = new Display();	
			}
		}

		swtApplicationShell = new Shell(swtDisplay, SWT.NO_TRIM);
		swtApplicationShell.setBounds(0, 0, 0, 0);

		IusCLScreen.calculateMetrics();
	}
	
	/* **************************************************************************************************** */
	public IusCLApplication() {
		super(null);
	}	
	
	/* **************************************************************************************************** */
	public static void initialize() {
		/* ? (event?) */
	}

	/* **************************************************************************************************** */
	public static void run() {

		if (mainForm != null) {
			
			mainForm.show();
			run = true;

//			while(!mainForm.getSwtShell().isDisposed()) {
			while(run) {
				
				handleMessage();
		    }
			terminate();
		}
	}

	/* **************************************************************************************************** */
	public static Boolean processMessage() {
		
		return swtDisplay.readAndDispatch();
	}

	/* **************************************************************************************************** */
	public static void idle() {
		
		swtDisplay.sleep();
	}

	/* **************************************************************************************************** */
	public static void processMessages() {
		
		/* To be used from outside, "doEvents" */
		
		while (processMessage()) {
			/* do */
		}
	}

	/* **************************************************************************************************** */
	public static void handleMessage() {

		/* Application events */
		if (applicationEvents.size() > 0) {

			Object msg = IusCLOS.osIusCLApplication_PeekMessage();
			
			for (int index = 0; index < applicationEvents.size(); index++) {
				
				IusCLApplicationEvents appEvents = applicationEvents.get(index);
				
				IusCLMessageEvent messageEvent = appEvents.getOnMessage();
				if (IusCLEvent.isDefinedEvent(messageEvent)) {
					
					Boolean handled = messageEvent.invoke(appEvents.findForm(), msg);
					if (handled == true) {
						
						break;
					}
				}
			}
		}
		
		if (processMessage() == false)
		{
			idle();
		}
	}

	/* **************************************************************************************************** */
	public static void terminate() {
		
		swtDisplay.dispose();
	}

	/* **************************************************************************************************** */
	private static IusCLPicture loadDefaultIcon() {
		
		IusCLPicture defaultIcon = new IusCLPicture();
		
		if (IusCLStrUtils.equalValues(title, "Iustin")) {
			
			defaultIcon.loadFromResource(IusCLApplication.class, "resources/icons/iustin.ico");
		}
		else {
			
			defaultIcon.loadFromResource(IusCLApplication.class, "resources/icons/IusCLMainIcon.ico");	
		}
		return defaultIcon;
	}

	/* **************************************************************************************************** */
	public static Shell getSwtApplicationShell() {
		
		return swtApplicationShell;
	}

	public Integer getHintPause() {
		return hintPause;
	}

	public void setHintPause(Integer hintPause) {
		IusCLApplication.hintPause = hintPause;
	}

	public static IusCLForm getMainForm() {
		return mainForm;
	}

	public static void setMainForm(IusCLForm mainForm) {
		
		IusCLApplication.mainForm = mainForm;
	}

	/* **************************************************************************************************** */
	public static void addForm(IusCLForm form) {
		
		if (forms.contains(form)) {
			
			return;
		}
		
		forms.add(form);
	}

	/* **************************************************************************************************** */
	public static void removeForm(IusCLForm form) {
		
		if (forms.contains(form)) {
			
			forms.remove(form);
		}
	}
	
	public static ArrayList<IusCLForm> getForms() {
		return forms;
	}

	public static IusCLForm getActiveForm() {
		return activeForm;
	}

	/* **************************************************************************************************** */
	public synchronized static void setActiveForm(IusCLForm activeForm) {
		
		IusCLApplication.activeForm = activeForm;
	}

	/* **************************************************************************************************** */
	public static Display getSwtDisplay() {
		
		return swtDisplay;
	}
	
	public static void setDisplay(Display display) {
		IusCLApplication.swtDisplay = display;
	}

	public static String getTitle() {
		return title;
	}

	public static void setTitle(String title) {
		IusCLApplication.title = title;
	}

	public static String getInitialDir() {
		return initialDir;
	}

	public static void setInitialDir(String initialDir) {
		IusCLApplication.initialDir = initialDir;
	}

	public synchronized static Boolean getPutNextCreatedFormInDesignMode() {
		return putNextCreatedFormInDesignMode;
	}

	public synchronized static void setPutNextCreatedFormInDesignMode(Boolean putNextCreatedFormInDesignMode) {
		IusCLApplication.putNextCreatedFormInDesignMode = putNextCreatedFormInDesignMode;
	}

	/* **************************************************************************************************** */
	public static Boolean getIsRunning() {
		
		return run;
	}

	/* **************************************************************************************************** */
	public static void setIsTerminated() {
		
		run = false;
	}

	/* **************************************************************************************************** */
	public static IusCLPicture getIcon() {
		
		if (icon == null) {
			icon = loadDefaultIcon();
		}
		
		return icon;
	}

	public static void setIcon(IusCLPicture icon) {
		
		IusCLApplication.icon = icon;
	}

	/* **************************************************************************************************** */
	public static void putFMFile(String formClassName, String formFMFile) {
		
		fmFiles.put(formClassName, formFMFile);
	}

	/* **************************************************************************************************** */
	public static String getFMFile(String formClassName) {
		
		return fmFiles.get(formClassName);
	}

	/* **************************************************************************************************** */
	public static String getFormsResFolder(Class<?> formClass) {
		
		String fmFile = fmFiles.get(formClass.getCanonicalName()); 
		
		if (fmFile == null) {
			
			return null;
		}
		
		String resName = "resources/forms";
		String sep = IusCLFileUtils.getPathDelimiter(); 
		String formsResFolder = fmFile.substring(0, fmFile.indexOf("src")) + resName.replace("/", sep) + sep;

		return formsResFolder;
	}

	/* **************************************************************************************************** */
	public static void putRefComponent(String refComponentName, IusCLPersistent refComponentDestination,
			String refComponentPropertyName) {

		refComponentNames.add(refComponentName);
		refComponentDestinations.add(refComponentDestination);
		refComponentPropertyNames.add(refComponentPropertyName);
	}

	/* **************************************************************************************************** */
	public static void findRefComponent(IusCLComponent refComponent) {

		int index = refComponentNames.indexOf(refComponent.getName());
		
		while (index > -1) {
			
			String propertyName = refComponentPropertyNames.get(index);
			IusCLPersistent destinationPersistent = refComponentDestinations.get(index);
				
			destinationPersistent.setPropertyValueInvoke(propertyName, 
					new IusCLParam(destinationPersistent.getProperty(propertyName).getRefClass(), refComponent));
			
			refComponentNames.remove(index);
			refComponentDestinations.remove(index);
			refComponentPropertyNames.remove(index);

			index = refComponentNames.indexOf(refComponent.getName());
		}
	}

	/* **************************************************************************************************** */
	public static List<?> getItemsFromFormResource(Class<?> relativeClass, String resFormAndFileName) {

		SAXBuilder jdomBuilder = new SAXBuilder();
		Document jdomDocument = null;
		
		String formsResFolder = getFormsResFolder(relativeClass);

		try {
			if (formsResFolder == null) {
				/* Runtime, resource */
				String resJarFileName = "resources/forms/" + resFormAndFileName;
				InputStream inputStream = relativeClass.getClassLoader().getResourceAsStream(resJarFileName);
				if (inputStream == null) {
					
					return null;
				}
				jdomDocument = jdomBuilder.build(inputStream);
			}
			else {
				/* Design time, resources on disk */
				String resFileName = formsResFolder + resFormAndFileName.replace("/", IusCLFileUtils.getPathDelimiter());
				if (!(IusCLFileUtils.fileExists(resFileName))) {
					
					return null;
				}
				jdomDocument = jdomBuilder.build(new File(resFileName));
			}
		} 
		catch (Exception jdomException) {
			
			IusCLLog.logError("jdom XML exception in getting resource items", jdomException);
			return null;
		}

		return jdomDocument.getRootElement().getChildren();
	}

	/* **************************************************************************************************** */
	public static void loadFromFormResource(Object objectInstance, Class<?> relativeClass, String resFormAndFileName) {
		
		String formsResFolder = getFormsResFolder(relativeClass);

		if (formsResFolder == null) {
			/* Runtime, resource */
			String resJarFileName = "resources/forms/" + resFormAndFileName;
			
			IusCLObjUtils.invokeMethod(objectInstance, "loadFromResource", 
					new IusCLParam(Class.class, relativeClass),
					new IusCLParam(String.class, resJarFileName));
		}
		else {
			/* Design time, resources on disk */
			String resFileName = formsResFolder + resFormAndFileName.replace("/", IusCLFileUtils.getPathDelimiter());
			
			IusCLObjUtils.invokeMethod(objectInstance, "loadFromFile", 
					new IusCLParam(String.class, resFileName));
		}
	}

	/* **************************************************************************************************** */
	public static void loadFromApplicationResource(Object objectInstance, Class<?> relativeClass, String resSimpleFileName) {
		
		String formsResFolder = getFormsResFolder(relativeClass);

		if (formsResFolder == null) {
			/* Runtime, resource */
			String resFileName = "resources/application/" + resSimpleFileName;
			
			IusCLObjUtils.invokeMethod(objectInstance, "loadFromResource", 
					new IusCLParam(Class.class, relativeClass),
					new IusCLParam(String.class, resFileName));
		}
		else {
			/* Design time, resources on disk */
			String resFileFolder = IusCLStrUtils.subStringBetween(formsResFolder, null, "forms");
			String resFileName = IusCLFileUtils.includeTrailingPathDelimiter(resFileFolder) + 
				"application" + IusCLFileUtils.getPathDelimiter() + resSimpleFileName;
			
			if (IusCLFileUtils.fileExists(resFileName)) {
				
				IusCLObjUtils.invokeMethod(objectInstance, "loadFromFile", 
						new IusCLParam(String.class, resFileName));
			}
		}
	}

	public static ArrayList<IusCLApplicationEvents> getApplicationEvents() {
		return applicationEvents;
	}
	
}
