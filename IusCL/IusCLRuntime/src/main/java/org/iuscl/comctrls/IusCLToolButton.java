/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.iuscl.classes.IusCLCollectionItem;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLNotifyEvent;
import org.iuscl.menus.IusCLPopupMenu;

/* **************************************************************************************************** */
public class IusCLToolButton extends IusCLCollectionItem {

	public enum IusCLToolButtonStyle { tbsButton, tbsCheck, tbsDropDown, tbsSeparator, tbsRadio }
	
	/* SWT */
	private ToolItem swtToolItem = null;

	/* Properties */
	private String caption = "";
	private Boolean enabled = true;
	private Integer imageIndex = -1;
	private Integer hotImageIndex = -1;
	private Integer disabledImageIndex = -1;
	private IusCLToolButtonStyle style = IusCLToolButtonStyle.tbsButton;
	private Boolean down = false;
	private IusCLPopupMenu dropDownMenu = null;
	
	/* Events */
	private IusCLNotifyEvent onClick = null;
	
	/* Fields */

	/* **************************************************************************************************** */
	public IusCLToolButton(IusCLToolButtons toolButtons) {
		
		this(toolButtons, null);
	}

	/* **************************************************************************************************** */
	public IusCLToolButton(IusCLToolButtons toolButtons, Integer index) {
		
		super(toolButtons);

		/* Properties */
		defineProperty("Caption", IusCLPropertyType.ptString, "");
		defineProperty("Enabled", IusCLPropertyType.ptBoolean, "true");
		defineProperty("ImageIndex", IusCLPropertyType.ptInteger, "-1");
		defineProperty("HotImageIndex", IusCLPropertyType.ptInteger, "-1");
		defineProperty("DisabledImageIndex", IusCLPropertyType.ptInteger, "-1");
		defineProperty("Style", IusCLPropertyType.ptEnum, "tbsButton", IusCLToolButtonStyle.tbsButton);
		defineProperty("Down", IusCLPropertyType.ptBoolean, "false");
		defineProperty("DropDownMenu", IusCLPropertyType.ptComponent, "", IusCLPopupMenu.class);

		/* Events */
		defineProperty("OnClick", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);
		
		/* Create */
		IusCLToolBar toolBar = toolButtons.getToolBar();
		ToolBar swtToolBar = (ToolBar)toolBar.getSwtControl();
		
		if (index == null) {

			swtToolItem = new ToolItem(swtToolBar, getCreateParams());
		}
		else {
			
			swtToolItem = new ToolItem(swtToolBar, getCreateParams(), index);	
		}
		
		reCreate();
		assign();
		toolBar.doAutoSizeParentAlignControls();
	}

	/* **************************************************************************************************** */
	private void reCreate() {

		swtToolItem.addSelectionListener(new SelectionAdapter() {
			/* **************************************************************************************************** */
			@Override
			public void widgetSelected(SelectionEvent swtSelectionEvent) {
				
				if (swtToolItem.getSelection() == true) {
					
					down = true;
				}
				else {
					
					down = false;
				}
				
				if (swtSelectionEvent.detail == SWT.ARROW) {
					
					if (dropDownMenu != null) {

						Rectangle swtRectangle = swtToolItem.getBounds();
						Point swtPoint = swtToolItem.getParent().toDisplay(
							new Point(swtRectangle.x, swtRectangle.y));
						dropDownMenu.popup(swtPoint.x, swtPoint.y + swtRectangle.height);
					}
				}
				
				if (IusCLEvent.isDefinedEvent(onClick)) {
					
					onClick.invoke(IusCLToolButton.this);
				}
			}
		});
	}

	/* **************************************************************************************************** */
	public IusCLToolButtons getToolButtons() {
		
		return (IusCLToolButtons)getCollection();
	}

	/* **************************************************************************************************** */
	@Override
	public void free() {
		super.free();
		
		if (swtToolItem != null) {
			
			swtToolItem.dispose();
		}
	}

