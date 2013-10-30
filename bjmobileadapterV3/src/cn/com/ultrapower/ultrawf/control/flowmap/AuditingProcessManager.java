package cn.com.ultrapower.ultrawf.control.flowmap;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.flowmap.*;
import cn.com.ultrapower.ultrawf.models.flowmap.Process;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

/**
 * 对AuditingProcess操作的类
 * 
 * @author BigMouse
 */
public class AuditingProcessManager extends ProcessManager
{
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
		// 查询出ProcessInfo的List
		List pInfoList = getProcessList(baseid, baseschema, isArchive, processtype);

		// 查询出所有的Log信息
		List pLogInfoList = getProcessLogList(baseid, baseschema, isArchive, processtype);

		List processList = new ArrayList();

		// 创建ProcessList，并将所有的ProcessInfo都放进去
		for (Iterator it = pInfoList.iterator(); it.hasNext();)
		{
			ProcessInfo apInfo = (ProcessInfo) it.next();
			apInfo.setFlowMapNo(((AuditingProcessInfo)apInfo).getAuditingWayNo());
			boolean isAdd = false;

			// 将起始环节号相同的环节放到一个ProcessList里边
			for (int i = 0; i < processList.size(); i++)
			{
				ProcessList pList = (ProcessList) processList.get(i);
				ProcessModel pModel = (ProcessModel) pList.getProcessList().get(0);
				String beginPhaseNo = pModel.getBeginPhaseNo();
				String flowMapNo = pModel.getFlowMapNo();
				if (beginPhaseNo.equals(apInfo.getBeginPhaseNo()) && flowMapNo.equals(apInfo.getFlowMapNo()))
				{
					pList.addProcessInfo(apInfo);
					processList.set(i, pList);
					isAdd = true;
					break;
				}
			}

			// 如果没有相同的，则追加一条记录
			if (!isAdd)
			{
				ProcessList pList = new ProcessList();
				pList.setProcessLogInfoList(pLogInfoList);
				pList.addProcessInfo(apInfo);
				processList.add(pList);
			}
		}
		System.out.println("Read end.");
		return processList;
	}

	public List getProcessList(String baseid, String baseschema, int isArchive, String processtype) throws Exception
	{
		RemedyDBOp rdbop = new RemedyDBOp();
        String baseform;
        if(isArchive == 0)
        {
        	baseform = rdbop.GetRemedyTableName("WF:App_AuditingProcess");
        }
        else
        {
        	baseform = rdbop.GetRemedyTableName("WF:App_AuditingProcess_Archive");
        }
		
		// 查询出ProcessInfo的List
		List pInfoList = Process.getProcess(processtype).getProcessInfoList("SELECT * FROM " + baseform + " WHERE C700020001 = '" + baseid + "' AND C700020002 = '" + baseschema + "' AND C700020011 LIKE '%' ORDER BY C700020015 ASC");
		return pInfoList;
	}
	
	public List getLinkList(String baseid, String baseschema, String baseTplID, int isArchive, String processtype) throws Exception
	{
		return null;
	}
	
	public List getProcessLogList(String baseid, String baseschema, int isArchive, String processtype) throws Exception
	{
		RemedyDBOp rdbop = new RemedyDBOp();
        String baseform;
		// 查询出所有的Log信息
		ProcessLog pLog = new ProcessLog();
		
		if(isArchive == 0)
        {
        	baseform = rdbop.GetRemedyTableName("WF:App_AuditingProcessLog");
        }
        else
        {
        	baseform = rdbop.GetRemedyTableName("WF:App_AuditingProcessLog_Archive");
        }
		List pLogInfoList = pLog.getProcessLogInfoList("SELECT C1, C700020401, C700020402, C700020403, C700020404, C700020405, C700020406 FROM " + baseform + " WHERE C700020407 = '" + baseid + "' AND C700020408 = '" + baseschema + "'");
		return pLogInfoList;
	}
}
