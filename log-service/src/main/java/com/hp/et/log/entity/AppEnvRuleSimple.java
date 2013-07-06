package com.hp.et.log.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogSeverityEnum;

@Entity
@DiscriminatorValue(value="AppEnvRuleSimple")
public class AppEnvRuleSimple extends AppEnvRule {

	@Basic(optional = true)
    @Column(name = "SEVERITY")
    private Integer severity;
	
	@Basic(optional = true)
    @Column(name = "MESSAGE_TYPE", length = 20)
    private String messageType;
	
	@Basic(optional = true)
	@Column(name = "THROWABLE_MESSAGE", length = 3000)
	private String throwableMessage;
	
	@Basic(optional = true)
	@Column(name = "MESSAGE", length = 3000)
	private String message;
	
	@Basic(optional = true)
	@Column(name = "THROW_MSG_STATUS")
	private Integer throwMsgStatus;
	
	@Basic(optional = true)
	@Column(name = "MSG_STATUS")
	private Integer msgStatus;

	public AppEnvRuleSimple()
    {
        super();
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
	
	@Override
	public int hashCode(){
		 int hash = 0;
		 hash += (ruleId != null ? ruleId.hashCode() : 0);
		 return hash;
	}
	
	@Override
	public boolean equals(Object object){
		// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppEnvRuleSimple)) {
            return false;
        }
        AppEnvRuleSimple other = (AppEnvRuleSimple) object;
        if ((this.ruleId == null && other.ruleId != null) || (this.ruleId != null && !this.ruleId.equals(other.ruleId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.AppEnvRuleSimple[ruleId=" +ruleId + "]";
	}
	
	public String toExpression(){
		StringBuffer exp = new StringBuffer("(suppressionTime=="+suppressionTime+")");
		
		if (severity!=null && severity>=LogSeverityEnum.TRACE.getIndex() && severity <= LogSeverityEnum.FATAL.getIndex()){
			exp.append("&&(severity=="+severity+")");
		}
		if (messageType!=null){
			exp.append("&&(messageType.equals('"+messageType+"'))");
		}
		
		if (message!=null && msgStatus!=null){
			if (msgStatus == AppEnvRuleInfo.INCLUDE.intValue()){
				exp.append("&&(message.contains('"+message+"'))");
			}else if(msgStatus == AppEnvRuleInfo.EXCLUDE.intValue()){
				exp.append("&&(!message.contains('"+message+"'))");
			}
		}
		if (throwableMessage!=null && throwMsgStatus!=null){
			if (throwMsgStatus == AppEnvRuleInfo.INCLUDE.intValue()){
				exp.append("&&(throwableMessage.contains('"+throwableMessage+"'))");
			}else if(throwMsgStatus == AppEnvRuleInfo.EXCLUDE.intValue()){
				exp.append("&&(!throwableMessage.contains('"+throwableMessage+"'))");
			}
		}
		return exp.toString();
	}

	@Override
	public Boolean judgeLogByExpression(LogEvent log) {
		Map<String, Boolean> expMap = new HashMap<String, Boolean>(); 

		//trim the string
		if (messageType!=null){
			messageType = messageType.trim();
		}
		if (message!=null){
			message = message.trim();
		}
		if (throwableMessage!=null){
			throwableMessage = throwableMessage.trim();
		}
		
    	// return false if all the properties are null(this rule is not valid) 
    	if (severity==null && (messageType==null||messageType=="") && (message==null || message=="" || msgStatus==null) && (throwableMessage==null || throwableMessage=="" || throwMsgStatus==null)){
    		return false;
    	}
    	if (severity==null || (severity!=null && log.getSeverity()==severity)){
    		expMap.put("severity", true);
    	}else{
    		expMap.put("severity", false);
    	}
    	
    	if (messageType==null || messageType.equals("") || (log.getMessageType().equals(messageType))){
    		expMap.put("msgType", true);
    	}else{
    		expMap.put("msgType", false);
    	}
    	
    	if (message==null || message.equals("")){
    		expMap.put("msg", true);
    	}else{
    		if (log.getMessage().contains(message) && msgStatus==AppEnvRuleInfo.INCLUDE.intValue()){
    			expMap.put("msg", true);
    		}else if ((!log.getMessage().contains(message)) && msgStatus==AppEnvRuleInfo.EXCLUDE.intValue()){
    			expMap.put("msg", true);
    		}else{
    			expMap.put("msg", false);
    		}
    	}
    	
    	if (throwableMessage==null || throwableMessage.equals("")){
    		expMap.put("throwMsg", true);
    	}else{
    		if (log.getThrowableMessage().contains(throwableMessage) && throwMsgStatus==AppEnvRuleInfo.INCLUDE.intValue()){
    			expMap.put("throwMsg", true);
    		}else if ((!log.getThrowableMessage().contains(throwableMessage)) && throwMsgStatus==AppEnvRuleInfo.EXCLUDE.intValue()){
    			expMap.put("throwMsg", true);
    		}else{
    			expMap.put("throwMsg", false);
    		}
    	}
    	
    	if (expMap.get("severity") && expMap.get("msgType") && expMap.get("msg") && expMap.get("throwMsg")){
    		return true;
    	}
    	return false;
	}
	
}
