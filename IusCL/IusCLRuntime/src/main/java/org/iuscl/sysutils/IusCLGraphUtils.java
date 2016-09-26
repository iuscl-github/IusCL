/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.sysutils;

import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLPicture;

/* **************************************************************************************************** */
public class IusCLGraphUtils {

	/* **************************************************************************************************** */
	public static Boolean isEmptyPicture(IusCLPicture picture) {

		if (picture == null) {
			return true;
		}
		else {
			if (picture.getIsEmpty() == true) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	/* **************************************************************************************************** */
	public static IusCLColor getHighlightColor(IusCLColor color) {
		
		float hue = color.getHue();
		float saturation = color.getSaturation();
		float brightness = color.getBrightness();
		
		IusCLColor highlightColor = new IusCLColor();
		if (brightness * 1.5f > 1) {
			brightness = 0.99f;
		}
		highlightColor.loadFromHueSaturationBrightness(hue, saturation, brightness);
		
		return highlightColor;
	}

	/* **************************************************************************************************** */
	public static IusCLColor getShadowColor(IusCLColor color) {
		
		float hue = color.getHue();
		float saturation = color.getSaturation();
		float brightness = color.getBrightness();
		
		IusCLColor shadowColor = new IusCLColor();
		shadowColor.loadFromHueSaturationBrightness(hue, saturation, brightness * 0.8f);
		
		return shadowColor;
	}
}
