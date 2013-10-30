package cn.com.ultrapower.eoms.user.userinterface.cm;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.SkillGrandParm;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserSkillGrandInfo;;

public class GetSkillGrandInfoInterface {
	

	public List getSkillGrand(SkillGrandParm skillgrandparm)
	{
		List returnList 					= new ArrayList();
		IDataBase dataBase					= null;
		
		Statement stm						= null;
		ResultSet rs 						= null;
		
		StringBuffer sql 					= new StringBuffer();
		String roletablename				= "";
		String RemedyTrolesusergrouprel		= "";
		String RemedyTrolesskillmanage		= "";
		String RemedyTgroupuser				= "";
		String RemedyTskillconfer			= "";
		String usertable					= "";
		String commissionid					= "";
		String userLoginName				= "";
		commissionid						= skillgrandparm.getCommissionuserid();
		userLoginName						= skillgrandparm.getUserLoginName();
		
		GetFormTableName getTableProperty 	= new GetFormTableName();
		try
		{
			roletablename						= getTableProperty.GetFormName("RemedyTrole");
			RemedyTskillconfer					= getTableProperty.GetFormName("RemedyTskillconfer");
			RemedyTrolesusergrouprel			= getTableProperty.GetFormName("RemedyTrolesusergrouprel");
			RemedyTrolesskillmanage				= getTableProperty.GetFormName("RemedyTrolesskillmanage");
			RemedyTgroupuser					= getTableProperty.GetFormName("RemedyTgroupuser");
			usertable		 					= getTableProperty.GetFormName("RemedyTpeople");
		}
		catch(Exception e)
		{
			System.out.println("获得Remedy真实表失败");
		}
		try
		{
			if(!String.valueOf(commissionid).equals("")&&!String.valueOf(commissionid).equals("null"))
			{
				sql.append("select distinct * from (");
				sql.append("select distinct RemedyTrole.C610000010 grandvalue");
				sql.append(" from "+roletablename+" RemedyTrole,");
				sql.append(usertable+" usertable ");
				sql.append(" where RemedyTrole.C610000008='"+Function.nullString(skillgrandparm.getSourceid())+"'");
				sql.append(" and RemedyTrole.C610000007=usertable.c1'");
				sql.append(" and usertable.c630000001='"+userLoginName+"'");
				sql.append(" and RemedyTrole.C610000018='0'");
				sql.append(" union ");
				sql.append("select distinct RemedyTrolesskillmanage.C660000009 grandvalue");
				sql.append(" from "+RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage");
				sql.append(usertable+" ,usertable ");
				sql.append(" where RemedyTrolesskillmanage.C660000007='"+Function.nullString(skillgrandparm.getSourceid())+"'");
				sql.append(" and RemedyTrolesusergrouprel.C660000026=usertable.c1");
				sql.append(" and usertable.c630000001='"+userLoginName+"'");
				sql.append(" and RemedyTrolesskillmanage.C660000006 = RemedyTrolesusergrouprel.c660000028 ");
				sql.append(" union ");
				sql.append("select distinct RemedyTrolesskillmanage.C660000009 grandvalue");
				sql.append(" from "+RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage");
				sql.append(","+RemedyTgroupuser+" RemedyTgroupuser,");
				sql.append(usertable+" ,usertable ");
				sql.append(" where RemedyTrolesskillmanage.C660000007='"+Function.nullString(skillgrandparm.getSourceid())+"'");
				sql.append(" and RemedyTrolesusergrouprel.C660000027= RemedyTgroupuser.C620000027 and RemedyTrolesusergrouprel.C660000026 is null");
				sql.append(" and usertable.c630000001='"+userLoginName+"'");
				sql.append(" and RemedyTgroupuser.C620000028=usertable.c1");
				sql.append(" and RemedyTrolesskillmanage.C660000006 = RemedyTrolesusergrouprel.c660000028 ");
				sql.append(" union ");
				sql.append(" select distinct RemedyTskillconfer.C610000034 grandvalue");
				sql.append(" from "+RemedyTskillconfer+" RemedyTskillconfer,");
				sql.append(usertable+" usertable ");
				sql.append(" where RemedyTskillconfer.C610000025=usertable.c1");
				sql.append(" and usertable.c630000001='"+userLoginName+"'");
				sql.append(" and RemedyTskillconfer.C610000027='"+Function.nullString(skillgrandparm.getCommissionuserid())+"'");
				sql.append(" and RemedyTskillconfer.C610000031='"+Function.nullString(skillgrandparm.getSourceid())+"'");
				sql.append(")");
			}
			else
			{
				sql.append("select distinct * from (");
				sql.append("select distinct RemedyTrole.C610000010 grandvalue");
				sql.append(" from "+roletablename+" RemedyTrole,");
				sql.append(usertable+" usertable");
				sql.append(" where RemedyTrole.C610000008='"+Function.nullString(skillgrandparm.getSourceid())+"'");
				sql.append(" and RemedyTrole.C610000007=usertable.c1");
				sql.append(" and usertable.c630000001='"+userLoginName+"'");
				sql.append(" and RemedyTrole.C610000018='0'");
				sql.append(" union ");
				sql.append("select distinct RemedyTrolesskillmanage.C660000009 grandvalue");
				sql.append(" from "+RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage,");
				sql.append(usertable+" usertable ");
				sql.append(" where RemedyTrolesskillmanage.C660000007='"+Function.nullString(skillgrandparm.getSourceid())+"'");
				sql.append(" and RemedyTrolesusergrouprel.C660000026=usertable.c1");
				sql.append(" and usertable.c630000001='"+userLoginName+"'");
				sql.append(" and RemedyTrolesskillmanage.C660000006 = RemedyTrolesusergrouprel.c660000028 ");
				sql.append(" union ");
				sql.append("select distinct RemedyTrolesskillmanage.C660000009 grandvalue");
				sql.append(" from "+RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage");
				sql.append(","+RemedyTgroupuser+" RemedyTgroupuser,");
				sql.append(usertable+" usertable ");
				sql.append(" where RemedyTrolesskillmanage.C660000007='"+Function.nullString(skillgrandparm.getSourceid())+"'");
				sql.append(" and RemedyTrolesusergrouprel.C660000027= RemedyTgroupuser.C620000027 and RemedyTrolesusergrouprel.C660000026 is null");
				sql.append(" and RemedyTgroupuser.C620000028=usertable.c1");
				sql.append(" and usertable.c630000001='"+userLoginName+"'");
				sql.append(" and RemedyTrolesskillmanage.C660000006 = RemedyTrolesusergrouprel.c660000028 ");
				sql.append(")");
			}
			System.out.println(sql.toString()+"获得权限值sql");
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
			//获得数据库查询结果集
			stm		= dataBase.GetStatement();
			rs 		= dataBase.executeResultSet(stm,sql.toString());
			String userid = "";
			String userfullname = "";
			while(rs.next())
			{
				UserSkillGrandInfo userSkillGrandInfo	= new UserSkillGrandInfo();
				userSkillGrandInfo.setGrandvalue(rs.getString("grandvalue"));
				if(userSkillGrandInfo!=null)
				{
					returnList.add(userSkillGrandInfo);
				}
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
			return returnList;
		}
		catch(Exception e)
		{
			System.out.println("Exception!");
		}
		finally
		{
			if(dataBase!=null)
			{
				Function.closeDataBaseSource(rs,stm,dataBase);
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
}