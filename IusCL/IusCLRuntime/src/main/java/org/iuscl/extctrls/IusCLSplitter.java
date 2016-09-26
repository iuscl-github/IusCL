/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.extctrls;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Composite;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLControl;
import org.iuscl.controls.IusCLWinControl;
import org.iuscl.forms.IusCLCursor.IusCLPredefinedCursors;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLColor.IusCLStandardColors;

/* **************************************************************************************************** */
public class IusCLSplitter extends IusCLWinControl {

	public enum IusCLResizeStyle { rsNone, rsLine, rsUpdate, rsPattern };

	private boolean drag = false;
	private int initialMouseLeft = 0;
	private int initialMouseTop = 0;

	/* SWT */
	private Composite swtSplitter = null;
	private Composite swtSplitterTracker = null;
	
	/* Properties */
	
	/*
	 * TODO autoSnap & beveled
	 * 
	 * 
	 */
	
//	private Boolean autoSnap = false;
//	private Boolean beveled = false;
	private IusCLResizeStyle resizeStyle = IusCLResizeStyle.rsUpdate;

	private IusCLColor trackerColor = IusCLColor.getStandardColor(IusCLStandardColors.clBlack);

	/* Events */

	/* **************************************************************************************************** */
	public IusCLSplitter(IusCLComponent aOwner) {
		
		super(aOwner);

		/* Properties */
//		defineProperty("AutoSnap", IusCLPropertyType.ptBoolean, "false");
//		defineProperty("Beveled", IusCLPropertyType.ptBoolean, "false");
		defineProperty("ResizeStyle", IusCLPropertyType.ptEnum, "rsUpdate", IusCLResizeStyle.rsUpdate);
		defineProperty("TrackerColor", IusCLPropertyType.ptColor, "clBlack", IusCLStandardColors.clBlack);

		/* Events */
		
		/* Create */
		swtSplitter = new Composite(this.getFormSwtComposite(), SWT.NONE);
		createWnd(swtSplitter);
	}

	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();
		

		swtSplitter.addMouseListener(new MouseListener() {
			/* **************************************************************************************************** */
			@Override
			public void mouseUp(MouseEvent swtMouseUpMouseEvent) {
				
				if (swtSplitterTracker != null) {

					swtSplitter.setLocation(swtSplitterTracker.getLocation().x, swtSplitterTracker.getLocation().y);
					updateControls(swtSplitterTracker.getLocation().x, swtSplitterTracker.getLocation().y);
					swtSplitterTracker.dispose();
				}
				drag = false;
			}
			/* **************************************************************************************************** */
			@Override
			public void mouseDown(MouseEvent swtMouseDownMouseEvent) {
				
				if (resizeStyle == IusCLResizeStyle.rsNone) {
					return;
				}
				
				if (resizeStyle == IusCLResizeStyle.rsUpdate) {
					initialMouseLeft = swtMouseDownMouseEvent.x;
					initialMouseTop = swtMouseDownMouseEvent.y;
				}
				else {
					initialMouseLeft = swtMouseDownMouseEvent.x - swtSplitter.getLocation().x;
					initialMouseTop = swtMouseDownMouseEvent.y - swtSplitter.getLocation().y;
				}

				if ((resizeStyle == IusCLResizeStyle.rsLine) || (resizeStyle == IusCLResizeStyle.rsPattern)) {
					
					swtSplitterTracker = new Composite(swtSplitter.getParent(), SWT.NONE);
					swtSplitterTracker.setBackground(IusCLSplitter.this.trackerColor.getAsSwtColor());
					swtSplitterTracker.moveAbove(null);
					swtSplitterTracker.setBounds(swtSplitter.getBounds());
					
					if (resizeStyle == IusCLResizeStyle.rsPattern) {
						Region trackerRegion = new Region();
						for (int indexX = 0; indexX < swtSplitterTracker.getSize().x / 2 + 1; indexX++) {
							for (int indexY = 0; indexY < swtSplitterTracker.getSize().y; indexY++) {
								if (indexY % 2 == 0) {
									trackerRegion.add(indexX * 2, indexY, 1, 1);
								}
								else {
									trackerRegion.add(indexX * 2 + 1, indexY, 1, 1);
								}
							}
						}
						swtSplitterTracker.setRegion(trackerRegion);
					}
				}
				drag = true;
			}
			/* **************************************************************************************************** */
			@Override
			public void mouseDoubleClick(MouseEvent swtMouseDoubleClickMouseEvent) {
				/*
				 * TODO Go to min/max?
				 * 
				 * 
				 */
			}
		});

