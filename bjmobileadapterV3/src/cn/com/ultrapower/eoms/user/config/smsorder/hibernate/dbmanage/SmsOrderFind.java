package cn.com.ultrapower.eoms.user.config.smsorder.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.function.ShowMenu;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
import cn.com.ultrapower.eoms.user.config.menu.hibernate.po.SysDropDownConfigpo;
import cn.com.ultrapower.eoms.user.config.smsorder.hibernate.po.SysSmsOrderpo;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

/**
 * <p>Description:使用hibernate从数据库中查找字段<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-20
 */
public class SmsOrderFind {
	
	static final Logger logger	= (Logger) Logger.getLogger(SmsOrderFind.class);
	
	GetFormTableName tablename	= new GetFormTableName();
	String smsorder				= tablename.GetFormName("smsorder");
	String smsorder_action		= tablename.GetFormName("smsorder_smsOrderAction");
	String all					= "all";
	
	/**
	 * <p>Description:通过c1查找工单短信订阅信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-20
	 * @param smsOrderId
	 * @return SysSmsOrderpo
	 */
	public SysSmsOrderpo findByPk(String smsOrderId)
	{
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			SysSmsOrderpo sysSmsOrderpo = (SysSmsOrderpo)session.load(SysSmsOrderpo.class,smsOrderId);
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			公用的hibernate方法
			SysSmsOrderpo sysSmsOrderpo = (SysSmsOrderpo)HibernateDAO.loadStringValue(SysSmsOrderpo.class,smsOrderId);
			return sysSmsOrderpo;
		}
		catch(Exception e)
		{
			logger.error("[471]SmsOrderFind.findByPk() 通过c1查找工单短信订阅信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:通过用户登陆名和工单schema查找工单动作信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-20
	 * @param smsOrderUserName
	 * @param smsOrderFormSchema
	 * @return List
	 */
	public List findActionList(String smsOrderUserName,String smsOrderFormSchema)
	{
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query		= session.createQuery("from SysSmsOrderpo a where a.c650000002 = '"+smsOrderUserName+"' and a.c650000003 = '"+smsOrderFormSchema+"'");
//			List list		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			
//			-----------shigang modify--------------
			String sql="from SysSmsOrderpo a where a.c650000002 = '"+smsOrderUserName+"' and a.c650000003 = '"+smsOrderFormSchema+"'";

			List list= HibernateDAO.queryObject(sql);

			return list;
		}
		catch(Exception e)
		{
			logger.error("[472]SmsOrderFind.findActionList() 通过用户登陆名和工单schema查找工单动作信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:通过工单动作ID查找工单动作名称<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-20
	 * @param formActionId
	 * @return String
	 */
	public String findFormActionName(String formActionId)
	{
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query		= session.createQuery("select b.c620000016 from SysSmsOrderpo a,SysDropDownConfigpo b where a.c650000001=b.c620000017 and b.c620000015="+smsorder_action+" and b.c620000019="+smsorder);
//			List list		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			-----------shigang modify--------------
			String sql="select b.c620000016 from SysSmsOrderpo a,SysDropDownConfigpo b where a.c650000001=b.c620000017 and b.c620000015="+smsorder_action+" and b.c620000019="+smsorder;

			List list= HibernateDAO.queryObject(sql);
			
			for(Iterator it = list.iterator();it.hasNext();)
			{
				String formActionName	= (String)it.next();
				return formActionName;
			}
			return null;
		}
		catch(Exception e)
		{
			logger.error("[473]SmsOrderFind.findFormActionName() 通过工单动作ID查找工单动作名称失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:显示所有工单Schema<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-20
	 * @param userName
	 * @return String
	 */
	public String showFormSchema(String userName)
	{
		//生成页面左侧工单FORM信息列表
		StringBuffer str	= new StringBuffer();
		//sql语句
		StringBuffer sql	= new StringBuffer();
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
			
			sql.append("select a.source_id,a.source_cnname from Sourceconfig a,Sourceconfig b,Sourceconfig c");
			sql.append(" where (a.source_parentid = b.source_id and b.source_name='workflow'");
			sql.append(" and a.source_module = c.source_id and c.source_name='workflow'");
			sql.append(" and a.source_type like '%1;%' and a.source_type like '%3;%')");
			sql.append(" union ");
			sql.append(" select d.source_id,d.source_cnname from sourceconfig d,sourceconfig e ");
			sql.append(" where e.source_type like '%11;%' and d.source_id=e.source_id" );
			System.out.println("工单短信订阅SQL："+sql.toString());
//			-----------shigang modify--------------
//			List list= HibernateDAO.queryObject(sql.toString());

			IDataBase dataBase	= null;
			dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
			Statement stm		= dataBase.GetStatement();
			ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
			try
			{
				while(rs.next())
				{
					String sourceId 			= rs.getString("source_id");
		 	 	  	String sourceCName       	= rs.getString("source_cnname");
					str.append("<tr><td><a href='FindSmsOrderActionDao?smsOrderUserName="+userName+"&smsOrderFormSchema="+sourceId+"' target='mainFrame' class='treelink'>"+sourceCName+"</a></td></tr>");
		 	 	  	
				}
				rs.close();
				stm.close();
				dataBase.closeConn();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				Function.closeDataBaseSource(rs,stm,dataBase);
			}
//			Query query		= session.createQuery(sql.toString());
//			List list		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();			
//			for(Iterator it = list.iterator();it.hasNext();)
//			{	
//				Sourceconfig sourceConfig	= (Sourceconfig)it.next();
//				String sourceId		= String.valueOf(sourceConfig.getSourceId());
//				String sourceCName	= sourceConfig.getSourceCnname();
//				str.append("<tr><td><a href='FindSmsOrderActionDao?smsOrderUserName="+userName+"&smsOrderFormSchema="+sourceId+"' target='mainFrame' class='treelink'>"+sourceCName+"</a></td></tr>");
//			}
			return str.toString();
		}
		catch(Exception e)
		{
			logger.error("[474]SmsOrderFind.showFormSchema() 显示所有工单Schema失败"+e.getMessage());
			return null;
		}	
	}
	
	/**
	 * <p>Description:显示小时分钟时间控件,需要脚本方法popup_date_helper()</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-16
	 * @param formName		form名
	 * @param inputName		input名
	 * @return String
	 */
	public String showTime(String formName,String inputName,String selectedValue)
	{
		StringBuffer str = new StringBuffer();
		str.append("<td align='left'><input type=\"text\" id=\""+inputName+"\" name=\""+inputName+"\" value='"+selectedValue+"' style=\"width:105px\" readonly>\n");
		str.append("<img src=\"../img/text_expand.gif\" style=\"cursor:hand\" onClick=\"popup_date_helper('"+formName+"','"+inputName+"')\">\n"); 	
		str.append("</td>\n");
		return str.toString();
	}
	
	
	/**
	 * <p>Description:根据已选中的工单动作和所有的工单动作确定为选中的工单动作</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-17
	 * @param selectedStr	已选中的工单动作
	 * @return String
	 */
	public String joinFormActionStr(String[] selectedStr)
	{
		String temp_smsorder_action		= "";
		//将所有工单动作得值以","隔开拼成字符串
		String final_smsorder_action	= "";
		try
		{
			//根据UltraProcess:SysDropDownConfig表查的所有工单动作的值
			ShowMenu showMenu	= new ShowMenu();
			List list			= showMenu.getList(smsorder_action,smsorder);
			//所有工单动作
			for(Iterator it = list.iterator();it.hasNext();)
			{
				//是否在已选中工单动作中
				boolean found = false;
				SysDropDownConfigpo sysDropDownConfigpo = (SysDropDownConfigpo)it.next();
				//工单动作值
				temp_smsorder_action					= sysDropDownConfigpo.getC620000017();
				//已选中的工单动作
				if(!String.valueOf(selectedStr).equals("null")&&!String.valueOf(selectedStr).equals(""))
				{
					for(int i = 0;i<selectedStr.length;i++)
					{
						//若该动作在已选中的工单动作中
						if(temp_smsorder_action.equals(selectedStr[i]))
						{
							found = true;
							break;
						}
					}
				}
				else
				{
					found	= false;
				}
				//若该动作在已选中工单动作中
				if(found)
				{
					continue;
				}
				//将该动作拼在最中工单动作字符串中
				else
				{
					if(!final_smsorder_action.equals(""))
					{
						final_smsorder_action = final_smsorder_action + ",";
					}
					final_smsorder_action = final_smsorder_action + temp_smsorder_action;
				}
			}
			return final_smsorder_action;
		}
		catch(Exception e)
		{
			logger.error("[529]SmsOrderFind.joinFormActionStr() 根据已选中的工单动作和所有的工单动作确定未选中的工单动作失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据数据库中未选中的工单动作和所有的工单动作确定选中的工单动作</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-17
	 * @param unSelectedStr
	 * @return String
	 */
	public String joinFormActionStr(String unSelectedStr)
	{
		String temp_smsorder_action		= "";
		//将所有工单动作得值以","隔开拼成字符串
		String final_smsorder_action	= "";
		//从数据库中得到的未选中的工单动作
		String[] unSelectedValue		= unSelectedStr.split(",");
		try
		{
			//根据UltraProcess:SysDropDownConfig表查的所有工单动作的值
			ShowMenu showMenu	= new ShowMenu();
			List list			= showMenu.getList(smsorder_action,smsorder);
			//所有工单动作
			for(Iterator it = list.iterator();it.hasNext();)
			{
				//是否在未选中工单动作中
				boolean found = false;
				SysDropDownConfigpo sysDropDownConfigpo = (SysDropDownConfigpo)it.next();
				//工单动作值
				temp_smsorder_action					= sysDropDownConfigpo.getC620000017();
				//未已选中的工单动作
				for(int i = 0;i<unSelectedValue.length;i++)
				{
					//若该动作在未已选中的工单动作中
					if(temp_smsorder_action.equals(unSelectedValue[i]))
					{
						found = true;
						break;
					}
				}
				//若该动作在未已选中工单动作中
				if(found)
				{
					continue;
				}
				//将该动作拼在最中工单动作字符串中
				else
				{
					if(!final_smsorder_action.equals(""))
					{
						final_smsorder_action = final_smsorder_action + ",";
					}
					final_smsorder_action = final_smsorder_action + temp_smsorder_action;
				}
			}
			return final_smsorder_action;
		}
		catch(Exception e)
		{
			logger.error("[530]SmsOrderFind.joinFormActionStr() 根据数据库中未选中的工单动作和所有的工单动作确定选中的工单动作失败"+e.getMessage());
			return null;
		}
	}

	/**
	 * <p>Description:获得特殊工单动作的ID</p>
	 * @author wangwenzhuo
	 * @CreatTime 2007-2-9
	 * @param smsOrderUserName
	 * @param smsOrderFormSchema
	 * @return String
	 */
	public String getSpecialActionId(String smsOrderUserName,String smsOrderFormSchema)
	{
		try
		{
			String sql = "select a.c1 from SysSmsOrderpo a where a.c650000002 = '"+smsOrderUserName+"' and a.c650000003 = '"+smsOrderFormSchema+"' and a.c650000001 = '"+all+"'";
			List list	= HibernateDAO.queryObject(sql);
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				String c1 = (String)it.next();
				return c1;
			}

			logger.info("[]SmsOrderFind.getSpecialActionId() 数据库中没有该特殊工单动作");
			return null;
		}
		catch(Exception e)
		{
			logger.error("[]SmsOrderFind.getSpecialActionId() 获得特殊工单动作的ID失败"+e.getMessage());
			return null;
		}
	}
}
