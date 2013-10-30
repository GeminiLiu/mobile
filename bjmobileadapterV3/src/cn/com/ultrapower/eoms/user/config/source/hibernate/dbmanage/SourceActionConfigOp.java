
package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;
import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceActionConfigBean;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceactionconfig;

public class SourceActionConfigOp {
	static final Logger logger = (Logger) Logger.getLogger(SourceActionConfigOp.class);

	/**
	 * date 2007-1-23
	 * author shigang
	 * @param args
	 * @return void
	 */
	public Sourceactionconfig beanValue(SourceActionConfigBean sourceattributeconfigBean){
		Sourceactionconfig Sourceactionconfig=new Sourceactionconfig();
		Sourceactionconfig.setId(Function.javaLong(sourceattributeconfigBean.getId()));
		Sourceactionconfig.setSourceactionFieldvalue(sourceattributeconfigBean.getSourceaction_fieldValue());
		Sourceactionconfig.setSourceactionNumvalue(sourceattributeconfigBean.getSourceaction_numValue());
		Sourceactionconfig.setSourceactionOrderby(Function.javaLong(sourceattributeconfigBean.getSourceaction_orderBy()));
		Sourceactionconfig.setSourceactionTypeflag(sourceattributeconfigBean.getSourceaction_typeflag());
		return Sourceactionconfig;
	}
	public synchronized boolean sourceActionCnfInsert(SourceActionConfigBean SourceActionConfigBean){
		try{
			 
			HibernateDAO.insert(beanValue(SourceActionConfigBean));
			return true;
		}catch(Exception e){
			logger.info("391:sourceActionCnfInsert==="+e.toString());
			return false;
		}
	}
	public synchronized boolean sourceActionCnfModify(SourceActionConfigBean SourceActionConfigBean){
		try{
			 
			HibernateDAO.modify(beanValue(SourceActionConfigBean));
				return true;
			}catch(Exception e){
				logger.info("392:sourceActionCnfModify==="+e.toString());
				return false;
			}
	}
	public synchronized boolean sourceActionCnfDel(String id){
		try{
				String Sql="from Sourceactionconfig t where t.id="+id;
				HibernateDAO hibernateDAO=new HibernateDAO();
				if(hibernateDAO.deleteMulObjects1(Sql)){
					return true;
				}else{
					return false;
				}
			}catch(Exception e){
				logger.info("393:sourceActionCnfDel==="+e.toString());
				return false;
			}
	}
}
