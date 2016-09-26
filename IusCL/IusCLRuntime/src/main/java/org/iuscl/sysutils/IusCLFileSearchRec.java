/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.sysutils;

/* **************************************************************************************************** */
public class IusCLFileSearchRec {

//    private Integer time;
//    private Integer size;
//    private Integer includeAttr;
//    private Integer excludeAttr;

    private String includeNamePattern = null;
    private String excludeNamePattern = null;

    private Boolean returnFiles = true;
    private Boolean returnFolders = false;

    private Boolean isRecursive = true;
    private Boolean isSorted = true;

    //Mode: mode_t;
    //FindHandle: Pointer;
    
//    private String pathOnly;
//    private String pattern;
    
	/* **************************************************************************************************** */
	public String getIncludeNamePattern() {
		return includeNamePattern;
	}
	
	public void setIncludeNamePattern(String includeNamePattern) {
		this.includeNamePattern = includeNamePattern;
	}
	
	public String getExcludeNamePattern() {
		return excludeNamePattern;
	}
	
	public void setExcludeNamePattern(String excludeNamePattern) {
		this.excludeNamePattern = excludeNamePattern;
	}
	
	public Boolean getReturnFiles() {
		return returnFiles;
	}
	
	public void setReturnFiles(Boolean returnFiles) {
		this.returnFiles = returnFiles;
	}
	
	public Boolean getReturnFolders() {
		return returnFolders;
	}
	
	public void setReturnFolders(Boolean returnFolders) {
		this.returnFolders = returnFolders;
	}
	
	public Boolean getIsRecursive() {
		return isRecursive;
	}
	
	public void setIsRecursive(Boolean isRecursive) {
		this.isRecursive = isRecursive;
	}
	
	public Boolean getIsSorted() {
		return isSorted;
	}
	
	public void setIsSorted(Boolean isSorted) {
		this.isSorted = isSorted;
	}

}
