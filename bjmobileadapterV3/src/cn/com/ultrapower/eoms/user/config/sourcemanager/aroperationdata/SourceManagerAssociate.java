package cn.com.ultrapower.eoms.user.config.sourcemanager.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.config.sourcemanager.bean.SourceManagerBean;

public class SourceManagerAssociate {
	
	static final Logger logger = (Logger) Logger.getLogger(SourceManagerAssociate.class);
	public SourceManagerAssociate()
	{
		
	}
    public static ArInfo setObject(String ID,String value,String flag)
    {
    	try
    	{
    		ArInfo sourceManagerbean = new ArInfo();
    		sourceManagerbean.setFieldID(ID);
    		sourceManagerbean.setValue(value);
    		sourceManagerbean.setFlag(flag);
    		return sourceManagerbean;
    	}
    	catch(Exception e)
    	{
    		logger.error("[000]SourceManagerAssociate.setObject() 字段信息封装bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	/**
	 * @author wuwenlong
	 * @CreatTime 2006-10-14
	 * @param peopleInfo
	 * @return backlist
	 * @把字段信息转换成ArrayList对象��������field��ArrayList
	 */
    public static ArrayList associateInsert(SourceManagerBean sourceManagerBean){
			
		String sourcemanager_sourceid		= sourceManagerBean.getsourcemanager_sourceid();
		String sourcemanager_query			= sourceManagerBean.getsourcemanager_query();
		String sourcemanager_groupid		= sourceManagerBean.getsourcemanager_groupid();
		String sourcemanager_userid			= sourceManagerBean.getsourcemanager_userid();
		String sourcemanager_type			= sourceManagerBean.getsourcemanager_type();
		String sourcemanager_sgroupid		= sourceManagerBean.getsourcemanager_sgroupid();
		String sourcemanager_suserid		= sourceManagerBean.getsourcemanager_suserid();
	
		ArInfo arinfosourcemanager_sourceid		= setObject("650000001",sourcemanager_sourceid,"1");
		ArInfo arinfosourcemanager_query		= setObject("650000002",sourcemanager_query,"1");
		ArInfo arinfosourcemanager_groupid		= setObject("650000003",sourcemanager_groupid,"1");
		ArInfo arinfosourcemanager_userid		= setObject("650000004",sourcemanager_userid,"1");
		ArInfo arinfosourcemanager_type			= setObject("650000005",sourcemanager_type,"1");
		ArInfo arinfosourcemanager_sgroupid		= setObject("650000006",sourcemanager_sgroupid,"1");
		ArInfo arinfosourcemanager_suserid		= setObject("650000007",sourcemanager_suserid,"1");

				
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(arinfosourcemanager_sourceid);
			backlist.add(arinfosourcemanager_query);
			backlist.add(arinfosourcemanager_groupid);
			backlist.add(arinfosourcemanager_userid);
			backlist.add(arinfosourcemanager_type);
			backlist.add(arinfosourcemanager_sgroupid);
			backlist.add(arinfosourcemanager_suserid);

			return backlist;
		}
		catch(Exception e)
		{
			logger.error("[000]SourceManagerAssociate.associateInsert() 字段信息转换ArrayList对象失败"+e.getMessage());
			return null;
		}
	}	
}
