/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.et.log.appender.log4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.xml.UnrecognizedElementHandler;
import org.w3c.dom.Element;

import com.hp.et.log.appender.AppenderUtil;
import com.hp.et.log.appender.DefaultEnvLoader;
import com.hp.et.log.appender.DefaultNodeLoader;
import com.hp.et.log.appender.IEnvLoader;
import com.hp.et.log.appender.ILogSender;
import com.hp.et.log.appender.INodeLoader;
import com.hp.et.log.appender.RunContext;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogSeverityEnum;

/**
 * Description goes here.
 */
public class CentralAppender extends AppenderSkeleton implements UnrecognizedElementHandler
{
    //fields what need to be set in the XML configuration file.
    private String appName;

    private String serviceHttpUrl;
    
    private IEnvLoader envLoader = new DefaultEnvLoader();
    
    private INodeLoader nodeLoader = new DefaultNodeLoader();
    
    private ILogSender logSender;

    
    
    public INodeLoader getNodeLoader()
    {
        return nodeLoader;
    }

    public void setNodeLoader(INodeLoader nodeLoader)
    {
        this.nodeLoader = nodeLoader;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getServiceHttpUrl()
    {
        return serviceHttpUrl;
    }

    public void setServiceHttpUrl(String serviceHttpUrl)
    {
        this.serviceHttpUrl = serviceHttpUrl;
    }

    public IEnvLoader getEnvLoader()
    {
        return envLoader;
    }

    public void setEnvLoader(IEnvLoader envLoader)
    {
        this.envLoader = envLoader;
    }

    public ILogSender getLogSender()
    {
        return logSender;
    }

    public void setLogSender(ILogSender logSender)
    {
        this.logSender = logSender;
    }

    //fields that don't need to be set.
    private RunContext runContext;

    private String env;

    private String node;

    /* (non-Javadoc)
     * @see org.apache.log4j.Appender#close()
     */
    public void close()
    {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.apache.log4j.Appender#requiresLayout()
     */
    public boolean requiresLayout()
    {
        // TODO Auto-generated method stub
        return false;
    }

    public void activateOptions()
    {
        env = envLoader.getEnv();
        node = nodeLoader.getNode();

        runContext = AppenderUtil.getRunContext(serviceHttpUrl, appName, env, node);

        logSender.startup(runContext);
    }

    /* (non-Javadoc)
     * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
     */
    @Override
    protected void append(LoggingEvent event)
    {
        // TODO Auto-generated method stub

        LogEvent logEvent = convertLog(event);

        logSender.sendLog(logEvent);

    }

    private LogEvent convertLog(LoggingEvent event)
    {
        LogEvent logEvent = new LogEvent();
        
        logEvent.setAppName(appName);
        logEvent.setEnv(env);
        logEvent.setMessageType(LogEvent.MESSAGE_TYPE_REPORT_APP);
        logEvent.setMessage(event.getRenderedMessage());
        
        String[] errorStrs = event.getThrowableStrRep();
		if(errorStrs != null){
			String errorStr = Arrays.toString(errorStrs);
			logEvent.setThrowableMessage(errorStr);
		}      
 
        logEvent.setSeverity(LogSeverityEnum.valueOf(event.getLevel().toString()).getIndex());
        logEvent.setTimestamp(event.getTimeStamp());
        logEvent.setLoggerName(event.getLoggerName());
        logEvent.setThreadName(event.getThreadName());
        logEvent.setRunId(runContext.getApplicationInstance().getRunId());
       // logEvent.setNode(node);

        HashMap<String, String> extraProps = new HashMap();
        Map mdc = event.getProperties();
        if (mdc != null && mdc.size() > 0)
        {
            Set<Map.Entry> entries = mdc.entrySet();
            for (Map.Entry entry : entries)
            {
                //System.out.println(entry.getKey());
                //System.out.println(entry.getValue());
                extraProps.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        logEvent.setExtraProps(extraProps);

        return logEvent;
    }

    public boolean parseUnrecognizedElement(Element element, Properties props)
        throws Exception
    {   
        Object obj = XmlConfigParser.parseElement(element);
        if (obj instanceof ILogSender)
        {
            setLogSender((ILogSender)obj);
            return true;
        }
        if (obj instanceof IEnvLoader)
        {
            setEnvLoader((IEnvLoader)obj);
            return true;
        }
        return false;
    }

}
