package cn.com.ultrapower.eoms.user.userinterface;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceElementInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceInfoPram;

public class SourceInfoInterface
{
	static final Logger logger = (Logger) Logger.getLogger(SourceInfoInterface.class);
	/**
	 * 根据参数Bean查询出此用户有权限的资源信息。
	 * 日期 2007-1-24
	 * @author wangyanguang
	 */
	public List getSourceInfo(SourceInfoPram sourcepram)
	{
		List returnList 		= null;
		String userLoginName 	= "";
		String sourceParentid 	= "";
		String moduleName		= "";
		
		userLoginName 	= Function.nullString(sourcepram.getUserLoginName());
		sourceParentid 	= Function.nullString(sourcepram.getSourceParentid());
		moduleName		= Function.nullString(sourcepram.getMoudleName());
		
		if(userLoginName.equals(""))
		{
			logger.error("用户登陆名不能为空！");
			return null;
		}
		else
		{
			if(sourceParentid.equals(""))
			{
				//查询顶级节点
				if(moduleName.equals(""))
				{
					logger.error("模块名不能为空！");
					return null;
				}else
				{
					returnList = getRootElementInfo(sourcepram);
				}
				
			}
			else
			{
				//根据父节点查询子节点。
				returnList = getChildElementInfo(sourcepram);
			}
		}
		
		if(returnList==null)
		{
			return null;
		}
		else
		{
			return returnList;
		}
	}

	/**
	 * 根据参数Bean查询此用户有权限的顶能节点。
	 * 日期 2007-1-24
	 * @author wangyanguang
	 */
	public List getRootElementInfo(SourceInfoPram sourcepram)
	{
		List returnList = null;
		//查询顶级节点。
		SourceInfoInterfaceSQL sourcesql = new SourceInfoInterfaceSQL();
		returnList = sourcesql.getRootElementList(sourcepram);
		
		if(returnList==null)
		{
			return null;
		}
		else
		{
			return returnList;
		}
	}
	
	/**
	 * 根据参数Bean中资源父ID信息查询此用户有权限的子节点信息。
	 * 日期 2007-1-24
	 * @author wangyanguang
	 */
	public List getChildElementInfo(SourceInfoPram sourcepram)
	{
		List returnList = null;
		//查询子节点
		SourceInfoInterfaceSQL sourcesql = new SourceInfoInterfaceSQL();
		returnList = sourcesql.getChildElementList(sourcepram);
		
		if(returnList==null)
		{
			return null;
		}
		else
		{
			return returnList;
		}
	}
	
	public static void main(String[] args)
	{
		SourceInfoInterface sourceinterface = new SourceInfoInterface();
		
		SourceInfoPram sourcepram = new SourceInfoPram();
		sourcepram.setUserLoginName("wangyanguang");
		sourcepram.setGrandValue("");
		sourcepram.setSourceParentid("");
		sourcepram.setGrandValue("");
		sourcepram.setMoudleName("systemmanage");
		
		List list = sourceinterface.getSourceInfo(sourcepram);
		
		for(Iterator it = list.iterator();it.hasNext();)
		{
			SourceElementInfo sourceelementinfo = (SourceElementInfo)it.next();
			System.out.println("资源ID："+sourceelementinfo.getSourceid());
			System.out.println("资源名称："+sourceelementinfo.getSourcename());
			System.out.println("url:"+sourceelementinfo.getUrlvalue());
		}
	}
	
	
	
}
