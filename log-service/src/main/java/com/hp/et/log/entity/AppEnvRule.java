package com.hp.et.log.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.hp.et.log.domain.bean.LogEvent;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="APP_ENV_RULE")
@DiscriminatorColumn(name="Discriminator", discriminatorType=DiscriminatorType.STRING, length=50)
@DiscriminatorValue(value="AppEnvRule") 
public abstract class AppEnvRule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GenericGenerator(name="generator", strategy="uuid", parameters={})
    @GeneratedValue(generator="generator")
    @Basic(optional = false)
    @Column(name = "RULE_ID")
    protected String ruleId;
	
	@Basic(optional = false)
    @Column(name = "RULE_NAME", length = 255)
    private String ruleName;
	
	@Basic(optional = false)
    @Column(name = "SUPPRESSION_TIME")
    protected Integer suppressionTime;
	
	@Basic(optional = false)
    @Column(name = "EMAIL_PDL", length = 500)
    private String emailPdl;	
	
	@Basic(optional = false)
    @Column(name = "CREATE_TIME")
    private Date createTime;
	
	@Basic(optional = false)
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
	
	@ManyToOne(targetEntity=AppEnv.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="ENV_ID")
    private AppEnv appEnv;
	
	@Transient
	private Date lastEmailTime;
	
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
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

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getSuppressionTime() {
		return suppressionTime;
	}

	public void setSuppressionTime(Integer suppressionTime) {
		this.suppressionTime = suppressionTime;
	}

	public String getEmailPdl() {
		return emailPdl;
	}

	public void setEmailPdl(String emailPdl) {
		this.emailPdl = emailPdl;
	}

	public AppEnv getAppEnv() {
		return appEnv;
	}

	public void setAppEnv(AppEnv appEnv) {
		this.appEnv = appEnv;
	}

	public Date getLastEmailTime() {
		return lastEmailTime;
	}

	public void setLastEmailTime(Date lastEmailTime) {
		this.lastEmailTime = lastEmailTime;
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
        if (!(object instanceof AppEnvRule)) {
            return false;
        }
        AppEnvRule other = (AppEnvRule) object;
        if ((this.ruleId == null && other.ruleId != null) || (this.ruleId != null && !this.ruleId.equals(other.ruleId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.AppEnvRule[ruleId=" +ruleId + "]";
	}
	
	/**
	 * judge the log email 
	 * @param log
	 * @return
	 */
	public abstract Boolean judgeLogByExpression(LogEvent log);
	
	/**
	 * judge the it suppression Time
	 * @param lastTime
	 * @return
	 */
	public synchronized Boolean judgeSuppression(boolean updateFlag){
		
		Date now = new Date();
		if (lastEmailTime==null){
			if (updateFlag){
				lastEmailTime = now;
			}
			return true;
		}else{
			long duration = now.getTime() - lastEmailTime.getTime();
			if (suppressionTime.intValue()*60*60*1000 < duration){
				if (updateFlag){
					lastEmailTime = now;
				}
				return true;
			}
		}
		return false;
	}
	
}
