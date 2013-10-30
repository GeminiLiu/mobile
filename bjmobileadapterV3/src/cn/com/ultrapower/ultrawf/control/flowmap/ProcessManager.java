package cn.com.ultrapower.ultrawf.control.flowmap;

import java.util.*;

import cn.com.ultrapower.ultrawf.share.flowmap.FlowMapConfig;

/**
 * 对Process操作的类
 * 
 * @author BigMouse
 */
public class ProcessManager
{
	public static ProcessManager getManager(String processtype) throws Exception
	{
		FlowMapConfig fmConfig = new FlowMapConfig();
		String strProcessClass = fmConfig.getProcessManagerClassName(processtype);
		return (ProcessManager) Class.forName(strProcessClass).newInstance();
	}
	
	/**
	 * 查询出指定工单的所有环节
	 * 
	 * @param baseid：工单号
	 * @param baseschema：工单类别
	 * @param isArchive：是否为历史数据
	 * @param processtype：环节类型（Deal和Auditing）
	 * @return 封装指定工单的所有环节的List
	 * @throws Exception
	 */
	public List buildProcessList(String baseid, String baseschema, String baseTplID, String type, String beginProcess, int isArchive, String processtype) throws Exception
	{
		throw new Exception();
	}
	
	public List getProcessList(String baseid, String baseschema, int isArchive, String processtype) throws Exception
	{
		throw new Exception();
	}
	
	public List getLinkList(String baseid, String baseschema, String baseTplID, int isArchive, String processtype) throws Exception
	{
		throw new Exception();
	}
	
	public List getProcessLogList(String baseid, String baseschema, int isArchive, String processtype) throws Exception
	{
		throw new Exception();
	}
}
