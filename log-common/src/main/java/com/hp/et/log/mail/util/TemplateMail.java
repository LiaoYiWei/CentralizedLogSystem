package com.hp.et.log.mail.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class TemplateMail extends Mail {

	private VelocityEngine ve = new VelocityEngine();
    private VelocityContext context = new VelocityContext();
    private Writer writer;
    private Template template;
    
    public TemplateMail(String templateFile)
    {
    	ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    	ve.setProperty("classpath.resource.loader.class",
    	ClasspathResourceLoader.class.getName());
    	ve.init();
    	template = ve.getTemplate(templateFile);
    }

    @SuppressWarnings("rawtypes")
    public void setContexElementList(Map<String, Object> map) {
	
	if (null != map && map.size() > 0) {
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
		Map.Entry entry = (Map.Entry) it.next();
		context.put((String)entry.getKey(),entry.getValue());
	    }
	}

    }

    public TemplateMail setContexElement(String elName, Object elValue) {
	
	    context.put(elName, elValue);
	    return this;
	    
    }
    
    
    
    public String getBody() {
    	
    	StringWriter sw = new StringWriter();
    	
    	template.merge( context, sw );
    	return sw.toString();
	}

}
