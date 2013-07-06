/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URL;
import java.util.Enumeration;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import com.hp.et.log.configure.Configure;
import com.hp.et.log.restful.tool.Utility;
import com.hp.et.log.rules.IRuleEngineAware;

/**
 * Note: This is not a common FTP service
 * Description goes here.
 */
@Path("/fileTransfer")
public class FileTransferResource
{
	private IRuleEngineAware ruleEngineAware; 
    /**
     * 
     * Download groovy file for the specific application ID
     *
     * @param applicationID
     * @return
     */
    @POST
    @Path("downloadGroovy")
    @Produces("text/plain")
    public File downloadGroovy(String applicationID) {
    	String fileURL = getGroovyFileURL(Configure.getProperty("groovy.file.location"), Configure.getProperty("groovy.file.name"), applicationID);
        //the first step, query the grovvy file in specific folder 
        final File groovyFile = new File(fileURL);
        
        if(!groovyFile.exists())
        {
            throw new RuntimeException("File do not exist!");
        }
        
        return groovyFile;
        
//       return new StreamingOutput() {
//          public void write(OutputStream outputStream) throws IOException, WebApplicationException {
//            try
//            {
//                outputFile(outputStream, groovyFile);
//            }
//            catch (Exception e)
//            {
//                // TODO Auto-generated catch block
//                throw new RuntimeException(e);
//            }
//          }
//       };
    }

//    protected void outputFile(OutputStream outputStream, File file) throws Exception
//    {
//        Utility.inputStreamToOutputStream(new FileInputStream(file), outputStream);       
//    }
    
    @POST
    @Path("uploadGroovy/{appID}")
    public Void uploadGroovy(@PathParam("appID") String appID, InputStream grovyInStream ) {
        String fileURL = getGroovyFileURL(Configure.getProperty("groovy.file.location"), Configure.getProperty("groovy.file.name"), appID);
        final File groovyFile = new File(fileURL);
        FileOutputStream fStream = null;
        try
        {
            if(!groovyFile.exists())
            {
                groovyFile.createNewFile();
            }
            fStream = new FileOutputStream(groovyFile);
            Utility.inputStreamToOutputStream(grovyInStream, fStream);
            
            ruleEngineAware.ruleEngineTrigger(appID);
            
            return null;
            
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
        finally {
            if(fStream != null)
            {
                try{
                    fStream.close();
                }
                catch(Exception ex) {}
            }
        }
        
        
    }
    
    
    private String getGroovyFileURL(String dir, String fileName, String appId) {
		StringBuilder fileURL = new StringBuilder();
		fileURL.append(dir);
		fileURL.append(fileName);
		fileURL.append(appId);
		fileURL.append(".groovy");
		return fileURL.toString();
	}

    
    
	public IRuleEngineAware getRuleEngineAware() {
		return ruleEngineAware;
	}

	public void setRuleEngineAware(IRuleEngineAware ruleEngineAware) {
		this.ruleEngineAware = ruleEngineAware;
	}
    
    
}
