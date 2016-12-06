package com.lamfire.wkit;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Bytes;
import com.lamfire.utils.JvmInfo;
import com.lamfire.utils.NumberUtils;
import com.lamfire.utils.StringUtils;

public final class Version {
	private static final Logger LOGGER = Logger.getLogger(Version.class);
	public static final String VERSION = "2.0.5";

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

	int[] getJavaVersions(String version){
		int[] versions = new int[4];
		String[] jvs = StringUtils.split(version,".");
		String[] minVs = StringUtils.split(jvs[2],'_');

		versions[0] = NumberUtils.toInt(jvs[0],0);
		versions[1] = NumberUtils.toInt(jvs[1],0);
		versions[2] = NumberUtils.toInt(minVs[0],0);
		versions[3] = NumberUtils.toInt(minVs[1],0);
		return versions;
	}

	public boolean isJavaVersionAndLater(String version){
		int[] versions = getJavaVersions(version);
		return isJavaVersionAndLater(versions[0],versions[1],versions[2],versions[3]);
	}

	public boolean isJavaVersionAndLater(int ... versions){
		String javaVer = getJavaVersion();
		int[] jvs = getJavaVersions(javaVer);

		for(int i=0;i<jvs.length;i++){
			if(i >= versions.length){
				break;
			}
			if(jvs[i] < versions[i]){
				return false;
			}
		}

		return true;
	}

}
