package daiwei.mobile.util;

/**
 * 存配置信息常量
 * @author changxiaofei
 * @time 2013-3-26 下午4:37:02
 */
public class AppConfigs {
	/* app配置参数 */
	/**
	 * <b>数据库版本号。</b>
	 * 发布新版本时，如果更新了数据库结构，则该值要加1。可以保持和APP_VERSION一致，每次升级都加1，这样好处理。
	 */
	public static final int DB_VERSION = 1;
	
	/* URL配置参数 */
	/**
	 * <b>服务器请求路径。</b> 目前只有一个接口路径，不同的功能传不同的参数。“http://192.168.1.119:8010”部分根据用户填写的host补全。
	 */
	public static final String URL_PATH = "/ultramobile/mobile/call.action";
	/**
	 * <b>下载app地址。</b> 其中的newVersionName需替换成从服务器获取的最新的版本名称，如lnmams_1.0.1.apk
	 */
	public static final String URL_NEW_APP = "/ultramobile/mobileApp/eoms_newVersionName.apk";
	/**
	 * <b>服务器主机。</b> 缺省主机。不带路径。
	 */
	//public static final String[] URL_HOST_DEFAULTS = { "211.137.3.156:8087", "192.168.1.119:8010" };
	//public static final String[] URL_HOST_DEFAULTS = { "192.168.173.1:8888", "192.168.173.1:8888" };
	public static final String[] URL_HOST_DEFAULTS = { "192.168.173.1:8888" };
	/**
	 * <b>服务器主机名称。</b> 缺省主机名称。与URL_HOST_DEFAULT对应
	 */
	//public static final String[] URL_HOST_NAME_DEFAULTS = {  "默认外网地址" ,"默认内网地址"};
	public static final String[] URL_HOST_NAME_DEFAULTS = {  "默认网络地址"};
}
