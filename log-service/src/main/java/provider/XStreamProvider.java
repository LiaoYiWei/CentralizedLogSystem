/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.hp.et.log.restful.tool.Utility;
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;

@Provider
@Produces("application/xstream_xml")	//'application/java_serializable'
@Consumes("application/xstream_xml")
public class XStreamProvider implements MessageBodyReader, MessageBodyWriter
{

    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {   
        //always return true
        //do not force to implement Serializable
        return true;
    }

    public long getSize(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        // TODO Auto-generated method stub
        
        return -1;
    }

    public void writeTo(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders,
            OutputStream entityStream)
        throws IOException, WebApplicationException
    {
    	// ObjectOutPutStream/ObjectInputStream
        Utility.objToOutputStream(t, entityStream);	 
//        XStream xStream = new XStream(new DomDriver());
//        String xmlStr =  xStream.toXML(t);
//        OutputStreamWriter streamWriter = new OutputStreamWriter(entityStream);
//        streamWriter.write(xmlStr, 0, xmlStr.length());
//        streamWriter.flush();
    }

    public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        
        return true;
    }

    public Object readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders,
            InputStream entityStream)
        throws IOException, WebApplicationException
    {
        return Utility.inputStreamToObj(entityStream);
        
    }
    
    private String readString(InputStream is)
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
    
}
