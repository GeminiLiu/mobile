package cn.com.ultrapower.eoms.processSheet;

public class Contents {
	
	public static String CFGFILE = "processSheetCfg.xml";//配置文件名称
	public static String METHOD = "method";	//服务方法名称
	public static String ACTION = "action";
	public static String OPDETAIL = "opDetail";//操作内容
	
	public static String BMCSPLIT = ":";//工单分割符合
	public static String SUPPLIER = "supplier";//服务提供方
	public static String CALLER = "caller";//服务调用方
	public static String CALLERPWD="callerPwd";//服务调用方密码
	public static String OPCORP = "opCorp";//调用公司名称
	public static String DATEFORMAT = "dateFormat";//时间转换格式
	public static int BMCDATATYPE_DATE = 7;//bmc时间格式
	
	public static String PERSONPROCESS = "person";//个人投诉工单xml配置名称
	public static String NEWWORKSHEET = "newWorksheet";//新建工单方法名称
	public static String PREFIX = "prefix"; //工单前缀字符定义
	public static String BMCTABLE = "bmcTable"; //工单对应的表名
	public static String QUATZTABLE = "quatzTable"; //轮询的表名称
	public static String URLS = "urls"; //服务方调用地址
	public static String QUATZSELECT = "quatzSelect"; //轮询查询语句
	public static String QUATZUPDATE = "quatzUpdate"; //轮询更新语句
	public static String CHNAME = "chName"; //方法的中文名称
	public static String ACTIONID = "actionId"; //当前动作的ID
	public static String FIELDBMC = "fieldBMC"; //字段的bmc对应名称
	public static String FIELDCOLUMN = "fieldColumn"; //字段的bmc对应的列
	public static String BASEFIELDINFO = "basefieldInfo"; //接口调用准备参数名称
	public static String TYPE = "type"; //接口调用准备参数类型
	public static String SORT = "sort"; //接口调用准备参数顺序
	public static String FIELDCHNAME = "fieldChName"; //工单字段中文名称
	public static String FIELD= "field"; //工单字段
	public static String FIELDENNAME = "fieldEnName"; //工单字段的英文名称
	public static String FIELDDATATYPE = "fieldDataType"; //bmc字段类型
	public static String FIELDTYPE="fieldType"; //field 类型
	public static String FIELDCONTENT="fieldContent"; //field 类型
	public static String CONNECTBMC="connectBMC"; //field 类型
	public static String CONNECTCOLUMN="connectColumn"; //field 类型
	public static String CONNECT="connect"; //field 类型
	public static String CONNECTSPLIT = ":";
	public static String DISPLAY = "display"; //阶段回复工单方法名称
	public static String SUGGESTWORKSHEET = "suggestWorkSheet"; //阶段回复工单方法名称
	public static String CONFIRMWORKSHEET = "confirmWorkSheet";
	public static String NOTIFYWORKSHEET = "notifyWorkSheet";
	public static String REPLYWORKSHEET = "replyWorkSheet";
	public static String WITHDROWWORKSHEET = "withdrawWorkSheet";
	public static String CLASSNAME = "classname";//转换的类
	
	
	
	
	
	public static String ACCEPTDETAIL="<fieldInfo><fieldChName>操作类型</fieldChName><fieldEnName>opType</fieldEnName><fieldContent>受理</fieldContent></recordInfo><fieldInfo>" +
			"<fieldChName>操作说明</fieldChName><fieldEnName>opDesc</fieldEnName><fieldContent>受理完成</fieldContent></recordInfo>";
	
	
	public static String NMSPROCESS = "nmsAlarm";//告警工单名称
	public static String EOMSAUTHENTICATION = "eomsAuthentication";//鉴权名称
	public static String USERNAME = "userName";//鉴权用户名
	public static String USERPASSWORD="userPassword";//鉴权密码
	public static String NEWALARM = "newAlarm";//网管告警方法名称
	public static String WGKF = "WGKF";//自动派单告警公用账号
	public static String CREATETYPE = "INC_Alarm_Type"; //告警派发方式
	public static String CREATORSJWG = "数据网管";//数据网管建单人
	public static String CREATORCSWG = "传输网管";//传输网管建单人
	public static String CREATORPOWERWG = "动力环境";//动力环境建单人
	public static String CREATORDEP = "运行监控室";//转换的类
	public static String CREATORCOP = "宁夏移动区公司";//转换的类
	public static String ISROLE = "isRole";//告警工单名称

	//规范定义相关数据格式
	public static String RECORDINFO = "recordInfo"; 

	public static String NMSsyncAlarm="syncAlarm"; //告警同步
	public static String SynalarmStatus = "INC_Alarm_Status";
	public static String SynclearTime="INC_Alarm_ClearTime";
	public static String SynalarmId = "INC_Alarm_SN";
	public static String SynsheetNoColumn = "sheetNoColumn";
	public static String SynsyncSheetState = "syncSheetState";
}
