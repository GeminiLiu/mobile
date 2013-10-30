package cn.com.ultrapower.ultrawf.control.design;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.design.RoleUserHandler;

public class RoleUserManager
{
	/**
	 * 获取角色细分下的用户，返回该角色细分下的用户的登录名集合
	 * @param roleID 角色细分ID
	 * @return 该角色细分下的用户的登录名集合
	 */
	public List<String> getRoleUsers(String roleID)
	{
		RoleUserHandler ruHandler = new RoleUserHandler();
		return ruHandler.getRoleUsers(roleID);
	}
}
