/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.editors;

import java.io.FileInputStream;
import java.util.Hashtable;

import org.iuscl.plugin.ide.IusCLDesignException;
import org.iuscl.sysutils.IusCLFileUtils;

/* **************************************************************************************************** */
public class IusCLFormClassLoader extends ClassLoader {
    private Hashtable<String, Class<?>> classes = new Hashtable<String, Class<?>>();
    
    private ClassLoader parentClassLoader;
    private String classesFolder;
    
	/* **************************************************************************************************** */
    public IusCLFormClassLoader() {
    	/*  */
    }

	/* **************************************************************************************************** */
    private byte getClassImplFromDisk(String classesFolder, String canonicalClassName)[] {
    	byte result[];
    	
    	try {
    		
    		String sep = IusCLFileUtils.getPathDelimiter();

    		String fileClassName = canonicalClassName.replace(".", sep) + ".class";
    		String fileClassFullPath = classesFolder + sep + fileClassName;
    	    FileInputStream fileInputStream = new FileInputStream(fileClassFullPath);

    	    result = new byte[fileInputStream.available()];
    	    fileInputStream.read(result);
    	    fileInputStream.close();
    	    
    	    return result;
    	} 
    	catch (Exception exception) {
    		
    		IusCLDesignException.error("Class implementation from disk", exception);
    	    return null;
    	}
    }

	/* **************************************************************************************************** */
    public Class<?> loadClass(String className) throws ClassNotFoundException {
    	
        return (loadClass(className, true));
    }

	/* **************************************************************************************************** */
    public synchronized Class<?> loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
        Class<?> resultClass;
        byte classData[];

        resultClass = (Class<?>)classes.get(className);
        if (resultClass != null) {
        	
            return resultClass;
        }

        try {
        	
            resultClass = parentClassLoader.loadClass(className);
            return resultClass;
        } 
        catch (ClassNotFoundException classNotFoundException) {
        	/* OK */
        }

        try {
        	
            resultClass = super.findSystemClass(className);
            return resultClass;
        } 
        catch (ClassNotFoundException classNotFoundException) {
        	/* OK */
        }

        classData = getClassImplFromDisk(classesFolder, className);
        if (classData == null) {
        	
            throw new ClassNotFoundException();
        }

        resultClass = defineClass(null, classData, 0, classData.length);
        if (resultClass == null) {
        	
            throw new ClassFormatError();
        }
        if (resolveIt) {
        	
            resolveClass(resultClass);
        }

        classes.put(className, resultClass);
    	/* OK */
        return resultClass;
    }

	/* **************************************************************************************************** */
	public ClassLoader getParentClassLoader() {
		return parentClassLoader;
	}

	public void setParentClassLoader(ClassLoader parentClassLoader) {
		this.parentClassLoader = parentClassLoader;
	}

	public String getClassesFolder() {
		return classesFolder;
	}

	public void setClassesFolder(String classesFolder) {
		this.classesFolder = classesFolder;
	}
}
