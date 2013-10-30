package cn.com.ultrapower.ultrawf.models.flowmap;

import java.util.*;

import cn.com.ultrapower.ultrawf.share.flowmap.*;

/**
 * 操作Process的与数据库交互的类
 * 
 * @author BigMouse
 */
public class Process
{
	protected Process()
	{}

	/**
	 * 根据传入参数获得对Process表操作的类
	 * 
	 * @param processtype：环节的类型，Deal或者Auditing
	 * @return 对Process表操作的类
	 * @throws Exception
	 */
	public static Process getProcess(String processtype) throws Exception
	{
		FlowMapConfig fmConfig = new FlowMapConfig();
		String strProcessClass = fmConfig.getProcessClassName(processtype);
		return (Process) Class.forName(strProcessClass).newInstance();
	}

	/**
	 * 获取环节的Process集合
	 * 
	 * @param sqlString：sql语句
	 * @throws Exception
	 */
	public List getProcessInfoList(String sqlString) throws Exception
	{
		throw new Exception();
	}

	/**
	 * 获取环节的日志信息
	 * 
	 * @param isArchive：是否为历史数据
	 * @return 环节的日志信息
	 */
	public List getProcessLogList(int isArchive) throws Exception
	{
		throw new Exception();
	}
}
