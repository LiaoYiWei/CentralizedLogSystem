package com.hp.et.log.mail.util;

public class AddressPaser {
	
	/**
	 * parse address(seperated by ; or , or ; or : into address array, also support blank space
	 * @param address
	 * @return
	 */
	public static String[] parseAddress(String address)
	{
		if(address == null)
		{
			return null;
		}
		
		
		
		address = address.replaceAll(";", " ");
		address = address.replaceAll(",", " ");
		address = address.replaceAll(":", " ");
		
		address = removeSpaces(address.trim());
		
		if( address.indexOf(' ') != -1)
		{
			return address.split(" ");
		}
		else
		{
			String[] addrStrs = new String[1];
			addrStrs[0] = new String(address);
			return addrStrs;
		}
		
	}
	/**
	 * Remove all the consecutive spaces to be only one space, it will also do the trim for the heading and tail. 
	 * For example, string "  aa     bb     cc  " will be converted to "aa bb cc"
	 * @param content
	 * @return
	 */
	public static String removeSpaces(String content)
	{
		if(content == null)
			return null;
		return content.trim().replaceAll("[\\s]+"," ");
		
		
	}

}
