package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;
import java.util.Iterator;

import cn.com.ultrapower.ultrawf.models.config.ConfigQueryDetail;
import cn.com.ultrapower.ultrawf.models.config.ParConfigQueryDetailModel;

public class ConfigQueryDetailManage {
 
	public List getList(ParConfigQueryDetailModel m_ParConfigQueryDetailModel,int p_PageNumber,int p_StepRow)
	{
		ConfigQueryDetail m_ConfigQueryDetail=new ConfigQueryDetail();
		return m_ConfigQueryDetail.getList(m_ParConfigQueryDetailModel,p_PageNumber,p_StepRow);
		
	}
    public boolean insert(ParConfigQueryDetailModel m_ParConfigQueryDetailModel)
    {
        ConfigQueryDetail m_ConfigQueryDetail=new ConfigQueryDetail();
        return m_ConfigQueryDetail.insert(m_ParConfigQueryDetailModel);
    }
    public int update(ParConfigQueryDetailModel m_ParConfigQueryDetailModel)
    {
        ConfigQueryDetail m_ConfigQueryDetail=new ConfigQueryDetail();
        return m_ConfigQueryDetail.update(m_ParConfigQueryDetailModel);
    }
    public int delete(List deleteList)
    {
        Iterator deleteIterator=(Iterator)deleteList.iterator();
        int deleteCount=deleteList.size();
        if(deleteCount==0)
            return deleteCount;
        ConfigQueryDetail m_ConfigQueryDetail=new ConfigQueryDetail();
        while(deleteIterator.hasNext())
        {
            String code=(String)deleteIterator.next();
            if(m_ConfigQueryDetail.delete(code))
                deleteCount--;
        }
        return deleteCount;
    }
    
    public int Delete(List p_ParConfigQueryDetailModelList)
    {
    	 ConfigQueryDetail m_ConfigQueryDetail=new ConfigQueryDetail();
    	 return m_ConfigQueryDetail.Delete(p_ParConfigQueryDetailModelList);
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
