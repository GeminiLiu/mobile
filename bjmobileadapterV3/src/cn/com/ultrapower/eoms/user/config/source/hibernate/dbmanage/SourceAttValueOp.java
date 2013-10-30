package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceattributevalue;

/**
 * <p>Description:使用hibernate进行数据库操作<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-30
 */
public class SourceAttValueOp {
	
	static final Logger logger = (Logger) Logger.getLogger(SourceAttValueOp.class);
	
	/**
	 * <p>Description:根据资源类别的po执行插入操作<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-30
	 * @param sourceAttributeValue
	 * @return boolean
	 */
	public synchronized boolean sourceAttValueInsert(Sourceattributevalue sourceAttributeValue)
	{
		try
		{
			HibernateDAO.insert(sourceAttributeValue);
			return true;
		}
		catch(Exception e)
		{
			logger.error("[482]SourceAttValueOp.sourceAttValueInsert() 根据资源类别的po执行插入操作失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据资源类别的po执行修改操作<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-31
	 * @param sourceAttributeValue
	 * @return boolean
	 */
	public synchronized boolean sourceAttValueModify(Sourceattributevalue sourceAttributeValue)
	{
		try
		{			
			HibernateDAO.modify(sourceAttributeValue);
			return true;
		}
		catch(Exception e)
		{
			logger.error("[483]SourceAttValueOp.sourceAttValueModify() 根据资源类别的po执行修改操作失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据sourceattvalueBelongrow字段执行删除操作<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-30
	 * @param sourceValueId
	 * @return boolean
	 */
	public synchronized boolean sourceAttValueDelete(String sourceAttValueBelongrow)
	{
		try
		{
			String Sql="from Sourceattributevalue a where a.sourceattvalueBelongrow="+sourceAttValueBelongrow;
		
			if(HibernateDAO.deleteMulObjects1(Sql))
			{
				logger.info("删除"+sourceAttValueBelongrow+"成功");
				return true;
			}
			else
			{
				logger.info("删除"+sourceAttValueBelongrow+"失败");
				return false;
			}
		}
		catch(Exception e)
		{
			logger.error("[484]SourceAttValueOp.sourceAttValueDelete() 根据sourceattvalueBelongrow字段执行删除操作失败"+e.getMessage());
			return false;
		}
	}
	
}
