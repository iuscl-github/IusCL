/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.sysctrls;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.iuscl.classes.IusCLComponent;
import org.iuscl.events.IusCLEvent;
import org.iuscl.events.IusCLNotifyEvent;

/* **************************************************************************************************** */
public class IusCLScheduler extends IusCLComponent {

	public enum IusCLExecutionType { etFixedDelay, etFixedRate };
	
	/* Properties */
	private Integer startDelay = 1000;
	private Integer interval = 1000;
	
	private IusCLExecutionType executionType = IusCLExecutionType.etFixedRate;

	/* Events */
	private IusCLNotifyEvent onTimer = null;
	
	/* Fields */
	private Date startDate = null;
	private Boolean fixedDelayOnlyOnce = false;

	
	private Timer javaTimer = null;
	private TimerTask javaTimerTask = null;

	/* **************************************************************************************************** */
	public IusCLScheduler(IusCLComponent aOwner) {
		
		super(aOwner);

		/* Properties */
		defineProperty("StartDelay", IusCLPropertyType.ptInteger, "1000");
		defineProperty("Interval", IusCLPropertyType.ptInteger, "1000");
		defineProperty("ExecutionType", IusCLPropertyType.ptEnum, "etFixedRate", IusCLExecutionType.etFixedRate);
		defineProperty("FixedDelayOnlyOnce", IusCLPropertyType.ptBoolean, "false");

		/* Events */
		defineProperty("OnTimer", IusCLPropertyType.ptEvent, null, IusCLNotifyEvent.class);

		/* Create */
	}

	/* **************************************************************************************************** */
	public void start() {

		stop();

		javaTimerTask = new TimerTask() {

			/* **************************************************************************************************** */
			@Override
			public void run() {

				if (IusCLEvent.isDefinedEvent(onTimer)) {

					onTimer.invoke(IusCLScheduler.this);
				}
			}
		};

		javaTimer = new Timer();

		switch (executionType) {
		case etFixedDelay:
			
			if (fixedDelayOnlyOnce) {
				if (startDate == null) {
					javaTimer.schedule(javaTimerTask, startDelay);
				}
				else {
					javaTimer.schedule(javaTimerTask, startDate);		
				}
			}
			else {
				if (startDate == null) {
					javaTimer.schedule(javaTimerTask, startDelay, interval);
				}
				else {
					javaTimer.schedule(javaTimerTask, startDate, interval);
				}
			}
			break;
		case etFixedRate:
			
			if (startDate == null) {
				javaTimer.scheduleAtFixedRate(javaTimerTask, startDelay, interval);
			}
			else {
				javaTimer.scheduleAtFixedRate(javaTimerTask, startDate, interval);
			}
			break;
		}
	}

	/* **************************************************************************************************** */
	public void stop() {

		if (isStarted()) {
			
			javaTimerTask.cancel();
			javaTimerTask = null;
			
			javaTimer.cancel();
			javaTimer = null;
		}
	}

	/* **************************************************************************************************** */
	public Boolean isStarted() {

		if (javaTimer != null) {
			
			return true;
		}
		
		return false;
	}

	/* **************************************************************************************************** */
	@Override
	public void free() {
		super.free();
		
		stop();
	}

	/* **************************************************************************************************** */
	public void findStartDelay(Integer year4, Integer month112, Integer day131, 
			Integer hour023, Integer minute060, Integer second060) {

		/*
		 * TODO
		 * 
		 * GregorianCalendar
		 */
	}

	/* **************************************************************************************************** */
	public Integer mlsOneSecond() {
		
		return 1000;
	}

	/* **************************************************************************************************** */
	public Integer mlsOneMinute() {
		
		return 60 * mlsOneSecond();
	}

	/* **************************************************************************************************** */
	public Integer mlsOneHour() {
		
		return 60 * mlsOneMinute();
	}

	/* **************************************************************************************************** */
	public Integer mlsOneDay() {
		
		return 24 * mlsOneHour();
	}

	/* **************************************************************************************************** */
	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public IusCLNotifyEvent getOnTimer() {
		return onTimer;
	}

	public void setOnTimer(IusCLNotifyEvent onTimer) {
		this.onTimer = onTimer;
	}

	public Integer getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(Integer startDelay) {
		this.startDelay = startDelay;
	}

	public IusCLExecutionType getExecutionType() {
		return executionType;
	}

	public void setExecutionType(IusCLExecutionType executionType) {
		this.executionType = executionType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Boolean getFixedDelayOnlyOnce() {
		return fixedDelayOnlyOnce;
	}

	public void setFixedDelayOnlyOnce(Boolean fixedDelayOnlyOnce) {
		this.fixedDelayOnlyOnce = fixedDelayOnlyOnce;
	}

}
