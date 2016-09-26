/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allcomponents.iuscl;

import org.eclipse.swt.internal.win32.MSG;
import org.iuscl.forms.IusCLForm;
import org.iuscl.menus.IusCLMainMenu;
import org.iuscl.menus.IusCLMenuItem;
import org.iuscl.system.IusCLObject;
import org.iuscl.comctrls.IusCLImageList;
import org.iuscl.dialogs.IusCLDialogs;
import org.iuscl.menus.IusCLPopupMenu;
import org.iuscl.stdctrls.IusCLButton;
import org.iuscl.extctrls.IusCLPanel;
import org.iuscl.sysctrls.IusCLApplicationEvents;
import java.lang.Object;

/* **************************************************************************************************** */
public class FormMain extends IusCLForm {

	/* IusCL Components */
	public IusCLMainMenu mainMenu;
	public IusCLMenuItem menuItem1;
	public IusCLMenuItem menuItem2;
	public IusCLMenuItem menuItem3;

	
	public IusCLMenuItem menuItem4;
	public IusCLImageList imageListMenu;
	public IusCLPopupMenu popupMenu;
	public IusCLMenuItem menuItem9;
	public IusCLMenuItem menuItem10;
	public IusCLMenuItem menuItem11;
	public IusCLMenuItem menuItem12;
	public IusCLMenuItem menuItem;
	public IusCLMenuItem menuItem13;
	public IusCLMenuItem menuItem14;
	public IusCLMenuItem menuItem15;
	public IusCLMenuItem menuItem16;
	public IusCLMenuItem menuItem5;
	public IusCLMenuItem menuItem6;
	public IusCLButton button1;
	public IusCLPanel panel1;
	public IusCLMenuItem menuItem7;
	public IusCLApplicationEvents applicationEventss;
	public IusCLButton btnApplicationEvents;
	
	/* Fields */
	private FormIusCLGraphicControl formIusCLGraphicControl = new FormIusCLGraphicControl();

	private FormComctrls formComctrls = new FormComctrls();

	private FormUtils formUtils = new FormUtils();

	private FormResizes formResizes = new FormResizes();

	private TheForm theForm = new TheForm();

	
//	private FormIusCLGraphicControl formIusCLGraphicControl = null;
//	private FormComctrls formComctrls = null;
//	private FormUtils formUtils = null;
//	private FormResizes formResizes = null;
	/** menuItem3.OnClick event implementation */
	public void menuItem3Click(IusCLObject sender) {

		formIusCLGraphicControl.show();
	}
	/** menuItem4.OnClick event implementation */
	public void menuItem4Click(IusCLObject sender) {

		formComctrls.show();
	}
	/** menuItem5.OnClick event implementation */
	public void menuItem5Click(IusCLObject sender) {

		formUtils.setFormComctrls(formComctrls);
		formUtils.show();
	}
	/** menuItem6.OnClick event implementation */
	public void menuItem6Click(IusCLObject sender) {

		formResizes.show();
	}
	/** menuItem7.OnClick event implementation */
	public void menuItem7Click(IusCLObject sender) {

		theForm.show();
		IusCLDialogs.showinfo("After form showing function");

//		IusCLModalResult modalResult = theForm.showModal();
//		IusCLDialogs.showinfo("IusCLModalResult = " + modalResult.name());
		
		
	}
	/** btnApplicationEvents.OnClick event implementation */
	public void btnApplicationEventsClick(IusCLObject sender) {

		if (applicationEventss.getEnabled() == true) {

			applicationEventss.setEnabled(false);
		}
		else {

			applicationEventss.setEnabled(true);
		}
	}
	/** applicationEventss.OnMessage event implementation */
	public Boolean applicationEventssMessage(IusCLObject sender, Object msg) {

		MSG win32Msg = (MSG)msg;
		if (win32Msg != null) {

			if ((win32Msg.message == 523) && (win32Msg.wParam == 65536)) {
			
				System.out.println(win32Msg.message + " " + win32Msg.lParam + " " + win32Msg.wParam);
			}
		}
		
		return false;
	}

}