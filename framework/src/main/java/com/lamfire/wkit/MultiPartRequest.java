package com.lamfire.wkit;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


interface MultiPartRequest {

    public void parse(HttpServletRequest request, String saveDir) throws IOException;

    public Enumeration<String> getFileParameterNames();

    public String[] getContentType(String fieldName);

    public File[] getFile(String fieldName);

    public MultiPartFile[] getMultiPartFile(String fieldName);

    public String[] getFileNames(String fieldName);

    public String[] getFilesystemName(String fieldName);

    public String getParameter(String name);

    public Enumeration<String> getParameterNames();

    public String[] getParameterValues(String name);

    public List<String> getErrors();

}
