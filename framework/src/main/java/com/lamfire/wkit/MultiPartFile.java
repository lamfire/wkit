package com.lamfire.wkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;

public class MultiPartFile {

	private FileItem fileItem;
	
	public MultiPartFile(FileItem fileItem){
		this.fileItem = fileItem;
	}
	
	public String getFieldName(){
		if(this.fileItem == null)return null;
		return this.fileItem.getFieldName();
	}
	
	public String getFileName(){
		if(this.fileItem == null)return null;
		return this.fileItem.getName();
	}
	
	public long getFileSize(){
		if(this.fileItem == null)return -1;
		return this.fileItem.getSize();
	}

	public byte[] getBytes(){
		if(this.fileItem == null)return null;
		return this.fileItem.get();
	}
	
	public void saveAs(File file) throws IOException{
		if(this.fileItem == null || file == null){
			throw new FileNotFoundException();
		}
		try {
			this.fileItem.write(file);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	public void delete(){
		if(this.fileItem == null)return;
		this.fileItem.delete();
	}

	protected void finalize() throws Throwable {
		delete();
	}
	
	public String getContentType(){
		if(this.fileItem == null)return null;
		return this.fileItem.getContentType();
	}
	
	public FileItem getFileItem(){
		return this.fileItem;
	}
	
	public String toString(){
		if(this.fileItem == null)return null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(getFieldName() +  "=" + getFileName());
		buffer.append(", size=" + getFileSize());
		return buffer.toString();
	}
}
