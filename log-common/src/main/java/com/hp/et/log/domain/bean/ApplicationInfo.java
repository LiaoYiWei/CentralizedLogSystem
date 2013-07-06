package com.hp.et.log.domain.bean;

import java.io.Serializable;
import java.util.List;

import com.hp.et.log.domain.bean.EnvInfo;

public class ApplicationInfo implements Serializable{

	private String appId;

	private String appName;

	private List<EnvInfo> envInfos;

	public List<EnvInfo> getEnvInfos() {
		return envInfos;
	}

	public void setEnvInfos(List<EnvInfo> envInfos) {
		this.envInfos = envInfos;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
