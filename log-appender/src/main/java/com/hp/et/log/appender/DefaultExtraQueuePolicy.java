/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.appender;

import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogSeverityEnum;

public class DefaultExtraQueuePolicy implements IExtraQueuePolicy
{

    int extraQueueSize = 1000; 
    
    //this function will be called inside of synchronized(queue), so doesn't have to synchronize again 
    //but you could still do that.
    
    public int getExtraQueueSize()
    {
        return extraQueueSize;
    }

    public void setExtraQueueSize(int extraQueueSize)
    {
        this.extraQueueSize = extraQueueSize;
    }

    public boolean accept(LogQueue queue, LogEvent logEvent)
    {
        if (LogEvent.MESSAGE_TYPE_LOG_SYSTEM.equals(logEvent.getMessageType()))
        {
            //if this is system generated log message or state change request
            //always accept
            return true;
            
        }
        
        if(logEvent.getSeverity()== LogSeverityEnum.valueOf("ERROR").getIndex())
        {
            int queueSize = queue.size(); 
            if(queueSize < queue.getQueueCapacity()+ extraQueueSize)
            {
                //if extra space is still able to accept this log item
                return true;
            }
        }
        
        return false;
    }
    
}
