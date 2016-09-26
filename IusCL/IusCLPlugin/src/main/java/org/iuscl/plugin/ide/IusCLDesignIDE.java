/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.ide;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteListener;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.palette.DefaultPaletteViewerPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerPreferences;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLStrings;
import org.iuscl.designintf.componenteditors.IusCLDesignComponentEditor;
import org.iuscl.designintf.componenteditors.IusCLDesignDefaultComponentEditor;
import org.iuscl.designintf.packages.IusCLDesignComponentInfo;
import org.iuscl.designintf.packages.IusCLDesignEventInfo;
import org.iuscl.designintf.packages.IusCLDesignPackage;
import org.iuscl.designintf.packages.IusCLDesignPropertyInfo;
import org.iuscl.obj.IusCLObjUtils;
import org.iuscl.plugin.IusCLPlugin;
import org.iuscl.plugin.editors.IusCLFormDesignEditor;
import org.iuscl.plugin.views.IusCLObjectInspectorView;
import org.iuscl.plugin.views.IusCLObjectTreeViewView;
import org.iuscl.plugin.views.PaletteComponentModel;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLStrUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/* **************************************************************************************************** */
public class IusCLDesignIDE {

	/* IDE */
	public enum IusCLDesignIDEState {dsCreate, dsActivate, dsSelection, dsChange, dsDelete, dsDispose};
	
	private static IusCLObjectTreeViewView objectTreeViewView;
	private static IusCLObjectInspectorView objectInspectorView;
	private static IusCLFormDesignEditor formDesignEditor;
	
	/* Palette */
	private static CreationToolEntry paletteCreationToolEntry = null;
	private static PaletteViewer paletteViewerIDE = null;
	private static HashMap<String, Image> designComponentImages = new HashMap<String, Image>();
	
	/* Packages */
	private static ArrayList<String> designPackagesNames = new ArrayList<String>();
	private static HashMap<String, IusCLDesignPackage> designPackages = 
		new HashMap<String, IusCLDesignPackage>();

	/* Components (with Editors) */
	private static HashMap<String, IusCLDesignComponentInfo> designComponentInfos = 
		new HashMap<String, IusCLDesignComponentInfo>();

	private static HashMap<String, IusCLDesignComponentEditor> designComponentEditors = 
		new HashMap<String, IusCLDesignComponentEditor>();

	/* Properties */
	private static HashMap<String, IusCLDesignPropertyInfo> designPropertyInfos = 
		new HashMap<String, IusCLDesignPropertyInfo>();

	/* Events */
	private static HashMap<String, IusCLDesignEventInfo> designEventInfos = 
		new HashMap<String, IusCLDesignEventInfo>();
	
	/* Code templates */
	private static IusCLStrings designCodeTemplates = new IusCLStrings();

	/* **************************************************************************************************** */
	static {
		
		loadDesignPackagesClassNames();
		loadDesignPackages();
	}
	
	/* **************************************************************************************************** */
	private static void loadDesignPackagesClassNames() {

		/* Runtime package */
		designPackagesNames.add("org.iuscl.designintf.packages.IusCLDesignPackageUsr");

		/* Other packages */
		String pd = IusCLFileUtils.getPathDelimiter();
		String packagesConf = IusCLPlugin.getPluginFolder() + pd + "conf" + pd + "iuscl-packages.xml"; 
		
		SAXBuilder jdomBuilder = new SAXBuilder();
		Document jdomDocument = null;

		try {

			jdomDocument = jdomBuilder.build(new File(packagesConf));
			
			List<?> packagesClassNames = jdomDocument.getRootElement().getChildren();
			
			for (int index = 0; index < packagesClassNames.size(); index++) {
				
				Element jdomPackageElement = (Element)packagesClassNames.get(index);
				
				designPackagesNames.add(jdomPackageElement.getChildTextTrim("class"));
			}
		}
		catch (IOException ioException) {
			
			IusCLDesignException.error("loadDesignPackagesClassNames File Error", ioException);
		}
		catch (JDOMException jdomException) {
			
			IusCLDesignException.error("loadDesignPackagesClassNames XML Error", jdomException);
		}
		
//		designPackagesNames.add("org.iuscl.designintf.packages.IusCLDesignPackagePdf");
//		designPackagesNames.add("org.iuscl.designintf.packages.IusCLDesignPackageNet");
	}

