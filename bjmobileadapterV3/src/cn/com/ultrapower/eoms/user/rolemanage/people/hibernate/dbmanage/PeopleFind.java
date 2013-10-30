package cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage;

import java.util.List;

import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

/**
 * <p>Description:使用hibernate从数据库中查找字段<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-17
 */
public class PeopleFind {
	
	/**
	 * <p>Description:调用GetUserInfoList类的方法getUserInfoID()查找所有用户信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-17
	 * @return List
	 */
	public List find()
	{
		GetUserInfoList userInfoList = new GetUserInfoList();
    	return userInfoList.getUserList();
	}
	
	/**
	 * <p>Description:调用GetUserInfoList类的方法getUserInfoID(),对用户信息进行修改<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-17
	 * @param id
	 * @return SysPeoplepo
	 */
	public SysPeoplepo findModify(String id)
	{
		GetUserInfoList userInfoList = new GetUserInfoList();
    	return userInfoList.getUserInfoID(id);
	}
	
}
