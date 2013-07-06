/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.restful.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;

public class Utility
{
    public static Object inputStreamToObj(InputStream xmlInputStream)
    {
        String rst = inputStreamToStr(xmlInputStream);
        return xmlToObj(rst);
    }
    
    public static Object xmlToObj(String xmlString)
    {
//        XStream xStream = new XStream(new DomDriver());
//        return xStream.fromXML(xmlString);
    	return null;
    }

    public static void objToOutputStream(Object obj, OutputStream output)
    {
        
        String xml = objToXml(obj);
        strToOutputStream(xml,output);
    }
    
    public static String objToXml(Object obj)
    {
//        XStream xStream = new XStream(new DomDriver());
//        String xml = xStream.toXML(obj);
//        return xml;
    	return null;
    }
    
    public static void strToOutputStream(String str, OutputStream output)
    {
        PrintWriter printWriter = new PrintWriter(output);
        printWriter.print(str);
        printWriter.flush();
    }
    public static String inputStreamToStr(InputStream is)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));    
            StringBuffer strBuf = new StringBuffer();
            String str = reader.readLine();        
            while(str != null)
            {
                strBuf.append(str);
                str = reader.readLine();
            }
            
            return strBuf.toString().trim();
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
    
    public static void inputStreamToOutputStream(InputStream inStream, OutputStream outStream) throws IOException
    {
        PrintWriter printWritter = new PrintWriter(new OutputStreamWriter(outStream));
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(inStream));
        String line;
        while((line = bufReader.readLine()) != null )
        {
            printWritter.println(line);
            
        }
        printWritter.flush();
        //outStream.flush();
        
    }
    
    public static String dateFormat2String(Date time, String format){
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
	    return formatter.format(time);
    }
    
}
