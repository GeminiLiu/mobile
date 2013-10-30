package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleInfo;

/**
 * <p>Description:获得一个组下及其下所有节点的所有人员<p>
 * @author wangwenzhuo
 * @creattime 2007-2-28
 */
public class PeopleUnderGroupInterface {
	
	static final Logger logger 			= (Logger) Logger.getLogger(PeopleUnderGroupInterface.class);
	
	GetFormTableName getFormTableName	= new GetFormTableName();
	//用户信息表
	private String people				= getFormTableName.GetFormName("RemedyTpeople");
	//组信息表
	private String group				= getFormTableName.GetFormName("RemedyTgroup");
	//组成员表
	private String groupuser			= getFormTableName.GetFormName("RemedyTgroupuser");

	/**
	 * <p>Description:根据传过来的组ID或组IntId获得组下及其下所有节点的所有人员的List<p>
	 * @author wangwenzhuo
	 * @creattime 2007-2-28
	 * @param groupId		可以为标准组ID,也可以为Int组ID
	 * @return List			List中为PeopleInfo的Bean
	 */
	public List getPeopleUnderGroup(String groupId)
	{
		System.out.println("the department id is: " + groupId);
		//将groupId转为标准组ID
		String finalGroupId = Function.getStrZeroConvert(groupId);
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct a.C630000001,a.C1,a.C630000029 from "+people+" a,"+group+" b,"+groupuser+" c");
		sql.append(" where b.C630000037 like'%"+finalGroupId+";%' and b.C1 = c.C620000027 and c.C620000028 = a.C1");
		sql.append(" and a.C630000012='0'");
		List list = new ArrayList();
		try
		{
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
			while(rs.next())
			{
				PeopleInfo peopleInfo = new PeopleInfo();
				
				peopleInfo.setUserLoginname(rs.getString("C630000001"));
				peopleInfo.setUserId(rs.getString("C1"));
				peopleInfo.setUserIntId(Integer.parseInt(rs.getString("C630000029")));
				list.add(peopleInfo);
			}
		}
		catch(Exception e)
		{
			logger.error("[548]PeopleUnderGroupInterface.getPeopleUnderGroup() 根据传过来的组ID或组IntId获得组下及其下所有节点的所有人员的List失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
		return list;
	}
	
	/**
	 * <p>Description:根据传过来的组ID或组IntId获得组下及其下所有节点的所有人员的List<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param groupId
	 * @return List		List中为SysPeoplepo
	 */
	public List getSysPeopleUnderGroup(String groupId)
	{
		//将groupId转为标准组ID
		String finalGroupId = Function.getStrZeroConvert(groupId);
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct a.C630000009,a.C630000003,a.C630000001,a.C1,a.C630000029 from "+people+" a,"+group+" b,"+groupuser+" c");
		sql.append(" where b.C630000037 like'%"+finalGroupId+";%' and b.C1 = c.C620000027 and c.C620000028 = a.C1");
		sql.append(" and a.C630000012='0'");
		
		List list = new ArrayList();
		try
		{
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
			while(rs.next())
			{
				//需要获得SysPeoplepo类型对象的列表
				SysPeoplepo sysPeoplepo = new SysPeoplepo();
				sysPeoplepo.setC630000009(rs.getString("C630000009"));
				sysPeoplepo.setC630000003(rs.getString("C630000003"));
				sysPeoplepo.setC630000001(rs.getString("C630000001"));
				sysPeoplepo.setC1(rs.getString("C1"));
				sysPeoplepo.setC630000029(new Long(Integer.parseInt(rs.getString("C630000029"))));
				list.add(sysPeoplepo);
			}
		}
		catch(Exception e)
		{
			logger.error("[549]PeopleUnderGroupInterface.getPeopleUnderGroup() 根据传过来的组ID或组IntId获得组下及其下所有节点的所有人员的List失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
		System.out.println("the list size is: " + list.size());
		return list;
	}

}
