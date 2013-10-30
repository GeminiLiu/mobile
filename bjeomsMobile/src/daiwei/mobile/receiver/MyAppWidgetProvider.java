package daiwei.mobile.receiver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import daiwei.mobile.Tools.IsNetwork;
import daiwei.mobile.activity.BaseTmpActivity;
import daiwei.mobile.activity.GdListActivity;
import daiwei.mobile.activity.SettingActivity;
import daiwei.mobile.activity.TestActivity;
import daiwei.mobile.animal.ListModel;
import daiwei.mobile.animal.User;
import daiwei.mobile.service.GDListService;
import daiwei.mobile.service.GDListServiceImp;
import daiwei.mobile.service.LoginService;
import daiwei.mobile.service.LoginServiceImp;
import daiwei.mobile.util.AppInfo;
import daiwei.mobile.util.StringUtil;
import eoms.mobile.R;

/**
 * @author changxiaofei
 * @time 2013-4-8 下午4:01:44
 */
public class MyAppWidgetProvider extends AppWidgetProvider {
	private static final String TAG = "MyAppWidgetProvider";
	private final static int WHAT_AUTO_LOGIN = 1;
	private final static int WHAT_LOAD_DATA = 2;
	private User user;
	/** ApplicationContext */
	private Context context;
	private final int PAGE_SIZE = 4;
	private final String KEY_PAGE_NUM = "PAGE_NUM";
	private final String KEY_ITEM_BUNDLE = "KEY_ITEM_BUNDLE";
	private int pageNum = 1;
	private int pageCount = 1;
	/** 
	 * 点设置按钮时，先检测登录状态，没登录则先登录，再跳转。注意以下2类情形的区别处理： 
	 * 1.刷新、翻页点击时未登录：自动登录并在内容显示登录过程，登录成功后自动加载数据。
	 * 2.列表项、设置点击时未登录：跳转到app时，先检测登录状态，未登录则自动登录并在内容显示登录过程，登录完毕后刷新当前页面(页码不变)，完成后直接跳走 。
	 */
	private int checkLoginType = 1;
	/** 
	 * checkLoginType = 1时使用：
	 * 1列表项项点击操作; 2设置按钮点击操作。
	 */
	private int operateType = 1;
	private Bundle itemClickBundle;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context.getApplicationContext();
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		ComponentName provider = new ComponentName(context, MyAppWidgetProvider.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(provider);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout);
		if(!IsNetwork.isAccessNetWork(context)){
			remoteViews.setTextViewText(R.id.tv_widget_msg, context.getResources().getString(R.string.msg_error_network_unavailable));
			setViewVisible(remoteViews, R.id.tv_widget_msg);
			setListeners(remoteViews);
			appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
			return;
		}
		if ("daiwei.mobile.widget.pageup.click".equals(intent.getAction())||
				"daiwei.mobile.widget.pagedown.click".equals(intent.getAction())||
				"daiwei.mobile.widget.refresh.click".equals(intent.getAction())) {
			pageNum = intent.getIntExtra(KEY_PAGE_NUM, -1);
			if ("daiwei.mobile.widget.pageup.click".equals(intent.getAction())) {
				Log.i(TAG, "daiwei.mobile.widget.pageup.click loadDwgd");
				if(pageNum>1){
					pageNum--;
				}else{
					pageNum=1;
				}
			} else if ("daiwei.mobile.widget.pagedown.click".equals(intent.getAction())) {
				Log.i(TAG, "daiwei.mobile.widget.pagedown.click  loadDwgd");
				pageNum++;
			}else if("daiwei.mobile.widget.refresh.click".equals(intent.getAction())){
				Log.i(TAG, "daiwei.mobile.widget.refresh.click  loadDwgd");
				if(pageNum<1){
					pageNum = 1;
				}
			}else {
			}
			checkLoginType = 1;
			checkLogin(appWidgetManager, appWidgetIds, remoteViews);
		}else if("daiwei.mobile.widget.set.click".equals(intent.getAction())){//点击设置按钮
			Log.i(TAG, "daiwei.mobile.widget.set.click  checkLogin " + pageNum);
			pageNum = intent.getIntExtra(KEY_PAGE_NUM, 1);
			checkLoginType = 2;
			operateType = 2;
			checkLogin(appWidgetManager, appWidgetIds, remoteViews);
		}else if("daiwei.mobile.widget.item.click".equals(intent.getAction())){//列表项点击
			Log.i(TAG, "daiwei.mobile.widget.item.click  checkLogin " + pageNum);
			pageNum = intent.getIntExtra(KEY_PAGE_NUM, 1);
			itemClickBundle = intent.getBundleExtra(KEY_ITEM_BUNDLE);
			checkLoginType = 2;
			operateType = 1;
			checkLogin(appWidgetManager, appWidgetIds, remoteViews);
		}
		super.onReceive(context, intent);
	}
	
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// 需要一个刷新按钮???。
		// 检测网络，无网则不显示内容。
		// 检测是否已记录用户名密码，是则自动登录，否则不显示内容。
		// 登录成功获取列表，登录失败(主要是联网超时)可点刷新按钮重试登录。
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout);
		if (!IsNetwork.isAccessNetWork(context)) {
			remoteViews.setTextViewText(R.id.tv_widget_msg, "自动登录失败，请稍后重试。");
			setViewVisible(remoteViews, R.id.tv_widget_msg);
			return;
		}
		checkLogin(appWidgetManager, appWidgetIds, remoteViews);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}
	
	/**
	 * @param appWidgetManager
	 * @param appWidgetIds
	 * @param remoteViews
	 */
	private void checkLogin(AppWidgetManager appWidgetManager, int[] appWidgetIds, RemoteViews remoteViews) {
		user = AppInfo.getUserFromSharedPreferences(context);
		if (StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPasswordEncode())) {
			// 配置文件无缓存的用户登录信息，直接进登录页。
			Log.i(TAG, "checkLogin() userinfo in sp is empty");
			remoteViews.setTextViewText(R.id.tv_widget_msg, "没有登录信息，请先启动EOMS系统并登录。");
			setViewVisible(remoteViews, R.id.tv_widget_msg);
		} else if (AppInfo.isLoggedIn(context)) {
			if(checkLoginType==2){//需要跳转到app里
				Log.i(TAG, "checkLogin() have logged in， jump into app");
				Intent intent;
				if(operateType == 1){//待办工单处理
					intent = new Intent(context, BaseTmpActivity.class);
					intent.putExtras(itemClickBundle);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}else if(operateType == 2){//系统设置
					intent = new Intent(context, SettingActivity.class);
					Bundle mBundle = new Bundle();
					mBundle.putSerializable("LoginModel", AppInfo.getLoginModelFromApplication(context));
					mBundle.putInt(TestActivity.KEY_FROM, TestActivity.CONSTANT_FROM_WIDGET);
					intent.putExtras(mBundle);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			}else{
				Log.i(TAG, "checkLogin() have logged in， loadDwgd");
				loadDwgd(appWidgetManager, appWidgetIds, remoteViews);
			}
		} else {
			Log.i(TAG, "checkLogin() have not logged in, autoLogin");
			autoLogin(appWidgetManager, appWidgetIds, remoteViews);
		}
	}
	
	/**
	 * 根据缓存的登录信息自动登录
	 */
	private void autoLogin(AppWidgetManager appWidgetManager, int[] appWidgetIds, RemoteViews remoteViews) {
		Log.i(TAG, "autoLogin");
		remoteViews.setTextViewText(R.id.ll_widget_loading_text, "正在登录");
		setViewVisible(remoteViews, R.id.ll_widget_loading);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
		if (IsNetwork.isAccessNetWork(context)) {// 自动登录
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = mHandler.obtainMessage();
					msg.what = WHAT_AUTO_LOGIN;
					// 这里暂用旧的登录模块，里面很多异常没处理。???
					LoginService lg = new LoginServiceImp();
					Map<String, Object> loginMap = lg.login(context, user.getUsername(), user.getPasswordEncode());// 返回Map<String,Object>集合
					try {// ???测试延迟
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					msg.obj = loginMap;
					mHandler.sendMessage(msg);
				}
			}).start();
		}
	}
	
	/**
	 * 加载EOMS工单
	 * @param appWidgetManager
	 * @param appWidgetIds
	 * @param remoteViews
	 */
	private void loadDwgd(AppWidgetManager appWidgetManager, int[] appWidgetIds, RemoteViews remoteViews) {
		Log.i(TAG, "loadDwgd pageNum==" + pageNum);
		remoteViews.setTextViewText(R.id.ll_widget_loading_text, "正在加载数据。");
		setViewVisible(remoteViews, R.id.ll_widget_loading);
		remoteViews.removeAllViews(R.id.ll_widget_content);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
		if (IsNetwork.isAccessNetWork(context)) {// 已联网
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = mHandler.obtainMessage();
					msg.what = WHAT_LOAD_DATA;
					GDListService gdList = new GDListServiceImp();
					//代办，第pageNum页，每页pageSize条，当前工单类别为全部
					ListModel lp = gdList.getWaitList(context, 1, pageNum, PAGE_SIZE, "ALL", "");
					List<Map<String, String>> list = new ArrayList<Map<String,String>>();
					if(lp!=null){
						list = lp.getListInfo();
						Map<String,String> mapNum = lp.getBaseCount();//计算总页数
						try {
							int count = 0;
//							int count = Integer.parseInt(mapNum.get("WF4:EL_AM_AT"))+Integer.parseInt(mapNum.get("WF4:EL_AM_TTH"))+Integer.parseInt(mapNum.get("WF4:EL_AM_PS"));

							//循环map计算待办工单总数
							Iterator it = mapNum.keySet().iterator();
							int num = 0;
							while(it.hasNext()){
								String key = (String)it.next();
								String value = (String)mapNum.get(key);
								if(StringUtil.isNotEmpty(value)){
									num = Integer.parseInt(value);
								}
							}
							count = count+num;

							if(count>4){
								pageCount = count/PAGE_SIZE + ((count%PAGE_SIZE==0)?0:1);
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
					try {// ???测试延迟
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					msg.obj = list;
					mHandler.sendMessage(msg);
				}
			}).start();
		}
	}
	/**
	 * 加载   工单列表
	 */
	private void loadDwgdOld(AppWidgetManager appWidgetManager, int[] appWidgetIds, RemoteViews remoteViews) {
		Log.i(TAG, "loadDwgd pageNum==" + pageNum);
		remoteViews.setTextViewText(R.id.ll_widget_loading_text, "正在加载数据。");
		setViewVisible(remoteViews, R.id.ll_widget_loading);
		remoteViews.removeAllViews(R.id.ll_widget_content);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
		if (IsNetwork.isAccessNetWork(context)) {// 已联网
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = mHandler.obtainMessage();
					msg.what = WHAT_LOAD_DATA;
					GDListService gdList = new GDListServiceImp();
					//代办，第pageNum页，每页pageSize条，当前工单类别为全部
					ListModel lp = gdList.getWaitList(context, 1, pageNum, PAGE_SIZE, "ALL", "");
					List<Map<String, String>> list = new ArrayList<Map<String,String>>();
					if(lp!=null){
						list = lp.getListInfo();
						Map<String,String> mapNum = lp.getBaseCount();//计算总页数
						try {
							int count = Integer.parseInt(mapNum.get("WF4:EL_AM_AT"))+Integer.parseInt(mapNum.get("WF4:EL_AM_TTH"))+Integer.parseInt(mapNum.get("WF4:EL_AM_PS"));
							if(count>4){
								pageCount = count/PAGE_SIZE + ((count%PAGE_SIZE==0)?0:1);
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
					try {// ???测试延迟
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					msg.obj = list;
					mHandler.sendMessage(msg);
				}
			}).start();
		}
	}	
	/**
	 * handler
	 */
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			ComponentName provider = new ComponentName(context, MyAppWidgetProvider.class);
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(provider);
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout);
			switch (msg.what) {
			case WHAT_AUTO_LOGIN:
				Map<String, Object> loginMap = (Map<String, Object>) msg.obj;
				boolean ok = (Boolean) loginMap.get("IsLegal");// 从集合中获取IsLegal字段，是否合法
				Log.i(TAG, "MyAppWidgetProvider autoLogin " + ok);
				if (ok) {
					Log.i(TAG, "autoLogin success");
					loadDwgd(appWidgetManager, appWidgetIds, remoteViews);// 加载数据
				} else {
					Log.i(TAG, "autoLogin false");
					remoteViews.setTextViewText(R.id.tv_widget_msg, "自动登录失败，请稍后重试。");
					setViewVisible(remoteViews, R.id.tv_widget_msg);
					setListeners(remoteViews);
				}
				appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
				break;
			case WHAT_LOAD_DATA:
				List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
				Log.i(TAG, "loadDwgd success");
				if (list == null || list.size() < 1) {
					remoteViews.removeAllViews(R.id.ll_widget_content);
					remoteViews.setTextViewText(R.id.tv_widget_msg, "暂无数据。");
					setViewVisible(remoteViews, R.id.tv_widget_msg);
				} else {
					remoteViews.setTextViewText(R.id.iv_content_page_text, new StringBuilder(String.valueOf(pageNum)).append("/").append(String.valueOf(pageCount)));//当前页码
					for (int j = 0; j < list.size(); j++) {
						RemoteViews convertView = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_item);
						Map<String, String> map = list.get(j);
						String img_type = map.get("BaseSchema");
						String imgText = "其它";
						StringBuilder sbName = new StringBuilder();
						/*
						if("WF4:EL_TTM_TTH".equalsIgnoreCase(img_type)){
							imgText = "故障";
							sbName.append("网元名称：").append(map.get("INC_NE_Name"));// 网元名称内容
						}else if("WF4:EL_UVS_TSK".equalsIgnoreCase(img_type)){
							imgText = "任务";
							sbName.append("任务类型：").append(map.get("BaseTaskType"));// 网元名称内容
						}else if("WF4:EL_TTM_CCH".equalsIgnoreCase(img_type)){
							imgText = "投诉";
							sbName.append("投诉分类：").append(map.get("CCH_ComplainType"));
						}else if("WF4:EL_ITSM_EVENT".equalsIgnoreCase(img_type)){
							imgText = "IT投诉";
							sbName.append("事件分类：").append(map.get("ITMS_Event_Classify"));
						}else if("WF4:EL_ITSM_EVENT_TTH".equalsIgnoreCase(img_type)){//it事件故障
							imgText = "IT故障";
							sbName.append("变更类型：").append(map.get("Approval1_Type"));
						}else if("WF4:EL_ITSM_CHANGE".equalsIgnoreCase(img_type)){
							imgText = "IT变更";
							sbName.append("变更类型：").append(map.get("Approval1_Type"));
						}else if("WF4:EL_ITSM_DBUA".equalsIgnoreCase(img_type)){
							imgText = "账号权限变更";
							sbName.append("变更类型：").append(map.get("ITSM_DBUA_Change_Type"));
						}*/
//						if (img_type.equals("WF4:EL_AM_AT")) {
//							imgText = "任务";
//							sbName.append("任务类型：").append(map.get("AffectBussType"));// 代维专业
//						} else if (img_type.equals("WF4:EL_AM_TTH")) {
//							imgText = "故障";
//							sbName.append("网元名称：").append(map.get("NetElement"));// 网元名称内容
//						} else if (img_type.equals("WF4:EL_AM_PS")) {
//							imgText = "发电";
//							sbName.append("基站名称：").append(map.get("NetElement"));// 网元名称内容
//						} else {
//							imgText = "其它";
//						}
//						convertView.setTextViewText(R.id.tv_gd_type, imgText);//分类
						int num = j+ (pageNum-1)*PAGE_SIZE+1;
						convertView.setTextViewText(R.id.tv_gd_type, String.valueOf(num));//分类
						convertView.setTextViewText(R.id.tv_theme_content, map.get("BaseSummary"));// 标题内容
						convertView.setTextViewText(R.id.tv_person, sbName);// 代维专业
						convertView.setTextViewText(R.id.tv_time, new StringBuilder("处理时限：").append(StringUtil.StringToLongToString(map.get("BaseDealOuttime"))).toString());
						convertView.setTextViewText(R.id.tv_status, new StringBuilder("工单状态：").append(map.get("BaseStatus")).toString());
						Intent setClick = new Intent("daiwei.mobile.widget.item.click");
						Bundle bundle = new Bundle();
						bundle.putString("BaseSchema", map.get("BaseSchema"));
						bundle.putString("BaseSummary", map.get("BaseSummary"));
						bundle.putString("BaseSN", map.get("BaseSN"));
						bundle.putString("BaseID", map.get("BaseID"));
						bundle.putString("TaskID", map.get("TaskID"));
						bundle.putInt("IsWait", 1);//代办
						setClick.putExtra(KEY_PAGE_NUM, pageNum);
						setClick.putExtra(KEY_ITEM_BUNDLE, bundle);
						PendingIntent piItemClick = PendingIntent.getBroadcast(context, j+1000, setClick, PendingIntent.FLAG_UPDATE_CURRENT);
						convertView.setOnClickPendingIntent(R.id.rl_widget_content_main, piItemClick);
						remoteViews.addView(R.id.ll_widget_content, convertView);
					}
					setViewVisible(remoteViews, R.id.ll_widget_content);
					setListeners(remoteViews);
				}
				appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
				if(checkLoginType==2){//需要跳转到app里
					Log.i(TAG, "loadData ok, jump into app");
					if(operateType == 1){
						Intent intent = new Intent(context, BaseTmpActivity.class);
						intent.putExtras(itemClickBundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}else if(operateType == 2){
						Intent intent = new Intent(context, GdListActivity.class);
						Bundle mBundle = new Bundle();
						mBundle.putSerializable("LoginModel", AppInfo.getLoginModelFromApplication(context));
						mBundle.putInt(TestActivity.KEY_FROM, 2);
						intent.putExtras(mBundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}
				}else{
					Log.i(TAG, "loadData ok, over");
				}
				break;
			default:
				break;
			}
		}
	};
	
	/**
	 * 加载中；暂无数据，内容。3项只显示一项，其它项隐藏。
	 * @param views
	 * @param idVisible
	 */
	private void setViewVisible(RemoteViews remoteViews, int idVisible) {
		remoteViews.setViewVisibility(idVisible, View.VISIBLE);
		switch (idVisible) {
		case R.id.ll_widget_loading:
			remoteViews.setViewVisibility(R.id.ll_widget_loading, View.VISIBLE);
			remoteViews.setViewVisibility(R.id.tv_widget_msg, View.GONE);
			remoteViews.setViewVisibility(R.id.ll_widget_content, View.GONE);
			remoteViews.setViewVisibility(R.id.ll_content_page, View.GONE);
			break;
		case R.id.tv_widget_msg:
			remoteViews.setViewVisibility(R.id.ll_widget_loading, View.GONE);
			remoteViews.setViewVisibility(R.id.tv_widget_msg, View.VISIBLE);
			remoteViews.setViewVisibility(R.id.ll_widget_content, View.GONE);
			remoteViews.setViewVisibility(R.id.ll_content_page, View.GONE);
			break;
		case R.id.ll_widget_content:
			remoteViews.setViewVisibility(R.id.ll_widget_loading, View.GONE);
			remoteViews.setViewVisibility(R.id.tv_widget_msg, View.GONE);
			remoteViews.setViewVisibility(R.id.ll_widget_content, View.VISIBLE);
			remoteViews.setViewVisibility(R.id.ll_content_page, View.VISIBLE);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 注册监听
	 * @param remoteViews
	 */
	private void setListeners(RemoteViews remoteViews) {
		if(pageNum>1){
			Intent pageUpClick = new Intent("daiwei.mobile.widget.pageup.click");
			pageUpClick.putExtra(KEY_PAGE_NUM, pageNum);
			PendingIntent piPageUpClick = PendingIntent.getBroadcast(context, 0, pageUpClick, PendingIntent.FLAG_UPDATE_CURRENT);//FLAG_CANCEL_CURRENT
			remoteViews.setOnClickPendingIntent(R.id.iv_content_page_up, piPageUpClick);
			remoteViews.setViewVisibility(R.id.iv_content_page_up, View.VISIBLE);
		}else{
			remoteViews.setViewVisibility(R.id.iv_content_page_up, View.INVISIBLE);
		}
		if(pageNum<pageCount){
			Intent pageDownClick = new Intent("daiwei.mobile.widget.pagedown.click");
			pageDownClick.putExtra(KEY_PAGE_NUM, pageNum);
			PendingIntent piPageDownClick = PendingIntent.getBroadcast(context, 0, pageDownClick, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.iv_content_page_down, piPageDownClick);
			remoteViews.setViewVisibility(R.id.iv_content_page_down, View.VISIBLE);
		}else{
			remoteViews.setViewVisibility(R.id.iv_content_page_down, View.INVISIBLE);
		}
		//注册刷新监听
		Intent refreshClick = new Intent("daiwei.mobile.widget.refresh.click");
		refreshClick.putExtra(KEY_PAGE_NUM, pageNum);
		PendingIntent piRefreshClick = PendingIntent.getBroadcast(context, 0, refreshClick, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.iv_widget_refresh, piRefreshClick);
		//注册设置监听
		Intent setClick = new Intent("daiwei.mobile.widget.set.click");
		setClick.putExtra(KEY_PAGE_NUM, pageNum);
		PendingIntent piSetClick = PendingIntent.getBroadcast(context, 0, setClick, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.ib_widget_set, piSetClick);
		//注册公告监听
		Intent gongdanClick = new Intent("daiwei.mobile.widget.gonggao.click");
		PendingIntent piGonggaoClick = PendingIntent.getBroadcast(context,0,gongdanClick,PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.imageButton3, piGonggaoClick);
	}
}
