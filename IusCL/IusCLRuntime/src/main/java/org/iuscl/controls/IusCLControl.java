/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.controls;

import java.util.ArrayList;
import java.util.EnumSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.events.IusCLCanResizeEvent;
import org.iuscl.events.IusCLConstrainedResizeEvent;
import org.iuscl.events.IusCLContextPopupEvent;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLMouseEvent;
import org.iuscl.events.IusCLMouseHoverEvent;
import org.iuscl.events.IusCLMouseMoveEvent;
import org.iuscl.events.IusCLMouseWheelEvent;
import org.iuscl.events.IusCLMouseWheelUpDownEvent;
import org.iuscl.events.IusCLNotifyEvent;
import org.iuscl.forms.IusCLApplication;
import org.iuscl.forms.IusCLCursor;
import org.iuscl.forms.IusCLCursor.IusCLPredefinedCursors;
import org.iuscl.forms.IusCLForm;
import org.iuscl.forms.IusCLScreen;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLColor.IusCLStandardColors;
import org.iuscl.graphics.IusCLFont;
import org.iuscl.menus.IusCLMenuItem;
import org.iuscl.menus.IusCLPopupMenu;
import org.iuscl.sysutils.IusCLStrUtils;
import org.iuscl.types.IusCLPoint;
import org.iuscl.types.IusCLRectangle;
import org.iuscl.types.IusCLSize;

/* **************************************************************************************************** */
public class IusCLControl extends IusCLComponent {

	public enum IusCLAlign { alNone, alTop, alBottom, alLeft, alRight, alClient, alCustom };
	
	public enum IusCLMouseButton { mbLeft, mbRight, mbMiddle };
	public enum IusCLShiftState { ssShift, ssAlt, ssAltGr, ssCtrl, ssCmd,
		ssMouseLeft, ssMouseRight, ssMouseMiddle, ssMouseDouble };
	
	private IusCLParentControl parent = null;
	private Control swtControl = null;
	
	/* SWT */
	private MenuDetectListener swtMenuDetectListener = null;

	private MouseListener swtMouseListener = null; 
	private MouseTrackListener swtMouseTrackListener = null;
	private MouseMoveListener swtMouseMoveListener = null;
	private MouseWheelListener swtMouseWheelListener = null;
	
	/* Properties */
	protected Integer top = 0;
	protected Integer left = 0;
	protected Integer width = 100;
	protected Integer height = 100;

	private IusCLPoint originalPos = new IusCLPoint(0, 0);
	private IusCLSize compensatePos = new IusCLSize(0, 0);
	private IusCLSize compensateSize = new IusCLSize(0, 0);

	
	protected String caption = "";
	protected String text = "";

	protected Boolean enabled = true;
	private Boolean visible = true;

	
	private Boolean autoSize = false;
	private IusCLAnchors anchors = new IusCLAnchors();
	private IusCLAlign align = IusCLAlign.alNone;
	private IusCLSizeConstraints constraints = new IusCLSizeConstraints();

	
	private String cursor = "crDefault";

	private IusCLFont font = new IusCLFont();
	private Boolean parentFont = true;

	private String hint = "";
	private Boolean parentShowHint = true;
	private Boolean showHint = false;

	private IusCLColor color = IusCLColor.getStandardColor(IusCLStandardColors.clBtnFace);
	private Boolean parentColor = true;
	
	private IusCLPopupMenu popupMenu = null;

	/* Events */
	
	/* Resize */
	private IusCLCanResizeEvent onCanResize = null;
	private IusCLConstrainedResizeEvent onConstrainedResize = null;
	private IusCLNotifyEvent onResize = null;
	
	/* Selection */
	private IusCLContextPopupEvent onContextPopup = null;
	
	/* Mouse */
	private IusCLMouseEvent onMouseDown = null;
	private IusCLMouseWheelUpDownEvent onMouseWheelDown = null;
	private IusCLMouseEvent onMouseUp = null;
	private IusCLMouseWheelUpDownEvent onMouseWheelUp = null;
	
	private IusCLNotifyEvent onDoubleClick = null;
	
	private IusCLNotifyEvent onMouseEnter = null;
	private IusCLNotifyEvent onMouseExit = null;

	private IusCLMouseMoveEvent onMouseMove = null;
	private IusCLMouseHoverEvent onMouseHover = null;
	
	private IusCLMouseWheelEvent onMouseWheel = null;
	
