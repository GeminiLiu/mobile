package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.CommissionBean;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillConferpo;
import cn.com.ultrapower.eoms.user.comm.function.ConvertTimeToSecond;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class GetSkillConfer
{

	static final Logger logger = (Logger) Logger.getLogger(GetSkillConfer.class);

	String sourcemanager			= "";
	String systemmanage				= "";
	String sourceconfig				= "";
	String dutyorgnazition			= "";
	String orgnazitionarranger		= "";
	String usertablename			= "";
	String grouptablename			= "";
	String groupusertablename		= "";
	String sysskill					= "";
	String uid						= "";
	String skillaction				= "";
	String skillconfer				= "";
	String skillconferstatus		= "";
	String dropdowntable			= "";
	String skillformname			= "";
	/**
	 * 取得配制信息，读取配制文件
	 */
	public GetSkillConfer()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			sourcemanager		= getTableProperty.GetFormName("RemedyTsourceManager");
			systemmanage		= getTableProperty.GetFormName("systemmanage");
			sourceconfig		= getTableProperty.GetFormName("sourceconfig");
			dutyorgnazition		= getTableProperty.GetFormName("dutyorgnazition");
			orgnazitionarranger	= getTableProperty.GetFormName("orgnazitionarranger");
			usertablename		= getTableProperty.GetFormName("RemedyTpeople");
			grouptablename		= getTableProperty.GetFormName("RemedyTgroup");
			groupusertablename	= getTableProperty.GetFormName("RemedyTgroupuser");
			sysskill			= getTableProperty.GetFormName("RemedyTrole");
			skillaction			= getTableProperty.GetFormName("managergrandaction");
			skillconfer			= getTableProperty.GetFormName("RemedyTskillconfer");
			skillconferstatus	= getTableProperty.GetFormName("skillconferstatus");
			dropdowntable		= getTableProperty.GetFormName("RemedyTdropdown");
			skillformname		= getTableProperty.GetFormName("skillconfer");
		}
		catch(Exception e)
		{
			System.out.print("从配置表中读取数据表名时出现异常！");
		}
	}
	
	
	/**
	 * 根据用户ID与权限ID查询个人授权信息。
	 * 日期 2007-1-15
	 * @author wangyanguang
	 * @param userid		用户ID
	 * @param roleid		权限ID
	 */
	public SysSkillConferpo getSkillConferInfo(String userid,String roleid)
	{
		SysSkillConferpo skillconferpo = null;
		String sql = " from SysSkillConferpo skillconferpo where skillconferpo.c610000024='"+roleid +
		             "'  and skillconferpo.c610000025='"+userid+"' and skillconferpo.c610000026='0'";
		try
		{
//			Session session	= HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query 	= session.createQuery(sql);
//			List list 		= query.list();
			List list =HibernateDAO.queryObject(sql);
			if(list!=null)
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				for(Iterator it = list.iterator();it.hasNext();)
				{
					skillconferpo = (SysSkillConferpo)it.next();
				}
			}
			if(skillconferpo!=null)
			{
				return skillconferpo;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			try
			{
//				HibernateSessionFactory.closeSession();
			}catch(Exception ex)
			{
			}
			logger.error("770 GetSkillConfer 类中 getSkillConferInfo(String userid,String roleid) " +
							"根据ID查询技能管理信息时出现异常！"+e.getMessage());
			return null;
		}
		
	}
	
	public List getHistoryRecord(String userid,String roleid)
	{
		List returnList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select usertable1.c630000003,usertable2.c630000003 goalname,");
		sql.append("skillconfertable.c610000020,skillconfertable.c610000021,");
		sql.append("skillconfertable.c610000022,skillconfertable.c610000023,");
		sql.append("dropdowntable.c620000016,sourcetable.source_cnname");
		sql.append(" from "+skillconfer+" skillconfertable,"+usertablename+" usertable1,");
		sql.append(usertablename+" usertable2,"+dropdowntable+" dropdowntable,");
		sql.append(sysskill+" skilltable,"+sourceconfig+" sourcetable");
		sql.append(" where skillconfertable.c610000025=usertable1.c1 ");
		sql.append(" and skillconfertable.c610000027=usertable2.c1");
		sql.append(" and skillconfertable.c610000026=dropdowntable.c620000017");
		sql.append(" and dropdowntable.c620000015='"+skillconferstatus+"'");
		sql.append(" and dropdowntable.c620000019='"+skillformname+"'");
		sql.append(" and skillconfertable.c610000024=skilltable.c1");
		sql.append(" and skilltable.c610000008=sourcetable.source_id");
		sql.append(" and skillconfertable.c610000024='"+roleid +"'");
		sql.append(" and skillconfertable.c610000025='"+userid+"'");
		sql.append(" and usertable1.c630000012='0'");
		sql.append(" and usertable2.c630000012='0'");
		
		System.out.println(sql.toString());
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
					String begintime    = "";
					String closetime    = "";
					CommissionBean  commissioninfo = new CommissionBean();
					String username 	= rs.getString("C630000003");
		 	    	String goalname    	= rs.getString("goalname");
		 	    	String tmpcause		= rs.getString("C610000020");
		 	    	String tmpcancel	= rs.getString("C610000021");
		 	    	long   tmpbegin		= rs.getLong("C610000022");
		 	    	if(tmpbegin>0)
		 	    	{
		 	    		begintime  	= ConvertTimeToSecond.numberToLong(String.valueOf(tmpbegin));
		 	    	}
		 	 	  	long tmpclosetime	= rs.getLong("C610000023");
		 	 	  	if(tmpclosetime>0)
		 	 	  	{
		 	 	  		closetime	= ConvertTimeToSecond.numberToLong(String.valueOf(tmpclosetime));
		 	 	  	}
		 	 	  	String tmpstatus	= rs.getString("C620000016");
		 	 	  	String tmpsource	= rs.getString("source_cnname");
		 	 	  	commissioninfo.setBegintime(begintime);
		 	 	  	commissioninfo.setClosetime(closetime);
		 	 	  	commissioninfo.setGoalname(goalname);
		 	 	  	commissioninfo.setTmpcancel(tmpcancel);
		 	 	  	commissioninfo.setTmpcause(tmpcause);
		 	 	  	commissioninfo.setTmpsource(tmpsource);
		 	 	  	commissioninfo.setTmpstatus(tmpstatus);
		 	 	  	commissioninfo.setUsername(username);
		 	 	  	returnList.add(commissioninfo);
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			System.out.println("关闭数据库出现异常！");
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return returnList;
	}
	
	public static void main(String args[])
	{
		GetSkillConfer getconfer = new GetSkillConfer();
		getconfer.getHistoryRecord("000000000000002","000000000000061");


	}
}
