package com.hp.et.log.domain.bean;

import java.io.Serializable;
import java.util.Date;

public class RuntimeInstanceInfo implements Serializable{
	private String runId;
	
	private Date startTime;
	
	private Date clientStartTime;

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getClientStartTime() {
		return clientStartTime;
	}

	public void setClientStartTime(Date clientStartTime) {
		this.clientStartTime = clientStartTime;
	}
	
	

}
