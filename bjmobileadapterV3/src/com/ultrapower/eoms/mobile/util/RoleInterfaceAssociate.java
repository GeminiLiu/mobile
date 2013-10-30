package com.ultrapower.eoms.mobile.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRole;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.dbmanage.GetGroupuserInfoList;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceAttQueryBean;
//import cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage.SourceConfigInfo;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.eoms.user.userinterface.SourceConfigInfoBean;

public class RoleInterfaceAssociate {
	static final Logger logger = (Logger) Logger.getLogger(RoleInterfaceAssociate.class);
	
	//根据用户ID，取得用户归属组ID的LIST。
	public List getGroupIdList(String userid)
	{
		GetGroupuserInfoList groupinfo = new GetGroupuserInfoList();
		List list = groupinfo.getGroupList(userid);
		if(list.size()>0)
		{
			return list;
		}else
		{
			return null;
		}
	}
	//根据组ID，取得组成员ID的List.
	public List getUserIdList(String groupid)
	{	
		try
		{
			GetGroupuserInfoList grouplist = new GetGroupuserInfoList();
			List list = grouplist.getUserList(groupid);
			if(list.size()>0)
			{
				return list;
			}else
			{
				return null;
			}
		}catch(Exception e)
		{
			e.getMessage();
			return null;
		}
	}
	
	//根据用户英文名取得用户ID；
	public  SysPeoplepo getUserID(String username)
	{
		GetUserInfoList userFind = new GetUserInfoList();
		SysPeoplepo peoplepo 	= null;
		try
		{
		 peoplepo = userFind.getUserInfoName(username);
		}catch(Exception e)
		{
			logger.info("用户名不存在，或者用户名输入有误！");
			peoplepo = null;
		}
		if(peoplepo!=null)
		{
			return peoplepo;
		}else
		{
			return null;
		}
	}
	
	//根据资源英文名，取得资源Bean：
	public Sourceconfig  getSourceBean(String sourceName)
	{
		Long 			sourceid 		= null;
		List 			sourceList 		= null;
		Sourceconfig	sourceConfig	= null;
		SourceConfigInfo t1Info = new SourceConfigInfo();
		try 
		{
			try
			{
				sourceList 		= t1Info.GetsourceCFGlist(sourceName);
			}
			catch(Exception e)
			{
				logger.error("查询资源名出错！");
			}
			if(sourceList!=null)
			{
				Iterator t1it 	= sourceList.iterator();
				
				while (t1it.hasNext()) 
				{
					sourceConfig = (Sourceconfig) t1it.next();
					
				}
			}
		} catch (Exception e) 
		{
			logger.info("Exception :get T1.sourceID base on T1.en_name");
		}
		if(sourceConfig !=null)
		{
			return sourceConfig;
		}else
		{
			return null;
		}
	
	}
	
	/**
	 * 根据用户ID从用户信息表中取得用户信息Bean.
	 * 日期 2006-11-17
	 * 
	 * @author wangyanguang/王彦广 
	 * @param userLoginName
	 * @return SysPeoplepo
	 */
    public SysPeoplepo getUserInfoName(String userLoginName)
    {
    	try
    	{
//			Session session 	= HibernateSessionFactory.currentSession();
//			Transaction tx 		= session.beginTransaction();
//			Query query 		= session.createQuery("from SysPeoplepo a where a.c630000001 ='"+userLoginName+"'");
//			List list 			= query.list();
    		
//			-----------shigang modify--------------
    		String sql="from SysPeoplepo a where a.c630000001 ='"+userLoginName+"'";
    		List list= HibernateDAO.queryObject(sql);

    		
			for(Iterator it=list.iterator(); it.hasNext();){
				SysPeoplepo people=(SysPeoplepo)it.next();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return people;
			}
			return null;
		}
		catch(Exception e)
		{
			logger.error("[417]GetUserInfoList.getUserInfoName() 通过userLoginName名查找用户信息失败"+e.getMessage());
			return null;
		}
    }  

    /**
     * 根据用户ID查询组成员表，取得此用户的归属组。
     * 日期 2006-11-17
     * 
     * @author wangyanguang/王彦广 
     * @param muserId
     * @return List
     */
	public List getGroupList(String muserId)
	{
		try 
		{
//		    Session session 	= HibernateSessionFactory.currentSession();
//		    session.clear();
//			Transaction tx 		= session.beginTransaction();
//			Query query 		= session.createQuery("from SysGroupUserpo where c620000028 ="+muserId);
//			List list 			= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
    		
//			-----------shigang modify--------------
    		String sql="from SysGroupUserpo where c620000028 ="+muserId;
    		List list= HibernateDAO.queryObject(sql);
			return list;
		} 
		catch (Exception e) 
		{
			logger.error("[429]GroupUserFind.find() 获得组列表List失败"+e.getMessage());
			return null;
		}
	}

