package cn.com.ultrapower.eoms.user.config.sourcemanager.aroperationdata;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata.PeopleAssociate;
import cn.com.ultrapower.eoms.user.sla.hibernate.po.SlaConfigPo;
import  cn.com.ultrapower.eoms.user.config.sourcemanager.bean.SourceManagerBean;

public class SourceManager {
	static final Logger logger = (Logger) Logger.getLogger(SourceManager.class);
	GetFormTableName tablename = new GetFormTableName();
	String driverurl           = tablename.GetFormName("driverurl");
	String user     		   = tablename.GetFormName("user");
	String password			   = tablename.GetFormName("password");
	int serverport			   = Integer.parseInt(tablename.GetFormName("serverport"));
	String TBLName			   = tablename.GetFormName("sourceManager");
	public SourceManager()
	{
		
	}
	
	public boolean sourceManagerInsert(SourceManagerBean ourceManagerBean)
	{
		ArrayList infoValue = SourceManagerAssociate.associateInsert(ourceManagerBean);
		ArEdit ar 				  = new ArEdit(user, password, driverurl, serverport);
		logger.info("[WangYanGuang]TBLNAME"+TBLName);
		System.out.println("[WangYanGuang]TBLNAME"+TBLName);
		return ar.ArInster(TBLName,infoValue);

	}
	
	public boolean sourceManagerModify(SourceManagerBean ourceManagerBean,String id)
	{
		ArrayList infoValue = SourceManagerAssociate.associateInsert(ourceManagerBean);
		ArEdit ar 				  = new ArEdit(user, password, driverurl, serverport);
		return ar.ArModify(TBLName,id,infoValue);
	}
	
	public boolean sourceManagerDel(String id)
	{
		ArEdit ar 				  = new ArEdit(user, password, driverurl, serverport);
		return ar.ArDelete(TBLName,id);
	}
	
	/**
	 * 添加时排重
	 * @param SourceManagerBean
	 * @return boolean
	 */
	public boolean isDuplicate(SourceManagerBean sourceManagerBean)
	{
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("from SourceManagerpo sourceManagerpo where");
		  	sql.append(" sourceManagerpo.c650000005='"+sourceManagerBean.getsourcemanager_type()+"'");
		  	sql.append(" and sourceManagerpo.c650000007='"+sourceManagerBean.getsourcemanager_suserid()+"'");
		  	
		  	if(!"".equals(Function.nullString(sourceManagerBean.getsourcemanager_groupid())))
		  		sql.append(" and sourceManagerpo.c650000003='"+sourceManagerBean.getsourcemanager_groupid()+"'");
		  	if(!"".equals(Function.nullString(sourceManagerBean.getsourcemanager_userid())))
		  		sql.append(" and sourceManagerpo.c650000004='"+sourceManagerBean.getsourcemanager_userid()+"'");  
		  	
		  	sql.append(" and sourceManagerpo.c650000001='"+sourceManagerBean.getsourcemanager_sourceid()+"'");

		  	List list = HibernateDAO.queryObject(sql.toString());
		  		  	
		  	if(list.size()>0){	  		
		  		return true;
		  	}else{
		  		return false;
		  	}			
		}
		catch(Exception e)
		{
			logger.error("SourceManager.isDuplicate() 排重失败"+e.getMessage());
			return true;
		}
	}

}
