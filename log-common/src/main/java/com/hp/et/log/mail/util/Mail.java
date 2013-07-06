package com.hp.et.log.mail.util;

public class Mail {
	
	public static String TextType="text/plain;charset=utf8";
	public static String HtmlType="text/html;charset=utf8";
	protected String to;
	protected String cc;
	protected String from;
	protected String body;
	protected String subject;
	protected String envelopeFrom;
	protected String smtpHost;
	
	//by default, mail type is text type.
	protected String mailType = TextType;
	
	
	
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getEnvelopeFrom() {
		return envelopeFrom;
	}
	public void setEnvelopeFrom(String envelopeFrom) {
		this.envelopeFrom = envelopeFrom;
	}
	
	

}
