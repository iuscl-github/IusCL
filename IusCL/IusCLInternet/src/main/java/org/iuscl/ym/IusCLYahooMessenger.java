/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.ym;

import java.io.File;

import org.iuscl.classes.IusCLComponent;
import org.iuscl.system.IusCLLog;
import org.openymsg.network.Session;
import org.openymsg.network.SessionState;

/* **************************************************************************************************** */
public class IusCLYahooMessenger extends IusCLComponent {

	/* Properties */

	/* Events */
	
	/* Fields */
	private Session ymSession = null;

	/* **************************************************************************************************** */
	public IusCLYahooMessenger(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
//		defineProperty("Enabled", IusCLPropertyType.ptBoolean, "false");
//		defineProperty("Interval", IusCLPropertyType.ptInteger, "1000");

		/* Events */
//		defineProperty("OnTimer", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);

		/* Create */
	}

	/* **************************************************************************************************** */
	public Session getYMSession() {
		
		return ymSession;
	}

	/* **************************************************************************************************** */
	public Boolean connected() {
		
		if (ymSession == null) {
			
			return false;
		}
		
		if (ymSession.getSessionStatus() != SessionState.LOGGED_ON) {

			return false;
		}
		
		return true;
	}


	/* **************************************************************************************************** */
	public void login(String ymUser, String ymPassword) {

		long msStart = System.currentTimeMillis();

		try {

			ymSession = new Session();
			ymSession.login(ymUser, ymPassword);
		}
		catch (Exception ymException) {
			
			IusCLLog.logError("YahooMessenger login error", ymException);
		}
		
		long msEnd = System.currentTimeMillis();
		
		IusCLLog.logDebug("YahooMessenger login: " + (msEnd - msStart) / 1000 + " seconds.");
	}

	/* **************************************************************************************************** */
	public void logout() {

		if (connected() == false) {
			return;
		}

		try {

			ymSession.logout();
		}
		catch (Exception ymException) {
			
			IusCLLog.logError("YahooMessenger logout error", ymException);
		}
	}

	/* **************************************************************************************************** */
	public void sendMessage(String ymToUser, String ymMessage) {

		if (connected() == false) {
			return;
		}

		try {
			
			ymSession.sendMessage(ymToUser, ymMessage);
		}
		catch (Exception ymException) {
			
			IusCLLog.logError("YahooMessenger send message error", ymException);
		}
	}

	/* **************************************************************************************************** */
	public void sendFile(String ymToUser, String ymFileName) {

		try {

			File file = new File(ymFileName);
			ymSession.sendFileTransfer(ymToUser, file, "msg");
		}
		catch (Exception ymException) {
			
			IusCLLog.logError("YahooMessenger send file error", ymException);
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void free() {
		
		logout();
		super.free();
	}

}
