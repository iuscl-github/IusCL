/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.forms;

/* **************************************************************************************************** */
public class IusCLPluginApplicationDeploy {

	private String projectFolder = null;
	private String pluginFolder = null;
	private String distributionFolder = null;
	private String projectName = null;
	
	/* **************************************************************************************************** */
	public String getProjectFolder() {
		return projectFolder;
	}
	
	public void setProjectFolder(String projectFolder) {
		this.projectFolder = projectFolder;
	}
	
	public String getPluginFolder() {
		return pluginFolder;
	}
	
	public void setPluginFolder(String pluginFolder) {
		this.pluginFolder = pluginFolder;
	}
	
	public String getDistributionFolder() {
		return distributionFolder;
	}
	
	public void setDistributionFolder(String distributionFolder) {
		this.distributionFolder = distributionFolder;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
