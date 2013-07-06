package com.hp.it.et.log.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class ServletFileUploadSimple extends FileUpload {
    
    private Map paramMap = new HashMap();
    
    private FileItem fileItem;
    
//    private FileItemStream fileTimeStream;
    
    private static final int DEFAULT_BUFFER_SIZE = 15 * 1024;
    
    private static long maxSize;
    
    private static final Logger log = Logger.getLogger(ServletFileUploadSimple.class);

    /**
     * @param fileItemFactory
     */
    public ServletFileUploadSimple(FileItemFactory fileItemFactory) {
        super(fileItemFactory);
    }
    
    /**
     * multipart type
     * @param request
     * @return 
     */
    public static final boolean isMultipartContent(HttpServletRequest request) {
        if (false == "post".equals(request.getMethod().toLowerCase())) {
            return false;
        }
        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        }
        if (false == contentType.toLowerCase().startsWith(MULTIPART)) {
            return false;
        }
        return true;
    }
    
    /**
     * size
     * @param request
     * @throws Exception 
     * @throws UploadException
     */
    public static final void fileIsOverMaxSize(HttpServletRequest request) throws Exception{
        if (request.getContentLength() > maxSize) {
            throw new Exception("file is too big");
        }
    }
    
    /**
     * parseRequestHeader
     * @param request
     */
    public void parseRequestHeader(HttpServletRequest request) {
        ServletRequestContext ctx = new ServletRequestContext(request);
        try {
            FileItemIterator iter = getItemIterator(ctx);
            FileItemFactory fac = getFileItemFactory();
            Assert.notNull(fac, "No FileItemFactory has been set.");
            
            int count = 0;
            while (iter.hasNext()) {
                FileItemStream  fileTimeStream = iter.next();
                if (fileTimeStream.isFormField()) {
//                    log.info("fileTimeStream.getFieldName():" + fileTimeStream.getFieldName());////
                    paramMap.put(fileTimeStream.getFieldName(), Streams.asString(fileTimeStream.openStream(), "UTF-8"));
                } else {
//                    log.info("fileTimeStream.getName():" + fileTimeStream.getName());////
                    fileItem = fac.createItem(fileTimeStream.getFieldName(), fileTimeStream.getContentType(), fileTimeStream.isFormField(), fileTimeStream.getName());
                    Streams.copy(fileTimeStream.openStream(), fileItem.getOutputStream(), true);
                }
            }
        } catch (FileUploadException ex) {
            ReflectionUtils.handleReflectionException(ex);
        } catch (FileUploadIOException ex) {
            ReflectionUtils.handleReflectionException(ex);
        } catch (IOException ex) {
            ReflectionUtils.handleReflectionException(ex);
        }
    }

    /**
     * 
     * @throws FileUploadIOException
     * @throws IOException
     */
//    public byte[] parseRequestBody() {
//        try {
//            Streams.copy(fileTimeStream.openStream(), fileItem.getOutputStream(), true);
//            return fileItem.get();
//        } catch (IOException ex) {
//            ReflectionUtils.handleReflectionException(ex);
//        }
//        return null;
//    }

    public void setMaxSize(long maxSize) {
        this.maxSize = BusinessUtils.m2k(maxSize);
    }

    public FileItem getFileItem() {
        return fileItem;
    }

    public Map getParamMap() {
        return paramMap;
    }
}
