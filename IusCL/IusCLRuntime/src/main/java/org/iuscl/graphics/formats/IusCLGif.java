/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.graphics.formats;

import java.io.OutputStream;

import org.eclipse.swt.SWT;
import org.iuscl.graphics.IusCLMultiGraphic;

/* **************************************************************************************************** */
public class IusCLGif extends IusCLMultiGraphic {

	/* **************************************************************************************************** */
	@Override
	public void saveToFile(String fileName) {
		
		super.saveToFile(fileName);

		/* Only one saved, SWT is incomplete */
		swtImageLoader.save(fileName, SWT.IMAGE_GIF);	
	}

	/* **************************************************************************************************** */
	@Override
	public void saveToStream(OutputStream outputStream) {

		super.saveToStream(outputStream);

		/* Only one saved, SWT is incomplete */
		swtImageLoader.save(outputStream, SWT.IMAGE_GIF);	
	}

	/* **************************************************************************************************** */
	public Boolean getIsAnimated() {
		
		if (size() > 1) {
			
			return true;
		}
		
		return false;
	}

}
