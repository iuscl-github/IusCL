/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allcomponents.iuscl;

import org.iuscl.comctrls.IusCLProgressBar;
import org.iuscl.extctrls.IusCLTrayIcon;
import org.iuscl.forms.IusCLApplication;
import org.iuscl.forms.IusCLForm;
import org.iuscl.stdctrls.IusCLButton;
import org.iuscl.stdctrls.IusCLLabel;
import org.iuscl.stdctrls.IusCLMemo;
import org.iuscl.sysctrls.IusCLScheduler;
import org.iuscl.sysctrls.IusCLTimer;
import org.iuscl.sysctrls.IusCLUnzip;
import org.iuscl.sysctrls.IusCLZip;
import org.iuscl.system.IusCLObject;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLProgressMonitor;
import org.iuscl.extctrls.IusCLPanel;

/* **************************************************************************************************** */
public class FormUtils extends IusCLForm {

	/* IusCL Components */
	public IusCLTrayIcon trayIcon1;
	public IusCLButton button1;
	
	public IusCLTimer timer1;
	public IusCLScheduler scheduler1;
	public IusCLButton button2;
	public IusCLButton button3;
	public IusCLMemo memo1;
	public IusCLLabel label1;
	public IusCLProgressBar progressBar1;
	public IusCLZip zip1;
	public IusCLUnzip unzip1;
	public IusCLPanel panel1;
	public IusCLButton button4;
	private FormComctrls formComctrls = null;

	private class IusCLListUnzipProgressMonitor extends IusCLProgressMonitor {
		/* **************************************************************************************************** */
		@Override
		public void setPosition(Integer position) {
			super.setPosition(position);
			
			System.out.println("IusCLListUnzipProgressMonitor " + getPositionAsPercent() + "%");
		}
	}

	private class IusCLFileUnzipProgressMonitor extends IusCLProgressMonitor {
		/* **************************************************************************************************** */
		@Override
		public void setPosition(Integer position) {
			super.setPosition(position);
			
			System.out.println("IusCLFileUnzipProgressMonitor " + getPositionAsPercent() + "%");
		}
	}

	/** button1.OnClick event implementation */
	public void button1Click(IusCLObject sender) {

		trayIcon1.setVisible(true);
		trayIcon1.showBalloon();
		trayIcon1.setPopupMenu(formComctrls.popupMenu1);
	}
	
	
	public void setFormComctrls(FormComctrls formComctrls) {
		this.formComctrls = formComctrls;
	}
	/** timer1.OnTimer event implementation */
	public void timer1Timer(IusCLObject sender) {

		System.out.println("timer1Timer");
	}
	/** scheduler1.OnTimer event implementation */
	public void scheduler1Timer(IusCLObject sender) {

		System.out.println("scheduler1Timer");
	}
	/** button2.OnClick event implementation */
	public void button2Click(IusCLObject sender) {

		scheduler1.start();
	}
	/** button3.OnClick event implementation */
	public void button3Click(IusCLObject sender) {

		String currentFolder = IusCLFileUtils.getCurrentFolder();
		
		System.out.println("Current folder = " + IusCLFileUtils.getCurrentFolder());
		
		String newFolder = IusCLFileUtils.includeTrailingPathDelimiter(currentFolder) + "_foldertest";

		String zipFolder = newFolder + IusCLFileUtils.getPathDelimiter() + "_folderziptest";

		IusCLFileUtils.createFolder(zipFolder);
 
		progressBar1.setMin(0);
		progressBar1.setMax(4);
		progressBar1.setPosition(0);
		for (int index = 0; index < 5; index++) {
			
			System.out.println("memo1 = " + index);
			label1.setCaption("memo1 = " + index);

			
			memo1.getLines().append(memo1.getLines());
			
			progressBar1.setPosition(index);
			progressBar1.repaint();
			IusCLApplication.processMessages();
			
		}
		
		//IusCLFileUtils.
		memo1.getLines().saveToFile(IusCLFileUtils.includeTrailingPathDelimiter(zipFolder) + "_filetest.txt");
		
		String zipFileName = newFolder + IusCLFileUtils.getPathDelimiter() + "folderziptest.zip";
		
		zip1.setZipFileName(zipFileName);
		
		zip1.addFolderIncludingName(zipFolder);
		
		unzip1.setZipFileName(zipFileName);
		
		String destinationFolder = IusCLFileUtils.includeTrailingPathDelimiter(newFolder) + "destination";
		//IusCLFileUtils.createFolder(zipFolder);
		
		unzip1.setDestinationFolder(destinationFolder);
		
		IusCLListUnzipProgressMonitor listUnzipProgressMonitor = new IusCLListUnzipProgressMonitor();
		IusCLFileUnzipProgressMonitor fileUnzipProgressMonitor = new IusCLFileUnzipProgressMonitor();
		
		unzip1.extractAll(listUnzipProgressMonitor, fileUnzipProgressMonitor);
		
		System.out.println("All done.");
	}

}