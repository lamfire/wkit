package com.lamfire.wkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
final class MultiPartRequestWrapper extends AbstractRequestWrapper{

    Collection<String> errors;
    MultiPartRequest  multi;

    public MultiPartRequestWrapper(MultiPartRequest multiPartRequest, HttpServletRequest request, String saveDir) {
	super(request);
	multi = multiPartRequest;
        try {
            multi.parse(request, saveDir);
            for (Object o : multi.getErrors()) {
                String error = (String) o;
                addError(error);
            }
        } catch (IOException e) {
            addError("Cannot parse request: "+e.toString());
        } 
    }

    public Enumeration<String> getFileParameterNames() {
        if (multi == null) {
            return null;
        }

        return multi.getFileParameterNames();
    }

    public String[] getContentTypes(String name) {
        if (multi == null) {
            return null;
        }

        return multi.getContentType(name);
    }

    public File[] getFiles(String fieldName) {
        if (multi == null) {
            return null;
        }

        return multi.getFile(fieldName);
    }

    public String[] getFileNames(String fieldName) {
        if (multi == null) {
            return null;
        }

        return multi.getFileNames(fieldName);
    }

    public String[] getFileSystemNames(String fieldName) {
        if (multi == null) {
            return null;
        }

        return multi.getFilesystemName(fieldName);
    }

    public String getParameter(String name) {
        return ((multi == null) || (multi.getParameter(name) == null)) ? super.getParameter(name) : multi.getParameter(name);
    }

    public Map getParameterMap() {
        Map<String, String[]> map = new HashMap<String, String[]>();
        Enumeration enumeration = getParameterNames();

        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            map.put(name, this.getParameterValues(name));
        }

        return map;
    }

    public Enumeration getParameterNames() {
        if (multi == null) {
            return super.getParameterNames();
        } else {
            return mergeParams(multi.getParameterNames(), super.getParameterNames());
        }
    }

    public String[] getParameterValues(String name) {
        return ((multi == null) || (multi.getParameterValues(name) == null)) ? super.getParameterValues(name) : multi.getParameterValues(name);
    }

    public boolean hasErrors() {
        return !((errors == null) || errors.isEmpty());
    }

    public Collection<String> getErrors() {
        return errors;
    }

    protected void addError(String anErrorMessage) {
        if (errors == null) {
            errors = new ArrayList<String>();
        }

        errors.add(anErrorMessage);
    }

    protected Enumeration mergeParams(Enumeration params1, Enumeration params2) {
        Vector temp = new Vector();

        while (params1.hasMoreElements()) {
            temp.add(params1.nextElement());
        }

        while (params2.hasMoreElements()) {
            temp.add(params2.nextElement());
        }

        return temp.elements();
    }


	public String[] getContentType(String fieldName) {
		return multi.getContentType(fieldName);
	}


	public File[] getFile(String fieldName) {
		return multi.getFile(fieldName);
	}


	public MultiPartFile[] getMultiPartFile(String fieldName) {
		return multi.getMultiPartFile(fieldName);
	}


	public String[] getFilesystemName(String fieldName) {
		return multi.getFilesystemName(fieldName);
	}

	
}
