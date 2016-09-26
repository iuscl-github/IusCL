/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.sysutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.iuscl.classes.IusCLStrings;
import org.iuscl.system.IusCLLog;

/* **************************************************************************************************** */
public class IusCLFileUtils {

	/* **************************************************************************************************** */
	public static String extractFileExt(String fileName) {
		
		return extractFileExt(fileName, false);
	}

	/* **************************************************************************************************** */
	public static String extractFileExt(String fileName, Boolean toLowerCase) {
		
		int lastDotPos = fileName.lastIndexOf('.');
		String extension = "";
		if (lastDotPos > -1) {
			
			extension = fileName.substring(lastDotPos + 1);
		}
		if (toLowerCase == true) {
			
			extension = extension.toLowerCase();
		}
	    return extension;
	}

	/* **************************************************************************************************** */
	public static String extractFileName(String fileName) {
		
		String sep = getPathDelimiter();
		
		int lastSepPos = fileName.lastIndexOf(sep);
		if (lastSepPos > -1) {
			
			return fileName.substring(lastSepPos + 1);
		}
		return fileName;
	}

	/* **************************************************************************************************** */
	public static String extractResourceFileName(String resourceName) {
		
		String sep = "/";
		
		int lastSepPos = resourceName.lastIndexOf(sep);
		if (lastSepPos > -1) {
			
			return resourceName.substring(lastSepPos + 1);
		}
		return resourceName;
	}

	/* **************************************************************************************************** */
	public static String extractResourcePath(String resourceName) {
		
		String sep = "/";
		
		int lastSepPos = resourceName.lastIndexOf(sep);
		if (lastSepPos > -1) {
			
			return resourceName.substring(0, lastSepPos);
		}
		return "";
	}

	/* **************************************************************************************************** */
	public static String extractFilePath(String fileName) {
		
		String sep = getPathDelimiter();
		
		int lastSepPos = fileName.lastIndexOf(sep);
		if (lastSepPos > -1) {
			
			return fileName.substring(0, lastSepPos);
		}
		return "";
	}
	
	/* **************************************************************************************************** */
	public static Boolean fileExists(String fileName) {
		
		File javaFile = new File(fileName);
		if (javaFile.exists()) {
			
			if (javaFile.isFile()) {
				return true;	
			}
		}
		
		return false;
	}

	/* **************************************************************************************************** */
	public static Boolean folderExists(String folder) {
		
		File javaFolder = new File(folder);
		if (javaFolder.exists()) {
			
			if (javaFolder.isDirectory()) {
				return true;	
			}
		}
		
		return false;
	}

	/* **************************************************************************************************** */
	public static void createFolder(String folder) {
		
		if (!folderExists(folder)) {
			
			File javaFolder = new File(folder);
			if (!javaFolder.mkdirs()) {
				
				IusCLLog.logError("Error creating the folder: " + folder);
			}
		}
	}
	
	/* **************************************************************************************************** */
	public static void createFile(String fileName) {
		
		String filePath = extractFilePath(fileName);
		createFolder(filePath);
		
		File javaFile = new File(fileName);
		try {
			
			javaFile.createNewFile();
		}
		catch (IOException ioException) {
			
			IusCLLog.logError("Error creating the file: " + fileName, ioException);
		}
	}
	
	/* **************************************************************************************************** */
	public static String getCurrentFolder() {
		
		String currentFolder = null;
		
		File javaFile = new File(".");
		try {
			
			currentFolder = javaFile.getCanonicalPath();
		}
		catch (IOException ioException) {
			/* Ignore */
		}
		
		return currentFolder;
	}

	/* **************************************************************************************************** */
	public static String getPathDelimiter() {
		
		return File.separator;
	}

	/* **************************************************************************************************** */
	public static String includeTrailingPathDelimiter(String pathName) {
		String delimiterPath = pathName;
		
		if (delimiterPath.lastIndexOf(File.separator) != (delimiterPath.length() - 1)) {
			
			delimiterPath = delimiterPath + File.separator;
		}
		
		return delimiterPath;
	}
	
	/* **************************************************************************************************** */
	public static void deleteFile(String fileName) {
		
		if (fileExists(fileName) == false) {
			
			return;
		}
		File javaFile = new File(fileName);
		javaFile.delete();
	}

	/* **************************************************************************************************** */
	public static void deleteFolderIncludingName(String folderName) {
		
		deleteFolderIncludingName(folderName, null);
	}

	/* **************************************************************************************************** */
	public static void deleteFolderIncludingName(String folderName, IusCLProgressMonitor deleteProgressMonitor) {
		
		deleteFolderContent(folderName, deleteProgressMonitor);
		
		File javaFolder = new File(folderName);
		javaFolder.delete();
	}


	/* **************************************************************************************************** */
	public static void deleteFolderContent(String folderName) {
		
		deleteFolderContent(folderName, null);
	}

