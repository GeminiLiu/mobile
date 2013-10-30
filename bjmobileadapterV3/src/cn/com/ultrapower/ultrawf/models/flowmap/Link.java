package cn.com.ultrapower.ultrawf.models.flowmap;

import java.util.*;

import cn.com.ultrapower.ultrawf.share.flowmap.*;

/**
 * 操作Link的与数据库交互的类
 * 
 * @author BigMouse
 */
public class Link
{
	protected Link()
	{}
	
	/**
	 * 根据传入参数获得对Link表操作的类
	 * 
	 * @param linktype：环节的类型，Deal或者Auditing
	 * @return 对Link表操作的类
	 * @throws Exception
	 */
	public static Link getLink(String linktype) throws Exception
	{
		FlowMapConfig fmConfig = new FlowMapConfig();
		String strLinkClass = fmConfig.getLinkClassName(linktype);
		return (Link)Class.forName(strLinkClass).newInstance();
	}
	
	/**
	 * 获取环节的Link集合
	 * 
	 * @param sqlString：sql语句
	 * @throws Exception
	 */
	public List getLinkListInfo(String sqlString) throws Exception
	{
		throw new Exception();
	}
}
