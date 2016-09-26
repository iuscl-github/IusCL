/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.plugin.editors;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.classes.IusCLFiler;
import org.iuscl.classes.IusCLPersistent;
import org.iuscl.classes.IusCLPersistent.IusCLPropertyType;
import org.iuscl.classes.IusCLProperty;
import org.iuscl.classes.IusCLStrings;
import org.iuscl.controls.IusCLContainerControl;
import org.iuscl.controls.IusCLControl;
import org.iuscl.controls.IusCLControl.IusCLAlign;
import org.iuscl.controls.IusCLParentControl;
import org.iuscl.controls.IusCLWinControl;
import org.iuscl.designintf.componenteditors.IusCLDesignComponentEditor;
import org.iuscl.designintf.componenteditors.IusCLDesignComponentEditorVerb;
import org.iuscl.designintf.componenteditors.IusCLDesignSelection;
import org.iuscl.designintf.packages.IusCLDesignEventInfo;
import org.iuscl.events.IusCLNotifyEvent;
import org.iuscl.extctrls.IusCLImage;
import org.iuscl.forms.IusCLApplication;
import org.iuscl.forms.IusCLForm;
import org.iuscl.forms.IusCLForm.IusCLFormBorderStyle;
import org.iuscl.forms.IusCLForm.IusCLFormPosition;
import org.iuscl.forms.IusCLForm.IusCLFormStyle;
import org.iuscl.forms.IusCLScreen;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLColor.IusCLStandardColors;
import org.iuscl.graphics.IusCLPicture;
import org.iuscl.menus.IusCLMainMenu;
import org.iuscl.menus.IusCLMenuItem;
import org.iuscl.obj.IusCLObjUtils;
import org.iuscl.obj.IusCLParam;
import org.iuscl.plugin.ide.IusCLDesignException;
import org.iuscl.plugin.ide.IusCLDesignIDE;
import org.iuscl.plugin.ide.IusCLDesignIDE.IusCLDesignIDEState;
import org.iuscl.stdctrls.IusCLLabel;
import org.iuscl.stdctrls.IusCLLabel.IusCLAlignment;
import org.iuscl.system.IusCLObject;
import org.iuscl.sysutils.IusCLFileSearchRec;
import org.iuscl.sysutils.IusCLFileUtils;
import org.iuscl.sysutils.IusCLStrUtils;
import org.iuscl.types.IusCLPoint;

/* **************************************************************************************************** */
public class IusCLFormDesignEditor extends MultiPageEditorPart implements IResourceChangeListener{

	final private static int DESIGN_PAGE = 0;
	final private static int NONCONTROLS_PAGE = 1;
	final private static int VIEWASTEXT_PAGE = 2;

	private enum IusCLThumb { C, N, S, E, W, NE, SE, SW, NW };
	
	final private static int DESIGN_BORDER = 24;
	final private static int THUMB_SIZE = 5;
	final private static int TRACKER_BORDER = 2;

