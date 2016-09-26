/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.iuscl.classes.IusCLComponent;
import org.iuscl.controls.IusCLWinControl;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLTreeViewCollapsedEvent;
import org.iuscl.events.IusCLTreeViewExpandedEvent;

/* **************************************************************************************************** */
public class IusCLTreeView extends IusCLWinControl {

	/* SWT */
	private Tree swtTree = null;

	/* Properties */
	private IusCLBorderStyle borderStyle = IusCLBorderStyle.bsSingle;
	private IusCLImageList images = null;
	private Boolean showLines = false;
	private Boolean multiSelect = false;
	private Boolean rowSelect = false;
	
	/* Events */
	private IusCLTreeViewExpandedEvent onExpanded = null;
	private IusCLTreeViewCollapsedEvent onCollapsed = null;
	
	/* Fields */
	private IusCLTreeNodes rootTreeNodes = new IusCLTreeNodes(this); 

	/* **************************************************************************************************** */
	public IusCLTreeView(IusCLComponent aOwner) {
		super(aOwner);

		/* Properties */
		defineProperty("BorderStyle", IusCLPropertyType.ptEnum, "bsSingle", IusCLBorderStyle.bsSingle);
		defineProperty("Images", IusCLPropertyType.ptComponent, "", IusCLImageList.class);
		defineProperty("ShowLines", IusCLPropertyType.ptBoolean, "false");
		defineProperty("MultiSelect", IusCLPropertyType.ptBoolean, "false");
		defineProperty("RowSelect", IusCLPropertyType.ptBoolean, "false");
		
		/* Events */
		defineProperty("OnExpanded", IusCLPropertyType.ptEvent, null, IusCLTreeViewExpandedEvent.class);
		defineProperty("OnCollapsed", IusCLPropertyType.ptEvent, null, IusCLTreeViewCollapsedEvent.class);
		
		/* Fields */

		/* Create */
		createWnd(createSwtControl());
	}

	/* **************************************************************************************************** */
	@Override
	protected Control createSwtControl() {

		int swtCreateParams = SWT.NONE;
		
		if (borderStyle == IusCLBorderStyle.bsSingle) {
			
			swtCreateParams = swtCreateParams | SWT.BORDER;
		}
		
		if (multiSelect == true) {
			
			swtCreateParams = swtCreateParams | SWT.MULTI;
		}
		else {
			
			swtCreateParams = swtCreateParams | SWT.SINGLE;
		}

		if (rowSelect == true) {
			
			swtCreateParams = swtCreateParams | SWT.FULL_SELECTION;
		}

		swtTree = new Tree(this.getFormSwtComposite(), swtCreateParams);

		swtTree.addTreeListener(new TreeListener() {
			/* **************************************************************************************************** */
			@Override
			public void treeExpanded(TreeEvent swtTreeEvent) {
				
				TreeItem swtTreeItem = (TreeItem)swtTreeEvent.item;
				expand((IusCLTreeNode)swtTreeItem.getData());
			}
			/* **************************************************************************************************** */
			@Override
			public void treeCollapsed(TreeEvent swtTreeEvent) {
				
				TreeItem swtTreeItem = (TreeItem)swtTreeEvent.item;
				collapse((IusCLTreeNode)swtTreeItem.getData());
			}
		});
		
		return swtTree;
	}

	/* **************************************************************************************************** */
	@Override
	public void reCreateWnd() {

		if (this.getIsLoading()) {
			/* recreate at the end of loading */
			return;
		}

		super.reCreateWnd();
		
		/* Nodes */
		for (int index = 0; index < rootTreeNodes.getCount(); index++) {
			
			IusCLTreeNode rootTreeNode = rootTreeNodes.get(index);
			
			TreeItem swtTreeItem = new TreeItem(swtTree, SWT.NONE, index);
			rootTreeNode.setSwtTreeItem(swtTreeItem);
			rootTreeNode.assign();

			recursiveRecreateNodes(rootTreeNode.getChildNodes());
		}
	}

	/* **************************************************************************************************** */
	private void recursiveRecreateNodes(IusCLTreeNodes treeNodes) {
		
		IusCLTreeNode parentTreeNode = treeNodes.getParentNode();
		
		for (int index = 0; index < treeNodes.getCount(); index++) {
			
			IusCLTreeNode treeNode = treeNodes.get(index);
			
			TreeItem swtTreeItem = new TreeItem(parentTreeNode.getSwtTreeItem(), SWT.NONE, index);
			treeNode.setSwtTreeItem(swtTreeItem);
			treeNode.assign();

			recursiveRecreateNodes(treeNode.getChildNodes());
		}
	}

	/* **************************************************************************************************** */
	public void expand(IusCLTreeNode treeNode) {
		
		treeNode.setExpanded(true);
		
		if (IusCLEvent.isDefinedEvent(onExpanded)) {
			
			onExpanded.invoke(IusCLTreeView.this, treeNode);
		}
	}

