package cn.com.ultrapower.eoms.user.userinterface;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.userinterface.bean.ElementInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserGroupPram;

public class UserGroupInterface
{

	static final Logger logger = (Logger) Logger.getLogger(UserGroupInterface.class);
	
	/**
	 * 查询组织结构的LIST。
	 */
	public List getGroupUserList(UserGroupPram usergroupPram)
	{
		List returnList = null;
		
		String groupParentid = Function.nullString(usergroupPram.getGroupParentid());
		if(!groupParentid.equals(""))
		{
			returnList = getGroupUserList(groupParentid);
		}else
		{
			String userCPID 		= Function.nullString(usergroupPram.getUser_CPID());
			String userLoginName 	= Function.nullString(usergroupPram.getUserLoginName());
			if(userCPID.equals("1"))
			{
				if(userLoginName.equals(""))
				{
					logger.info("用户登陆名不能为空！");
					return null;
				}else
				{
					returnList = getRootGroupList(userLoginName);
				}
			}else
			{
				returnList = getRootGroupList("");
			}
		}
		return returnList;
	}
	
	/**
	 * 查询组织结构的顶级节点的LIST。
	 */
	public List getRootGroupList(String userLoginName)
	{
		List list = null;
		UserGroupInterfaceSQL usergroupsql = new UserGroupInterfaceSQL();
		list = usergroupsql.getRootGroupListSQL(userLoginName);
		return list;
	}
	
	/**
	 * 查询组织结构的组与用户的LIST。
	 */
	public List getGroupUserList(String groupParentid)
	{
		List list = null;
		List userList = null;
		UserGroupInterfaceSQL usergroupsql = new UserGroupInterfaceSQL();
		list = usergroupsql.getGroupListSQL(groupParentid);
		userList = usergroupsql.getUserListSQL(groupParentid);
		list = getListMergeValue(list,userList);
		return list;
	}

	
	
	/**
	 * 根据传入的二个List将后一个List值添加到前一个List中。
	 * 日期 2007-4-16
	 * @author wangyanguang
	 * @param endList		目标List
	 * @param sourceList	源List 
	 */
	public List getListMergeValue(List endList,List sourceList)
	{
		if(sourceList!=null)
		{
			for(Iterator it = sourceList.iterator();it.hasNext();)
			{
				endList.add(it.next());
			}
		}
		if(endList!=null)
		{
			return endList;
		}
		else
		{
			return null;
		}
	}
	
	
	

	public static void main(String[] args)
	{
//		//根据用户登陆名，及单位标识，查询出顶级的公司List。
//		UserGroupPram usergrouppram = new UserGroupPram();
//		//用户登陆名。
//		usergrouppram.setUserLoginName("Demo");
//		//组的父ID：当查义顶级公司节点时，此参数为空。
//		usergrouppram.setGroupParentid("");
//		//公司标识，1：代表查询当前登陆用户所在的公司，0：代表查询所有公司。
//		usergrouppram.setUser_CPID("1");
//		UserGroupInterface usergroupinterface = new UserGroupInterface();
//		List list = usergroupinterface.getGroupUserList(usergrouppram);
//		if(list!=null)
//		{
//			for(Iterator it = list.iterator();it.hasNext();)
//			{
//				//返回值List中是一个Bean信息。
//				ElementInfoBean elementinfo = (ElementInfoBean)it.next();
//				//ID,是组时为组ID，是用户是为用户登陆名。
//				String id   = elementinfo.getElementid();
//				//名称：是组是为组名称，是用户是为用户中文名称。
//				String name = elementinfo.getElementname();
//				//标识，1：用户，0：组。
//				String flag = elementinfo.getElementflag();
//				System.out.println("ID:"+id+",NAME:"+name+",flag:"+flag);
//			}
//		}
		
		
		//根据组的父ID，查询出子组信息List及用此组ID下的用户信息List
		UserGroupPram usergrouppram = new UserGroupPram();
		//用户登陆名。
		usergrouppram.setUserLoginName("wangxuelei");
		//组的父ID：当查义顶级公司节点时，此参数为空。
		usergrouppram.setGroupParentid("600001");
		//公司标识，1：代表查询当前登陆用户所在的公司，0：代表查询所有公司。
		usergrouppram.setUser_CPID("1");
		UserGroupInterface usergroupinterface = new UserGroupInterface();
		List list = usergroupinterface.getGroupUserList(usergrouppram);
		if(list!=null)
		{
			for(Iterator it = list.iterator();it.hasNext();)
			{
				//返回值List中是一个Bean信息。
				ElementInfoBean elementinfo = (ElementInfoBean)it.next();
				//ID,是组时为组ID，是用户是为用户登陆名。
				String id   = elementinfo.getElementid();
				//名称：是组是为组名称，是用户是为用户中文名称。
				String name = elementinfo.getElementname();
				//标识，1：用户，0：组。
				String flag = elementinfo.getElementflag();
				String userid   = elementinfo.getUserid();
				System.out.println("ID:"+id+",NAME:"+name+",flag:"+flag+",用户ID："+userid);
			}
		}
	}
}
