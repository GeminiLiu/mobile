package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceattributevalue;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfigattribute;

/**
 * <p>Description:获得资源类别属性值记录类<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-28
 */
public class GetAttValueInfoList {
	
	static final Logger logger = (Logger) Logger.getLogger(GetAttValueInfoList.class);

	/**
	 * <p>Description:根据资源类别ID查找属性值<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-28
	 * @param sourceId
	 * @return List
	 */
	public List findAttributeValue(String sourceId)
	{
		try
		{
			 //String sql	= "select a.sourceattvalueId,a.sourceattvalueAttid,a.sourceattvalueBelongrow,a.sourceattvalueValue from Sourceattributevalue a,Sourceconfigattribute b where a.sourceattvalueAttid = b.sourceattId and b.sourceattSourceid='"+sourceId+"' group by a.sourceattvalueBelongrow,a.sourceattvalueId,a.sourceattvalueAttid,a.sourceattvalueValue";
			String sql	= "select a.sourceattvalueId,a.sourceattvalueAttid,a.sourceattvalueBelongrow,a.sourceattvalueValue from Sourceattributevalue a,Sourceconfigattribute b where a.sourceattvalueAttid = b.sourceattId and b.sourceattSourceid='"+sourceId+"' order by a.sourceattvalueBelongrow";
			List list	= HibernateDAO.queryObject(sql);
			return list;
		}
		catch(Exception e)
		{
			logger.error("[475]GetAttValueInfoList.findAttributeValue() 根据资源类别ID查找属性值失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据资源类别ID查找资源类别属性<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-30
	 * @param sourceId
	 * @return List
	 */
	public List findConfigAttValue(String sourceId)
	{
		try
		{
			String sql	= "select c.sourceattId,c.sourceattCnname from Sourceconfigattribute c where c.sourceattSourceid ='"+sourceId+"' group by c.sourceattId,c.sourceattCnname";
			List list	= HibernateDAO.queryObject(sql);
			return list;
		}
		catch(Exception e)
		{
			logger.error("[476]GetAttValueInfoList.findConfigAttValue() 根据资源类别ID查找资源类别属性失败"+e.getMessage());
			return null;
		}		
	}
	
	/**
	 * <p>Description:根据资源类别ID查找资源类别属性中文名<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-31
	 * @param sourceAttId
	 * @return String
	 */
	public String findAttValue(String sourceAttId)
	{
		try
		{
			Sourceconfigattribute configAttribute	= (Sourceconfigattribute)HibernateDAO.load(Sourceconfigattribute.class,new Long(sourceAttId));
			String cName							= configAttribute.getSourceattCnname();
			return cName;
		}
		catch(Exception e)
		{
			logger.error("[477]GetAttValueInfoList.findAttValue() 根据资源类别ID查找资源类别属性中文名失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据资源属性ID和资源属性所属值查找资源类别属性值对象<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-9
	 * @param id
	 * @param belongRow
	 * @return String
	 */
	public Sourceattributevalue findAttValue(String attId,String belongRow)
	{
		String sql	= "from Sourceattributevalue c where c.sourceattvalueBelongrow ='"+belongRow+"' and c.sourceattvalueAttid='"+attId+"'";
		try
		{
			List list = HibernateDAO.queryObject(sql);
			if(list!=null)
			{
				for(Iterator it=list.iterator();it.hasNext();)
				{
					Sourceattributevalue attributeValue = (Sourceattributevalue)it.next();
					return attributeValue;
				}
				return null;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("[478]GetAttValueInfoList.findAttValue() 根据资源属性ID和资源属性所属值查找资源类别属性值对象失败"+e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * <p>Description:根据资源类别所属查找表Sourceattributevalue数据<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-3
	 * @param sourcebelongrow
	 * @return List
	 */
	public List findModifyAttributeValue(String sourcebelongrow)
	{
		try
		{
			 String sql	= "from Sourceattributevalue a where a.sourceattvalueBelongrow = "+sourcebelongrow;
			 List list	= HibernateDAO.queryObject(sql);
		     return list;
		 }
		catch(Exception e)
		{
			logger.error("[479]GetAttValueInfoList.findModifyAttributeValue() 根据资源类别所属查找表Sourceattributevalue数据失败"+e.getMessage());
			return null;
		}
	}
	
}