	/* **************************************************************************************************** */
	public void collapse(IusCLTreeNode treeNode) {

		treeNode.setExpanded(false);
		
		if (IusCLEvent.isDefinedEvent(onCollapsed)) {
			
			onCollapsed.invoke(IusCLTreeView.this, treeNode);
		}
	}

	/* **************************************************************************************************** */
	@Override
	protected void create() {
		super.create();

		this.getProperty("Height").setDefaultValue("97");
		setHeight(97);
		this.getProperty("Width").setDefaultValue("121");
		setWidth(121);
	}

	/* **************************************************************************************************** */
	public ArrayList<IusCLTreeNode> getAllTreeNodes() {
		
		ArrayList<IusCLTreeNode> treeNodes = new ArrayList<IusCLTreeNode>();
		
		for (int index = 0; index < rootTreeNodes.getCount(); index++) {
			
			IusCLTreeNode rootTreeNode = rootTreeNodes.get(index);
			treeNodes.add(rootTreeNode);
			recursiveAddNodes(treeNodes, rootTreeNode);
		}
		
		return treeNodes;
	}

	/* **************************************************************************************************** */
	private void recursiveAddNodes(ArrayList<IusCLTreeNode> treeNodes, IusCLTreeNode addTreeNode) {
		
		for (int index = 0; index < addTreeNode.getChildNodes().getCount(); index++) {
			
			IusCLTreeNode childTreeNode = addTreeNode.getChildNodes().get(index);
			treeNodes.add(childTreeNode);
			recursiveAddNodes(treeNodes, childTreeNode);
		}
	}

	/* **************************************************************************************************** */
	public IusCLTreeNode getSelectedNode() {

		IusCLTreeNode selectedTreeNode = null;
		
		if (swtTree.getSelection().length > 0) {
			
			selectedTreeNode = (IusCLTreeNode)(swtTree.getSelection()[0].getData());
		}
		
		return selectedTreeNode;
	}

	/* **************************************************************************************************** */
	public ArrayList<IusCLTreeNode> getSelectedNodes() {

		ArrayList<IusCLTreeNode> selectedTreeNodes = new ArrayList<IusCLTreeNode>();
		
		for (int index = 0; index < swtTree.getSelection().length; index++) {
			
			selectedTreeNodes.add((IusCLTreeNode)(swtTree.getSelection()[index].getData()));
		}
		
		return selectedTreeNodes;
	}

	/* **************************************************************************************************** */
	public Integer getSelectionCount() {

		return swtTree.getSelectionCount();
	}

	/* **************************************************************************************************** */
	public void selectNode(IusCLTreeNode node) {

		swtTree.select(node.getSwtTreeItem());
	}

	/* **************************************************************************************************** */
	public IusCLTreeNode getFirstVisibleTreeNode() {

		return (IusCLTreeNode)(swtTree.getTopItem().getData());
	}

	/* **************************************************************************************************** */
	public void setFirstVisibleTreeNode(IusCLTreeNode treeNode) {

		swtTree.setTopItem(treeNode.getSwtTreeItem());
	}

	/* **************************************************************************************************** */
	public IusCLImageList getImages() {
		return images;
	}

	public void setImages(IusCLImageList images) {
		this.images = images;
	}

	public IusCLTreeNodes getRootTreeNodes() {
		return rootTreeNodes;
	}
	
	public IusCLBorderStyle getBorderStyle() {
		return borderStyle;
	}

	/* **************************************************************************************************** */
	public void setBorderStyle(IusCLBorderStyle borderStyle) {

		if (this.borderStyle != borderStyle) {
			
			this.borderStyle = borderStyle;
			
			reCreateWnd();		
		}
	}

	public Boolean getShowLines() {
		return showLines;
	}

	/* **************************************************************************************************** */
	public void setShowLines(Boolean showLines) {
		this.showLines = showLines;
		
		swtTree.setLinesVisible(showLines);
	}

	public Boolean getMultiSelect() {
		return multiSelect;
	}

	/* **************************************************************************************************** */
	public void setMultiSelect(Boolean multiSelect) {

		if (this.multiSelect != multiSelect) {
			
			this.multiSelect = multiSelect;
			
			reCreateWnd();		
		}
	}

	public Boolean getRowSelect() {
		return rowSelect;
	}

	/* **************************************************************************************************** */
	public void setRowSelect(Boolean rowSelect) {

		if (this.rowSelect != rowSelect) {
			
			this.rowSelect = rowSelect;			
			reCreateWnd();		
		}
	}

	public IusCLTreeViewExpandedEvent getOnExpanded() {
		return onExpanded;
	}

	public void setOnExpanded(IusCLTreeViewExpandedEvent onExpanded) {
		this.onExpanded = onExpanded;
	}

	public IusCLTreeViewCollapsedEvent getOnCollapsed() {
		return onCollapsed;
	}

	public void setOnCollapsed(IusCLTreeViewCollapsedEvent onCollapsed) {
		this.onCollapsed = onCollapsed;
	}

	
}