	/* **************************************************************************************************** */
	private static void loadDesignPackages() {

		/* Loop the design packages */
		for (int indexPackage = 0; indexPackage < designPackagesNames.size(); indexPackage++) {
			
			/* Load designPackage */
			String designPackageClassName = designPackagesNames.get(indexPackage);
			IusCLDesignPackage designPackage = getDesignPackage(designPackageClassName);
			
			/* Load designPropertyInfos */
			for (int index = 0; index < designPackage.getDesignPropertyInfos().size(); index++) {
				
				IusCLDesignPropertyInfo propertyInfo = designPackage.getDesignPropertyInfos().get(index);
				designPropertyInfos.put(propertyInfo.getName(), propertyInfo);
			}

			/* Load designEventTemplates */
			for (int index = 0; index < designPackage.getDesignEventInfos().size(); index++) {
				
				IusCLDesignEventInfo eventInfo = designPackage.getDesignEventInfos().get(index);
				designEventInfos.put(eventInfo.getEventName(), eventInfo);
			}

			/* Load designComponentInfos */
			for (int index = 0; index < designPackage.getDesignComponentInfos().size(); index++) {
				
				IusCLDesignComponentInfo designComponentInfo = designPackage.getDesignComponentInfos().get(index);
				designComponentInfos.put(designComponentInfo.getComponentClass(), designComponentInfo);
			}

			/* Load designCodeTemplates */
			if (designPackage.getCodeTemplatesFile() != null) {

				IusCLStrings packageCodeTemplates = new IusCLStrings();
				packageCodeTemplates.loadFromResource(designPackage.getClass(), 
						"/resources/designintf/texts/" + designPackage.getCodeTemplatesFile());

				designCodeTemplates.append(packageCodeTemplates);
			}
		}
	}

	/* **************************************************************************************************** */
	public static PaletteViewer createPaletteViewer(Composite parent, final Action actionCursor) {
		
		PaletteViewer paletteViewer = new PaletteViewer();
		paletteViewer.addPaletteListener(new PaletteListener() {
			/* **************************************************************************************************** */
			@Override
			public void activeToolChanged(PaletteViewer paletteViewer, ToolEntry toolEntry) {
				
				if (toolEntry instanceof CreationToolEntry) {
					
					actionCursor.setChecked(false);
					paletteCreationToolEntry = (CreationToolEntry)toolEntry;
				}
			}
		});
		paletteViewer.createControl(parent);
		
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteViewer.setPaletteRoot(paletteRoot);

		HashMap<String, PaletteDrawer> componentPalettePages = new HashMap<String, PaletteDrawer>();
		
		/*  Loop the design packages */
		for (int indexPackage = 0; indexPackage < designPackagesNames.size(); indexPackage++) {
			
			String designPackageClassName = designPackagesNames.get(indexPackage);
			IusCLDesignPackage designPackage = getDesignPackage(designPackageClassName);

			/* Loop the package design component info */
			for (int index = 0; index < designPackage.getDesignComponentInfos().size(); index++) {
				
				IusCLDesignComponentInfo designComponentInfo = designPackage.getDesignComponentInfos().get(index);
				
				/*
				 * Could have been removed by the user..
				 * OR
				 * The place could of been changed by the user..
				 */
		
				PaletteDrawer componentPalettePage;
				String pageName = designComponentInfo.getPalettePage();
				
				if (pageName != null) {
					
					if (componentPalettePages.containsKey(pageName)) {
						
						componentPalettePage = componentPalettePages.get(pageName);
					} 
					else {
						
						componentPalettePage = new PaletteDrawer(pageName);
						componentPalettePages.put(pageName, componentPalettePage);
						paletteRoot.add(componentPalettePage);
					}
	
					ImageDescriptor componentImageDescriptor = null;
					URL url = designPackage.getClass().getClassLoader().getResource(designComponentInfo.getPaletteImage());
					componentImageDescriptor = ImageDescriptor.createFromURL(url); 
					
					if (componentImageDescriptor == null) {
						
						IusCLDesignPackage usrDesignPackage = designPackages.get("org.iuscl.designintf.packages.IusCLDesignPackageUsr");
						String resNotFound = "resources/designintf/images/IusCLDesignNotFound.gif";
						url = usrDesignPackage.getClass().getClassLoader().getResource(resNotFound);
						componentImageDescriptor = ImageDescriptor.createFromURL(url); 
					}
					
					CreationToolEntry creationToolEntry = new CreationToolEntry(
							designComponentInfo.getPaletteCaption(), 
							designComponentInfo.getName() + "\n" + designComponentInfo.getComponentClass(), 
							new SimpleFactory(PaletteComponentModel.class), 
							componentImageDescriptor, 
							componentImageDescriptor);
					creationToolEntry.setId(designComponentInfo.getComponentClass());
	
					componentPalettePage.add(creationToolEntry);
				}
			}
		}
				
		PaletteViewerPreferences palettePreferences = new DefaultPaletteViewerPreferences();
		palettePreferences.setLayoutSetting(PaletteViewerPreferences.LAYOUT_COLUMNS);
		palettePreferences.setUseLargeIcons(PaletteViewerPreferences.LAYOUT_COLUMNS, true);
		paletteViewer.setPaletteViewerPreferences(palettePreferences);

		paletteViewerIDE = paletteViewer;
		return paletteViewer;
	}
	
