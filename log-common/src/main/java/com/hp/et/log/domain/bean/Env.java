/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.domain.bean;

public enum Env
{
    DEV("DEV"),ITG("ITG"),PROD("PROD");
    
    private String val;
    
    Env(String val)
    {
        this.val = val;
    }
    
    public String toString()
    {
        return val;
    }
    
    public static Env from(String envStr)
    {
        for(Env env: Env.values())
        {
            if(env.toString().equals(envStr))
            {
                return env;
            }
        }
        return null;
    } 
}
