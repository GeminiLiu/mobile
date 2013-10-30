package com.ultrapower.eoms.common.cfg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigKeys {
	private static Log log = LogFactory.getLog(ConfigKeys.class);

	/**
	 * 监控文件修改的时间间隔
	 */
	public static final String FILE_CHANGES_DELAY = "file.changes.delay";

	/**
	 * 应用程序路径
	 */
	public static final String APPLICATION_PATH = "application.path";

	/**
	 * 配置文件路径
	 */
	public static final String CONFIG_PATH = "config.path";

	/**
	 * 默认配置文件
	 */
	public static final String DEFAULT_CONFIG = "default.config";

	/**
	 * 当前配置文件
	 */
	public static final String NOW_CONFIG = "now.config";

	/**
	 *附加配置文件
	 */
	public static final String ADDITIONAL_CONFIG = "additional.config";

	public static String ROOT;

	/**
	 * 主键管理中默认的ID值
	 */
	public final static Long DEFAULT_NEXT_ID = 1L;

	/**
	 * 主键管理中默认的步长
	 */
	public final static Long DEFAULT_STEP = 1L;

	/**
	 * 附件存储到FTP
	 */
	public final static String ATTACHMENT_STORE_TYPE_FTP = "FTP";

	/**
	 * 附件存储到本地磁盘
	 */
	public final static String ATTACHMENT_STORE_TYPE_DISK = "DISK";

	/**
	 * 附件存储到数据库
	 */
	public final static String ATTACHMENT_STORE_TYPE_DB = "DB";

	/**
	 * 附件的默认存储方式：存储到数据库的BLOB字段中
	 */

	public static final String DEFAULT_ATTACHMENT_STORE_TYPE = ATTACHMENT_STORE_TYPE_DB;

	/**
	 * 附件的存储方式
	 */
	public static final String ATTACHMENT_STORE_TYPE = "attachment.store.type";

	/**
	 * 存储附件的FTP主机
	 */
	public static final String ATTACHMENT_FTP_HOST = "attachment.ftp.host";
	/**
	 * 存储附件的FTP用户名
	 */
	public static final String ATTACHMENT_FTP_USERNAME = "attachment.ftp.username";
	/**
	 * 存储附件的FTP用户密码
	 */
	public static final String ATTACHMENT_FTP_PASSWORD = "attachment.ftp.password";

	/**
	 * 存储附件的DISK根路径
	 */
	public static final String ATTACHMENT_DISK_PATH = "attachment.disk.path";
	/**
	 * 存储下载临时存放路径
	 */
	public static final String ATTACHMENT_DISK_TEMPDOWNLOAD = "attachment.disk.tempdownload";
	

	/**
	 * 移动集团EOMS系统的数据库SCHEMA,用于查询用户信息
	 */
	public static final String EOMS_SCHEMA = "eoms.schema";

	/**
	 * 移动集团DUTY系统的数据库SCHEMA，用于查询单点登陆信息
	 */
	public static final String DUTY_SCHEMA = "duty.schema";

	/**
	 * 数据库连接池别名,默认别名为:eomsdb
	 */
	public static final String DB_ALIAS = "db.alias";

	/**
	 * 数据库驱动名称
	 */
	public static final String DB_DRIVER_CLASS = "db.driver-class";
	/**
	 * 数据库连接URL
	 */
	public static final String DB_DRIVER_URL = "db.driver-url";
	/**
	 * 数据库用户名
	 */
	public static final String DB_USER = "db.user";
	/**
	 * 数据库用户密码
	 */
	public static final String DB_PASSWORD = "db.password";
	/**
	 * 数据库最大连接数
	 */
	public static final String DB_MAXIMUM_CONNECTION_COUNT = "db.maximum-connection-count";
	/***
	 * 数据库最小连接数
	 */
	public static final String DB_MINIMUM_CONNECTION_COUNT = "db.minimum-connection-count";
	/**
	 * 数据库调试跟踪判断
	 */
	public static final String DB_TRACE = "db.trace";

	/**
	 * 没有通过单点认证时转跳的登陆页面
	 */
	public static final String CAS_LOGIN_URL = "cas.login.url";

	/**
	 * 单点认证页面
	 */
	public static final String CAS_SERVICE_URL = "cas.service.url";

	
	/**
	 * 默认的缓存实现类
	 */
	public static final String CACHE_IMPLEMENTATION = "cache.implementation";
	
	/**
	 * COOKIE中SID的名称
	 */
	public static final String COOKIE_NAME_SID = "cookie.name.sid";
	/**
	 * SESSION的名称
	 */
	public static final String SESSION_NAME = "session.name";
	/**
	 * 单点认证实现类
	 */
	public static final String SSO_IMPLEMENTATION = "sso.implementation";

	/**
	 * 公司名称缓存
	 */
	public static final String CACHE_COMPANY = "CACHE_COMPANY";
	/**
	 * 部门名称缓存
	 */
	public static final String CACHE_DEPARTMENT = "CACHE_DEPARTMENT";
	/**
	 * 组名称缓存
	 */
	public static final String CACHE_GROUP = "CACHE_GROUP";
	
	/**
	 * Remedy主机名
	 */
	public static final String REMEDY_SERVER_NAME = "remedy.server.name";
	/**
	 * Remedy服务端口
	 */
	public static final String REMEDY_SERVER_PORT = "remedy.server.port";
	/**
	 * Remedy Demo的用户名
	 */
	public static final String REMEDY_USERNAME = "remedy.username";
	/**
	 * Remedy Demo的密码
	 */
	public static final String REMEDY_PASSWORD = "remedy.password";
	
}
