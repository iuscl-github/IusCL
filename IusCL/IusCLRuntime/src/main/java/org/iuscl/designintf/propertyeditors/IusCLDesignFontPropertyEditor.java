/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.designintf.propertyeditors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.iuscl.classes.IusCLPersistent;
import org.iuscl.forms.IusCLApplication;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLFont;

/* **************************************************************************************************** */
public class IusCLDesignFontPropertyEditor extends IusCLDesignPropertyEditor {

	private Composite fontComposite;
	private Button fontButton;
	private Text fontText;

	private IusCLFont originalFont = null;
	private IusCLFont modifiedFont = null;

	/* **************************************************************************************************** */
	FocusListener editorFocusListener = new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent focusEvent) {
			
			originalFont.setSwtFont(modifiedFont.getSwtFont());
			originalFont.setColor(modifiedFont.getColor());
			IusCLDesignFontPropertyEditor.this.closeEditor();
		}
		@Override
		public void focusGained(FocusEvent focusEvent) {
			/*  */
		}
	};

	/* **************************************************************************************************** */
	KeyListener editorKeyListener = new KeyListener() {
		
		/* **************************************************************************************************** */
		@Override
		public void keyReleased(KeyEvent keyEvent) {
			
			switch (keyEvent.character) {
			case SWT.ESC:
				IusCLDesignFontPropertyEditor.this.closeEditor();
				break;
			case SWT.CR:
				originalFont.setSwtFont(modifiedFont.getSwtFont());
				originalFont.setColor(modifiedFont.getColor());
				IusCLDesignFontPropertyEditor.this.closeEditor();
				break;
			default:
				break;
			}
		}

		/* **************************************************************************************************** */
		@Override
		public void keyPressed(KeyEvent keyEvent) {
			/*  */
		}
	};
	
	/* **************************************************************************************************** */
	public IusCLDesignFontPropertyEditor(final TreeItem swtTreeItem, IusCLPersistent persistent) {
		
		super(swtTreeItem, persistent);
		
		originalFont = (IusCLFont)(persistent.getPropertyValueInvoke(propertyName));
		modifiedFont = new IusCLFont();
		
		modifiedFont.setSwtFont(originalFont.getSwtFont());
		modifiedFont.setColor(originalFont.getColor());
		
		/* Editor cell */
		fontComposite = new Composite(swtTreeItem.getParent(), SWT.NONE);
		GridLayout stringsCompositeGridLayout = new GridLayout();
		stringsCompositeGridLayout.numColumns = 2;
		stringsCompositeGridLayout.marginTop = 0;
		stringsCompositeGridLayout.marginBottom = 0;
		stringsCompositeGridLayout.marginLeft = 0;
		stringsCompositeGridLayout.marginRight = 0;
		stringsCompositeGridLayout.verticalSpacing = 0;
		stringsCompositeGridLayout.horizontalSpacing = 0;
		stringsCompositeGridLayout.marginWidth = 0;
		stringsCompositeGridLayout.marginHeight = 0;

		fontComposite.setLayout(stringsCompositeGridLayout);
		swtEditorFocusControl = fontComposite;
		
		fontText = new Text(fontComposite, SWT.READ_ONLY | SWT.BORDER);
		GridData gridDataText = new GridData();
		gridDataText.heightHint = 10;
		gridDataText.horizontalAlignment = GridData.FILL;
		gridDataText.verticalAlignment = GridData.FILL;
		gridDataText.grabExcessHorizontalSpace = true;
		gridDataText.grabExcessVerticalSpace = true;
		fontText.setLayoutData(gridDataText);
		fontText.setText("(IusCLFont)");
		
		fontButton = new Button(fontComposite, SWT.PUSH);
		fontButton.setText("...");
		GridData gridDataButton = new GridData();
		gridDataButton.heightHint = 10;
		gridDataButton.horizontalAlignment = GridData.END;
		gridDataButton.verticalAlignment = GridData.FILL;
		fontButton.setLayoutData(gridDataButton);
		
		
		swtEditorFocusControl.setData(IusCLDesignFontPropertyEditor.this);

		fontComposite.addKeyListener(editorKeyListener);
		fontText.addKeyListener(editorKeyListener);
		fontButton.addKeyListener(editorKeyListener);
		
		fontButton.addFocusListener(editorFocusListener);
		fontText.addFocusListener(editorFocusListener);
		
		fontButton.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				
				clickButton();
			}
		});
        
		fontButton.setFocus();
	}

	/* **************************************************************************************************** */
	private void clickButton() {

		FontDialog swtFontDialog = new FontDialog(IusCLApplication.getSwtApplicationShell());
		
		swtFontDialog.setText("Font");
		swtFontDialog.setFontList(modifiedFont.getSwtFont().getFontData());
		RGB originalSwtRGB = modifiedFont.getColor().getAsSwtColor().getRGB();
		swtFontDialog.setRGB(originalSwtRGB);
		
		fontButton.removeFocusListener(editorFocusListener);

		FontData swtFontData = swtFontDialog.open();
		fontButton.addFocusListener(editorFocusListener);
		if (swtFontData != null) {
			
			Font swtFont = new Font(Display.getCurrent(), swtFontData);
			modifiedFont.setSwtFont(swtFont);
			
			RGB modifiedSwtRGB = swtFontDialog.getRGB();
			if (modifiedSwtRGB != originalSwtRGB) {
				
				IusCLColor modifiedColor = new IusCLColor();
				modifiedColor.loadFromSwtColor(new Color(Display.getCurrent(), modifiedSwtRGB));
				modifiedFont.setColor(modifiedColor);
			}
		}
	}

	/* **************************************************************************************************** */
	@Override
	public void closeEditor() {

		fontText.dispose();
		fontButton.dispose();
		fontComposite.dispose();

		super.closeEditor();
	}
}
