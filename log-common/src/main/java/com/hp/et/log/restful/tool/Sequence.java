/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.restful.tool;

public class Sequence
{
    
    private long value=0;
    private int incrementBy = 1; 
    
    public Sequence(int startFrom, int incrementBy)
    {
        this.value = startFrom; 
        this.incrementBy = incrementBy; 
    }
    
    public Sequence()
    {
        this.value = 0; 
        this.incrementBy = 1; 
    }
        
    
    public synchronized long currentVal()
    {
        return value; 
    }
    
    public synchronized long nextVal()
    {
        value = value + incrementBy; 
        return value;
    }
    

}
