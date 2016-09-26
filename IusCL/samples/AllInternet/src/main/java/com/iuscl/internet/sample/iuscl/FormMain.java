/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package com.iuscl.internet.sample.iuscl;

import org.iuscl.comctrls.IusCLToolBar;
import org.iuscl.extctrls.IusCLBevel;
import org.iuscl.extctrls.IusCLPanel;
import org.iuscl.forms.IusCLForm;
import org.iuscl.stdctrls.IusCLButton;
import org.iuscl.stdctrls.IusCLEdit;
import org.iuscl.stdctrls.IusCLLabel;
import org.iuscl.system.IusCLObject;
import org.iuscl.types.IusCLPoint;
import org.iuscl.types.IusCLSize;
import org.iuscl.web.IusCLInternetExplorer;
import org.iuscl.web.IusCLWebBrowser;

/** IusCL form class */
public class FormMain extends IusCLForm {

	/* IusCL Components */
	public IusCLPanel panel1;
	public IusCLToolBar toolBar1;
	public IusCLToolBar toolBar2;
	public IusCLPanel panel2;
	public IusCLLabel label1;
	public IusCLBevel bevel1;
	public IusCLPanel panel3;
	public IusCLPanel panel4;
	public IusCLButton button1;
	public IusCLEdit edit1;
	public IusCLEdit edit2;

	public IusCLEdit edit3;

	public IusCLInternetExplorer internetExplorer1;

	/** button1.OnClick event implementation */
	public void button1Click(IusCLObject sender) {

		internetExplorer1.navigate(edit1.getText());
	}
	/** internetExplorer1.OnBeforeNavigate event implementation */
	public Boolean internetExplorer1BeforeNavigate(IusCLObject sender, String location, Boolean thePage) {

		System.out.println("internetExplorer1BeforeNavigate = " + location);
		return true;
	}
	/** internetExplorer1.OnTitleChange event implementation */
	public void internetExplorer1TitleChange(IusCLObject sender) {

		label1.setCaption(internetExplorer1.getTitle());
	}
	/** internetExplorer1.OnNavigateComplete event implementation */
	public Boolean internetExplorer1NavigateComplete(IusCLObject sender, String location, Boolean thePage) {

		if (thePage == true) {
			
			edit2.setText(internetExplorer1.getLocation());
		}
		return true;
	}
	/** internetExplorer1.OnProgressChange event implementation */
	public void internetExplorer1ProgressChange(IusCLObject sender) {

		edit3.setText(internetExplorer1.getProgress() + " / " + internetExplorer1.getProgressMax());
	}
	/** internetExplorer1.OnOpenWindow event implementation */
	public IusCLWebBrowser internetExplorer1OpenWindow(IusCLObject sender, IusCLPoint location, IusCLSize size,
			Boolean menuBar, Boolean toolBar, Boolean addressBar, Boolean statusBar) {

		edit3.setText("internetExplorer1OpenWindow");
		return null;
	}

}