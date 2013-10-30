package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.RoleInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserAndSkillInfo;
import cn.com.ultrapower.eoms.user.userinterface.cm.UserAndSkillInfoAssociate;

public class SkillUserInfoInterface 
{
	static final Logger logger = (Logger) Logger.getLogger(SkillUserInfoInterface.class);

	/**
	 * 日期 2007-3-1
	 * @author wangyanguang
	 * @param args void
	 */
	public List getSkillInfo(RoleInfoBean roleInfoBean)
	{
		List returnList 					= null;
		String usertablename 				= "";
		String roletablename				= "";
		String groupusertablename			= "";
		String roleskillmanagetable			= "";
		String usergrouprel					= "";
		String rolemanagetable				= "";
		IDataBase dataBase					= null;
		Statement stm 	= null;
		ResultSet rs	= null;
		
		GetFormTableName getTableProperty 	= new GetFormTableName();
		try
		{
			usertablename 			= getTableProperty.GetFormName("RemedyTpeople");
			roletablename			= getTableProperty.GetFormName("RemedyTrole");
			groupusertablename		= getTableProperty.GetFormName("RemedyTgroupuser");
			roleskillmanagetable 	= getTableProperty.GetFormName("RemedyTrolesskillmanage");
			usergrouprel			= getTableProperty.GetFormName("RemedyTrolesusergrouprel");
			rolemanagetable		 	= getTableProperty.GetFormName("RemedyTrolesmanage");
			
		}
		catch(Exception e)
		{
			logger.error(" 读取配制文件出现异常！");
		}
		StringBuffer sql 		= new StringBuffer();
		String skill_Module		= "";
		String skill_Action 	= "";
		String companyid		= "";
		//申川增加，排序条件
		String orderByStr		= "userloginName";
		//增加排序条件的nlssort字符串
		String nlssortStr="";
		if(roleInfoBean!=null)
		{
			skill_Module 				= Function.nullString(roleInfoBean.getSkill_Module());
			skill_Action 				= Function.nullString(roleInfoBean.getSkill_Action());
			companyid					= Function.nullString(roleInfoBean.getCompanyid());
			orderByStr					= Function.nullString(roleInfoBean.getOrderByStr());
		}
		//增加对排序条件的判断
		if(orderByStr==null||orderByStr.equals("")){
			nlssortStr=", nlssort(usertable.c630000003,'NLS_SORT=SCHINESE_PINYIN_M') tt";
		}else if(orderByStr.equals("userfullname")){
			nlssortStr=", nlssort(usertable.c630000003,'NLS_SORT=SCHINESE_PINYIN_M') tt";
		}else if(orderByStr.equals("userloginName")){
			nlssortStr=", nlssort(usertable.C630000001,'NLS_SORT=SCHINESE_PINYIN_M') tt";
		}
		sql.append( "select distinct usertable.C1 userid,usertable.C630000029 userintid,usertable.C630000001 userloginname");
		sql.append(" ,usertable.C630000003 userfullname  "+nlssortStr);
		sql.append(" from " + roletablename +" roletable,"+ usertablename+" usertable");
		sql.append(" where roletable.C610000007 = usertable.C1");
		sql.append(" and usertable.c630000012='0'");

		if(!skill_Module.equals(""))
		{
			sql.append( " and roletable.C610000008='"+skill_Module+"'");
		}
		if(!skill_Action.equals(""))
		{
			String tmpsql[] = skill_Action.split(",");
			String sql1 = " and (";
			sql1 = sql1 + "roletable.C610000010='"+(tmpsql[0])+"'";
			for(int i=1;i<tmpsql.length;i++)
			{
				sql1 = sql1 + " or roletable.C610000010='"+(tmpsql[i])+"'";
			}
			sql1 = sql1 + ")";
			System.out.println("action sql:"+sql1);
			sql.append(sql1);
		}
		if(!companyid.equals(""))
		{
			//sql.append(" and usertable.c630000013='"+companyid+"'");
		}
		sql.append(" union ");
		sql.append(" select distinct usertable.C1 userid,usertable.C630000029 userintid,");
		sql.append(" usertable.C630000001 userloginname,usertable.C630000003 userfullname "+nlssortStr );
		sql.append(" from "+ usertablename+" usertable,");
		sql.append(  roleskillmanagetable+"  roleskillmanage,");                
		sql.append(  usergrouprel+" rolegroupuserrel,");
		sql.append(  rolemanagetable + " rolemanagetable,");

		sql.append(  groupusertablename+" groupuser");               
		sql.append(" where groupuser.c620000028 = usertable.C1  ");
		sql.append(" and usertable.c630000012='0'");
		//新加的权限控制
		sql.append(" and ((rolegroupuserrel.c660000026 = groupuser.c620000028 and");		           
		sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027) or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
		sql.append(" and roleskillmanage.c660000006=rolegroupuserrel.c660000028 ");                     
		if(!skill_Module.equals(""))
		{
			sql.append( " and roleskillmanage.c660000007='"+skill_Module+"'");
		}
		if(!skill_Action.equals(""))
		{
			String tmpsql[] = skill_Action.split(",");
			String sql1 = " and (";
			sql1 = sql1 + "roleskillmanage.c660000009='"+(tmpsql[0])+"'";
			for(int i=1;i<tmpsql.length;i++)
			{
				sql1 = sql1 + " or roleskillmanage.c660000009='"+(tmpsql[i])+"'";
			}
			sql1 = sql1 + ")";
			System.out.println("action sql:"+sql1);
			sql.append(sql1);
		}            
		if(!companyid.equals(""))
		{
			//sql.append(" and usertable.c630000013='"+companyid+"'");
		}
		if("".equals(nlssortStr)){
			sql.append("order by "+orderByStr);
		}else{
			sql.append(" order by tt");
		}
		
		logger.info("sql:"+sql.toString());
		
		try
		{
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
			//获得数据库查询结果集
			stm		= dataBase.GetStatement();
			rs 		= dataBase.executeResultSet(stm,sql.toString());
			if(rs!=null)
			{
				returnList = UserAndSkillInfoAssociate.associateSkillAndUser(rs);
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			logger.error("Exception!");
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
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
		// TODO Auto-generated method stub
		RoleInfoBean roleInfoBean = new RoleInfoBean();
		roleInfoBean.setSkill_Module("493");
		roleInfoBean.setSkill_Action("7");
		roleInfoBean.setCompanyid("000000000600001");
		SkillUserInfoInterface userinterface = new SkillUserInfoInterface();
		List list = userinterface.getSkillInfo(roleInfoBean);
		if(list!=null)
		{
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				UserAndSkillInfo userinfo = (UserAndSkillInfo)it.next();
				System.out.println(userinfo.getUser_ID());
				System.out.println(userinfo.getUser_LoginName());
				
			}
		}
	}

}
