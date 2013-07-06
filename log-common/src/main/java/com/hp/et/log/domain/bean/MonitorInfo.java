package com.hp.et.log.domain.bean;

import java.io.Serializable;

public class MonitorInfo implements Serializable{
	private long monitorId;

	public long getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(long monitorId) {
		this.monitorId = monitorId;
	}
}
