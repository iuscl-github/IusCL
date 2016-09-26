/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.sysctrls;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLStrings;
import org.iuscl.system.IusCLLog;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLProgressMonitor;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLUnzip extends IusCLComponent {

	/* Properties */
	private String zipFileName = null;
	private String destinationFolder = null;
	private String zipRootFolder = null;
	
	/* Events */
	
	/* Fields */
	private String internalRootFolder = "";
	private ZipFile javaZipFile = null;
	private ZipInputStream javaZipInputStream = null;

	/* **************************************************************************************************** */
	public IusCLUnzip(IusCLComponent aOwner) {
		
		super(aOwner);

		/* Properties */
		defineProperty("ZipFileName", IusCLPropertyType.ptString, "");
		defineProperty("DestinationFolder", IusCLPropertyType.ptString, "");
		defineProperty("ZipRootFolder", IusCLPropertyType.ptString, "");
		
		/* Events */
	}

	/* **************************************************************************************************** */
	public IusCLStrings getFilesAndFoldersList() {
		
		startRead();
		
		IusCLStrings list = new IusCLStrings();
		
		try {

			ZipEntry entry = null;
			while ((entry = javaZipInputStream.getNextEntry()) != null) {
			
				list.add(entry.getName());
			}
		} 
		catch (IOException ioException) {
			
			IusCLLog.logError("File exception in listing zip content", ioException);
		}

		endRead();
		
		return list;
	}

	/* **************************************************************************************************** */
	public void extractAll() {

		extractAll(null, null);
	}

	/* **************************************************************************************************** */
	public void extractAll(IusCLProgressMonitor listUnzipProgressMonitor, IusCLProgressMonitor fileUnzipProgressMonitor) {

		extractFolderContent("", listUnzipProgressMonitor, fileUnzipProgressMonitor);
	}
	
	/* **************************************************************************************************** */
	public void extractFolderIncludingName(String folderNameInZipIncludingPath) {

		extractFolderIncludingName(folderNameInZipIncludingPath, null, null);
	}

	/* **************************************************************************************************** */
	public void extractFolderIncludingName(String folderNameInZipIncludingPath, 
			IusCLProgressMonitor listUnzipProgressMonitor, IusCLProgressMonitor fileUnzipProgressMonitor) {

		IusCLStrings filesAndFolders = new IusCLStrings();
		filesAndFolders.add(folderNameInZipIncludingPath);
		internalRootFolder = IusCLFileUtils.extractResourcePath(folderNameInZipIncludingPath);
		
		extractFilesAndFoldersFromInternalRootFolder(filesAndFolders, 
				listUnzipProgressMonitor, fileUnzipProgressMonitor);
	}

	/* **************************************************************************************************** */
	public void extractFolderContent(String folderNameInZipIncludingPath) {

		extractFolderContent(folderNameInZipIncludingPath, null, null);
	}

	/* **************************************************************************************************** */
	public void extractFolderContent(String folderNameInZipIncludingPath, 
			IusCLProgressMonitor listUnzipProgressMonitor, IusCLProgressMonitor fileUnzipProgressMonitor) {

		IusCLStrings filesAndFolders = new IusCLStrings();
		filesAndFolders.add(folderNameInZipIncludingPath);
		internalRootFolder = folderNameInZipIncludingPath;
		
		extractFilesAndFoldersFromInternalRootFolder(filesAndFolders, 
				listUnzipProgressMonitor, fileUnzipProgressMonitor);
	}

	/* **************************************************************************************************** */
	public void extractFile(String fileNameInZipIncludingPath) {

		extractFile(fileNameInZipIncludingPath, null);
	}

	/* **************************************************************************************************** */
	public void extractFile(String fileNameInZipIncludingPath, IusCLProgressMonitor fileUnzipProgressMonitor) {

		IusCLStrings filesAndFolders = new IusCLStrings();
		filesAndFolders.add(fileNameInZipIncludingPath);
		internalRootFolder = IusCLFileUtils.extractResourcePath(fileNameInZipIncludingPath);
		
		extractFilesAndFoldersFromInternalRootFolder(filesAndFolders, null, fileUnzipProgressMonitor);
	}

	/* **************************************************************************************************** */
	public void extractFilesAndFoldersFromRootFolder(IusCLStrings filesAndFolders) {

		extractFilesAndFoldersFromRootFolder(filesAndFolders, null, null);
	}

	/* **************************************************************************************************** */
	public void extractFilesAndFoldersFromRootFolder(IusCLStrings filesAndFolders, 
			IusCLProgressMonitor listUnzipProgressMonitor, IusCLProgressMonitor fileUnzipProgressMonitor) {

		if (IusCLStrUtils.isNotNullNotEmpty(zipRootFolder)) {
			internalRootFolder = zipRootFolder;	
		}
		else {
			internalRootFolder = "";
		}
		
		extractFilesAndFoldersFromInternalRootFolder(filesAndFolders, 
				listUnzipProgressMonitor, fileUnzipProgressMonitor);
	}
	
	/* **************************************************************************************************** */
	private void extractFilesAndFoldersFromInternalRootFolder(IusCLStrings filesAndFolders, 
			IusCLProgressMonitor listUnzipProgressMonitor, IusCLProgressMonitor fileUnzipProgressMonitor) {

		IusCLStrings zipFilesAndFolders = new IusCLStrings();
		
		String rootFolderPrefix = internalRootFolder.toLowerCase();
		for(int index = 0; index < filesAndFolders.size(); index++) {
			
			String fileOrFolder = filesAndFolders.get(index);
			fileOrFolder = fileOrFolder.toLowerCase();
			
			if (fileOrFolder.startsWith(rootFolderPrefix)) {
				
				zipFilesAndFolders.add(fileOrFolder);
			}
		}

		zipFilesAndFolders.sort();

		/* Reduce list */
		IusCLStrings listReduce = new IusCLStrings();
		String reduceFileName = zipFilesAndFolders.get(0);
		listReduce.add(reduceFileName);
		for(int index = 0; index < zipFilesAndFolders.size(); index++) {
			
			String indexfileName = zipFilesAndFolders.get(index);
			
			if (indexfileName.startsWith(reduceFileName) == false) {
				
				reduceFileName = indexfileName;
				listReduce.add(reduceFileName);
//				System.out.println("Reduce: " + reduceFileName);
			}
		}
		
		/* Real list */
		IusCLStrings listReal = new IusCLStrings();
		IusCLStrings listZip = getFilesAndFoldersList();	
		
		for(int indexZip = 0; indexZip < listZip.size(); indexZip++) {
			
			String zipFileName = listZip.get(indexZip);
			
			for(int indexReduce = 0; indexReduce < listReduce.size(); indexReduce++) {
				
				reduceFileName = listReduce.get(indexReduce);
				
				if (zipFileName.toLowerCase().startsWith(reduceFileName)) {
					
					listReal.add(zipFileName);
//					System.out.println("Real: " + zipFileName);
					break;
				}
			}			
		}
		
		startRead();
		
		if (listUnzipProgressMonitor != null) {
			listUnzipProgressMonitor.setBegin(0);
			listUnzipProgressMonitor.setEnd(listReal.size());
		}

		Integer bufferSize = 8 * 1024;
		byte bytes[] = new byte[bufferSize];

		String destFolder = IusCLFileUtils.includeTrailingPathDelimiter(destinationFolder);
		String pd = IusCLFileUtils.getPathDelimiter();
		try {
			
			ZipEntry entryStream = null;
			Integer realIndex = 0;
			while (((entryStream = javaZipInputStream.getNextEntry()) != null) && (realIndex < listReal.size())) {
			
				String entryName = entryStream.getName();
				String zipFileName = entryName;
				if (IusCLStrUtils.equalValues(zipFileName, listReal.get(realIndex))) {

					if (listUnzipProgressMonitor != null) {
						listUnzipProgressMonitor.setShortMessage(zipFileName);
					}

					zipFileName = zipFileName.substring(internalRootFolder.length());
					zipFileName = zipFileName.replace("/", pd);
					zipFileName = destFolder + zipFileName;

					if (zipFileName.endsWith(pd)) {
						/* Folder, create  */
						IusCLFileUtils.createFolder(zipFileName);
					}
					else {
						/* File, extract */
						ZipEntry entryFile = javaZipFile.getEntry(entryName);
						Integer entrySize = (int)entryFile.getSize();
						
//						ByteArrayOutputStream javaByteArrayOutputStream = new ByteArrayOutputStream((int)entry.getSize());
						ByteArrayOutputStream javaByteArrayOutputStream = new ByteArrayOutputStream(entrySize);
						
						if (fileUnzipProgressMonitor != null) {
							fileUnzipProgressMonitor.setBegin(0);
							fileUnzipProgressMonitor.setEnd(entrySize);
						}

					    int count = 0;
					    int countTotal = 0;
					    while((count = javaZipInputStream.read(bytes, 0, bufferSize)) != -1) {
					    	
					    	javaByteArrayOutputStream.write(bytes, 0, count);

					    	if (fileUnzipProgressMonitor != null) {
					    		countTotal = countTotal + count;
								fileUnzipProgressMonitor.setPosition(countTotal);
							}
					    }						
						
				        IusCLFileUtils.writeBufferIntoFile(zipFileName, javaByteArrayOutputStream.toByteArray());
				        javaByteArrayOutputStream.close();
					}
					
					if (listUnzipProgressMonitor != null) {
						listUnzipProgressMonitor.setPosition(realIndex);
					}
					realIndex = realIndex + 1;
				}
			}
		} 
		catch (IOException ioException) {
			
			IusCLLog.logError("File exception in unzipping", ioException);
		}
		
		endRead();
	}
	
	/* **************************************************************************************************** */
	private void startRead() {

		try {

			javaZipFile = new ZipFile(zipFileName);
			javaZipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFileName))); 
		} 
		catch (FileNotFoundException fileNotFoundException) {
			
			IusCLLog.logError("Zip file not found in start read", fileNotFoundException);
		}
		catch (IOException ioException) {
			
			IusCLLog.logError("Zip file IOException in start read", ioException);
		}
	}

	/* **************************************************************************************************** */
	private void endRead() {

		try {

			javaZipInputStream.close();
			javaZipFile.close();
		} 
		catch (IOException ioException) {
			
			IusCLLog.logError("File exception in unzipping", ioException);
		}
	}

	/* **************************************************************************************************** */
	public String getZipFileName() {
		return zipFileName;
	}

	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}

	public String getDestinationFolder() {
		return destinationFolder;
	}

	public void setDestinationFolder(String destinationFolder) {
		this.destinationFolder = destinationFolder;
	}

	public String getZipRootFolder() {
		return zipRootFolder;
	}

	public void setZipRootFolder(String zipRootFolder) {
		this.zipRootFolder = zipRootFolder;
	}
	
}
