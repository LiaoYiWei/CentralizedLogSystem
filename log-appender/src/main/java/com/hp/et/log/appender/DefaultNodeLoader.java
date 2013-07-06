/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.appender;

public class DefaultNodeLoader implements INodeLoader
{
    private String node;

    public String getNode()
    {
        if(node == null)
        {
            return AppenderUtil.getLocalHostName();
        }
        
        return node;
    }

    public void setNode(String node)
    {
        this.node = node;
    }
    
    

}
