package com.hp.et.log.domain.bean;


import java.io.Serializable;
import java.util.HashMap;

import com.hp.et.log.restful.tool.SequenceGenerator;


public class LogEvent implements Serializable{
        
    public static final String EVENT_SEQ_ID = "EVENT_SEQ_ID";
    public static final String MESSAGE_TYPE_LOG_SYSTEM = "LOG_SYSTEM";
    public static final String MESSAGE_TYPE_REPORT_APP = "REPORT_APP";
	
    public LogEvent()
    {
        
    }
    
    private long logSequence; 
    

    private String id = "";
	private String messageType = ""; 
	

    private int severity;
	private String message = "";
	private String throwableMessage = "";
	private long timestamp;
	private String appName = "";
	private String host = ""; // this field will NOT be saved to db, it is kept only
							// for the log purpose
	private String env = ""; // this field will NOT be saved to db, it is kept only
						// for log purpose
	private String runId = ""; // this filed will be saved to db.
	

    private String loggerName = ""; 
    private String threadName = ""; 
    
    private HashMap<String, String> extraProps = new HashMap();
    
    private String nodeName = "";

    
 // Reserved Attributes
    private String attribute1Name;
    private String attribute2Name;
    private String attribute3Name;
    private String attribute4Name;
    private String attribute5Name;
    
 // Reserved Attributes
    private String attribute1Value;
    private String attribute2Value;
    private String attribute3Value;
    private String attribute4Value;
    private String attribute5Value;


    public long getLogSequence()
    {
        return logSequence;
    }

    public void setLogSequence(long logSequence)
    {
        this.logSequence = logSequence;
    }

    public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
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

	public String getThrowableMessage() {
		return throwableMessage;
	}

	public void setThrowableMessage(String throwableMessage) {
		this.throwableMessage = throwableMessage;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
    
    public String getLoggerName()
    {
        return loggerName;
    }

    public void setLoggerName(String loggerName)
    {
        this.loggerName = loggerName;
    }

    public String getThreadName()
    {
        return threadName;
    }

    public void setThreadName(String threadName)
    {
        this.threadName = threadName;
    }


    public String getMessageType()
    {
        return messageType;
    }

    public void setMessageType(String messageType)
    {
        this.messageType = messageType;
    }

    
    public HashMap<String,String> getExtraProps()
    {
        return extraProps;
    }

    public String getAttribute1Name() {
		return attribute1Name;
	}

	public void setAttribute1Name(String attribute1Name) {
		this.attribute1Name = attribute1Name;
	}

	public void setExtraProps(HashMap<String,String> extraProps)
    {
        this.extraProps = extraProps;
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
	
	/**
     * Note, we don't want to override toString. Maybe toString will be used for other purpose to marshal/unmarshal JAXB
     * Description goes here.
     *
     * @return
     */
	public String debugString()
	{
	    StringBuffer strBuf = new StringBuffer(); 
        strBuf.append("id:").append(id).append("\n");
        strBuf.append("runId:").append(runId).append("\n");
        strBuf.append("logSequence:").append(logSequence).append("\n");
        strBuf.append("messageType:").append(messageType).append("\n");
        strBuf.append("severity:").append(severity).append("\n");
        strBuf.append("nodeName:").append(nodeName).append("\n");        
        
        return strBuf.toString();
	}

}
