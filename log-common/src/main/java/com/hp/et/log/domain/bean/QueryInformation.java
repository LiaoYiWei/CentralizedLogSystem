package com.hp.et.log.domain.bean;

import java.io.Serializable;
import java.util.List;

public class QueryInformation implements Serializable{

	private Integer time;
    private String specificTime;
	private List<String> nodes;
	
	private String runId;
	private int pageStart;	//page start number
	private int limit;	//current page count
	
	private String nodeId;	//use to disable the node
	private String envId;	//use to disable the all nodes which referenced by envId
	
	private String appName;
	
	private String envName;
	
	private String nodeName;
	
	//Event properties
	private Integer logLevel;
	
	private String messageType;
	
	private String message;
	
	private String throwableMessage;
	
	private String threadName;
	
	private String loggerName;
	
	private int logSequence;
	
	private String eventId;
	
	private String attribute1Name;
	
	private String attribute2Name;
	
	private String attribute3Name;
	
	private String attribute4Name;
	
	private String attribute5Name;
	
	private String attribute1Value;
	
	private String attribute2Value;
	
	private String attribute3Value;
	
	private String attribute4Value;
	
	private String attribute5Value;
	

	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}
	public List<String> getNodes() {
		return nodes;
	}
	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
	public String getRunId() {
		return runId;
	}
	public void setRunId(String runId) {
		this.runId = runId;
	}
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
    public void setSpecificTime(String specificTime)
    {
        this.specificTime = specificTime;
    }
    public String getSpecificTime()
    {
        return specificTime;
    }
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getEnvId() {
		return envId;
	}
	public void setEnvId(String envId) {
		this.envId = envId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getThrowableMessage() {
		return throwableMessage;
	}
	public void setThrowableMessage(String throwableMessage) {
		this.throwableMessage = throwableMessage;
	}
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public String getLoggerName() {
		return loggerName;
	}
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getAttribute1Name() {
		return attribute1Name;
	}
	public void setAttribute1Name(String attribute1Name) {
		this.attribute1Name = attribute1Name;
	}
	public String getAttribute2Name() {
		return attribute2Name;
	}
	public void setAttribute2Name(String attribute2Name) {
		this.attribute2Name = attribute2Name;
	}
	public String getAttribute3Name() {
		return attribute3Name;
	}
	public void setAttribute3Name(String attribute3Name) {
		this.attribute3Name = attribute3Name;
	}
	public String getAttribute4Name() {
		return attribute4Name;
	}
	public void setAttribute4Name(String attribute4Name) {
		this.attribute4Name = attribute4Name;
	}
	public String getAttribute5Name() {
		return attribute5Name;
	}
	public void setAttribute5Name(String attribute5Name) {
		this.attribute5Name = attribute5Name;
	}
	public String getAttribute1Value() {
		return attribute1Value;
	}
	public void setAttribute1Value(String attribute1Value) {
		this.attribute1Value = attribute1Value;
	}
	public String getAttribute2Value() {
		return attribute2Value;
	}
	public void setAttribute2Value(String attribute2Value) {
		this.attribute2Value = attribute2Value;
	}
	public String getAttribute3Value() {
		return attribute3Value;
	}
	public void setAttribute3Value(String attribute3Value) {
		this.attribute3Value = attribute3Value;
	}
	public String getAttribute4Value() {
		return attribute4Value;
	}
	public void setAttribute4Value(String attribute4Value) {
		this.attribute4Value = attribute4Value;
	}
	public String getAttribute5Value() {
		return attribute5Value;
	}
	public void setAttribute5Value(String attribute5Value) {
		this.attribute5Value = attribute5Value;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public int getLogSequence() {
		return logSequence;
	}
	public void setLogSequence(int logSequence) {
		this.logSequence = logSequence;
	}
	
}
