/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.configure;

import java.io.IOException;
import java.util.Properties;

public class Configure
{
    private static Properties props = new Properties();
    static 
    {
        try
        {
            props.load(Configure.class.getClassLoader().getResourceAsStream("appConfig.properties"));
        }
        catch (IOException e)
        {
           throw new RuntimeException(e);
        }
    }
    public static String getProperty(String propertyKey)
    {
        String val = System.getProperty(propertyKey);
        if(val != null)
        {
            return val;                        
        }
        else
        {
            return props.getProperty(propertyKey);
        }
            
    }
}
