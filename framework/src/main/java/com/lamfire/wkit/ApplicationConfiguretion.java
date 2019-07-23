package com.lamfire.wkit;

import java.util.Map;

import javax.servlet.FilterConfig;

import com.lamfire.logger.Logger;
import com.lamfire.utils.PropertiesUtils;
import com.lamfire.utils.StringUtils;

final class ApplicationConfiguretion {
	static final Logger LOGGER = Logger.getLogger(ApplicationConfiguretion.class);

	private static final String CONF_FILE = "application.properties";
	public static final String MAPPING_PACKAGE = "mapping.package";
	public static final String MULTIPART_TEMPDIR = "multipart.tempdir"; 
	public static final String MULTIPART_LIMIT = "multipart.limit";


	private static Config conf;

	private ApplicationConfiguretion() {
	}

	private static Config parseConfig() {
		Map<String, String> prop = null;
		try {
			prop = PropertiesUtils.loadAsMap(CONF_FILE, ApplicationConfiguretion.class);
		}catch (Exception e){
			throw new RuntimeException("The application can not load config file:" + CONF_FILE);
		}
		LOGGER.info("found properties : " + prop);
		LOGGER.info("Parse Config from "+CONF_FILE);

		String mappingPackage = prop.get(MAPPING_PACKAGE);
		String tempDir = prop.get(MULTIPART_TEMPDIR);
		String maxSize = prop.get(MULTIPART_LIMIT);

		if (StringUtils.isBlank(mappingPackage)) {
			throw new RuntimeException("The configure item 'package.root' not found.");
		}

		Config conf = new Config();
		conf.setMappingPackage(mappingPackage.trim());

		if (StringUtils.isNotBlank(maxSize)) {
			try {
				long v = Long.parseLong(maxSize.trim());
				conf.setMultipartLimit(v);
			} catch (Exception e) {
				conf.setMultipartLimit(Config.DEFAULT_MULTIPART_LIMIT);
				LOGGER.warn("Parser property 'multipart-limit' from 'application.properties' catch Exception,use default :" + Config.DEFAULT_MULTIPART_LIMIT);
			}
		}

		if (StringUtils.isNotBlank(tempDir)) {
			conf.setMultipartTempDir(tempDir.trim());
		}
		return conf;

	}

	private static Config parseConfig(FilterConfig filterConfig) {
		String mappingPackage = filterConfig.getInitParameter(MAPPING_PACKAGE);
		String tempDir = filterConfig.getInitParameter(MULTIPART_TEMPDIR);
		String maxSize = filterConfig.getInitParameter(MULTIPART_LIMIT);

		LOGGER.info("Parse Config from FilterConfig");

		if (StringUtils.isBlank(mappingPackage)) {
			LOGGER.error("Not found '"+MAPPING_PACKAGE+"' from web.xml");
			return null;
		}

		Config conf = new Config();
		conf.setMappingPackage(mappingPackage.trim());

		if (StringUtils.isNotBlank(maxSize)) {
			try {
				long v = Long.parseLong(maxSize.trim());
				conf.setMultipartLimit(v);
			} catch (Exception e) {
				conf.setMultipartLimit(Config.DEFAULT_MULTIPART_LIMIT);
				LOGGER.warn("Parser init-parameter 'multipart-limit' from web.xml catch Exception,use default :" + Config.DEFAULT_MULTIPART_LIMIT);
			}
		}

		if (StringUtils.isNotBlank(tempDir)) {
			conf.setMultipartTempDir(tempDir.trim());
		}

		return conf;
	}

	/**
	 * 得到本实例
	 * 
	 * @return
	 */
	static synchronized Config getConfig(FilterConfig filterConfig) {
		if (conf != null) {
			return conf;
		}

		//parse from filter config
		if (conf == null) {
			conf = parseConfig(filterConfig);
		}

		//parse from application.properties
		if (conf == null) {
			conf = parseConfig();
		}

		return conf;
	}

	public static Config getConfig() {
		return conf;
	}
}
