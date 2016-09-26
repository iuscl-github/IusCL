/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.forms;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLOS;
import org.iuscl.classes.IusCLStrings;
import org.iuscl.forms.IusCLForm.IusCLFormBorderStyle;
import org.iuscl.types.IusCLMargins;
import org.iuscl.types.IusCLSize;

/* **************************************************************************************************** */
public class IusCLScreen extends IusCLComponent {

	/* Fields */
	private static IusCLStrings fonts = null;
	
	private static HashMap<IusCLFormBorderStyle, IusCLMargins> formMargins = new HashMap<IusCLFormBorderStyle, IusCLMargins>();
	private static HashMap<IusCLFormBorderStyle, IusCLSize> formCompensateSize = new HashMap<IusCLFormBorderStyle, IusCLSize>();
	
	private static Integer menuBarHeight = 0;

	private static Boolean isAero = true;
	private static Boolean isThemed = true;

	/* **************************************************************************************************** */
	public IusCLScreen() {
		super(null);
	}

	/* **************************************************************************************************** */
	private static void calculateMargins(IusCLFormBorderStyle borderStyle, int createParams) {

		Shell swtShell = new Shell(IusCLApplication.getSwtDisplay(), createParams);
		swtShell.setBounds(0, 0, 300, 300);
		
		/* GetWindowRect */
		Rectangle windowRect = swtShell.getBounds();
		/* GetClientRect + MapWindowPoints */
		Rectangle clientRect = swtShell.getClientArea();
		Point clientLeftTop = swtShell.toDisplay(0, 0);

		int leftBorder = clientLeftTop.x;
		int rightBorder = windowRect.width - (leftBorder + clientRect.width);
		int topBorder = clientLeftTop.y;
		int bottomBorder = windowRect.height - (topBorder + clientRect.height);

		/* System.out.println(borderStyle + ": " + leftBorder + ", " + rightBorder + ", " + topBorder + ", " + bottomBorder); */
		
		formMargins.put(borderStyle, new IusCLMargins(leftBorder, rightBorder, topBorder, bottomBorder));
		
		swtShell.dispose();
	}
	
