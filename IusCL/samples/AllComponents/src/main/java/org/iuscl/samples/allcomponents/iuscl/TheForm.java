/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allcomponents.iuscl;

import java.util.EnumSet;

import org.iuscl.buttons.IusCLBitButton;
import org.iuscl.extctrls.IusCLPanel;
import org.iuscl.forms.IusCLForm;
import org.iuscl.forms.IusCLScrollBox;
import org.iuscl.system.IusCLObject;

/* **************************************************************************************************** */
public class TheForm extends IusCLForm {

	/** IusCL Components */
	public IusCLPanel panel1;
	public IusCLPanel panel2;
	public IusCLScrollBox scrollBox1;
	public IusCLBitButton bitButton1;
	public IusCLBitButton bitButton2;
	public IusCLBitButton bitButton3;

	/** scrollBox1.OnMouseWheel event implementation */
	public Boolean scrollBox1MouseWheel(IusCLObject sender, 
		EnumSet<IusCLShiftState> shift, Integer wheelDelta, Integer x, Integer y) {

		System.out.println("wheelDelta");
		
		//scrollBox1.get
		
		return true;
	}
	/** bitButton3.OnClick event implementation */
	public void bitButton3Click(IusCLObject sender) {

//		this.setModalResult(IusCLModalResult.mrYesToAll);
		
		close();
	}
	/** theForm.OnClose event implementation */
	public IusCLCloseAction theFormClose(IusCLObject sender) {

		
		return IusCLCloseAction.caHide;
	}

}