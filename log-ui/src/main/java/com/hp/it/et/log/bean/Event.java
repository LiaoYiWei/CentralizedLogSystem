package com.hp.it.et.log.bean;

/**
 * Event
 * 
 * @author shihuj
 * 
 */
public class Event {
	/*
	 * Event id
	 */
	private int id;
	/*
	 * Event createDate
	 */
	private String createDate;
	/*
	 * Event severity
	 */
	private String severity;
	/*
	 * Event message
	 */
	private String message;
	/*
	 * Event application ID
	 */
	private String applicationId;
	/*
	 * Event Host Id
	 */
	private String hostId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

}