	/* **************************************************************************************************** */
	public static void calculateMetrics() {
		
		calculateMargins(IusCLFormBorderStyle.bsNone, SWT.NO_TRIM);
		calculateMargins(IusCLFormBorderStyle.bsSingle, SWT.TITLE | SWT.BORDER | SWT.CLOSE);
		calculateMargins(IusCLFormBorderStyle.bsSizeable, SWT.TITLE | SWT.BORDER | SWT.RESIZE | SWT.CLOSE);
		calculateMargins(IusCLFormBorderStyle.bsDialog, SWT.TITLE | SWT.BORDER);
		calculateMargins(IusCLFormBorderStyle.bsSizeDialog, SWT.TITLE | SWT.BORDER | SWT.RESIZE);
		calculateMargins(IusCLFormBorderStyle.bsToolWindow, SWT.TITLE | SWT.BORDER | SWT.TOOL | SWT.CLOSE);
		calculateMargins(IusCLFormBorderStyle.bsSizeToolWindow, SWT.TITLE | SWT.BORDER | SWT.RESIZE | SWT.TOOL | SWT.CLOSE);
		
		/* Menu */
		Shell swtResizableShell = new Shell(IusCLApplication.getSwtDisplay(), SWT.SHELL_TRIM);
		swtResizableShell.setBounds(0, 0, 300, 300);

		Menu swtMenu = new Menu(swtResizableShell, SWT.BAR);
		MenuItem swtMenuItem = new MenuItem(swtMenu, SWT.CASCADE);
		swtMenuItem.setText("swtMenuItem");
		swtResizableShell.setMenuBar(swtMenu);
		
		Point clientLeftTop = swtResizableShell.toDisplay(0, 0);
		
		menuBarHeight = clientLeftTop.y - formMargins.get(IusCLFormBorderStyle.bsSizeable).getTop();

	    formCompensateSize.put(IusCLFormBorderStyle.bsNone, new IusCLSize(0, 0));
	    formCompensateSize.put(IusCLFormBorderStyle.bsSizeable, new IusCLSize(0, 0));
	    formCompensateSize.put(IusCLFormBorderStyle.bsSizeDialog, new IusCLSize(0, 0));
	    formCompensateSize.put(IusCLFormBorderStyle.bsSizeToolWindow, new IusCLSize(0, 0));

		/* Aero */
	    isAero = IusCLOS.osIusCLScreen_isAero(swtResizableShell);

		if (isAero == true) {

		    formCompensateSize.put(IusCLFormBorderStyle.bsSingle, new IusCLSize(
		    		formMargins.get(IusCLFormBorderStyle.bsSingle).getLeft() -
		    		formMargins.get(IusCLFormBorderStyle.bsSizeable).getLeft() +
		    		formMargins.get(IusCLFormBorderStyle.bsSingle).getRight() -
		    		formMargins.get(IusCLFormBorderStyle.bsSizeable).getRight(),
		    		
		    		formMargins.get(IusCLFormBorderStyle.bsSingle).getTop() -
		    		formMargins.get(IusCLFormBorderStyle.bsSizeable).getTop() +
		    		formMargins.get(IusCLFormBorderStyle.bsSingle).getBottom() -
		    		formMargins.get(IusCLFormBorderStyle.bsSizeable).getBottom()));
		    
		    formCompensateSize.put(IusCLFormBorderStyle.bsDialog, 
		    		formCompensateSize.get(IusCLFormBorderStyle.bsSingle));
		    
		    formCompensateSize.put(IusCLFormBorderStyle.bsToolWindow, new IusCLSize(
		    		formMargins.get(IusCLFormBorderStyle.bsToolWindow).getLeft() -
		    		formMargins.get(IusCLFormBorderStyle.bsSizeToolWindow).getLeft() +
		    		formMargins.get(IusCLFormBorderStyle.bsToolWindow).getRight() -
		    		formMargins.get(IusCLFormBorderStyle.bsSizeToolWindow).getRight(),
		    		
		    		formMargins.get(IusCLFormBorderStyle.bsToolWindow).getTop() -
		    		formMargins.get(IusCLFormBorderStyle.bsSizeToolWindow).getTop() +
		    		formMargins.get(IusCLFormBorderStyle.bsToolWindow).getBottom() -
		    		formMargins.get(IusCLFormBorderStyle.bsSizeToolWindow).getBottom()));
		}
		else {

		    formCompensateSize.put(IusCLFormBorderStyle.bsSingle, new IusCLSize(0, 0));
		    formCompensateSize.put(IusCLFormBorderStyle.bsDialog, new IusCLSize(0, 0));
		    formCompensateSize.put(IusCLFormBorderStyle.bsToolWindow, new IusCLSize(0, 0));
		}

		swtResizableShell.dispose();

		/* Themed */
		isThemed = IusCLOS.osIusCLScreen_isThemed();
	}

	/* **************************************************************************************************** */
	public static IusCLStrings getFonts() {
		
		if (fonts == null) {
			fonts = new IusCLStrings();
			
			FontData[] swtFontData = Display.getCurrent().getFontList(null, true);

			for (int index = 0; index < swtFontData.length; index++) {
				
				String fontName = swtFontData[index].getName();
				
				if (fonts.indexOf(fontName) == -1) {
					
					fonts.add(fontName);
				}
			}
			fonts.sort();
		}
		
		return fonts;
	}

	/* **************************************************************************************************** */
	public static Integer getMenuBarHeight() {
		return menuBarHeight;
	}

	public static Boolean getIsAero() {
		return isAero;
	}

	public static Boolean getIsThemed() {
		return isThemed;
	}

	public static HashMap<IusCLFormBorderStyle, IusCLMargins> getFormMargins() {
		return formMargins;
	}

	public static HashMap<IusCLFormBorderStyle, IusCLSize> getFormCompensateSize() {
		return formCompensateSize;
	}
	
}
