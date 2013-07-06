/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.appender;


public class DefaultEnvLoader implements IEnvLoader
{
    private String env;


    public String getEnv()
    {
        if(env == null)
        {
            env = "DefaultEnv";
        }
        return env;
    }

    public void setEnv(String env)
    {
        
        this.env = env;
    }
    

}
