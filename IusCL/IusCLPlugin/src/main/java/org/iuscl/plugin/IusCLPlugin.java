/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.iuscl.forms.IusCLApplication;
import org.iuscl.plugin.ide.IusCLDesignException;
import org.osgi.framework.BundleContext;

/* **************************************************************************************************** */
public class IusCLPlugin extends AbstractUIPlugin {

	/* The plug-in ID */
	public static final String PLUGIN_ID = "org.iuscl.ide";

	/* The shared instance */
	private static IusCLPlugin plugin;
	
	/* The plugin's installation folder */
	private static String pluginFolder;
	
	/* **************************************************************************************************** */
	public IusCLPlugin() {
		/*  */
	}

	/* **************************************************************************************************** */
	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;
		
	    try {
	    	
	    	URL pluginURL = FileLocator.find(plugin.getBundle(), new Path("/"), null);
	    	pluginURL = FileLocator.toFileURL(pluginURL);
		    pluginFolder = new File(pluginURL.getFile()).getCanonicalPath();
		}
		catch (Exception exception) {
			
			IusCLDesignException.error("Can't find the plugin folder", exception);
		}
		
	    /* Default application for the plug-in to be used for dialogs */
		IusCLApplication.setTitle("IusCL Design Application");
		IusCLApplication.setInitialDir(pluginFolder);
	}

	/* **************************************************************************************************** */
	public void stop(BundleContext context) throws Exception {
		
		plugin = null;
		super.stop(context);
	}

	/* **************************************************************************************************** */
	public static IusCLPlugin getDefault() {
		
		return plugin;
	}

	/* **************************************************************************************************** */
	public static ImageDescriptor getImageDescriptor(String path) {
		
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/* **************************************************************************************************** */
	public static String getPluginFolder() {

		return pluginFolder;
	}
}