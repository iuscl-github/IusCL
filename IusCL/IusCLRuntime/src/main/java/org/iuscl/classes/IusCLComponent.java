/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.classes;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.iuscl.forms.IusCLForm;

/* **************************************************************************************************** */
public class IusCLComponent extends IusCLPersistent {

	/* Fields */
	protected Boolean isInDesignMode = false;
	
	private IusCLComponent ownerComponent = null;
	private ArrayList<IusCLComponent> ownedComponents = new ArrayList<IusCLComponent>();
	
	private Object tag = null;

	/* This is used only for non-visual components, for controls is the in IusCLParentControl  */
	private IusCLComponent parentComponent = null;
	private ArrayList<IusCLComponent> childComponents = new ArrayList<IusCLComponent>();

	private IusCLCollection subCollection = null;

	/* Styles */
	private Boolean isSubComponent = false;

	private Boolean isEmbedded = false;

	/* Properties */
	private String name = null;

	/* **************************************************************************************************** */
	public IusCLComponent(IusCLComponent aOwner) {
		super();
		
		this.ownerComponent = aOwner;
		if (ownerComponent != null) {
			
			ownerComponent.getComponents().add(this);
		}

		isInDesignMode = this.findForm().getIsInDesignMode();

		/* Properties */
		defineProperty("Name", IusCLPropertyType.ptString, "defaultName");
	}

	/* **************************************************************************************************** */
	public void destroy() {
		/* To destroy in here SWT objects */
	}
	
	/* **************************************************************************************************** */
	public void free() {
		/*  To free in here */
		if (this != null) {
			
			for (int index = ownedComponents.size() - 1; index >= 0; index--) {
				
				ownedComponents.get(index).free();
			}
			
			/* Free also the child components */
			for (int index = childComponents.size() - 1; index >= 0; index--) {
				
				childComponents.get(index).free();
			}

			/* Remove from ownerComponent */
			if (this.getOwner() != null) {
				
				this.getOwner().getComponents().remove(this);
			}

			/* Remove from parentComponent */
			if (this.getParentComponent() != null) {
				
				this.getParentComponent().getChildren().remove(this);
			}

			this.destroy();
		}
	}

	/* **************************************************************************************************** */
	public IusCLComponent findOwnedComponentByName(String ownedComponentName) {
		
		for (int index = 0; index < ownedComponents.size(); index ++) {

			IusCLComponent ownedComponent = ownedComponents.get(index); 
			if (ownedComponent.getName().equalsIgnoreCase(ownedComponentName)) {
				
				return ownedComponent;
			}
		}
		
		return null;
	}

	/* **************************************************************************************************** */
	public IusCLForm findForm() {
		
		IusCLComponent component = this;
		while(!(component instanceof IusCLForm)) {
			
			component = component.getOwner();
		}
		return (IusCLForm)component;
	}

	/* **************************************************************************************************** */
	public Composite getFormSwtComposite() {
		
		return this.findForm().getSwtComposite();
	}

	/* **************************************************************************************************** */
	public void selectSubComponent(IusCLComponent subComponent) {
		/* For IusCLToolButtons, IusCLTabSheets (and maybe others) */
	}

	/* **************************************************************************************************** */
	public IusCLComponent getOwner() {
		return ownerComponent;
	}

	public ArrayList<IusCLComponent> getComponents() {
		return ownedComponents;
	}

	public IusCLComponent getParentComponent() {
		return parentComponent;
	}

	/* **************************************************************************************************** */
	public void setParentComponent(IusCLComponent parentComponent) {
		
		if (this.parentComponent != null) {
			
			if (!(this.parentComponent.equals(parentComponent))) {
				
				this.parentComponent.getChildren().remove(this);
				parentComponent.getChildren().add(this);
			}
		}
		else {
			
			parentComponent.getChildren().add(this);
		}
		
		this.parentComponent = parentComponent;
	}

	public ArrayList<IusCLComponent> getChildren() {
		return childComponents;
	}
	
	public String getName() {
		return name;
	}

	/* **************************************************************************************************** */
	@Override
	public String getDisplayName() {
		
		return name;
	}

	public Boolean getIsEmbedded() {
		return isEmbedded;
	}

	/* **************************************************************************************************** */
	public void setIsEmbedded(Boolean isEmbedded) {

		/* No dis-embedd (what would suppose to do?) */
		if (isEmbedded == true) {
			
			this.getOwner().getComponents().remove(this);
			this.isEmbedded = isEmbedded;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsSubComponent() {
		return isSubComponent;
	}

	public void setIsSubComponent(Boolean isSubComponent) {
		this.isSubComponent = isSubComponent;
	}

	public IusCLCollection getSubCollection() {
		return subCollection;
	}

	public void setSubCollection(IusCLCollection subCollection) {
		this.subCollection = subCollection;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

}