	final private static Color GRID_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	final private static Color THUMB_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	final private static Color TRACKER_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
	final private static Color TRACKER_GRID_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
	final private static Color DESIGN_BACKGROUND_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);

	/* Fields */
	private IusCLForm designForm = null;
	private IusCLComponent designComponent = null;
	private IusCLDesignSelection designSelection = null;

	private IusCLControl designControl = null;
	private Control swtDesignControl = null;
	
	private String newComponentName = null;
	
	private Menu contextMenu = null;
	
	private Image imageCapture = null;
	private Integer leftCapture = null;
	private Integer topCapture = null;
	
	/* Form Designer page */
	private ScrolledComposite designPageScrolledComposite = null;
	private Composite designPageComposite = null;
	private Composite borderCaptureComposite = null;
	private Composite formReplacementComposite = null;

	private Composite designComponentTracker = null;

	IusCLThumb dragThumb = null;
	private boolean isDraggingCenter = false;
	private boolean isDraggingThumb = false;
	private int initialMouseLeft = 0;
	private int initialMouseTop = 0;

	private int gridX = 8;
	private int gridY = 8;
	private boolean alignToGrid = true;
	
	private int menuBarHeight = 0;
	
	private Composite thumbN = null;
	private Composite thumbS = null;
	private Composite thumbE = null;
	private Composite thumbW = null;
	private Composite thumbNE = null;
	private Composite thumbSE = null;
	private Composite thumbSW = null;
	private Composite thumbNW = null;
	
	/* Non Visual Components page */
	private Table nonVisualComponentsTable;
	
	/* View As Text page */
	private StructuredTextEditor formIusCLFMXMLEditor;
	private ITextEditor formJavaSourceTextEditor = null;
	private ITextEditor applicationJavaSourceTextEditor = null;

	/* Listeners */

	public Listener contextMenuListener = new Listener() {
		/* **************************************************************************************************** */
		@Override
		public void handleEvent(Event menuEvent) {
			
			if (!contextMenu.getData().equals(designComponent)) {

				/* Remove previously added items */
				int indexRemove = 0;
				while (indexRemove < contextMenu.getItems().length) {
					
					MenuItem swtMenuItem = contextMenu.getItems()[indexRemove];
					
					if (swtMenuItem.getData() != null) {
						
						swtMenuItem.dispose();
					}
					else {
						
						indexRemove++;
					}
				}
				
				/* Component editor */
				final IusCLDesignComponentEditor componentEditor = 
					IusCLDesignIDE.getDesignComponentEditor(designComponent.getClass().getCanonicalName(), designComponent);
				
				/* Menu items to add */
				int verbs = componentEditor.getVerbCount();
				
				if (verbs > 0) {
					
					MenuItem swtMenuItem = new MenuItem(contextMenu, SWT.SEPARATOR, 0);
					swtMenuItem.setData(designComponent);

					for (int indexAdd = verbs - 1; indexAdd >= 0; indexAdd--) {
						
						swtMenuItem = new MenuItem(contextMenu, SWT.PUSH, 0);
						swtMenuItem.setText(componentEditor.getVerb(indexAdd));
						swtMenuItem.setData(indexAdd);
						
						swtMenuItem.addSelectionListener(new SelectionAdapter() {
							
							/* **************************************************************************************************** */
							@Override
							public void widgetSelected(SelectionEvent selectionEvent) {
								
								MenuItem selectedMenuItem = (MenuItem)selectionEvent.widget;

								IusCLDesignComponentEditorVerb verb = 
									componentEditor.executeVerb((Integer)(selectedMenuItem.getData()));

								if (verb != null) {
									/* Invoke the verb */
									IusCLObjUtils.invokeMethod(IusCLFormDesignEditor.this,
											verb.getMethodName(), verb.getParams());
								}
							}
						});
						
						componentEditor.prepareItem(indexAdd, swtMenuItem);
					}
				}
				
				contextMenu.setData(designComponent);
			}
			
			contextMenu.setLocation(menuEvent.x, menuEvent.y);
			contextMenu.setVisible(true);
		}
	};

	private Listener designComponentGridResizeListener = new Listener() {
		/* **************************************************************************************************** */
		@Override
		public void handleEvent(Event resizeEvent) {
			
			Control swtControl = (Control)resizeEvent.widget;
			
			if (swtControl.getBackgroundImage() != null) {
				
				removeDesignGrid(swtControl);
				putDesignGrid(swtControl);
			}
		}
	};

	private MouseListener designComponentMouseListener = new MouseListener() {
		/* **************************************************************************************************** */
		@Override
		public void mouseUp(MouseEvent mouseEvent) {

			if (isDraggingCenter == true) {
				
				int trackerLeft = designComponentTracker.getLocation().x;
				int trackerTop = designComponentTracker.getLocation().y;
				int trackerWidth = designComponentTracker.getSize().x;
				int trackerHeight = designComponentTracker.getSize().y;

				if (!((trackerLeft == 0) && (trackerTop == 0) && (trackerWidth == 0) && (trackerHeight == 0))) {
					
					IusCLContainerControl containerControl = (IusCLContainerControl)designComponentTracker.getParent().getData();
					
					designControl.setBounds(
							trackerLeft - containerControl.getContainerClientLeft(), 
							trackerTop - containerControl.getContainerClientTop(), 
							trackerWidth, trackerHeight);
				}
				
				designControl.doParentAlignControls();
				
				dragThumb = null;
				isDraggingCenter = false;
				removeDesignGrid(designComponentTracker);
				designComponentTracker.dispose();
				
				showThumbs(swtDesignControl);
				
				IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsChange);
				serializeChange();
			}
		}
		
		/* **************************************************************************************************** */
		@Override
		public void mouseDown(MouseEvent mouseEvent) {

			Control swtMouseDownControl = (Control)mouseEvent.getSource();
			IusCLControl mouseDownControl = (IusCLControl)swtMouseDownControl.getData();
			
			if (mouseDownControl != designComponent) {
				
				setDesignComponent(mouseDownControl);
				IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsSelection);
				//return;
			}
			else {
				
				if (mouseEvent.button == 3) {
					
					return;
				}
			}
			
			/* Drop palette component */
			if (IusCLDesignIDE.getPaletteComponent() != null) {
				/* Create the palette component */
				String paletteComponentCanonicalClassName = IusCLDesignIDE.getPaletteComponent();
				
				addDesignPaletteComponent(paletteComponentCanonicalClassName, 
						mouseDownControl, new IusCLPoint(mouseEvent.x, mouseEvent.y));
				
				IusCLDesignIDE.resetPaletteComponent();
				return;
			}

			if (swtMouseDownControl == formReplacementComposite) {
				
				return;
			}
			
			if (isDraggingCenter == false) {
				
				disposeThumbs();
				if (designControl.getParent() instanceof IusCLContainerControl) {
					
					isDraggingCenter = true;
				}
			}
			
			if (isDraggingCenter == true) {
				
				initialMouseLeft = 0;
				initialMouseTop = 0;
				designComponentTracker = new Composite(swtMouseDownControl.getParent(), SWT.NONE);
				designComponentTracker.setBackground(TRACKER_COLOR);
				designComponentTracker.moveAbove(null);
			}
		}
		/* **************************************************************************************************** */
		@Override
		public void mouseDoubleClick(MouseEvent mouseEvent) {

			invokeEditVerb();
			isDraggingCenter = false;
		}
	};
	
	private MouseMoveListener designComponentMouseMoveListener = new MouseMoveListener() {
		/* **************************************************************************************************** */
		@Override
		public void mouseMove(MouseEvent mouseEvent) {

			if (isDraggingCenter == true) {
				
				int controlLeft = swtDesignControl.getLocation().x;
				int controlTop = swtDesignControl.getLocation().y;
				
				int mouseLeft = mouseEvent.x;
				int mouseTop = mouseEvent.y;
				
				if (initialMouseLeft == 0) {
					
					initialMouseLeft = mouseLeft;
				}
				if (initialMouseTop == 0) {
					
					initialMouseTop = mouseTop;
				}
				
				int trackerLeft = findGridX(controlLeft + mouseLeft) + controlLeft - 
					(findGridX(controlLeft) + gridX * (initialMouseLeft / gridX));
				int trackerTop = findGridY(controlTop + mouseTop) + controlTop - 
					(findGridY(controlTop) + gridY * (initialMouseTop / gridY));
				int trackerWidth = swtDesignControl.getSize().x;
				int trackerHeight = swtDesignControl.getSize().y;

				removeDesignGrid(designComponentTracker);
				
				if (designComponentTracker.getRegion() != null) {
					
					designComponentTracker.getRegion().dispose();
					designComponentTracker.setRegion(null);
				}
				Region trackerRegion = new Region();
				trackerRegion.add(0, trackerHeight - TRACKER_BORDER, trackerWidth, trackerHeight);
				trackerRegion.add(trackerWidth - TRACKER_BORDER, 0, trackerWidth, trackerHeight);
				trackerRegion.add(0, 0, trackerWidth, TRACKER_BORDER);
				trackerRegion.add(0, 0, TRACKER_BORDER, trackerHeight);
				designComponentTracker.setRegion(trackerRegion);
				designComponentTracker.setBounds(trackerLeft, trackerTop, trackerWidth, trackerHeight);
				
				int gridMarginX = findGridX(trackerLeft) - (trackerLeft + gridX);
				int gridMarginY = findGridY(trackerTop) - (trackerTop + gridY);
				putDesignGrid(designComponentTracker, gridMarginX, gridMarginY, TRACKER_GRID_COLOR);
				designComponentTracker.redraw();

				invokeContributor(trackerLeft, trackerTop, trackerWidth, trackerHeight);
			}
		}
	};

	/* **************************************************************************************************** */
	private void invokeContributor() {
		
		int left = 0;
		int top = 0;
		int width = 0;
		int height = 0;
		
		if (designControl != null) {

			left = designControl.getLeft();
			top = designControl.getTop();
			width = designControl.getWidth();
			height = designControl.getHeight();
		}
		invokeContributor(left, top, width, height);	
	}

	/* **************************************************************************************************** */
	private void invokeContributor(int left, int top, int width, int height) {

		String statusBarMessage = designComponent.getClass().getSimpleName() + " " + designComponent.getName();
		
		if (designControl != null) {
			
			statusBarMessage = statusBarMessage + " (" + left + ", " + top + " - " + width + ", " + height + ")";
		}
			
		if (designControl instanceof IusCLWinControl) {
			
			statusBarMessage = statusBarMessage + 
					" tab order " + ((IusCLWinControl)designControl).getTabOrder() + 
					", tab stop " + ((IusCLWinControl)designControl).getTabStop();
		}
		
		IusCLFormDesignEditorContributor.getStatusField().setText(statusBarMessage);
	}

	/* **************************************************************************************************** */
	private void invokeEditVerb() {
		
		IusCLDesignComponentEditor componentEditor = IusCLDesignIDE.
				getDesignComponentEditor(designComponent.getClass().getCanonicalName(), designComponent);
			
		IusCLDesignComponentEditorVerb verb = componentEditor.edit();

		if (verb != null) {
			/* Invoke the edit verb */
			IusCLObjUtils.invokeMethod(IusCLFormDesignEditor.this, verb.getMethodName(), verb.getParams());
		}
	}
	
	/* **************************************************************************************************** */
	private int findGridX(int coordinate) {
		
		IusCLContainerControl containerControl = (IusCLContainerControl)designControl.getParent();
		return findGridX(containerControl, coordinate) + containerControl.getContainerClientLeft();
	}
	
	/* **************************************************************************************************** */
	private int findGridX(IusCLContainerControl gridControl, int coordinate) {
		
		if (alignToGrid == false) {
			
			return coordinate;
		}
		int margin = gridControl.getContainerClientLeft();
//		int gridCoordinate = ((coordinate - margin) / gridX + 1) * gridX; 
		int gridCoordinate = ((coordinate - margin) / gridX) * gridX; 
		
		return gridCoordinate;
	}

	/* **************************************************************************************************** */
	private int findGridY(int coordinate) {

		IusCLContainerControl containerControl = (IusCLContainerControl)designControl.getParent();
		return findGridY(containerControl, coordinate) + containerControl.getContainerClientTop();
	}
	
	/* **************************************************************************************************** */
	private int findGridY(IusCLContainerControl gridControl, int coordinate) {
		
		if (alignToGrid == false) {
			
			return coordinate;
		}
		int margin = gridControl.getContainerClientTop();
//		int gridCoordinate = ((coordinate - margin) / gridY + 1) * gridY; 
		int gridCoordinate = ((coordinate - margin) / gridY) * gridY; 
		
		return gridCoordinate;
	}

	/* **************************************************************************************************** */
	@Override
	protected IEditorSite createSite(IEditorPart page) {
		IEditorSite site = null;
		if (page == formIusCLFMXMLEditor) {
			site = new MultiPageEditorSite(this, page) {
				public String getId() {
					/* Sets this ID so nested editor is configured for XML source */
					return "org.eclipse.core.runtime.xml.source";
					//return ContentTypeIdForXML.ContentTypeID_XML + ".source"; //$NON-NLS-1$;
				}
			};
		}
		else {
			site = super.createSite(page);
		}
		return site;
	}

	/* **************************************************************************************************** */
	public void change(String propertyName, String oldPropertyValue, IusCLDesignSelection designSelection) {

		this.designSelection = designSelection;
		IusCLPersistent designPersistent = designSelection.findPersistent();

		IusCLProperty changeProperty = designPersistent.getProperty(propertyName);
		
		/* Event */
		if (IusCLStrUtils.equalValues(changeProperty.getType(), IusCLPropertyType.ptEvent.name())) {
			
			String eventFunctionName = designPersistent.getPropertyValue(propertyName);
			if (IusCLStrUtils.isNotNullNotEmpty(eventFunctionName)) { 
				
				/* The event is defined */
				String persistentName = designPersistent.getPersistentName();
				String javaSource =	getFormJavaSource();

				int selPos = -1;
				
				int defPos = javaSource.indexOf("public void " + eventFunctionName + "(");
				if (defPos > -1) {
					/* The event implementation name is already defined */
					String sep = IusCLStrUtils.sLineBreak() + "\t\t";
					selPos = javaSource.indexOf(sep, defPos) + sep.length();
				}
				else if (IusCLStrUtils.isNotNullNotEmpty(oldPropertyValue)) { 
					/* The event was implemented under another name, rename */
					javaSource = IusCLStrUtils.replaceAllWholeWord(javaSource, oldPropertyValue, eventFunctionName);
					setFormJavaSource(javaSource);

					selPos = javaSource.indexOf("public void " + eventFunctionName + "(");
					String sep = IusCLStrUtils.sLineBreak() + "\t\t";
					selPos = javaSource.indexOf(sep, selPos) + sep.length();
				}
				else {
					/* The event implementation is not defined  */
					Class<?> eventClass = changeProperty.getRefClass();
					IusCLDesignEventInfo eventInfo = IusCLDesignIDE.getDesignEventInfos().get(eventClass.getSimpleName());

					/* Imports */
					for (int importIndex = 0; importIndex < eventInfo.getImports().size(); importIndex++) {
						
						javaSource = addImport(javaSource, eventInfo.getImports().get(importIndex));
					}
					
					/* Event code */
					String codeTemplate = eventInfo.getCodeTemplateContent();
					
					if (codeTemplate == null) {
						
						codeTemplate = IusCLDesignIDE.getCodeTemplate(eventInfo.getCodeTemplateName());
					}
					
					codeTemplate = codeTemplate.replace("${component}", persistentName);
					codeTemplate = codeTemplate.replace("${event}", propertyName);
					codeTemplate = codeTemplate.replace("${implementation}", eventFunctionName);
					selPos = codeTemplate.indexOf("${caret}");
					codeTemplate = codeTemplate.replace("${caret}", "");

					int insertPos = javaSource.lastIndexOf("}") - 1;
					javaSource = javaSource.substring(0, insertPos) +
							codeTemplate + IusCLStrUtils.sLineBreak() + "}";
					setFormJavaSource(javaSource);
					
					selPos = insertPos + selPos;
				}
				/* Move the cursor */
				getFormJavaSourceTextEditor().selectAndReveal(selPos, 0);
				getFormJavaSourceTextEditor().setHighlightRange(selPos, 0, true);
				this.getSite().getPage().activate(getFormJavaSourceTextEditor());
			}
			
			if (swtDesignControl != null) {
				
				removeAllAddedListeners(swtDesignControl);
				swtDesignControl.addMouseListener(designComponentMouseListener);
				swtDesignControl.addMouseMoveListener(designComponentMouseMoveListener);
				swtDesignControl.addListener(SWT.MenuDetect, contextMenuListener);
			}
		}
		
		/* Name property */
		if (propertyName.equalsIgnoreCase("Name")) {
			
			/* Get form code */
			String formJavaSource =	getFormJavaSource();
			
			/* Name string */
			String newPropertyValue = designComponent.getName();
			formJavaSource = IusCLStrUtils.replaceAllWholeWord(
					formJavaSource, oldPropertyValue, newPropertyValue);
			
			/* Name in events strings */
			for (Iterator<String> iterator = designComponent.getProperties().keySet().iterator(); iterator.hasNext();) {
				
				String key = iterator.next();
				IusCLProperty eventProperty = designComponent.getProperty(key);
				
				if (eventProperty.getType() == IusCLPropertyType.ptEvent.name()) {
					
					String oldEventValue = designComponent.getPropertyValue(eventProperty.getName());
					
					if (IusCLStrUtils.isNotNullNotEmpty(oldEventValue)) {
						
						String newEventValue = newPropertyValue + eventProperty.getName().substring(2);
						formJavaSource = IusCLStrUtils.replaceAllWholeWord(
								formJavaSource, oldEventValue, newEventValue);
						designComponent.setPropertyValue(eventProperty.getName(), newEventValue);
					}
				}
			}

			/* Set form code */
			setFormJavaSource(formJavaSource);
			
			/* Change the form name in application code */
			if (designComponent instanceof IusCLForm) {
				/* Get application code */
				String applicationJavaSource = getApplicationJavaSource();
				applicationJavaSource = IusCLStrUtils.replaceAllWholeWord(
						applicationJavaSource, oldPropertyValue, newPropertyValue);
				/* Set application code */
				setApplicationJavaSource(applicationJavaSource);
			}
			
			/* Non-visual components name */
			if (((designComponent instanceof IusCLControl) == false) && 
					(designComponent.getIsSubComponent() == false)) {
				
				int index = findNonVisualComponentIndex(designComponent);
				TableItem item = nonVisualComponentsTable.getItem(index);
				item.setText(0, designComponent.getName());
			}
			
			/* Resource names */
			String formsResFolder = IusCLApplication.getFormsResFolder(designForm.getClass()); 

			String formResFolder = formsResFolder + designForm.getClass().getSimpleName();
			String oldResPrefix = oldPropertyValue + "_";
			String newResPrefix = newPropertyValue + "_";
			
			IusCLFileSearchRec srcSearchRec = new IusCLFileSearchRec();
			IusCLStrings resFileNames = IusCLFileUtils.findFiles(formResFolder, srcSearchRec);

			/* Resource files */
			for (int index = 0; index < resFileNames.size(); index++) {
				
				String resFileNameWithPath = resFileNames.get(index);
				String resFileName = IusCLFileUtils.extractFileName(resFileNameWithPath);
				
				if (resFileName.startsWith(oldResPrefix) == true) {
					
					IusCLFileUtils.renameFile(resFileNameWithPath, resFileName.replace(oldResPrefix, newResPrefix));
				}
			}
			
			/* Broadcast */
			IusCLDesignIDE.dispatch(this, IusCLDesignIDEState.dsChange);
		};

		/* Form properties */
		if (designComponent instanceof IusCLForm) {

			/* Form size */
			if ((propertyName.equalsIgnoreCase("Width") || 
					propertyName.equalsIgnoreCase("Height") || 
					propertyName.equalsIgnoreCase("ClientWidth") || 
					propertyName.equalsIgnoreCase("ClientHeight"))) {
				
				designPageScrolledComposite.setMinSize(designForm.getWidth() + (2 * DESIGN_BORDER), 
						designForm.getHeight() + (2 * DESIGN_BORDER));
				borderCaptureComposite.setSize(designForm.getWidth() + 2 * DESIGN_BORDER, designForm.getHeight() + 2 * DESIGN_BORDER);
				borderCaptureComposite.setBackgroundImage(createBorderCapture());
				formReplacementComposite.setSize(designForm.getClientWidth(), designForm.getClientHeight());
				
				designPageScrolledComposite.layout();
				designPageComposite.layout();
	
	//			IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsChange);
			}

			/* Grid */
			if ((propertyName.equalsIgnoreCase("GridX") || propertyName.equalsIgnoreCase("GridY"))) {
				
				gridX = designForm.getGridX();
				gridY = designForm.getGridY();
			}

			/* Main menu */
			if (propertyName.equalsIgnoreCase("Menu")) {
				
				borderCaptureComposite.setBackgroundImage(createBorderCapture());

				formReplacementComposite.setSize(designForm.getClientWidth(), designForm.getClientHeight());
				
				designPageScrolledComposite.layout();
				designPageComposite.layout();
			}
	
			/* Form border */
			if (propertyName.equalsIgnoreCase("Caption") || 
					propertyName.equalsIgnoreCase("Icon") ||
					(propertyName.indexOf("BorderIcons") == 0)) {

				borderCaptureComposite.setBackgroundImage(createBorderCapture());
			}

			if (propertyName.equalsIgnoreCase("BorderStyle")) {

				borderCaptureComposite.setBackgroundImage(createBorderCapture());
				formReplacementComposite.setSize(designForm.getClientWidth(), designForm.getClientHeight());
				moveFormControlsToDesignReplacement();
				
				designPageScrolledComposite.layout();
				designPageComposite.layout();
			}

			if (propertyName.equalsIgnoreCase("FormStyle")) {

				moveFormControlsToDesignReplacement();
				
				designPageScrolledComposite.layout();
				designPageComposite.layout();
			}

			/* Form color */
			if ((propertyName.equalsIgnoreCase("Color")) || (propertyName.equalsIgnoreCase("ParentColor"))) {

				formReplacementComposite.setBackground(designForm.getColor().getAsSwtColor());
			}
		}

		/* Menu properties */
		if (designComponent instanceof IusCLMainMenu) {
			if (propertyName.equalsIgnoreCase("Images")) {
				borderCaptureComposite.setBackgroundImage(createBorderCapture());
			}
		}
		
		if (designComponent instanceof IusCLMenuItem) {

			IusCLMenuItem menuItem = (IusCLMenuItem)designComponent;
			createBorderCaptureMenuItem(menuItem);
		}

		/* For display name */
		if (designComponent.getIsSubComponent() == true) {
			
			IusCLDesignIDE.dispatch(this, IusCLDesignIDEState.dsChange);
		}
		if (designSelection.getDesignCollectionItem() != null) {
			
			IusCLDesignIDE.dispatch(this, IusCLDesignIDEState.dsChange);
		}

		/* Serialize */
		serializeChange();

		/* Refresh */
//		formReplacementComposite.layout();
		if (swtDesignControl != null) {
			
			if (swtDesignControl.isDisposed()) {
				/* Could happen for some SWT controls that needs to be recreated to apply some properties  */
				swtDesignControl = designControl.getSwtControl();
				removeAllAddedListeners(swtDesignControl);
				swtDesignControl.addMouseListener(designComponentMouseListener);
				swtDesignControl.addMouseMoveListener(designComponentMouseMoveListener);
				swtDesignControl.addListener(SWT.MenuDetect, contextMenuListener);
			}
			
			if (designComponent instanceof IusCLForm) {
				
				swtDesignControl.redraw();
				putDesignGrid(swtDesignControl);
			}
			else {
				
				disposeThumbs();
				swtDesignControl.redraw();
				/* Put again the background grid */
				putDesignGrid(swtDesignControl);
				showThumbs(swtDesignControl);
			}
		}
	}

	/* **************************************************************************************************** */
	private void loadDesignForm() {
		
		/* IusCLfm */
		IFile iusclfmFile = (IFile)this.getEditorInput().getAdapter(IFile.class);
		this.setPartName(iusclfmFile.getName().replaceAll("." + iusclfmFile.getFileExtension(), ""));

		IProject iProject = (IProject)iusclfmFile.getProject();
		IJavaProject iJavaProject = JavaCore.create(iProject);

		String iusclfmFileFullPath = iusclfmFile.getFullPath().toString();
		String javaFileFullPath = iusclfmFileFullPath.replaceAll(".iusclfm", ".java");

		IFile javaFile = (IFile)iusclfmFile.getWorkspace().getRoot().findMember(javaFileFullPath);
		IPath javaFileIPath = javaFile.getFullPath();
		
		String classesFolder = null;
		String canonicalClassName = null;
		
		try {
			
			for (int index = 0; index < iJavaProject.getAllPackageFragmentRoots().length; index ++) {
				
				IPath packageIPath = iJavaProject.getAllPackageFragmentRoots()[index].getPath();
				if (packageIPath.isPrefixOf(javaFileIPath)) {
					
					canonicalClassName = javaFileIPath.toString().replaceAll(packageIPath.toString(), ""); 
					canonicalClassName = canonicalClassName.replace("/", ".").replace(".java", "").replaceFirst(".", "");
				}
			}

			/* Find classes folder */
			URI uri = iProject.findMember(iJavaProject.getOutputLocation().removeFirstSegments(1)).getRawLocationURI();
			classesFolder = (new java.io.File(uri)).getAbsolutePath();

			/* Form files */
			String fmFile = iusclfmFile.getLocation().toOSString();
			IusCLApplication.putFMFile(canonicalClassName, fmFile);
			
			IusCLFormClassLoader formClassLoader = new IusCLFormClassLoader();
			formClassLoader.setParentClassLoader(this.getClass().getClassLoader());
			formClassLoader.setClassesFolder(classesFolder);
			Class<?> formClass = formClassLoader.loadClass(canonicalClassName);
			
			IusCLApplication.setPutNextCreatedFormInDesignMode(true);
			designForm = (IusCLForm)IusCLObjUtils.invokeConstructor(formClass);

			designForm.getSwtShell().redraw();
			
			swtDesignControl = formReplacementComposite;
			designComponent = designForm;
			designSelection = new IusCLDesignSelection(designComponent, null, null);
		}
		catch (Exception exception) {
			
			IusCLDesignException.error("Load design form exception", exception);
		}
	}

	/* **************************************************************************************************** */
	public IusCLFormDesignEditor() {
		super();
		
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/* **************************************************************************************************** */
	private void createPageFormDesigner() {

		/* Capture shell */
		Image borderCapture = createBorderCapture();

		/* Outer scroll */
		designPageScrolledComposite = new ScrolledComposite(getContainer(), 
				SWT.EMBEDDED | SWT.H_SCROLL | SWT.V_SCROLL);
		designPageScrolledComposite.setBackground(DESIGN_BACKGROUND_COLOR);
		designPageScrolledComposite.setExpandHorizontal(true);
		designPageScrolledComposite.setExpandVertical(true);
		designPageScrolledComposite.setMinSize(designForm.getWidth() + (2 * DESIGN_BORDER), 
				designForm.getHeight() + (2 * DESIGN_BORDER));

		/* Form holder */
		designPageComposite = new Composite(designPageScrolledComposite, SWT.NONE);
		designPageScrolledComposite.setContent(designPageComposite);
		designPageComposite.setBackground(DESIGN_BACKGROUND_COLOR);
		designPageComposite.setVisible(true);
		designPageComposite.setLayout(new Layout() {
			/* **************************************************************************************************** */
			@Override
			protected void layout(Composite layoutComposite, boolean flushCache) {

				int designPageWidth = layoutComposite.getBounds().width;
				int designPageHeight = layoutComposite.getBounds().height;

				for (int i = 0; i < layoutComposite.getChildren().length; i++) {
					
					Control swtControl = layoutComposite.getChildren()[i];

					int borderCaptureWidth = borderCaptureComposite.getBounds().width;
					int borderCaptureHeight = borderCaptureComposite.getBounds().height;

					if (swtControl == borderCaptureComposite) {
						
						borderCaptureComposite.setLocation(
								(designPageWidth - borderCaptureWidth) / 2, 
								(designPageHeight - borderCaptureHeight) / 2);
					}
					
					if (swtControl == formReplacementComposite) {

						formReplacementComposite.setLocation(
								((designPageWidth - borderCaptureWidth - 
										IusCLScreen.getFormCompensateSize().get(designForm.getBorderStyle()).getWidth()) / 2) + 
								DESIGN_BORDER + IusCLScreen.getFormMargins().get(designForm.getBorderStyle()).getLeft(),
								
									(
										(
											designPageHeight - borderCaptureHeight - 
											IusCLScreen.getFormCompensateSize().get(designForm.getBorderStyle()).getHeight()
										) / 2
									) + 
									DESIGN_BORDER + 
									IusCLScreen.getFormMargins().get(designForm.getBorderStyle()).getTop() +
									menuBarHeight
								);
					}
				}
			}
			/* **************************************************************************************************** */
			@Override
			protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
				return null;
			}
		});

		/* Shell replacement */
		formReplacementComposite = new Composite(designPageComposite, SWT.NONE);
		formReplacementComposite.setSize(designForm.getClientWidth(), designForm.getClientHeight());
		formReplacementComposite.setBackground(designForm.getColor().getAsSwtColor());
		formReplacementComposite.setData(designForm.getSwtShell().getData());
		formReplacementComposite.addMouseListener(designComponentMouseListener);
		putDesignGrid(formReplacementComposite);
		formReplacementComposite.addListener(SWT.Resize, designComponentGridResizeListener);
		
		/* Move controls from shell to design replacement */
		moveFormControlsToDesignReplacement();
		formReplacementComposite.setVisible(true);
		
		swtDesignControl = formReplacementComposite;

		/* Border capture */
		borderCaptureComposite = new Composite(designPageComposite, SWT.NONE);
		borderCaptureComposite.setSize(designForm.getWidth() + 2 * DESIGN_BORDER, designForm.getHeight() + 2 * DESIGN_BORDER);
		borderCaptureComposite.setBackground(DESIGN_BACKGROUND_COLOR);
		borderCaptureComposite.setForeground(THUMB_COLOR);
		borderCaptureComposite.setBackgroundImage(borderCapture);
		
		borderCaptureComposite.addMouseListener(new MouseListener() {
			
			/* **************************************************************************************************** */
			@Override
			public void mouseUp(MouseEvent mouseEvent) {
				/*  */
			}
			
			/* **************************************************************************************************** */
			@Override
			public void mouseDown(MouseEvent mouseEvent) {
				
				int borderCaptureWidth = borderCaptureComposite.getBounds().width;
				int borderCaptureTop = borderCaptureComposite.getBounds().y;

				int formReplacementWidth = formReplacementComposite.getBounds().width;
				int formReplacementTop = formReplacementComposite.getBounds().y;

				int lateralBorder = (borderCaptureWidth - formReplacementWidth) / 2;
				
				if ((mouseEvent.x > lateralBorder) && 
						(mouseEvent.x < borderCaptureWidth - lateralBorder) &&
						(mouseEvent.y > formReplacementTop - borderCaptureTop - menuBarHeight) &&
						(mouseEvent.y < formReplacementTop - borderCaptureTop)) {
					
					setDesignComponent(designForm.getMenu());
					IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsSelection);
				}
			}
			
			/* **************************************************************************************************** */
			@Override
			public void mouseDoubleClick(MouseEvent mouseEvent) {
				/*  */
			}
		});

		/* Context Menu */
		contextMenu = new Menu(designForm.getSwtShell(), SWT.POP_UP);
		MenuItem itemEdit = new MenuItem(contextMenu, SWT.PUSH);
	    itemEdit.setText("Edit");
	    itemEdit.setEnabled(false);

		new MenuItem(contextMenu, SWT.SEPARATOR);

	    MenuItem itemRevert = new MenuItem(contextMenu, SWT.PUSH);
	    itemRevert.setText("Revert to default (to inherited)");
	    itemRevert.setEnabled(false);
	    
		contextMenu.setData(designForm.getSwtShell());
		
		int index = addPage(designPageScrolledComposite);
		setPageText(index, "Design");
	}
	
	/* **************************************************************************************************** */
	private void moveFormControlsToDesignReplacement() {

		/* Move controls from shell to design replacement */
		for (int index = 0; index < designForm.getControls().size(); index++) {
			
			IusCLControl control = designForm.getControls().get(index);
			Control swtControl = control.getSwtControl();
			swtControl.setParent(formReplacementComposite);
			swtControl.moveAbove(null);
			makeChildDesignListeners(control);
		}
	}
	
	/* **************************************************************************************************** */
	private void createPageNonVisualComponents() {

		nonVisualComponentsTable = new Table(getContainer(), SWT.FULL_SELECTION);
		nonVisualComponentsTable.setHeaderVisible(true);
		
		nonVisualComponentsTable.addListener(SWT.MenuDetect, contextMenuListener);
		
		nonVisualComponentsTable.addListener(SWT.Selection, new Listener() {
			/* **************************************************************************************************** */
			@Override
			public void handleEvent(Event event) {
				
				TableItem tableItem = (TableItem)event.item;
				IusCLComponent itemComponent = (IusCLComponent)tableItem.getData();
				
				if (designComponent != itemComponent) {
					
					setDesignComponent(itemComponent);
					IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsSelection);
				}
			}
		});

		nonVisualComponentsTable.addMouseListener(new MouseListener() {
			/* **************************************************************************************************** */
			@Override
			public void mouseUp(MouseEvent mouseEvent) {
				/*  */
			}
			/* **************************************************************************************************** */
			@Override
			public void mouseDown(MouseEvent mouseEvent) {
				/*  */
			}
			/* **************************************************************************************************** */
			@Override
			public void mouseDoubleClick(MouseEvent mouseEvent) {
				
				invokeEditVerb();
			}
		});
		
		/* Columns */
		TableColumn nameColumn = new TableColumn(nonVisualComponentsTable, SWT.NONE);
		nameColumn.setText("Name");
		nameColumn.setWidth(200);
		nameColumn.setImage(IusCLDesignIDE.loadImageFromResource("IusCLObjectTreeViewComponent.gif"));

		TableColumn typeColumn = new TableColumn(nonVisualComponentsTable, SWT.NONE);
		typeColumn.setText("Type");
		typeColumn.setWidth(400);
		
		/* Display non-visual components */
		fillNonVisualComponentsTable();
		
		int index = addPage(nonVisualComponentsTable);
		setPageText(index, "Non-Visual Components");
	}

	/* **************************************************************************************************** */
	private void fillNonVisualComponentsTable() {
		
		nonVisualComponentsTable.removeAll();
		
		for (int index = 0; index < designForm.getComponents().size(); index++) {
			
			IusCLComponent component = designForm.getComponents().get(index);
			
			if ( (!(component instanceof IusCLControl)) && (!(component.getIsSubComponent()))) {
				
				addNonVisualComponent(component);
			}
		}
	}

	/* **************************************************************************************************** */
	private void makeChildDesignListeners(IusCLControl control) {
		
		Control swtControl = control.getSwtControl();
		if (swtControl != null) {
			
			removeAllAddedListeners(swtControl);
			swtControl.addMouseListener(designComponentMouseListener);
		}

		if (control instanceof IusCLParentControl ) {
			
			IusCLParentControl parentControl = (IusCLParentControl)control;
			
			for (int index = 0; index < parentControl.getControls().size(); index++) {
				
				IusCLControl childControl = parentControl.getControls().get(index);
				makeChildDesignListeners(childControl);
			}
		}
	}
	
	/* **************************************************************************************************** */
	private void createPageFormViewAsText() {
		
		try {

			formIusCLFMXMLEditor = new StructuredTextEditor();
			int index = addPage(formIusCLFMXMLEditor, getEditorInput());
			setPageText(index, "View As Text");
			//setPageText(index, getEditorInput().getName());
		} 
		catch (PartInitException partInitException) {
			
			IusCLDesignException.error("Error creating nested text editor", partInitException);
		}
	}

	/* **************************************************************************************************** */
	protected void createPages() {
		
		loadDesignForm();
		
		gridX = designForm.getGridX();
		gridY = designForm.getGridY();
		
		createPageFormDesigner();
		createPageNonVisualComponents();
		createPageFormViewAsText();
		
		designForm.getSwtShell().setVisible(false);

		formIusCLFMXMLEditor.getTextViewer().setEditable(false);
		//IusCLDesignIDE.dispatch(this, designForm, designComponent);
	}

	/* **************************************************************************************************** */
	public void setFocus() {
		
		IusCLDesignIDE.dispatch(this, IusCLDesignIDEState.dsActivate);
		invokeContributor();
	}

	/* **************************************************************************************************** */
	public void dispose() {
		
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		IusCLDesignIDE.dispatch(this, IusCLDesignIDEState.dsDispose);
		super.dispose();
	}

	/* **************************************************************************************************** */
	public void doSave(IProgressMonitor monitor) {
		
		getEditor(VIEWASTEXT_PAGE).doSave(monitor);
		formIusCLFMXMLEditor.getTextViewer().setEditable(false);
	}

	/* **************************************************************************************************** */
	public void doSaveAs() {
		
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	/* **************************************************************************************************** */
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		
		if (!(editorInput instanceof IFileEditorInput)) {
			
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		}
		super.init(site, editorInput);
	}
	
	/* **************************************************************************************************** */
	public boolean isSaveAsAllowed() {
		
		return true;
	}

	/* **************************************************************************************************** */
	protected void pageChange(int newPageIndex) {
		
		super.pageChange(newPageIndex);
		if (newPageIndex == VIEWASTEXT_PAGE) {
			//sortWords();
		}
	}

	/* **************************************************************************************************** */
	public void resourceChanged(final IResourceChangeEvent event){
		
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			
			Display.getDefault().asyncExec(new Runnable(){
				/* **************************************************************************************************** */
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i<pages.length; i++){
						
						if(((FileEditorInput)formIusCLFMXMLEditor.getEditorInput()).getFile().getProject().equals(event.getResource())){
							
							IEditorPart editorPart = pages[i].findEditor(formIusCLFMXMLEditor.getEditorInput());
							pages[i].closeEditor(editorPart,true);
						}
					}
				}            
			});
		}
	}
	
	/* **************************************************************************************************** */
	public IusCLForm getDesignForm() {
		
		return designForm;
	}
	
	public IusCLComponent getDesignComponent() {
		
		return designComponent;
	}

	/* **************************************************************************************************** */
	public void setDesignComponent(IusCLComponent designComponent) {
		
		setDesignSelection(new IusCLDesignSelection(designComponent, null, null));
	}

	/* **************************************************************************************************** */
	public void setDesignSelection(IusCLDesignSelection designSelection) {
		
		IusCLComponent designComponent = designSelection.getDesignComponent();
		/* Old design component */
		if (this.designComponent instanceof IusCLControl) {
			
			designControl = (IusCLControl)this.designComponent;
			if (designControl instanceof IusCLForm) {
				
				swtDesignControl = formReplacementComposite;
			}
			else {
				
				swtDesignControl = designControl.getSwtControl();
				disposeThumbs();
			}
			
			if (swtDesignControl != null) {
				/* Remove grid */
				Control swtParentControl = findSwtDesignControlParent();
	
				while (swtParentControl != designPageComposite) {
					
					removeDesignGrid(swtParentControl);
					swtParentControl.removeListener(SWT.Resize, designComponentGridResizeListener);
					swtParentControl = swtParentControl.getParent();
				}
				
				/* Remove thumbs */
				swtDesignControl.removeMouseMoveListener(designComponentMouseMoveListener);
				swtDesignControl.redraw();
				
				/* Remove context menu */
				swtDesignControl.removeListener(SWT.MenuDetect, contextMenuListener);
			}
		}
		else {
			
			nonVisualComponentsTable.setSelection(-1);
		}

		/* Change design component */
		this.designComponent = designComponent;
		
		/* New design component */
		if (this.designComponent instanceof IusCLControl) {
			
			changePage(DESIGN_PAGE);
			designControl = (IusCLControl)this.designComponent;
			if (designControl instanceof IusCLForm) {
				
				swtDesignControl = formReplacementComposite;
			}
			else {
				swtDesignControl = designControl.getSwtControl();
				if (swtDesignControl != null) {
					
					if (swtDesignControl.getParent() instanceof Shell) {
						
						swtDesignControl.setParent(formReplacementComposite);
						swtDesignControl.moveAbove(null);
					}

					showThumbs(swtDesignControl);
				}
				else {
					
					showThumbs(findSwtDesignControlParent());
				}
				
				IusCLControl parent = designControl;
				
				while (parent != null) {
					
					if (parent.getIsSubComponent()) {
						
						parent.getParent().selectSubComponent(parent);
						break;
					}
					parent = parent.getParent();
				}
			}
			
			if (swtDesignControl != null) {
				/* Put grid */
				Control swtParentControl = findSwtDesignControlParent();
	
				while (swtParentControl != designPageComposite) {
					
					putDesignGrid(swtParentControl);
					swtParentControl.addListener(SWT.Resize, designComponentGridResizeListener);
					swtParentControl = swtParentControl.getParent();
				}
				
				/* Put thumbs */
				swtDesignControl.addMouseMoveListener(designComponentMouseMoveListener);
				swtDesignControl.redraw();
				
				/* Put context menu */
				swtDesignControl.addListener(SWT.MenuDetect, contextMenuListener);
			}
		}
		else {
			
			designControl = null;
			swtDesignControl = null;
			changePage(NONCONTROLS_PAGE);
			nonVisualComponentsTable.setSelection(findNonVisualComponentIndex(designComponent));
		}
		
		this.designSelection = designSelection;
		
		invokeContributor();
	}

	public IusCLDesignSelection getDesignSelection() {
		
		return designSelection;
	}

	/* **************************************************************************************************** */
	private Control findSwtDesignControlParent() {

		if (designControl instanceof IusCLContainerControl) {
			
			return swtDesignControl;
		}

		IusCLParentControl parentControl = designControl.getParent();
		
		if (parentControl instanceof IusCLForm) {
			
			return formReplacementComposite;
		}
		else {
			
			return parentControl.getSwtControl();
		}
	}

	/* **************************************************************************************************** */
	private void removeAllAddedListeners(Control swtControl) {
		
//		final int[] eventTypes = { SWT.KeyDown, SWT.KeyUp, SWT.MouseDown, SWT.MouseUp, SWT.MouseMove,
//				SWT.MouseEnter, SWT.MouseHover, SWT.MouseExit, SWT.MouseDoubleClick, SWT.Paint, SWT.Move, 
//				SWT.Resize, SWT.Dispose, SWT.Selection, SWT.DefaultSelection, SWT.FocusIn, SWT.FocusOut,
//				SWT.Expand, SWT.Collapse, SWT.Iconify, SWT.Deiconify, SWT.Close, SWT.Show, SWT.Hide,
//				SWT.Modify, SWT.Verify, SWT.Activate, SWT.Deactivate, SWT.Help, SWT.DragDetect, SWT.MenuDetect,
//				SWT.Arm, SWT.Traverse, SWT.HardKeyDown, SWT.HardKeyUp };

//		final int[] eventTypes = { SWT.KeyDown, SWT.KeyUp, SWT.MouseDown, SWT.MouseUp, SWT.MouseMove,
//				SWT.MouseEnter, SWT.MouseHover, SWT.MouseExit, SWT.MouseDoubleClick, SWT.Move, 
//				SWT.Resize, SWT.Dispose, SWT.Selection, SWT.DefaultSelection, SWT.FocusIn, SWT.FocusOut,
//				SWT.Expand, SWT.Collapse, SWT.Iconify, SWT.Deiconify, SWT.Close, SWT.Show, SWT.Hide,
//				SWT.Modify, SWT.Verify, SWT.Activate, SWT.Deactivate, SWT.Help, SWT.DragDetect, SWT.MenuDetect,
//				SWT.Arm, SWT.Traverse, SWT.HardKeyDown, SWT.HardKeyUp };

		final int[] eventTypes = { SWT.KeyDown, SWT.KeyUp, SWT.MouseDown, SWT.MouseUp, SWT.MouseMove,
				SWT.MouseEnter, SWT.MouseHover, SWT.MouseExit, SWT.MouseDoubleClick, SWT.Move, 
				SWT.Dispose, SWT.Selection, SWT.DefaultSelection, SWT.FocusIn, SWT.FocusOut,
				SWT.Expand, SWT.Collapse, SWT.Iconify, SWT.Deiconify, SWT.Close, SWT.Show, SWT.Hide,
				SWT.Modify, SWT.Verify, SWT.Activate, SWT.Deactivate, SWT.Help, SWT.DragDetect, SWT.MenuDetect,
				SWT.Arm, SWT.Traverse, SWT.HardKeyDown, SWT.HardKeyUp };

		for (int i = 0; i < eventTypes.length; i++) {
			
			int eventType = eventTypes[i];
			
			while (swtControl.getListeners(eventType).length > 0) {
				
				Listener listener = swtControl.getListeners(eventType)[0];
				swtControl.removeListener(eventType, listener);
			}
		}
	}

	/* **************************************************************************************************** */
	private void putDesignGrid(Control swtControl) {

		IusCLControl gridControl = (IusCLControl)(swtControl.getData());
		if (gridControl instanceof IusCLContainerControl) {
			
			IusCLContainerControl gridContainerControl = (IusCLContainerControl)gridControl;
			if (gridContainerControl.getSwtComposite() != null) {
				
				int gridMargin = gridContainerControl.getContainerBorderWidth();
//				putDesignGrid(swtControl, gridMargin, gridMargin, GRID_COLOR);
				if (gridContainerControl instanceof IusCLForm) {
					
					putDesignGrid(swtControl, gridMargin, gridMargin, GRID_COLOR);
				}
				else {
					
					putDesignGrid(gridContainerControl.getSwtComposite(), gridMargin, gridMargin, GRID_COLOR);
				}
			}
		}
	}

	/* **************************************************************************************************** */
	private void putDesignGrid(Control swtControl, int marginX, int marginY, Color gridColor) {
		
		Rectangle clientArea = ((Composite)swtControl).getClientArea();

		int imageLeft = clientArea.x;
		int imageTop = clientArea.y;
		int imageWidth = imageLeft + clientArea.width;
		int imageHeight = imageTop + clientArea.height;

		if (imageWidth <= 0) {
			
			imageWidth = 1;
		}
		if (imageHeight <= 0) {
			
			imageHeight = 1;
		}
		
		Image backgroundImage = new Image(Display.getCurrent(), imageWidth, imageHeight);
		GC gridGc = new GC(backgroundImage);
		gridGc.setBackground(swtControl.getBackground());
		
		gridGc.fillRectangle(0, 0, imageWidth, imageHeight);
		
		gridGc.setForeground(gridColor);

		for (int i = 0; i <= (imageWidth - 2 * marginX - imageLeft)/gridX; i++) {
			
			for (int j = 0; j <= (imageHeight - 2 * marginY - imageTop)/gridY; j++) {
				
				int x = imageLeft + marginX + i * gridX;
				int y = imageTop + marginY + j * gridY;
				if ((x < imageWidth - marginX) && (y < imageHeight - marginY)) {
					
					gridGc.drawPoint(x, y);
				}
			}
		}
		
		gridGc.drawRectangle(imageLeft + marginX - 1, imageTop + marginY - 1, 
				imageWidth - 2 * (marginX - 1) - 1 - imageLeft, imageHeight - 2 * (marginY - 1) - 1 - imageTop);
		
		swtControl.setBackgroundImage(backgroundImage);
		swtControl.redraw();
	}
	
	/* **************************************************************************************************** */
	private void removeDesignGrid(Control swtControl) {

		if (swtControl.getBackgroundImage() != null) {
			
			Image backgroundImage = swtControl.getBackgroundImage();
			swtControl.setBackgroundImage(null);
			backgroundImage.dispose();
		}
	}

	/* **************************************************************************************************** */
	private void showThumbs(Control swtControl) {

		if (!(designControl.getParent() instanceof IusCLContainerControl)) {
			
			return;
		}
		
		int left = swtControl.getLocation().x;
		int top = swtControl.getLocation().y;
		int width = swtControl.getSize().x;
		int height = swtControl.getSize().y;
		Composite parent = swtControl.getParent();

		thumbN = createThumb(parent, IusCLThumb.N, SWT.CURSOR_SIZEN);
		thumbN.setLocation(left + width / 2 - THUMB_SIZE / 2, top - THUMB_SIZE / 2);
		
		thumbS = createThumb(parent, IusCLThumb.S, SWT.CURSOR_SIZES);
		thumbS.setLocation(left + width / 2 - THUMB_SIZE / 2, top + height - THUMB_SIZE / 2 - 1);

		thumbE = createThumb(parent, IusCLThumb.E, SWT.CURSOR_SIZEE);
		thumbE.setLocation(left + width - THUMB_SIZE / 2 - 1, top + height / 2 - THUMB_SIZE / 2);

		thumbW = createThumb(parent, IusCLThumb.W, SWT.CURSOR_SIZEW);
		thumbW.setLocation(left - THUMB_SIZE / 2, top + height / 2 - THUMB_SIZE / 2);

		thumbNE = createThumb(parent, IusCLThumb.NE, SWT.CURSOR_SIZENE);
		thumbNE.setLocation(left + width - THUMB_SIZE / 2 - 1, top - THUMB_SIZE / 2);

		thumbSE = createThumb(parent, IusCLThumb.SE, SWT.CURSOR_SIZESE);
		thumbSE.setLocation(left + width - THUMB_SIZE / 2 - 1, top + height - THUMB_SIZE / 2 - 1);

		thumbSW = createThumb(parent, IusCLThumb.SW, SWT.CURSOR_SIZESW);
		thumbSW.setLocation(left - THUMB_SIZE / 2, top + height - THUMB_SIZE / 2 - 1);

		thumbNW = createThumb(parent, IusCLThumb.NW, SWT.CURSOR_SIZENW);
		thumbNW.setLocation(left - THUMB_SIZE / 2, top - THUMB_SIZE / 2);
	}

	/* **************************************************************************************************** */
	private Composite createThumb(Composite parent, IusCLThumb thumb, int thumbCursor) {
		
		Composite swtThumb = new Composite(parent, SWT.NONE);
		swtThumb.setSize(THUMB_SIZE, THUMB_SIZE);
		swtThumb.setBackground(THUMB_COLOR);
		swtThumb.setCursor(new Cursor(formReplacementComposite.getDisplay(), thumbCursor));
		swtThumb.moveAbove(null);
		
		final IusCLThumb thumbl = thumb;
		
		swtThumb.addMouseMoveListener(new MouseMoveListener() {
			
			/* **************************************************************************************************** */
			@Override
			public void mouseMove(MouseEvent mouseEvent) {
				
				if (isDraggingThumb == true) {
					
					int controlLeft = swtDesignControl.getLocation().x;
					int controlTop = swtDesignControl.getLocation().y;
					int controlWidth = swtDesignControl.getSize().x;
					int controlHeight = swtDesignControl.getSize().y;

					int trackerLeft = controlLeft;
					int trackerTop = controlTop;
					int trackerWidth = controlWidth;
					int trackerHeight = controlHeight;

					int mouseLeft = mouseEvent.x;
					int mouseTop = mouseEvent.y;
					
					if (initialMouseLeft == 0) {
						
						initialMouseLeft = mouseLeft;
					}
					if (initialMouseTop == 0) {
						
						initialMouseTop = mouseTop;
					}
					
					switch (dragThumb) {
					case N:
						trackerTop = findGridY(controlTop + mouseTop);
						trackerHeight = (controlTop - trackerTop) + controlHeight;
						break;
					case S:
						trackerHeight = findGridY(controlTop + controlHeight + mouseTop) - controlTop + 1;
						break;
					case E:
						trackerWidth = findGridX(controlLeft + controlWidth + mouseLeft) - controlLeft + 1;
						break;
					case W:
						trackerLeft = findGridX(controlLeft + mouseLeft);
						trackerWidth = (controlLeft - trackerLeft) + controlWidth;
						break;
					case NW:
						trackerLeft = findGridX(controlLeft + mouseLeft);
						trackerTop = findGridY(controlTop + mouseTop);
						trackerWidth = (controlLeft - trackerLeft) + controlWidth;
						trackerHeight = (controlTop - trackerTop) + controlHeight;
						break;
					case NE:
						trackerTop = findGridY(controlTop + mouseTop);
						trackerWidth = findGridX(controlLeft + controlWidth + mouseLeft) - controlLeft + 1;
						trackerHeight = (controlTop - trackerTop) + controlHeight;
						break;
					case SE:
						trackerWidth = findGridX(controlLeft + controlWidth + mouseLeft) - controlLeft + 1;
						trackerHeight = findGridY(controlTop + controlHeight + mouseTop) - controlTop + 1;
						break;
					case SW:
						trackerLeft = findGridX(controlLeft + mouseLeft);
						trackerWidth = (controlLeft - trackerLeft) + controlWidth;
						trackerHeight = findGridY(controlTop + controlHeight + mouseTop) - controlTop + 1;
						break;
					case C:
						trackerLeft = findGridX(controlLeft + mouseLeft) + controlLeft - 
							(findGridX(controlLeft) + gridX * (initialMouseLeft / gridX));
						trackerTop = findGridY(controlTop + mouseTop) + controlTop - 
							(findGridY(controlTop) + gridY * (initialMouseTop / gridY));
						trackerWidth = controlWidth;
						trackerHeight = controlHeight;
						break;
					default:
						break;
					}
	
					if (trackerWidth < THUMB_SIZE) {
						
						trackerWidth = THUMB_SIZE;
					}
					if (trackerHeight < THUMB_SIZE) {
						
						trackerHeight = THUMB_SIZE;
					}
					
					removeDesignGrid(designComponentTracker);
					
					if (designComponentTracker.getRegion() != null) {
						
						designComponentTracker.getRegion().dispose();
						designComponentTracker.setRegion(null);
					}
					Region trackerRegion = new Region();
					trackerRegion.add(0, trackerHeight - TRACKER_BORDER, trackerWidth, trackerHeight);
					trackerRegion.add(trackerWidth - TRACKER_BORDER, 0, trackerWidth, trackerHeight);
					trackerRegion.add(0, 0, trackerWidth, TRACKER_BORDER);
					trackerRegion.add(0, 0, TRACKER_BORDER, trackerHeight);
					designComponentTracker.setRegion(trackerRegion);
					
					designComponentTracker.setBounds(trackerLeft, trackerTop, trackerWidth, trackerHeight);
					
					int gridMarginX = findGridX(trackerLeft) - (trackerLeft + gridX);
					int gridMarginY = findGridY(trackerTop) - (trackerTop + gridY);
					putDesignGrid(designComponentTracker, gridMarginX, gridMarginY, TRACKER_GRID_COLOR);
					designComponentTracker.redraw();
	
					invokeContributor(trackerLeft, trackerTop, trackerWidth, trackerHeight);
				}
			}
		});
		
		swtThumb.addMouseListener(new MouseListener() {

			/* **************************************************************************************************** */
			@Override
			public void mouseUp(MouseEvent mouseEvent) {
				
				if (isDraggingThumb == true) {
				
					int trackerLeft = designComponentTracker.getLocation().x;
					int trackerTop = designComponentTracker.getLocation().y;
					int trackerWidth = designComponentTracker.getSize().x;
					int trackerHeight = designComponentTracker.getSize().y;
	
					if (!((trackerLeft == 0) && (trackerTop == 0) && (trackerWidth == 0) && (trackerHeight == 0))) {
						
						IusCLContainerControl containerControl = (IusCLContainerControl)designComponentTracker.getParent().getData();
						
						designControl.setBounds(
								trackerLeft - containerControl.getContainerClientLeft(), 
								trackerTop - containerControl.getContainerClientTop(), 
								trackerWidth, trackerHeight);
					}
					
					designControl.doParentAlignControls();
					
					dragThumb = null;
					isDraggingThumb = false;
					removeDesignGrid(designComponentTracker);
					designComponentTracker.dispose();
					
					disposeThumbs();
					showThumbs(swtDesignControl);
					
					IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsChange);
					serializeChange();
				}
			}

			/* **************************************************************************************************** */
			@Override
			public void mouseDown(MouseEvent mouseEvent) {

				if (SWT.getPlatform().equalsIgnoreCase("win32")) {

					thumbN.setVisible(false);
					thumbS.setVisible(false);
					thumbE.setVisible(false);
					thumbW.setVisible(false);
					thumbNE.setVisible(false);
					thumbSE.setVisible(false);
					thumbSE.setVisible(false);
					thumbSW.setVisible(false);
					thumbNW.setVisible(false);
				}

				if ((mouseEvent.stateMask & SWT.CTRL) != 0) {
					
					dragThumb = IusCLThumb.C;
				}
				else {
					
					dragThumb = thumbl;
				}

				isDraggingThumb = true;
				
				initialMouseLeft = 0;
				initialMouseTop = 0;
				designComponentTracker = new Composite(swtDesignControl.getParent(), SWT.NONE);
				designComponentTracker.setBackground(TRACKER_COLOR);
				designComponentTracker.moveAbove(null);
			}

			/* **************************************************************************************************** */
			@Override
			public void mouseDoubleClick(MouseEvent mouseEvent) {
				/* Default? */
			}
		});
		
		return swtThumb;
	}
	
	/* **************************************************************************************************** */
	private void disposeThumbs() {
		
		if (thumbN == null) {
			
			return;
		}
		if (thumbN.isDisposed() == true) {
			
			return;
		}
		
		thumbN.dispose();
		thumbS.dispose();
		thumbE.dispose();
		thumbW.dispose();
		thumbNE.dispose();
		thumbSE.dispose();
		thumbSE.dispose();
		thumbSW.dispose();
		thumbNW.dispose();
	}

	/* **************************************************************************************************** */
	private Image createBorderCapture() {
		
		imageCapture = new Image(Display.getCurrent(), 
				designForm.getWidth() + 2 * DESIGN_BORDER, designForm.getHeight() + 2 * DESIGN_BORDER);
		
		IusCLForm whiteForm = new IusCLForm();
		whiteForm.setWidth(designForm.getWidth() + 2 * DESIGN_BORDER);
		whiteForm.setHeight(designForm.getHeight() + 2 * DESIGN_BORDER);
		whiteForm.setColor(IusCLColor.getStandardColor(IusCLStandardColors.clWhite));
		whiteForm.setFormStyle(IusCLFormStyle.fsStayOnTopDialog);
		whiteForm.setBorderStyle(IusCLFormBorderStyle.bsNone);
		whiteForm.setPosition(IusCLFormPosition.poScreenCenter);
		leftCapture = whiteForm.getLeft();
		topCapture = whiteForm.getTop();
		
		whiteForm.show();

		
		IusCLForm captureForm = new IusCLForm();
		captureForm.setBorderStyle(designForm.getBorderStyle());
		captureForm.setWidth(designForm.getWidth());
		captureForm.setHeight(designForm.getHeight());
		captureForm.setColor(IusCLColor.getStandardColor(IusCLStandardColors.clWhite));
		captureForm.setFormStyle(IusCLFormStyle.fsStayOnTopDialog);
		
		captureForm.setCaption(designForm.getCaption());
		captureForm.setIcon(designForm.getIcon());
		captureForm.setBorderIcons(designForm.getBorderIcons());
		captureForm.setPosition(IusCLFormPosition.poScreenCenter);
		
		/* Menu */
		IusCLMainMenu designMenu = designForm.getMenu();
		if (designMenu != null) {
			
			IusCLMainMenu captureMainMenu = new IusCLMainMenu(captureForm);
			
			captureMainMenu.setImages(designMenu.getImages());
			
			for (int index = 0; index < designMenu.getItemCount(); index++) {
				
				IusCLMenuItem designMenuItem = designMenu.getItem(index);
				
				IusCLMenuItem captureMenuItem = new IusCLMenuItem(captureForm);
				
				captureMenuItem.setParentMenu(captureMainMenu);
				
				captureMenuItem.setCaption(designMenuItem.getCaption());
				captureMenuItem.setImageIndex(designMenuItem.getImageIndex());
			}
			
			captureForm.setMenu(captureMainMenu);
			
			menuBarHeight = IusCLScreen.getMenuBarHeight();
		}
		else {
			
			menuBarHeight = 0;
		}
		
		/* Wait label */
		IusCLLabel waitLabel = new IusCLLabel(captureForm);
		waitLabel.setAutoSize(false);
		waitLabel.setParent(captureForm);
		waitLabel.setHeight(16);
		waitLabel.setCaption("Don't click, IusCL form capture... 2 seconds.");
		waitLabel.setAlignment(IusCLAlignment.taCenter);
		waitLabel.setAlign(IusCLAlign.alBottom);
		
		/* Splash */
		IusCLImage splashImage = new IusCLImage(captureForm);
		splashImage.setParent(captureForm);
		IusCLPicture splashPicture = new IusCLPicture();
		splashPicture.loadFromResource(this.getClass(), "/resources/images/IusCLSplash.png");
//		splashPicture.loadFromResource(this.getClass(), "/resources/images/IusCLSplash_white.png");
		splashImage.setPicture(splashPicture);
		splashImage.setProportional(true);
		splashImage.setCenter(true);
		splashImage.setAlign(IusCLAlign.alClient);
		
		/* OnActivate event */
		IusCLNotifyEvent onActivateEvent = new IusCLNotifyEvent();
		onActivateEvent.setEventDeclaringInstance(IusCLFormDesignEditor.this);
		onActivateEvent.setEventMethodName("captureFormActivate");

		captureForm.setOnActivate(onActivateEvent);
		
		
		captureForm.show();
		
		/*
		 * Delphi7 style, Windows ~~Java SWT based component library~~ and ~~Eclipse development plug-in~~
		 * 
		 * White
		 * 640 x 48
		 * 
		 * 0, 100, 152
		 * 640 x 16
		 * 
		 * Tahoma, 32, Bold, Stroke: none, Fill: black
		 * 
		 * 
		 * IusCL size 32 - 1; right -4
		 * 
		 * (0, 100, 152) 16
		 * 
		 * White bottom 4
		 * 
		 */

		whiteForm.release();
		captureForm.release();

		return imageCapture;
	}

	/* **************************************************************************************************** */
	public void captureFormActivate(IusCLObject sender) {
		
		try {
			
			Thread.sleep(2000);
		} 
		catch (InterruptedException interruptedException) {
			
			IusCLDesignException.error("Exception in capture sleep", interruptedException);
		}

		GC gc = new GC(Display.getCurrent());
		gc.copyArea(imageCapture, leftCapture, topCapture);
		gc.dispose();
	}

	/* **************************************************************************************************** */
	private ITextEditor getFormJavaSourceTextEditor() {

		if (formJavaSourceTextEditor == null) {
			
			IFile thisFmFile = (IFile)this.getEditorInput().getAdapter(IFile.class);
			String thisFmFileFullPath = thisFmFile.getFullPath().toString();
			String javaSourceFileFullPath = thisFmFileFullPath.replaceAll(".iusclfm", ".java");
			IFile javaSourceFile = (IFile)thisFmFile.getWorkspace().getRoot().findMember(javaSourceFileFullPath);
			IEditorDescriptor javaEditorDescriptor = PlatformUI.getWorkbench().
				getEditorRegistry().getDefaultEditor(javaSourceFile.getName());

			try {
				
				IWorkbenchPage iWorkbenchPage = getSite().getPage();
				IEditorPart iEditorPart = iWorkbenchPage.openEditor(
					new FileEditorInput(javaSourceFile), javaEditorDescriptor.getId());
				formJavaSourceTextEditor = (ITextEditor)iEditorPart;
			} 
			catch (PartInitException partInitException) {
				
				IusCLDesignException.error("Exception in getting Java text editor", partInitException);
			}
		}
		return formJavaSourceTextEditor;
	}

	/* **************************************************************************************************** */
	private ITextEditor getApplicationJavaSourceTextEditor() {

		if (applicationJavaSourceTextEditor == null) {
			
			IFile thisFmFile = (IFile)this.getEditorInput().getAdapter(IFile.class);
			
			IProject formProject = thisFmFile.getProject();
		    String formProjectName = formProject.getName();

		    String formName = designForm.getClass().getSimpleName();

			String thisFmFileFullPath = thisFmFile.getFullPath().toString();
			String javaSourceFileFullPath = thisFmFileFullPath.replaceAll(
					formName + ".iusclfm", formProjectName + ".java");
			IFile javaSourceFile = (IFile)thisFmFile.getWorkspace().getRoot().findMember(javaSourceFileFullPath);
			IEditorDescriptor javaEditorDescriptor = PlatformUI.getWorkbench().
				getEditorRegistry().getDefaultEditor(javaSourceFile.getName());

			try {
				
				IWorkbenchPage iWorkbenchPage = getSite().getPage();
				IEditorPart iEditorPart = iWorkbenchPage.openEditor(
					new FileEditorInput(javaSourceFile), javaEditorDescriptor.getId());
				applicationJavaSourceTextEditor = (ITextEditor)iEditorPart;
			} 
			catch (PartInitException partInitException) {
				
				IusCLDesignException.error("Exception in getting Java text editor", partInitException);
			}
		}
		return applicationJavaSourceTextEditor;
	}

	/* **************************************************************************************************** */
	public void deleteDesignComponent() {
		
		if (designComponent instanceof IusCLForm) {
			
			return;
		}
		
		IusCLComponent deleteComponent = designComponent;
		
		if (designComponent != null) {

			if (designComponent instanceof IusCLControl) {
				
				IusCLParentControl newParentControl = ((IusCLControl)designComponent).getParent();
				
				int newIndex = newParentControl.getControls().indexOf(designComponent);
				int newSize = newParentControl.getControls().size();
				if (newSize == 1) {
					/* Only */
					newIndex = -1;
				}
				else if (newSize == newIndex + 1) {
					/* Last */
					newIndex = newIndex - 1; 
				}
				else {
					/* Between */
					newIndex = newIndex + 1; 
				}
				
				if (newIndex > -1) {
					
					setDesignComponent(newParentControl.getControls().get(newIndex));
				}
				else {
					
					setDesignComponent(newParentControl);
				}
			}
			else {
				
				setDesignComponent(designForm);
			}
		}

		/* Java code */
		String javaSource =	getFormJavaSource();
		javaSource = deleteComponentDeclaration(javaSource, deleteComponent);
		setFormJavaSource(javaSource);
		
		/* Component FM */
		deleteComponent.free();
		serializeChange();
		
		if (deleteComponent instanceof IusCLControl) {
			
			formReplacementComposite.redraw();
		}
		else {
			if (deleteComponent.getIsSubComponent() == false) {
				
				nonVisualComponentsTable.remove(findNonVisualComponentIndex(deleteComponent));
			}
			if (deleteComponent instanceof IusCLMenuItem) {
				
				IusCLMenuItem menuItem = (IusCLMenuItem)deleteComponent;
				createBorderCaptureMenuItem(menuItem);
			}
		}

		IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsChange);

		/* Organize imports */
		
