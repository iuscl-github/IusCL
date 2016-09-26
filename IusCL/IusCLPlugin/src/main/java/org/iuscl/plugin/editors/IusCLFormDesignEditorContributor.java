/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.editors;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

/* **************************************************************************************************** */
public class IusCLFormDesignEditorContributor extends MultiPageEditorActionBarContributor {
	
	private IEditorPart activeEditorPart;

	private static StatusLineContributionItem statusField;
	
	/* **************************************************************************************************** */
	public IusCLFormDesignEditorContributor() {
		super();
	}

	/* **************************************************************************************************** */
	public void setActivePage(IEditorPart part) {

		if (activeEditorPart == part) {
			
			return;
		}

		activeEditorPart = part;
	}

	/* **************************************************************************************************** */
	public void contributeToMenu(IMenuManager manager) {
		/*  */
	}

	/* **************************************************************************************************** */
	public void contributeToToolBar(IToolBarManager manager) {
		/*  */
	}

	/* **************************************************************************************************** */
	@Override
	public void contributeToStatusLine(IStatusLineManager statusLineManager) {
		
		statusField = new StatusLineContributionItem("control", 80);
		statusField.setText("");
	    statusLineManager.add(statusField);

		super.contributeToStatusLine(statusLineManager);
	}

	/* **************************************************************************************************** */
	public static StatusLineContributionItem getStatusField() {
		return statusField;
	}
}
