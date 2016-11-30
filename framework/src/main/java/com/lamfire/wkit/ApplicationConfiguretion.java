package com.lamfire.wkit;

import java.util.Map;

import javax.servlet.FilterConfig;

import com.lamfire.logger.Logger;
import com.lamfire.utils.PropertiesUtils;
import com.lamfire.utils.StringUtils;

final class ApplicationConfiguretion {
	static final Logger LOGGER = Logger.getLogger(ApplicationConfiguretion.class);

	private static final String CONF_FILE = "application.properties";

	public static final String ACTION_ROOT = "action.root"; 
	public static final String MULTIPART_TEMPDIR = "multipart.tempdir"; 
	public static final String MULTIPART_LIMIT = "multipart.limit";


	private static Config conf;

	private ApplicationConfiguretion() {
	}

	private static Config parserConfig() {
		Map<String, String> prop = PropertiesUtils.loadAsMap(CONF_FILE, ApplicationConfiguretion.class);
		LOGGER.info("found properties : " + prop);

		String actionRoot = prop.get(ACTION_ROOT);
		String tempDir = prop.get(MULTIPART_TEMPDIR);
		String maxSize = prop.get(MULTIPART_LIMIT);

		if (StringUtils.isBlank(actionRoot)) {
			throw new RuntimeException("The application can not load config file:" + CONF_FILE);
		}

		Config conf = new Config();
		conf.setActionRoot(actionRoot.trim());

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

	private static Config parserConfig(FilterConfig filterConfig) {
		String actionRoot = filterConfig.getInitParameter(ACTION_ROOT);
		String tempDir = filterConfig.getInitParameter(MULTIPART_TEMPDIR);
		String maxSize = filterConfig.getInitParameter(MULTIPART_LIMIT);

		if (StringUtils.isBlank(actionRoot)) {
			return null;
		}

		Config conf = new Config();
		conf.setActionRoot(actionRoot.trim());

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
		if (conf == null) {
			conf = parserConfig(filterConfig);
		}

		if (conf == null) {
			conf = parserConfig();
		}

		return conf;
	}

	public static Config getConfig() {
		return conf;
	}
}
