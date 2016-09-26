/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.system;

import java.net.URL;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;
import org.iuscl.classes.IusCLStrings;
import org.iuscl.dialogs.IusCLDialogs;
import org.iuscl.forms.IusCLApplication;
import org.iuscl.sysutils.IusCLFileUtils;

/* **************************************************************************************************** */
public class IusCLLog {

	private static Logger log = Logger.getLogger("org.iuscl.log");

	private static String logPropertiesFile = IusCLFileUtils.includeTrailingPathDelimiter(
			IusCLApplication.getInitialDir()) + 
			"conf" + IusCLFileUtils.getPathDelimiter() + "log4j.properties";

	private static String logFile = IusCLFileUtils.includeTrailingPathDelimiter(
			IusCLApplication.getInitialDir()) + 
			"log" + IusCLFileUtils.getPathDelimiter() + "iuscl.log";
	
	private static final String sep =
			"********************************************************************************";

	/* **************************************************************************************************** */
	static {
		/* IusCL Log */
		String propertiesFileName = logPropertiesFile; 
		
		if (IusCLFileUtils.fileExists(logPropertiesFile)) {
			
			PropertyConfigurator.configure(logPropertiesFile);
		}
		else {
			
			URL urlPropertiesFile = IusCLLog.class.getClassLoader().getResource("conf/log4j.properties");
			PropertyConfigurator.configure(urlPropertiesFile);
			propertiesFileName = urlPropertiesFile.toString();
		}
		logFile = setLogFile();
		
		IusCLStrings startLines = new IusCLStrings();
		startLines.add(sep);
		startLines.add("Start IusCL logger sesssion");
		startLines.add("Default configuration: " + propertiesFileName);
		startLines.add("Log file: " + logFile);
		startLines.add(sep);
		
		log.info(startLines.getText());
	}

	/* **************************************************************************************************** */
	private static String setLogFile() {
		
		Appender logFileAppender = log.getAppender("logfile");
		if (logFileAppender != null) {
			
			RollingFileAppender rollingFileAppender = (RollingFileAppender)logFileAppender;
			String fileName = rollingFileAppender.getFile();
			if (fileName.equalsIgnoreCase("log/iuscl.log")) {
				
				rollingFileAppender.setFile(logFile);
				rollingFileAppender.activateOptions();
				return logFile;
			}
			else {
				
				return fileName;
			}
		}
		
		return "Custom defined.";
	}

	/* **************************************************************************************************** */
	public static void logInfo(String infoMessage) {
		
		log.info(infoMessage);
	}

	/* **************************************************************************************************** */
	public static void logWarning(String warningMessage) {
		
		log.info(warningMessage);
	}
	
	/* **************************************************************************************************** */
	public static void logError(String errorMessage) {
		
		logError(errorMessage, null);
	}
	
	/* **************************************************************************************************** */
	public static void logError(String errorMessage, Throwable t) {
		
		if (t == null) {
			
			log.error(errorMessage);
		}
		else {
			
			IusCLStrings errorLines = new IusCLStrings();
			errorLines.setText(errorMessage);
			errorLines.add("");
			errorLines.add(sep);
			errorLines.add("");
			
			log.error(errorLines.getText(), t);
		}

		IusCLStrings errorDialogLines = new IusCLStrings();
		errorDialogLines.setText(errorMessage);
		errorDialogLines.add("");
		errorDialogLines.add("See log file:");
		errorDialogLines.add(logFile);
		errorDialogLines.add("for the stack trace");
		
		IusCLDialogs.showError(errorDialogLines.getText());
	}

	/* **************************************************************************************************** */
	public static void logDebug(String debugMessage) {
		
		log.debug(debugMessage);
	}
	
	/* **************************************************************************************************** */
	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		IusCLLog.log = log;
	}
	
}
