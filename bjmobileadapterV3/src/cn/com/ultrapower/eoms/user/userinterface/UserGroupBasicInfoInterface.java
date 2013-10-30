package cn.com.ultrapower.eoms.user.userinterface;

import cn.com.ultrapower.eoms.user.userinterface.bean.UserGroupBasicInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserGroupBasicInfoPram;

public class UserGroupBasicInfoInterface
{
	
	/**
	 * 根据用户登陆名，获得用户的部门名，公司名接口。
	 * 日期 2007-4-25
	 * @author wangyanguang
	 * @param pram
	 * @return UserGroupBasicInfo
	 */
	public UserGroupBasicInfo getUserGroupInfo(UserGroupBasicInfoPram pram)
	{
		UserGroupBasicInfo returnInfo = null;
		UserGroupBasicInfoInterfaceSQL infosql = new UserGroupBasicInfoInterfaceSQL();
		returnInfo = infosql.getUserGroupBasicInfo(pram);
		
		return returnInfo;
	}
	
	
	
	public static void main(String[] args)
	{
		//参数Bean信息。当前只有一个set与get 方法，就是用户登陆名。
		UserGroupBasicInfoPram pram = new UserGroupBasicInfoPram();
		pram.setUserLoginName("wangxuelei");
		
		UserGroupBasicInfoInterface userinfo = new UserGroupBasicInfoInterface();
		//返回值Bean（UserGroupBasicInfo）
		UserGroupBasicInfo basicinfo = userinfo.getUserGroupInfo(pram);
		System.out.println("公司名："+basicinfo.getUserCPName());
		System.out.println("部门名："+basicinfo.getUserDPName());
	}

}
