/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.forms;

/* **************************************************************************************************** */
public class IusCLPluginApplicationOptions {

	private String projectFolder;
	private String projectName;
	private String applicationTitle;
	private String applicationIcon;
	private String applicationVersion;
	
	/* **************************************************************************************************** */
	public String getProjectFolder() {
		return projectFolder;
	}
	
	public void setProjectFolder(String projectFolder) {
		this.projectFolder = projectFolder;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getApplicationTitle() {
		return applicationTitle;
	}
	
	public void setApplicationTitle(String applicationTitle) {
		this.applicationTitle = applicationTitle;
	}
	
	public String getApplicationIcon() {
		return applicationIcon;
	}
	
	public void setApplicationIcon(String applicationIcon) {
		this.applicationIcon = applicationIcon;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

}
