/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.iuscl.classes.IusCLCollectionItem;

/* **************************************************************************************************** */
public class IusCLTreeNode extends IusCLCollectionItem {

	/* SWT */
	private TreeItem swtTreeItem = null;

	/* Properties */
	private String caption = "";
	private Integer imageIndex = -1;
	private IusCLTreeNodes childNodes = null;
	private IusCLTreeView treeView = null;
	private Boolean expanded = false;
	
	/* Events */
	
	/* Fields */

	/* **************************************************************************************************** */
	public IusCLTreeNode(IusCLTreeNodes treeNodes) {
		
		this(treeNodes, null);
	}

	/* **************************************************************************************************** */
	public IusCLTreeNode(IusCLTreeNodes treeNodes, Integer index) {
		super(treeNodes);

		/* Properties */
		defineProperty("Caption", IusCLPropertyType.ptString, "");
		defineProperty("ImageIndex", IusCLPropertyType.ptInteger, "-1");
		defineProperty("Expanded", IusCLPropertyType.ptBoolean, "false");
		
		/* Events */
		
		/* Create */
		treeView = treeNodes.getPropertyComponent();
		childNodes = new IusCLTreeNodes(this);

		if (treeNodes.getParentNode() == null) {
			
			Tree swtTree = (Tree)treeView.getSwtControl();
			if (index == null) {
				
				swtTreeItem = new TreeItem(swtTree, SWT.NONE);
			}
			else {
				
				swtTreeItem = new TreeItem(swtTree, SWT.NONE, index);	
			}
		}
		else {
			if (index == null) {
				
				swtTreeItem = new TreeItem(treeNodes.getParentNode().getSwtTreeItem(), SWT.NONE);
			}
			else {
				
				swtTreeItem = new TreeItem(treeNodes.getParentNode().getSwtTreeItem(), SWT.NONE, index);
			}
		}
		swtTreeItem.setData(this);
		assign();
	}

	/* **************************************************************************************************** */
	public IusCLTreeNodes getParentTreeNodes() {
		
		return (IusCLTreeNodes)getCollection();
	}

	/* **************************************************************************************************** */
	@Override
	public void free() {
		super.free();
		
		if (swtTreeItem != null) {
			
			swtTreeItem.dispose();
		}
	}

	/* **************************************************************************************************** */
	public IusCLTreeNode getParentNode() {
		
		return getParentTreeNodes().getParentNode();
	}

	/* **************************************************************************************************** */
	public String getCaption() {
		return caption;
	}

	/* **************************************************************************************************** */
	public void setCaption(String caption) {
		this.caption = caption;
		
		swtTreeItem.setText(caption);
	}

	public Integer getImageIndex() {
		return imageIndex;
	}

	/* **************************************************************************************************** */
	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
		
		if (imageIndex == -1) {
			swtTreeItem.setImage((Image)null);
			return;
		}

		if (swtTreeItem != null) {
			
			IusCLImageList images = treeView.getImages();
			if (images != null) {
				Image swtImage = images.getAsSizedSwtImage(imageIndex);
				swtTreeItem.setImage(swtImage);
			}
			else {
				swtTreeItem.setImage((Image)null);
			}
		}
	}

	public IusCLTreeView getTreeView() {
		return treeView;
	}

	public TreeItem getSwtTreeItem() {
		return swtTreeItem;
	}

	public void setSwtTreeItem(TreeItem swtTreeItem) {
		this.swtTreeItem = swtTreeItem;
		
		this.swtTreeItem.setData(this);
	}

	public IusCLTreeNodes getChildNodes() {
		return childNodes;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	/* **************************************************************************************************** */
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;

		swtTreeItem.setExpanded(expanded);
	}
	
}
