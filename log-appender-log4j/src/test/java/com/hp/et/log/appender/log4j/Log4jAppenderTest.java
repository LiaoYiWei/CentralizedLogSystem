/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */


package com.hp.et.log.appender.log4j;

import java.io.FileNotFoundException;
import java.net.InetAddress;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Description goes here.
 */
public class Log4jAppenderTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
        throws FileNotFoundException
    {

        try
        {
            InetAddress thisIp = InetAddress.getLocalHost();
            System.out.println("IP:" + thisIp.getHostAddress());

            java.net.InetAddress addr = java.net.InetAddress.getLocalHost();
            String hostname = addr.getHostName();
            System.out.println("Hostname of system = " + hostname);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        System.setProperty("sbs.lifecycle", "DEV");
        Logger logger = LoggerFactory.getLogger("Test");

        Exception ex = new RuntimeException("this is test exception");
        Exception ex2 = new RuntimeException("the second error", ex);
        Exception ex3 = new RuntimeException("this is the third exception");
        MDC.put("testKey1", "testValue1");
        MDC.put("testKey2", "testValue2");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++)
        {
            System.out.println("write " + i + " log item");
            logger.info("test appender");
            //        try
            //        {
            //            Thread.currentThread().sleep(1);
            //        }
            //        catch (InterruptedException e)
            //        {
            //            // TODO Auto-generated catch block
            //            e.printStackTrace();
            //        }
        }

        long end = System.currentTimeMillis();
        System.out.println("spent time:" + (end - start) / 1000);

        while (true)
        {
            try
            {
                Thread.currentThread().sleep(10000);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
