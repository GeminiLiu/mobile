package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import cn.com.ultrapower.eoms.user.authorization.bean.UserSendScopeBean;
import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.po.UserSendScope;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class UserSendScopeOp
{
	public UserSendScope beanValue(UserSendScopeBean usersendscopebean)
	{
		
		UserSendScope usersendscopeinfo = new UserSendScope();

		usersendscopeinfo.setId(usersendscopebean.getId());
		usersendscopeinfo.setGrandid(usersendscopebean.getGrandid());
		usersendscopeinfo.setGroupid(usersendscopebean.getGroupid());
		usersendscopeinfo.setGroupnameid(usersendscopebean.getGroupnameid());
		usersendscopeinfo.setMemo(usersendscopebean.getMemo());
		usersendscopeinfo.setMemo1(usersendscopebean.getMemo1());
		usersendscopeinfo.setNote(usersendscopebean.getNote());
		usersendscopeinfo.setUserid(usersendscopebean.getUserid());
		usersendscopeinfo.setSourceid(usersendscopebean.getSourceid());
		usersendscopeinfo.setSendscopetype(usersendscopebean.getSendscopetype());
		
		return usersendscopeinfo;
	}
	public synchronized boolean sourceCnfInsert(UserSendScopeBean usersendscopebean)
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
	public synchronized boolean sourceCnfModify(UserSendScopeBean usersendscopebean)
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
			String Sql="from UserSendScope where id="+id;
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
