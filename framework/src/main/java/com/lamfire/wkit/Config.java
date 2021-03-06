package com.lamfire.wkit;

class Config{
	
	public static final long DEFAULT_MULTIPART_LIMIT = 1024 * 1024;
	public static final String DEFAULT_MULTIPART_TEMPDIR = System.getProperty("java.io.tmpdir");
	
	private String mappingPackage;
	private String multipartTempDir = DEFAULT_MULTIPART_TEMPDIR;
	private long multipartLimit = DEFAULT_MULTIPART_LIMIT;

	
	public String getMappingPackage() {
		return mappingPackage;
	}
	public void setMappingPackage(String mappingPackage) {
		this.mappingPackage = mappingPackage;
	}
	public String getMultipartTempDir() {
		return multipartTempDir;
	}
	public void setMultipartTempDir(String multipartTempDir) {
		this.multipartTempDir = multipartTempDir;
	}
	public long getMultipartLimit() {
		return multipartLimit;
	}
	public void setMultipartLimit(long multipartLimit) {
		this.multipartLimit = multipartLimit;
	}
}
