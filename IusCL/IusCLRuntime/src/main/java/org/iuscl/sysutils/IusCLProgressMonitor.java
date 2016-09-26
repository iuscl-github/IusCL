/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.sysutils;

/* **************************************************************************************************** */
public class IusCLProgressMonitor {

	public enum IusCLProgressCancelAction { pcaNone, pcaCancelOnly, pcaCancelAndRollback, pcaCommitAndCancel };

	private Integer begin = 0;
	private Integer end = 100;
	private Integer position = 0;
	
	private String shortMessage = null;
	private String longMessage = null;

	private IusCLProgressCancelAction progressCancelAction = IusCLProgressCancelAction.pcaNone;

	/* **************************************************************************************************** */
	public IusCLProgressMonitor() {
		super();
		
		this.begin = 0;
		this.end = 100;
	}

	/* **************************************************************************************************** */
	public IusCLProgressMonitor(Integer begin, Integer end) {
		super();
		
		this.begin = begin;
		this.end = end;
	}

	/* **************************************************************************************************** */
	public Integer getPositionAsPercent() {
		
		return ((position - begin) * 100) / (end - begin);
	}

	/* **************************************************************************************************** */
	public void setPositionToPercent(Integer positionPercent) {
		
		setPosition(((end - begin) * positionPercent) / 100);
	}
	
	/* **************************************************************************************************** */
	public Integer getBegin() {
		return begin;
	}
	
	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	
	public Integer getEnd() {
		return end;
	}
	
	public void setEnd(Integer end) {
		this.end = end;
	}
	
	public Integer getPosition() {
		return position;
	}
	
	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	public String getLongMessage() {
		return longMessage;
	}

	public void setLongMessage(String longMessage) {
		this.longMessage = longMessage;
	}

	public IusCLProgressCancelAction getProgressCancelAction() {
		return progressCancelAction;
	}

	public void setProgressCancelAction(IusCLProgressCancelAction progressCancelAction) {
		this.progressCancelAction = progressCancelAction;
	}
	
}
