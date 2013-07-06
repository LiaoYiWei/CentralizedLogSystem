/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.appender;

import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogSeverityEnum;

public class SuppressSystemLogPolicy implements ISuppressLogPolicy
{

    private int suppressTimeoutSec = 30; //by seconds
    
    private long previousTimeStamp = 0;
    
    public int getSuppressTimeoutSec()
    {
        return suppressTimeoutSec;
    }


    public void setSuppressTimeoutSec(int suppressTimeoutSec)
    {
        this.suppressTimeoutSec = suppressTimeoutSec;
    }


    public boolean suppressLog(LogEvent log)
    {
        if(LogEvent.MESSAGE_TYPE_LOG_SYSTEM.equals(log.getMessageType()) && log.getSeverity() == LogSeverityEnum.valueOf("ERROR").getIndex())
        {
            long currentTime = System.currentTimeMillis();
            if(currentTime - previousTimeStamp < suppressTimeoutSec * 1000)
            {
                //should suppress this log
                return true;
            }
            else
            {
                //should NOT suppress
                //reset previousTimeStamp 
                previousTimeStamp = currentTime;
                return false;
            }
        }
        
        //for all the others, do NOT suppress
        return false;
    }

}
