package cn.com.ultrapower.eoms.user.userinterface;

import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.eoms.user.userinterface.bean.SourceAttributeInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceAttributePram;

public class SourceAttributeInterface
{

	/**
	 * 根据资源属性信息Bean 查询资源ID，资源属性及资源属性值的List。
	 * 日期 2007-1-25
	 * @author wangyanguang
	 */
	public List getSourceAttList(SourceAttributePram sourcepram)
	{
		List returnList = null;
		SourceAttributeInterfaceSQL  intersql = new SourceAttributeInterfaceSQL();
		returnList = intersql.getSourceAttributeInfo(sourcepram);
		
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}

	
	public static void main(String args[])
	{
		SourceAttributeInterface sourceinterface = new SourceAttributeInterface();
		SourceAttributePram sourcepram = new SourceAttributePram();
		sourcepram.setSourceid("");
		List list = sourceinterface.getSourceAttList(sourcepram);
		for(Iterator it = list.iterator();it.hasNext();)
		{
			SourceAttributeInfo sourceattinfo = (SourceAttributeInfo)it.next();
			String sourceid = sourceattinfo.getSourceid();
			String attname  = sourceattinfo.getSourceattname();
			String attvalue = sourceattinfo.getAttvalue();
			System.out.println("资源ID："+ sourceid);
			System.out.println("资源属性名："+attname);
			System.out.println("资源属性值："+attvalue);
		}
	}
	
}
