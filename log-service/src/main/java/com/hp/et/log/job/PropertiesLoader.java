/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.et.log.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Description goes here.
 */
public final class PropertiesLoader
{
    private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    private PropertiesLoader()
    {

    }

    public static Properties loadProperties(String path)
    {
        Properties prop = new Properties();

        try
        {
            InputStream is = new ClassPathResource(path).getInputStream();
            prop.load(is);
            is.close();
        }
        catch (IOException e)
        {
            logger.error("the datasource properties file is not exist.", e);
        }
        return prop;
    }
}