	/* **************************************************************************************************** */
	public static String getCodeTemplate(String codeTemplateName) {
		
		int beginLine = designCodeTemplates.indexOf("begin " + codeTemplateName);
		int endLine = designCodeTemplates.indexOf("end " + codeTemplateName);
		
		String codeTemplate = "";
		
		for (int index = beginLine + 1; index < endLine; index++) {
			
			codeTemplate = codeTemplate + designCodeTemplates.get(index) + IusCLStrUtils.sLineBreak();
		}
		
		return codeTemplate;
	}

	/* **************************************************************************************************** */
	public static String getPaletteComponent() {
		
		String paletteComponent = null;
		
		if (paletteCreationToolEntry != null) {
			
			paletteComponent = paletteCreationToolEntry.getId();
		}
		
		return paletteComponent;
	}

	/* **************************************************************************************************** */
	public static void resetPaletteComponent() {
		
		paletteViewerIDE.setActiveTool(null);
		IusCLDesignIDE.paletteCreationToolEntry = null;
	}
	
	/* **************************************************************************************************** */
	public static Image loadImageFromResource(String resource) {
		
		InputStream inputStream = IusCLDesignIDE.class.getResourceAsStream("/resources/images/" + resource);
		return new Image(Display.getDefault(), inputStream);
	}

	/* **************************************************************************************************** */
	public static String loadTextFromResource(String resource) {

		IusCLStrings lines = new IusCLStrings();
		lines.loadFromResource(IusCLDesignIDE.class, "/resources/texts/" + resource);
		return lines.getText();
	}

	/* **************************************************************************************************** */
	private static IusCLDesignPackage getDesignPackage(String designPackageClassName) {
		
		IusCLDesignPackage designPackage = designPackages.get(designPackageClassName);
		
		if (designPackage == null) {
			
			try {
				
				Class<?> clazz = IusCLDesignIDE.class.getClassLoader().loadClass(designPackageClassName);
				designPackage = (IusCLDesignPackage)clazz.newInstance();
				designPackages.put(designPackageClassName, designPackage);
			}
			catch (Exception reflectionException) {
				
				IusCLDesignException.error("Reflection exception in design package", reflectionException);
			}
		}
		
		return designPackage;
	}

	/* **************************************************************************************************** */
	public static Image loadDesignComponentImage(IusCLComponent component) {
		
		Image designComponentImage = designComponentImages.get(component.getClass().getCanonicalName());
		
		if (designComponentImage == null) {
			
			designComponentImage = createDesignComponentImage(component);
			designComponentImages.put(component.getClass().getCanonicalName(), designComponentImage);
		}
		
		return designComponentImage;
	}

	/* **************************************************************************************************** */
	private static IusCLDesignPackage findDesignPackageForComponent(IusCLComponent component) {
		
		return findDesignPackageForComponent(component.getClass().getCanonicalName());
	}

	/* **************************************************************************************************** */
	private static IusCLDesignPackage findDesignPackageForComponent(String componentClassName) {
		
		for (int indexPackage = 0; indexPackage < designPackagesNames.size(); indexPackage++) {
			
			String designPackageClassName = designPackagesNames.get(indexPackage);
			IusCLDesignPackage designPackage = getDesignPackage(designPackageClassName);
			
			for (int indexComponent = 0; indexComponent < designPackage.getDesignComponentInfos().size(); indexComponent++) {
				
				IusCLDesignComponentInfo designComponentInfo = designPackage.getDesignComponentInfos().get(indexComponent);
				if (designComponentInfo.getComponentClass().equalsIgnoreCase(componentClassName)) {
					
					return designPackage;
				}
			}
		}
		return null;
	}
	
