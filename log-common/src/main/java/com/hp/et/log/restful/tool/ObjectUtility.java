package com.hp.et.log.restful.tool;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;


public class ObjectUtility {

	public static Object inputStreamToObj(InputStream xmlInputStream) throws IOException, ClassNotFoundException
    {
		ObjectInputStream ois = new ObjectInputStream(xmlInputStream);
		Object obj = ois.readObject();
		ois.close();
        return obj;
    }
    
    public static void objToOutputStream(Object obj, OutputStream output) throws IOException
    {
        
       
       if(obj instanceof String)
       {
           BufferedOutputStream bStream = new BufferedOutputStream(output);
           PrintStream pStream = new PrintStream(output);
           pStream.print((String) obj);           
           pStream.close();
       }
       else
       {
           ObjectOutputStream oos = new ObjectOutputStream(output);
           oos.writeObject(obj);
           oos.close();
       }
       
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
}
