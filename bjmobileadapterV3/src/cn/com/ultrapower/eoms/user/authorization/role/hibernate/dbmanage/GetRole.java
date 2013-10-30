package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.AbstractT92;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceAttQueryBean;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigBean;
import cn.com.ultrapower.eoms.user.userinterface.SourceConfigInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.RoleInfoBean;

public class GetRole {
	static final Logger logger = (Logger) Logger.getLogger(GetRole.class);
	
	/**
	 * 查询技能授权表中所有信息
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @return List
	 */
	public  List getRole() 
	{
		try 
		{
//			Transaction tx = session.beginTransaction();
//			String sql = "from SysSkillpo";
//			List l_result = session.find(sql);
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return l_result;
			return HibernateDAO.queryObject("from SysSkillpo");
		} 
		catch (Exception e) 
		{
			logger.info("214 GetRole 类中 getRole 方法中执行查询时出现异常！");
			return null;
		}
	}
	
	/**
	 * 根据资源ID查询技能授权表中的满足条件的记录集合。
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param sourceid
	 * @return List
	 */
	public  List getRoleInfo(String sourceid) 
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery("from SysSkillpo where c610000008 ="+ sourceid);
//			List list = query.list();
//			if(list!=null)
//			{
//				return list;
//			}
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return null;
			return HibernateDAO.queryObject("from SysSkillpo where c610000008 ='"+ sourceid+"'");
	
		} catch (Exception e) 
		{
			logger.info("215 GetRole 类中 getRoleInfo 方法执行查询时出现异常！");
			return null;
		}
	}

	/**
	 * 根据ID查询技能授权表的一条信息Bean.
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param id
	 * @return AbstractT92
	 */
	public  AbstractT92 getFormfieldBean(String id) 
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery("from SysSkillpo where c1 ="
//					+ id);
//			List list = query.list();
			List list= HibernateDAO.queryObject("from SysSkillpo where c1 ='"+id+"'");
			
			for (Iterator it = list.iterator(); it.hasNext();) 
			{
				AbstractT92 t92 = (AbstractT92) it.next();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return t92;
			}
			return null;
		} 
		catch (Exception e) 
		{
			logger.info("216 GetRole 类中 getFormfieldBean 方法中根据ID查询出现异常！");
			return null;
		}
	}
	
	/**
	 * 根据ID查询技能授权表的一条信息Bean.
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param id
	 * @return AbstractT92
	 */
	public  SysSkillpo getSkillInfo(String id) 
	{
		try 
		{
			List list= HibernateDAO.queryObject("from SysSkillpo where c1 ='"+id+"'");
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery("from SysSkillpo where c1 ="
//					+ id);
//			List list = query.list();
			for (Iterator it = list.iterator(); it.hasNext();) 
			{
				SysSkillpo t92 = (SysSkillpo) it.next();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return t92;
			}
			return null;
		} 
		catch (Exception e) 
		{
			logger.info("216 GetRole 类中 getSkillInfo(String id) 方法中根据ID查询出现异常！");
			return null;
		}
	}
	//根据角色ID，资源ID，从角色授权表中读取信息，看此用户是否有对此表的权限。

	/**
	 * 根据角色ID，资源ID查询技能授权表中满足条件的记录集合。
	 */
	public List getUserFlag(String roleid, String schemnameid)
	{
		
		try 
		{
//			
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
			String sql = "from SysSkillpo where c610000007='"+roleid+"' and c610000008='"+schemnameid+"'";
			List list= HibernateDAO.queryObject(sql);
//			Query query = session.createQuery(sql);
//			List list = query.list();
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
			
		} catch (Exception e) 
		{
			logger.info("217 GetRole 类中 getUserFlag 方法中根据角色ID资源ID查询时出现异常！");
			return null;
		}
	}
	
	//根据角色ID，资源ID，(代办人ID，资源ID)从角色授权表中读取信息，看此用户是否有对此表的权限。

	public List getFlag(String roleid, String schemnameid)
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
			String sql = "from SysSkillpo where (c610000007='"+roleid+"' and c610000008='"+schemnameid+"') or (c610000014='"+roleid+"' and c610000008='"+schemnameid+"')";
//			Query query = session.createQuery(sql);
//			List list = query.list();
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
			logger.info("218 GetRole 类的 getFlag 方法中根据角色ID，资源ID查询时出现异常！");
			return null;
		}
	}
	
	//根据代办人ID，资源ID，资源moduleID，查询role、sourceconfig表，取出所有代办人信息。
	public List getCommissionInfo(String commissionUID,String moduleid)
	{
		String sql ="from SysSkillpo t1,Sourceconfig t2 where t1.c610000014='"+commissionUID+"' and t1.c610000008=t2.sourceId and t2.sourceModule='"+moduleid+"'";
		System.out.println(sql);
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(sql);
//			List list = query.list();
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
			logger.info("219 GetRole 类中 getCommissionInfo 方法根据代办人ID资源ID查询时出现异常！");
			return null;
		}
	}

	//根据角色ID，资源Bean，(代办人ID，资源Bean)从角色授权表中读取信息，看此用户是否有对此表的权限。
	public List getFlag(String roleid, SourceConfigBean sourceConfigBean)
	{	
		String sql ="from SysSkillpo t1,Sourceconfig t2 where (t1.c610000007='"+roleid+"' and t1.c610000008=t2.sourceId";
		
		String sourceid 	= "";
		String parentid 	= "";
		String enname		= "";
		String cnname 		= "";
		String desc 		= "";
		String type 		= "";
		String orderby 		= "";
		String module 		= "";
		if(sourceConfigBean!=null)
		{
			sourceid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
			parentid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
			enname 		= Function.nullString(sourceConfigBean.getSource_name());
			cnname 		= Function.nullString(sourceConfigBean.getSource_cnname());
			desc 		= Function.nullString(sourceConfigBean.getSource_desc());
			type 		= Function.nullString(sourceConfigBean.getSource_type());
			orderby 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
			module 		= Function.nullString(sourceConfigBean.getSource_module());
		}
		if(!sourceid.equals("")&&sourceid!=null)
		{
			sql = sql + " and t2.sourceId='"+sourceid+"'";
		}
		if(!parentid.equals("")&&parentid!=null)
		{
			sql = sql + " and t2.sourceParentid='"+parentid+"'";
		}
		if(!enname.equals("")&&enname!=null)
		{
			sql = sql + " and t2.sourceName='"+enname+"'";
		}
		if(!cnname.equals("")&&cnname!=null)
		{
			sql = sql + " and t2.sourceCnname='"+cnname+"'";
		}
		if(!desc.equals("")&&desc!=null)
		{
			sql = sql + " and t2.sourceDesc='"+desc+"'";
		}
		if(!type.equals("")&&type!=null)
		{
			sql = sql + " and t2.sourceType='"+type+"'";
		}
		if(!orderby.equals("")&&orderby!=null)
		{
			sql = sql + " and t2.sourceOrderby='"+orderby+"'";
		}
		if(!module.equals("")&&module!=null)
		{
			sql = sql + " and t2.sourceModule='"+module+"'";
		}
		sql = sql + ") or ( t1.c610000014='"+roleid+"' and t1.c610000008=t2.sourceId";
		
		if(!sourceid.equals("")&&sourceid!=null)
		{
			sql = sql + " and t2.sourceId='"+sourceid+"'";
		}
		if(!parentid.equals("")&&parentid!=null)
		{
			sql = sql + " and t2.sourceParentid='"+parentid+"'";
		}
		if(!enname.equals("")&&enname!=null)
		{
			sql = sql + " and t2.sourceName='"+enname+"'";
		}
		if(!cnname.equals("")&&cnname!=null)
		{
			sql = sql + " and t2.sourceCnname='"+cnname+"'";
		}
		if(!desc.equals("")&&desc!=null)
		{
			sql = sql + " and t2.sourceDesc='"+desc+"'";
		}
		if(!type.equals("")&&type!=null)
		{
			sql = sql + " and t2.sourceType='"+type+"'";
		}
		if(!orderby.equals("")&&orderby!=null)
		{
			sql = sql + " and t2.sourceOrderby="+orderby;
		}
		if(!module.equals("")&&module!=null)
		{
			sql = sql + " and t2.sourceModule='"+module+"'";
		}
		sql = sql +")";
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(sql);
//			List list = query.list();
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
			logger.info("220 GetRole 类中 getFlag（String roleid, SourceConfigBean sourceConfigBean）方法查询时出现异常！");
			return null;
		}
	}
	
	//根据Bean查询出资源表中的记录。
	public List getFlag(SourceConfigBean sourceConfigBean)
	{	
		String sql ="from Sourceconfig t2 where 1=1 ";
		
		String sourceid 	= "";
		String parentid 	= "";
		String enname 		= "";
		String cnname 		= "";
		String desc 		= "";
		String type 		= "";
		String orderby 		= "";
		String module 		= "";
		if(sourceConfigBean!=null)
		{
			sourceid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
			parentid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
			enname 		= Function.nullString(sourceConfigBean.getSource_name());
			cnname 		= Function.nullString(sourceConfigBean.getSource_cnname());
			desc 		= Function.nullString(sourceConfigBean.getSource_desc());
			type 		= Function.nullString(sourceConfigBean.getSource_type());
			orderby 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
			module 		= Function.nullString(sourceConfigBean.getSource_module());
		}
		
		if(!sourceid.equals("")&&sourceid!=null)
		{
			sql = sql + " and t2.sourceId='"+sourceid+"'";
		}
		if(!parentid.equals("")&&parentid!=null)
		{
			sql = sql + " and t2.sourceParentid='"+parentid+"'";
		}
		if(!enname.equals("")&&enname!=null)
		{
			sql = sql + " and t2.sourceName='"+enname+"'";
		}
		if(!cnname.equals("")&&cnname!=null)
		{
			sql = sql + " and t2.sourceCnname='"+cnname+"'";
		}
		if(!desc.equals("")&&desc!=null)
		{
			sql = sql + " and t2.sourceDesc='"+desc+"'";
		}
		if(!type.equals("")&&type!=null)
		{
			sql = sql + " and t2.sourceType='"+type+"'";
		}
		if(!orderby.equals("")&&orderby!=null)
		{
			sql = sql + " and t2.sourceOrderby="+orderby;
		}
		if(!module.equals("")&&module!=null)
		{
			sql = sql + " and t2.sourceModule='"+module+"'";
		}
		
		System.out.println("sql:"+sql);
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(sql);
//			List list = query.list();
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
			logger.info("221 GetRole 类中 getFlag(SourceConfigBean sourceConfigBean)方法中查询时出现异常！");
			return null;
		}
	}
	
	//根据Bean与List 查询资源ID List.
	//根据Bean查询出资源表中的记录。

	public List getFlag(SourceConfigInfoBean sourceConfigBean, List beanList)
	{	
		String sql 		= "";
		String sourceid = "";
		String parentid = "";
		String enname 	= "";
		String cnname 	= "";
		String desc 	= "";
		String type 	= "";
		String orderby 	= "";
		String module 	= "";
		int size 		= 0; 
		if(beanList!=null)
		{
			size = beanList.size();
		}
		
		if(size==0)
		{	
			sql = "select distinct t1.sourceId,t1.sourceModule from Sourceconfig t1 where 1=1 ";
			if(sourceConfigBean!=null)
			{
				 sourceid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
				 parentid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
				 enname 	= Function.nullString(sourceConfigBean.getSource_name());
				 cnname 	= Function.nullString(sourceConfigBean.getSource_cnname());
				 desc 		= Function.nullString(sourceConfigBean.getSource_desc());
				 type 		= Function.nullString(sourceConfigBean.getSource_type());
				 orderby 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
				 module 	= Function.nullString(sourceConfigBean.getSource_module());
			}
			if(!sourceid.equals("")&&sourceid!=null)
			{
				String tmpsql[] = sourceid.split(",");
				String sql1 	= " and (";
				sql1 			= sql1 + " t1.sourceId='"+tmpsql[0];
				for(int i=1;i<tmpsql.length;i++)
				{
					sql1 = sql1 + "' or t1.sourceId='"+tmpsql[i];
				}
				sql1 = sql1 + "')";
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
				sql = sql + " and t1.sourceOrderby='"+orderby+"'";
			}
			if(!module.equals("")&&module!=null)
			{
				sql = sql + " and t1.sourceModule='"+module+"'";
			}
		}else
		{
			sql = "select distinct t1.sourceId,t1.sourceModule from Sourceconfig t1 ";
			
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
			
			if(sourceConfigBean!=null)
			{
			 sourceid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
			 parentid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
			 enname 	= Function.nullString(sourceConfigBean.getSource_name());
			 cnname 	= Function.nullString(sourceConfigBean.getSource_cnname());
			 desc 		= Function.nullString(sourceConfigBean.getSource_desc());
			 type 		= Function.nullString(sourceConfigBean.getSource_type());
			 orderby 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
			 module 	= Function.nullString(sourceConfigBean.getSource_module());
			}
			
			if(!sourceid.equals("")&&sourceid!=null)
			{
				String tmpsql[] = sourceid.split(",");
				String sql1 = " and (";
				sql1 = sql1 + "t1.sourceId='"+(tmpsql[0]);
				for(int i=1;i<tmpsql.length;i++)
				{
					sql1 = sql1 + "' or t1.sourceId='"+(tmpsql[i]);
				}
				sql1 = sql1 + "')";
				System.out.println(sql1);
				sql = sql + sql1;
			}
			if(!parentid.equals("")&&parentid!=null)
			{
				sql = sql + " and t1.sourceParentid='"+parentid+"'";
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
			
		}
		System.out.println("sql:"+sql);
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(sql);
//			List list = query.list();
			
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
			logger.info("222 GetRole 类中 getFlag(SourceConfigInfoBean sourceConfigBean, List beanList)方法执行查询时出现异常！");
			return null;
		}
	}
	
	//根据组ID，资源ID，从角色授权表中读取信息，看此组是否有结此表的权限。
	public List getBoolean(String groupid,String schemnameid)
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery("from SysSkillpo where  c610000011="+groupid+" and c610000008="+schemnameid);
//			List list = query.list();
			String sql="from SysSkillpo where  c610000011='"+groupid+"' and c610000008='"+schemnameid+"'";

			List list= HibernateDAO.queryObject(sql);
			if(list.size()>0)
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
			logger.info("223 GetRole 类中 getBoolean(String groupid,String schemnameid) 方法执行查询时出现异常！");
			return null;
		}
	}
	
	public  List getRoleList() 
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
			String sql = "select  distinct t.c610000011,t.c610000007,t.c610000008,t.c610000009 from SysSkillpo t";
