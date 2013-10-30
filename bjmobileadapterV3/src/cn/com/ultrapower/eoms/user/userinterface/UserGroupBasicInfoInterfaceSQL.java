package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;


import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserGroupBasicInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserGroupBasicInfoPram;

public class UserGroupBasicInfoInterfaceSQL
{
	private String usertable	= "";
	private String grouptable   = "";
	static final Logger logger = (Logger) Logger.getLogger(UserGroupBasicInfoInterfaceSQL.class);
	public UserGroupBasicInfoInterfaceSQL()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			usertable		= getTableProperty.GetFormName("RemedyTpeople");
			grouptable		= getTableProperty.GetFormName("RemedyTgroup");
			
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	

	/**
	 * 根据用户登陆名，获得用户的部门名，公司名数据库查询方法。
	 * 日期 2007-4-25
	 * @author wangyanguang
	 * @param pram
	 * @return UserGroupBasicInfo
	 */
	public UserGroupBasicInfo getUserGroupBasicInfo(UserGroupBasicInfoPram pram)
	{
		UserGroupBasicInfo infovalue = new UserGroupBasicInfo();
		String userLoginName = Function.nullString(pram.getUserLoginName());
		StringBuffer sql = new StringBuffer();
		
		if(!userLoginName.equals(""))
		{
			sql.append("select distinct usertable.c630000008,usertable.c630000013,usertable.c630000015,grouptable1.c630000018 cpname,grouptable2.c630000018 dpname");
			sql.append(" from "+usertable+" usertable,"+grouptable+" grouptable1,"+grouptable+" grouptable2");
			sql.append(" where usertable.c630000001='"+userLoginName+"'");
			sql.append(" and usertable.c630000013=grouptable1.c1");
			sql.append(" and usertable.c630000013 = grouptable1.c1 and (usertable.c630000015 = grouptable2.c1 or (usertable.c630000015 is null and usertable.c630000013 = grouptable2.c1))");
			logger.info("查询语句："+sql.toString());
			System.out.println("查询语句："+sql.toString());
			IDataBase dataBase	= null;
			dataBase			= DataBaseFactory.createDataBaseClassFromProp();
			Statement stm		= dataBase.GetStatement();
			ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
			try
			{
				while(rs.next())
				{
					String dpid	= "";
					String mobile   = Function.nullString(rs.getString("c630000008"));
					String cpname 	= Function.nullString(rs.getString("cpname"));
					String dpname	= Function.nullString(rs.getString("dpname"));
					if(rs.getString("c630000015")== null)
					{
						dpid = "";
					}else
					{
						dpid = rs.getString("c630000015");
					}
					if(dpid.equals(""))
					{
						infovalue.setUserDPName("");
					}else
					{
						infovalue.setUserDPName(dpname);
					}
					infovalue.setUserCPName(cpname);
					infovalue.setMobile(mobile);
					
				}
				rs.close();
				stm.close();
				dataBase.closeConn();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				Function.closeDataBaseSource(rs,stm,dataBase);
			}
		}
		return infovalue;
	}

}
