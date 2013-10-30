package com.ultrapower.eoms.mobile.util;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.userinterface.SendScopeInterfaceSQL;
import cn.com.ultrapower.eoms.user.userinterface.bean.ElementInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SendScopePram;

public class SendScopeInterface
{
	static final Logger logger = (Logger) Logger.getLogger(SendScopeInterface.class);
	/**
	 * 根据传入的参数Bean信息查询出用户所能派发的组及用户信息。
	 * 日期 2007-1-13
	 * @author wangyanguang
	 * @param sendscopepram		
	 */
	public List getElementInfo(SendScopePram sendscopepram)
	{
		List returnList 	 = null;
		String username 	 = "";
		String parentid		 = "";
		String sourcename 	 = "";
		String sendscopetype = "";
		String customerflag	 = "";
		
		username 	  = Function.nullString(sendscopepram.getUserLoginName());
		parentid	  = Function.nullString(sendscopepram.getNodeParentid());
		sourcename 	  = Function.nullString(sendscopepram.getSourceName());
		sendscopetype = Function.nullString(sendscopepram.getSendscopetype());
		customerflag  = Function.nullString(sendscopepram.getcustomerflag());
		
		
		if(username.equals("")||sourcename.equals("")||sendscopetype.equals(""))
		{
			logger.error("用户名或者资源名及派发类型不能为空！您的输入有误，请检查参数！");
			return null;
		}else
		{
			if(customerflag.equals("1"))
			{
				//自定义派发树。
				if(parentid.equals(""))
				{
					returnList = getCustomerElemetInfo(sendscopepram);
				}else
				{
					returnList = getCustomerGroupElementInfo(sendscopepram);
				}
				
			}
			else if(customerflag.equals("2"))
			{
				//无权限派发树。
				if(parentid.equals(""))
				{
					returnList = getNoGrandRootElement(sendscopepram);
				}
				else
				{
					returnList = getNoGrandNextElement(sendscopepram);
				}
			}
			else
			{
				if(parentid.equals(""))
				{
					returnList = getRootElement(sendscopepram);
				}
				else
				{
					returnList = getNextElement(sendscopepram);
				}
			}
		}
		if(returnList!=null)
		{
			return returnList;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 根据用户登陆名与资源名查询出此用户所能派发的组（顶级组）
	 * 日期 2007-1-13
	 * @author wangyanguang
	 * @param username		用户登陆名
	 * @param sourcename	资源名
	 */
	public List getRootElement(SendScopePram sendscopepram)
	{
		List list = null;
		SendScopeInterfaceSQL sendscopesql = new SendScopeInterfaceSQL();
		list = sendscopesql.getParentElement(sendscopepram);
		if(list!=null)
		{
			return list;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 根据组ID与资源ID查询出组的子组信息及组的用户信息（组成员信息）
	 * 日期 2007-1-13
	 * @author wangyanguang
	 * @param sendscopepram		参数Bean信息
	 */
	public List getNextElement(SendScopePram sendscopepram)
	{
		SendScopeInterfaceSQL sendscopesql = new SendScopeInterfaceSQL();
		List returnList 	 = null;
		List userList 		 = null;
		String groupid		 = "";
		String sourcename	 = "";
		String username		 = "";
		String actionvalue	 = "";
		String sendscopetype = "";
		String workflowtype  = "";
		
		groupid 	  = Function.nullString(sendscopepram.getNodeParentid());
		username 	  = Function.nullString(sendscopepram.getUserLoginName());
		sourcename 	  = Function.nullString(sendscopepram.getSourceName());
		actionvalue	  = Function.nullString(sendscopepram.getActionvalue());
		sendscopetype = Function.nullString(sendscopepram.getSendscopetype());
		workflowtype  = Function.nullString(sendscopepram.getWorkflowtype());
		
		groupid=Function.getStrZero(new Long(groupid));
		returnList = sendscopesql.getChildElement(groupid,username,sourcename,sendscopetype);
		if(actionvalue.equals(""))
		{
			userList = sendscopesql.getUserElement(groupid,sourcename,workflowtype);
		}
		else
		{
			userList = sendscopesql.getUserElement(groupid,sourcename,actionvalue,workflowtype);
		}
		
		returnList = getListMergeValue(returnList,userList);
		
		if(returnList!=null)
		{
			return returnList;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 根据参数得到自定义派发信息。
	 * 日期 2007-1-19
	 * @author wangyanguang
	 * @param sendscopepram
	 */
	public List getCustomerElemetInfo(SendScopePram sendscopepram)
	{
		List list = null;
		SendScopeInterfaceSQL sendscopesql = new SendScopeInterfaceSQL();
		list = sendscopesql.getCustomerSendScopeInfo(sendscopepram);
		if(list!=null)
		{
			return list;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 根据自定义组ID查询此组ID对应的派发组信息。
	 * 日期 2007-1-19
	 * @author wangyanguang
	 * @param sendscopepram
	 */
	public List getCustomerGroupElementInfo(SendScopePram sendscopepram)
	{
		List list = null;
		SendScopeInterfaceSQL sendscopesql = new SendScopeInterfaceSQL();
		list = sendscopesql.getCustomerGroupInfo(sendscopepram);
		if(list!=null)
		{
			return list;
		}
		else
		{
			return null;
		}
	}
	/**
	 * 根据传入的二个List将后一个List值添加到前一个List中。
	 * 日期 2007-1-13
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
	public List getNoGrandRootElement(SendScopePram sendscopepram)
	{
		List list = null;
		SendScopeInterfaceSQL sendscopesql = new SendScopeInterfaceSQL();
		list = sendscopesql.getNoGrandParentElement(sendscopepram);
		if(list!=null)
		{
			return list;
		}
		else
		{
			return null;
		}
	}
	
	
	public List getNoGrandNextElement(SendScopePram sendscopepram)
	{
		SendScopeInterfaceSQL sendscopesql = new SendScopeInterfaceSQL();
		List returnList 	 = null;

		returnList = sendscopesql.getNoGrandChildElement(sendscopepram);
		
		if(returnList!=null)
		{
			return returnList;
		}
		else
		{
			return null;
		}
	}
	
	public static void main(String args[])
	{
		//新添加的字段。
		//0:派发1：提审
		//private String sendscopetype =  null;
		
		SendScopePram sendpram = new SendScopePram();
		sendpram.setUserLoginName("Demo");     //用户登陆名
		sendpram.setNodeParentid("600024");  //组ID
		sendpram.setSourceName("UltraProcess:App_Eoms_Task");  //资源名
		sendpram.setcustomerflag("0");
		sendpram.setSendscopetype("0");
		sendpram.setWorkflowtype("0");
		sendpram.setActionvalue("");
		
		SendScopeInterface sendscopeinterface = new SendScopeInterface();
		List list = sendscopeinterface.getElementInfo(sendpram);
		
		for(Iterator its = list.iterator();its.hasNext();)
		{
			ElementInfoBean elementinfo = (ElementInfoBean)its.next();
			System.out.println("标识："+elementinfo.getElementflag());
			System.out.println("ID："+elementinfo.getElementid());
			System.out.println("名称："+elementinfo.getElementname());
			System.out.println("手机号:"+elementinfo.getMobile());
			System.out.println("DN:"+elementinfo.getGroupFullName());
			
		}
	}
}
