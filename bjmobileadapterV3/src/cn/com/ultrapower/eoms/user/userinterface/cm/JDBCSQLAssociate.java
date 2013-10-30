package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.userinterface.SourceConfigInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SkillSourceInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceAndPropertyInfo;

public class JDBCSQLAssociate
{
	//用户信息.根据RS结果，将PeopleInfo Bean信息返回。
	public PeopleInfo getUserInfoAsso(ResultSet rs)
	{
		String user_ID 					= "";
		String user_IntID 				= "";
		String user_LoginName 			= "";
		String user_PassWord 			= "";
		String user_FullName 			= "";
		String user_CreateUser 			= "";
		String user_Position 			= "";
		String user_IsManager 			= "";
		String user_Type 				= "";
		String user_Mobie 				= "";
		String user_Phone 				= "";
		String user_Fax 				= "";
		String user_Mail 				= "";
		String user_Status 				= "";
		String user_CPID 				= "";
		String user_CPType 				= "";
		String user_DPID 				= "";
		String user_LicenseType 		= "";
		String user_BelongGroupID 		= "";
		String user_OrderBy 			= "";
		try
		{
			if(rs!=null)
			{
				while(rs.next())
				{
					PeopleInfo userskillinfo 	= new PeopleInfo();
					user_ID 					= Function.nullString(rs.getString("userid"));
					user_IntID 					= Function.nullString(rs.getString("userintid")); 
					user_LoginName 				= Function.nullString(rs.getString("userloginname")); 
					user_PassWord 				= Function.nullString(rs.getString("userpassword"));
					user_FullName 				= Function.nullString(rs.getString("userfullname"));
					user_CreateUser 			= Function.nullString(rs.getString("usercreateuser")); 
					user_Position 				= Function.nullString(rs.getString("userposition"));
					user_IsManager 				= Function.nullString(rs.getString("userismanager"));
					user_Type 					= Function.nullString(rs.getString("usertype"));
					user_Mobie 					= Function.nullString(rs.getString("usermobie"));
					user_Phone 					= Function.nullString(rs.getString("userphone")); 
					user_Fax 					= Function.nullString(rs.getString("userfax"));
					user_Mail 					= Function.nullString(rs.getString("usermail"));
					user_Status 				= Function.nullString(rs.getString("userstatus"));
					user_CPID 					= Function.nullString(rs.getString("usercpid"));
					user_CPType 				= Function.nullString(rs.getString("usercptype"));
					user_DPID 					= Function.nullString(rs.getString("userdpid"));
					user_LicenseType 			= Function.nullString(rs.getString("userlicensetype"));
					user_BelongGroupID 			= Function.nullString(rs.getString("userbelonggroupid"));
					user_OrderBy 				= Function.nullString(rs.getString("userorderby"));
					
					userskillinfo.setUserBelongGroupId(user_BelongGroupID);
					userskillinfo.setUserCpid(user_CPID);
					userskillinfo.setUserCptype(user_CPType);
					userskillinfo.setUserCreateuser(user_CreateUser);
					userskillinfo.setUserDpid(user_DPID);
					userskillinfo.setUserFax(user_Fax);
					userskillinfo.setUserFullname(user_FullName);
					userskillinfo.setUserId(user_ID);
					userskillinfo.setUserIntId(Integer.parseInt(user_IntID));
					userskillinfo.setUserIsmanager(user_IsManager);
					userskillinfo.setUserLicensetype(user_LicenseType);
					userskillinfo.setUserLoginname(user_LoginName);
					userskillinfo.setUserMail(user_Mail);
					userskillinfo.setUserMobie(user_Mobie);
					userskillinfo.setUserOrderby(user_OrderBy);
					userskillinfo.setUserPassword(user_PassWord);
					userskillinfo.setUserPhone(user_Phone);
					userskillinfo.setUserPosition(user_Position);
					userskillinfo.setUserStatus(user_Status);
					userskillinfo.setUserType(user_Type);
					if(userskillinfo!=null)
					{
						return userskillinfo;
					}
				}
			}
			else
			{
				return null;
			}
			return null;
		}
		catch(Exception e)
		{
			System.out.println("EXception!");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	//技能+资源属性+资源属性值。根据RS结果，将SkillSourceInfo Bean信息集合返回。
	public List getSkillAndPropertyList(ResultSet rs)
	{
		List returnList = new ArrayList();
		//skill
		String skill_ID 					= "";
		String skill_Type 					= "";
		String skill_GroupID				= "";
		String skill_UserID 				= "";
		String skill_Module 				= "";
		String skill_CategoryQueryID 		= "";
		String skill_Action 				= "";
		String skill_CommissionGID 			= "";
		String skill_CommissionUID 			= "";
		String skill_CommissionCloseTime 	= "";
		String skill_Status 				= "";
		String skill_WorkFlowType 			= "";
		//T1
		String source_id					= "";
		String source_parentid				= "";
		String source_cnname				= "";
		String source_name					= "";
		String source_module				= "";
		String source_type					= "";
		String source_orderby				= "";
		String source_desc					= "";
		//T2
		String sourceatt_id					= "";
		String sourceatt_sourceid			= "";
		String sourceatt_cnname				= "";
		String sourceatt_enname				= "";
		String sourceatt_type				= "";
		String sourceatt_orderby			= "";
		String sourceatt_desc				= "";
		//T3
		String sourceattvalue_id			= "";
		String sourceattvalue_attid			= "";
		String sourceattvalue_belongrow		= "";
		String sourceattvalue_value			= "";
		String sourceattvalue_type			= "";
		
		if(rs!=null)
		{
			try
			{
				while(rs.next())
				{
					
					//skill
					skill_ID 					= Function.nullString(rs.getString("skillid"));
					skill_Type 					= Function.nullString(rs.getString("skilltype"));
					skill_GroupID				= Function.nullString(rs.getString("skillgroupid"));
					skill_UserID 				= Function.nullString(rs.getString("skilluserid"));
					skill_Module 				= Function.nullString(rs.getString("skillmodule"));
					skill_CategoryQueryID 		= Function.nullString(rs.getString("skillcategoryqueryid"));
					skill_Action 				= Function.nullString(rs.getString("skillaction"));
					skill_CommissionGID 		= Function.nullString(rs.getString("skillcommissiongid"));
					skill_CommissionUID 		= Function.nullString(rs.getString("skillcommissionuid"));
					skill_CommissionCloseTime 	= Function.nullString(rs.getString("skillcommissionclosetime"));
					skill_Status 				= Function.nullString(rs.getString("skillstatus"));
					skill_WorkFlowType 			= Function.nullString(rs.getString("skillworkflowtype"));
					//T1
					source_id					= Function.nullString(rs.getString("sourceid"));
					source_parentid				= Function.nullString(rs.getString("sourceparentid"));
					source_cnname				= Function.nullString(rs.getString("sourcecnname"));
					source_name					= Function.nullString(rs.getString("sourcename"));
					source_module				= Function.nullString(rs.getString("sourcemodule"));
					source_type					= Function.nullString(rs.getString("sourcetype"));
					source_orderby				= Function.nullString(rs.getString("sourceorderby"));
					source_desc					= Function.nullString(rs.getString("sourcedesc"));
					//T2
					sourceatt_id				= Function.nullString(rs.getString("sourceattid"));
					sourceatt_sourceid			= Function.nullString(rs.getString("sourceattsourceid"));
					sourceatt_cnname			= Function.nullString(rs.getString("sourceattcnname"));
					sourceatt_enname			= Function.nullString(rs.getString("sourceattenname"));
					sourceatt_type				= Function.nullString(rs.getString("sourceatttype"));
					sourceatt_orderby			= Function.nullString(rs.getString("sourceattordeby"));
					sourceatt_desc				= Function.nullString(rs.getString("sourceattdesc"));
					//T3
					sourceattvalue_id			= Function.nullString(rs.getString("sourceattvalueid"));
					sourceattvalue_attid		= Function.nullString(rs.getString("sourceattvalueattid"));
					sourceattvalue_belongrow	= Function.nullString(rs.getString("sourceattvaluebelongrow"));
					sourceattvalue_value		= Function.nullString(rs.getString("sourceattvaluevalue"));
					sourceattvalue_type			= Function.nullString(rs.getString("sourceattvaluetype"));
					
					SkillSourceInfo skillsourceinfo = new SkillSourceInfo();
					skillsourceinfo.setSkill_Action(skill_Action);
					skillsourceinfo.setSkill_CategoryQueryID(skill_CategoryQueryID);
					skillsourceinfo.setSkill_CommissionCloseTime(skill_CommissionCloseTime);
					skillsourceinfo.setSkill_CommissionGID(skill_CommissionGID);
					skillsourceinfo.setSkill_CommissionUID(skill_CommissionUID);
					skillsourceinfo.setSkill_GroupID(skill_GroupID);
					skillsourceinfo.setSkill_ID(skill_ID);
					skillsourceinfo.setSkill_Module(skill_Module);
					skillsourceinfo.setSkill_Status(skill_Status);
					skillsourceinfo.setSkill_Type(skill_Type);
					skillsourceinfo.setSkill_UserID(skill_UserID);
					skillsourceinfo.setSkill_WorkFlowType(skill_WorkFlowType);
					skillsourceinfo.setSource_cnname(source_cnname);
					skillsourceinfo.setSource_desc(source_desc);
					skillsourceinfo.setSource_id(source_id);
					skillsourceinfo.setSource_module(source_module);
					skillsourceinfo.setSource_name(source_name);
					skillsourceinfo.setSource_orderby(source_orderby);
					skillsourceinfo.setSource_parentid(source_parentid);
					skillsourceinfo.setSource_type(source_type);
					skillsourceinfo.setSourceatt_cnname(sourceatt_cnname);
					skillsourceinfo.setSourceatt_desc(sourceatt_desc);
					skillsourceinfo.setSourceatt_enname(sourceatt_enname);
					skillsourceinfo.setSourceatt_id(sourceatt_id);
					skillsourceinfo.setSourceatt_orderby(sourceatt_orderby);
					skillsourceinfo.setSourceatt_sourceid(sourceatt_sourceid);
					skillsourceinfo.setSourceatt_type(sourceatt_type);
					skillsourceinfo.setSourceattvalue_attid(sourceattvalue_attid);
					skillsourceinfo.setSourceattvalue_belongrow(sourceattvalue_belongrow);
					skillsourceinfo.setSourceattvalue_id(sourceattvalue_id);
					skillsourceinfo.setSourceattvalue_type(sourceattvalue_type);
					skillsourceinfo.setSourceattvalue_value(sourceattvalue_value);
					
					returnList.add(skillsourceinfo);
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
			catch(Exception e)
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	
	}
	
	//资源+资源属性+资源属性值。根据RS结果，返回SourceAndProperty Bean 信息集合.
	public List getSourceAndPropertyList(ResultSet rs)
	{
		List returnList = new ArrayList();

		//T1
		String source_id					= "";
		String source_parentid				= "";
		String source_cnname				= "";
		String source_name					= "";
		String source_module				= "";
		String source_type					= "";
		String source_orderby				= "";
		String source_desc					= "";
		//T2
		String sourceatt_id					= "";
		String sourceatt_sourceid			= "";
		String sourceatt_cnname				= "";
		String sourceatt_enname				= "";
		String sourceatt_type				= "";
		String sourceatt_orderby			= "";
		String sourceatt_desc				= "";
		//T3
		String sourceattvalue_id			= "";
		String sourceattvalue_attid			= "";
		String sourceattvalue_belongrow		= "";
		String sourceattvalue_value			= "";
		String sourceattvalue_type			= "";
		
		if(rs!=null)
		{
			try
			{
				while(rs.next())
				{
					

					//T1
					source_id					= Function.nullString(rs.getString("sourceid"));
					source_parentid				= Function.nullString(rs.getString("sourceparentid"));
					source_cnname				= Function.nullString(rs.getString("sourcecnname"));
					source_name					= Function.nullString(rs.getString("sourcename"));
					source_module				= Function.nullString(rs.getString("sourcemodule"));
					source_type					= Function.nullString(rs.getString("sourcetype"));
					source_orderby				= Function.nullString(rs.getString("sourceorderby"));
					source_desc					= Function.nullString(rs.getString("sourcedesc"));
					//T2
					sourceatt_id				= Function.nullString(rs.getString("sourceattid"));
					sourceatt_sourceid			= Function.nullString(rs.getString("sourceattsourceid"));
					sourceatt_cnname			= Function.nullString(rs.getString("sourceattcnname"));
					sourceatt_enname			= Function.nullString(rs.getString("sourceattenname"));
					sourceatt_type				= Function.nullString(rs.getString("sourceatttype"));
					sourceatt_orderby			= Function.nullString(rs.getString("sourceattordeby"));
					sourceatt_desc				= Function.nullString(rs.getString("sourceattdesc"));
					//T3
					sourceattvalue_id			= Function.nullString(rs.getString("sourceattvalueid"));
					sourceattvalue_attid		= Function.nullString(rs.getString("sourceattvalueattid"));
					sourceattvalue_belongrow	= Function.nullString(rs.getString("sourceattvaluebelongrow"));
					sourceattvalue_value		= Function.nullString(rs.getString("sourceattvaluevalue"));
					sourceattvalue_type			= Function.nullString(rs.getString("sourceattvaluetype"));
					
					SourceAndPropertyInfo sourcepropertyinfo = new SourceAndPropertyInfo();

					sourcepropertyinfo.setSource_cnname(source_cnname);
					sourcepropertyinfo.setSource_desc(source_desc);
					sourcepropertyinfo.setSource_id(source_id);
					sourcepropertyinfo.setSource_module(source_module);
					sourcepropertyinfo.setSource_name(source_name);
					sourcepropertyinfo.setSource_orderby(source_orderby);
					sourcepropertyinfo.setSource_parentid(source_parentid);
					sourcepropertyinfo.setSource_type(source_type);
					sourcepropertyinfo.setSourceatt_cnname(sourceatt_cnname);
					sourcepropertyinfo.setSourceatt_desc(sourceatt_desc);
					sourcepropertyinfo.setSourceatt_enname(sourceatt_enname);
					sourcepropertyinfo.setSourceatt_id(sourceatt_id);
					sourcepropertyinfo.setSourceatt_orderby(sourceatt_orderby);
					sourcepropertyinfo.setSourceatt_sourceid(sourceatt_sourceid);
					sourcepropertyinfo.setSourceatt_type(sourceatt_type);
					sourcepropertyinfo.setSourceattvalue_attid(sourceattvalue_attid);
					sourcepropertyinfo.setSourceattvalue_belongrow(sourceattvalue_belongrow);
					sourcepropertyinfo.setSourceattvalue_id(sourceattvalue_id);
					sourcepropertyinfo.setSourceattvalue_type(sourceattvalue_type);
					sourcepropertyinfo.setSourceattvalue_value(sourceattvalue_value);
					
					returnList.add(sourcepropertyinfo);
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
			catch(Exception e)
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	
	}

	//资源信息Bean
	public List getSourceInfo(ResultSet rs)
	{
		List returnList 					= new ArrayList();
		String source_id					= "";
		String source_parentid				= "";
		String source_cnname				= "";
		String source_name					= "";
		String source_module				= "";
		String source_type					= "";
		String source_orderby				= "";
		String source_desc					= "";
		if(rs!=null)
		{
			try
			{
				while(rs.next())
				{
					SourceConfigInfoBean sourceinfo = new SourceConfigInfoBean();
					source_id					= Function.nullString(rs.getString("sourceid"));
					source_parentid				= Function.nullString(rs.getString("sourceparentid"));
					source_cnname				= Function.nullString(rs.getString("sourcecnname"));
					source_name					= Function.nullString(rs.getString("sourcename"));
					source_module				= Function.nullString(rs.getString("sourcemodule"));
					source_type					= Function.nullString(rs.getString("sourcetype"));
					source_orderby				= Function.nullString(rs.getString("sourceorderby"));
					source_desc					= Function.nullString(rs.getString("sourcedesc"));
					
					sourceinfo.setSource_cnname(source_cnname);
					sourceinfo.setSource_desc(source_desc);
					sourceinfo.setSource_fieldtype("");
					sourceinfo.setSource_id(source_id);
					sourceinfo.setSource_module(source_module);
					sourceinfo.setSource_name(source_name);
					sourceinfo.setSource_orderby(new Long(source_orderby));
					sourceinfo.setSource_parentid(new Long(source_parentid));
					sourceinfo.setSource_type(source_type);
					
					if(sourceinfo!=null)
					{
						returnList.add(sourceinfo);
					}
				}
				if(returnList != null)
				{
					return returnList;
				}
				else
				{
					return null;
				}
			}
			catch(Exception e)
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
}
