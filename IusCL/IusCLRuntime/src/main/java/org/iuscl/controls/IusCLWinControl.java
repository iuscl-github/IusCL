/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.controls;

import java.util.ArrayList;
import java.util.EnumSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLKeyEvent;
import org.iuscl.events.IusCLKeyPressEvent;
import org.iuscl.events.IusCLNotifyEvent;

/* **************************************************************************************************** */
public class IusCLWinControl extends IusCLControl {

	public enum IusCLBorderStyle { bsNone, bsSingle }
	
	public enum IusCLKeyPosition { kpLeft, kpRight, kpNumericPad };
	
	/* SWT */
	private Listener swtOnClickListener = null;
	private FocusListener swtFocusListener = null;
	private KeyListener swtKeyListener = null;
	/* Properties */
	private Integer tabOrder = -1;
	private Boolean tabStop = true;
	
	/* Events */
	
	/* Selection */
	private IusCLNotifyEvent onClick = null;

	/* Focus */
	private IusCLNotifyEvent onEnter = null;
	private IusCLNotifyEvent onExit = null;

	/* Keyboard */
	private IusCLKeyPressEvent onKeyPress = null;
	private IusCLKeyEvent onKeyDown = null; 
	private IusCLKeyEvent onKeyUp = null; 
	
	/* **************************************************************************************************** */
	public IusCLWinControl(IusCLComponent aOwner) {
		super(aOwner);
		
		/* Properties */
		defineProperty("TabOrder", IusCLPropertyType.ptInteger, "-1");
		defineProperty("TabStop", IusCLPropertyType.ptBoolean, "true");
		
		/* Events */
		
		/* Selection */
		defineProperty("OnClick", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);

		/* Focus */
		defineProperty("OnEnter", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		defineProperty("OnExit", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		
		/* Keyboard */
		defineProperty("OnKeyPress", IusCLPropertyType.ptEvent, null, IusCLKeyPressEvent.class);
		defineProperty("OnKeyDown", IusCLPropertyType.ptEvent, null, IusCLKeyEvent.class);
		defineProperty("OnKeyUp", IusCLPropertyType.ptEvent, null, IusCLKeyEvent.class);
	}

	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();
		
		/* Selection */
		swtOnClickListener = new Listener() {
			/* **************************************************************************************************** */
			@Override
			public void handleEvent(Event swtEvent) {

				if (IusCLEvent.isDefinedEvent(onClick)) {
					
					onClick.invoke(IusCLWinControl.this);
				}
			}
		};

		swtFocusListener = new FocusListener() {
			/* **************************************************************************************************** */
			@Override
			public void focusLost(FocusEvent focusEvent) {
				
				doExit();
			}
			/* **************************************************************************************************** */
			@Override
			public void focusGained(FocusEvent focusEvent) {
				
				doEnter();
			}
		};
		
		swtKeyListener = new KeyListener() {
			/* **************************************************************************************************** */
			@Override
			public void keyReleased(KeyEvent swtKeyEvent) {
				
				keyUpDown(swtKeyEvent, onKeyUp);
			}
			/* **************************************************************************************************** */
			@Override
			public void keyPressed(KeyEvent swtKeyEvent) {

				keyUpDown(swtKeyEvent, onKeyDown);
				if (IusCLEvent.isDefinedEvent(onKeyPress)) {
					
					char ch = swtKeyEvent.character;
					if ((int)ch > 0) {
					
						onKeyPress.invoke(IusCLWinControl.this, ch);
					}
				}
			}
		};
	}

	/* **************************************************************************************************** */
	@Override
	protected void reCreate() {
		super.reCreate();
		
		this.getSwtControl().addFocusListener(new FocusAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void focusGained(FocusEvent focusEvent) {
			
				IusCLWinControl.this.findForm().setActiveControlValue(IusCLWinControl.this);
			}
		});
	}

	/* **************************************************************************************************** */
	private void putFocusListener() {
		
		this.getSwtControl().removeFocusListener(swtFocusListener);
		
		if (!((onEnter == null) && (onExit == null))) {
			
			this.getSwtControl().addFocusListener(swtFocusListener);
		}
	}

	/* **************************************************************************************************** */
	private void putKeyListener() {
		
		this.getSwtControl().removeKeyListener(swtKeyListener);
		
		if (!((onKeyUp == null) && (onKeyDown == null) && (onKeyPress == null))) {
			
			this.getSwtControl().addKeyListener(swtKeyListener);
		}
	}

	/* **************************************************************************************************** */
	private void keyUpDown(KeyEvent swtKeyEvent, IusCLKeyEvent onKeyUpDown) {

		if (IusCLEvent.isDefinedEvent(onKeyUpDown)) {

			IusCLKeyboardKey key = new IusCLKeyboardKey(swtKeyEvent.keyCode, swtKeyEvent.keyLocation);
			onKeyUpDown.invoke(IusCLWinControl.this, key, findShiftStateFromSwtKeyEvent(swtKeyEvent));
		}
	}
	
	/* **************************************************************************************************** */
	protected void doEnter() {
		
		if (IusCLEvent.isDefinedEvent(onEnter)) {

			onEnter.invoke(IusCLWinControl.this);
		}
	}

	/* **************************************************************************************************** */
	protected void doExit() {

		if (IusCLEvent.isDefinedEvent(onExit)) {

			onExit.invoke(IusCLWinControl.this);
		}
	}

