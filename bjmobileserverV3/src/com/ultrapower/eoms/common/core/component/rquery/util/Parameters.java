package com.ultrapower.eoms.common.core.component.rquery.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ultrapower.eoms.common.core.web.ActionContext;

public class Parameters {
	/**
	 * 将request参数、手工参数valuemap、传入的参数indirectValues合成一个参数变量
	 * @param indirectValues
	 */
	public static HashMap CollectionParameter(HashMap indirectValues)
	{
		HashMap var_hash=null;
		//this.custwhereElement=(Element)obj;
		HttpServletRequest request=ActionContext.getRequest();
		HashMap reqeustValues=null;//ruequest中的值
		HashMap reqeustCustomValues=null;//自定义的Map
		if(request!=null)
		{
			reqeustValues=new HashMap();
			Object objmap=request.getAttribute("valuemap");
			if(objmap!=null && objmap instanceof HashMap)
			{
				reqeustCustomValues=(HashMap)objmap;
			}
			if(indirectValues==null)
				indirectValues=new HashMap();
			Map map=request.getParameterMap();
			String strTemp;
			for(Iterator iter = map.entrySet().iterator();iter.hasNext();)
			{  
				Map.Entry element = (Map.Entry)iter.next();  
				Object strKey = element.getKey();  
				Object strObj = element.getValue();
				String[] strAry = (String[])strObj;
				strTemp="";
				for(int i=0;i<strAry.length;i++)
				{
					if(strTemp.equals(""))
						strTemp=strAry[0];
					else
						strTemp+=","+strAry[0];
				}
				reqeustValues.put(strKey, strTemp);
			}
		}
		if(reqeustValues!=null)
		{
			if(reqeustCustomValues!=null)
				reqeustValues.putAll(reqeustCustomValues);
			if(indirectValues!=null)
				reqeustValues.putAll(indirectValues);
			var_hash=reqeustValues;
		}
		else
		{
			var_hash=indirectValues;
		}
		return var_hash;
			
	}
}