	/* **************************************************************************************************** */
	private int getCreateParams() {
		
		int swtStyle = SWT.PUSH;
		
		switch (style) {
		case tbsCheck:
			swtStyle = SWT.CHECK;
			break;
		case tbsRadio:
			swtStyle = SWT.RADIO;
			break;
		case tbsDropDown:
			swtStyle = SWT.DROP_DOWN;
			break;
		case tbsSeparator:
			swtStyle = SWT.SEPARATOR;
			break;
		default:
			/* Intentionally left blank */
			break;
		}
		
		return swtStyle;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	/* **************************************************************************************************** */
	public void setEnabled(Boolean enabled) {
		
		this.enabled = enabled;
		swtToolItem.setEnabled(enabled);
	}

	/* **************************************************************************************************** */
	@Override
	public String getDisplayName() {

		return caption;
	}

	public String getCaption() {
		return caption;
	}

	/* **************************************************************************************************** */
	public void setCaption(String caption) {
		this.caption = caption;
		
		if (swtToolItem != null) {
			swtToolItem.setText(caption);
		}
	}

	public Integer getImageIndex() {
		return imageIndex;
	}

	/* **************************************************************************************************** */
	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
		
		if (imageIndex == -1) {
			swtToolItem.setImage(null);
			return;
		}
		
		IusCLToolButtons toolButtons = getToolButtons();
		IusCLToolBar toolBar = toolButtons.getToolBar();

		if (swtToolItem != null) {
			
			IusCLImageList images = toolBar.getImages();
			if (images != null) {
				Image swtImage = images.getAsSizedSwtImage(imageIndex);
				swtToolItem.setImage(swtImage);
			}
			else {
				swtToolItem.setImage(null);
			}
		}
		
		toolBar.doAutoSizeParentAlignControls();
	}

	public Integer getHotImageIndex() {
		return hotImageIndex;
	}

	/* **************************************************************************************************** */
	public void setHotImageIndex(Integer hotImageIndex) {
		this.hotImageIndex = hotImageIndex;
		
		if (hotImageIndex == -1) {
			swtToolItem.setHotImage(null);
			return;
		}
		
		IusCLToolButtons toolButtons = getToolButtons();
		IusCLToolBar toolBar = toolButtons.getToolBar();

		if (swtToolItem != null) {
			
			IusCLImageList hotImages = toolBar.getHotImages();
			if (hotImages != null) {
				Image swtImage = hotImages.getAsSizedSwtImage(hotImageIndex);
				swtToolItem.setHotImage(swtImage);
			}
			else {
				swtToolItem.setHotImage(null);
			}
		}
		
		toolBar.doAutoSizeParentAlignControls();
	}

	public Integer getDisabledImageIndex() {
		return disabledImageIndex;
	}

	/* **************************************************************************************************** */
	public void setDisabledImageIndex(Integer disabledImageIndex) {
		this.disabledImageIndex = disabledImageIndex;
		
		if (disabledImageIndex == -1) {
			swtToolItem.setDisabledImage(null);
			return;
		}
		
		IusCLToolButtons toolButtons = getToolButtons();
		IusCLToolBar toolBar = toolButtons.getToolBar();

		if (swtToolItem != null) {
			
			IusCLImageList disabledImages = toolBar.getDisabledImages();
			if (disabledImages != null) {
				Image swtImage = disabledImages.getAsSizedSwtImage(disabledImageIndex);
				swtToolItem.setDisabledImage(swtImage);
			}
			else {
				swtToolItem.setDisabledImage(null);
			}
		}
		
		toolBar.doAutoSizeParentAlignControls();
	}

	public ToolItem getSwtToolItem() {
		return swtToolItem;
	}

	public void setSwtToolItem(ToolItem swtToolItem) {
		this.swtToolItem = swtToolItem;
	}

	public IusCLToolButtonStyle getStyle() {
		return style;
	}

	/* **************************************************************************************************** */
	public void setStyle(IusCLToolButtonStyle style) {
		
		if (this.style != style) {

			this.style = style;

			IusCLToolButtons toolButtons = getToolButtons();
			IusCLToolBar toolBar = toolButtons.getToolBar();
			ToolBar swtToolBar = (ToolBar)toolBar.getSwtControl();
			
			int index = swtToolBar.indexOf(swtToolItem);
			swtToolItem.dispose();
			
			swtToolItem = new ToolItem(swtToolBar, getCreateParams(), index);
			
			reCreate();
			assign();
			
			toolBar.doAutoSizeParentAlignControls();
		}
	}
	
	public IusCLNotifyEvent getOnClick() {
		return onClick;
	}

	public void setOnClick(IusCLNotifyEvent onClick) {
		this.onClick = onClick;
	}

	public Boolean getDown() {
		return down;
	}

	/* **************************************************************************************************** */
	public void setDown(Boolean down) {
		this.down = down;

		swtToolItem.setSelection(down);
	}

	public IusCLPopupMenu getDropDownMenu() {
		return dropDownMenu;
	}

	/* **************************************************************************************************** */
	public void setDropDownMenu(IusCLPopupMenu dropDownMenu) {
		this.dropDownMenu = dropDownMenu;
	}

}
