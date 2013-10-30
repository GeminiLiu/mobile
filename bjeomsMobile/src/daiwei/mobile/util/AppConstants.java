package daiwei.mobile.util;

/**
 * 存全局常量，只存static final的
 * @author changxiaofei
 * @time 2013-3-26 上午10:04:21
 */
public class AppConstants {
	/* SharedPreferences相关  */
	/** SharedPreferences文件名，该文件存登录ip信息 */
	public static final String SP_CONFIG = "sp_config";
	/** SharedPreferences Key ip名称历史记录 */
	public static final String SP_IP_NAME_HIS = "sp_ip_name_his";
	/** SharedPreferences Key ip地址历史记录 */
	public static final String SP_IP_ADDRESS_HIS = "sp_ip_address_his";
	/** SharedPreferences Key 当前设置的ip名称 */
	public static final String SP_IP_NAME = "sp_ip_name";
	/** SharedPreferences Key 当前设置的ip地址 */
	public static final String SP_IP_ADDRESS = "sp_ip_address";
	/** SharedPreferences Key 是否开启自动上传附件服务 */
	public static final String SP_IS_AUTO_UPLOAD = "sp_is_auto_upload";
	/** SharedPreferences Key 是否开启消息震动服务 */
	public static final String SP_IS_AUTO_NOTICE = "sp_is_auto_notice";
	/** SharedPreferences Key 本地app的versionCode */
	public static final String SP_VERSION_CODE = "sp_version_code";
	/** SharedPreferences Key 本地app的versionName */
	public static final String SP_VERSION_NAME = "sp_version_name";
	/** SharedPreferences Key 本地缓存的用户名 */
	public static final String SP_USER_NAME = "sp_user_name";
	/** SharedPreferences Key 本地缓存的密码 */
	public static final String SP_USER_PSW_ENCODE = "sp_user_psw";
	/** SharedPreferences Key 是否开启消息声音服务 */
	public static final String SP_IS_VOICE_NOTICE = "sp_is_voice_notice";
	/** SharedPreferences Key 是否开启消息提示服务 */
	public static final String SP_IS_NOTIFICATION_NOTICE="sp_is_notification_notice";
	
	/* 文件目录相关  */
	/** 主路径目录 */
	public static final String PATH_MAIN = "/eoms";
	/** 附件路径。存工单相关的照片和录音压缩文件
	 * 如果是手机存/data/data/package/files/daiwei/attachment。如果是 SDCard则/daiwei/attachment */
	public static final String PATH_ATTACHMENT = "/daiwei/attachment";
	/** 工单附件路径。
	 * 如果是 SDCard则/daiwei/attachment/gongdan */
	public static final String PATH_ATTACHMENT_GONGDAN = "/daiwei/attachment/gongdan";
	/** 巡检附件路径。
	 * 如果是 SDCard则/daiwei/attachment/xunjian */
	public static final String PATH_ATTACHMENT_XUNJIAN = "/daiwei/attachment/xunjian";
	/** 缓存已上传成功工单附件的路径。清理缓存时会删除
	 * 如果是手机存/data/data/package/files/daiwei/attachment_old。如果是 SDCard则/daiwei/attachment_old */
	public static final String PATH_ATTACHMENT_OLD = "/daiwei/attachment_old";
	/** apk升级临时文件 */
	public static final String PATH_UPGRADE_APK_FILENAME = "eoms.apk";
	/** 扫描附件文件夹的频率，单位毫秒 */
	public static final long AUTO_SCAN_ATTACHMENT_FREQUENCY = 5*60000;//???测试，正式为5*60000
	
	/* http请求相关 */
	public static final int ERROR_OTHER = 1000;
	public static final int ERROR_HTTP_SERVER_ERROR = 1001;
	public static final int ERROR_HTTP_UNKNOWNHOST = 1002;
	public static final int ERROR_HTTP_TIMEOUT = 1003;
	
	
	public static final String FILE_CACHE = "/dw/cache";
}
