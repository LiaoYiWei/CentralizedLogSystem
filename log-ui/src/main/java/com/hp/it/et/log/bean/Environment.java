package com.hp.it.et.log.bean;
/**
 * Environment
 * @author shihuj
 *
 */
public class Environment {
	/*
	 * Environment ID
	 */
	private int id;
	/*
	 * Environment HOST
	 */
	private String host;
	/*
	 * Environment Name
	 */
	private String env;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	
}