//		final ICommandService cmdService = (ICommandService)PlatformUI.getWorkbench().getService(ICommandService.class);
//		if (cmdService != null) {
//			final Command cmd = cmdService.getCommand(IJavaEditorActionDefinitionIds.ORGANIZE_IMPORTS);
//			final ExecutionEvent execEvt = new ExecutionEvent(cmd, Collections.EMPTY_MAP, 
//					getJavaTextEditor(), null);
//			Display.getDefault().syncExec(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    cmd.executeWithChecks(execEvt);
//	                } 
//	                catch (Exception exception) {
//	                    IusCLDesignException.error("Organize Imports failed", exception);
//	                }
//	            }
//			});
//		}
	}

	/* **************************************************************************************************** */
	private void createBorderCaptureMenuItem(IusCLMenuItem menuItem) {
		
		if (menuItem.getParentMenu() != null) {
			
			if (menuItem.getParentMenu() == designForm.getMenu()) {
				
				borderCaptureComposite.setBackgroundImage(createBorderCapture());
				
				if (designForm.getMenu().getItemCount() == 1) {
					/* First menu item */
					designForm.setMenu(designForm.getMenu());
					formReplacementComposite.setSize(designForm.getClientWidth(), designForm.getClientHeight());
					
					designPageScrolledComposite.layout();
					designPageComposite.layout();
				}
			}
		}
	}
	
	/* **************************************************************************************************** */
	private void addNonVisualComponent(IusCLComponent component) {
		
		TableItem item = new TableItem(nonVisualComponentsTable, SWT.NONE);
		item.setText(0, component.getName());
		item.setText(1, component.getClass().getCanonicalName());
		item.setImage(IusCLDesignIDE.loadDesignComponentImage(component));
		item.setData(component);
	}

	/* **************************************************************************************************** */
	private void changePage(int pageIndex) {
		
		if (this.getActivePage() != pageIndex) {
			
			this.setActivePage(pageIndex);
		}
	}

	/* **************************************************************************************************** */
	private int findNonVisualComponentIndex(IusCLComponent component) {
		
		for (int index = 0; index < nonVisualComponentsTable.getItems().length; index++) {
			
			if (nonVisualComponentsTable.getItem(index).getData().equals(component)) {
				
				return index;
			}
		}
		return -1;
	}

	/* **************************************************************************************************** */
	public void addDesignNonVisualComponent(String componentCanonicalClassName, IusCLComponent parentComponent) {
		
		addDesignComponent(componentCanonicalClassName, null, null, parentComponent);
	}

	/* **************************************************************************************************** */
	public void addDesignControl(String controlCanonicalClassName, IusCLControl parentControl) {
		
		addDesignComponent(controlCanonicalClassName, parentControl, null, null);
	}

	/* **************************************************************************************************** */
	public void addDesignControl(String controlCanonicalClassName, IusCLControl mouseDownControl, IusCLPoint pos) {
		
		addDesignComponent(controlCanonicalClassName, mouseDownControl, pos, null);
	}

	/* **************************************************************************************************** */
	private void addDesignPaletteComponent(String componentCanonicalClassName, IusCLControl mouseDownControl, IusCLPoint pos) {
		
		addDesignComponent(componentCanonicalClassName, mouseDownControl, pos, designForm);
	}

	/* **************************************************************************************************** */
	private void addDesignComponent(String componentCanonicalClassName, IusCLControl mouseDownControl, 
			IusCLPoint pos, IusCLComponent parentComponent) {

		String componentSimpleClassName = componentCanonicalClassName.substring(componentCanonicalClassName.lastIndexOf(".") + 1);

		/* Import package and declare component in code */
		String javaSource = getFormJavaSource();	
		javaSource = addImport(javaSource, componentCanonicalClassName);
		javaSource = addNewComponentDeclaration(javaSource, componentSimpleClassName);
		
		/* Owner */
		IusCLComponent newComponent = (IusCLComponent)IusCLObjUtils.invokeConstructor(
			componentCanonicalClassName, 
			new IusCLParam(IusCLComponent.class, designForm));
		
		/* Name */
		newComponent.setName(newComponentName);
		
		if (newComponent instanceof IusCLControl) {
			
			/* Move on owner before non-visual components */
			IusCLComponent ownerComponent = newComponent.getOwner();
			
			if (ownerComponent.getChildren().size() > 0) {
				
				int childrenPos = ownerComponent.getComponents().indexOf(ownerComponent.getChildren().get(0));
				ownerComponent.getComponents().remove(newComponent);
				ownerComponent.getComponents().add(childrenPos, newComponent);
			}
			
			/* Find parent & container control */
			IusCLParentControl mouseDownParentControl = null;
			IusCLContainerControl mouseDownContainerControl = null;
			
			if (mouseDownControl instanceof IusCLContainerControl) {
				
				mouseDownParentControl = (IusCLParentControl)mouseDownControl;
				mouseDownContainerControl = (IusCLContainerControl)mouseDownControl;
			}
			else {
				if (mouseDownControl instanceof IusCLParentControl) {
					
					mouseDownParentControl = (IusCLParentControl)mouseDownControl;
				}
				else {
					
					mouseDownParentControl = mouseDownControl.getParent();
					mouseDownContainerControl = (IusCLContainerControl)mouseDownParentControl;
				}
			}

			/* Control */
			IusCLControl newControl = (IusCLControl)newComponent;
			
			/* Parent control */
			newControl.setParent(mouseDownParentControl);
			
			int shiftX = 0;
			int shiftY = 0;
			
			if (mouseDownContainerControl != null) {
				
				if (mouseDownControl != mouseDownContainerControl) {
					
					shiftX = mouseDownControl.getLeft() + mouseDownContainerControl.getContainerClientLeft();
					shiftY = mouseDownControl.getTop() + mouseDownContainerControl.getContainerClientTop();
				}
				newControl.setLeft(findGridX(mouseDownContainerControl, pos.getX() + shiftX));
				newControl.setTop(findGridY(mouseDownContainerControl, pos.getY() + shiftY));
			}
			
			newControl.setCaption(newComponentName);
			newControl.setText(newComponentName);
			
			/* TabOrder */
			if (newControl instanceof IusCLWinControl) {
				
				((IusCLWinControl)newControl).setTabOrder(-1);
			}
			
			/* Draw in design */
			Control swtNewControl = newControl.getSwtControl();
			if (swtNewControl != null) {
				
				makeChildDesignListeners(newControl);
				
				if (swtNewControl.getParent() instanceof Shell) {
					
					swtNewControl.setParent(formReplacementComposite);
					swtNewControl.moveAbove(null);
				}
			}
			
			//IusCLFormDesignEditorContributor.statusField.setText("New at " + pos.getX()  + " - " + pos.getY());
		}
		else {
			/* Non-visual component */
			newComponent.setParentComponent(parentComponent);
			
			if (newComponent.getIsSubComponent() == false) {
				
				addNonVisualComponent(newComponent);
			}
			if (newComponent instanceof IusCLMenuItem) {
				
				IusCLMenuItem menuItem = (IusCLMenuItem)newComponent;
				menuItem.setCaption(newComponentName);
				createBorderCaptureMenuItem(menuItem);
			}
		}
		
		serializeChange();
		setFormJavaSource(javaSource);
		setDesignComponent(newComponent);
		IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsChange);
	}

	/* **************************************************************************************************** */
