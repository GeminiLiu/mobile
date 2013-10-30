package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;
import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.AbstractSourceattributeconfig;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceattributeconfig;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigBean;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceattributeconfigBean;

public class SourceAttributeConfigOp {
	static final Logger logger = (Logger) Logger.getLogger(SourceAttributeConfigOp.class);

	/**
	 * date 2006-11-10
	 * author shigang
	 * @param args
	 * @return void
	 */
	public Sourceattributeconfig beanValue(SourceattributeconfigBean sourceattributeconfigBean){
		
		//DutyOrgBean		  	 dutyorgbean   = new DutyOrgBean();
		Sourceattributeconfig sourceattributeconfig = new Sourceattributeconfig();

		sourceattributeconfig.setSourceattcfgId(sourceattributeconfigBean.getSourceattcfg_id());
		sourceattributeconfig.setSourceattcfgTypeflag(sourceattributeconfigBean.getSourceattcfg_typeflag());
		sourceattributeconfig.setSourceattcfgCnname(sourceattributeconfigBean.getSourceattcfg_cnname());
		sourceattributeconfig.setSourceattcfgEnname(sourceattributeconfigBean.getSourceattcfg_enname());
		sourceattributeconfig.setSourceattcfgType(sourceattributeconfigBean.getSourceattcfg_type());
		sourceattributeconfig.setSourceattcfgOrderby(sourceattributeconfigBean.getProurceattcfg_orderby());
		sourceattributeconfig.setSourceattcfgDesc(sourceattributeconfigBean.getSourceattcfg_desc());
		
		return sourceattributeconfig;
	}
	public synchronized boolean sourceCnfInsert(SourceattributeconfigBean sourceattributeconfigBean){
		try{
			HibernateDAO hibernateDAO=new HibernateDAO();
			hibernateDAO.insert(beanValue(sourceattributeconfigBean));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("sourceCnfInsert 366"+e.toString());
			return false;
		}
	}
	public synchronized boolean sourceCnfModify(SourceattributeconfigBean sourceattributeconfigBean){
		try{
			HibernateDAO hibernateDAO=new HibernateDAO();
			hibernateDAO.modify(beanValue(sourceattributeconfigBean));
				return true;
			}catch(Exception e){
				logger.info("sourceCnfModify 367"+e.toString());
				return false;
			}
	}
	public synchronized boolean sourceCnfDel(String id){
		try{
				String Sql="from Sourceattributeconfig where sourceattcfg_id="+id;
				HibernateDAO hibernateDAO=new HibernateDAO();
				
				if(hibernateDAO.deleteMulObjects1(Sql)){
					System.out.print("删除"+id+"ok!");
					return true;
				}else{
					System.out.print("删除"+id+"失败!");
					return false;
				}
			}catch(Exception e){
				logger.info("sourceCnfDel 368"+e.toString());
				return false;
			}
	}
	
	public static void main(String[] args) throws HibernateException {

			SourceAttributeConfigOp aa=new SourceAttributeConfigOp();
			aa.sourceCnfDel("2");
	}

}
