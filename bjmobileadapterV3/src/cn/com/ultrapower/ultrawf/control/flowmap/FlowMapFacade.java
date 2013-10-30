package cn.com.ultrapower.ultrawf.control.flowmap;

import java.util.*;

import cn.com.ultrapower.ultrawf.control.flowmap.ProcessDrawManager;
import cn.com.ultrapower.ultrawf.control.flowmap.ProcessManager;
import cn.com.ultrapower.ultrawf.models.process.*;
import cn.com.ultrapower.ultrawf.share.flowmap.*;

public class FlowMapFacade
{
	/**
	 * 画处理流程的流程图
	 * 
	 * @param baseID：工单ID
	 * @param baseSchema：工单类别
	 * @param processType：流程类别
	 * @param isArchive：是否为历史数据
	 * @return 处理流程的流程图字符串
	 */
	public String draw(String baseID, String baseSchema, String baseTplID, String type, String beginProcess, String processType, int isArchive)
	{
		String strFlowMap = ""; 
		try
		{
			ProcessManager pm = ProcessManager.getManager(processType);
			ProcessDrawManager pdManager = new ProcessDrawManager();
			List processList = pm.buildProcessList(baseID, baseSchema, baseTplID, type, beginProcess, isArchive, processType);
			for(Iterator it = processList.iterator(); it.hasNext();)
			{
				ProcessList pList = (ProcessList)it.next();
				pList.setTplProcessTip();
			}
			
			strFlowMap = pdManager.draw(processList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strFlowMap;
	}

	/**
	 * 获取工单信息
	 * 
	 * @param baseID：工单ID
	 * @param baseSchema：工单类别
	 * @param isArchive：是否为历史数据
	 * @return 工单信息
	 */
	public BaseInforModel getBaseInfomation(String baseID, String baseSchema, int isArchive)
	{
		Base base = new Base();
		BaseInforModel bModel = base.getBaseModel(baseID, baseSchema, isArchive);
		return bModel;
	}

	/**
	 * 获取环节状态集合
	 * 
	 * @return 环节状态集合
	 * @throws Exception
	 */
	public List getProcessStatusList() throws Exception
	{
		FlowMapConfig fmConfig = new FlowMapConfig();
		ProcessStatusList psList = fmConfig.getProcessStatusList();
		return psList.getProcessStatusModelList();
	}
}
