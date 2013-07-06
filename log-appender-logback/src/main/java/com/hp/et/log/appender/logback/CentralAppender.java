package com.hp.et.log.appender.logback;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import com.hp.et.log.appender.AppenderUtil;
import com.hp.et.log.appender.DefaultEnvLoader;
import com.hp.et.log.appender.DefaultNodeLoader;
import com.hp.et.log.appender.IEnvLoader;
import com.hp.et.log.appender.ILogSender;
import com.hp.et.log.appender.INodeLoader;
import com.hp.et.log.appender.RunContext;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogSeverityEnum;

public class CentralAppender extends AppenderBase<ILoggingEvent>
{
    //fields what need to be set in the XML configuration file.
    private String appName;

    private String env;

    private String node;
    
    private IEnvLoader envLoader = new DefaultEnvLoader(); 
    
    private INodeLoader nodeLoader = new DefaultNodeLoader();

    private String serviceHttpUrl;

    private ILogSender logSender;

    
    
    public INodeLoader getNodeLoader()
    {
        return nodeLoader;
    }

    public void setNodeLoader(INodeLoader nodeLoader)
    {
        this.nodeLoader = nodeLoader;
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

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getEnv()
    {
        return env;
    }

    public void setEnv(String env)
    {
        this.env = env;
    }

    public String getNode()
    {
        return node;
    }

    public void setNode(String node)
    {
        this.node = node;
    }

    public String getServiceHttpUrl()
    {
        return serviceHttpUrl;
    }

    public void setServiceHttpUrl(String serviceHttpUrl)
    {
        this.serviceHttpUrl = serviceHttpUrl;
    }

    //fields that don't need to be set.
    private ThrowableProxyConverter proxyConverter = new ThrowableProxyConverter();

    private RunContext runContext;

    @Override
    public void start()
    {
        env = envLoader.getEnv();
        node = nodeLoader.getNode();
        runContext = AppenderUtil.getRunContext(serviceHttpUrl, appName, env, node);

        logSender.startup(runContext);
        proxyConverter.start();
        
        super.start();
    }


    @Override
    protected void append(ILoggingEvent eventObject)
    {
        // TODO Auto-generated method stub
        //System.out.println("***************" + eventObject.getFormattedMessage());
        
        
        LogEvent logEvent = convertLog(eventObject);

        logSender.sendLog(logEvent);

    }

    private LogEvent convertLog(ILoggingEvent eventObject)
    {

        
        LogEvent logEvent = new LogEvent();

        logEvent.setAppName(appName);
        logEvent.setEnv(env);
        logEvent.setMessageType(LogEvent.MESSAGE_TYPE_REPORT_APP);
        logEvent.setMessage(eventObject.getFormattedMessage());
        String errorStr = proxyConverter.convert(eventObject);
        logEvent.setThrowableMessage(errorStr);
        logEvent.setSeverity(LogSeverityEnum.valueOf(eventObject.getLevel().levelStr).getIndex());
        logEvent.setTimestamp(eventObject.getTimeStamp());
        logEvent.setLoggerName(eventObject.getLoggerName());
        logEvent.setThreadName(eventObject.getThreadName());
        logEvent.setRunId(runContext.getApplicationInstance().getRunId());

        HashMap<String, String> extraProps = new HashMap();
        Map mdc = eventObject.getMDCPropertyMap();
        if (mdc != null && mdc.size() > 0)
        {
            Set<Map.Entry> entries = mdc.entrySet();
            for (Map.Entry entry : entries)
            {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
                extraProps.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        logEvent.setExtraProps(extraProps);
        return logEvent;
    }

}