	/* **************************************************************************************************** */
	private static Image createDesignComponentImage(IusCLComponent component) {

		IusCLDesignPackage designPackage = findDesignPackageForComponent(component);
		
		IusCLDesignComponentInfo designComponentInfo = null;
		
		for(int index = 0; index < designPackage.getDesignComponentInfos().size(); index++) {
			if (designPackage.getDesignComponentInfos().get(index).getComponentClass().
				equalsIgnoreCase(component.getClass().getCanonicalName())) {
				
				designComponentInfo = designPackage.getDesignComponentInfos().get(index);
			}
		}

		InputStream inputStream = designPackage.getClass().getClassLoader().getResourceAsStream(designComponentInfo.getPaletteImage());
		if (inputStream == null) {
			
			IusCLDesignPackage usrDesignPackage = designPackages.get("org.iuscl.designintf.packages.IusCLDesignPackageUsr");
			String resNotFound = "resources/designintf/images/IusCLDesignNotFound.gif";
			inputStream = usrDesignPackage.getClass().getClassLoader().getResourceAsStream(resNotFound);
		}
		Image paletteImage = new Image(Display.getDefault(), inputStream);

		Color transparentColor = new Color(Display.getCurrent(), new RGB(255, 0, 255));
		Color shadowColor = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
		Color lightShadowColor = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
		Color backgroundColor = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		
		Image componentDesignImage = new Image(Display.getCurrent(), 32, 32);
		GC gc = new GC(componentDesignImage);
		gc.setBackground(transparentColor);
		gc.setForeground(transparentColor);
		gc.fillRectangle(0, 0, 32, 32);
		gc.setBackground(backgroundColor);
		gc.setForeground(backgroundColor);
		gc.fillRectangle(3, 3, 26, 26);
		gc.setForeground(lightShadowColor);
		gc.drawLine(2, 2, 28, 2);
		gc.drawLine(2, 2, 2, 28);
		gc.setForeground(shadowColor);
		gc.drawLine(29, 2, 29, 29);
		gc.drawLine(29, 29, 2, 29);
		gc.drawImage(paletteImage, 4, 4);
		gc.dispose();

		PaletteData palette = new PaletteData(new RGB[] { new RGB(0, 0, 0), new RGB(0xFF, 0xFF, 0xFF), });
		ImageData maskData = new ImageData(32, 32, 1, palette);
	    Image mask = new Image(Display.getCurrent(), maskData);
	    gc = new GC(mask);
	    gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
	    gc.fillRectangle(0, 0, 32, 32);
	    gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
	    gc.fillRectangle(2, 2, 28, 28);
	    gc.dispose();
	    maskData = mask.getImageData();
		
		Image componentDesignTransparentImage = new Image(Display.getCurrent(), componentDesignImage.getImageData(), maskData);
		
		return componentDesignTransparentImage;
	}

	/* **************************************************************************************************** */
	public static HashMap<String, IusCLDesignPropertyInfo> getDesignPropertyInfos() {
		
		return designPropertyInfos;
	}

	/* **************************************************************************************************** */
	public static HashMap<String, IusCLDesignEventInfo> getDesignEventInfos() {
		
		return designEventInfos;
	}

	/* **************************************************************************************************** */
	public static boolean getIsPaletteComponent(String componentClassName) {
		
		if (designComponentInfos.get(componentClassName) != null) {
			
			return true;
		}
		
		return false;
	}

	/* **************************************************************************************************** */
	public static IusCLDesignComponentEditor getDesignComponentEditor(String componentClassName, IusCLComponent component) {
		
		IusCLDesignComponentInfo componentInfo = designComponentInfos.get(componentClassName);
		
		String componentEditorClassName = IusCLDesignDefaultComponentEditor.class.getCanonicalName();
		
		if (componentInfo != null) {
			
			componentEditorClassName = componentInfo.getComponentEditorClass();
			if (componentEditorClassName == null) {
				
				componentEditorClassName = IusCLDesignDefaultComponentEditor.class.getCanonicalName();
			}
		}
		
		IusCLDesignComponentEditor componentEditor = designComponentEditors.get(componentEditorClassName);
		
		if (componentEditor == null) {
			
			componentEditor = (IusCLDesignComponentEditor)IusCLObjUtils.invokeConstructor(componentEditorClassName);
			designComponentEditors.put(componentClassName, componentEditor);
		}
		
		componentEditor.setComponent(component);
		
		return componentEditor;
	}

