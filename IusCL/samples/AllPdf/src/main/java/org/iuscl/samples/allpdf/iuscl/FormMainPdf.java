/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allpdf.iuscl;

import org.iuscl.dialogs.IusCLSaveDialog;
import org.iuscl.forms.IusCLForm;
import org.iuscl.pdf.IusCLPdfDocument;
import org.iuscl.pdf.IusCLPdfImage;
import org.iuscl.pdf.IusCLPdfText;
import org.iuscl.stdctrls.IusCLButton;
import org.iuscl.stdctrls.IusCLLabel;
import org.iuscl.system.IusCLObject;

/** IusCL form class */
public class FormMainPdf extends IusCLForm {

	/* IusCL Components */
	public IusCLPdfDocument pdfDocument;
	public IusCLPdfText pdfText;
	public IusCLPdfImage pdfImage;
	public IusCLButton buttonPrint;
	public IusCLSaveDialog pdfSaveDialog;

	public IusCLLabel fileNameLabel;

	/** buttonPrint.OnClick event implementation */
	public void buttonPrintClick(IusCLObject sender) {

		if (pdfSaveDialog.execute()) {

			String fileName = pdfSaveDialog.getFileName();
			
			fileNameLabel.setCaption(fileName);
			
			pdfDocument.setFileName(fileName);
			
			pdfDocument.startDoc();

			pdfImage.draw();

			pdfText.write();
			
			pdfImage.draw();
			
			pdfDocument.endDoc();
		}
		
	}

}