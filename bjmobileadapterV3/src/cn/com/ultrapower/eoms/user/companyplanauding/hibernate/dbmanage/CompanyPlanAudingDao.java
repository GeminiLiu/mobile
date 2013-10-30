package cn.com.ultrapower.eoms.user.companyplanauding.hibernate.dbmanage;

import java.util.List;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

/**
 * @author xuquanxing
 * 对Companyplanauding表的操作
 */
public class CompanyPlanAudingDao {
	List list = null;

	static final Logger logger = (Logger) Logger
			.getLogger(CompanyPlanAudingDao.class);

	/**
	 * @author xuquanxing
	 * @param id
	 * @return 根据id返回该对象
	 */
	public List getCompanyplanauding(String id) {
		String hql = "FROM CompanyplanaudingPo cpap WHERE cpap.auding_id='"
				+ id + "'";
		try {
			list = HibernateDAO.queryObject(hql);
		} catch (HibernateException e) {
			logger.error("CompanyPlanAudingDao:getCompanyplanauding："
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * @param obj
	 * @return
	 * 修改操作
	 */
	public boolean modifyCompanyplanauding(Object obj) {
		boolean isSuc = false;
		try {
			isSuc = HibernateDAO.modify(obj);
		} catch (HibernateException e) {
			logger.error("CompanyPlanAudingDao:modifyCompanyplanauding:"+e.getMessage());
		}
		return isSuc;
	}
	
	/**
	 * @param obj
	 * @return
	 * 删除操作
	 */
	public boolean deleteCompanyplanauding(Object obj){
		boolean isSuc = false;
		try {
			isSuc = HibernateDAO.deleteObject(obj);
		} catch (HibernateException e) {
			logger.error("CompanyPlanAudingDao:deleteCompanyplanauding:"+e.getMessage());
		}
		return isSuc;
	}
	
	/**
	 * @param obj
	 * @return
	 * 插入操作
	 */
	public boolean insertCompanyplanauding(Object obj){
		boolean isSuc = false;
		try {
			isSuc = HibernateDAO.insert(obj);
		} catch (HibernateException e) {
			logger.error("CompanyPlanAudingDao:deleteCompanyplanauding:"+e.getMessage());
		}
		return isSuc;
	}
}
