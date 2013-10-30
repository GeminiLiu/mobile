package cn.com.ultrapower.ultrawf.models.flowmap;

import java.util.*;

/**
 * 环节Process信息的实体类
 * 
 * @author BigMouse
 */
public class ProcessInfo
{
	protected String processID;

	public String getProcessID()
	{
		return processID;
	}

	public void setProcessID(String processID)
	{
		this.processID = processID;
	}

	protected String baseID;

	public String getBaseID()
	{
		return baseID;
	}

	public void setBaseID(String baseID)
	{
		this.baseID = baseID;
	}

	protected String baseSchema;

	public String getBaseSchema()
	{
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	protected String beginPhaseNo;

	public String getBeginPhaseNo()
	{
		return beginPhaseNo;
	}

	public void setBeginPhaseNo(String beginPhaseNo)
	{
		this.beginPhaseNo = beginPhaseNo;
	}
	
	protected String flowMapNo;
	
	public String getFlowMapNo()
	{
		return flowMapNo;
	}
	
	public void setFlowMapNo(String flowMapNo)
	{
		this.flowMapNo = flowMapNo;
	}

	protected String phaseNo;

	public String getPhaseNo()
	{
		return phaseNo;
	}

	public void setPhaseNo(String phaseNo)
	{
		this.phaseNo = phaseNo;
	}

	protected String prevPhaseNo;

	public String getPrevPhaseNo()
	{
		return prevPhaseNo;
	}

	public void setPrevPhaseNo(String prevPhaseNo)
	{
		this.prevPhaseNo = prevPhaseNo;
	}

	protected String assginee;

	public String getAssginee()
	{
		return assginee;
	}

	public void setAssginee(String assginee)
	{
		this.assginee = assginee;
	}
	
	protected String assgineeID;

	public String getAssgineeID()
	{
		return assgineeID;
	}

	public void setAssgineeID(String assgineeID)
	{
		this.assgineeID = assgineeID;
	}

	protected String group;

	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}
	
	protected String groupID;

	public String getGroupID()
	{
		return groupID;
	}

	public void setGroupID(String groupID)
	{
		this.groupID = groupID;
	}

	protected String dealer;

	public String getDealer()
	{
		return dealer;
	}

	public void setDealer(String dealer)
	{
		this.dealer = dealer;
	}

	protected String dealerID;

	public String getDealerID()
	{
		return dealerID;
	}

	public void setDealerID(String dealerID)
	{
		this.dealerID = dealerID;
	}

	protected String processStatus;

	public String getProcessStatus()
	{
		return processStatus;
	}

	public void setProcessStatus(String processStatus)
	{
		this.processStatus = processStatus;
	}

	protected int stDate;

	public int getStDate()
	{
		return stDate;
	}

	public void setStDate(int stDate)
	{
		this.stDate = stDate;
	}

	protected int bgDate;

	public int getBgDate()
	{
		return bgDate;
	}

	public void setBgDate(int bgDate)
	{
		this.bgDate = bgDate;
	}

	protected int edDate;

	public int getEdDate()
	{
		return edDate;
	}

	public void setEdDate(int edDate)
	{
		this.edDate = edDate;
	}

	protected String desc;

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	protected int flagType;

	public int getFlagType()
	{
		return flagType;
	}

	public void setFlagType(int flagType)
	{
		this.flagType = flagType;
	}

	protected int flagActive;

	public int getFlagActive()
	{
		return flagActive;
	}

	public void setFlagActive(int flagActive)
	{
		this.flagActive = flagActive;
	}

	protected int flagDuplicated;

	public int getFlagDuplicated()
	{
		return flagDuplicated;
	}

	public void setFlagDuplicated(int flagDuplicated)
	{
		this.flagDuplicated = flagDuplicated;
	}

	protected int flagPredefined;

	public int getFlagPredefined()
	{
		return flagPredefined;
	}

	public void setFlagPredefined(int flagPredefined)
	{
		this.flagPredefined = flagPredefined;
	}

	protected String processType;

	public String getProcessType()
	{
		return processType;
	}

	public void setProcessType(String processType)
	{
		this.processType = processType;
	}

	protected List processLogs = new ArrayList();

	public List getProcessLogs()
	{
		return processLogs;
	}

	/**
	 * 添加Process的日志
	 * 
	 * @param pLog：Process的日志
	 */
	public void addProcessLog(ProcessLogInfo pLog)
	{
		// 标识下边的插入是否成功
		boolean isAdd = false;

		// 遍历日志的List，把传入参数插入到List中日志的StDate大于参数的StDate的索引处
		for (int i = 0; i < processLogs.size(); i++)
		{
			ProcessLogInfo pLogElement = (ProcessLogInfo) processLogs.get(i);
			if (pLog.getStDate() < pLogElement.getStDate())
			{
				processLogs.add(i, pLog);
				isAdd = true;
				break;
			}
		}
		// 如果没有，则将传入参数插入到List的最后一个
		if (!isAdd)
		{
			processLogs.add(pLog);
		}
	}
}
