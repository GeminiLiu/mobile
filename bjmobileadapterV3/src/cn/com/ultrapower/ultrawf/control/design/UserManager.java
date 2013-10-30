package cn.com.ultrapower.ultrawf.control.design;

import java.util.*;

import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.ultrawf.models.design.*;
import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class UserManager
{
	public List<UserModel> getUserList()
	{
		GroupUserInterfaceTmp guit = new GroupUserInterfaceTmp();
		return guit.getUserList();
	}
	
	public void addUserGroup(List<String> userIDs, String groupID)
	{
		GroupUserInterfaceTmp guit = new GroupUserInterfaceTmp();
		RoleUserHandler ruHandler = new RoleUserHandler();
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		for(Iterator<String> it = userIDs.iterator(); it.hasNext();)
		{
			String userID = it.next();
			UserModel uModel = guit.getUser(userID);
			if((";" + uModel.getGroups()).indexOf(";" + groupID + ";") < 0)
			{
				uModel.setGroups(uModel.getGroups() + groupID +  ";");
				guit.setUser(uModel);
				ruHandler.addRoleUser(RemedyOp, userID, groupID);
			}
		}
		RemedyOp.RemedyLogout();
	}
	
	public void addUserGroup(String userID, List<String> groupIDs)
	{
		GroupUserInterfaceTmp guit = new GroupUserInterfaceTmp();
		UserModel uModel = guit.getUser(userID);
		StringBuffer bs = new StringBuffer();
		RoleUserHandler ruHandler = new RoleUserHandler();
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		for(String groupID : groupIDs)
		{
			bs.append(groupID + ";");
			ruHandler.addRoleUser(RemedyOp, userID, groupID);
		}
		RemedyOp.RemedyLogout();
		uModel.setGroups(uModel.getGroups() + bs.toString());
		guit.setUser(uModel);
	}
	
	public void removeUserGroup(List<String> users, String groupID)
	{
		GroupUserInterfaceTmp guit = new GroupUserInterfaceTmp();
		RoleUserHandler ruHandler = new RoleUserHandler();
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		for(Iterator<String> it = users.iterator(); it.hasNext();)
		{
			String userID = it.next();
			UserModel uModel = guit.getUser(userID);
			String tmpGroups = ";" + uModel.getGroups();
			if(tmpGroups.indexOf(";" + groupID + ";") > -1)
			{
				uModel.setGroups(tmpGroups.replaceAll(";" + groupID + ";", ";").substring(1));
				guit.setUser(uModel);
				ruHandler.removeRoleUser(RemedyOp, userID, groupID);
			}
		}
		RemedyOp.RemedyLogout();
	}
	
	/**
	 * 批量删除用户细分角色关系
	 * @author lihongbo
	 * @param userID
	 * @param groupIDs
	 */
	public void removeUserGroup(String userID,List<String> groupIDs){
		GroupUserInterfaceTmp guit = new GroupUserInterfaceTmp();
		RoleUserHandler ruHandler = new RoleUserHandler();
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		for(String groupID : groupIDs)
		{   
			UserModel uModel = guit.getUser(userID);
			String tmpGroups = ";" + uModel.getGroups();
			if(tmpGroups.indexOf(";" + groupID + ";") > -1)
			{
				uModel.setGroups(tmpGroups.replaceAll(";" + groupID + ";", ";").substring(1));
				guit.setUser(uModel);
			}
			ruHandler.removeRoleUser(RemedyOp, userID, groupID);
		}
		RemedyOp.RemedyLogout();
	}
}
