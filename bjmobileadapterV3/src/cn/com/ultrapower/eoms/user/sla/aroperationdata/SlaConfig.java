package cn.com.ultrapower.eoms.user.sla.aroperationdata;

import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.BaseCatagorygrandpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.sla.hibernate.po.SlaConfigPo;

/**
 * 
 * @author fangqun
 * @CreatTime 2008-4-17
 */
public class SlaConfig {
	
	static final Logger logger = (Logger) Logger.getLogger(SlaConfig.class);			
	
	/**
	 * <p>Description:对工单超时配置进行数据添加<p>
	 * @author fangqun
	 * @creattime 2008-4-17
	 * @param SlaConfigPo
	 * @return boolean
	 */
	public boolean insertSlaConfig(SlaConfigPo slaconfigpo)
	{
		try{
			slaconfigpo.setId(Function.getNewID("slaconfig", "id"));//id
			return HibernateDAO.insert(slaconfigpo);
		}
		catch(Exception e)
		{
			logger.error("[456]SlaConfig.insertSlaConfig() 对信息进行数据添加失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:对工单超时配置进行数据修改<p>
	 * @author fangqun
	 * @creattime 2008-4-17
	 * @param SlaConfigPo
	 * @return boolean
	 */
	public boolean modifySlaConfig(SlaConfigPo slaconfigpo)
	{
		try{
			return HibernateDAO.modify(slaconfigpo);
		}
		catch(Exception e)
		{
			logger.error("[458]SlaConfig.modifySlaConfig() 对信息进行数据修改失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:对工单超时配置进行数据删除<p>
	 * @author fangqun
	 * @param id
	 * @return
	 */
	public boolean deleteSlaConfig(String id)
	{
		
		try{
			IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			Statement stmt		= dataBase.GetStatement();
			String sql = " delete from slaconfig where id="+id+"";
			System.out.println("删除"+sql);
			stmt.execute(sql);
			Function.closeDataBaseSource(null, stmt, dataBase);
			return true;			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			logger.error("[457]SlaConfig.deleteSlaConfig() 对信息进行数据删除失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据id查询信息,返回SlaConfig和sysgroup<p>
	 * @author fangqun
	 * @param id
	 * @return
	 */
	public Object[] getSlaConfig_SysGroup(String id)
	{
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("from SlaConfigPo slaconfigpo,SysGrouppo sysGrouppo");
		  	sql.append(" where (slaconfigpo.Slacompany=sysGrouppo.c1)");
		  	sql.append(" and slaconfigpo.id='"+id+"'"); 
		  	
		  	List list	= HibernateDAO.queryObject(sql.toString());
		  	if(list!=null)
	   	    {
	   	    	Object[] obj = null;
		   	    Iterator it=list.iterator();
	    	    while(it.hasNext())
	    	    {
	    	    	obj = (Object[])it.next();
	    	    }
	    	    return obj;
	   	    }else{
	   	    	return null;
	   	    }
		}
		catch(Exception e)
		{
			logger.error("[418]SlaConfig.getSlaConfig_SysGroup() 根据组ID查找信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 添加或修改时排重
	 * @param SlaConfigPo
	 * @return boolean
	 */
	public boolean isDuplicate(SlaConfigPo slaconfigpo)
	{
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("from SlaConfigPo slaconfigpo where");
		  	sql.append(" slaconfigpo.SlaType='"+slaconfigpo.getSlaType()+"'");
		  	sql.append(" and slaconfigpo.SlaSchema='"+slaconfigpo.getSlaSchema()+"'");
		  	sql.append(" and slaconfigpo.Slacompany='"+slaconfigpo.getSlacompany()+"'");
		  	sql.append(" and slaconfigpo.Sendobj='"+slaconfigpo.getSendobj()+"'");
		  	sql.append(" and slaconfigpo.Slasupertime='"+slaconfigpo.getSlasupertime()+"'");
		  	List list = HibernateDAO.queryObject(sql.toString());
		  		  	
		  	if(list.size()>0){
		  		boolean flag = Function.nullString(slaconfigpo.getId()).equals(((SlaConfigPo)list.get(0)).getId());
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
			logger.error("[421]SlaConfig.isDuplicate() 排重失败"+e.getMessage());
			return true;
		}
	}
	
	/**
	 * 工单类型下拉列表
	 * @return String
	 */
	public static String getSlaSchemaMenu(String optionValue){
		
		String menusql = " from BaseCatagorygrandpo basecatagorygrandpo ";
		StringBuffer selectMenu = new StringBuffer();
		String SlaSchema = "";
		String SlaSchemaname = "";
		selectMenu.append("<option value=''>请选择</option>");
		
		try{
			List list = HibernateDAO.queryObject(menusql.toString());
			if(list.size()>0){
				for(int i=0; i<list.size(); i++){
					SlaSchema = ((BaseCatagorygrandpo)list.get(i)).getC1();
					SlaSchemaname = ((BaseCatagorygrandpo)list.get(i)).getC650000001();
					
					if(SlaSchema.equals(optionValue))
					{
						selectMenu.append("<option value='"+SlaSchema+"' selected>"+SlaSchemaname+"</option>");
					}
					else
					{
						selectMenu.append("<option value='"+SlaSchema+"'>"+SlaSchemaname+"</option>");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return selectMenu.toString();
	}
}

