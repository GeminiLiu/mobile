package cn.com.ultrapower.eoms.user.config.menu.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.config.menu.bean.GrandActionConfigBean;

public class GrandActionConfigAssociate {

	static final Logger logger = (Logger) Logger.getLogger(GrandActionConfigAssociate.class);
	
	public static ArInfo setObject(String ID, String value, String flag)
	{
		try 
		{
			ArInfo arpeopleInfo = new ArInfo();
			arpeopleInfo.setFieldID(ID);
			arpeopleInfo.setValue(value);
			arpeopleInfo.setFlag(flag);
			return arpeopleInfo;
		} catch (Exception e) 
		{
			logger.info("[ArInfo error:GrandActionConfig "+e.getMessage());
			return null;
		}
	}
	
	public static ArrayList associateCondition(GrandActionConfigBean grandactionconfigbean) 
	{
		
		String tmp_fieldID 				= "";
		String tmp_fieldValue			= "";
		String tmp_numValue 			= "";
		String tmp_orderby				= "";
		try
		{
			
			tmp_fieldID          	= grandactionconfigbean.getDropDownConf_FieldID();
			tmp_fieldValue          = grandactionconfigbean.getDropDownConf_FieldValue();
			tmp_numValue          	= grandactionconfigbean.getDropDownConf_NumValue();
			tmp_orderby          	= grandactionconfigbean.getDropDownConf_OrderBy();
		}catch(Exception e)
		{
			logger.info("?�������");
		}
		
		ArInfo fieldIDInfo          = setObject("620000032", tmp_fieldID, "1");
		ArInfo typeInfo             = setObject("620000033", tmp_fieldValue, "1");
		ArInfo roleInfo             = setObject("620000034", tmp_numValue, "1");
		ArInfo moduleInfo           = setObject("620000035", tmp_orderby, "1");
		
		ArrayList returnList        = new ArrayList();
		returnList.add(fieldIDInfo);
		returnList.add(typeInfo);
		returnList.add(roleInfo);
		returnList.add(moduleInfo);
		
		return returnList;
	}
}