	/**
	 * 根据组ID查询组成员表，取得属于此组的用户List.
	 * 日期 2006-11-17
	 * 
	 * @author wangyanguang/王彦广 
	 * @param mgroupId
	 * @return List
	 */
	public List getUserList(String mgroupId)
	{
		try 
		{
//		    Session session 	= HibernateSessionFactory.currentSession();
//		    session.clear();
//			Transaction tx 		= session.beginTransaction();
//			Query query 		= session.createQuery("from SysGroupUserpo where c620000027 ="+mgroupId);
//			List list 			= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
			
//			-----------shigang modify--------------
    		String sql="from SysGroupUserpo where c620000027 ="+mgroupId;
    		List list= HibernateDAO.queryObject(sql);
			return list;
		} 
		catch (Exception e) 
		{
			logger.error("[428]GetGroupuserInfoList.getUserList() 获得用户列表返回List失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 根据资源ID，从资源信息表中查询出资源Bean。
	 * 日期 2006-11-21
	 * 
	 * @author wangyanguang/王彦广 
	 * @param source_id
	 * @return
	 * @throws HibernateException Sourceconfig
	 *
	 */
	public  Sourceconfig getSourceInfo(java.lang.Long source_id) 
	{
		try{
			 String 		sql	 	 = "from Sourceconfig t1 where t1.sourceId="+source_id;
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
			 if(l1.size()>0)
			 {
				 Iterator it = l1.iterator();
				 while(it.hasNext())
				 {
					 Sourceconfig sourceconfig = (Sourceconfig)it.next();
					 return sourceconfig;
				 }
			 }
		     return null;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	/**
	 * 根据用户ID，查询用户信息表，查询出用户信息Bean.
	 * 日期 2006-11-21
	 * 
	 * @author wangyanguang/王彦广 
	 * @param id
	 * @return SysPeoplepo
	 *
	 */
	public static SysPeoplepo findModify(String id)
	{
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			SysPeoplepo sysPeoplepo = (SysPeoplepo)session.load(SysPeoplepo.class,id);
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			-----------shigang modify--------------
			SysPeoplepo sysPeoplepo = (SysPeoplepo) HibernateDAO.loadStringValue(SysPeoplepo.class,id);
			
			return sysPeoplepo;
		}
		catch(Exception e)
		{
			logger.error("[420]PeopleFind.findModify() 用户信息修改失败"+e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * 根据用户ID查询管理者表，查询用户管理的组，并且关联到组表，使组类型为公司。
	 * 日期 2006-11-24
	 * 
	 * @author wangyanguang/王彦广 
	 * @param userid             	用户ID
	 * @return List
	 *
	 */
	public List getSourceManagerInfo(String userid)
	{
		String sql ="from SourceManagerpo t1,SysGrouppo t2 where t1.c650000007='"
			+userid+"' and t1.c650000003=t2.c1 and t2.c630000021='2' and t1.c650000005='1'";
		System.out.println(sql);
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(sql);
//			List list = query.list();
//			-----------shigang modify--------------
    		List list= HibernateDAO.queryObject(sql);
			
			if(list!=null)
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return list;
			}
			else
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return null;
			}
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 根据用户ID，资源信息BEAN，条件LIST，查询出资源信息，组信息，技能授权信息的LIST集合。
	 * 日期 2006-11-24
	 * 
	 * @author wangyanguang/王彦广 
	 * @param userid
	 * @param sourceConfigBean
	 * @param beanList
	 * @return List
	 *
	 */
	public List getSource(String userid,SourceConfigInfoBean sourceConfigBean, List beanList)
	{	String sql = "";
		int size = 0;
		if(beanList==null)
		{
			size = 0;
		}
		if(size==0)
		{	
			//sql = "select distinct t1.sourceId,t1.sourceModule,t2.c650000003,t3.c630000018 from Sourceconfig t1,SourceManagerpo t2,SysGrouppo t3 where 1=1 and t2.c650000007='"
			//+userid+"' and t2.c650000003=t3.c1 and t3.c630000021='2' and t2.c650000005='1'";
			sql = "select  t1,t3,t4 from Sourceconfig t1,SourceManagerpo t2,SysGrouppo t3,SysSkillpo t4 where 1=1 and t2.c650000007='"
				+userid+"' and t2.c650000003=t3.c1 and t3.c630000021='2' and t2.c650000005='1' and t4.c610000008=t1.sourceId and t4.c610000007="+userid;
			String sourceid = Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
			String parentid = Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
			String enname = Function.nullString(sourceConfigBean.getSource_name());
			String cnname = Function.nullString(sourceConfigBean.getSource_cnname());
			String desc = Function.nullString(sourceConfigBean.getSource_desc());
			String type = Function.nullString(sourceConfigBean.getSource_type());
			String orderby = Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
			String module = Function.nullString(sourceConfigBean.getSource_module());
			
			if(!sourceid.equals("")&&sourceid!=null)
			{
				String tmpsql[] = sourceid.split(";");
				String sql1 = " and (";
				sql1 = sql1 + " t1.sourceId="+tmpsql[0];
				for(int i=1;i<tmpsql.length;i++)
				{
					sql1 = sql1 + " or t1.sourceId="+tmpsql[i];
				}
				sql1 = sql1 + ")";
				sql = sql + sql1;
			}
			
			if(!parentid.equals("")&&parentid!=null)
			{
				sql = sql + " and t1.sourceParentid="+parentid;
			}
			if(!enname.equals("")&&enname!=null)
			{
				sql = sql + " and t1.sourceName='"+enname+"'";
			}
			if(!cnname.equals("")&&cnname!=null)
			{
				sql = sql + " and t1.sourceCnname='"+cnname+"'";
			}
			if(!desc.equals("")&&desc!=null)
			{
				sql = sql + " and t1.sourceDesc='"+desc+"'";
			}
			if(!type.equals("")&&type!=null)
			{
				sql = sql + " and t1.sourceType like '%"+type+"%'";
			}
			if(!orderby.equals("")&&orderby!=null)
			{
				sql = sql + " and t1.sourceOrderby="+orderby;
			}
			if(!module.equals("")&&module!=null)
			{
				sql = sql + " and t1.sourceModule='"+module+"'";
			}
		}else
		{
			sql = "select distinct t1,t5,t6 from Sourceconfig t1 ,SourceManagerpo t4,SysGrouppo t5 ,SysSkillpo t6";
			
			for(int i=0;i<size;i++)
			{
				sql = sql +" ,Sourceconfigattribute t2"+i;
			}
			
			for(int j =0;j<size;j++)
			{
				sql = sql + " ,Sourceattributevalue t3"+j;
			}
			sql = sql +" where 1=1 ";
			
			for(int m=0;m<size;m++)
			{
				sql = sql + " and t1.sourceId = t2"+m+".sourceattSourceid ";
			}
			
			for(int n=0;n<size;n++)
			{
				SourceAttQueryBean sourceAttBean = (SourceAttQueryBean)beanList.get(n);
				String attname = Function.nullString(sourceAttBean.getsource_attname());
				System.out.println(attname);
				if(!attname.equals(""))
				{
					sql = sql + " and t2"+n+".sourceattEnname='"+attname+"'";
				}
				
			}
			for(int o=0;o<size;o++)
			{
				sql = sql + " and t2"+o+".sourceattId = t3"+o+".sourceattvalueAttid";
			}
			for(int p=0;p<size;p++)
			{
				SourceAttQueryBean sourceAttBean = (SourceAttQueryBean)beanList.get(p);
				String attvalue = Function.nullString(sourceAttBean.getsource_attnamevalue());
				String operation = Function.nullString(sourceAttBean.getsource_attqueryop());
				if(!operation.equals("")&&!attvalue.equals(""))
				{
					sql = sql + " and t3"+p+".sourceattvalueValue "+operation+" '"+attvalue+"'";
				}
			}
			
			String sourceid = Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
			String parentid = Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
			String enname = Function.nullString(sourceConfigBean.getSource_name());
			String cnname = Function.nullString(sourceConfigBean.getSource_cnname());
			String desc = Function.nullString(sourceConfigBean.getSource_desc());
			String type = Function.nullString(sourceConfigBean.getSource_type());
			String orderby = Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
			String module = Function.nullString(sourceConfigBean.getSource_module());
			
			
			if(!sourceid.equals("")&&sourceid!=null)
			{
				String tmpsql[] = sourceid.split(";");
				String sql1 = " and (";
				sql1 = sql1 + "t1.sourceId="+(tmpsql[0]);
				for(int i=1;i<tmpsql.length;i++)
				{
					sql1 = sql1 + " or t1.sourceId="+(tmpsql[i]);
				}
				sql1 = sql1 + ")";
				System.out.println(sql1);
				sql = sql + sql1;
			}
			if(!parentid.equals("")&&parentid!=null)
			{
				sql = sql + " and t1.sourceParentid="+parentid;
			}
			if(!enname.equals("")&&enname!=null)
			{
				sql = sql + " and t1.sourceName='"+enname+"'";
			}
			if(!cnname.equals("")&&cnname!=null)
			{
				sql = sql + " and t1.sourceCnname='"+cnname+"'";
			}
			if(!desc.equals("")&&desc!=null)
			{
				sql = sql + " and t1.sourceDesc='"+desc+"'";
			}
			if(!type.equals("")&&type!=null)
			{
				sql = sql + " and t1.sourceType like '%"+type+"%'";
			}
			if(!orderby.equals("")&&orderby!=null)
			{
				sql = sql + " and t1.sourceOrderby="+orderby;
			}
			if(!module.equals("")&&module!=null)
			{
				sql = sql + " and t1.sourceModule='"+module+"'";
			}
			sql = sql + "and t4.c650000007='"
				+userid+"' and t4.c650000003=t5.c1 and t5.c630000021='2' and t4.c650000005='1'"+
				"and t6.c610000008=t1.sourceId and t6.c610000007="+userid;
		}
		System.out.println("sql:"+sql);
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(sql);
//			List list = query.list();
//			-----------shigang modify--------------
			List list= HibernateDAO.queryObject(sql);
			if(list!=null)
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return list;
			}
			else
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return null;
			}
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * 根据角色ID，资源ID，(代办人ID，资源ID)从角色授权表与资源表的关联中读取信息，看此用户是否有对此表的权限（查询出所有一级节点）。
	 * 日期 2006-11-24
	 * 
	 * @author wangyanguang/王彦广 
	 * @param roleid
	 * @param schemnameid
	 * @return List
	 */

	public List getRoleRelationSourceInfo(String roleid, String schemnameid)
	{
		
		try {
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
			String sql = "from SysSkillpo t1,Sourceconfig t2 where t1.c610000007="+roleid+" and t1.c610000008=t2.sourceId  and t2.sourceId="+schemnameid;
//			Query query = session.createQuery(sql);
//			List list = query.list();
			
//			-----------shigang modify--------------
			List list= HibernateDAO.queryObject(sql);
			if(list!=null)
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return list;
			}
			else
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return null;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}
	
	public static void main(String [] args)
	{
	
		//List getFlag(SourceConfigBean sourceConfigBean, List beanList)
		SourceConfigInfoBean sourceconfigBean = new SourceConfigInfoBean();
//		sourceconfigBean.setSource_id("187;");
		sourceconfigBean.setSource_parentid(new Long(232));
//		sourceconfigBean.setSource_type("4;");
		
		SourceAttQueryBean sourceatt = new SourceAttQueryBean();
		sourceatt.setsource_attname("source_type");
		sourceatt.setsource_attnamevalue("1");
		sourceatt.setsource_attqueryop("=");
		List list1 = new ArrayList();
		list1.add(sourceatt);
		GetRole getRole = new GetRole();
		RoleInterfaceAssociate roleasso = new RoleInterfaceAssociate();
		List list  = roleasso.getSource("000000000000139",sourceconfigBean,list1);
		if(list!=null)
		{
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				Object[] obj = (Object[])it.next();
				Sourceconfig sourceconfig = (Sourceconfig)obj[0];
				SysGrouppo sysgroup       = (SysGrouppo)obj[1];
				SysSkillpo sysskill		  = (SysSkillpo)obj[2];
				System.out.println(sourceconfig.getSourceCnname());
				System.out.println(sysgroup.getC630000020());
				System.out.println(sysskill.getC610000007());
			}
		}
		
//		try 
//		{
//			Document doc = sourceRoleGrandInterface.getMenuTree("hb3", sourceconfigBean,list1,"0","0");
//			XMLWriter writer = new XMLWriter(new FileOutputStream(new File("E:/testaaa.xml")));
//			writer.write(doc);
//			writer.close();
//		} catch (Exception e1) 
//		{
//			System.out.println("Excepiton");
//			e1.getMessage();
//		}
	}
}
