package cn.com.ultrapower.eoms.user.userinterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.userinterface.bean.PortaletInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.PortaletSourcePram;

public class PortaletInterface
{

	static final Logger logger = (Logger) Logger.getLogger(PortaletInterface.class);
	
	/**
	 * 根据资源信息Bean 查询资源ID，资源属性及资源属性值的List。
	 * 日期 2007-1-30
	 * @author wangyanguang
	 */
	public List getSourceAttList(PortaletSourcePram sourcepram,String userLoginName)
	{
		List returnList = null;
		PortaletInterfaceSQL  intersql = new PortaletInterfaceSQL();
		returnList = intersql.getSourceAttributeInfo(sourcepram,userLoginName);
		
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	/**
	 * 首页Portalet显示查询
	 * 日期 2007-3-9
	 * @author wangyanguang
	 * @param userLoginName
	 * @return HashMap
	 */
	public LinkedHashMap getPortaletList(String userLoginName)
	{
		PortaletInterfaceSQL  intersql = new PortaletInterfaceSQL();
		LinkedHashMap hm = intersql.getPortaletSQl(userLoginName);
		return hm;
	}
	
	public static void main(String args[])
	{
		PortaletInterface sourceinterface = new PortaletInterface();
		LinkedHashMap hm = sourceinterface.getPortaletList("Demo");
		System.out.println(hm.size());
		
//		List list = (List)hm.get("ChangYongGongDanQiCao");
		List list = (List)hm.get("platformformyself");
		if(list!=null)
		{
			for(Iterator it=list.iterator();it.hasNext();)
			{
				PortaletInfo portaletinfo = (PortaletInfo)it.next();
				//System.out.print("资源中文名："+portaletinfo.getSourcecnname()+" ");
				//System.out.print("资源ID："+portaletinfo.getSourceid()+" ");
				
				//System.out.print("资源英文名："+portaletinfo.getSourcename()+" ");
				//System.out.print("资源URL值："+portaletinfo.getUrlvalue());
				//System.out.println("");
//				System.out.print(portaletinfo.getModuleid());
			}
		}
	}
	
}
