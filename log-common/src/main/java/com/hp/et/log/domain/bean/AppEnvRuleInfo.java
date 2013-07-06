package com.hp.et.log.domain.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class AppEnvRuleInfo implements Serializable {
	
	public static final Integer INCLUDE = 0;
	public static final Integer EXCLUDE = 1;
	
	public static final Integer SIMPLE = 0;
	public static final Integer ADVANCE = 1;

	public AppEnvRuleInfo(){
		
	}

	private String Id;
	

    private String ruleId;
	
	private String ruleName;
	
	private int suppressionTime;
	
	private String emailPdl;
	
	private Integer severity;
	
	private String messageType;
	
	private String throwableMessage;
	
	private String message;
	
	private Integer throwMsgStatus;
	
	private Integer msgStatus;
	
	private String envId;
	
	private String appName;
	
    private String envName;
	
	private Date createTime;
	
	private Date updateTime;
	
	private Date lastEmailTime;
	

    public String getId()
    {
        return Id;
    }
    public void setId(String id)
    {
        Id = id;
    }
    public String getAppName()
    {
        return appName;
    }
    public void setAppName(String appName)
    {
        this.appName = appName;
    }
    public String getEnvName()
    {
        return envName;
    }
    public void setEnvName(String envName)
    {
        this.envName = envName;
    }
    
	public String getEnvId() {
		return envId;
	}
	public void setEnvId(String envId) {
		this.envId = envId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public Date getLastEmailTime() {
		return lastEmailTime;
	}
	public void setLastEmailTime(Date lastEmailTime) {
		this.lastEmailTime = lastEmailTime;
	}
	public Integer getThrowMsgStatus() {
		return throwMsgStatus;
	}
	public void setThrowMsgStatus(Integer throwMsgStatus) {
		this.throwMsgStatus = throwMsgStatus;
	}
	public Integer getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public String getEmailPdl() {
		return emailPdl;
	}
	public void setEmailPdl(String emailPdl) {
		this.emailPdl = emailPdl;
	}
	public Integer getSeverity() {
		return severity;
	}
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getThrowableMessage() {
		return throwableMessage;
	}
	public void setThrowableMessage(String throwableMessage) {
		this.throwableMessage = throwableMessage;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    public Integer getSuppressionTime()
    {
        return suppressionTime;
    }
    public void setSuppressionTime(Integer suppressionTime)
    {
        this.suppressionTime = suppressionTime;
    }
	
}
