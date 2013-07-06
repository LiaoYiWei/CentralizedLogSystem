package com.hp.it.et.log.utils;
import org.apache.log4j.Logger;

public class BusinessUtils {
	
	private static final char FILE_SEPARATOR_WINDOWS = '\\';

    private static final char FILE_SEPARATOR_UNIXS = '/';
    
    private static final Logger log = Logger.getLogger(BusinessUtils.class);

	public static String fileRealName(String fileName) {
        char file_operator = FILE_SEPARATOR_WINDOWS;
        if (fileName.indexOf(FILE_SEPARATOR_UNIXS) > -1) {
            file_operator = FILE_SEPARATOR_UNIXS;
        }
        int lastIndex = fileName.lastIndexOf(file_operator);
        fileName = fileName.substring(lastIndex + 1, fileName.length());
        return fileName;
    }
	
	/**
     * @param millis
     * @return
     */
    public static long second2Millis(long second) {
        return (second * 1000);
    }

    /**
     * change M to byte
     * @param k
     * @return
     */
    public static long m2k(long k) {
        return (k * 1024 * 1000);
    }

}
