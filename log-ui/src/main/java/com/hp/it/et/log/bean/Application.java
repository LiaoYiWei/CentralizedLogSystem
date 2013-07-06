package com.hp.it.et.log.bean;

/**
 * Application
 * @author shihuj
 *
 */
public class Application {
	/*
	 * Application ID
	 */
	private int id;
	/*
	 * Application Name
	 */
	private String appName;
	
	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
