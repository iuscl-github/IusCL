/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.classes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

import org.iuscl.system.IusCLLog;
import org.iuscl.system.IusCLObject;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLStrings extends IusCLObject {

	private ArrayList<String> strings = new ArrayList<String>();

	/* **************************************************************************************************** */
	public IusCLStrings() {
		
		super();
	}
	
	/* **************************************************************************************************** */
	public void add(String string) {
		
		strings.add(string);
		invokeNotify();
	}

	/* **************************************************************************************************** */
	public void append(IusCLStrings strings) {
		
		this.strings.addAll(strings.strings);
		invokeNotify();
	}

	/* **************************************************************************************************** */
	public void insert(Integer index, String string) {
		
		strings.add(index, string);
		invokeNotify();
	}

	/* **************************************************************************************************** */
	public Integer size() {
		
		return strings.size();
	}

	/* **************************************************************************************************** */
	public String get(Integer index) {
		
		return strings.get(index);
	}

	/* **************************************************************************************************** */
	public void set(Integer index, String line) {
		
		strings.set(index, line);
		invokeNotify();
	}

	/* **************************************************************************************************** */
	public void delete(Integer index) {
		
		strings.remove(index.intValue());
		invokeNotify();
	}

	/* **************************************************************************************************** */
	public Integer indexOf(String string) {
		
		return strings.indexOf(string);
	}

	/* **************************************************************************************************** */
	public void sort() {
		
		Collections.sort(strings);
		invokeNotify();
	}

	/* **************************************************************************************************** */
	public String getText() {
		
		String text = "";

		if (strings.size() == 0) {
			return text;
		}

		for (int index = 0; index < strings.size() - 1; index++) {
			text = text + strings.get(index) + IusCLStrUtils.sLineBreak();
		}
		text = text + strings.get(strings.size() - 1);
		
		return text;
	}

	/* **************************************************************************************************** */
	public void setText(String text) {

		strings.clear();

		if (text == null) {
			return;
		}
		
		String[] splitStrings = text.split(IusCLStrUtils.sLineBreak());
		
		for (int index = 0; index < splitStrings.length; index++) {
			strings.add(splitStrings[index]);
		}
	}

	/* **************************************************************************************************** */
	public void loadFromFile(String fileName) {
		
		if (IusCLFileUtils.fileExists(fileName) == false) {
			strings.clear();
			return;
		}
		
		try {
			loadFromStream(new FileInputStream(fileName));
		}
		catch (IOException ioException) {
			IusCLLog.logError("Exception loading strings from file", ioException);
		}
	}

	/* **************************************************************************************************** */
	public void loadFromResource(Class<?> relativeClass, String resourceName) {
		
		loadFromStream(relativeClass.getClassLoader().getResourceAsStream(resourceName));
	}
	
	/* **************************************************************************************************** */
	public void loadFromStream(InputStream inputStream) {
		
		strings.clear();
		
		try {
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strings.add(line);
			}
			dataInputStream.close();
			invokeNotify();
		}
		catch (IOException ioException) {
			IusCLLog.logError("Exception loading strings from stream", ioException);
		}
	}
	
	/* **************************************************************************************************** */
	public void saveToFile(String fileName) {
		
		IusCLFileUtils.createFile(fileName);
		try {
			saveToStream(new FileOutputStream(fileName));
		}
		catch (IOException ioException) {
			IusCLLog.logError("Exception saving strings to file", ioException);
		}		
	}

	/* **************************************************************************************************** */
	public void saveToStream(OutputStream outputStream) {
		 
		try {
		    PrintStream printStream = new PrintStream(outputStream);
		    
		    int lastIndex = strings.size() - 1;
			for (int index = 0; index < lastIndex; index++) {
				
				printStream.println(strings.get(index));
			}
			printStream.print(strings.get(lastIndex));
			
			outputStream.close();               
		}
		catch (IOException ioException) {
			
			IusCLLog.logError("Exception saving strings to stream", ioException);
		}		
	}

	/* **************************************************************************************************** */
	public Boolean getIsEmpty() {

		if (strings.size() > 1) {
			return false;
		}

		if (strings.size() == 0) {
			return true;
		}

		if (strings.get(0).length() == 0) {
			return true;
		}
		
		return false;
	}
}
