package com.ultrapower.eoms.common.portal.service;

import com.ultrapower.eoms.common.portal.model.UserSession;


public interface PortalManagerService 
{
   
   /**
   是否成功登陆系统
   @param loginName - 用户登陆名
   @param pwd - 用户密码
   @param isVerify - 是否密码验证
   @return boolean
    */
   public UserSession login(String loginName, String pwd, boolean isVerify);
   
   /**
    * 根据用户名和密码判断此用户是否可以登录
    * @param loginName - 用户登录名
    * @param pwd - 用户密码
    * @return
    */
   public boolean isValidateLoginInfo(String loginName, String pwd);
}