		swtSplitter.addMouseMoveListener(new MouseMoveListener() {
			/* **************************************************************************************************** */
			@Override
			public void mouseMove(MouseEvent mouseMoveMouseEvent) {
				if (drag == true) {
					if ((IusCLSplitter.this.getAlign() == IusCLAlign.alLeft) || 
							(IusCLSplitter.this.getAlign() == IusCLAlign.alRight)) {
						/* LeftRight */
						if (resizeStyle == IusCLResizeStyle.rsUpdate) {
							swtSplitter.setLocation(swtSplitter.getLocation().x + mouseMoveMouseEvent.x - initialMouseLeft, swtSplitter.getLocation().y);
							updateControls(swtSplitter.getLocation().x, swtSplitter.getLocation().y);
						}
						else {
							swtSplitterTracker.setLocation(mouseMoveMouseEvent.x - initialMouseLeft, swtSplitterTracker.getLocation().y);
						}
					}
					else if ((IusCLSplitter.this.getAlign() == IusCLAlign.alTop) || 
							(IusCLSplitter.this.getAlign() == IusCLAlign.alBottom)) {
						/* TopBottom */
						if (resizeStyle == IusCLResizeStyle.rsUpdate) {
							swtSplitter.setLocation(swtSplitter.getLocation().x, swtSplitter.getLocation().y + mouseMoveMouseEvent.y - initialMouseTop);
							updateControls(swtSplitter.getLocation().x, swtSplitter.getLocation().y);
						}
						else {
							swtSplitterTracker.setLocation(swtSplitterTracker.getLocation().x, mouseMoveMouseEvent.y - initialMouseTop);
						}
					}
				}
			}
		});
	}

	/* **************************************************************************************************** */
	private void updateControls(int splitterX, int splitterY) {

		ArrayList<IusCLControl> parentControls = IusCLSplitter.this.getParent().getControls();
		
		int indexInParent = parentControls.indexOf(IusCLSplitter.this);

		if (indexInParent == 0) {
			return;
		}

		IusCLControl prevControl = parentControls.get(indexInParent - 1);

		if (prevControl.getAlign() != IusCLSplitter.this.getAlign()) {
			return;
		}
		
		/* Left */
		if (IusCLSplitter.this.getAlign() == IusCLAlign.alLeft) {

			prevControl.setWidth(splitterX - prevControl.getLeft());
		}

		/* Right */
		if (IusCLSplitter.this.getAlign() == IusCLAlign.alRight) {

			int newLeft = splitterX + IusCLSplitter.this.getWidth();
			int moved = newLeft - prevControl.getLeft();
			prevControl.setWidth(prevControl.getWidth() - moved);
			prevControl.setLeft(newLeft);
		}

		/* Top */
		if (IusCLSplitter.this.getAlign() == IusCLAlign.alTop) {

			prevControl.setHeight(splitterY - prevControl.getTop());
		}

		/* Bottom */
		if (IusCLSplitter.this.getAlign() == IusCLAlign.alBottom) {

			int newTop = splitterY + IusCLSplitter.this.getHeight();
			int moved = newTop - prevControl.getTop();
			prevControl.setHeight(prevControl.getHeight() - moved);
			prevControl.setTop(newTop);
		}
		
		//doParentAlignControls();
	}
	
	/* **************************************************************************************************** */
	@Override
	public void setAlign(IusCLAlign align) {

		super.setAlign(align);
		
		if ((this.getAlign() == IusCLAlign.alLeft) || (this.getAlign() == IusCLAlign.alRight)) {
			
			setCursor(IusCLPredefinedCursors.crHSplit.name());
		}
		else if ((this.getAlign() == IusCLAlign.alTop) || (this.getAlign() == IusCLAlign.alBottom)) {
			
			setCursor(IusCLPredefinedCursors.crVSplit.name());
		}
		else {
			
			setCursor(IusCLPredefinedCursors.crArrow.name());
		}
	}

	/* **************************************************************************************************** */
//	public Boolean getAutoSnap() {
//		return autoSnap;
//	}
//	
//	public void setAutoSnap(Boolean autoSnap) {
//		this.autoSnap = autoSnap;
//	}
//	
//	public Boolean getBeveled() {
//		return beveled;
//	}
//	
//	public void setBeveled(Boolean beveled) {
//		this.beveled = beveled;
//	}
	
	public IusCLResizeStyle getResizeStyle() {
		return resizeStyle;
	}
	
	public void setResizeStyle(IusCLResizeStyle resizeStyle) {
		this.resizeStyle = resizeStyle;
	}

	public IusCLColor getTrackerColor() {
		return trackerColor;
	}

	public void setTrackerColor(IusCLColor trackerColor) {
		this.trackerColor = trackerColor;
	}
	
}
