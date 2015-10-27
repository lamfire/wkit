package com.lamfire.wkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lamfire.logger.Logger;

@SuppressWarnings("unchecked")
final class MultiPartRequestImpl implements MultiPartRequest {

	static final Logger LOG = Logger.getLogger(MultiPartRequestImpl.class);

	protected Map<String, List<MultiPartFile>> files = new HashMap<String, List<MultiPartFile>>();

	protected Map<String, List<String>> params = new HashMap<String, List<String>>();

	protected List<String> errors = new ArrayList<String>();

	protected long maxSize = 2 * 1024 * 1024; // upload file limit 2MB

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public void parse(HttpServletRequest request, String saveDir) throws IOException {
		try {
			processUpload(request, saveDir);
		} catch (FileUploadException e) {
			LOG.warn("Unable to parse request", e);
			errors.add(e.getMessage());
		}
	}

	private void processUpload(HttpServletRequest request, String saveDir) throws FileUploadException, UnsupportedEncodingException {
		for (FileItem item : parseRequest(request, saveDir)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Found field:" + item.getFieldName());
			}
			if (item.isFormField()) {
				processNormalFormField(item, request.getCharacterEncoding());
			} else {
				processFileField(item);
			}
		}
	}

	private void processFileField(FileItem item) {
		if (item.getName() == null || item.getName().trim().length() < 1) {
			LOG.debug("No file has been uploaded for the field: " + item.getFieldName());
			return;
		}
		
		LOG.debug("Process file field:"+item.getName());
		
		List<MultiPartFile> values;
		if (files.get(item.getFieldName()) != null) {
			values = files.get(item.getFieldName());
		} else {
			values = new ArrayList<MultiPartFile>();
		}

		values.add(new MultiPartFile(item));
		files.put(item.getFieldName(), values);
	}

	private void processNormalFormField(FileItem item, String charset) throws UnsupportedEncodingException {
		List<String> values;
		if (params.get(item.getFieldName()) != null) {
			values = params.get(item.getFieldName());
		} else {
			values = new ArrayList<String>();
		}
		if (charset != null) {
			values.add(item.getString(charset));
		} else {
			values.add(item.getString());
		}
		params.put(item.getFieldName(), values);
	}

	private List<FileItem> parseRequest(HttpServletRequest servletRequest, String saveDir) throws FileUploadException {
		DiskFileItemFactory fac = createDiskFileItemFactory(saveDir);
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setSizeMax(maxSize);
		return upload.parseRequest(createRequestContext(servletRequest));
	}

	private DiskFileItemFactory createDiskFileItemFactory(String saveDir) {
		DiskFileItemFactory fac = new DiskFileItemFactory();
		fac.setSizeThreshold(0);
		if (saveDir != null) {
			File dir = new File(saveDir);
			if (!dir.exists())
				dir.mkdirs();
			fac.setRepository(dir);
		}
		return fac;
	}

	public Enumeration<String> getFileParameterNames() {
		return Collections.enumeration(files.keySet());
	}


	public String[] getContentType(String fieldName) {
		List<MultiPartFile> items = files.get(fieldName);

		if (items == null) {
			return null;
		}

		List<String> contentTypes = new ArrayList<String>(items.size());
		for (MultiPartFile fileItem : items) {
			contentTypes.add(fileItem.getContentType());
		}

		return contentTypes.toArray(new String[contentTypes.size()]);
	}


	public File[] getFile(String fieldName) {
		List<MultiPartFile> items = files.get(fieldName);

		if (items == null) {
			return null;
		}

		List<File> fileList = new ArrayList<File>(items.size());
		for (MultiPartFile fileItem : items) {
			fileList.add(((DiskFileItem) fileItem.getFileItem()).getStoreLocation());
		}

		return fileList.toArray(new File[fileList.size()]);
	}
	
	public MultiPartFile[] getMultiPartFile(String fieldName){
		List<MultiPartFile> items = files.get(fieldName);
		if(items==null){
			return null;
		}
	
		return items.toArray(new MultiPartFile[items.size()]);
	}

	public String[] getFileNames(String fieldName) {
		List<MultiPartFile> items = files.get(fieldName);

		if (items == null) {
			return null;
		}

		List<String> fileNames = new ArrayList<String>(items.size());
		for (MultiPartFile fileItem : items) {
			fileNames.add(getCanonicalName(fileItem.getFileName()));
		}

		return fileNames.toArray(new String[fileNames.size()]);
	}


	public String[] getFilesystemName(String fieldName) {
		List<MultiPartFile> items = files.get(fieldName);

		if (items == null) {
			return null;
		}

		List<String> fileNames = new ArrayList<String>(items.size());
		for (MultiPartFile fileItem : items) {
			fileNames.add(((DiskFileItem) fileItem.getFileItem()).getStoreLocation().getName());
		}

		return fileNames.toArray(new String[fileNames.size()]);
	}


	public String getParameter(String name) {
		List<String> v = params.get(name);
		if (v != null && v.size() > 0) {
			return v.get(0);
		}

		return null;
	}


	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(params.keySet());
	}


	public String[] getParameterValues(String name) {
		List<String> v = params.get(name);
		if (v != null && v.size() > 0) {
			return v.toArray(new String[v.size()]);
		}

		return null;
	}

	public List getErrors() {
		return errors;
	}

	private String getCanonicalName(String filename) {
		int forwardSlash = filename.lastIndexOf("/");
		int backwardSlash = filename.lastIndexOf("\\");
		if (forwardSlash != -1 && forwardSlash > backwardSlash) {
			filename = filename.substring(forwardSlash + 1, filename.length());
		} else if (backwardSlash != -1 && backwardSlash >= forwardSlash) {
			filename = filename.substring(backwardSlash + 1, filename.length());
		}

		return filename;
	}

	private RequestContext createRequestContext(final HttpServletRequest req) {
		return new RequestContext() {
			public String getCharacterEncoding() {
				return req.getCharacterEncoding();
			}

			public String getContentType() {
				return req.getContentType();
			}

			public int getContentLength() {
				return req.getContentLength();
			}

			public InputStream getInputStream() throws IOException {
				InputStream in = req.getInputStream();
				if (in == null) {
					throw new IOException("Missing content in the request");
				}
				return req.getInputStream();
			}
		};
	}
}
