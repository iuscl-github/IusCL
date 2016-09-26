/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.iuscl.plugin.IusCLPlugin;

/* **************************************************************************************************** */
public class IusCLDesignOptions extends AbstractPreferenceInitializer {

	private static final IPreferenceStore preferencesStore = IusCLPlugin.getDefault().getPreferenceStore();

	public static final String ENV_DISTRIBUTIONLOCATION = "P_ENV_DISTRIBUTIONLOCATION";

	public static final String NEWAPP_PROJECTNAME = "P_NEWAPP_PROJECTNAME";
	public static final String NEWAPP_PROJECTLOCATION = "P_NEWAPP_PROJECTLOCATION";
	public static final String NEWAPP_PACKAGENAME = "P_NEWAPP_PACKAGENAME";
	public static final String NEWAPP_FORMNAME = "P_NEWAPP_FORMNAME";

	public static final String NEWFORM_FORMNAME = "P_NEWFORM_FORMNAME";
	public static final String NEWFORM_WIDTH = "P_NEWFORM_WIDTH";
	public static final String NEWFORM_HEIGHT = "P_NEWFORM_HEIGHT";
	public static final String NEWFORM_LEFT = "P_NEWFORM_LEFT";
	public static final String NEWFORM_TOP = "P_NEWFORM_TOP";
	
	/* **************************************************************************************************** */
	public void initializeDefaultPreferences() {
		
		/* Environment */
		String distributionsLocation = "C:/IusCL_Distributions";
		if ("win32".equalsIgnoreCase(SWT.getPlatform())) {
			distributionsLocation = distributionsLocation.replace("/", "\\");
		}
		preferencesStore.setDefault(ENV_DISTRIBUTIONLOCATION, distributionsLocation);
		
		/* New application */
		preferencesStore.setDefault(NEWAPP_PROJECTNAME, "Application");

		String projectLocation = "C:/IusCL_Applications";
		if ("win32".equalsIgnoreCase(SWT.getPlatform())) {
			projectLocation = projectLocation.replace("/", "\\");
		}
		preferencesStore.setDefault(NEWAPP_PROJECTLOCATION, projectLocation);
		preferencesStore.setDefault(NEWAPP_PACKAGENAME, "com.application");
		preferencesStore.setDefault(NEWAPP_FORMNAME, "MainForm");
		
		/* New form */
		preferencesStore.setDefault(NEWFORM_FORMNAME, "Form");
		preferencesStore.setDefault(NEWFORM_WIDTH, "800");
		preferencesStore.setDefault(NEWFORM_HEIGHT, "640");
		preferencesStore.setDefault(NEWFORM_LEFT, "100");
		preferencesStore.setDefault(NEWFORM_TOP, "100");
		
	}

	/* **************************************************************************************************** */
	public static String getEnvDistributionLocation() {
		return preferencesStore.getString(ENV_DISTRIBUTIONLOCATION);
	}

	/* **************************************************************************************************** */
	public static String getNewAppProjectName() {
		return preferencesStore.getString(NEWAPP_PROJECTNAME);
	}

	/* **************************************************************************************************** */
	public static String getNewAppProjectLocation() {
		return preferencesStore.getString(NEWAPP_PROJECTLOCATION);
	}
	
	/* **************************************************************************************************** */
	public static String getNewAppPackageName() {
		return preferencesStore.getString(NEWAPP_PACKAGENAME);
	}
	
	/* **************************************************************************************************** */
	public static String getNewAppFormName() {
		return preferencesStore.getString(NEWAPP_FORMNAME);
	}
	
	/* **************************************************************************************************** */
	public static String getNewFormFormName() {
		return preferencesStore.getString(NEWFORM_FORMNAME);
	}
	
	/* **************************************************************************************************** */
	public static String getNewFormFormWidth() {
		return preferencesStore.getString(NEWFORM_WIDTH);
	}
	
	/* **************************************************************************************************** */
	public static String getNewFormFormHeight() {
		return preferencesStore.getString(NEWFORM_HEIGHT);
	}
	
	/* **************************************************************************************************** */
	public static String getNewFormFormLeft() {
		return preferencesStore.getString(NEWFORM_LEFT);
	}
	
	/* **************************************************************************************************** */
	public static String getNewFormFormTop() {
		return preferencesStore.getString(NEWFORM_TOP);
	}
	
}
