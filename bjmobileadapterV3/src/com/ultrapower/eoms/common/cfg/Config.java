package com.ultrapower.eoms.common.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;

import cn.com.ultrapower.eoms.user.comm.function.ConfigContents;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.constants.ConstantsManager;

public class Config {
	Log log = LogFactory.getLog(Config.class);
	private static Config instance = new Config();
	private String defaultConfig;
	private Properties defaults = new Properties();;
	private String nowConfig;
	private Properties now = new Properties();
	private static List<String> additionalList = new ArrayList<String>();
	private static final String PREFIX = "${";
	private static final String SUFFIX = "}";
	private static volatile boolean dbinited = false;

	private Config() {
	}

	public static void init(String path) {
		instance = new Config();
		// 加载配置文件
		instance.load(path);
		// 监听配置文件文件修改
		listenFileChanges();
	}
	

	public void load(String path) {
		defaults = new Properties();
		defaults.put(ConfigKeys.APPLICATION_PATH, path);
		String configPath = path + "WEB-INF" + File.separator + "cfg"
				+ File.separator;
		defaults.put(ConfigKeys.CONFIG_PATH, configPath);
		defaultConfig = configPath + "config_default.properties";
		defaults.put(ConfigKeys.DEFAULT_CONFIG, defaultConfig);
		nowConfig = configPath + "config.properties";
		defaults.put(ConfigKeys.NOW_CONFIG, nowConfig);
		// defaults
		this.loadDefaults();
		// now
		this.loadConfig(nowConfig);
		// additional
		String additional = this.getValue(ConfigKeys.ADDITIONAL_CONFIG);
		if (additional != null && !"".equals(additional)) {
			String[] filenames = additional.split(",");
			if (filenames != null && filenames.length > 0) {
				for (String filename : filenames) {
					loadConfig(filename);
					if (!additionalList.contains(filename)) {
						additionalList.add(filename);
					}
				}
			}
		}
		// load database pool
		initDB();
		// load ar table names
		initARSchema();
		// 基础组配置文件
		ConfigContents.initContents();
		// 流程的配置文件
		String constantsFile = path + "WEB-INF";
		ConstantsManager process = new ConstantsManager(constantsFile);
		process.getConstantInstance();
		// 兼容性处理
		initCompatibility();
	}

