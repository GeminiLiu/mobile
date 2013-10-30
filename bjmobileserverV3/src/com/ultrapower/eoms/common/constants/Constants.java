package com.ultrapower.eoms.common.constants;

public class Constants {
	public static boolean ISUSERCACHE		= true; 					//是否使用缓存
	public static int CACHE_TYPE			= 1;						//缓存类型 1：登陆缓存  2：全局缓存
	public static String PRIVILEGECACHE 	= "userprivilege";			//权限缓存名,修改需同步resources\ehcache.xml中配置
	public static String DICTYPE			= "dictype";				//字典类型缓存名,修改需同步resources\ehcache.xml中配置
	public static String QUERYSQL			= "querysql";				//查询SQL缓存名,修改需同步resources\ehcache.xml中配置
	public static String REPOSITORYMAP		= "repositoryMap";			//知识库缓存名,修改需同步resources\ehcache.xml中配置
	public static String REPOSITORYFLAG		= "repositoryFlag";		//知识库缓存是否保存标识,修改需同步resources\ehcache.xml中配置
	public final static boolean IS_SSO		= false; 				//是否让其他系统单点登录本系统
	public final static String SSO_USER_PWD_SEPARATOR = "@semi@";		//单点登录中用户名和密码分隔符
	public final static String SSO_COOKIE_NAME = "eoms4_ultra";
	public static String DATABASE_ALIAS		= "";
	public static boolean isSynch = false;                             //开关,用来标识是否进行信息同步(与pasm)
	public static boolean isPasmSynchEoms = false;                     //开关,用来标识是否进行pasm向eoms同步
	public static boolean isUip = false;                               //开关,用来标识是否进行了uip的集成
	public static String DOCSMANAGERTYPE	= "file";					//文档库类型 file:以文件为主;info:以信息为主
	public static String WORKSHEET_UPLOAD_PATH;							//工单附件上传路径
	public static String PATROL_UPLOAD_PATH;                            //巡检附件上传路径
	public static String APP_UPLOAD_PATH;									//手机APK上传路径
	public static String OFFLINE_TEMP_PATH;                            //离线压缩包临时存放路径
}
