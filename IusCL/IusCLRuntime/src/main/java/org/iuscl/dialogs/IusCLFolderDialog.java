/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.sysutils.IusCLFileUtils;

/* **************************************************************************************************** */
public class IusCLFolderDialog extends IusCLCommonDialog {

	/* SWT */
	private DirectoryDialog swtDirectoryDialog = null; 
	
	/* Properties */
	private String title = "Browse For Folder";
	private String message = "";
	private String folderName = "";
	
	/* Events */
	
	/* **************************************************************************************************** */
	public IusCLFolderDialog(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Title", IusCLPropertyType.ptString, "Browse For Folder");
		defineProperty("Message", IusCLPropertyType.ptString, "");
		defineProperty("FolderName", IusCLPropertyType.ptString, "");
		
		/* Events */
		
		/* Create */
		swtDirectoryDialog = new DirectoryDialog(this.findForm().getSwtShell());
	}
	
	/* **************************************************************************************************** */
	@Override
	public Boolean execute() {
		
		Boolean result = false;
		String resultString = swtDirectoryDialog.open();
		if (resultString != null) {
			
			folderName = IusCLFileUtils.includeTrailingPathDelimiter(swtDirectoryDialog.getFilterPath());

			result = true;
		}
		
		return result;
	}
	
	/* **************************************************************************************************** */
	@Override
	public Dialog getSwtDialog() {
		
		return swtDirectoryDialog;
	}

	public String getTitle() {
		return title;
	}

	/* **************************************************************************************************** */
	public void setTitle(String title) {
		this.title = title;
		
		if (swtDirectoryDialog != null) {
			
			swtDirectoryDialog.setText(title);
		}
	}

	public String getFolderName() {
		return folderName;
	}

	/* **************************************************************************************************** */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
		
		if (swtDirectoryDialog != null) {
			
			swtDirectoryDialog.setFilterPath(folderName);
		}
	}

	public String getMessage() {
		return message;
	}

	/* **************************************************************************************************** */
	public void setMessage(String message) {
		this.message = message;
		
		if (swtDirectoryDialog != null) {
			
			swtDirectoryDialog.setMessage(message);
		}
	}
}
