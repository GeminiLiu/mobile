package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;
import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigBean;

public class SourceConfigOp {
	static final Logger logger = (Logger) Logger.getLogger(SourceConfigOp.class);

	/**
	 * date 2006-10-27
	 * author shigang
	 * @param args
	 * @return void
	 */
	public Sourceconfig beanValue(SourceConfigBean sourceConfigBean){
		
		//DutyOrgBean		  	 dutyorgbean   = new DutyOrgBean();
		Sourceconfig sourceconfig  = new Sourceconfig();

		sourceconfig.setSourceId(sourceConfigBean.getSource_id());
		sourceconfig.setSourceParentid(sourceConfigBean.getSource_parentid());
		sourceconfig.setSourceCnname(sourceConfigBean.getSource_cnname());
		sourceconfig.setSourceName(sourceConfigBean.getSource_name());
		sourceconfig.setSourceModule(sourceConfigBean.getSource_module());
		
		sourceconfig.setSourceOrderby(sourceConfigBean.getSource_orderby());
		sourceconfig.setSourceDesc(sourceConfigBean.getSource_desc());
		
		sourceconfig.setSourceType(sourceConfigBean.getSource_type());
		sourceconfig.setSourceFieldtype(sourceConfigBean.getSource_fieldtype());
		sourceconfig.setSourceIsleft(sourceConfigBean.getSource_isleft());
		sourceconfig.setSourceUrl(sourceConfigBean.getSource_url());
		
		return sourceconfig;
	}
	public synchronized boolean sourceCnfInsert(SourceConfigBean sourceConfigBean){
		try{
			HibernateDAO hibernateDAO=new HibernateDAO();
			hibernateDAO.insert(beanValue(sourceConfigBean));
			return true;
		}catch(Exception e){
			logger.info("394 sourceCnfInsert:"+e.toString());
			return false;
		}
	}
	public synchronized boolean sourceCnfModify(SourceConfigBean sourceConfigBean){
		try{
			HibernateDAO hibernateDAO=new HibernateDAO();
			hibernateDAO.modify(beanValue(sourceConfigBean));
				return true;
			}catch(Exception e){
				logger.info("395 sourceCnfModify:"+e.toString());
				return false;
			}
	}
	public synchronized boolean sourceCnfDel(String id){
		try{
				String Sql="from Sourceconfig where source_id="+id;
				HibernateDAO hibernateDAO=new HibernateDAO();
				
				if(hibernateDAO.deleteMulObjects1(Sql)){
					return true;
				}else{
					return false;
				}
			}catch(Exception e){
				logger.info("396 sourceCnfDel:"+e.toString());
				return false;
			}
	}
	
	public static void main(String[] args) throws HibernateException {

			SourceConfigOp aa=new SourceConfigOp();
			aa.sourceCnfDel("2");
	}

}
