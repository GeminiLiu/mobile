package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.Catagorygrandpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

/**
 * 
 * @author fangqun
 * @CreatTime 2008-3-10
 */
public class Catagorygrand {
	
	static final Logger logger = (Logger) Logger.getLogger(Catagorygrand.class);			
	
	/**
	 * <p>Description:对组成员信息进行数据添加<p>
	 * @author fangqun
	 * @creattime 2008-3-10
	 * @param GroupUserInfo
	 * @return boolean
	 */
	public boolean insertCatagorygrand(Catagorygrandpo catagorygrandpo)
	{
		try{
			catagorygrandpo.setId(Function.getNewID("catagorygrand", "id"));
			return HibernateDAO.insert(catagorygrandpo);
		}
		catch(Exception e)
		{
			logger.error("[456]PeopleGroup.insertPeopleGroup() 对组成员信息进行数据添加失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:对组成员信息进行数据删除<p>
	 * @param syspeoplegrouppo
	 * @return
	 */
	public boolean deleteCatagorygrand(String Catagorygrand_id)
	{
		
		try{
			IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			Statement stmt		= dataBase.GetStatement();
			String sql = " from Catagorygrandpo catagorygrandpo where catagorygrandpo.id="+Catagorygrand_id+"";
			HibernateDAO.deleteMulObjects1(sql);
			//stmt.execute(sql);
			//Function.closeDataBaseSource(null, stmt, dataBase);
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
			logger.error("[457]PeopleGroup.deletePeopleGroup() 对组成员信息进行数据删除失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据组id查询组成员信息<p>
	 * @param groupId
	 * @return
	 */
	public List find(String roleid)
	{
		try
		{
			String sql=" from Catagorygrandpo catagorygrandpo,Sourceconfig sourcecofig where catagorygrandpo.Condition_RoleID="+roleid
			+"and sourcecofig.sourceId=catagorygrandpo.Condition_sourceid";
			List list= HibernateDAO.queryObject(sql);
			return list;
		}
		catch(Exception e)
		{
			logger.error("[458]PeopleGroup.find() 按照组ID查找相应成员信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据组id查询组信息<p>
	 * @param groupId
	 * @return
	 */
	/*public SysGrouppo getGroupInfo(String groupID)
	{
		try
		{
			SysGrouppo sysGrouppo=(SysGrouppo) HibernateDAO.loadStringValue(SysGrouppo.class,groupID);
			return sysGrouppo;
		}
		catch(Exception e) 
		{
			logger.error("[418]GetGroupInfoList.getGroupInfo() 根据组ID查找组信息失败"+e.getMessage());
			return null;
		}
	}*/
	
	/**
	 * 添加或修改时排重
	 * @param SysPeopleGrouppo
	 * @return boolean
	 */
	public boolean isDuplicate(Catagorygrandpo catagorygrandpo)
	{
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("from Catagorygrandpo catagorygrandpo where");
		  	sql.append(" catagorygrandpo.Condition_RoleID='"+catagorygrandpo.getCondition_RoleID()+"'");
		  	sql.append(" and catagorygrandpo.Condition_sourceid='"+catagorygrandpo.getCondition_sourceid()+"'");
		  	List list	= HibernateDAO.queryObject(sql.toString());
		  		  	
		  	if(list.size()>0){
		  		boolean flag = Function.nullString(catagorygrandpo.getId()).equals(((Catagorygrandpo)list.get(0)).getId());
		  		if(flag){
		  			return false;
		  		}else{
		  			return true;
		  		}
		  	}else{
		  		return false;
		  	}			
		}
		catch(Exception e)
		{
			System.out.println(e);
			logger.error("[421]GroupGrand.isDuplicate() 排重失败"+e.getMessage());
			return true;
		}
	}
}
