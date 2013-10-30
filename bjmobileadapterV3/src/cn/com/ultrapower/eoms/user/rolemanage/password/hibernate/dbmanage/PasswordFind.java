package cn.com.ultrapower.eoms.user.rolemanage.password.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.password.hibernate.po.Passwordmanage;
import cn.com.ultrapower.eoms.user.comm.function.Function;

/**
 * <p>Description:用户密码信息模块使用hibernate从数据库中查找字段<p>
 * @author wangwenzhuo
 * @CreatTime 2006-12-25
 */
public class PasswordFind {
	
	static final Logger logger = (Logger) Logger.getLogger(PasswordFind.class);
	
	GetFormTableName getFormTableName	= new GetFormTableName();
	//用户信息表
	private String usertablename		= getFormTableName.GetFormName("RemedyTpeople");
	
	/**
	 * <p>Description:根据用户密码信息ID在passwordmanage表中查找用户密码信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-27
	 * @param pwdId
	 * @return Passwordmanage
	 */
	protected Passwordmanage findPasswordInfoById(String pwdId)
	{
		try 
		{
			Passwordmanage passwordmanage	= (Passwordmanage)HibernateDAO.load(Passwordmanage.class,new Long(pwdId));
			return passwordmanage;
		} 
		catch (Exception e) 
		{
			logger.error("[522]PasswordFind.findPasswordInfo() 根据用户密码信息ID在passwordmanage表中查找用户密码信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据用户登陆名在passwordmanage表中查找用户密码信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-25
	 * @param loginName
	 * @return
	 */
	protected Passwordmanage findPasswordInfoByName(String loginName)
	{
		try 
		{
			String sql	= "from Passwordmanage where loginname ='"+loginName+"'";
			List list = HibernateDAO.queryClearObject(sql);
			if(list.size()>0)
			{
				for(Iterator it = list.iterator();it.hasNext();)
				{
					Passwordmanage passwordmanage = (Passwordmanage)it.next();
					return passwordmanage;
				}
				return null;
			}
			else
			{
				logger.info("[516]PasswordFind.findPasswordInfo() 该用户在系统中不存在");
				return null;
			}
		} 
		catch (Exception e) 
		{
			logger.error("[516]PasswordFind.findPasswordInfo() 根据用户登陆名在passwordmanage表中查找用户密码信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:验证用户登陆名和密码信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-26
	 * @param loginName
	 * @param password
	 * @return boolean
	 */
	protected boolean validatePasswordFromPeopleTable(String loginName,String password)
	{
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try 
		{
			String sql = "select a.C630000002 from "+usertablename+" a where a.C630000001='"+loginName+"' and a.C630000012='0'" ;
			System.out.println(sql);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			//用户密码
			String userPassword	= "";
			//若有记录
			if(rs.next())
			{
				userPassword = Function.nullString(rs.getString("C630000002"));
				//如果密码相符
				if(password.equals(userPassword))
				{
					logger.info("[517]PasswordFind.validatePasswordFromPeopleTable() 用户名密码验证通过");
					return true;
				}
				else
				{
					logger.info("[517]PasswordFind.validatePasswordFromPeopleTable() 用户名密码信息不正确");
					return false;
				}
			}
			else
			{
				logger.info("[517]PasswordFind.validatePasswordFromPeopleTable() 系统中该用户不存在");
				return false;
			}
		} 
		catch (Exception e) 
		{
			logger.error("[517]PasswordFind.validatePasswordFromPeopleTable() 验证用户登陆名和密码信息失败"+e.getMessage());
			return false;
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
	}
	
	/**
	 * <p>Description:获得用户最后n次修改的密码<p>
	 * @author wangwenzhuo
	 * @creattime 2006-02-03
	 * @param userLoginName
	 * @return String
	 */
	protected String getLastPassword(String userLoginName)
	{
		try
		{
			String sql	= "from Passwordmanage where loginname ='"+userLoginName+"'";
			List list = HibernateDAO.queryClearObject(sql);
			if(list.size()>0)
			{
				for(Iterator it = list.iterator();it.hasNext();)
				{
					Passwordmanage passwordmanage	= (Passwordmanage)it.next();
					return passwordmanage.getLastpassword();
				}
				return null;
			}
			else
			{
				logger.info("[545]PasswordFind.getLastPassword() 该用户在系统中不存在");
				return null;
			}
		}
		catch (Exception e) 
		{
			logger.error("[545]PasswordFind.getLastPassword() 获得用户最后n次修改的密码失败"+e.getMessage());
			return null;
		}
	}
}