	/* **************************************************************************************************** */
	public static void deleteFolderContent(String folderName, IusCLProgressMonitor deleteProgressMonitor) {
		
		if (folderExists(folderName) == false) {
			
			return;
		}
		
		IusCLFileSearchRec srcSearchRec = new IusCLFileSearchRec();
		
		IusCLStrings srcFileNames = findFiles(folderName, srcSearchRec);
		
		srcSearchRec.setReturnFolders(true);
		srcSearchRec.setReturnFiles(false);
		IusCLStrings srcFolderNames = findFiles(folderName, srcSearchRec);

		if (deleteProgressMonitor != null) {
			deleteProgressMonitor.setBegin(0);
			deleteProgressMonitor.setEnd(srcFileNames.size() + srcFolderNames.size());
		}

		int pos = 0;
		
		/* Files */
		for (int index = 0; index < srcFileNames.size(); index++) {
			
			String delFileName = srcFileNames.get(index);

			if (deleteProgressMonitor != null) {
				deleteProgressMonitor.setShortMessage(delFileName);
			}

			deleteFile(delFileName);
			
			if (deleteProgressMonitor != null) {
				pos = pos + index;
				deleteProgressMonitor.setPosition(pos);
			}
		}
		
		/* Folders */
		for (int index = 0; index < srcFolderNames.size(); index++) {
			
			String delFolderName = srcFolderNames.get(srcFolderNames.size() - (index + 1));

			if (deleteProgressMonitor != null) {
				deleteProgressMonitor.setShortMessage(delFolderName);
			}

			File javaFolder = new File(delFolderName);
			javaFolder.delete();
			
			if (deleteProgressMonitor != null) {
				pos = pos + index;
				deleteProgressMonitor.setPosition(pos);
			}
		}
	}

	/* **************************************************************************************************** */
	public static Integer getFileSize(String fileName) {

		File javaFile = new File(fileName);
		return Integer.valueOf((int)javaFile.length());
	}

	/* **************************************************************************************************** */
	public static void copyFile(String fileNameSrc, String fileNameDest) {
		
		copyFile(fileNameSrc, fileNameDest, null);
	}

	/* **************************************************************************************************** */
	public static void copyFile(String fileNameSrc, String fileNameDest, IusCLProgressMonitor progressMonitor) {
		
		Integer bufferSize = 8 * 1024;
		
		try {
			InputStream inputStream = new FileInputStream(fileNameSrc);
			createFile(fileNameDest);
			OutputStream outputStream = new FileOutputStream(fileNameDest);
			  
			if (progressMonitor != null) {
				progressMonitor.setBegin(0);
				progressMonitor.setEnd(getFileSize(fileNameSrc));
				progressMonitor.setShortMessage(fileNameSrc);
				progressMonitor.setLongMessage(fileNameDest);
			}
			/* Copy the bytes from input stream to output stream */
			Integer pos = 0;
			
			byte[] buffer = new byte[bufferSize];
			int len;
			while ((len = inputStream.read(buffer)) > 0) {
				
				outputStream.write(buffer, 0, len);
				
				if (progressMonitor != null) {
					pos = pos + bufferSize; 
					progressMonitor.setPosition(pos);
				}
			}
			inputStream.close();
			outputStream.close();
		}
		catch (IOException ioException) {
			
			IusCLLog.logError("Error on copy the file: " + fileNameSrc + " into the file:" + fileNameDest, ioException);
		}
	}

	/* **************************************************************************************************** */
	public static void copyFolderIncludingName(String folderNameSrc, String folderNameDest) {
		
		copyFolderIncludingName(folderNameSrc, folderNameDest, null, null);
	}

	/* **************************************************************************************************** */
	public static void copyFolderIncludingName(String folderNameSrc, String folderNameDest, 
			IusCLProgressMonitor folderProgressMonitor, IusCLProgressMonitor fileProgressMonitor) {
		
		folderNameDest = includeTrailingPathDelimiter(folderNameDest) + extractFileName(folderNameSrc);
		copyFolderContent(folderNameSrc, folderNameDest, folderProgressMonitor, fileProgressMonitor);
	}

	/* **************************************************************************************************** */
	public static void copyFolderContent(String folderNameSrc, String folderNameDest) {
		
		copyFolderContent(folderNameSrc, folderNameDest, null, null);
	}

	/* **************************************************************************************************** */
	public static void copyFolderContent(String folderNameSrc, String folderNameDest, 
			IusCLProgressMonitor folderProgressMonitor, IusCLProgressMonitor fileProgressMonitor) {
		
		int posSrc = folderNameSrc.length();
		
		IusCLFileSearchRec srcSearchRec = new IusCLFileSearchRec();
		srcSearchRec.setReturnFolders(true);
		
		IusCLStrings srcFileNames = findFiles(folderNameSrc, srcSearchRec);
		
		if (folderProgressMonitor != null) {
			folderProgressMonitor.setBegin(0);
			folderProgressMonitor.setEnd(srcFileNames.size());
		}
		
		for (int index = 0; index < srcFileNames.size(); index++) {
			
			String fileNameSrc = srcFileNames.get(index);
			String fileNameDest = folderNameDest + fileNameSrc.substring(posSrc);

			if (folderProgressMonitor != null) {
				folderProgressMonitor.setShortMessage(fileNameSrc);
			}

			if (fileExists(fileNameSrc) == true) {
			
				copyFile(fileNameSrc, fileNameDest, fileProgressMonitor);
			}
			else {
				
				createFolder(fileNameDest);
			}
			
			if (folderProgressMonitor != null) {
				folderProgressMonitor.setPosition(index);
			}
		}
	}