//	public void addDesignCollectionItem(IusCLComponent parentComponent, IusCLCollection collection) {
//
//		serializeChange();
//		IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsChange);
//	}

	/* **************************************************************************************************** */
	private void serializeChange() {
		/* Serialize */
		String formDFM = IusCLFiler.writeComponentRes(designForm);
		formIusCLFMXMLEditor.getTextViewer().getDocument().set(formDFM);
	}
	
	/* **************************************************************************************************** */
	public void serializeAndBroadcastChange() {

		serializeChange();
		IusCLDesignIDE.dispatch(IusCLFormDesignEditor.this, IusCLDesignIDEState.dsChange);
	}

	/* **************************************************************************************************** */
	public void serializeZOrder(IusCLComponent aboveComponent, IusCLComponent belowComponent) {

//		serializeAndBroadcastChange();
		
		if ((aboveComponent instanceof IusCLControl) && (belowComponent instanceof IusCLControl)) {
			
//			Control swtAboveControl = ((IusCLControl)aboveComponent).getSwtControl();
//			Control swtBelowControl = ((IusCLControl)belowComponent).getSwtControl();
//			
//			if ((swtAboveControl != null) && (swtBelowControl != null)) {
//				swtBelowControl.moveAbove(swtAboveControl);	
//			}
		}
		else if ((aboveComponent.getIsSubComponent() == false) && (belowComponent.getIsSubComponent() == false)) {
			
			int selectedIndex = nonVisualComponentsTable.getSelectionIndex();
			
			TableItem selectedTableItem = nonVisualComponentsTable.getItem(selectedIndex);
			
			if (selectedTableItem.getData().equals(aboveComponent)) {
				
				selectedIndex = selectedIndex - 1;
			}
			else {
				
				selectedIndex = selectedIndex + 1;
			}
			
			fillNonVisualComponentsTable();
			
			nonVisualComponentsTable.select(selectedIndex);
		}
	}

	/* **************************************************************************************************** */
	private String getFormJavaSource() {
		
		return	getFormJavaSourceTextEditor().getDocumentProvider().
				getDocument(getFormJavaSourceTextEditor().getEditorInput()).get();
	}

	/* **************************************************************************************************** */
	private void setFormJavaSource(String formJavaSource) {
		
		getFormJavaSourceTextEditor().getDocumentProvider().
			getDocument(getFormJavaSourceTextEditor().getEditorInput()).set(formJavaSource);
	}

	/* **************************************************************************************************** */
	private String getApplicationJavaSource() {
		
		return	getApplicationJavaSourceTextEditor().getDocumentProvider().
				getDocument(getApplicationJavaSourceTextEditor().getEditorInput()).get();
	}

	/* **************************************************************************************************** */
	private void setApplicationJavaSource(String applicationJavaSource) {
		
		getApplicationJavaSourceTextEditor().getDocumentProvider().getDocument(
			getApplicationJavaSourceTextEditor().getEditorInput()).set(applicationJavaSource);
	}

	/* **************************************************************************************************** */
	private String deleteComponentDeclaration(String javaSource, IusCLComponent component) {
		
		String newJavaSource = javaSource;

		String componentDeclaration = IusCLStrUtils.sLineBreak() + "\tpublic " + 
			component.getClass().getSimpleName() + " " + component.getName() + ";";
		newJavaSource = newJavaSource.replaceFirst(componentDeclaration, "");
		
		if (component instanceof IusCLParentControl) {
			
			IusCLParentControl parentControl = (IusCLParentControl)component;
			for (int index = 0; index < parentControl.getControls().size(); index++) {
				
				newJavaSource = deleteComponentDeclaration(newJavaSource, parentControl.getControls().get(index));
			}
		}
		else {
			
			for (int index = 0; index < component.getChildren().size(); index++) {
				
				newJavaSource = deleteComponentDeclaration(newJavaSource, component.getChildren().get(index));
			}
		}
		return newJavaSource;
	}

	/* **************************************************************************************************** */
	private String addImport(String formJavaSource, String importName) {

		String newFormJavaSource = formJavaSource;
		
		try {

			Document document = new Document(newFormJavaSource);
			/* JLS4 in Juno */
			@SuppressWarnings("deprecation")
			ASTParser astParser = ASTParser.newParser(AST.JLS3);
			astParser.setSource(document.get().toCharArray());
			CompilationUnit compilationUnit = (CompilationUnit)astParser.createAST(null);
			
			compilationUnit.recordModifications();
			
			AST ast = compilationUnit.getAST();
			ImportDeclaration importDeclaration = ast.newImportDeclaration();
			importDeclaration.setName(ast.newName(importName.split("\\.")));
			
			Boolean importExists = false;
			for (Object o : compilationUnit.imports()) {

				ImportDeclaration oImportDeclaration = (ImportDeclaration)o;
				
				if (oImportDeclaration.getName().getFullyQualifiedName().equalsIgnoreCase(importName)) {
					
					importExists = true;
				}
			};

			if (importExists == false) {

				@SuppressWarnings("unchecked")
				List<ImportDeclaration> listImportDeclaration = compilationUnit.imports();
				listImportDeclaration.add(importDeclaration);
				
				TextEdit edits = compilationUnit.rewrite(document, null);
				edits.apply(document);
				
				newFormJavaSource = document.get();
			}
		} 
		catch (BadLocationException badLocationException) {
			
			IusCLDesignException.error("Error manipulating Java, in: addNewComponentDeclaration", 
					badLocationException);
		}
		
		return newFormJavaSource;
	}

	/* **************************************************************************************************** */
	private String addNewComponentDeclaration(String formJavaSource, String componentSimpleClassName) {

		String newFormJavaSource = formJavaSource;
		
		try {

			Document document = new Document(newFormJavaSource);
			/* JLS4 in Juno */
			@SuppressWarnings("deprecation")
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(document.get().toCharArray());
			CompilationUnit cu = (CompilationUnit)parser.createAST(null);
			
			cu.recordModifications();
			
			AST ast = cu.getAST();
			
			TypeDeclaration typeDeclaration = (TypeDeclaration)cu.types().get(0);
			
			ArrayList<String> existingFields = new ArrayList<String>();
			
			Integer pos = 0;
			for (Object o : typeDeclaration.bodyDeclarations()) {
				
				if (o instanceof FieldDeclaration) {
					
					FieldDeclaration oFieldDeclaration = (FieldDeclaration)o;
					existingFields.add(oFieldDeclaration.fragments().get(0).toString());
					
					@SuppressWarnings("unchecked")
					List<Modifier> modifiersFieldDeclaration = oFieldDeclaration.modifiers();
					for (Object om : modifiersFieldDeclaration) {
						
						Modifier modifier = (Modifier)om;
						if (modifier.getKeyword() == ModifierKeyword.PUBLIC_KEYWORD) {
							
							pos++;
						}
					}
				}
			}
			
			newComponentName = componentSimpleClassName.replace("IusCL", "");
			newComponentName = newComponentName.substring(0, 1).toLowerCase() + newComponentName.substring(1);

			int componentNameIndex = 1;
			boolean nameFound = true;
			while(nameFound) {
				
				if (existingFields.indexOf(newComponentName + componentNameIndex) != -1) {
					
					componentNameIndex++;
				}
				else {
					
					nameFound = false;
				}
			}
			newComponentName = newComponentName + componentNameIndex;

			VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
			vdf.setName(ast.newSimpleName(newComponentName));
			FieldDeclaration fieldDeclaration = ast.newFieldDeclaration(vdf);

			@SuppressWarnings("unchecked")
			List<Modifier> listFieldDeclaration = fieldDeclaration.modifiers();
			listFieldDeclaration.add(ast.newModifier(ModifierKeyword.PUBLIC_KEYWORD));

			fieldDeclaration.setType(ast.newSimpleType(ast.newSimpleName(componentSimpleClassName)));

			@SuppressWarnings("unchecked")
			List<BodyDeclaration> listBodyDeclaration = typeDeclaration.bodyDeclarations();

			Boolean isFirstFieldDeclaration = false;
			if (pos == 0) {
				
				isFirstFieldDeclaration = true;
			};
			listBodyDeclaration.add(pos, fieldDeclaration);

			TextEdit edits = cu.rewrite(document, null);
			edits.apply(document);
			
			newFormJavaSource = document.get();
			
			if (isFirstFieldDeclaration == true) {
				
				/* IusCL Components */
				
				document = new Document(newFormJavaSource);
				parser.setSource(document.get().toCharArray());
				cu = (CompilationUnit)parser.createAST(null);
				typeDeclaration = (TypeDeclaration)cu.types().get(0);
				
				Integer commentPos = 0;
				for (Object o : typeDeclaration.bodyDeclarations()) {
					
					if (o instanceof FieldDeclaration) {
						
						FieldDeclaration oFieldDeclaration = (FieldDeclaration)o;
						if (oFieldDeclaration.fragments().get(0).toString().equalsIgnoreCase(newComponentName)) {
							
							commentPos = oFieldDeclaration.getStartPosition();
							break;
						}
					}
				}

				String ls = IusCLStrUtils.sLineBreak();
				String comment = "/* IusCL Components */" + ls + "\t";
				
				newFormJavaSource = newFormJavaSource.substring(0, commentPos) + 
						comment + newFormJavaSource.substring(commentPos);
			}
		} 
		catch (BadLocationException badLocationException) {
			
			IusCLDesignException.error("Error manipulating Java, in: addNewComponentDeclaration", 
					badLocationException);
		}
		
		return newFormJavaSource;
	}
}
