/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.windows;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/* **************************************************************************************************** */
public class IusCLSwtWindowsStatusBar extends Composite {

	private static HashMap<Integer, IusCLSwtWindowsStatusBar> windowsStatusBars = new HashMap<Integer, IusCLSwtWindowsStatusBar>();
	
	private static final int /*long*/ statusBarProc;
	
	private static final TCHAR statusBarClass = new TCHAR(0, "msctls_statusbar32", true);
	
	
	public static final int SB_SETPARTS = OS.WM_USER + 4;
	public static final int SB_GETBORDERS = OS.WM_USER + 7;
	public static final int SB_SETMINHEIGHT = OS.WM_USER + 8;
	public static final int SB_GETRECT = OS.WM_USER + 10;
	public static final int SB_SETTEXTW = OS.WM_USER + 11;
	public static final int SB_SETICON = OS.WM_USER + 15;

	
	public static final int SBARS_SIZEGRIP = 0x0100;
	public static final int SBT_NOBORDERS = 0x0400;	

//	public static final int SB_SETTEXT = OS.WM_USER + 1;
//	public static final int SB_GETTEXTLENGTH = OS.WM_USER + 3;
	
	/* **************************************************************************************************** */
	static {

		WNDCLASS lpWndClass = new WNDCLASS();
		OS.GetClassInfo(0, statusBarClass, lpWndClass);
		statusBarProc = lpWndClass.lpfnWndProc;
	}

	/* **************************************************************************************************** */
	public IusCLSwtWindowsStatusBar(Composite parent, int style) {
		super(parent, style);
		
		handle = OS.CreateWindowEx(
				0,
				statusBarClass,
				null,
				OS.WS_CHILD | OS.WS_VISIBLE | SBARS_SIZEGRIP,
				0, 0, 0, 0,
				parent.handle,
				0,
				OS.GetModuleHandle(null),
				null);

		
		Callback swtCallback = new Callback(IusCLSwtWindowsStatusBar.class, "statusBarWindowProc", 4);
		OS.SetWindowLongPtr(handle, OS.GWLP_WNDPROC, swtCallback.getAddress());
		
		windowsStatusBars.put(handle, this);
	}

	/* **************************************************************************************************** */
	public static int /*long*/ statusBarWindowProc(int /*long*/ hwnd, int /*long*/ msg, int /*long*/ wParam, int /*long*/ lParam) {
		
		IusCLSwtWindowsStatusBar windowsStatusBar = null;
		Event event = null;

		switch ((int)/*64*/msg) {
		
		case OS.WM_LBUTTONDOWN:
//			System.out.println("wmCommandChild = " + msg);

			windowsStatusBar = windowsStatusBars.get(hwnd);
			
			event = new Event();
			
			event.button = SWT.BUTTON1;
			
			windowsStatusBar.notifyListeners(SWT.MouseDown, event);
			
//			for (int index = 0; index < windowsStatusBar.getListeners(SWT.MouseDown).length; index++) {
//				Listener swtListener = windowsStatusBar.getListeners(SWT.MouseDown)[index];
//				
//				windowsStatusBar.notifyListeners(eventType, event)
//				
//				System.out.println("swtListener = " + swtListener);
//			}
			
			
//			RECT rect = new RECT ();
//			OS.GetClientRect (hwnd, rect);
//			OS.FillRect (wParam, rect, OS.GetSysColorBrush (OS.COLOR_WINDOW));
			//return 0;
			break;
		case OS.WM_LBUTTONUP:

			windowsStatusBar = windowsStatusBars.get(hwnd);
			
			event = new Event();
			event.button = SWT.BUTTON1;
			
			windowsStatusBar.notifyListeners(SWT.MouseUp, event);
			//return 0;
			break;
		case OS.WM_MOUSEMOVE:

			windowsStatusBar = windowsStatusBars.get(hwnd);
			
			event = new Event();
			//event.button = SWT.BUTTON1;
			
			event.x = OS.GET_X_LPARAM(lParam);
			event.y = OS.GET_Y_LPARAM(lParam);
			
			windowsStatusBar.notifyListeners(SWT.MouseMove, event);
			//return 0;
			break;
		}

		return OS.CallWindowProc(statusBarProc, hwnd, (int)/*64*/msg, wParam, lParam);
	}	
	
}