	/**
	 * 初始化数据库连接池
	 */
	private void initDB() {
		if (dbinited) {
			return;
		}
		try {
			dbinited = true;
			log.info("开始加载数据库连接池配置");
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			Properties info = new Properties();
			info.setProperty("proxool.maximum-connection-count", Config
					.getValue(ConfigKeys.DB_MAXIMUM_CONNECTION_COUNT));
			info.setProperty("proxool.minimum-connection-count", Config
					.getValue(ConfigKeys.DB_MINIMUM_CONNECTION_COUNT));
			info.setProperty("proxool.trace", Config
					.getValue(ConfigKeys.DB_TRACE));
			info.setProperty("user", Config.getValue(ConfigKeys.DB_USER));
			info.setProperty("password", Config
					.getValue(ConfigKeys.DB_PASSWORD));
			String alias = Config.getValue(ConfigKeys.DB_ALIAS);
			String driverClass = Config.getValue(ConfigKeys.DB_DRIVER_CLASS);
			String driverUrl = Config.getValue(ConfigKeys.DB_DRIVER_URL);
			String url = "proxool." + alias + ":" + driverClass + ":"
					+ driverUrl;
			ProxoolFacade.registerConnectionPool(url, info);
			log.info("完成加载数据库连接池配置");
		} catch (ProxoolException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void initARSchema() {
		// 加载AR的表名称
		ARSchemaInitDaoImpl aRdao = ARSchemaInitDaoImpl.getInstance();
		Map<String, String> tables = aRdao.loadARSchema();
		now.putAll(tables);
	}
	// 处理与原有系统的兼容性问题
	private void initCompatibility() {

		// 基础的remedy配置
		ConfigContents.mapParam.put("driverurl",
				getValue(ConfigKeys.REMEDY_SERVER_NAME));
		ConfigContents.mapParam.put("serverport",
				getIntValue(ConfigKeys.REMEDY_SERVER_PORT));
		ConfigContents.mapParam.put("user",
				getValue(ConfigKeys.REMEDY_USERNAME));
		ConfigContents.mapParam.put("password",
				getValue(ConfigKeys.REMEDY_PASSWORD));
		// 流程的remedy配置
		Constants.REMEDY_SERVERNAME = getValue(ConfigKeys.REMEDY_SERVER_NAME);
		Constants.REMEDY_SERVERPORT = getIntValue(ConfigKeys.REMEDY_SERVER_PORT);
		Constants.REMEDY_DEMONAME = getValue(ConfigKeys.REMEDY_USERNAME);
		Constants.REMEDY_DEMOPASSWORD = getValue(ConfigKeys.REMEDY_PASSWORD);
	}

	/**
	 * 根据关键字取配置信息
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	public static String getValue(String key) {
		String value = instance.now.getProperty(key);
		if (value == null) {
			value = instance.defaults.getProperty(key);
			if (value == null) {
				return null;
			}
		}
		return expandValue(value);
	}

	/**
	 * 根据关键字取Boolean型的配置信息
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	public static boolean getBooleanValue(String key) {
		return "true".equalsIgnoreCase(getValue(key));
	}

	/**
	 * 根据关键字取Int型的配置信息
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	public static int getIntValue(String key) {
		return Integer.parseInt(getValue(key));
	}

	/**
	 * 监控配置文件
	 */
	private static void listenFileChanges() {
		int period = Config.getIntValue(ConfigKeys.FILE_CHANGES_DELAY) * 1000;
		FileMonitor monitor = FileMonitor.getInstance();
		String defaultConfig = Config.getValue(ConfigKeys.DEFAULT_CONFIG);
		String nowConfig = Config.getValue(ConfigKeys.NOW_CONFIG);
		if (period > 0) {
			FileChangeListener listener = new ConfigChangeListener();
			monitor.addFileChangeListener(listener, defaultConfig, period);
			monitor.addFileChangeListener(listener, nowConfig, period);
			if (additionalList != null && !additionalList.isEmpty()) {
				for (String filename : additionalList) {
					monitor.addFileChangeListener(listener, filename, period);
				}
			}
		} else {
			monitor.removeFileChangeListener(defaultConfig);
			monitor.removeFileChangeListener(nowConfig);
			if (additionalList != null && !additionalList.isEmpty()) {
				for (String filename : additionalList) {
					monitor.removeFileChangeListener(filename);
				}
			}
		}
	}

	/**
	 * 加载默认配置文件
	 */
	private void loadDefaults() {
		try {
			String filename = this.defaultConfig;
			if (filename == null || "".equals(filename)) {
				return;
			}
			File file = new File(filename);
			if (!file.exists()) {
				return;
			}
			FileInputStream input = new FileInputStream(file);
			defaults.load(input);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载指定的配置文件
	 * @param filename
	 */
	private void loadConfig(String filename) {
		try {
			if (filename == null || "".equals(filename)) {
				return;
			}
			File file = new File(filename);
			if (!file.exists()) {
				return;
			}
			FileInputStream input = new FileInputStream(file);
			now.load(input);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 替换配置信息中的通配符号,如:${application.path}
	private static String expandValue(String source) {
		int fIndex = source.indexOf(PREFIX);
		if (fIndex == -1) {
			return source;
		}
		StringBuffer sb = new StringBuffer(source);
		while (fIndex > -1) {
			int lIndex = sb.indexOf(SUFFIX);
			int start = fIndex + PREFIX.length();
			if (fIndex == 0) {
				String varName = sb.substring(start, start + lIndex
						- PREFIX.length());
				sb.replace(fIndex, fIndex + lIndex + 1, getValue(varName));
			} else {
				String varName = sb.substring(start, lIndex);
				sb.replace(fIndex, lIndex + 1, getValue(varName));
			}
			fIndex = sb.indexOf(PREFIX);
		}
		return sb.toString();
	}
}
