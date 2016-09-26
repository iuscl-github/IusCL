/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.propertyeditors;

import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.iuscl.classes.IusCLPersistent;
import org.iuscl.sysutils.IusCLStrUtils;

/* **************************************************************************************************** */
public class IusCLDesignFilterPropertyEditor extends IusCLDesignPropertyEditor {

	private Composite filterComposite;
	private Button filterButton;
	private Text filterText;

	private String propertyValue;

	/* **************************************************************************************************** */
	public IusCLDesignFilterPropertyEditor(final TreeItem swtTreeItem, IusCLPersistent persistent) {
		
		super(swtTreeItem, persistent);
		
		propertyValue = swtTreeItem.getText(1);
		
		/* Editor cell */
		filterComposite = new Composite(swtTreeItem.getParent(), SWT.NONE);
		//filterComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		GridLayout filterCompositeGridLayout = new GridLayout();
		filterCompositeGridLayout.numColumns = 2;
		filterCompositeGridLayout.marginTop = 0;
		filterCompositeGridLayout.marginBottom = 0;
		filterCompositeGridLayout.marginLeft = 0;
		filterCompositeGridLayout.marginRight = 0;
		filterCompositeGridLayout.verticalSpacing = 0;
		filterCompositeGridLayout.horizontalSpacing = 0;
		filterCompositeGridLayout.marginWidth = 0;
		filterCompositeGridLayout.marginHeight = 0;

		filterComposite.setLayout(filterCompositeGridLayout);
		swtEditorFocusControl = filterComposite;
		
		filterText = new Text(filterComposite, SWT.READ_ONLY | SWT.BORDER);
		GridData gridDataText = new GridData();
		gridDataText.heightHint = 10;
		gridDataText.horizontalAlignment = GridData.FILL;
		gridDataText.verticalAlignment = GridData.FILL;
		gridDataText.grabExcessHorizontalSpace = true;
		gridDataText.grabExcessVerticalSpace = true;
		filterText.setLayoutData(gridDataText);
		filterText.setText("(String)");
		
		filterButton = new Button(filterComposite, SWT.PUSH);
		filterButton.setText("...");
		GridData gridDataButton = new GridData();
		gridDataButton.heightHint = 10;
		gridDataButton.horizontalAlignment = GridData.END;
		gridDataButton.verticalAlignment = GridData.FILL;
		filterButton.setLayoutData(gridDataButton);
		
		
		swtEditorFocusControl.setData(IusCLDesignFilterPropertyEditor.this);
		
		filterComposite.addKeyListener(swtKeyListener);
		filterText.addKeyListener(swtKeyListener);
		filterButton.addKeyListener(swtKeyListener);
		
		filterButton.addFocusListener(swtFocusListener);
		filterText.addFocusListener(swtFocusListener);
		
		filterButton.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				
				clickButton();
			}
		});
        
		filterButton.setFocus();
	}

	/* **************************************************************************************************** */
	private void clickButton() {
		/* Editor shell */
		final Shell filterShell = new Shell(SWT.CLOSE | SWT.BORDER | SWT.RESIZE |SWT.APPLICATION_MODAL);
		filterShell.setText("Filter Editor");

		InputStream inputStream = IusCLDesignStringListPropertyEditor.class.getResourceAsStream("/resources/images/IusCLPerspective.gif");
		Image imageIusCL = new Image(Display.getCurrent(), inputStream);
		filterShell.setImage(imageIusCL);

	    Monitor primary = Display.getDefault().getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    filterShell.setSize(640, 400);
	    Rectangle rect = filterShell.getBounds();
	    
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    
	    filterShell.setLocation(x, y);

		GridLayout filterShellGridLayout = new GridLayout();
		filterShellGridLayout.numColumns = 1;
		filterShellGridLayout.marginTop = 8;
		filterShellGridLayout.marginBottom = 8;
		filterShellGridLayout.marginLeft = 8;
		filterShellGridLayout.marginRight = 8;
		filterShellGridLayout.verticalSpacing = 8;
		filterShellGridLayout.horizontalSpacing = 0;
		filterShellGridLayout.marginWidth = 0;
		filterShellGridLayout.marginHeight = 0;
		filterShell.setLayout(filterShellGridLayout);
	    
	    
	    Composite upComposite = new Composite(filterShell, SWT.BORDER);
	    //upComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
	    GridData gridDataUpComposite = new GridData();
		gridDataUpComposite.horizontalAlignment = GridData.FILL;
		gridDataUpComposite.verticalAlignment = GridData.FILL;
		gridDataUpComposite.grabExcessHorizontalSpace = true;
		gridDataUpComposite.grabExcessVerticalSpace = true;
		upComposite.setLayoutData(gridDataUpComposite);
		upComposite.setLayout(filterShellGridLayout);

		final Table filterTable = new Table(upComposite, SWT.BORDER | SWT.FULL_SELECTION);
		filterTable.setLinesVisible(true);
		filterTable.setHeaderVisible(true);
	    GridData gridDataFilterTable = new GridData();
	    gridDataFilterTable.horizontalAlignment = GridData.FILL;
	    gridDataFilterTable.verticalAlignment = GridData.FILL;
	    gridDataFilterTable.grabExcessHorizontalSpace = true;
	    gridDataFilterTable.grabExcessVerticalSpace = true;
	    filterTable.setLayoutData(gridDataFilterTable);
	    
	    FontMetrics fm = new GC(Display.getCurrent()).getFontMetrics();
	    final int rowHeight = fm.getHeight() + 8; 
		
	    final TableEditor filterTableEditor = new TableEditor(filterTable);
	    filterTableEditor.horizontalAlignment = SWT.LEFT;
	    filterTableEditor.grabHorizontal = true;

	    
	    filterTable.addListener(SWT.Resize, new Listener() {
	    	
			/* **************************************************************************************************** */
			@Override
			public void handleEvent(Event event) {
				
				int tableWidth = filterTable.getBounds().width;
				int columnWidth = (int)((tableWidth - filterTable.getVerticalBar().getSize().x) / 2);
				filterTable.getColumn(0).setWidth(columnWidth);
				filterTable.getColumn(1).setWidth(columnWidth);
			}
		});
	    
	    filterTable.addListener(SWT.MeasureItem, new Listener() {
	    	
			/* **************************************************************************************************** */
			@Override
			public void handleEvent(Event event) {
				
				event.height = rowHeight; 
			}
		});
	    
	    filterTable.addTraverseListener(new TraverseListener() {
	    	
			/* **************************************************************************************************** */
			@Override
			public void keyTraversed(TraverseEvent traverseEvent) {
				
				switch (traverseEvent.detail) {
				
				case SWT.TRAVERSE_ARROW_NEXT:
					if (filterTable.getSelectionIndex() == filterTable.getItems().length - 1) {
						TableItem item = new TableItem(filterTable, SWT.NONE);
						item.setText(0, "");
						item.setText(1, "");
					}
					break;
				}
				
			}
		});
	    
	    filterTable.addListener(SWT.Selection, new Listener() {
	    	
			/* **************************************************************************************************** */
			@Override
			public void handleEvent(Event event) {
				
				final TableItem filterTableItem = (TableItem)event.item;

				Integer col = 0;
				Point mousePt = filterTable.toControl(Display.getCurrent().getCursorLocation());
				
				if (filterTableItem.getBounds(1).contains(mousePt)) {
					
					col = 1;
				}
				final Integer column = col;
				
				final Text columnTextEditor = new Text(filterTable, SWT.BORDER);
				columnTextEditor.setText(filterTableItem.getText(column));
				columnTextEditor.selectAll();
				columnTextEditor.setFocus();
				
				Listener columnTextEditorListener = new Listener() {
					
					/* **************************************************************************************************** */
					@Override
					public void handleEvent(final Event event) {
						
						switch (event.type) {
						case SWT.FocusOut:
							
							filterTableItem.setText(column, columnTextEditor.getText());
							columnTextEditor.dispose();
		                    break;
						case SWT.KeyDown:
							switch (event.keyCode) {
							case SWT.ARROW_DOWN:
							case SWT.ARROW_UP:
								
								filterTableItem.setText(column, columnTextEditor.getText());
		                    	columnTextEditor.dispose();
		                    	event.doit = false;
							}
							switch (event.character) {
							case SWT.CR:
								
								filterTableItem.setText(column, columnTextEditor.getText());
							case SWT.ESC:
								
		                    	columnTextEditor.dispose();
		                    	event.doit = false;
							}
							break;
						}
					}
				};
				
				columnTextEditor.addListener(SWT.FocusOut, columnTextEditorListener);
				columnTextEditor.addListener(SWT.KeyDown, columnTextEditorListener);
				
				filterTableEditor.setEditor(columnTextEditor, filterTableItem, column);
			}
		});
	    
		/* Columns */
		TableColumn nameColumn = new TableColumn(filterTable, SWT.NONE);
		nameColumn.setText("Filter Name");
		nameColumn.setWidth(200);
		//nameColumn.setImage(IusCLDesignIDE.loadImageFromResource("IusCLObjectTreeViewComponent.gif"));

		TableColumn typeColumn = new TableColumn(filterTable, SWT.NONE);
		typeColumn.setText("Filter");
		typeColumn.setWidth(200);
		
		/* Display filter pairs */
		if (IusCLStrUtils.isNotNullNotEmpty(propertyValue)) {
			
			String[] filters = propertyValue.split("\\|");
			
			for (int index = 0; index < filters.length / 2; index++) {
				
				TableItem item = new TableItem(filterTable, SWT.NONE);
				item.setText(0, filters[index * 2]);
				item.setText(1, filters[index * 2 + 1]);
			}
		}
		
	    Composite downComposite = new Composite(filterShell, SWT.NONE);
	    //downComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
	    GridData gridDataDownComposite = new GridData();
	    gridDataDownComposite.horizontalAlignment = GridData.FILL;
	    gridDataDownComposite.grabExcessHorizontalSpace = true;
	    downComposite.setLayoutData(gridDataDownComposite);
	    
		GridLayout downCompositeGridLayout = new GridLayout();
		downCompositeGridLayout.numColumns = 3;
		downCompositeGridLayout.marginTop = 0;
		downCompositeGridLayout.marginBottom = 0;
		downCompositeGridLayout.marginLeft = 0;
		downCompositeGridLayout.marginRight = 0;
		downCompositeGridLayout.verticalSpacing = 0;
		downCompositeGridLayout.horizontalSpacing = 8;
		downCompositeGridLayout.marginWidth = 0;
		downCompositeGridLayout.marginHeight = 0;
		downComposite.setLayout(downCompositeGridLayout);

	    Composite leftComposite = new Composite(downComposite, SWT.NONE);
	    GridData gridDataLeftComposite = new GridData();
	    gridDataLeftComposite.heightHint = 10;
	    gridDataLeftComposite.horizontalAlignment = GridData.FILL;
	    gridDataLeftComposite.grabExcessHorizontalSpace = true;
	    leftComposite.setLayoutData(gridDataLeftComposite);
	    
	    
	    
	    Button okButton = new Button(downComposite, SWT.PUSH);
	    GridData gridDataOkButton = new GridData();
	    gridDataOkButton.widthHint = 90;
	    okButton.setLayoutData(gridDataOkButton);
	    okButton.setText("OK");
	    
	    okButton.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				
				propertyValue = "";
				for (int index = 0; index < filterTable.getItemCount(); index++) {
					String filterName = filterTable.getItem(index).getText(0).trim();
					String filterExt = filterTable.getItem(index).getText(1).trim();
					
					if (IusCLStrUtils.isNotNullNotEmpty(filterName) &&
							IusCLStrUtils.isNotNullNotEmpty(filterExt)) {
						
						if (IusCLStrUtils.isNotNullNotEmpty(propertyValue)) {
							
							propertyValue = propertyValue + "|" + filterName + "|" + filterExt;
						}
						else {
							
							propertyValue = filterName + "|" + filterExt;
						}
					}
				}
				
				filterButton.addFocusListener(swtFocusListener);
				filterShell.dispose();
			}
		});
	    
	    Button cancelButton = new Button(downComposite, SWT.PUSH);
	    GridData gridDataCancelButton = new GridData();
	    gridDataCancelButton.widthHint = 90;
	    cancelButton.setLayoutData(gridDataCancelButton);
	    cancelButton.setText("Cancel");
	    
	    cancelButton.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				
				filterButton.addFocusListener(swtFocusListener);
				filterShell.dispose();
			}
		});
	    
	    filterButton.removeFocusListener(swtFocusListener);
	    filterShell.open();
		//stringsShell.setVisible(true);
		//System.out.println("out from modal");
	}

	/* **************************************************************************************************** */
	@Override
	public String getEditorValue() {
		
		return propertyValue; 
	}
	
	/* **************************************************************************************************** */
	@Override
	public void closeEditor() {
		
		filterText.dispose();
		filterButton.dispose();
		filterComposite.dispose();
		
		super.closeEditor();
	}
}
