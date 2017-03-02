package com.leafo3.service;

import com.leafo3.service.FileService.FileType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
public interface FileService {
    public String saveImage(InputStream content, String fileName, FileType fileType)throws IOException;
    public Object[] processImage(String path)throws IOException;
    public InputStream getStream(FileType type, String leafId)throws IOException;
    public File getAndroidApplicationFile()throws IOException; 
    public static enum FileType{
        ORIGINAL, PROCESSED;
    }
}