//			logger.info(sql);
//			Query l_result_l = session.createQuery(sql);
//			List l_result = l_result_l.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return l_result;
			return HibernateDAO.queryObject(sql);
		}
		catch (Exception e) 
		{
			logger.info("224 GetRole 类中 getRoleList() 方法执行查询时出现异常！");
			return null;
		}
	}
	
	public List getRoleActionList(String roleid,String roletype,String moduleid,String condition)
	{	
		String sql ="";
		
		if(condition==null||condition.equals(""))
		{
			System.out.println("condition==null"+roleid);
			if(roleid!=null&&(roleid.length()>0)&&roletype!=null)
			{
				 sql = "from SysSkillpo where c610000007='"+roleid+"' and c610000008='"+moduleid+"' and c610000011='"+roletype+"'";
			}
			if((roleid==null||roleid.equals(""))&&roletype!=null)
			{
				 sql = "from SysSkillpo where c610000011='"+roletype+"' and c610000008='"+moduleid+"'";
			}
		}else
		{
			System.out.println("condition not null"+roleid);
			if(roleid!=null&&roletype!=null&&(roleid.length()>0))
			{
				 sql = "from SysSkillpo where c610000007='"+roleid+"' and c610000008='"+moduleid+"' and c610000009='"+condition+"'";
			}
			if((roleid==null||roleid.equals(""))&&roletype!=null)
			{
				 sql = "from SysSkillpo where c610000011='"+roletype+"' and c610000008='"+moduleid+"' and c610000009='"+condition+"'";
			}
		}
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query l_result_l = session.createQuery(sql);
//			List l_result = l_result_l.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return l_result;
			return HibernateDAO.queryObject(sql);
		} 
		catch (Exception e) 
		{
			logger.info("225 GetRole 类中 getRoleActionList(String roleid,String roletype,String moduleid,String condition) 方法执行查询时出现异常!");
			return null;
		}
	}
	
	//根据角色ID，使用户信息表与技能授权表关联，查询用户信息与技能授中的信息。

	public  List getUserGrand(String userid) 
	{
		try 
		{
			String sql="from SysSkillpo t1,SysPeoplepo t2  where t2.c1 ='"+userid+"' and t2.c1=t1.c610000007 and t2.c630000012='0'";
//			HibernateDAO   session 	= new HibernateDAO(); 
			List 			l1	    = HibernateDAO.queryObject(sql);
			
			if(l1!=null)
			{
//				HibernateSessionFactory.closeSession();
				return l1;
			}else
			{
//				HibernateSessionFactory.closeSession();
				return null;
			}
		} 
		catch (Exception e) 
		{
			logger.info("226 GetRole 类中 getUserGrand(String userid)方法查询时出现异常！");
			return null;
		}
	}
	
	//根据角色ID，使用户信息表与技能授权表关联，查询用户信息与技能授权中的信息。
	public  List getAllUserGroupGrand(String userid,String sourceid) 
	{
		try 
		{
			String sql="select distinct t1,t2,t3,t4 from SysSkillpo t1,SysGroupUserpo t2,ManageGroupUserpo t3,GrandActionConfigpo t4  where t3.c610000020='"+userid
			+"' and t3.c610000023=t2.c620000027 and t2.c620000028=t1.c610000007 and t1.c610000010=t4.c620000034 and t1.c610000008=t4.c620000032 and t1.c610000008='"+sourceid;
			sql = sql+"' and t3.c610000023=t1.c610000011";
			sql = sql+" and t1.c610000008= t3.c610000025";
			sql = sql + " and (t1.c610000015 >"+(System.currentTimeMillis()/1000)+" or t1.c610000014 is null or t1.c610000015=0)";
			sql = sql + " order by t2";
//			HibernateDAO   session = new HibernateDAO(); 
			List 			l1	    = HibernateDAO.queryObject(sql);
			System.out.println("工单派发授权查询："+sql);
			if(l1!=null)
			{
//				HibernateSessionFactory.closeSession();
				return l1;
			}else
			{
//				HibernateSessionFactory.closeSession();
				return null;
			}
		} 
		catch (Exception e) 
		{
			logger.info("227 GetRole 类中 getAllUserGroupGrand(String userid,String sourceid) 方法执行查询时出现异常！");
			return null;
		}
	}
	//根据用户名、资源名和技能授权表Bean信息，查询用户派发对象，根据用户派发对象与资源ID查询技能表，生成用户、组Document对象。
	public List getAllUserGroupGrand1(String userid, String sourceid,RoleInfoBean roleinfobean) 
	{
		 String Skill_ID = "";
		 String Skill_Type = "";
		 String Skill_GroupID = "";
		 String Skill_UserID = "";
		 String Skill_Module = "";
		 String Skill_CategoryQueryID = "";
		 String Skill_Action = "";
		 String Skill_CommissionGID = "";
		 String Skill_CommissionUID = "";
		 long Skill_CommissionCloseTime = 0;
		 String Skill_Status = "";
		 String Skill_WorkFlowType = "";
		 String sql = "select distinct t1,t2,t3,t4 from SysSkillpo t1,SysGroupUserpo t2,ManageGroupUserpo t3,GrandActionConfigpo t4  where t3.c610000020='"
				+ userid
				+ "' and t3.c610000023=t2.c620000027 and t2.c620000028=t1.c610000007 and t1.c610000010=t4.c620000034 and t1.c610000008=t4.c620000032 and t1.c610000008='"
				+ sourceid +"'";
		if(roleinfobean!=null)
		{
			Skill_ID = Function.nullString(String.valueOf(roleinfobean.getSkill_ID()));
			Skill_Type = Function.nullString(String.valueOf(roleinfobean.getSkill_Type()));
			Skill_GroupID = Function.nullString(String.valueOf(roleinfobean.getSkill_GroupID()));
			Skill_UserID = Function.nullString(String.valueOf(roleinfobean.getSkill_UserID()));
			Skill_Module = Function.nullString(String.valueOf(roleinfobean.getSkill_Module()));
			Skill_CategoryQueryID = Function.nullString(String.valueOf(roleinfobean.getSkill_CategoryQueryID()));
			Skill_Action = Function.nullString(String.valueOf(roleinfobean.getSkill_Action()));
			Skill_CommissionGID = Function.nullString(String.valueOf(roleinfobean.getSkill_CommissionGID()));
			Skill_CommissionUID = Function.nullString(String.valueOf(roleinfobean.getSkill_CommissionUID()));
			Skill_CommissionCloseTime = roleinfobean.getSkill_CommissionCloseTime();
			Skill_Status = Function.nullString(String.valueOf(roleinfobean.getSkill_Status()));
			Skill_WorkFlowType = Function.nullString(String.valueOf(roleinfobean.getSkill_WorkFlowType()));
		}
		if(!Skill_ID.equals("")&&Skill_ID!=null)
		{
			sql = sql + " and t1.c1='"+Skill_ID+"'";
		}
		if(!Skill_Type.equals("")&&Skill_Type!=null)
		{
			sql = sql + " and t1.c610000006='"+Skill_Type+"'";
		}
		if(!Skill_GroupID.equals("")&&Skill_GroupID!=null)
		{
			sql = sql + " and t1.c610000011='"+Skill_GroupID+"'";
		}
		if(!Skill_UserID.equals("")&&Skill_UserID!=null)
		{
			sql = sql + " and t1.c610000007='"+Skill_UserID+"'";
		}
		if(!Skill_Module.equals("")&&Skill_Module!=null)
		{
			sql = sql + " and t1.c610000008='"+Skill_Module+"'";
		}
		if(!Skill_CategoryQueryID.equals("")&&Skill_CategoryQueryID!=null)
		{
			sql = sql + " and t1.c610000009='"+Skill_CategoryQueryID+"'";
		}
		if(!Skill_Action.equals("")&&Skill_Action!=null)
		{
			String tmpsql[] = Skill_Action.split(",");
			String sql1 = " and (";
			sql1 = sql1 + "t1.c610000010='"+(tmpsql[0])+"'";
			for(int i=1;i<tmpsql.length;i++)
			{
				sql1 = sql1 + " or t1.c610000010='"+(tmpsql[i])+"'";
			}
			sql1 = sql1 + ")";
			System.out.println("action sql:"+sql1);
			sql = sql + sql1;
		}
		if(!Skill_CommissionGID.equals("")&&Skill_CommissionGID!=null)
		{
			sql = sql + " and t1.c610000012='"+Skill_CommissionGID+"'";
		}
		if(!Skill_CommissionUID.equals("")&&Skill_CommissionUID!=null)
		{
			sql = sql + " and t1.c610000014='"+Skill_CommissionUID+"'";
		}
		if(Skill_CommissionCloseTime>0)
		{
			sql = sql + " and t1.c610000015='"+Skill_CommissionCloseTime+"'";
		}
		if(!Skill_Status.equals("")&&Skill_Status!=null)
		{
			sql = sql + " and t1.c610000018='"+Skill_Status+"'";
		}
		if(!Skill_WorkFlowType.equals("")&&Skill_WorkFlowType!=null)
		{
			sql = sql + " and t1.c610000019='"+Skill_WorkFlowType+"'";
		}
		sql = sql+" and t3.c610000023=t1.c610000011";
		sql = sql+" and t1.c610000008= t3.c610000025";
		if(!Skill_CommissionGID.equals("")&&Skill_CommissionGID!=null)
		{
			sql = sql + " and t1.c610000015 >"+(System.currentTimeMillis()/1000) ;
		}
		try 
		{
//			HibernateDAO session = new HibernateDAO();
			List l1 = HibernateDAO.queryObject(sql);

			if (l1!=null) 
			{
//				HibernateSessionFactory.closeSession();
				return l1;
			} 
			else 
			{
//				HibernateSessionFactory.closeSession();
				return null;
			}
		} 
		catch (Exception e) 
		{
			logger.info("228 GetRole 类中 getAllUserGroupGrand1(String userid, String sourceid,RoleInfoBean roleinfobean) 执行查询时出现异常！");
			return null;
		}
	}
	
	//根据角色ID，资源ID，(代办人ID，资源ID)从角色授权表与资源表的关联中读取信息，看此用户是否有对此表的权限（查询出所有一级节点）。
	public List getRoleRelationSourceInfo(String roleid, String schemnameid)
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
			String sql = "from SysSkillpo t1,Sourceconfig t2 where t1.c610000007='"+roleid+"' and t1.c610000008=t2.sourceId  and t2.sourceId='"+schemnameid+"' and t1.c610000018='0'";
