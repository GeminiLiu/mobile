package cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.eoms.user.authorization.bean.UserSendScopeGroupInfo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.userinterface.SendScopeInterfaceSQL;
import cn.com.ultrapower.eoms.user.userinterface.bean.ElementInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SendScopePram;

public class UserSendScopeSQL
{
	public String getDemoParentInfo(SendScopePram sendpram)
	{
		SendScopePram sendscopepram = new SendScopePram();
		String sourcename = Function.nullString(sendpram.getSourceName());
		String sendtype = Function.nullString(sendpram.getSendscopetype());
		sendscopepram.setUserLoginName(Function.nullString(sendpram.getUserLoginName()));
		sendscopepram.setSourceName(sourcename);
		sendscopepram.setSendscopetype(sendtype);
		List list = getElementInfo(sendscopepram);
		StringBuffer sbf 	= new StringBuffer();
		sbf.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
		sbf.append("try{");
		for(Iterator it = list.iterator();it.hasNext();)
		{
			ElementInfoBean elementinfo = (ElementInfoBean)it.next();
			String groupName 	= elementinfo.getElementname();
			String groupID 		= elementinfo.getElementid();
			String recordid  	= elementinfo.getRecordid();
			//顶级
			//顶级无选择
			//sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"sendscopeinfotree.jsp?gid="+groupID+";"+sourcename+";"+sendtype+"\"));");
			//顶级单选
			//sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"sendscopeinfotree.jsp?gid="+groupID+";"+sourcename+";"+sendtype+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+recordid+"\"));");
			//顶级复选
			//sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"sendscopeinfotree.jsp?gid="+groupID+";"+sourcename+";"+sendtype+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupID+"\"));");
			
			sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"usersendscopetree.jsp?gid="+groupID+";"+sourcename+";"+sendtype+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupID+";"+groupName+";"+recordid+"\"));");
		}
		sbf.append("}catch(e){}");
		sbf.append("</script>");
		sbf.append("<script>document.write(tree);</script>");
		
