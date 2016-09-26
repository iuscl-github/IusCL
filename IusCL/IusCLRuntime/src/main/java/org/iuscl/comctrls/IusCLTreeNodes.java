/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.comctrls;

import org.iuscl.classes.IusCLCollection;

/* **************************************************************************************************** */
public class IusCLTreeNodes extends IusCLCollection {

	private IusCLTreeNode parentNode = null;

	/* **************************************************************************************************** */
	public IusCLTreeNodes(IusCLTreeView treeView) {
		super(treeView);
	}

	/* **************************************************************************************************** */
	public IusCLTreeNodes(IusCLTreeNode parentTreeNode) {
		super(parentTreeNode.getTreeView());

		this.parentNode = parentTreeNode;
	}

	/* **************************************************************************************************** */
	@Override
	public IusCLTreeView getPropertyComponent() {
		
		return (IusCLTreeView)super.getPropertyComponent();
	}

	/* **************************************************************************************************** */
	public IusCLTreeView getTreeView() {

		return getPropertyComponent();
	}

	/* **************************************************************************************************** */
	@Override
	public Class<?> getItemClass() {
		
		return IusCLTreeNode.class;
	}

	/* **************************************************************************************************** */
	@Override
	public IusCLTreeNode add() {
		
		IusCLTreeNode treeNode = new IusCLTreeNode(this);
		return treeNode;
	}

	/* **************************************************************************************************** */
	@Override
	public IusCLTreeNode insert(int index) {

		IusCLTreeNode treeNode = new IusCLTreeNode(this, index);
		return treeNode;
	}

	/* **************************************************************************************************** */
	@Override
	public IusCLTreeNode get(int index) {
		
		return (IusCLTreeNode)super.get(index);
	}

	/* **************************************************************************************************** */
	public Integer indexOf(IusCLTreeNode treeNode) {
		
		return super.indexOf(treeNode);
	}

	public IusCLTreeNode getParentNode() {
		return parentNode;
	}

}