	/* **************************************************************************************************** */
	public IusCLControl(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		defineProperty("Top", IusCLPropertyType.ptInteger, "0");
		defineProperty("Left", IusCLPropertyType.ptInteger, "0");
		defineProperty("Width", IusCLPropertyType.ptInteger, "100");
		defineProperty("Height", IusCLPropertyType.ptInteger, "100");
		defineProperty("Caption", IusCLPropertyType.ptString, "defaultCaption");
		
		defineProperty("Anchors.Left", IusCLPropertyType.ptBoolean, "true");
		defineProperty("Anchors.Top", IusCLPropertyType.ptBoolean, "true");
		defineProperty("Anchors.Right", IusCLPropertyType.ptBoolean, "false");
		defineProperty("Anchors.Bottom", IusCLPropertyType.ptBoolean, "false");
		
		defineProperty("ParentShowHint", IusCLPropertyType.ptBoolean, "true");
		defineProperty("ShowHint", IusCLPropertyType.ptBoolean, "false");
		defineProperty("Hint", IusCLPropertyType.ptString, "");

		defineProperty("Enabled", IusCLPropertyType.ptBoolean, "true");
		defineProperty("Visible", IusCLPropertyType.ptBoolean, "true");
		
		defineProperty("Align", IusCLPropertyType.ptEnum, "alNone", IusCLAlign.alNone);
		
		defineProperty("Constraints.MaxHeight", IusCLPropertyType.ptInteger, "0");
		defineProperty("Constraints.MaxWidth", IusCLPropertyType.ptInteger, "0");
		defineProperty("Constraints.MinHeight", IusCLPropertyType.ptInteger, "0");
		defineProperty("Constraints.MinWidth", IusCLPropertyType.ptInteger, "0");

		defineProperty("Cursor", IusCLPropertyType.ptCursor, "crDefault", IusCLPredefinedCursors.crDefault);

		defineProperty("Color", IusCLPropertyType.ptColor, "clBtnFace", IusCLStandardColors.clBtnFace);
		defineProperty("ParentColor", IusCLPropertyType.ptBoolean, "true");
		
		defineProperty("PopupMenu", IusCLPropertyType.ptComponent, "", IusCLPopupMenu.class);

		/* Font */
		//IusCLFont defaultFont = new IusCLFont();
		font.setNotify(this, "setFont");
		defineProperty("Font", IusCLPropertyType.ptFont, "(IusCLFont)");
		IusCLFont.defineFontProperties(this, "Font", font);
		defineProperty("ParentFont", IusCLPropertyType.ptBoolean, "true");

		/* Events */
		
		/* Resize */
		defineProperty("OnCanResize", IusCLPropertyType.ptEvent, null, IusCLCanResizeEvent.class);
		defineProperty("OnConstrainedResize", IusCLPropertyType.ptEvent, null, IusCLConstrainedResizeEvent.class);
		defineProperty("OnResize", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);

		/* Selection */
		defineProperty("OnContextPopup", IusCLPropertyType.ptEvent, null, IusCLContextPopupEvent.class);

		/* Mouse */
		defineProperty("OnMouseDown", IusCLPropertyType.ptEvent, null, IusCLMouseEvent.class);
		defineProperty("OnMouseWheelDown", IusCLPropertyType.ptEvent, null, IusCLMouseWheelUpDownEvent.class);
		defineProperty("OnMouseUp", IusCLPropertyType.ptEvent, null, IusCLMouseEvent.class);
		defineProperty("OnMouseWheelUp", IusCLPropertyType.ptEvent, null, IusCLMouseWheelUpDownEvent.class);
		
		defineProperty("OnDoubleClick", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		
		defineProperty("OnMouseEnter", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		defineProperty("OnMouseExit", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);

		defineProperty("OnMouseMove", IusCLPropertyType.ptEvent, null, IusCLMouseMoveEvent.class);
		defineProperty("OnMouseHover", IusCLPropertyType.ptEvent, null, IusCLMouseHoverEvent.class);

		defineProperty("OnMouseWheel", IusCLPropertyType.ptEvent, null, IusCLMouseWheelEvent.class);

		/* TODO Drag'n'drop */
	}

	/* **************************************************************************************************** */
	@Override
	public void free() {
		
		if (this != null) {
			/* Remove from parent */
			if (this.getParent() != null) {
				this.getParent().getControls().remove(this);
			}
		}
		
		super.free();
	}

	/* **************************************************************************************************** */
	@Override
	public void destroy() {

		if (swtControl != null) {
//			if (storedColor != null) {
//				if (storedColor.getColorConstant() == null) {
//					swtControl.getBackground().dispose();
//				}
//			}
			swtControl.dispose();
		}
		
		super.destroy();
	}
	
	/* **************************************************************************************************** */
	public void setSwtControl(Control swtControl) {
		
		this.swtControl = swtControl;
		this.swtControl.setData(IusCLControl.this);
		reCreate();
	}

	/* **************************************************************************************************** */
	protected void reCreate() {
		/* Listeners applied to swtControl always */
	}

	/* **************************************************************************************************** */
	private void putMouseListener() {
		
		swtControl.removeMouseListener(swtMouseListener);
		
		if (!((onMouseDown == null) && (onMouseWheelDown == null) && 
				(onMouseUp == null) && (onMouseWheelUp == null) && (onDoubleClick == null))) {
			
			swtControl.addMouseListener(swtMouseListener);
		}
	}

	/* **************************************************************************************************** */
	private void putMouseTrackListener() {
		
		swtControl.removeMouseTrackListener(swtMouseTrackListener);
		
		if (!((onMouseEnter == null) && (onMouseExit == null) && (onMouseHover == null))) {
			
			swtControl.addMouseTrackListener(swtMouseTrackListener);
		}
	}

	/* **************************************************************************************************** */
	private void mouseUpDown(MouseEvent swtMouseEvent, 
			IusCLMouseWheelUpDownEvent onMouseWheelUpDown, IusCLMouseEvent onMouseUpDown) {
		
		IusCLMouseButton button = findMouseButtonFromMouseEvent(swtMouseEvent);
		Boolean handled = false;

		if (button == IusCLMouseButton.mbMiddle) {
			
			if (IusCLEvent.isDefinedEvent(onMouseWheelUpDown)) {
				
				EnumSet<IusCLShiftState> shift = findShiftStateFromSwtMouseEvent(swtMouseEvent);
				handled = onMouseWheelUpDown.invoke(IusCLControl.this, shift, swtMouseEvent.x, swtMouseEvent.y);
			}
		}
		
		if (handled == false) {
			
			if (IusCLEvent.isDefinedEvent(onMouseUpDown)) {

				EnumSet<IusCLShiftState> shift = findShiftStateFromSwtMouseEvent(swtMouseEvent);
				onMouseUpDown.invoke(IusCLControl.this, button, shift, swtMouseEvent.x, swtMouseEvent.y);
			}
		}
	}

	/* **************************************************************************************************** */
	public void repaint() {
		
		if (swtControl != null) {
			swtControl.redraw();
		}
	}

	public Control getSwtControl() {
		return swtControl;
	}
	
	public Integer getTop() {
		return top;
	}

	/* **************************************************************************************************** */
	public void setTop(Integer top) {
		
		updateBounds(this.left, top, this.width, this.height);
		doParentAlignControls();
	}
	
	public Integer getLeft() {
		return left;
	}
	
	/* **************************************************************************************************** */
	public void setLeft(Integer left) {

		updateBounds(left, this.top, this.width, this.height);
		doParentAlignControls();
	}

	public Integer getWidth() {
		return width;
	}
	
	/* **************************************************************************************************** */
	public void setWidth(Integer width) {
		
		updateBounds(this.left, this.top, width, this.height);
		doParentAlignControls();
	}
	
	public Integer getHeight() {
		return height;
	}

	/* **************************************************************************************************** */
	public void setHeight(Integer height) {

		updateBounds(this.left, this.top, this.width, height);
		doParentAlignControls();
	}

	/* **************************************************************************************************** */
	public void setBounds(Integer aLeft, Integer aTop, Integer aWidth, Integer aHeight) {
		
		updateBounds(aLeft, aTop, aWidth, aHeight);
		doParentAlignControls();
	}

	/* **************************************************************************************************** */
	protected void updateBounds(Integer aLeft, Integer aTop, Integer aWidth, Integer aHeight) {

//		if (this.left.equals(aLeft) && this.top.equals(aTop) && 
//				this.width.equals(aWidth) && this.height.equals(aHeight)) {
//			
//			return;
//		}
		
		IusCLSize parentDelta = new IusCLSize(aWidth - width, aHeight - height);
		
		this.left = aLeft;
		this.top = aTop;
		this.width = aWidth;
		this.height = aHeight;
		
		int containerClientLeft = 0;
		int containerClientTop = 0;
		
		if (this.getParent() != null) {
			if (this.getParent() instanceof IusCLContainerControl) {
				
				containerClientLeft = ((IusCLContainerControl)this.getParent()).getContainerClientLeft();
				containerClientTop = ((IusCLContainerControl)this.getParent()).getContainerClientTop();
			}
		}
		if (swtControl != null) {
			swtControl.setLocation(containerClientLeft + this.left, containerClientTop + this.top);
			swtControl.setSize(this.width, this.height);
			
			if (this instanceof IusCLContainerControl) {
				
				((IusCLContainerControl)this).doAlignControls(parentDelta);
			}
		}

		originalPos.setX(left);
		originalPos.setY(top);
		compensatePos.setWidthHeight(0, 0);
		compensateSize.setWidthHeight(0, 0);
	}

	/* **************************************************************************************************** */
	public void bringToFront() {
		
		bringToFront(null);
	}

	/* **************************************************************************************************** */
	public void bringToFront(IusCLControl onFrontOfControl) {
		
		int controlIndexInParent = parent.getControls().indexOf(this);
		
		if (onFrontOfControl == null) {
			
			parent.getControls().remove(controlIndexInParent);
			parent.getControls().add(this);
			swtControl.moveAbove(null);
			doParentAlignControls();
		}
		else {

			parent.getControls().remove(controlIndexInParent);

			int onFrontOfControlIndexInParent = parent.getControls().indexOf(onFrontOfControl);

			parent.getControls().add(onFrontOfControlIndexInParent + 1, this);

			swtControl.moveAbove(onFrontOfControl.getSwtControl());
			doParentAlignControls();
		}
	}

	/* **************************************************************************************************** */
	public void sendToBack() {
		
		sendToBack(null);
	}

	/* **************************************************************************************************** */
	public void sendToBack(IusCLControl onBackOfControl) {
		
		int controlIndexInParent = parent.getControls().indexOf(this);
		
		if (onBackOfControl == null) {
			
			parent.getControls().remove(controlIndexInParent);
			parent.getControls().add(0, this);
			swtControl.moveBelow(null);
			doParentAlignControls();
		}
		else {

			parent.getControls().remove(controlIndexInParent);

			int onBackOfControlIndexInParent = parent.getControls().indexOf(onBackOfControl);

			parent.getControls().add(onBackOfControlIndexInParent, this);

			swtControl.moveBelow(onBackOfControl.getSwtControl());
			doParentAlignControls();
		}
	}

	/* **************************************************************************************************** */
	public void doParentAlignControls() {

		if (this.getParent() != null) {
			
			if (this.getParent() instanceof IusCLContainerControl) {
				
				((IusCLContainerControl)this.getParent()).doAlignControls(new IusCLSize(0, 0));
			}
		}
	}

	/* **************************************************************************************************** */
	private Integer doConstraint(Integer size, Integer min, Integer max) {
		
		if (size <= min) {
			
			return min;
		}
		else {
			if ((max > 0) && (size > max)) {
				
				return max;
			}
			else {
				return size;
			}
		}
	}

	/* **************************************************************************************************** */
	private Integer doCompensate(Integer size, Integer min, Integer max) {
		
		if (size <= min) {
			
			return size - min;
		}
		else {
			if ((max > 0) && (size > max)) {
				
				return size - max;
			}
			else {
				return 0;
			}
		}
	}

	/* **************************************************************************************************** */
	public IusCLRectangle doPositionAndSize(IusCLSize parentDelta, IusCLRectangle availableClientRect) {
		
		Integer newLeft = left;
		Integer newTop = top;
		Integer newWidth = width;
		Integer newHeight = height;

		/* AutoSize */
		if (autoSize == true) {

			swtControl.pack();
			newWidth = swtControl.getSize().x;
			newHeight = swtControl.getSize().y;
		}
		
		/* OnCanResize event */
		IusCLSize newSize = new IusCLSize(newWidth, newHeight);
		
		Boolean canResize = true;
		if (IusCLEvent.isDefinedEvent(onCanResize) && (isInDesignMode == false)) {
			
			canResize = onCanResize.invoke(IusCLControl.this, newSize);
		}
		if (canResize == false) {
			
			return availableClientRect;
		}
		newWidth = newSize.getWidth();
		newHeight = newSize.getHeight();

		
		/* OnConstrainedResize event */
		IusCLSizeConstraints newSizeConstraints = new IusCLSizeConstraints();
		newSizeConstraints.setMaxHeight(constraints.getMaxHeight());
		newSizeConstraints.setMaxWidth(constraints.getMaxWidth());
		newSizeConstraints.setMinHeight(constraints.getMinHeight());
		newSizeConstraints.setMinWidth(constraints.getMinWidth());
		
		if (IusCLEvent.isDefinedEvent(onConstrainedResize) && (isInDesignMode == false)) {
			
			onConstrainedResize.invoke(IusCLControl.this, newSizeConstraints);
		}

		Integer minWidth = newSizeConstraints.getMinWidth(); 
		Integer maxWidth = newSizeConstraints.getMaxWidth();
		Integer minHeight = newSizeConstraints.getMinHeight(); 
		Integer maxHeight = newSizeConstraints.getMaxHeight();

		
		Integer newAvailableClientLeft = availableClientRect.getLeft();
		Integer newAvailableClientTop = availableClientRect.getTop();
		Integer newAvailableClientWidth = availableClientRect.getWidth();
		Integer newAvailableClientHeight = availableClientRect.getHeight();

		/* Compensations */
		Integer originalLeft = originalPos.getX();
		Integer originalTop = originalPos.getY();
		
		Integer newCompensateLeft = compensatePos.getWidth();
		Integer newCompensateTop = compensatePos.getHeight();

		Integer newCompensateWidth = compensateSize.getWidth();
		Integer newCompensateHeight = compensateSize.getHeight();
		
		Boolean anchorLeft = anchors.getLeft();
		Boolean anchorTop = anchors.getTop();
		Boolean anchorRight = anchors.getRight();
		Boolean anchorBottom = anchors.getBottom();
		
		Integer parentDeltaWidth = parentDelta.getWidth();
		Integer parentDeltaHeight = parentDelta.getHeight();
		
		IusCLContainerControl parentContainer = (IusCLContainerControl)this.getParent();
		
		Integer containerClientWidth = parentContainer.getContainerClientWidth();
		Integer containerClientHeight = parentContainer.getContainerClientHeight();

		
		switch (align) {
		case alNone:
			/* Horizontal */
			if (anchorLeft == false) {
				if (anchorRight == false) {					
					newCompensateLeft = newCompensateLeft + parentDeltaWidth;
					
					int originalParentWidth = containerClientWidth - newCompensateLeft;
					
					newLeft = originalLeft + (int)(width / 2);
					newLeft = (int)((newLeft * containerClientWidth) / originalParentWidth);
					newLeft = newLeft - (int)(width / 2);
				}
				else {
					newLeft = left + parentDeltaWidth;
				}
			}
			else {
				if (anchorRight == true) {

					newWidth = width + newCompensateWidth + parentDeltaWidth;
					newCompensateWidth = doCompensate(newWidth, minWidth, maxWidth);
					newWidth = doConstraint(newWidth, minWidth, maxWidth);
				}
			}
			
			/* Vertical */
			if (anchorTop == false) {
				if (anchorBottom == false) {					
					newCompensateTop = newCompensateTop + parentDeltaHeight;
					
					int originalParentHeight = containerClientHeight - newCompensateTop;
					
					newTop = originalTop + (int)(height / 2);
					newTop = (int)((newTop * containerClientHeight) / originalParentHeight);
					newTop = newTop - (int)(height / 2);
				}
				else {
					newTop = top + parentDeltaHeight;
				}
			}
			else {
				if (anchorBottom == true) {

					newHeight = height + newCompensateHeight + parentDeltaHeight;
					newCompensateHeight = doCompensate(newHeight, minHeight, maxHeight);
					newHeight = doConstraint(newHeight, minHeight, maxHeight);
				}
			}

			break;
		case alLeft:

			newLeft = newAvailableClientLeft;
			newTop = newAvailableClientTop;
			newHeight = newAvailableClientHeight;
			
			if (anchorRight == true) {

				newWidth = width + newCompensateWidth + parentDeltaWidth;
				newCompensateWidth = doCompensate(newWidth, minWidth, maxWidth);
			}
			newWidth = doConstraint(newWidth, minWidth, maxWidth);
			
			newAvailableClientLeft = newAvailableClientLeft + newWidth;
			newAvailableClientWidth = newAvailableClientWidth - newWidth;
			
			break;
		case alRight:
			
			if (anchorLeft == true) {

				newWidth = width + newCompensateWidth + parentDeltaWidth;
				newCompensateWidth = doCompensate(newWidth, minWidth, maxWidth);
			}
			newWidth = doConstraint(newWidth, minWidth, maxWidth);
			
			newLeft = newAvailableClientLeft + newAvailableClientWidth - newWidth;
			newTop = newAvailableClientTop;
			newHeight = newAvailableClientHeight;

			newAvailableClientWidth = newAvailableClientWidth - newWidth;
			
			break;
		case alTop:
			
			newTop = newAvailableClientTop;
			newLeft = newAvailableClientLeft;
			newWidth = newAvailableClientWidth;
			
			if (anchorBottom == true) {

				newHeight = height + newCompensateHeight + parentDeltaHeight;
				newCompensateHeight = doCompensate(newHeight, minHeight, maxHeight);
			}
			newHeight = doConstraint(newHeight, minHeight, maxHeight);
			
			newAvailableClientTop = newAvailableClientTop + newHeight;
			newAvailableClientHeight = newAvailableClientHeight - newHeight;

			break;
		case alBottom:
			
			if (anchorTop == true) {

				newHeight = height + newCompensateHeight + parentDeltaHeight;
				newCompensateHeight = doCompensate(newHeight, minHeight, maxHeight);
			}
			newHeight = doConstraint(newHeight, minHeight, maxHeight);
			
			newTop = newAvailableClientTop + newAvailableClientHeight - newHeight;
			newLeft = newAvailableClientLeft;
			newWidth = newAvailableClientWidth;

			newAvailableClientHeight = newAvailableClientHeight - newHeight;
			
			break;
		case alClient:
			
			newLeft = newAvailableClientLeft;
			newTop = newAvailableClientTop;
			newWidth = newAvailableClientWidth;
			newHeight = newAvailableClientHeight;

			break;
		case alCustom:
			/* ? */
			break;
		default:
			break;
		}
		
		updateBounds(newLeft, newTop, newWidth, newHeight);

		/* OnResize event */
		if (IusCLEvent.isDefinedEvent(onResize) && (isInDesignMode == false)) {
			
			onResize.invoke(this);
		}

		originalPos.setXY(originalLeft, originalTop);
		compensatePos.setWidthHeight(newCompensateLeft, newCompensateTop);
		compensateSize.setWidthHeight(newCompensateWidth, newCompensateHeight);
		IusCLRectangle newAvailableClientRect = new IusCLRectangle(
				newAvailableClientLeft, newAvailableClientTop,
				newAvailableClientWidth, newAvailableClientHeight);
		
		return newAvailableClientRect;
	}

	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/* **************************************************************************************************** */
	public String getText() {
		if (IusCLStrUtils.isNotNullNotEmpty(text)) {
			return text;
		}
		return getName();
	}

	public void setText(String text) {
		this.text = text;
	}

	public IusCLAnchors getAnchors() {
		return anchors;
	}

	public void setAnchors(IusCLAnchors anchors) {
		this.anchors = anchors;
	}
	
	public IusCLCanResizeEvent getOnCanResize() {
		return onCanResize;
	}

	public void setOnCanResize(IusCLCanResizeEvent onCanResize) {
		this.onCanResize = onCanResize;
	}

	public IusCLConstrainedResizeEvent getOnConstrainedResize() {
		return onConstrainedResize;
	}

	public void setOnConstrainedResize(IusCLConstrainedResizeEvent onConstrainedResize) {
		this.onConstrainedResize = onConstrainedResize;
	}

	public IusCLNotifyEvent getOnResize() {
		return onResize;
	}

	public void setOnResize(IusCLNotifyEvent onResize) {
		this.onResize = onResize;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	/* **************************************************************************************************** */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;

		if (swtControl != null) {
			swtControl.setEnabled(enabled);
		}
	}

	public IusCLMouseEvent getOnMouseUp() {
		return onMouseUp;
	}

	/* **************************************************************************************************** */
	public void setOnMouseUp(IusCLMouseEvent onMouseUp) {
		this.onMouseUp = onMouseUp;
		
		putMouseListener();
	}

	public IusCLMouseEvent getOnMouseDown() {
		return onMouseDown;
	}

	/* **************************************************************************************************** */
	public void setOnMouseDown(IusCLMouseEvent onMouseDown) {
		this.onMouseDown = onMouseDown;
		
		putMouseListener();
	}

	public IusCLContextPopupEvent getOnContextPopup() {
		return onContextPopup;
	}

	/* **************************************************************************************************** */
	public void setOnContextPopup(IusCLContextPopupEvent onContextPopup) {
		this.onContextPopup = onContextPopup;
		
		swtControl.removeMenuDetectListener(swtMenuDetectListener);
		if (this.onContextPopup != null) {
			
			swtControl.addMenuDetectListener(swtMenuDetectListener);
		}
	}

	public Boolean getVisible() {
		return visible;
	}

	/* **************************************************************************************************** */
	public void setVisible(Boolean visible) {
		this.visible = visible;
		
		if (swtControl != null) {
			swtControl.setVisible(visible);
		}
	}

	public IusCLParentControl getParent() {
		return parent;
	}

	/* **************************************************************************************************** */
	public void setParent(IusCLParentControl parentControl) {
		
		Boolean hadParent = false;
				
		if (parent != null) {
			
			parent.getControls().remove(this);
			doParentAlignControls();
			
			hadParent = true;
			if (this instanceof IusCLWinControl) {
				
				((IusCLWinControl)this).removeFromParentTabOrder();
			}
		}
		
		parent = parentControl;
		
		if (parent != null) {
			
			parent.getControls().add(this);
			
			/* SWT */
			if (parent instanceof IusCLContainerControl) {
				
				Composite swtParentComposite = ((IusCLContainerControl)parent).getSwtComposite();
				
				swtControl.setParent(swtParentComposite);
				swtControl.moveAbove(null);
			}
			doParentAlignControls();

			if (hadParent == true) {
				
				if (this instanceof IusCLWinControl) {
					/* Last */
					((IusCLWinControl)this).setTabOrder(-1);
				}
			}
		}

		this.setParentColor(parentColor);
		this.setParentShowHint(parentShowHint);
		this.setParentFont(parentFont);
	}

	/* **************************************************************************************************** */
	public void makeEmbedded(IusCLControl embeddingControl) {

		setIsEmbedded(true);
		swtControl.setData(embeddingControl);
	}
	
	/* **************************************************************************************************** */
	public void transferSwtListeners(final IusCLControl transferControl) {
		
		transferSwtListeners(swtControl, transferControl.getSwtControl());
	}

	public IusCLNotifyEvent getOnDoubleClick() {
		return onDoubleClick;
	}

	/* **************************************************************************************************** */
	public void setOnDoubleClick(IusCLNotifyEvent onDoubleClick) {
		this.onDoubleClick = onDoubleClick;
		
		putMouseListener();
	}
	
	public IusCLMouseWheelUpDownEvent getOnMouseWheelDown() {
		return onMouseWheelDown;
	}

	/* **************************************************************************************************** */
	public void setOnMouseWheelDown(IusCLMouseWheelUpDownEvent onMouseWheelDown) {
		this.onMouseWheelDown = onMouseWheelDown;
		
		putMouseListener();
	}

	public IusCLMouseWheelUpDownEvent getOnMouseWheelUp() {
		return onMouseWheelUp;
	}

	/* **************************************************************************************************** */
	public void setOnMouseWheelUp(IusCLMouseWheelUpDownEvent onMouseWheelUp) {
		this.onMouseWheelUp = onMouseWheelUp;
		
		putMouseListener();
	}

	public IusCLNotifyEvent getOnMouseEnter() {
		return onMouseEnter;
	}

	/* **************************************************************************************************** */
	public void setOnMouseEnter(IusCLNotifyEvent onMouseEnter) {
		this.onMouseEnter = onMouseEnter;
		
		putMouseTrackListener();
	}

	public IusCLNotifyEvent getOnMouseExit() {
		return onMouseExit;
	}

	/* **************************************************************************************************** */
	public void setOnMouseExit(IusCLNotifyEvent onMouseExit) {
		this.onMouseExit = onMouseExit;
		
		putMouseTrackListener();
	}

	public IusCLMouseMoveEvent getOnMouseMove() {
		return onMouseMove;
	}

	/* **************************************************************************************************** */
	public void setOnMouseMove(IusCLMouseMoveEvent onMouseMove) {
		this.onMouseMove = onMouseMove;
		
		swtControl.removeMouseMoveListener(swtMouseMoveListener);
		if (this.onMouseMove != null) {
			
			swtControl.addMouseMoveListener(swtMouseMoveListener);
		}
	}

	public IusCLMouseHoverEvent getOnMouseHover() {
		return onMouseHover;
	}

	/* **************************************************************************************************** */
	public void setOnMouseHover(IusCLMouseHoverEvent onMouseHover) {
		this.onMouseHover = onMouseHover;
		
		putMouseTrackListener();
	}

	public IusCLMouseWheelEvent getOnMouseWheel() {
		return onMouseWheel;
	}

	/* **************************************************************************************************** */
	public void setOnMouseWheel(IusCLMouseWheelEvent onMouseWheel) {
		this.onMouseWheel = onMouseWheel;

		swtControl.removeMouseWheelListener(swtMouseWheelListener);
		if (this.onMouseWheel != null) {
			
			swtControl.addMouseWheelListener(swtMouseWheelListener);
		}
	}

	protected Boolean getAutoSize() {
		return autoSize;
	}

	/* **************************************************************************************************** */
	protected void setAutoSize(Boolean autoSize) {
		
		if (this.autoSize != autoSize) {
			this.autoSize = autoSize;

			if (this.autoSize == true) {

				doParentAlignControls();
			}
		}
	}

	/* **************************************************************************************************** */
	protected void doAutoSizeParentAlignControls() {

		if (autoSize == true) {
			
			doParentAlignControls();
		}
	}

	public IusCLAlign getAlign() {
		return align;
	}

	/* **************************************************************************************************** */
	public void setAlign(IusCLAlign align) {
		this.align = align;
		
		doParentAlignControls();
	}

	public IusCLSizeConstraints getConstraints() {
		return constraints;
	}

	public void setConstraints(IusCLSizeConstraints constraints) {
		this.constraints = constraints;
	}

	public String getCursor() {
		return cursor;
	}

	/* **************************************************************************************************** */
	public void setCursor(String cursor) {
		this.cursor = cursor;
		
		if (swtControl != null) {
			swtControl.setCursor(IusCLCursor.getAsSwtCursor(cursor));
		}
	}

	/* **************************************************************************************************** */
	public IusCLPredefinedCursors getAsPredefinedCursor() {
		
		if (cursor != null) {
			
			IusCLPredefinedCursors predefinedCursor = null;
			
			try {
				
				predefinedCursor = IusCLPredefinedCursors.valueOf(cursor);
			}
			catch (Exception exception) {
				/*  */
			}
			
			if (predefinedCursor != null) {
				
				return predefinedCursor;
			}
		}
		
		return null;
	}

	/* **************************************************************************************************** */
	public void setPredefinedCursor(IusCLPredefinedCursors predefinedCursor) {
		
		setCursor(predefinedCursor.name());
	}

	/* Font  */
	public IusCLFont getFont() {
		return font;
	}

	public Boolean getParentFont() {
		return parentFont;
	}

	/* **************************************************************************************************** */
	public void setFont(IusCLFont font) {
		
		//this.font = font;
		this.font.setNotify(null, null);
		
		this.font.setSwtFont(font.getSwtFont());
		this.font.setColor(font.getColor());
		
		this.font.setNotify(this, "setFont");

		if (parent != null) {
			
			this.parentFont = false;
		}
		putFont();
	}

	/* **************************************************************************************************** */
	public void setParentFont(Boolean parentFont) {
		
		this.parentFont = parentFont;

		if (parent != null) {
			
			if (this.parentFont == true) {
				
				//this.getProperty("Font").setDefaultValue(parent.getShowHint().toString());
				IusCLFont.putFontPropertiesDefaultValues(this, "Font", parent.getFont());
				setFont(parent.getFont());
				this.parentFont = true;
			}
		}
	}

	/* **************************************************************************************************** */
	private void putFont() {
		
		if (swtControl != null) {
			
			swtControl.setFont(font.getSwtFont());
			swtControl.setForeground(font.getColor().getAsSwtColor());
		}
	}

	/* Hint */
	public Boolean getParentShowHint() {
		return parentShowHint;
	}

	public Boolean getShowHint() {
		return showHint;
	}

	public String getHint() {
		return hint;
	}

	/* **************************************************************************************************** */
	public void setParentShowHint(Boolean parentShowHint) {
		
		this.parentShowHint = parentShowHint;

		if (parent != null) {
			
			if (this.parentShowHint == true) {
				
				this.getProperty("ShowHint").setDefaultValue(parent.getShowHint().toString());
				setShowHint(parent.getShowHint());
				this.parentShowHint = true;
			}
		}
	}

	/* **************************************************************************************************** */
	public void setShowHint(Boolean showHint) {
		this.showHint = showHint;

		if (parent != null) {
			this.parentShowHint = false;
		}
		putHint();
	}

	/* **************************************************************************************************** */
	public void setHint(String hint) {
		this.hint = hint;
		
		putHint();
	}

	/* **************************************************************************************************** */
	private void putHint() {
		
		if (swtControl != null) {
			if (showHint == true) {
				swtControl.setToolTipText(hint);	
			}
			else {
				swtControl.setToolTipText(null);	
			}
		}
	}

	/* Color */
	public IusCLColor getColor() {
		return color;
	}

	public Boolean getParentColor() {
		return parentColor;
	}

	/* **************************************************************************************************** */
	public void setColor(IusCLColor color) {
		this.color = color;

		if (parent != null) {
			this.parentColor = false;	
		}
		putColor(this.color);
	}

	/* **************************************************************************************************** */
	public void setParentColor(Boolean parentColor) {
		
		this.parentColor = parentColor;

		if (parent != null) {
			
			if (this.parentColor == true) {
				
				this.getProperty("Color").setDefaultValue(parent.getColor().getAsString());
				setColor(parent.getColor());
				this.parentColor = true;
				if (IusCLScreen.getIsThemed() == true) {
					
					putColor(null);
				}
			}
			else {
				if (IusCLStrUtils.equalValues(color.getAsString(), getProperty("Color").getDefaultValue())) {
					putColor(color);
				}
			}
		}
	}

	/* **************************************************************************************************** */
	private void putColor(IusCLColor color) {
		
		if (swtControl != null) {
			if (color == null) {
				swtControl.setBackground(null);	
			}
			else {
				swtControl.setBackground(color.getAsSwtColor());	
			}
		}
	}

	public IusCLPopupMenu getPopupMenu() {
		return popupMenu;
	}

	/* **************************************************************************************************** */
	public void setPopupMenu(IusCLPopupMenu popupMenu) {
		this.popupMenu = popupMenu;

		if (swtControl != null) {
			
			swtControl.setMenu(null);

			if (popupMenu == null) {
				
				return;
			}
			
			if (popupMenu.getSwtMenu() != null) {
				
				Shell swtShell = IusCLControl.this.findForm().getSwtShell();

				if (popupMenu.getSwtMenu().getShell() != swtShell) {

					Menu swtPopupMenu = new Menu(swtShell, SWT.POP_UP);
					popupMenu.setSwtMenu(swtPopupMenu);
					
					for (int index = 0; index < popupMenu.getItemCount(); index++) {
						
						IusCLMenuItem popupMenuItem = popupMenu.getItem(index);
						popupMenuItem.setParentMenu(popupMenu);
					}
				}
				
				swtControl.setMenu(popupMenu.getSwtMenu());
				
//				try {
//					
//					swtControl.setMenu(popupMenu.getSwtMenu());
//				} 
//				catch (Exception exception) {
//					
//					IusCLLog.logError("Pop-up menu");
//					/* Won't work in design */
//				}
			}
		}
	}

	/* **************************************************************************************************** */
	protected Control createSwtControl() {
		
		return null;
	}

	/* **************************************************************************************************** */
	protected void createWnd(Control swtControl) {
		
		this.setSwtControl(swtControl);
		create();	
		assign();
		reCreateWnd();
	}

	/* **************************************************************************************************** */
	public void reCreateWnd() {

		if (this.getIsLoading()) {
			/* recreate at the end of loading */
			return;
		}

		Control newSwtControl = createSwtControl();
		
		if (newSwtControl == null) {
			/* No SWT constructor need to call again for this IusCLControl */
			return;
		}
		
		Control oldSwtControl = this.swtControl;
		
		Composite swtParentComposite = this.swtControl.getParent();
		//this.swtControl.dispose();
		
		Boolean parentColorOld = parentColor;
		Boolean parentShowHintOld = parentShowHint;
		Boolean parentFontOld = parentFont;
		
		this.setSwtControl(newSwtControl);
		assign();

		this.setParentColor(parentColorOld);
		this.setParentShowHint(parentShowHintOld);
		this.setParentFont(parentFontOld);

		/* SWT */
		
		/* Parent */
		IusCLParentControl parentControl = this.getParent();
		if (parentControl != null) {

			ArrayList<IusCLControl> siblingControls = this.getParent().getControls();
			
			newSwtControl.setParent(swtParentComposite);
			int index = siblingControls.indexOf(this);
			
			if (index > 0) {
				
				Control swtPrevControl = siblingControls.get(index - 1).getSwtControl();
				newSwtControl.moveAbove(swtPrevControl);
			}
			if (index < siblingControls.size() - 1) {
				
				Control swtNextControl = siblingControls.get(index + 1).getSwtControl();
				newSwtControl.moveBelow(swtNextControl);
			}
			
			if (parentControl instanceof IusCLContainerControl) {
			
				IusCLContainerControl containerControl = (IusCLContainerControl)parentControl;
				containerControl.doUpdateTabOrder();
			}
		}
		
		if (this instanceof IusCLContainerControl) {
			
			IusCLContainerControl containerControl = (IusCLContainerControl)this;
			for (int index = 0; index < containerControl.getControls().size(); index++) {
				
				containerControl.getControls().get(index).getSwtControl().setParent(containerControl.getSwtComposite());
				containerControl.getControls().get(index).getSwtControl().moveAbove(null);
			}
		}
		
		if (IusCLApplication.getIsRunning() == true) {
			
			if (IusCLControl.this.getVisible() == true) {
				
				setVisible(true);
			}
		}
		
		oldSwtControl.setMenu(null);
		
		/* Pop-up menus, change the parent shell */
		if (IusCLControl.this instanceof IusCLForm) {

			IusCLForm form = (IusCLForm)this;
			for (int index = 0; index < form.getComponents().size(); index++) {

				IusCLComponent ownedComponent = form.getComponents().get(index);
				if (ownedComponent instanceof IusCLControl) {
					
					IusCLControl ownedControl = (IusCLControl)ownedComponent;
					if (ownedControl.getPopupMenu() != null) {
						
						ownedControl.setPopupMenu(ownedControl.getPopupMenu());
					}
				}
			}
		}
		
		oldSwtControl.dispose();
		oldSwtControl = null;
	}

	/* **************************************************************************************************** */
	protected void create() {
		/* Listeners applied to control once */

		Point defaultSize = swtControl.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		this.getProperty("Height").setDefaultValue(Integer.toString(defaultSize.y));
		setHeight(defaultSize.y);
		this.getProperty("Width").setDefaultValue(Integer.toString(defaultSize.x));
		setWidth(defaultSize.x);
		
		/* Selection */
		swtMenuDetectListener = new MenuDetectListener() {
			/* **************************************************************************************************** */
			@Override
			public void menuDetected(MenuDetectEvent swtMenuDetectEvent) {
				
				if (IusCLEvent.isDefinedEvent(onContextPopup)) {

					Boolean handled = onContextPopup.invoke(IusCLControl.this, swtMenuDetectEvent.x, swtMenuDetectEvent.y);
					if (handled) {
						
						swtMenuDetectEvent.doit = false;
					}
				}
			}
		};
		
		/* Mouse */
		swtMouseListener = new MouseListener() {
			/* **************************************************************************************************** */
			@Override
			public void mouseUp(MouseEvent swtMouseEvent) {

				mouseUpDown(swtMouseEvent, onMouseWheelUp, onMouseUp);
			}
			/* **************************************************************************************************** */
			@Override
			public void mouseDown(MouseEvent swtMouseEvent) {

				mouseUpDown(swtMouseEvent, onMouseWheelDown, onMouseDown);
			}
			/* **************************************************************************************************** */
			@Override
			public void mouseDoubleClick(MouseEvent swtMouseEvent) {

				if (IusCLEvent.isDefinedEvent(onDoubleClick)) {

					onDoubleClick.invoke(IusCLControl.this);
				}
			}
		};

		swtMouseTrackListener = new MouseTrackListener() {
			/* **************************************************************************************************** */
			@Override
			public void mouseHover(MouseEvent swtMouseEvent) {

				if (IusCLEvent.isDefinedEvent(onMouseHover)) {

					onMouseHover.invoke(IusCLControl.this, swtMouseEvent.x, swtMouseEvent.y);
				}
			}
			/* **************************************************************************************************** */
			@Override
			public void mouseExit(MouseEvent swtMouseEvent) {
				
				if (IusCLEvent.isDefinedEvent(onMouseExit)) {

					onMouseExit.invoke(IusCLControl.this);
				}
			}
			/* **************************************************************************************************** */
			@Override
			public void mouseEnter(MouseEvent swtMouseEvent) {

				if (IusCLEvent.isDefinedEvent(onMouseEnter)) {

					onMouseEnter.invoke(IusCLControl.this);
				}
			}
		};

		swtMouseMoveListener = new MouseMoveListener() {
			/* **************************************************************************************************** */
			@Override
			public void mouseMove(MouseEvent swtMouseEvent) {

				if (IusCLEvent.isDefinedEvent(onMouseMove)) {

					EnumSet<IusCLShiftState> shift = findShiftStateFromSwtMouseEvent(swtMouseEvent);
					onMouseMove.invoke(IusCLControl.this, shift, swtMouseEvent.x, swtMouseEvent.y);
				}
			}
		};

		swtMouseWheelListener = new MouseWheelListener() {
			/* **************************************************************************************************** */
			@Override
			public void mouseScrolled(MouseEvent swtMouseEvent) {

				if (IusCLEvent.isDefinedEvent(onMouseWheel)) {

					EnumSet<IusCLShiftState> shift = findShiftStateFromSwtMouseEvent(swtMouseEvent);
					/* Clockwise */
					Integer wheelDelta = swtMouseEvent.count;
					onMouseWheel.invoke(IusCLControl.this, shift, wheelDelta, swtMouseEvent.x, swtMouseEvent.y);
				}
			}
		};
	}

	/* **************************************************************************************************** */
	protected IusCLMouseButton findMouseButtonFromMouseEvent(MouseEvent mouseEvent) {
		
		IusCLMouseButton mouseButton = null;
		
		switch (mouseEvent.button) {
		case 1:
			mouseButton = IusCLMouseButton.mbLeft;
			break;
		case 2:
			mouseButton = IusCLMouseButton.mbMiddle;
			break;
		case 3:
			mouseButton = IusCLMouseButton.mbRight;
			break;
		}
		return mouseButton;
	}
	
	/* **************************************************************************************************** */
	protected EnumSet<IusCLShiftState> findShiftStateFromSwtMouseEvent(MouseEvent swtMouseEvent) {

		return findShiftStateFromSwtStateMask(swtMouseEvent.stateMask);
	}
	
	/* **************************************************************************************************** */
	protected EnumSet<IusCLShiftState> findShiftStateFromSwtStateMask(int stateMask) {

		EnumSet<IusCLShiftState> shiftState = EnumSet.noneOf(IusCLShiftState.class);

		if ((stateMask & SWT.MODIFIER_MASK) != 0) {
			
			if ((stateMask & SWT.SHIFT) != 0) {
				
				shiftState.add(IusCLShiftState.ssShift);
			}		
			if ((stateMask & SWT.ALT) != 0) {
				
				shiftState.add(IusCLShiftState.ssAlt);
			}
			if ((stateMask & SWT.CTRL) != 0) {
				
				shiftState.add(IusCLShiftState.ssCtrl);
			}
			if ((stateMask & SWT.COMMAND) != 0) {
				
				shiftState.add(IusCLShiftState.ssShift);
			}
			if (((stateMask & SWT.CTRL) != 0) && ((stateMask & SWT.ALT) != 0) &&
					((stateMask & SWT.SHIFT) == 0) && ((stateMask & SWT.COMMAND) == 0)) {
				
				shiftState.add(IusCLShiftState.ssAltGr);
			}
		}
		
		if ((stateMask & SWT.BUTTON_MASK) != 0) {
			
			if ((stateMask & SWT.BUTTON1) != 0) {
				
				shiftState.add(IusCLShiftState.ssMouseLeft);
			}		
			if ((stateMask & SWT.BUTTON2) != 0) {
				
				shiftState.add(IusCLShiftState.ssMouseMiddle);
			}
			if ((stateMask & SWT.BUTTON3) != 0) {
				
				shiftState.add(IusCLShiftState.ssMouseRight);
			}
		}
		
		return shiftState;
	}

	/* **************************************************************************************************** */
	protected static void transferSwtListeners(Control swtSourceControl, final Control swtDestinationControl) {

		Integer[] eventTypes = new Integer[] { SWT.Selection, SWT.MenuDetect,
				SWT.MouseDown, SWT.MouseUp, SWT.MouseMove, SWT.MouseHover, SWT.MouseDoubleClick, 
				SWT.MouseEnter, SWT.MouseExit, SWT.MouseWheel,
				SWT.KeyDown, SWT.KeyUp };
		
		for (int index = 0; index < eventTypes.length; index++) {
			
			transferSwtListener(eventTypes[index], swtSourceControl, swtDestinationControl);
		}
	}
	
	/* **************************************************************************************************** */
	private static void transferSwtListener(final int eventType, 
			Control swtSourceControl, final Control swtDestinationControl) {
		
		swtSourceControl.addListener(eventType, new Listener() {
			/* **************************************************************************************************** */
			@Override
			public void handleEvent(Event event) {
				
				swtDestinationControl.notifyListeners(eventType, event);
			}
		});
	}

}
