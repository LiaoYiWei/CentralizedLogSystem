package com.hp.et.log.mail.util;



public class MailException extends Exception{
	
	public MailException(){super();}
   
   
    public MailException( String message){
    	super(message);
       
    }
    
    
    public MailException(Throwable cause) {
		super(cause);
	}
    
}
