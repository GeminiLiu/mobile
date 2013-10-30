package daiwei.mobile.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.animal.LoginModel;
import daiwei.mobile.animal.User;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.util.AppInfo;
import daiwei.mobile.util.TempletUtil.TempletService;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import daiwei.mobile.util.TempletUtil.TMPModel.Type;

@SuppressWarnings("unused")
public class LoginServiceImp implements LoginService{
	private Context context;
	private SharedPreferences sharedPref,sharedPrefLogin,sharedPrefOffLogin,sharedPreOffXML;
	private SharedPreferences.Editor editor,editorLogin,editorOffLogin,editorOffXML;
	public static boolean isLegal;
	public static String userName;
	public static String dwSpecialtyId;
	public static XMLUtil xml;
	//public static boolean templateFlag=true;
	@Override
	public Map<String, Object> login(Context context, String username,String password) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String x;
		this.context = context;
		sharedPrefLogin = context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
		sharedPref = context.getSharedPreferences("AUTOLOGOIN",Context.MODE_PRIVATE);
		sharedPrefOffLogin = context.getSharedPreferences("OFFLOGIN",Context.MODE_PRIVATE);
		sharedPreOffXML = context.getSharedPreferences("OFFXML",Context.MODE_PRIVATE);
		
		if (NetWork.checkWork(context) == 1) {
			HTTPConnection hc = new HTTPConnection(context);
			Map<String, String> parmas = new HashMap<String, String>();
			parmas.put("serviceCode", "L001");
			TestXmlCreat tc = new TestXmlCreat(context, username, password);
			parmas.put("inputXml", tc.doc.asXML());
			x = hc.Sent(parmas);
		} else {
			x = sharedPreOffXML.getString("offTemple", "");
		}
		//离线保存服务器返回数据
		editorOffXML=sharedPreOffXML.edit();
		editorOffXML.putString("offTemple", x);
		editorOffXML.commit();
		xml = new XMLUtil(x);
		isLegal = xml.getIsLegal();
		userName=xml.getUserName();
		dwSpecialtyId=xml.getDwSpecialtyId();
		//离线时保存desktop巡检类型
		editorOffLogin=sharedPrefOffLogin.edit();
		editorOffLogin.putString("dwSpecialtyId", dwSpecialtyId);
		editorOffLogin.commit();
		TempletService ts = new TempletService();		
		if(isLegal){
			String str=null;
			//**如果返回值为true  那么就把获取到的版本提交到服务器  获得服务器版本对应的模版*//
			boolean needCreate;//是否要重新解析模板数据
			System.out.println("模版个数..........="+xml.getType().size());
			List<Type> typeList = xml.getType();
			for(int i=0;i<typeList.size();i++){	
//				if(typeList.get(i).getCategoryType().equals("dwspecialty"))
//					continue;
				needCreate = true;
				if(!sharedPrefLogin.getString(typeList.get(i).getType(),"").equals("")){	//登录类型与模版类型相同时		
					if(typeList.get(i).getType_id().equals(sharedPrefLogin.getString(typeList.get(i).getType(),""))){//登录版本id与模版版本id相同时
						needCreate = false;//手机工单模板 版本没有变化   不需要重新解析模板数据
						str=sharedPref.getString(typeList.get(i).getType(),"");//从本地取出模版						
					}
					else{	
						//**登录类型与模版类型相同，但是登录版本与模版版本不同   从服务器获取模版并保存到本地中*//
						str = getTemplet(context, typeList.get(i).getCategoryType(),typeList.get(i).getType(), username, password);
						
						if(!str.equals("")){
						editor = sharedPref.edit();
						editor.putString(typeList.get(i).getType(),str);
						editor.commit();
						
						editorLogin=sharedPrefLogin.edit();
						editorLogin.putString(typeList.get(i).getType(),typeList.get(i).getType_id());					
						editorLogin.commit();
						}
					}
				}else{//登录类型与模版类型不同  从服务器获取模版并保存到本地中			
					str = getTemplet(context, typeList.get(i).getCategoryType(),typeList.get(i).getType(), username, password);
					if(!str.equals("")){
					editor = sharedPref.edit();
					editor.putString(typeList.get(i).getType(),str);					
					editor.commit();
					
					editorLogin=sharedPrefLogin.edit();
					editorLogin.putString(typeList.get(i).getType(),typeList.get(i).getType_id());					
					editorLogin.commit();
					}
				}
				//需要解析模板数据
//				if(needCreate){
					ts.TempletLoad(typeList.get(i).getType(),str);//解析获取到的模版
//				}
				//System.out.println("工单模板："+str);
				//System.out.println("巡检类型==========="+xml.getType().get(i).getCategoryType());
				//**巡检模版*/
				//=======================代维代码，eoms系统中用不到    modify by xiaxj=============================
				/*
				if(typeList.get(i).getCategoryType().equals("dwspecialty2")){
					
					if(str.equals("")){
						//System.out.println("str=============="+typeList.get(i).getType());
						System.out.println("巡检模板为空！！！！！！");
//						if(SplashActivity.loginFlag){
//						dialog.dismiss();
//						}
					}else{
					ts.TempletLoadXJ(typeList.get(i).getType(),str);
					System.out.println("str=============="+typeList.get(i).getType());
					System.out.println("str=============="+str);
					}
				}
				else{
					if(str.equals("")){
//						if(SplashActivity.loginFlag){
//						dialog.dismiss();
//						}
					}else{
						ts.TempletLoad(typeList.get(i).getType(),str);//解析获取到的模版
					}
				}
				*/
				//==================================================================
		}
		}
		
		map.put("IsLegal", isLegal);
		map.put("Ccount",getNumMap());
		if(isLegal){
			AppInfo.saveUserToApplication(context, new User(username, password));// 缓存用户名密码存Application内存
		}
		return map;
	}
	/**
	 * 获取工单、巡检个数
	 * @return
	 */
	private LoginModel getNumMap() {
		LoginModel lm = new LoginModel();
		lm.setGdNum("10");
		lm.setXjNum("20");
		return lm;
	}
	
	public String getTemplet(Context context, String categoryType,String version, String username, String password) {
		String str = null;
		TestXmlCreat tc = new TestXmlCreat(context, username, password);
		HTTPConnection hc = new HTTPConnection(context);
		tc.addElement(tc.recordInfo, "category", categoryType);
		tc.addElement(tc.recordInfo, "version", version);
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "L002");
		parmas.put("inputXml", tc.doc.asXML());
		str = hc.Sent(parmas);
		return str;
	}
	
	/**
	 * 检测TempletModel.TempletMap是否有值，有值返回true,没有值返回false
	 * 已登录并获取过模板，但TempletModel.TempletMap值被回收时，没有从配置文件读取缓存的工单模板给其赋值，并返回true。
	 * @return
	 */
	public boolean isTempletMapAvailable(Context context) {
		boolean result = false;
		if(TempletModel.TempletMap!=null && !TempletModel.TempletMap.isEmpty()){
			sharedPrefLogin=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
			sharedPref = context.getSharedPreferences("AUTOLOGOIN",Context.MODE_PRIVATE);
//			str=sharedPref.getString(xml.getType().get(i).getType(),"");//从本地取出模版
//			ts.TempletLoad(xml.getType().get(i).getType(),str);//解析获取到的模版
		}
		result = true;//???
		return result;
	}
	
	

}