/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.graphics.formats;

import java.io.OutputStream;

import org.eclipse.swt.internal.image.IusCLSwtJpegSave;
import org.iuscl.graphics.IusCLGraphic;

/* **************************************************************************************************** */
public class IusCLJpeg extends IusCLGraphic {

	private Integer compressionQuality = 75;
	
	private Boolean progressiveDisplay = false;
	private Boolean progressiveEncoding = false;
	
	/* **************************************************************************************************** */
	@Override
	public void saveToFile(String fileName) {
		
		super.saveToFile(fileName);

		IusCLSwtJpegSave.save(swtImageLoader, this, fileName);
	}

	/* **************************************************************************************************** */
	@Override
	public void saveToStream(OutputStream stream) {

		super.saveToStream(stream);

		IusCLSwtJpegSave.save(swtImageLoader, this, stream);
	}

	/* **************************************************************************************************** */
	public Integer getCompressionQuality() {
		return compressionQuality;
	}

	public void setCompressionQuality(Integer compressionQuality) {
		this.compressionQuality = compressionQuality;
	}

	public Boolean getProgressiveDisplay() {
		return progressiveDisplay;
	}

	public void setProgressiveDisplay(Boolean progressiveDisplay) {
		this.progressiveDisplay = progressiveDisplay;
	}

	public Boolean getProgressiveEncoding() {
		return progressiveEncoding;
	}

	public void setProgressiveEncoding(Boolean progressiveEncoding) {
		this.progressiveEncoding = progressiveEncoding;
	}
	
}
