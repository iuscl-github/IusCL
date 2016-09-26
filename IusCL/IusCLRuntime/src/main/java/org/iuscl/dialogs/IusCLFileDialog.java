/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.FileDialog;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLStrings;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLFileDialog extends IusCLCommonDialog {

	/* SWT */
	protected FileDialog swtFileDialog = null;
	protected int createParam = 0;
	
	/* Properties */
	private String title = "";
	private String fileName = "";
	private IusCLStrings files = new IusCLStrings();
	private String filter = "";
	private Integer filterIndex = 0;
	private String initialDir = "";
	private Boolean overwritePrompt = true;
	private Boolean allowMultiSelect = false;
	
	/* Events */
	
	/* **************************************************************************************************** */
	public IusCLFileDialog(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Title", IusCLPropertyType.ptString, "Open");
		defineProperty("FileName", IusCLPropertyType.ptString, "");
		defineProperty("Filter", IusCLPropertyType.ptFilter, "");
		defineProperty("InitialDir", IusCLPropertyType.ptString, "");
		defineProperty("FilterIndex", IusCLPropertyType.ptInteger, "0");
		defineProperty("OverwritePrompt", IusCLPropertyType.ptBoolean, "true");
		defineProperty("AllowMultiSelect", IusCLPropertyType.ptBoolean, "false");
		
		/* Events */
	}
	
	/* **************************************************************************************************** */
	@Override
	public Boolean execute() {
		
		Boolean result = false;
		String resultString = swtFileDialog.open();
		if (resultString != null) {
			
			String filePath = IusCLFileUtils.includeTrailingPathDelimiter(swtFileDialog.getFilterPath());

			fileName = filePath + swtFileDialog.getFileName();
			
			for (int index = 0; index < swtFileDialog.getFileNames().length; index++) {
				
				files.add(filePath + swtFileDialog.getFileNames()[index]);
			}
			
			result = true;
		}
		
		return result;
	}
	
	/* **************************************************************************************************** */
	@Override
	public Dialog getSwtDialog() {
		
		return swtFileDialog;
	}

	public String getTitle() {
		return title;
	}

	/* **************************************************************************************************** */
	public void setTitle(String title) {
		this.title = title;
		
		if (swtFileDialog != null) {
			
			swtFileDialog.setText(title);
		}
	}

	public String getFileName() {
		return fileName;
	}

	/* **************************************************************************************************** */
	public void setFileName(String fileName) {
		this.fileName = fileName;
		
		if (swtFileDialog != null) {
			
			swtFileDialog.setFileName(fileName);
		}
	}

	public String getFilter() {
		return filter;
	}

	/* **************************************************************************************************** */
	public void setFilter(String filter) {
		this.filter = filter;
		
		if (IusCLStrUtils.isNotNullNotEmpty(filter)) {
			
			String[] filters = filter.split("\\|");
			String[] filterNames = new String[filters.length / 2];
			String[] filterExtensions = new String[filters.length / 2];
			
			for (int index = 0; index < filters.length / 2; index++) {
				
				filterNames[index] = filters[index * 2];
				filterExtensions[index] = filters[index * 2 + 1];
			}
			swtFileDialog.setFilterNames(filterNames);
			swtFileDialog.setFilterExtensions(filterExtensions);
		}
		else {
			
			swtFileDialog.setFilterNames(null);
			swtFileDialog.setFilterExtensions(null);
		}
	}

	public Integer getFilterIndex() {
		return filterIndex;
	}

	/* **************************************************************************************************** */
	public void setFilterIndex(Integer filterIndex) {
		this.filterIndex = filterIndex;

		if (swtFileDialog != null) {
			
			swtFileDialog.setFilterIndex(filterIndex);
		}
	}

	public String getInitialDir() {
		return initialDir;
	}

	/* **************************************************************************************************** */
	public void setInitialDir(String initialDir) {
		this.initialDir = initialDir;
		
		if (swtFileDialog != null) {
			
			swtFileDialog.setFilterPath(initialDir);
		}
	}

	public IusCLStrings getFiles() {
		return files;
	}

	public Boolean getOverwritePrompt() {
		return overwritePrompt;
	}

	/* **************************************************************************************************** */
	public void setOverwritePrompt(Boolean overwritePrompt) {
		this.overwritePrompt = overwritePrompt;

		if (swtFileDialog != null) {
			
			swtFileDialog.setOverwrite(overwritePrompt);
		}
	}

	public Boolean getAllowMultiSelect() {
		return allowMultiSelect;
	}

	/* **************************************************************************************************** */
	public void setAllowMultiSelect(Boolean allowMultiSelect) {
		
		if (this.allowMultiSelect != allowMultiSelect) {
			
			this.allowMultiSelect = allowMultiSelect;

			int createParams = createParam;
			if (allowMultiSelect == true) {
			
				createParams = createParams | SWT.MULTI;
			}
			
			swtFileDialog = new FileDialog(this.findForm().getSwtShell(), createParams);
			
			assign();
		}
	}
}
