package com.hp.et.log.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "REJECT_HOST_HISTORY")
@NamedQueries
({
    @NamedQuery(name = "RejectHostHistory.findAll", query = "SELECT rhh FROM RejectHostHistory rhh"),
    @NamedQuery(name = "RejectHostHistory.findByRejHostId", query = "SELECT rhh FROM RejectHostHistory rhh WHERE rhh.rejHostId = :rejHostId"),
    @NamedQuery(name = "RejectHostHistory.findByRejHostName", query = "SELECT rhh FROM RejectHostHistory rhh WHERE rhh.rejHostName = :rejHostName"),
    @NamedQuery(name = "RejectHostHistory.findByRejIpAddress", query = "SELECT rhh FROM RejectHostHistory rhh WHERE rhh.rejIpAddress = :rejIpAddress"),
    @NamedQuery(name = "RejectHostHistory.findByRejAppName", query = "SELECT rhh FROM RejectHostHistory rhh WHERE rhh.rejAppName = :rejAppName"),
    @NamedQuery(name = "RejectHostHistory.findByRejNodeName", query = "SELECT rhh FROM RejectHostHistory rhh WHERE rhh.rejNodeName = :rejNodeName"),
    @NamedQuery(name = "RejectHostHistory.findByRejEnvName", query = "SELECT rhh FROM RejectHostHistory rhh WHERE rhh.rejEnvName = :rejEnvName")
})
public class RejectHostHistory implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
    @GenericGenerator(name="generator", strategy="uuid", parameters={})
    @GeneratedValue(generator="generator")
    @Basic(optional = false)
    @Column(name = "REJ_HOST_ID")
    private String rejHostId;
    
    @Basic(optional = false)
    @Column(name = "REJ_HOST_NAME", length = 100)
    private String rejHostName;
    
    @Basic(optional = false)
    @Column(name = "REJ_IP_ADDRESS", length = 40)
    private String rejIpAddress;
    
    @Basic(optional = false)
    @Column(name = "REJ_APP_NAME", length = 100)
    private String rejAppName;
    
    @Basic(optional = true)
    @Column(name = "REJ_NODE_NAME", length = 100)
    private String rejNodeName;
    
    @Basic(optional = true)
    @Column(name = "REJ_ENV_NAME", length = 100)
    private String rejEnvName;
    
    @Basic(optional = false)
    @Column(name = "REJ_CREATE_TIME")
    private Date rejCreateTime;
    
    public RejectHostHistory()
    {
        super();
    }

    public RejectHostHistory(String rejHostId, String rejHostName, String rejIpAddress, String rejAppName, String rejNodeName, String rejEnvName, Date rejCreateTime)
    {
        this.rejHostId = rejHostId;
        this.rejAppName = rejAppName;
        this.rejHostName = rejHostName;
        this.rejIpAddress = rejIpAddress;
        this.rejNodeName = rejNodeName;
        this.rejEnvName = rejEnvName;
        this.rejCreateTime = rejCreateTime;
    }
    
    public String getRejHostId() {
		return rejHostId;
	}

	public void setRejHostId(String rejHostId) {
		this.rejHostId = rejHostId;
	}

	public String getRejHostName() {
		return rejHostName;
	}

	public void setRejHostName(String rejHostName) {
		this.rejHostName = rejHostName;
	}

	public String getRejIpAddress() {
		return rejIpAddress;
	}

	public void setRejIpAddress(String rejIpAddress) {
		this.rejIpAddress = rejIpAddress;
	}

	public String getRejAppName() {
		return rejAppName;
	}

	public void setRejAppName(String rejAppName) {
		this.rejAppName = rejAppName;
	}

	public String getRejNodeName() {
		return rejNodeName;
	}

	public void setRejNodeName(String rejNodeName) {
		this.rejNodeName = rejNodeName;
	}

	public String getRejEnvName() {
		return rejEnvName;
	}

	public void setRejEnvName(String rejEnvName) {
		this.rejEnvName = rejEnvName;
	}

	public Date getRejCreateTime() {
		return rejCreateTime;
	}

	public void setRejCreateTime(Date rejCreateTime) {
		this.rejCreateTime = rejCreateTime;
	}

	@Override
	public int hashCode(){
		 int hash = 0;
		 hash += (rejHostId != null ? rejHostId.hashCode() : 0);
		 return hash;
	}
	
	@Override
	public boolean equals(Object object){
		// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Host)) {
            return false;
        }
        RejectHostHistory other = (RejectHostHistory) object;
        if ((this.rejHostId == null && other.rejHostId != null) || (this.rejHostId != null && !this.rejHostId.equals(other.rejHostId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.RejectHostHistory[rejHostId=" + rejHostId + "]";
	}
}
