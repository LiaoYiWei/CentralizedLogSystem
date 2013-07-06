package com.hp.et.log.mail.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.hp.et.log.configure.PropertiesLoader;

public class EmailBuilder {

    public Mail buildMail() {
	Mail mail = new Mail();

	return mail;

    }

    @SuppressWarnings("rawtypes")
    public TemplateMail buildTemplateMail(Map map, String subject,
	    String templateName, String to) throws MailException {
	String from = getConfigFrom();
	TemplateMail mail = buildTemplateMail(map, subject,
		templateName, to, from);
	return mail;
    }

    @SuppressWarnings("unchecked")
    public TemplateMail buildTemplateMail(Map map, String subject,
	    String templateName, String to, String from) throws MailException {
//	Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//	Velocity.setProperty("classpath.resource.loader.class",
//		ClasspathResourceLoader.class.getName());
//	Velocity.init();

//	Template template = Velocity.getTemplate(templateName);
	TemplateMail mail = new TemplateMail(templateName);

	mail.setContexElementList(map);

	mail.setSubject(subject);
	mail.setTo(to);
	mail.setFrom(from);

	return mail;
    }

    private String getConfigFrom() {
	String from = PropertiesLoader.loadProperties("appConfig.properties")
		.getProperty("email.from");
	if (null == from || "" == from.trim()) {
	    from = "no-reply@hp.com";
	}
	return from;
    }

}