	/* **************************************************************************************************** */
	public Boolean getCanFocus() {
		
		if ((this.getSwtControl().getStyle() & SWT.NO_FOCUS) != 0) {
			
			return false;
		}
		
		return true;
	}

	/* **************************************************************************************************** */
	public Boolean getHasFocus() {
		
		return this.getSwtControl().isFocusControl();
	}

	/* **************************************************************************************************** */
	public void setFocus() {
		
		this.getSwtControl().setFocus();
	}
	
	public Integer getTabOrder() {
		return tabOrder;
	}

	/* **************************************************************************************************** */
	public void setTabOrder(Integer tabOrder) {
		
		if (this.getIsLoading()) {
			
			this.tabOrder = tabOrder;
			return;
		}
		
		IusCLParentControl parentControl = this.getParent();
		
		if (parentControl == null) {
			
			this.tabOrder = -1;
			return;
		}

		if (!(parentControl instanceof IusCLContainerControl)) {
			
			this.tabOrder = -1;
			return;
		}
		
		IusCLContainerControl parent = (IusCLContainerControl)parentControl;
		ArrayList<IusCLWinControl> winControls = parent.getWinControls();

		int lastTabOrder = winControls.size() - 1;
		
		if ((tabOrder < 0) || (tabOrder > lastTabOrder)) {
//		if (tabOrder == -1) {
			
			tabOrder = lastTabOrder;
		}
		else {

			for (int index = 0; index < winControls.size(); index++) {
				
				int indexTabOrder = winControls.get(index).tabOrder;
				if ((this.tabOrder < indexTabOrder) && (indexTabOrder <= tabOrder)) {
					
					winControls.get(index).tabOrder = indexTabOrder - 1;
				}
				if ((tabOrder <= indexTabOrder) && (indexTabOrder < this.tabOrder)) {
					
					winControls.get(index).tabOrder = indexTabOrder + 1;
				}

			}
		}
		this.tabOrder = tabOrder;

		parent.doUpdateTabOrder();
	}
	
	/* **************************************************************************************************** */
	protected void removeFromParentTabOrder() {

		IusCLContainerControl parent = (IusCLContainerControl)this.getParent();
		ArrayList<IusCLWinControl> winControls = parent.getWinControls();
		
		for (int index = 0; index < winControls.size(); index++) {
			
			int indexTabOrder = winControls.get(index).tabOrder;
			if (indexTabOrder > tabOrder) {
				
				winControls.get(index).tabOrder = indexTabOrder - 1;	
			}
		}
		
		parent.doUpdateTabOrder();
	}

	public Boolean getTabStop() {
		return tabStop;
	}

	/* **************************************************************************************************** */
	public void setTabStop(Boolean tabStop) {
		this.tabStop = tabStop;
		
		if (this.getIsLoading()) {

			return;
		}
		
		IusCLParentControl parentControl = this.getParent();
		
		if (parentControl == null) {

			return;
		}
		if (!(parentControl instanceof IusCLContainerControl)) {
			
			return;
		}
		
		IusCLContainerControl parent = (IusCLContainerControl)parentControl;
		parent.doUpdateTabOrder();
	}

	public IusCLNotifyEvent getOnClick() {
		return onClick;
	}

	/* **************************************************************************************************** */
	public void setOnClick(IusCLNotifyEvent onClick) {
		this.onClick = onClick;
		
		IusCLWinControl.this.getSwtControl().removeListener(SWT.Selection, swtOnClickListener);
		if (this.onClick != null) {
			
			IusCLWinControl.this.getSwtControl().addListener(SWT.Selection, swtOnClickListener);
		}
	}

	public IusCLNotifyEvent getOnEnter() {
		return onEnter;
	}

	/* **************************************************************************************************** */
	public void setOnEnter(IusCLNotifyEvent onEnter) {
		this.onEnter = onEnter;
		
		putFocusListener();
	}

	public IusCLNotifyEvent getOnExit() {
		return onExit;
	}

	/* **************************************************************************************************** */
	public void setOnExit(IusCLNotifyEvent onExit) {
		this.onExit = onExit;
		
		putFocusListener();
	}
	
	public IusCLKeyPressEvent getOnKeyPress() {
		return onKeyPress;
	}

	/* **************************************************************************************************** */
	public void setOnKeyPress(IusCLKeyPressEvent onKeyPress) {
		this.onKeyPress = onKeyPress;
		
		putKeyListener();
	}

	public IusCLKeyEvent getOnKeyDown() {
		return onKeyDown;
	}

	/* **************************************************************************************************** */
	public void setOnKeyDown(IusCLKeyEvent onKeyDown) {
		this.onKeyDown = onKeyDown;
		
		putKeyListener();
	}

	public IusCLKeyEvent getOnKeyUp() {
		return onKeyUp;
	}

	/* **************************************************************************************************** */
	public void setOnKeyUp(IusCLKeyEvent onKeyUp) {
		this.onKeyUp = onKeyUp;
		
		putKeyListener();
	}

	/* **************************************************************************************************** */
	protected EnumSet<IusCLShiftState> findShiftStateFromSwtKeyEvent(KeyEvent swtKeyEvent) {

		return findShiftStateFromSwtStateMask(swtKeyEvent.stateMask);
	}

}