//			Query query = session.createQuery(sql);
//			List list = query.list();
			List list=HibernateDAO.queryObject(sql);
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
			logger.info("229 GetRole 类中 getRoleRelationSourceInfo(String roleid, String schemnameid) 方法执行查询时出现异常！");
			return null;
		}
	}
	
	//根据用户ID、组ID和资源ID判断数据库中是否有重复记录，如果有返回true否则返回false.
	public static boolean isExists(String userid,String groupid,String sourceid)
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
			String sql = "from SysSkillpo where c610000007='"+userid+"' and c610000008='"+sourceid+"' and c610000011='"+groupid+"'";
//			Query query = session.createQuery(sql);
//			List list = query.list();
			List list=HibernateDAO.queryObject(sql);
			if(list!=null)
			{
				if(list.size()>0)
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return true;
				}else
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return false;
				}
			}
			else
			{
				return false;
			}
		} 
		catch (Exception e) 
		{
			logger.info("230 GetRole 类中 isExists(String userid,String groupid,String sourceid) 方法执行查询时出现异常！");
			return false;
		}
	}
	
	//根据用户ID、组ID和资源ID,技能动作值，判断数据库中是否有重复记录，如果有返回true否则返回false.
	public static boolean isExists(String userid,String groupid,String sourceid,String actionvalue)
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
			String sql = "from SysSkillpo where c610000007='"+userid+"' and c610000008='"+sourceid+"' and c610000011='"+groupid+"'";
			sql = sql + " and c610000010="+actionvalue;
//			Query query = session.createQuery(sql);
//			List list = query.list();
			List list=HibernateDAO.queryObject(sql);
			if(list!=null)
			{
				if(list.size()>0)
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return true;
				}else
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return false;
				}
			}
			else
			{
				return false;
			}
		} 
		catch (Exception e) 
		{
			logger.info("230 GetRole 类中 isExists(String userid,String groupid,String sourceid) 方法执行查询时出现异常！");
			return false;
		}
	}
	public static void main(String[] args) 
	{
		
		boolean bl = isExists("000000000000019","000000000600297","1");
		System.out.println(bl);
	}
}
			
		
		
	


