package com.hp.et.log.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.hp.et.log.domain.bean.LogEvent;

@Entity
@DiscriminatorValue(value="AppEnvRuleAdvance")
public class AppEnvRuleAdvance extends AppEnvRule{
	
	@Basic(optional = true)
    @Column(name = "THREAD_NAME", length = 200)
    private String threadName;
    
    @Basic(optional = true)
    @Column(name = "LOGGER_NAME", length = 255)
    private String loggerName;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE1NAME", length = 255)
    private String attribute1Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE2NAME", length = 255)
    private String attribute2Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE3NAME", length = 255)
    private String attribute3Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE4NAME", length = 255)
    private String attribute4Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE5NAME", length = 255)
    private String attribute5Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE1VALUE", length = 255)
    private String attribute1Value;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE2VALUE", length = 255)
    private String attribute2Value;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE3VALUE", length = 255)
    private String attribute3Value;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE4VALUE", length = 255)
    private String attribute4Value;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE5VALUE", length = 255)
    private String attribute5Value;

    public AppEnvRuleAdvance()
    {
        super();
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
	@Override
	public int hashCode(){
		 int hash = 0;
		 hash += (ruleId != null ? ruleId.hashCode() : 0);
		 return hash;
	}
	
	@Override
	public boolean equals(Object object){
		// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppEnvRuleAdvance)) {
            return false;
        }
        AppEnvRuleAdvance other = (AppEnvRuleAdvance) object;
        if ((this.ruleId == null && other.ruleId != null) || (this.ruleId != null && !this.ruleId.equals(other.ruleId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.AppEnvRuleAdvance[ruleId=" +ruleId + "]";
	}

	@Override
	public Boolean judgeLogByExpression(LogEvent log) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
