/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.buttons;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.graphics.IusCLGraphic;

/* **************************************************************************************************** */
public class IusCLBitButton extends IusCLCustomButton {

	public enum IusCLBitButtonKind { bkCustom, bkOK, bkCancel, bkHelp, bkYes, bkNo, bkClose, 
		bkAbort, bkRetry, bkIgnore, bkAll };

	private IusCLBitButtonKind kind = IusCLBitButtonKind.bkCustom;
	private IusCLBitButtonKind oldKind = null;
	
	/* **************************************************************************************************** */
	public IusCLBitButton(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Kind", IusCLPropertyType.ptEnum, "bkCustom", IusCLBitButtonKind.bkCustom);
		/* TODO Related to the form */
//		defineProperty("Default", IusCLPropertyType.ptBoolean, "false");
//		defineProperty("ModalResult", IusCLPropertyType.ptEnum, "mrNone", IusCLModalResult.mrNone);

		/* Create */
		swtButton = new Button(this.getFormSwtComposite(), SWT.PUSH);
		createWnd(swtButton);
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.getProperty("Width").setDefaultValue("75");
		setWidth(75);
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void reCreate() {
		super.reCreate();
		
		swtButton.addMouseListener(new MouseAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void mouseUp(MouseEvent swtMouseEvent) {
				
				state = IusCLButtonState.bsUp;
			}
			/* **************************************************************************************************** */
			@Override
			public void mouseDown(MouseEvent swtMouseEvent) {
				
				state = IusCLButtonState.bsClicked;
			}
		});
	}

	/* **************************************************************************************************** */
	public IusCLBitButtonKind getKind() {
		return kind;
	}

	/* **************************************************************************************************** */
	public void setKind(IusCLBitButtonKind kind) {
		this.kind = kind;
		
		update();
	}

	/* **************************************************************************************************** */
	private void loadStandardGraphic(String imageName) {

		upGraphic = new IusCLGraphic();
		upGraphic.loadFromResource(this.getClass(), "resources/images/" + imageName + ".png");
		downGraphic = upGraphic;
		clickedGraphic = upGraphic;
		disabledGraphic = new IusCLGraphic();
		disabledGraphic.loadFromResource(this.getClass(), "resources/images/" + imageName + "_disabled.png");
	}

	/* **************************************************************************************************** */
	@Override
	public IusCLGraphic getGraphic() {
		
		if ((kind != oldKind) || (kind == IusCLBitButtonKind.bkCustom)) {
			
			oldKind = kind;

			/* Type */
			switch (kind) {
			case bkAbort:
				loadStandardGraphic("IusCLbbAbort");
				setCaption("Abort");
				break;
			case bkAll:
				loadStandardGraphic("IusCLbbAll");
				setCaption("All");
				break;
			case bkCancel:
				loadStandardGraphic("IusCLbbCancel");
				setCaption("Cancel");
				break;
			case bkClose:
				loadStandardGraphic("IusCLbbClose");
				setCaption("Close");
				break;
			case bkHelp:
				loadStandardGraphic("IusCLbbHelp");
				setCaption("Help");
				break;
			case bkIgnore:
				loadStandardGraphic("IusCLbbIgnore");
				setCaption("Ignore");
				break;
			case bkNo:
				loadStandardGraphic("IusCLbbNo");
				setCaption("No");
				break;
			case bkOK:
				loadStandardGraphic("IusCLbbOK");
				setCaption("OK");
				break;
			case bkRetry:
				loadStandardGraphic("IusCLbbRetry");
				setCaption("Retry");
				break;
			case bkYes:
				loadStandardGraphic("IusCLbbYes");
				setCaption("Yes");
				break;
			case bkCustom:
				loadGraphic();
				break;
			}
		}
		
		return super.getGraphic();
	}
}
