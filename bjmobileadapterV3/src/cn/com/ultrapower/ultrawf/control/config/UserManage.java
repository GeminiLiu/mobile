package cn.com.ultrapower.ultrawf.control.config;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.config.ParUserModel;
import cn.com.ultrapower.ultrawf.models.config.User;
import cn.com.ultrapower.ultrawf.models.config.UserModel;

public class UserManage {
	
	/**
	 * 描述：登陆需要密码的。
	 */
	public UserModel UserLogin(String str_LoginName,String str_PassWord) 
	{
		User UserOp = new User();
		UserModel UserModelObj = UserOp.UserIsExist(str_LoginName);
		if(UserModelObj==null)
			return null;
		if (UserModelObj.GetPassWord()==null)
		{
			UserModelObj.SetPassWord("");
		}
		//密码加码 
		System.out.println(UserModelObj.GetPassWord());
		/*if (UserModelObj.GetPassWord().equals(str_PassWord))
		{
			return UserModelObj;
		}*/
		return UserModelObj;
		//return null;
	}	

	/**
	 * 描述：登陆不需要密码的。
	 */
	public UserModel UserLogin(String str_LoginName) 
	{
		User UserOp = new User();
		UserModel UserModelObj = UserOp.UserIsExist(str_LoginName);
		return UserModelObj;
	}	

	public UserModel getUserInfo(String str_LoginName) 
	{
		User UserOp = new User();
		UserModel UserModelObj = UserOp.UserIsExist(str_LoginName);
		return UserModelObj;
	}	
	
	public List GetList(int p_PageNumber,int p_StepRow)
	{
		User m_User=new User();
		return m_User.GetList(p_PageNumber,p_StepRow);
	}
	public List GetList(ParUserModel p_ParUserModel,int p_PageNumber,int p_StepRow)
	{
		User m_User=new User();
		return m_User.GetList(p_ParUserModel,p_PageNumber,p_StepRow);
	}
	
}
