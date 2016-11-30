package com.lamfire.wkit;

import com.lamfire.logger.Logger;
import com.lamfire.utils.JvmInfo;
import com.lamfire.utils.StringUtils;

public final class Version {
	private static final Logger LOGGER = Logger.getLogger(Version.class);
	public static final String VERSION = "2.0.3";

	static{
		JvmInfo jvm = JvmInfo.getInstance();
		String jdkVer = jvm.getJdkVersion();
		LOGGER.info("Java version : " + jdkVer);
	}

	private static final Version instance = new Version();

	public static Version getInstance(){
		return instance;
	}

	private Version(){

	}

	public String getJavaVersion(){
		JvmInfo jvm = JvmInfo.getInstance();
		return jvm.getJdkVersion();
	}

	public String getVersion(){
		return VERSION;
	}

	public boolean isJavaVersion(String version){
		return StringUtils.isStartWith(getJavaVersion(),version);
	}
}