	/* **************************************************************************************************** */
	public static IusCLFormDesignEditor getFormDesignEditor() {
		
		return formDesignEditor;
	}

	/* **************************************************************************************************** */
	public static void dispatch(Object sender, IusCLDesignIDEState state) {

		/* IusCLFormDesignEditor */
		if (sender instanceof IusCLFormDesignEditor) {
			switch (state) {
			case dsCreate:
				formDesignEditor = (IusCLFormDesignEditor)sender;
				break;
			case dsActivate:
				if (!(sender.equals(formDesignEditor))) {
					formDesignEditor = (IusCLFormDesignEditor)sender;
					if (objectTreeViewView != null) {
						objectTreeViewView.initObjectTreeView(formDesignEditor.getDesignForm(),formDesignEditor.getDesignSelection());
					}
					if (objectInspectorView != null) {
						objectInspectorView.initObjectCombo(formDesignEditor.getDesignForm(),formDesignEditor.getDesignSelection());
					}
				}
				break;
			case dsSelection:
				if (objectTreeViewView == null) {
					
					IWorkbenchWindow workbenchWindow = formDesignEditor.getEditorSite().getPage().getWorkbenchWindow();
					
					try {
						workbenchWindow.getWorkbench().showPerspective("org.iuscl.plugin.perspectives.IusCLPerspective", workbenchWindow);
					}
					catch (WorkbenchException workbenchException) {
						IusCLDesignException.error("Opening IusCL perspective", workbenchException);
					}
				}
				objectTreeViewView.setDesignSelection(formDesignEditor.getDesignSelection());
				objectInspectorView.setDesignSelection(formDesignEditor.getDesignSelection());
				break;
			case dsChange:
				objectTreeViewView.initObjectTreeView(formDesignEditor.getDesignForm(), formDesignEditor.getDesignSelection());
				objectInspectorView.initObjectCombo(formDesignEditor.getDesignForm(), formDesignEditor.getDesignSelection());
				break;
			case dsDelete:
				//
				break;
			case dsDispose:
				if (sender.equals(formDesignEditor)) {
					formDesignEditor = null;
					objectTreeViewView.initObjectTreeView(null, null);
					objectInspectorView.initObjectCombo(null, null);
				}
				break;
			default:
				break;
			} 
		}
		
		/* IusCLObjectTreeViewView */
		if (sender instanceof IusCLObjectTreeViewView) {
			switch (state) {
			case dsCreate:
				objectTreeViewView = (IusCLObjectTreeViewView)sender;
				if (formDesignEditor != null) {
					objectTreeViewView.initObjectTreeView(formDesignEditor.getDesignForm(), formDesignEditor.getDesignSelection());
				}
				break;
			case dsActivate:
				//
				break;
			case dsSelection:
				objectInspectorView.setDesignSelection(formDesignEditor.getDesignSelection());
				formDesignEditor.setDesignSelection(formDesignEditor.getDesignSelection());
				break;
			case dsChange:
				//
				break;
			case dsDelete:
				formDesignEditor.deleteDesignComponent();
				break;
			case dsDispose:
				//
				break;
			default:
				break;
			} 
		}

		/* IusCLObjectInspectorView */
		if (sender instanceof IusCLObjectInspectorView) {
			switch (state) {
			case dsCreate:
				objectInspectorView = (IusCLObjectInspectorView)sender;
				if (formDesignEditor != null) {
					objectInspectorView.initObjectCombo(formDesignEditor.getDesignForm(), formDesignEditor.getDesignSelection());
				}
				break;
			case dsActivate:
				//
				break;
			case dsSelection:
				objectTreeViewView.setDesignSelection(objectInspectorView.getDesignSelection());
				formDesignEditor.setDesignSelection(objectInspectorView.getDesignSelection());
				break;
			case dsChange:
				formDesignEditor.change(objectInspectorView.getDesignPropertyName(), objectInspectorView.getOldDesignPropertyValue(), objectInspectorView.getDesignSelection());
				break;
			case dsDelete:
				//
				break;
			case dsDispose:
				//
				break;
			default:
				break;
			} 
		}
	}
}
