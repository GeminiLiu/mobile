package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import cn.com.ultrapower.eoms.user.authorization.bean.UserSendScopeNameMagBean;
import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.po.UserSendScopeNameMag;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class UserSendScopeNameMagOp
{
	public UserSendScopeNameMag beanValue(UserSendScopeNameMagBean usersendscopebean)
	{
		
		UserSendScopeNameMag usersendscopenameinfo = new UserSendScopeNameMag();

		usersendscopenameinfo.setId(usersendscopebean.getId());
		usersendscopenameinfo.setGroupname(usersendscopebean.getGroupname());
		usersendscopenameinfo.setMemo(usersendscopebean.getMemo());
		
		return usersendscopenameinfo;
	}
	public synchronized boolean sourceCnfInsert(UserSendScopeNameMagBean usersendscopebean)
	{
		try
		{
			HibernateDAO.insert(beanValue(usersendscopebean));
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	public synchronized boolean sourceCnfModify(UserSendScopeNameMagBean usersendscopebean)
	{
		try
		{
			HibernateDAO.modify(beanValue(usersendscopebean));
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	public synchronized boolean sourceCnfDel(String id)
	{
		try
		{
			String Sql="from UserSendScopeNameMag t where t.id="+id;
			if(HibernateDAO.deleteMulObjects1(Sql))
			{
				System.out.print("删除"+id+"ok!");
				return true;
			}
			else
			{
				System.out.print("删除"+id+"失败!");
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

}
