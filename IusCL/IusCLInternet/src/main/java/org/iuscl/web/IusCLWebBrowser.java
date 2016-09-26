/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.web;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.WindowEvent;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLNotifyEvent;
import org.iuscl.types.IusCLPoint;
import org.iuscl.types.IusCLSize;

/* **************************************************************************************************** */
public class IusCLWebBrowser extends IusCLWinControl {

	/* SWT */
	protected Browser swtBrowser = null;
	
	/* Properties */

	/* Events */
	private IusCLNotifyEvent onTitleChange = null;
	private IusCLWebBrowserNavigateEvent onBeforeNavigate = null;
	private IusCLWebBrowserNavigateEvent onNavigateComplete = null;

	private IusCLNotifyEvent onProgressChange = null;

	private IusCLWebBrowserOpenWindowEvent onOpenWindow = null;
//	CloseWindowListener, 
//	StatusTextListener, VisibilityWindowListener
	
	/* Fields */
	private String title = "IusCL Browser";
	private String location = "about:blank";

	private Integer progress = 0;
	private Integer progressMax = 0;
	
	/* **************************************************************************************************** */
	public IusCLWebBrowser(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Events */
		defineProperty("OnTitleChange", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		defineProperty("OnBeforeNavigate", IusCLPropertyType.ptEvent, null, IusCLWebBrowserNavigateEvent.class);
		defineProperty("OnNavigateComplete", IusCLPropertyType.ptEvent, null, IusCLWebBrowserNavigateEvent.class);
		defineProperty("OnProgressChange", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		defineProperty("OnOpenWindow", IusCLPropertyType.ptEvent, null, IusCLWebBrowserOpenWindowEvent.class);
	}
	
	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.getProperty("Width").setDefaultValue("256");
		setWidth(256);
		this.getProperty("Height").setDefaultValue("256");
		setHeight(256);
		
		swtBrowser.addTitleListener(new TitleListener() {
			/* **************************************************************************************************** */
			@Override
			public void changed(TitleEvent swtTitleEvent) {
				
				title = swtTitleEvent.title;
				
				IusCLNotifyEvent onTitleChangeEvent = IusCLWebBrowser.this.getOnTitleChange();
				if (IusCLEvent.isDefinedEvent(onTitleChangeEvent)) {

					onTitleChangeEvent.invoke(IusCLWebBrowser.this);
				}
			}
		});
		
		swtBrowser.addLocationListener(new LocationListener() {
			/* **************************************************************************************************** */
			@Override
			public void changing(LocationEvent swtLocationEvent) {

				IusCLWebBrowserNavigateEvent onBeforeNavigateEvent = IusCLWebBrowser.this.getOnBeforeNavigate();
				if (IusCLEvent.isDefinedEvent(onBeforeNavigateEvent)) {

					swtLocationEvent.doit = onBeforeNavigateEvent.invoke(IusCLWebBrowser.this, 
							swtLocationEvent.location, swtLocationEvent.top);
				}
			}
			/* **************************************************************************************************** */
			@Override
			public void changed(LocationEvent swtLocationEvent) {

				if (swtLocationEvent.top == true) {

					location = swtLocationEvent.location;
				}
						
				IusCLWebBrowserNavigateEvent onNavigateCompleteEvent = IusCLWebBrowser.this.getOnNavigateComplete();
				if (IusCLEvent.isDefinedEvent(onNavigateCompleteEvent)) {

					onNavigateCompleteEvent.invoke(IusCLWebBrowser.this, 
							swtLocationEvent.location, swtLocationEvent.top);
				}
			}
		});
		
		swtBrowser.addProgressListener(new ProgressListener() {
			/* **************************************************************************************************** */
			@Override
			public void completed(ProgressEvent swtProgressEvent) {

				updateProgress(swtProgressEvent);
			}
			/* **************************************************************************************************** */
			@Override
			public void changed(ProgressEvent swtProgressEvent) {
				
				updateProgress(swtProgressEvent);
			}
		});
		
		swtBrowser.addOpenWindowListener(new OpenWindowListener() {
			/* **************************************************************************************************** */
			@Override
			public void open(WindowEvent swtWindowEvent) {
				
				IusCLWebBrowserOpenWindowEvent onOpenWindowEvent = IusCLWebBrowser.this.getOnOpenWindow();
				if (IusCLEvent.isDefinedEvent(onOpenWindowEvent)) {

					IusCLPoint location = new IusCLPoint();
					
					if (swtWindowEvent.location != null) {
						
						location.setXY(swtWindowEvent.location.x, swtWindowEvent.location.y);
					}
					
					IusCLSize size = new IusCLSize();
					
					if (swtWindowEvent.size != null) {
						
						size.setWidthHeight(swtWindowEvent.size.x, swtWindowEvent.size.y);
					}
					
					IusCLWebBrowser webBrowser = onOpenWindowEvent.invoke(IusCLWebBrowser.this,
							location, size,
							swtWindowEvent.menuBar, swtWindowEvent.toolBar, swtWindowEvent.addressBar,
							swtWindowEvent.statusBar);
					
					if (webBrowser != null) {

						swtWindowEvent.browser = webBrowser.swtBrowser;
					}
				}
			}
		});

	}

	/* **************************************************************************************************** */
	private void updateProgress(ProgressEvent swtProgressEvent) {
		
		progress = swtProgressEvent.current;
		progressMax = swtProgressEvent.total;
		
		IusCLNotifyEvent onProgressChangeEvent = IusCLWebBrowser.this.getOnProgressChange();
		if (IusCLEvent.isDefinedEvent(onProgressChangeEvent)) {

			onProgressChangeEvent.invoke(IusCLWebBrowser.this);
		}
	}

	/* **************************************************************************************************** */
	public void navigate(String url) {
		
		swtBrowser.setUrl(url);
	}

	/* **************************************************************************************************** */
	public void stop() {
		
		swtBrowser.stop();
	}

	/* **************************************************************************************************** */
	public void refresh() {
		
		swtBrowser.refresh();
	}

	/* **************************************************************************************************** */
	public void goBack() {
		
		swtBrowser.back();
	}

	/* **************************************************************************************************** */
	public void goForward() {
		
		swtBrowser.forward();
	}

	/* **************************************************************************************************** */
	public String getTitle() {
		
		return title;
	}

	/* **************************************************************************************************** */
	public String getLocation() {
		
		return location;
	}

	/* **************************************************************************************************** */
	public Integer getProgress() {
		
		return progress;
	}

	/* **************************************************************************************************** */
	public Integer getProgressMax() {
		
		return progressMax;
	}

	/* **************************************************************************************************** */

	public IusCLNotifyEvent getOnTitleChange() {
		return onTitleChange;
	}

	public void setOnTitleChange(IusCLNotifyEvent onTitleChange) {
		this.onTitleChange = onTitleChange;
	}

	public IusCLWebBrowserNavigateEvent getOnBeforeNavigate() {
		return onBeforeNavigate;
	}

	public void setOnBeforeNavigate(IusCLWebBrowserNavigateEvent onBeforeNavigate) {
		this.onBeforeNavigate = onBeforeNavigate;
	}

	public IusCLWebBrowserNavigateEvent getOnNavigateComplete() {
		return onNavigateComplete;
	}

	public void setOnNavigateComplete(IusCLWebBrowserNavigateEvent onNavigateComplete) {
		this.onNavigateComplete = onNavigateComplete;
	}

	public IusCLNotifyEvent getOnProgressChange() {
		return onProgressChange;
	}

	public void setOnProgressChange(IusCLNotifyEvent onProgressChange) {
		this.onProgressChange = onProgressChange;
	}

	public IusCLWebBrowserOpenWindowEvent getOnOpenWindow() {
		return onOpenWindow;
	}

	public void setOnOpenWindow(IusCLWebBrowserOpenWindowEvent onOpenWindow) {
		this.onOpenWindow = onOpenWindow;
	}

}
