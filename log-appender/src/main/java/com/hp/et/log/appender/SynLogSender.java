/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.et.log.appender;

import java.util.ArrayList;

import com.hp.et.log.clientfacade.RestfulClientFacade;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogEventInfo;

/**
 * Description goes here.
 */
public class SynLogSender implements ILogSender
{
    private boolean ifSendingLog = true;

    private RunContext runContext;

    /* (non-Javadoc)
     * @see com.hp.et.log.appender.ILogSender#sendLog(com.hp.et.log.domain.bean.LogEvent)
     */
    public void sendLog(LogEvent logEvent)
    {
        if (ifSendingLog)
        {
            ArrayList<LogEvent> logs = new ArrayList<LogEvent>();
            logs.add(logEvent);
            LogEventInfo logInfo = new LogEventInfo();
            logInfo.setRunId(runContext.getApplicationInstance().getRunId());
            logInfo.setLogEventList(logs);
            logInfo.setQueueCapacity(0);
            logInfo.setConsumedSize(0);
            try
            {
                RestfulClientFacade clientFacade = new RestfulClientFacade(runContext.getServerUrl());

                clientFacade.sendLogEvent(logInfo);
            }
            catch (Throwable ex)
            {
                //if there is any exception
                //stop log sending 
                ifSendingLog = false;
            }
        }

    }

    /* (non-Javadoc)
     * @see com.hp.et.log.appender.ILogSender#startup(com.hp.et.log.appender.RunContext)
     */
    public void startup(RunContext context)
    {
        this.runContext = context;
        AppenderUtil.authenticateApp(context);

    }

}
