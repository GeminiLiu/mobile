package cn.com.ultrapower.eoms.util.user;

import java.util.*;
import javax.servlet.http.HttpSession;

/**该类功能为将传入的登录人员的信息类合成不同的人员信息类default、agent、now * 
 * @author zhaoqi
 * param:UserInformation
 * comment:该参数是由UserInfoAplication中的getUserInfoByName返回的用户信息类
 */
public class UserInfoSet {
	
	/////该方法返回默认的登录人员信息类
	public UserInformation getDefaultUserInfo(UserInformation userinfo){
		
		UserInformation defaultUserInfo = null;
		defaultUserInfo = userinfo;
		defaultUserInfo.setDefaultFlag("true");
		return defaultUserInfo;
	}
///////该方法返回当前各程序页面使用的人员信息类
	public UserInformation getNowUserInfo(UserInformation userinfo){
		
		UserInformation nowUserInfo = null;
		nowUserInfo = userinfo;
		nowUserInfo.setDefaultFlag("true");
		return nowUserInfo;
	}
/////该方法返回被代理的值班人员信息类列表
	public List getAgentUserInfo(List userinfolist){
		
		List agentuserlist	= new ArrayList();
		try{
		if(userinfolist.size()>0){
			for(int i=0;i<userinfolist.size();i++){
				UserInformation userinformation = (UserInformation)userinfolist.get(i);
				if(userinformation != null){
					userinformation.setDefaultFlag("false");
					agentuserlist.add(userinformation);
				}
			}
		}else{
			UserInformation userinformation = null;
			agentuserlist.add(userinformation);
		}
		}catch(Exception e){
			e.printStackTrace();
			UserInformation userinformation = null;
			agentuserlist.add(userinformation);
		}
		
		return agentuserlist;
	}
////////该方法用在点击导航条的菜单时，当前页面使用人员信息类设置为默认登录用户信息类
	public void setNowUserInfoNavigation(HttpSession session){
		
		UserInformation defaultuserinfo = (UserInformation)session.getAttribute("defaultuserInfo");	
		if(defaultuserinfo!=null){
			session.setAttribute("userInfo", defaultuserinfo);
		}
		
	}
////////该方法用在点击左侧页面里的菜单时，根据传入的userID将当前页面使用人员信息类设置成相应的用户信息类
	public void setNowUserInfoLeftMenu(HttpSession session,long userid){
		
		UserInformation   defaultuserinfo = (UserInformation)session.getAttribute("defaultuserInfo");
		List              agentuserlist   = (List)session.getAttribute("agentuserInfo");
		if(agentuserlist!=null){
		if(agentuserlist.size() > 0){
			for(int i=0;i<agentuserlist.size();i++){
				UserInformation userinfo = (UserInformation)agentuserlist.get(i);
				if(userid==userinfo.getUserIntID()){//////如果传入的用户ID为被代理人的ID，则将当前SESSION中的用户信息设为被代理人的信息类
					session.setAttribute("userInfo", userinfo);
					break;
				}else{//////////否则将其设为默认登录人员类信息
					session.setAttribute("userInfo", defaultuserinfo);
				}
				
			}
		}else{///////如果被代理人列表为空则，直接设置为默认的人员信息类
			
			session.setAttribute("userInfo", defaultuserinfo);
		}
		}else{
			session.setAttribute("userInfo", defaultuserinfo);
		}
	}
}






