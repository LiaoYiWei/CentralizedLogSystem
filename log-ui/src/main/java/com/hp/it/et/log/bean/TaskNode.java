package com.hp.it.et.log.bean;

import java.util.ArrayList;
import java.util.List;

public class TaskNode {
	private String appEnvName;
	private String taskName;
	private String id;
	private int serverity;
	private long clientLogQueueConsumedSize;
	private long clientLogQueueTotalSize;
	private float logsPerSec;
	private float sizePerSec;
	private float avgLogsPerSec;
	private float avgSizePerSec;
	private boolean activeStatus = true;
	private String lastRuntimeInstanceStartDate;
	
	public String getLastRuntimeInstanceStartDate() {
		return lastRuntimeInstanceStartDate;
	}

	public void setLastRuntimeInstanceStartDate(String lastRuntimeInstanceStartDate) {
		this.lastRuntimeInstanceStartDate = lastRuntimeInstanceStartDate;
	}

	public String getAppEnvName() {
		return appEnvName;
	}

	public void setAppEnvName(String appEnvName) {
		this.appEnvName = appEnvName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getServerity() {
		return serverity;
	}

	public void setServerity(int serverity) {
		this.serverity = serverity;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public long getClientLogQueueConsumedSize() {
		return clientLogQueueConsumedSize;
	}

	public void setClientLogQueueConsumedSize(long clientLogQueueConsumedSize) {
		this.clientLogQueueConsumedSize = clientLogQueueConsumedSize;
	}

	public long getClientLogQueueTotalSize() {
		return clientLogQueueTotalSize;
	}

	public void setClientLogQueueTotalSize(long clientLogQueueTotalSize) {
		this.clientLogQueueTotalSize = clientLogQueueTotalSize;
	}

	public float getLogsPerSec() {
		return logsPerSec;
	}

	public void setLogsPerSec(float logsPerSec) {
		this.logsPerSec = logsPerSec;
	}

	public float getSizePerSec() {
		return sizePerSec;
	}

	public void setSizePerSec(float sizePerSec) {
		this.sizePerSec = sizePerSec;
	}

	public float getAvgLogsPerSec() {
		return avgLogsPerSec;
	}

	public void setAvgLogsPerSec(float avgLogsPerSec) {
		this.avgLogsPerSec = avgLogsPerSec;
	}

	public float getAvgSizePerSec() {
		return avgSizePerSec;
	}

	public void setAvgSizePerSec(float avgSizePerSec) {
		this.avgSizePerSec = avgSizePerSec;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
}
