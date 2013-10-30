package cn.com.ultrapower.eoms.user.rolemanage.password.hibernate.dbmanage;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.entryid.aroperationdata.GetNewId;
import cn.com.ultrapower.eoms.user.rolemanage.password.hibernate.po.Passwordmanage;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.comm.function.Function;

/**
 * <p>Description:对用户密码信息模块数据库操作</p>
 * @author wangwenzhuo
 * @creattime 2006-12-26
 */
public class PasswordOp {
	
	static final Logger logger = (Logger) Logger.getLogger(PasswordOp.class);
	
	//当前系统时间
	private long currentTime = System.currentTimeMillis()/1000;

	/**
	 * <p>Description:根据用户信息的bean执行插入操作<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-26
	 * @param peopleInfo
	 * @return boolean
	 */
	public synchronized boolean insertPasswordInfo(PeopleInfo peopleInfo)
	{
		try
		{
			HibernateDAO.insert(updateWhileInsertPeople(peopleInfo));
			logger.info("[520]PasswordOp.insertPasswordInfo() 添加用户密码信息成功");
			return true;
		}
		catch(Exception e)
		{
			logger.error("[520]PasswordOp.insertPasswordInfo() 根据用户信息的bean执行插入操作失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据用户密码信息的po执行修改操作<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-26
	 * @param passwordManager
	 * @return boolean
	 */
	public synchronized boolean modifyPasswordInfo(Passwordmanage passwordManager)
	{
		try
		{			
			HibernateDAO.modify(passwordManager);
			logger.info("[521]PasswordOp.modifyPasswordInfo() 更新用户密码表成功");
			return true;
		}
		catch(Exception e)
		{
			logger.error("[521]PasswordOp.modifyPasswordInfo() 根据用户密码信息的po执行修改操作失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据用户信息的bean执行修改操作<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-27
	 * @param peopleInfo
	 * @return boolean
	 */
	public synchronized boolean modifyPasswordInfo(PeopleInfo peopleInfo)
	{
		try
		{
			Passwordmanage passwordmanage = updateWhileModifyPeople(peopleInfo);
			if(passwordmanage!=null)
			{
				HibernateDAO.modify(passwordmanage);
				logger.info("[523]PasswordOp.modifyPasswordInfo() 更新用户密码信息成功");
				return true;
			}
			else
			{
				logger.info("[523]PasswordOp.modifyPasswordInfo() PasswordOp.updateWhileModifyPeople()返回为null值");
				logger.info("无需要更新的用户密码信息");
				return false;
			}	
		}
		catch(Exception e)
		{
			logger.error("[523]PasswordOp.modifyPasswordInfo() 根据用户信息的bean执行修改操作失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:当向UltraProcess:SysUser表中添加数据时同步passwordmanage表<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-26
	 * @param peopleInfo
	 * @return Passwordmanage
	 */
	private Passwordmanage updateWhileInsertPeople(PeopleInfo peopleInfo)
	{
		//获得自增的字段ID
		GetNewId getId = new GetNewId();
		//实例化用户密码信息的po
		Passwordmanage passwordManage = new Passwordmanage();
		passwordManage.setPwdid(new Long(getId.getnewid("Passwordmanage","pwdid")));
		passwordManage.setLoginname(peopleInfo.getUserLoginname());
		passwordManage.setPassword(peopleInfo.getUserPassword());
		passwordManage.setNologintime(new Long(0));
		passwordManage.setLastmodifytime(new Long(currentTime));
		return passwordManage;
	}
	
	/**
	 * <p>Description:当系统管理员修改用户信息时数据时同步passwordmanage表数据<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-26
	 * @param peopleInfo
	 * @return Passwordmanage
	 */
	private Passwordmanage updateWhileModifyPeople(PeopleInfo peopleInfo)
	{
		//通过loginName获取该用户的用户密码信息
		PasswordFind passwordFind		= new PasswordFind();
		try
		{
			Passwordmanage passwordInfo		= passwordFind.findPasswordInfoByName(peopleInfo.getUserLoginname());
			if(passwordInfo!=null)
			{
				//用户密码信息表中的密码
				String temp_password			= Function.nullString(passwordInfo.getLastpassword());
				//实例化用户密码信息的po
				Passwordmanage passwordManage 	= new Passwordmanage();
				
				//从用户信息中获得密码
				passwordManage.setPassword(peopleInfo.getUserPassword());
				//从用户的用户密码信息获得其它信息
				passwordManage.setPwdid(passwordInfo.getPwdid());
				passwordManage.setLoginname(passwordInfo.getLoginname());
				passwordManage.setLastpassword(temp_password);
				passwordManage.setNologintime(passwordInfo.getNologintime());
				passwordManage.setConnnum(passwordInfo.getConnnum());
				//若修改了密码
				passwordManage.setIpcontrol_oaflag(passwordInfo.getIpcontrol_oaflag());
				if(!temp_password.equals(peopleInfo.getUserPassword()))
				{
					passwordManage.setLastmodifytime(new Long(currentTime));
				}
				else
				{
					passwordManage.setLastmodifytime(passwordInfo.getLastmodifytime());
				}
				return passwordManage;
			}
			else
			{
				logger.info("[537]PasswordOp.updateWhileModifyPeople() 方法PasswordFind.findPasswordInfoByName()返回为null值");
				logger.info("无需要更新的用户密码信息");
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("[537]PasswordOp.updateWhileModifyPeople() 当系统管理员修改用户信息时数据时同步passwordmanage表数据失败"+e.getMessage());
			return null;
		}
	} 

}