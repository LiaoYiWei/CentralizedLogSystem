package com.hp.et.log.domain.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class LogEventInfo implements Serializable {

	private int consumedSize;	//remaining count of logs which are waiting to be sent 
	
	private int queueCapacity;
	
	private String runId=""; 
	
	private ArrayList<LogEvent> logEventList = new ArrayList();


	/**
	 * 
	 * Return the rough estimation for the size 
	 * Note, it's not accurate size. 
	 *
	 * @return
	 */
	public long size()
	{
	    int size = runId.length() + 8; 
	    for(LogEvent logEvent: logEventList)
	    {
	        size = size + logEvent.getAppName().length() + logEvent.getLoggerName().length() + logEvent.getMessage().length();
	        size = size + logEvent.getThreadName().length() + logEvent.getThrowableMessage().length();
	    }
	    
	    return size;
	}
	

	public String getRunId()
    {
        return runId;
    }

    public void setRunId(String runId)
    {
        this.runId = runId;
    }

    public int getConsumedSize()
    {
        return consumedSize;
    }

    public void setConsumedSize(int consumedSize)
    {
        this.consumedSize = consumedSize;
    }

    public int getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public ArrayList<LogEvent> getLogEventList() {
		return logEventList;
	}

	public void setLogEventList(ArrayList<LogEvent> logEventList) {
		this.logEventList = logEventList;
	}
	
}
