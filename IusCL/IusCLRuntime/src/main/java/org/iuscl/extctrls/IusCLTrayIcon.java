/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.extctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.TrayItem;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.forms.IusCLApplication;
import org.iuscl.graphics.IusCLPicture;
import org.iuscl.menus.IusCLPopupMenu;
import org.iuscl.sysutils.IusCLGraphUtils;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLTrayIcon extends IusCLComponent {

	public enum IusCLTrayBalloonIcon { tbiError, tbiInformation, tbiWarning };

	/* SWT */
	private TrayItem swtTrayItem = null;
	private ToolTip swtToolTip = null;
	
	/* Properties */
	private Boolean visible = false;

	private Boolean showHint = true;
	private String hint = null;

	private Boolean balloonAutoHide = true;
	private String balloonMessage = null;
	private String balloonText = null;
	private IusCLTrayBalloonIcon balloonIcon = IusCLTrayBalloonIcon.tbiInformation; 

	private IusCLPicture picture = null;
	
	private IusCLPopupMenu popupMenu = null;

	/* Events */

	/* **************************************************************************************************** */
	public IusCLTrayIcon(IusCLComponent aOwner) {
		
		super(aOwner);

		/* Properties */
		defineProperty("Visible", IusCLPropertyType.ptBoolean, "false");

		defineProperty("ShowHint", IusCLPropertyType.ptBoolean, "true");		
		defineProperty("Hint", IusCLPropertyType.ptString, null);

		defineProperty("BalloonAutoHide", IusCLPropertyType.ptBoolean, "true");	
		defineProperty("BalloonMessage", IusCLPropertyType.ptString, null);
		defineProperty("BalloonText", IusCLPropertyType.ptString, null);
		defineProperty("BalloonIcon", IusCLPropertyType.ptEnum, "tbiInformation", IusCLTrayBalloonIcon.tbiInformation);

		defineProperty("Picture", IusCLPropertyType.ptPicture, "");

		defineProperty("PopupMenu", IusCLPropertyType.ptComponent, "", IusCLPopupMenu.class);

		/* Events */

		/* Create */
		swtTrayItem = new TrayItem(IusCLApplication.getSwtDisplay().getSystemTray(), SWT.NONE) ;
		
		swtTrayItem.addListener(SWT.MenuDetect, new Listener() {

			/* **************************************************************************************************** */
			@Override
			public void handleEvent(Event event) {
				
				if (popupMenu != null) {
					
					popupMenu.getSwtMenu().setVisible(true);
				}
			}
		});
		
		assign();
	}

	/* **************************************************************************************************** */
	public void showBalloon() {
		
		if (swtTrayItem.getToolTip() != null) {
			
			swtToolTip.setVisible(true);
		}
	}

	/* **************************************************************************************************** */
	private void createHint() {

		/* Hint */
		if (showHint == true) {
		
			if (IusCLStrUtils.isNotNullNotEmpty(hint)) {
				
				swtTrayItem.setToolTipText(hint);
				return;
			}
		}
		
		swtTrayItem.setToolTipText(null);
	}

	/* **************************************************************************************************** */
	private void createBalloon() {

		/* Balloon */
		if (IusCLStrUtils.isNotNullNotEmpty(balloonText)) {
			
			if (swtToolTip != null) {
				
				swtToolTip.dispose();
				swtToolTip = null;
			}
			
			switch (balloonIcon) {
			case tbiError:
				swtToolTip = new ToolTip(this.findForm().getSwtShell(), SWT.BALLOON | SWT.ICON_ERROR);
				break;
			case tbiInformation:
				swtToolTip = new ToolTip(this.findForm().getSwtShell(), SWT.BALLOON | SWT.ICON_INFORMATION);					
				break;
			case tbiWarning:
				swtToolTip = new ToolTip(this.findForm().getSwtShell(), SWT.BALLOON | SWT.ICON_WARNING);
				break;
			}
			
			swtToolTip.setMessage(balloonMessage);
			swtToolTip.setText(balloonText);
			swtToolTip.setAutoHide(balloonAutoHide);

			swtTrayItem.setToolTip(swtToolTip);
			
			return;
		}

		swtTrayItem.setToolTip(null);
	}

	public Boolean getVisible() {
		return visible;
	}

	/* **************************************************************************************************** */
	public void setVisible(Boolean visible) {
		this.visible = visible;
		
		swtTrayItem.setVisible(visible);
	}

	public Boolean getShowHint() {
		return showHint;
	}

	/* **************************************************************************************************** */
	public void setShowHint(Boolean showHint) {
		this.showHint = showHint;
		
		createHint();
	}

	public String getHint() {
		return hint;
	}

	/* **************************************************************************************************** */
	public void setHint(String hint) {
		this.hint = hint;
		
		createHint();
	}
	
	public Boolean getBalloonAutoHide() {
		return balloonAutoHide;
	}

	/* **************************************************************************************************** */
	public void setBalloonAutoHide(Boolean balloonAutoHide) {
		this.balloonAutoHide = balloonAutoHide;
		
		createBalloon();
	}

	public String getBalloonText() {
		return balloonText;
	}

	/* **************************************************************************************************** */
	public void setBalloonText(String balloonText) {
		this.balloonText = balloonText;
		
		createBalloon();
	}

	public String getBalloonMessage() {
		return balloonMessage;
	}

	/* **************************************************************************************************** */
	public void setBalloonMessage(String balloonMessage) {
		this.balloonMessage = balloonMessage;
		
		createBalloon();
	}

	public IusCLTrayBalloonIcon getBalloonIcon() {
		return balloonIcon;
	}

	/* **************************************************************************************************** */
	public void setBalloonIcon(IusCLTrayBalloonIcon balloonIcon) {
		this.balloonIcon = balloonIcon;
		
		createBalloon();
	}

	public IusCLPicture getPicture() {
		return picture;
	}

	/* **************************************************************************************************** */
	public void setPicture(IusCLPicture picture) {
		this.picture = picture;
		
		if (IusCLGraphUtils.isEmptyPicture(picture) == false) {
			
			swtTrayItem.setImage(picture.getGraphic().getSwtImage());	
		}
	}

	public IusCLPopupMenu getPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(IusCLPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}
}
