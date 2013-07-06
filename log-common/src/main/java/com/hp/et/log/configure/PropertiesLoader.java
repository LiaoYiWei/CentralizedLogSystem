/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.et.log.configure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Description goes here.
 */
public final class PropertiesLoader
{
    private PropertiesLoader()
    {

    }

    public static Properties loadProperties(String name)
    {
        Properties prop = new Properties();

        try
        {
            InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(name);
            prop.load(is);
            is.close();
        }
        catch (IOException e)
        {
//            logger.error("the datasource properties file is not exist.", e);
        }
        return prop;
    }
}
