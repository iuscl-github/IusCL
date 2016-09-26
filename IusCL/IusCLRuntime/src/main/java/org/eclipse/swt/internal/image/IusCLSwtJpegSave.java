/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.Compatibility;
import org.iuscl.graphics.formats.IusCLJpeg;

/* **************************************************************************************************** */
public class IusCLSwtJpegSave {

	/* **************************************************************************************************** */
	public static void save(ImageLoader swtImageLoader, IusCLJpeg jpeg, OutputStream outputStream) {

		JPEGFileFormat jpegFileFormat = new JPEGFileFormat();
		
		jpegFileFormat.encoderQFactor = jpeg.getCompressionQuality(); // Clever, huh?
		jpegFileFormat.progressive = jpeg.getProgressiveEncoding();
		
		@SuppressWarnings("resource")
		LEDataOutputStream stream = new LEDataOutputStream(outputStream);
		
		jpegFileFormat.outputStream = stream;
		jpegFileFormat.unloadIntoByteStream(swtImageLoader);
	}

	/* **************************************************************************************************** */
	public static void save(ImageLoader swtImageLoader, IusCLJpeg jpeg, String fileName) {

		OutputStream outputStream = null;
		try {
			outputStream = Compatibility.newFileOutputStream(fileName);
		} 
		catch (IOException ioException) {
			SWT.error(SWT.ERROR_IO, ioException);
		}

		save(swtImageLoader, jpeg, outputStream);
		
		try {
			outputStream.close();
		} 
		catch (IOException ioException) {
			SWT.error(SWT.ERROR_IO, ioException);
		}
	}
	
}