	/* **************************************************************************************************** */
	public static void renameFile(String fileOldNameIncludingPath, String fileNewNameNoPath) {

		if (fileExists(fileOldNameIncludingPath) == false) {
			
			return;
		}
		
		String newFileName = extractFilePath(fileOldNameIncludingPath);
		newFileName = includeTrailingPathDelimiter(newFileName);
		newFileName = newFileName + fileNewNameNoPath;
		
		if (fileExists(newFileName) == true) {
			
			deleteFile(newFileName);
		}

		File oldfile = new File(fileOldNameIncludingPath);
		File newfile = new File(newFileName);
		
		oldfile.renameTo(newfile);
	}

	/* **************************************************************************************************** */
	public static void renameFolder(String folderOldNameIncludingPath, String folderNewNameNoPath) {

		if (folderExists(folderOldNameIncludingPath) == false) {
			
			return;
		}
		
		String newFolderName = extractFilePath(folderOldNameIncludingPath);
		newFolderName = includeTrailingPathDelimiter(newFolderName);
		newFolderName = newFolderName + folderNewNameNoPath;
		
		if (folderExists(newFolderName) == true) {
			
			return;
		}

		File oldFolder = new File(folderOldNameIncludingPath);
		File newFolder = new File(newFolderName);
		
		oldFolder.renameTo(newFolder);
	}

	/* **************************************************************************************************** */
	public static byte[] readFileIntoBuffer(String fileName) {

		byte[] bytes = null;

		try {
			
			if (fileExists(fileName) == false) {
				
				return null;
			}
			File javaFile = new File(fileName);
			InputStream inputStream = new FileInputStream(javaFile);
	
	        long length = javaFile.length();
	        bytes = new byte[(int)length];
	        inputStream.read(bytes);
	        inputStream.close();
		}
		catch (IOException ioException) {
			
			IusCLLog.logError("Error on reading file into buffer " + fileName, ioException);
		}
		
		return bytes;
	}

	/* **************************************************************************************************** */
	public static void writeBufferIntoFile(String fileName, byte[] bytes) {

		try {
			
			createFile(fileName);
			File javaFile = new File(fileName);
			OutputStream outputStream = new FileOutputStream(javaFile);
	
			outputStream.write(bytes);
			outputStream.close();
		}
		catch (IOException ioException) {
			
			IusCLLog.logError("Error on writing buffer into file " + fileName, ioException);
		}
	}

	/* **************************************************************************************************** */
	public static IusCLStrings findFiles(String startingFolder, final IusCLFileSearchRec fileSearchRec) {

		FilenameFilter javaFilenameFilter = new FilenameFilter() {
			/* **************************************************************************************************** */
			@Override
			public boolean accept(File dir, String name) {
				
				String ps = getPathDelimiter();
				
				if (IusCLFileUtils.folderExists(dir + ps + name)) {

					if (fileSearchRec.getReturnFolders() == false) {
						
						return true;
					}
				}
				
				String includeNamePattern = fileSearchRec.getIncludeNamePattern();
				
				if (IusCLStrUtils.isNotNullNotEmpty(includeNamePattern)) {
					
					if (name.indexOf(includeNamePattern) == -1) {
						
						return false;
					}
				}
				
				String excludeNamePattern = fileSearchRec.getExcludeNamePattern();
				
				if (IusCLStrUtils.isNotNullNotEmpty(excludeNamePattern)) {
					
					if (name.indexOf(excludeNamePattern) > -1) {
						
						return false;
					}
				}
				
				return true;
			}
		};

		IusCLStrings files = new IusCLStrings();

		try {
			
			File javaStartingFolder = new File(startingFolder);
			File[] javaFilesAndFolders = javaStartingFolder.listFiles(javaFilenameFilter);
			
			if (javaFilesAndFolders == null) {
				
				return files;
			}
			
			for (File javaFile: javaFilesAndFolders) {
				
				if (javaFile.isDirectory()) {
					
					if (fileSearchRec.getReturnFolders()) {
						
						files.add(javaFile.getCanonicalPath());
					}
					if (fileSearchRec.getIsRecursive()) {
						
						files.append(findFiles(javaFile.getAbsolutePath(), fileSearchRec));
					}
				}
				else {
					
					if (fileSearchRec.getReturnFiles()) {
						
						files.add(javaFile.getCanonicalPath());
					}
				}
			}

			if (fileSearchRec.getIsSorted()) {
				
				files.sort();
			}
		}
		catch (IOException ioException) {
			
			IusCLLog.logError("Error on finding files", ioException);
		}
		
		return files;
	}
	
}
