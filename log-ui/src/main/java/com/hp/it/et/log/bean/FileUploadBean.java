package com.hp.it.et.log.bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUploadBean {
	private CommonsMultipartFile file;

	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

}
