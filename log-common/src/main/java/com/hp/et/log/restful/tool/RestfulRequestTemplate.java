/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.restful.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;

public abstract class RestfulRequestTemplate
{
    //private static JAXBContext jaxbContext = null;

//    static
//    {
//
//        try
//        {
//            jaxbContext = JAXBContext.newInstance(ApplicationInfo.class, ApplicationInstance.class, LogEvent.class,Void.class, HostInfo.class, ArrayList.class);
//        }
//        catch (JAXBException e)
//        {
//            // TODO Auto-generated catch block
//            throw new RuntimeException(e);
//        }
//
//    }
   
    public Object execute(String serverUrl, String actionUrl)
    {
        HttpURLConnection httpConn = null;
        OutputStream out =  null;
        InputStream is = null;
        try
        {
       
        URL url = new URL(serverUrl+ actionUrl);
        //System.out.println(Thread.currentThread().getName()+"-----------------begin to connect-------------------");
       // long startTime = System.currentTimeMillis(); 
        httpConn = (HttpURLConnection) url.openConnection();
        //long endTime = System.currentTimeMillis();
        //System.out.println(Thread.currentThread().getName()+"------------------Connected-----------------"+ (endTime-startTime)/1000);
//        httpConn.setRequestProperty("Content-Type", "application/xstream_xml");
        httpConn.setRequestProperty("Content-Type", "application/java_serializable");
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        
        out = httpConn.getOutputStream();
        //System.out.println(Thread.currentThread().getName()+"--------------------submit data--------------------");
        //startTime = System.currentTimeMillis(); 
        onSubmitRequest(httpConn,out);
        out.flush();
        //endTime = System.currentTimeMillis();
       // System.out.println(Thread.currentThread().getName()+ "--------------------submit done--------------------"+ (endTime-startTime)/1000);
        
       // System.out.println(Thread.currentThread().getName()+"--------------------get Response Code--------------------");
       // startTime = System.currentTimeMillis();
        int responseCode =  httpConn.getResponseCode();
       // endTime = System.currentTimeMillis();
       // System.out.println(Thread.currentThread().getName()+"--------------------Response Code done--------------------"+ (endTime-startTime)/1000);
        
        if(httpConn.getResponseCode() >= 400)
        {
            if (httpConn != null) {
                InputStreamReader errStream = new InputStreamReader(
                  httpConn.getErrorStream());
                BufferedReader br = new BufferedReader(errStream);
                String read = br.readLine(); 

                while (read != null) {
                 System.out.println(read);
                 read = br.readLine();
                }
               }


            throw new Exception("HTTP ERROR:" + httpConn.getResponseCode());
        }
        
        
        is = httpConn.getInputStream();
       // System.out.println(Thread.currentThread().getName()+"--------------------process response--------------------");
      //  startTime = System.currentTimeMillis();
        Object rst = onProcessResponse(httpConn,is);
       // endTime = System.currentTimeMillis();
       // System.out.println(Thread.currentThread().getName()+"--------------------process response done--------------------"+ (endTime-startTime)/1000);
        return rst;
        
//        if(outputClazz == String.class)
//        {
//            
//            
//            return rst;
//        }
//        
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//        
//        Object rst = unmarshaller.unmarshal(is);
//        
//        return  rst;
        
        
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
        finally{
            //System.out.println(Thread.currentThread().getName()+ "begin to close input stream");
            //long startTime = System.currentTimeMillis();
            closeInputStream(is);
            //long endTime = System.currentTimeMillis();
            //System.out.println(Thread.currentThread().getName()+ "end to close input stream" + (endTime-startTime)/1000);
            
            //System.out.println(Thread.currentThread().getName()+ "begin to close output stream");
            //startTime = System.currentTimeMillis();
            closeOutputStream(out);
            //endTime = System.currentTimeMillis();
            //System.out.println(Thread.currentThread().getName()+ "end to close output stream" + (endTime-startTime)/1000);
            
            //System.out.println(Thread.currentThread().getName()+ "begin to close connection");
            //startTime = System.currentTimeMillis();
            closeHttpConnection(httpConn);
            //endTime = System.currentTimeMillis();
            //System.out.println(Thread.currentThread().getName()+ "end to close connection" + (endTime-startTime)/1000);
           
        }
        
    }

    

    private void closeInputStream(InputStream is)
    {
        try
        {
            if (is != null)
            {
                is.close();
            }
        }
        catch (Exception ex)
        {
        }

    }

    private void closeHttpConnection(HttpURLConnection httpConn)
    {
        try
        {
            if (httpConn != null)
            {
                httpConn.disconnect();
            }
        }
        catch (Exception ex)
        {
        }

    }

    private void closeOutputStream(OutputStream out)
    {
        try
        {
            if (out != null)
            {
                out.close();
            }
        }
        catch (Exception ex)
        {
        }

    }

    public abstract void onSubmitRequest(HttpURLConnection conn, OutputStream outStream) throws Exception;
    
    public abstract Object onProcessResponse(HttpURLConnection conn, InputStream inputStream) throws Exception;

}
