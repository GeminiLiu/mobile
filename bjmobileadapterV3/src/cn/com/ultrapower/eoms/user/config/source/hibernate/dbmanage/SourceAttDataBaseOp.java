package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigAttributeBean;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfigattribute;

public class SourceAttDataBaseOp {
	static final Logger logger = (Logger) Logger.getLogger(SourceAttDataBaseOp.class);

public Sourceconfigattribute beanValue(SourceConfigAttributeBean sourceConfigBean){
		
		Sourceconfigattribute sourceconfigattr  = new Sourceconfigattribute();
		
		sourceconfigattr.setSourceattId(sourceConfigBean.getSourceatt_id());
		sourceconfigattr.setSourceattSourceid(sourceConfigBean.getSourceatt_sourceid());
		sourceconfigattr.setSourceattCnname(sourceConfigBean.getSourceatt_cnname());
		sourceconfigattr.setSourceattEnname(sourceConfigBean.getSourceatt_enname());
		sourceconfigattr.setSourceattOrderby(sourceConfigBean.getSourceatt_orderby());
		sourceconfigattr.setSourceattDesc(sourceConfigBean.getSourceatt_desc());
		sourceconfigattr.setSourceattType(sourceConfigBean.getSourceatt_type());
		return sourceconfigattr;
	}
	public synchronized boolean sourceCnfInsert(SourceConfigAttributeBean sourceConfigBean){
		try{
			logger.info("---------t2  =ok");
			HibernateDAO hibernateDAO=new HibernateDAO();
			hibernateDAO.insert(beanValue(sourceConfigBean));
			logger.info("---------t2  =ok1");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("---------t2  = waring");
			return false;
		}
	}
	public synchronized boolean sourceCnfModify(SourceConfigAttributeBean sourceConfigBean){
		try{
			HibernateDAO hibernateDAO=new HibernateDAO();
			hibernateDAO.modify(beanValue(sourceConfigBean));
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
	}
	public synchronized boolean sourceCnfDel(String id){
		try{
			String Sql="from Sourceconfigattribute where SOURCEATT_ID="+id;
			HibernateDAO hibernateDAO=new HibernateDAO();
			
			if(hibernateDAO.deleteMulObjects1(Sql)){
				System.out.print("删除"+id+"ok!");
				return true;
			}else{
				System.out.print("删除"+id+"失败!");
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	public static void main(String[] args) {
		SourceConfigAttributeBean sourceinfo = new SourceConfigAttributeBean();
		sourceinfo.setSourceatt_cnname("wyg");
		sourceinfo.setSourceatt_desc("ultrapower");
		sourceinfo.setSourceatt_enname("wangyanguagu");
		sourceinfo.setSourceatt_id(new java.lang.Long(5555555));
		sourceinfo.setSourceatt_orderby(new java.lang.Long(3));
		sourceinfo.setSourceatt_sourceid(new java.lang.Long(4565656));
		SourceAttDataBaseOp souop = new SourceAttDataBaseOp();
		System.out.println(souop.sourceCnfInsert(sourceinfo));

	}
}
