package com.ultrapower.mobile.common.constants;

public class Constants {
	
	//监听ip地址
	public static String IP_ADDRESS;
	
	//tcp监听端口
	public static int TCP_PORT;
	
	//udp监听端口
	public static int UDP_PORT;
	
	//udp发送数据端口
	public static int UDP_SEND_PORT;
	
	
	//tcp服务端处理线程数量
	public static int TCP_THREAD_COUNT;
	
	//用户在线扫描线程间隔(秒)
	public static int USERONLINE_SCAN_INTERVAL;
	
	//在线用户超时时间(秒)
	public static int USERONLINE_TIMEOUT;
	
	public final static String INF_TYPE = "InterfaceType";
	public final static String USERNAME = "UserName";
	public final static String USERFULLNAME = "UserFullName";
	public final static String PASSWORD = "Password";
	public final static String SIMID = "SIMID";
	public final static String ITSYSNAME = "ITSysName";
	public final static String ISSUCCESS = "IsSuccess";
	public final static String PUSHNOTICEDETAILS = "PushNoticeDetails";
	
	//待办总数
	public final static String WAITDEALTOTALCOUNT = "totalCount";
	
	//分页信息
	public final static String CURRENT_PAGE = "current_page";
	public final static String PAGE_SIZE = "page_size";
	public final static String BASESCHEMA = "baseSchema";
	
	
	///////////////手机终端与手机服务端交互接口/////////////
	//用户心跳接口
	public final static String INF_TYPE_USERONLINE = "UDP-UserOnLine";
	
	//tcp用户登陆
	public final static String INF_TYPE_TCP_USERINIT = "TCP-UserInit";
	
	//待办推送（手机端）
	public final static String INF_TYPE_UDP_PUSHWAITING = "UDP-PushWaiting";
	
	//客户端登陆验证结果返回
	public final static String INF_TYPE_TCP_REUSERINIT = "TCP-ReUserInit";
	
	//手机服务端向手机终端发送的提示
	public final static String INF_TYPE_UDP_PUSHNOTICE = "UDP-PushNotice";
	
	//手机客户端主动请求获取待办列表
	public final static String INF_TYPE_UDP_GETUSERWAITINGLIST = "UDP-GetUserWaitingList";
	
	
	///////////////手机服务端与业务系统交互接口/////////////
	//业务系统向手机服务端推送消息
	public final static String INF_TYPE_WS_PUSHNOTICE = "WS-PushNotice";
	
	//待办推送（业务系统）
	public final static String INF_TYPE_WS_PUSHWAITING = "WS-PushWaiting";
	
	//用户登录验证
	public final static String INF_TYPE_WS_USERAUTHENTICATION = "WS-UserAuthentication";
	
	//获取待办总数
//	public final static String INF_TYPE_WS_GETUSERWAITINGCOUNT = "WS-GetUserWaitingCount";
	
	//手机服务端主动获取接口
	public final static String INF_TYPE_WS_GETUSERWAITINGLIST = "WS-GetUserWaitingList";
	
	//手机服务端获取已办列表
	public final static String INF_TYPE_WS_GETUSERDEALEDLIST = "WS-GetUserDealedList";
	
	//业务系统主动推送
	public final static String INF_TYPE_WS_PULLUSERWAITINGLIST = "WS-PullUserWaitingList";
	
	//获取主工单字段
	public final static String INF_TYPE_WS_GETALLBIZFIELDS = "WS-GetWorkSheetInfo";
	
	//获取处理记录信息
	public final static String INF_TYPE_WS_GETDEALINFO = "WS_GETDEALINFO";
	
	//获取当前的可用动作
	public final static String INF_TYPE_WS_GETAVAILABLEACTIONS = "WS_GetAvailAbleActions";
	
	//获取当前动作的可编辑字段
	public final static String INF_TYPE_WS_GETEDITFIELDSBYACTION = "WS_GetEditFieldsByAction";
	
	//获取所有流程分类
	public final static String INF_TYPE_WS_GETWFTYPES = "WS_GETWFTYPES";
	
	
	///////////////流转动作接口//////////////////////////
	public final static String INF_TYPE_WS_TRANSFLOW = "WS_TransFlow";
	
}
