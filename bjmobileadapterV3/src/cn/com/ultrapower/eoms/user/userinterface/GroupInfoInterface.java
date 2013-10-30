package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.GroupFieldFlag;
import cn.com.ultrapower.eoms.user.userinterface.bean.GroupInfo;

/**
 * <p>Description:组模块接口<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-27
 */
public class GroupInfoInterface {

	static final Logger logger	= (Logger) Logger.getLogger(GroupInfoInterface.class);

	private String grouptablename			= "";

	public GroupInfoInterface()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			grouptablename		= getTableProperty.GetFormName("RemedyTgroup");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	/**
	 * <p>Description:根据传过来的参数selectQuality确定sql语句select
	 * 和传过来的参数whereQuality确定sql语句where条件返回一个List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-27
	 * @param selectQuality		0代表要查询的字段,1代表不查
	 * @param whereQuality
	 * @return List
	 */
	public List getGroupInfo(GroupFieldFlag selectQuality,GroupInfo whereQuality)
	{	
		//根据传入的第一个参数确定sql语句的select条件
		StringBuffer selectString	= new StringBuffer();
		
		String select_Group_ID			= Function.nullString(selectQuality.getGroupId());
		String select_Group_Name		= Function.nullString(selectQuality.getGroupName());
		String select_Group_FullName	= Function.nullString(selectQuality.getGroupFullname());
		String select_Group_ParentID	= Function.nullString(selectQuality.getGroupParentid());
		String select_Group_Type		= Function.nullString(selectQuality.getGroupType());
		String select_Group_Phone		= Function.nullString(selectQuality.getGroupPhone());
		String select_Group_Fax			= Function.nullString(selectQuality.getGroupFax());
		String select_Group_Status		= Function.nullString(selectQuality.getGroupStatus());
		String select_Group_CompanyID	= Function.nullString(selectQuality.getGroupCompanyid());
		String select_Group_CompanyType	= Function.nullString(selectQuality.getGroupCompanytype());
		String select_Group_Desc		= Function.nullString(selectQuality.getGroupDesc());
		String select_GroupIntId		= Function.nullString(selectQuality.getGroupIntId());
		String select_Group_DnId		= Function.nullString(selectQuality.getGroupDnId());	
		
		if(select_Group_ID.equals("0"))
		{
			selectString.append(" C1 as groupId,");
		}
		if(select_Group_Name.equals("0"))
		{
			selectString.append(" C630000018 as groupName,");
		}
		if(select_Group_FullName.equals("0"))
		{
			selectString.append(" C630000019 as groupFullName,");
		}
		if(select_Group_ParentID.equals("0"))
		{
			selectString.append(" C630000020 as groupParentId,");
		}
		if(select_Group_Type.equals("0"))
		{
			selectString.append(" C630000021 as groupType,");
		}
		if(select_Group_Phone.equals("0"))
		{
			selectString.append(" C630000023 as groupPhone,");
		}
		if(select_Group_Fax.equals("0"))
		{
			selectString.append(" C630000024 as groupFax,");
		}
		if(select_Group_Status.equals("0"))
		{
			selectString.append(" C630000025 as groupStatus,");
		}
		if(select_Group_CompanyID.equals("0"))
		{
			selectString.append(" C630000026 as groupCompanyId,");
		}
		if(select_Group_CompanyType.equals("0"))
		{
			selectString.append(" C630000027 as groupCompanyType,");
		}
		if(select_Group_Desc.equals("0"))
		{
			selectString.append(" C630000028 as groupDesc,");
		}
		if(select_GroupIntId.equals("0"))
		{
			selectString.append(" C630000030 as groupIntId,");
		}
		if(select_Group_DnId.equals("0"))
		{
			selectString.append(" C630000037 as groupDnId,");
		}
		
		//根据传入的第二个参数确定where条件
		StringBuffer whereString	= new StringBuffer();
		
		String where_Group_ID			= Function.nullString(whereQuality.getGroupId());
		String where_Group_Name			= Function.nullString(whereQuality.getGroupName());
		String where_Group_FullName		= Function.nullString(whereQuality.getGroupFullname());
		String where_Group_ParentID		= Function.nullString(whereQuality.getGroupParentid());
		String where_Group_Type			= Function.nullString(whereQuality.getGroupType());
		String where_Group_Phone		= Function.nullString(whereQuality.getGroupPhone());
		String where_Group_Fax			= Function.nullString(whereQuality.getGroupFax());
		String where_Group_Status		= Function.nullString(whereQuality.getGroupStatus());
		String where_Group_CompanyID	= Function.nullString(whereQuality.getGroupCompanyid());
		String where_Group_CompanyType	= Function.nullString(whereQuality.getGroupCompanytype());
		String where_Group_Desc			= Function.nullString(whereQuality.getGroupDesc());
		String where_GroupIntId			= String.valueOf(whereQuality.getGroupIntId());
		String where_Group_DnId			= Function.nullString(whereQuality.getGroupDnId());
		
		if(where_Group_ID!=null&&!where_Group_ID.equals(""))
		{
			whereString.append(" and C1='"+where_Group_ID+"'");
		}
		if(where_Group_Name!=null&&!where_Group_Name.equals(""))
		{
			whereString.append(" and C630000018='"+where_Group_Name+"'");
		}
		if(where_Group_FullName!=null&&!where_Group_FullName.equals(""))
		{
			whereString.append(" and C630000019='"+where_Group_FullName+"'");
		}
		if(where_Group_ParentID!=null&&!where_Group_ParentID.equals(""))
		{
			whereString.append(" and C630000020='"+where_Group_ParentID+"'");
		}
		if(where_Group_Type!=null&&!where_Group_Type.equals(""))
		{
			whereString.append(" and C630000021='"+where_Group_Type+"'");
		}
		if(where_Group_Phone!=null&&!where_Group_Phone.equals(""))
		{
			whereString.append(" and C630000023='"+where_Group_Phone+"'");
		}
		if(where_Group_Fax!=null&&!where_Group_Fax.equals(""))
		{
			whereString.append(" and C630000024='"+where_Group_Fax+"'");
		}
		if(where_Group_Status!=null&&!where_Group_Status.equals(""))
		{
			whereString.append(" and C630000025='"+where_Group_Status+"'");
		}
		if(where_Group_CompanyID!=null&&!where_Group_CompanyID.equals(""))
		{
			whereString.append(" and C630000026='"+where_Group_CompanyID+"'");
		}
		if(where_Group_CompanyType!=null&&!where_Group_CompanyType.equals(""))
		{
			whereString.append(" and C630000027='"+where_Group_CompanyType+"'");
		}
		if(where_Group_Desc!=null&&!where_Group_Desc.equals(""))
		{
			whereString.append(" and C630000028='"+where_Group_Desc+"'");
		}
		if(!where_GroupIntId.equals("0")&&!where_GroupIntId.equals(""))
		{
			whereString.append(" and C630000030='"+where_GroupIntId+"'");
		}
		if(where_Group_DnId!=null&&!where_Group_DnId.equals(""))
		{
			whereString.append(" and C630000037='"+where_Group_DnId+"'");
		}

		//根据先前的两个参数确定最终的sql语句
		StringBuffer sqlString	= new StringBuffer();
		
		sqlString.append(" select");
		sqlString.append(Function.splitString(selectString.toString(),","));
		sqlString.append("  from  "+grouptablename );
		sqlString.append("  where 1=1 ");
		sqlString.append(whereString.toString());
		IDataBase dataBase  = null;
		Statement stm	 = null;
		ResultSet rs	 = null;
		try
		{
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stm		= dataBase.GetStatement();
			//获得数据库查询结果集
			rs		= dataBase.executeResultSet(stm,sqlString.toString());
			//将ResultSet结果集作为参数通过方法getResultSetList得到最终定制的List
			List list			= getResultSetList(rs,sqlString.toString());
			stm.close();
			return list;
		}
		catch(Exception e)
		{
			logger.error("[495]GroupInfoInterface.getGroupInfo() 根据传过来的参数selectQuality和whereQuality返回一个List"+e.getMessage());
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	
	/**
	 * <p>Description:根据传入的sql语句查询返回List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-25
	 * @param sqlString
	 * @return List
	 */
	public List getGroupInfo(String sqlString)
	{
		IDataBase dataBase = null;
		Statement stm		= null;
		ResultSet rs	    = null;
		try
		{
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stm		= dataBase.GetStatement();
			//获得数据库查询结果集
			rs		= dataBase.executeResultSet(stm,sqlString);
			//将ResultSet结果集作为参数通过方法getResultSetList得到最终定制的List
			List list			= getResultSetList(rs,sqlString);
			stm.close();
			dataBase.closeConn();
			return list;
		}
		catch(Exception e)
		{
			logger.error("[496]GroupInfoInterface.getGroupInfo() 根据传入的sql语句查询返回List"+e.getMessage());
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	
	/**
	 * <p>Description:通过结果集返回包含Bean的List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-25
	 * @param rs
	 * @return List
	 */
	public List getResultSetList(ResultSet rs,String str)
	{
		ArrayList list = new ArrayList();
		try
		{
			
			
			while(rs.next())
			{	
				GroupInfo groupInfo = new GroupInfo();
				if(str.indexOf("groupId")!=-1)
				{
					groupInfo.setGroupId(rs.getString("groupId"));
				}
				if(str.indexOf("groupName")!=-1)
				{
					groupInfo.setGroupName(rs.getString("groupName"));
				}
				if(str.indexOf("groupFullName")!=-1)
				{
					groupInfo.setGroupFullname(rs.getString("groupFullName"));
				}
				if(str.indexOf("groupParentId")!=-1)
				{
					groupInfo.setGroupParentid(rs.getString("groupParentId"));
				}
				if(str.indexOf("groupType")!=-1)
				{
					groupInfo.setGroupType(rs.getString("groupType"));
				}
				if(str.indexOf("groupPhone")!=-1)
				{
					groupInfo.setGroupPhone(rs.getString("groupPhone"));
				}
				if(str.indexOf("groupFax")!=-1)
				{
					groupInfo.setGroupFax(rs.getString("groupFax"));
				}
				if(str.indexOf("groupStatus")!=-1)
				{
					groupInfo.setGroupStatus(rs.getString("groupStatus"));
				}
				if(str.indexOf("groupCompanyId")!=-1)
				{
					groupInfo.setGroupCompanyid(rs.getString("groupCompanyId"));
				}
				if(str.indexOf("groupCompanyType")!=-1)
				{
					groupInfo.setGroupCompanytype(rs.getString("groupCompanyType"));
				}
				if(str.indexOf("groupDesc")!=-1)
				{
					groupInfo.setGroupDesc(rs.getString("groupDesc"));
				}
				if(str.indexOf("groupIntId")!=-1)
				{
					groupInfo.setGroupIntId(rs.getInt("groupIntId"));
				}
				if(str.indexOf("groupDnId")!=-1)
				{
					groupInfo.setGroupDnId(rs.getString("groupDnId"));
				}
				
				list.add(groupInfo);
			}
			rs.close();
			return list;
		}
		catch(Exception e)
		{
			logger.error("[497]GroupInfoInterface.getResultSetList() 通过结果集返回包含Bean的List"+e.getMessage());
			return null;
		}
	}
	
}
