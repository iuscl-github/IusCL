/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.ide;

import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.ExceptionHandler;
import org.eclipse.ui.internal.ide.dialogs.InternalErrorDialog;

/* **************************************************************************************************** */
@SuppressWarnings("restriction")
public class IusCLDesignException {

	/* **************************************************************************************************** */
	public static void error(String message, Throwable exception) {
		
		/* Dialog */
		InputStream inputStream = IusCLDesignException.class.getResourceAsStream("/resources/images/IusCLPerspective.gif");
		Image imageIusCL = new Image(Display.getCurrent(), inputStream);
	    
		String dialogMessage = "";
		if (message != null) {
			dialogMessage = message +"\n\n";
		}
		dialogMessage = dialogMessage + exception.toString();
		
		InternalErrorDialog internalErrorDialog = new InternalErrorDialog(Display.getCurrent().getActiveShell(),
				"IusCL Error", imageIusCL, dialogMessage, exception, 1,
				new String[] { "Close", "Details" }, 0);
		internalErrorDialog.setDetailButton(1);
		internalErrorDialog.open();
		
	    /* Log */
		ExceptionHandler.getInstance().handleException(exception);
	}
}
