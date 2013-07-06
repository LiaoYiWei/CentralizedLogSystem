/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.restful.tool;

import java.util.HashMap;

public class SequenceGenerator
{
    
    private SequenceGenerator() 
    {
        
    }
    private static HashMap<String, Sequence> sequenceMap = new HashMap();
    
    public static synchronized  Sequence getSequence(String sequenceName)
    {
        Sequence seq = sequenceMap.get(sequenceName); 
        if(seq != null)
        {
            return seq; 
        }
        
        seq = new Sequence();
        sequenceMap.put(sequenceName, seq);
        return seq; 
        
    }
    
    

}
