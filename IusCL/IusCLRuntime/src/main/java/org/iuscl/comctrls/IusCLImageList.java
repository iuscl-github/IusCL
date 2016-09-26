/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.forms.IusCLApplication;
import org.iuscl.forms.IusCLForm;
import org.iuscl.graphics.IusCLGraphic;
import org.iuscl.graphics.IusCLPicture;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLStrUtils;
import org.jdom.Element;

/* **************************************************************************************************** */
public class IusCLImageList extends IusCLComponent {

	private ArrayList<IusCLPicture> images = new ArrayList<IusCLPicture>();
	
	/* Properties */
	private Integer imageWidth = 16;
	private Integer imageHeight = 16;

	/* Events */

	/* **************************************************************************************************** */
	public IusCLImageList(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("Images", "setPropertyValueImagesFromResName", "getPropertyValueImagesAsResName", 
				"ptImages", "", false, null, null);
		
		defineProperty("ImageWidth", IusCLPropertyType.ptInteger, "16");
		defineProperty("ImageHeight", IusCLPropertyType.ptInteger, "16");
		
		/* Events */
		
		/* Create */
		assign();
	}

	/* **************************************************************************************************** */
	public void setPropertyValueImagesFromResName(String propertyName, String propertyValue) {
		
		images.clear();
		
		String inheritedValue = this.getProperties().get(propertyName).getDefaultValue();
		
		if (IusCLStrUtils.isNotNullNotEmpty(propertyValue)) {
			
			IusCLForm form = (IusCLForm)this.findForm();
			
			List<?> resImages = IusCLApplication.getItemsFromFormResource(form.getClass(), propertyValue);
			
			for (int index = 0; index < resImages.size(); index++) {
				
				Element jdomItemElement = (Element)resImages.get(index);
				
				String imageExt = "png";
				String initialSimpleFileName = jdomItemElement.getChildTextTrim("initialSimpleFileName");
				
				if (initialSimpleFileName == null) {
					
					imageExt = jdomItemElement.getChildTextTrim("pictureExtension");
				}
				else {
					
					imageExt = IusCLFileUtils.extractFileExt(initialSimpleFileName);
				}
				
				String imageFormAndFileName = propertyValue.substring(0, propertyValue.indexOf("/") + 1) +
						this.getName() + "_Image" + Integer.toString(index) + "." + imageExt;
				
				IusCLPicture picture = new IusCLPicture();
				
				IusCLApplication.loadFromFormResource(picture, form.getClass(), imageFormAndFileName);
				
				if (initialSimpleFileName != null) {
					
					picture.setInitialSimpleFileName(initialSimpleFileName);
				}
				
				images.add(picture);
			}
			
			/* Reset the inherited */
			if (IusCLStrUtils.equalValues(inheritedValue, propertyValue) == false) {
			
				this.getProperties().get(propertyName).setDefaultValue("");
			}
		}
	}

	/* **************************************************************************************************** */
	public String getPropertyValueImagesAsResName(String propertyName) {
		
		String inheritedValue = this.getProperties().get(propertyName).getDefaultValue();

		if (IusCLStrUtils.isNotNullNotEmpty(inheritedValue)) {

			return inheritedValue;
		}

		if (images.size() == 0) {

			return "";
		}

		return getPersistentFormAndResName(propertyName) + ".xml";
	}

	/* **************************************************************************************************** */
	public Integer getCount() {
		
		return images.size();
	}

	/* **************************************************************************************************** */
	public void clear() {
		
		images.clear();
	}

	/* **************************************************************************************************** */
	public void add(IusCLPicture picture) {
		
		images.add(picture);
	}

	/* **************************************************************************************************** */
	public void delete(Integer index) {
		
		images.remove(index);
	}

	/* **************************************************************************************************** */
	public void insert(Integer index, IusCLPicture picture) {
		
		images.add(index, picture);
	}

	/* **************************************************************************************************** */
	public IusCLPicture getUnsizedImage(int index) {
		
		return images.get(index);
	}

	/* **************************************************************************************************** */
	public IusCLPicture getImage(int index) {
		
		Image swtImage = getAsSizedSwtImage(index);
		
		IusCLGraphic graphic = new IusCLGraphic();
		graphic.setSwtImage(swtImage);
		
		IusCLPicture picture = new IusCLPicture();
		picture.setGraphic(graphic);
		
		return picture;
	}

	/* **************************************************************************************************** */
	public Image getAsSizedSwtImage(int index) {
		
		if (index < images.size()) {
		
			IusCLPicture picture = images.get(index);
			Image swtImage = picture.getGraphic().getSwtImage();
			
			if ((swtImage.getBounds().width == imageWidth) && (swtImage.getBounds().height == imageHeight)) {
				return swtImage;
			}
			else {
				
				ImageData imageData = swtImage.getImageData(); 
				ImageData imageData2 = imageData.scaledTo(imageWidth, imageHeight); 
				return new Image(Display.getCurrent(), imageData2); 
			}
		}
		
		return null;
	}

	/* **************************************************************************************************** */
	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

}
