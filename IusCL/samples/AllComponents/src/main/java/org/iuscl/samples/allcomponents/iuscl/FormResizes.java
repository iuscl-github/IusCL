/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allcomponents.iuscl;

import java.util.EnumSet;

import org.iuscl.buttons.IusCLBitButton;
import org.iuscl.comctrls.IusCLListView;
import org.iuscl.comctrls.IusCLToolBar;
import org.iuscl.comctrls.IusCLTreeView;
import org.iuscl.comctrls.IusCLWindowsStatusBar;
import org.iuscl.controls.IusCLSizeConstraints;
import org.iuscl.extctrls.IusCLBevel;
import org.iuscl.extctrls.IusCLPanel;
import org.iuscl.extctrls.IusCLSplitter;
import org.iuscl.forms.IusCLForm;
import org.iuscl.menus.IusCLMainMenu;
import org.iuscl.menus.IusCLMenuItem;
import org.iuscl.stdctrls.IusCLButton;
import org.iuscl.stdctrls.IusCLCheckBox;
import org.iuscl.stdctrls.IusCLGroupBox;
import org.iuscl.system.IusCLObject;
import org.iuscl.types.IusCLSize;

/* **************************************************************************************************** */
public class FormResizes extends IusCLForm {

	/* IusCL Components */
	public IusCLMainMenu mainMenu1;
	public IusCLMenuItem menuItem1;
	public IusCLWindowsStatusBar windowsStatusBar1;
	public IusCLToolBar toolBar1;
	public IusCLBevel bevel1;
	public IusCLBevel bevel2;
	public IusCLPanel panel1;
	public IusCLSplitter splitter1;
	public IusCLPanel panel2;
	public IusCLPanel panel3;
	public IusCLPanel panel4;
	public IusCLTreeView treeView1;
	public IusCLSplitter splitter2;
	public IusCLListView listView1;
	public IusCLPanel panel5;

	public IusCLGroupBox groupBox1;

	public IusCLButton button1;


	public IusCLBitButton bitButton1;


	public IusCLCheckBox checkBox1;


	/** splitter2.OnMouseUp event implementation */
	public void splitter2MouseUp(IusCLObject sender, 
		IusCLMouseButton button, EnumSet<IusCLShiftState> shift, Integer x, Integer y) {

		System.out.println("splitter2MouseUp " + splitter2.getLeft());
	}
	/** groupBox1.OnConstrainedResize event implementation */
	public void groupBox1ConstrainedResize(IusCLObject sender, IusCLSizeConstraints newSizeConstraints) {

		newSizeConstraints.setMaxWidth(groupBox1.getHeight());
	}
	/** groupBox1.OnCanResize event implementation */
	public Boolean groupBox1CanResize(IusCLObject sender, IusCLSize newSize) {

		if (newSize.getWidth() <= 50) {
			
			return false;
		}
		return true;
	}
	/** form.OnClose event implementation */
	public IusCLCloseAction formClose(IusCLObject sender) {

		System.out.println("formClose " + this.getName());
		return IusCLCloseAction.caHide;
	}
	/** form.OnCloseQuery event implementation */
	public Boolean formCloseQuery(IusCLObject sender) {

		System.out.println("formCloseQuery " + this.getName());
		if (this.getWidth() < 300) {
			
			return false;
		}
		return true;
	}
	/** formResizes2.OnCloseQuery event implementation */
	public Boolean formResizes2CloseQuery(IusCLObject sender) {

		System.out.println("formCloseQuery " + this.getName());
		if (this.getWidth() < 300) {
			
			return false;
		}
		
		return true;
	}

}