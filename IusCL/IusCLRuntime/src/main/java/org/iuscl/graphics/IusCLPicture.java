/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.graphics;

import java.io.InputStream;
import java.util.HashMap;

import org.iuscl.classes.IusCLPersistent;
import org.iuscl.graphics.formats.IusCLBitmap;
import org.iuscl.graphics.formats.IusCLGif;
import org.iuscl.graphics.formats.IusCLIcon;
import org.iuscl.graphics.formats.IusCLJpeg;
import org.iuscl.graphics.formats.IusCLPng;
import org.iuscl.obj.IusCLObjUtils;
import org.iuscl.sysutils.IusCLFileUtils;

/* **************************************************************************************************** */
public class IusCLPicture extends IusCLPersistent {

	/* SWT */

	/* Fields */
	private IusCLGraphic graphic = null;

	private String initialSimpleFileName = null;

	private String pictureExtension = null;
	
	private String sourceFileName = null;
	private Class<?> sourceRelativeClass = null;
	private String sourceResourceName = null;
	
	private static HashMap<String, Class<?>> registeredGraphicClasses = 
			new HashMap<String, Class<?>>();

	/* **************************************************************************************************** */
	static {

		IusCLPicture.registerGraphicClass("bmp", IusCLBitmap.class);
		
		IusCLPicture.registerGraphicClass("rle", IusCLBitmap.class);

		IusCLPicture.registerGraphicClass("jpg", IusCLJpeg.class);
		IusCLPicture.registerGraphicClass("jpeg", IusCLJpeg.class);
		
		IusCLPicture.registerGraphicClass("gif", IusCLGif.class);
		
		IusCLPicture.registerGraphicClass("ico", IusCLIcon.class);
		
		IusCLPicture.registerGraphicClass("png", IusCLPng.class);
	}

	/* **************************************************************************************************** */
	public static void registerGraphicClass(String fileExtension, Class<?> graphicClass) {
		
		registeredGraphicClasses.put(fileExtension, graphicClass);
	}

	/* **************************************************************************************************** */
	private IusCLGraphic makeGraphicFromPictureExtension() {

		IusCLGraphic typedGraphic = null;
		
		if (registeredGraphicClasses.containsKey(pictureExtension)) {
			
			Class<?> graphicClass = registeredGraphicClasses.get(pictureExtension);
			typedGraphic = (IusCLGraphic)IusCLObjUtils.invokeConstructor(graphicClass); 
		}
		else {
			
			typedGraphic = new IusCLGraphic();
		}

		return typedGraphic;
	}

	/* **************************************************************************************************** */
	public void loadFromFile(String fileName) {
		
		initialSimpleFileName = IusCLFileUtils.extractFileName(fileName);
		pictureExtension = IusCLFileUtils.extractFileExt(initialSimpleFileName);
		graphic = makeGraphicFromPictureExtension();
		
		graphic.loadFromFile(fileName);
		
		sourceFileName = fileName;
		sourceRelativeClass = null;
		sourceResourceName = null;
		
		invokeNotify();
	}

	/* **************************************************************************************************** */
	public void loadFromResource(Class<?> relativeClass, String resourceName) {
		
		initialSimpleFileName = IusCLFileUtils.extractResourceFileName(resourceName);
		pictureExtension = IusCLFileUtils.extractFileExt(initialSimpleFileName);
		graphic = makeGraphicFromPictureExtension();
		
		graphic.loadFromStream(relativeClass.getClassLoader().getResourceAsStream(resourceName));

		sourceFileName = null;
		sourceRelativeClass = relativeClass;
		sourceResourceName = resourceName;
		
		invokeNotify();
	}

	/* **************************************************************************************************** */
	public void loadFromStream(InputStream stream) {

		initialSimpleFileName = null;
		pictureExtension = null;
		graphic = makeGraphicFromPictureExtension();

		graphic.loadFromStream(stream);

		sourceFileName = null;
		sourceRelativeClass = null;
		sourceResourceName = null;
		
		invokeNotify();
	}
	
	/* **************************************************************************************************** */
	public Boolean getIsEmpty() {
		
		if (graphic == null) {
			return true;
		}
		
		if (graphic.getIsEmpty() == true) {
			return true;
		}
		
		return false;
	}

	public IusCLGraphic getGraphic() {
		return graphic;
	}

	/* **************************************************************************************************** */
	public void setGraphic(IusCLGraphic graphic) {
		this.graphic = graphic;

		invokeNotify();
	}

	public String getPictureExtension() {
		return pictureExtension;
	}

	public void setPictureExtension(String pictureExtension) {
		this.pictureExtension = pictureExtension;
	}

	public String getInitialSimpleFileName() {
		return initialSimpleFileName;
	}

	public void setInitialSimpleFileName(String initialSimpleFileName) {
		this.initialSimpleFileName = initialSimpleFileName;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public Class<?> getSourceRelativeClass() {
		return sourceRelativeClass;
	}

	public String getSourceResourceName() {
		return sourceResourceName;
	}
	
}
