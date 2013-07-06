package com.hp.et.log.appender.logback;

import java.io.FileNotFoundException;
import java.net.InetAddress;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.Logger;


public class LogbackAppenderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException {
	    
	    try {
	        InetAddress thisIp =InetAddress.getLocalHost();
	        System.out.println("IP:"+thisIp.getHostAddress());
	        
	        java.net.InetAddress addr = java.net.InetAddress.getLocalHost();
	        String hostname = addr.getHostName();
	        System.out.println("Hostname of system = " + hostname);
	      
	    
	        }
	    catch(Exception ex)
	    {
	        ex.printStackTrace();
	    }
	    
	    System.setProperty("sbs.lifecycle", "DEV");
        Logger logger = (Logger)LoggerFactory.getLogger("logbackTest");
		
		
/*		File file = new File("C:\\Users\\lyi\\Desktop\\AppenderTest.txt");
		OutputStream stream = new FileOutputStream(file.getPath());
		
		Event event = new Event();
		event.setAppId("1");
		event.setDate(new Date());
		event.setEnv("LOCAL");
		event.setHost("123456");
		event.setId(9);
		event.setMessage("ABC");
		event.setSeverity("AAA");
		try {
			//JAXBContext jc = JAXBContext.newInstance("com.hp.et.log.domain");
			JAXBContext jc = JAXBContext.newInstance(Event.class);
			Marshaller m = jc.createMarshaller();
			m.marshal(event, stream);
			System.out.println(stream.toString());
		} catch (JAXBException e) {
			e.printStackTrace();
		}*/
		Exception ex = new RuntimeException("this is test exception");
		Exception ex2 = new RuntimeException ("the second error", ex);
		Exception ex3 = new RuntimeException("this is the third exception");
		MDC.put("testKey1", "testValue1");
		MDC.put("testKey2", "testValue2");
		
		long start = System.currentTimeMillis();
		for(int i=0; i<1; i++)
		{
		  System.out.println("write " + i + " log item");
		  logger.info("info test appender");
//		  logger.error("error test appnder");
//		  logger.warn("warn appender");
//		  logger.error("error appender");
		  
		try
        {
            Thread.currentThread().sleep(100);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		}
		
		long end = System.currentTimeMillis();
		System.out.println("spent time:" + (end - start)/1000);
		
		while(true)
		{
		    try
            {
                Thread.currentThread().sleep(10000);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}
	}

}