		return sbf.toString();
	}
	
	public String getGroupInfo(SendScopePram sendscopepram)
	{
		StringBuffer sbf 	= new StringBuffer();
		String sourcename = sendscopepram.getSourceName();
		String sendtype = sendscopepram.getSendscopetype();
		
		List list = getElementInfo(sendscopepram);
		for(Iterator it  = list.iterator();it.hasNext();)
		{
			ElementInfoBean elementinfo =(ElementInfoBean)it.next();
			
			String groupName 	= elementinfo.getElementname();
			String groupID		= elementinfo.getElementid();
			String recordid 	= elementinfo.getRecordid();
	  		//非顶级
			//不能选择
			//sbf.append("<tree text=\""+groupName+"\" src=\"usersendscopetree.jsp?gid="+groupID+";"+sourcename+";"+sendtype+"\" />");
			//单选
			//sbf.append("<tree text=\""+groupName+"\" src=\"usersendscopetree.jsp?gid="+groupID+";"+usertype+"\"  funpram=\""+groupName+":"+groupID+"\"/>");
			//复选
			sbf.append("<tree text=\""+groupName+"\" src=\"usersendscopetree.jsp?gid="+groupID+";"+sourcename+";"+sendtype+"\"  schkbox=\""+groupID+";"+groupName+";"+recordid+"\"/>");
	  		System.out.println(sbf.toString());
		}
		return sbf.toString();
	}
	
	
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
		
		username 	  = Function.nullString(sendscopepram.getUserLoginName());
		parentid	  = Function.nullString(sendscopepram.getNodeParentid());
		sourcename 	  = Function.nullString(sendscopepram.getSourceName());
		sendscopetype = Function.nullString(sendscopepram.getSendscopetype());
		
		if(username.equals("")||sourcename.equals("")||sendscopetype.equals(""))
		{
			System.out.println("用户名或者资源名及派发类型不能为空！您的输入有误，请检查参数！");
			return null;
		}else
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
	 * 根据组ID与资源ID查询出组的子组信息.
	 * 日期 2007-1-13
	 * @author wangyanguang
	 * @param sendscopepram		参数Bean信息
	 */
	public List getNextElement(SendScopePram sendscopepram)
	{
		SendScopeInterfaceSQL sendscopesql = new SendScopeInterfaceSQL();
		List returnList 	 = null;
		String groupid		 = "";
		String sourcename	 = "";
		String username		 = "";
		String sendscopetype = "";
		
		groupid 	  = Function.getStrZeroConvert(Function.nullString(sendscopepram.getNodeParentid()));
		username 	  = Function.nullString(sendscopepram.getUserLoginName());
		sourcename 	  = Function.nullString(sendscopepram.getSourceName());
		sendscopetype = Function.nullString(sendscopepram.getSendscopetype());
		
		returnList = sendscopesql.getChildElement(groupid,username,sourcename,sendscopetype);
		
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
	 * 根据用户自定义派发组名查询出此组名对应的多条记录信息，同时关联组信息表，查出组名，关联资源表，查出资源名称。
	 * 日期 2007-1-18
	 * @author wangyanguang
	 * @param groupname
	 */
	public List getUserSendScopeList(String groupnameid)
	{
		List returnList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct usersendscopepo.groupid,usersendscopepo.grandid,grouppo.c630000018,sourceconfig.sourceName,usersendscopepo.sendscopetype,usersendscopepo.note,namemagtable.groupname ");
	 	sql.append(" from UserSendScope usersendscopepo,SysGrouppo grouppo,Sourceconfig sourceconfig,UserSendScopeNameMag namemagtable ");
	 	sql.append(" where usersendscopepo.groupid=grouppo.c1");
	 	sql.append(" and usersendscopepo.sourceid=sourceconfig.sourceId");
	 	sql.append(" and usersendscopepo.groupnameid='"+groupnameid+"'");
	 	sql.append(" and usersendscopepo.groupnameid=namemagtable.id");
		try
		{
			List list = HibernateDAO.queryObject(sql.toString());
			for(Iterator it = list.iterator();it.hasNext();)
			{
				Object[] obj 		= (Object[])it.next();
				String groupid  	= (String)obj[0];
				String grandid   	= (String)obj[1];
				String gname 		= (String)obj[2];
				String sourcename 	= (String)obj[3];
				String sendtype 	= (String)obj[4];
				String senddesc		= (String)obj[5];
				String namemag		= (String)obj[6];
				UserSendScopeGroupInfo sendscopegrandinfo = new UserSendScopeGroupInfo();
				sendscopegrandinfo.setGroupid(groupid);
				sendscopegrandinfo.setGroupname(gname);
				sendscopegrandinfo.setRoleid(grandid);
				sendscopegrandinfo.setSourcename(sourcename);
				sendscopegrandinfo.setSendtype(sendtype);
				sendscopegrandinfo.setUsersendscopedesc(senddesc);
				sendscopegrandinfo.setNamemag(namemag);
				returnList.add(sendscopegrandinfo);
			}
		}
		catch(Exception e)
		{
			System.out.print("查询出与异常HibernageDAO 自定义派发中查询!");
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
	 * 根据用户自定义派发组名查询出此组名对应的记录ID。
	 * 日期 2007-1-18
	 * @author wangyanguang
	 * @param groupname
	 */
	public List getUserSendScopeID(String groupname)
	{
		List returnList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct usersendscopepo.id ");
	 	sql.append(" from UserSendScope usersendscopepo ");
	 	sql.append(" where usersendscopepo.groupnameid='"+groupname+"'");
		try
		{
			returnList = HibernateDAO.queryObject(sql.toString());
			
		}
		catch(Exception e)
		{
			System.out.print("查询出与异常HibernageDAO 自定义派发中查询!");
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
	
	
	public static void main(String[] args)
	{
		StringBuffer sb = new StringBuffer();
		UserSendScopeSQL sendsql = new UserSendScopeSQL();
		List list = sendsql.getUserSendScopeList("test");
		for(Iterator it = list.iterator();it.hasNext();)
		{
			UserSendScopeGroupInfo ussinfo = (UserSendScopeGroupInfo)it.next();
			sb.append(ussinfo.getGroupid());
			sb.append(";");
			sb.append(ussinfo.getGroupname());
			sb.append(";");
			sb.append(ussinfo.getRoleid());
			sb.append(",");
		}
		System.out.println(sb.toString());
	}
}
