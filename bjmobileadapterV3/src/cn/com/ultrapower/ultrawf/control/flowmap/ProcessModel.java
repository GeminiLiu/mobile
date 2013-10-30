package cn.com.ultrapower.ultrawf.control.flowmap;

import java.util.*;

import cn.com.ultrapower.system.util.FormatTime;
import cn.com.ultrapower.ultrawf.models.design.GroupUserInterfaceTmp;
import cn.com.ultrapower.ultrawf.models.design.RoleUserHandler;
import cn.com.ultrapower.ultrawf.models.design.UserModel;
import cn.com.ultrapower.ultrawf.models.flowmap.ProcessInfo;
import cn.com.ultrapower.ultrawf.models.flowmap.ProcessLogInfo;

/**
 * 存放一个环节的所有Process的类
 * 
 * @author BigMouse
 */
public class ProcessModel
{
	private String phaseNo;

	protected String getPhaseNo()
	{
		return phaseNo;
	}
	
	protected void setPhaseNo(String phaseNo)
	{
		this.phaseNo = phaseNo;
	}
	
	private String prevPhaseNo;
	
	protected String getPrevPhaseNo()
	{
		return prevPhaseNo;
	}
	
	protected void setPrevPhaseNo(String prevPhaseNo)
	{
		this.prevPhaseNo = prevPhaseNo;
	}

	protected int getStDate()
	{
		return processInfo.getStDate();
	}
	
	private String beginPhaseNo;
	
	protected String getBeginPhaseNo()
	{
		return beginPhaseNo;
	}
	
	private String flowMapNo;
	
	protected String getFlowMapNo()
	{
		return flowMapNo;
	}

	private boolean hasfreeProcess = false;
	
	protected void setHasFreeProcess(boolean hasfreeProcess)
	{
		this.hasfreeProcess = hasfreeProcess;
	}
	
	protected boolean getHasFreeProcess()
	{
		return hasfreeProcess;
	}
	
	/**
	 * 环节的Process
	 */
	private ProcessInfo processInfo;

	public ProcessInfo getProcess()
	{
		return processInfo;
	}

	/**
	 * 环节的复制品的Process
	 */
	private List duplicatedProcess = new ArrayList();
	
	public List getDuplicatedProcess()
	{
		return duplicatedProcess;
	}
	
	private int flagPredefined = 0;
	
	protected int getFlagPredefined()
	{
		return flagPredefined;
	}

	/**
	 * 环节的日志
	 */
	private List processLogs = new ArrayList();

	/**
	 * 添加复制品Process
	 * 
	 * @param pInfo：复制品的Process
	 */
	protected void addProcessInfo(ProcessInfo pInfo)
	{
		// 判断是否为复制品
		if (pInfo.getFlagDuplicated() == 0)
		{
			// 如果不是复制品，添加为Process
			processInfo = pInfo;
		}
		else
		{
			// 标识下边的插入是否成功
			boolean isAdd = false;

			// 遍历复制品Process的List，把传入参数插入到List中Process的StDate大于参数的StDate的索引处
			for (int i = 0; i < duplicatedProcess.size(); i++)
			{
				ProcessInfo pInfoElement = (ProcessInfo) duplicatedProcess.get(i);
				if (pInfo.getStDate() < pInfoElement.getStDate())
				{
					duplicatedProcess.add(i, pInfo);
					isAdd = true;
					break;
				}
			}
			// 如果没有，则将传入参数插入到List的最后一个
			if (!isAdd)
			{
				duplicatedProcess.add(pInfo);
			}
		}
		beginPhaseNo = pInfo.getBeginPhaseNo();
		phaseNo = pInfo.getPhaseNo();
		prevPhaseNo = pInfo.getPrevPhaseNo();
		flowMapNo = pInfo.getFlowMapNo();
		flagPredefined = pInfo.getFlagPredefined();
	}

	/**
	 * 添加Process的日志
	 * 
	 * @param pLog：Process的日志
	 */
	protected void addProcessLog(ProcessLogInfo pLog)
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
	
	public String getProcessLogXml()
	{
		StringBuffer processInfo = new StringBuffer();
		processInfo.append(getProcessInfoLogXml(getProcess()));
		for (Iterator it = getDuplicatedProcess().iterator(); it.hasNext();)
		{
			// 画复制品环节信息
			processInfo.append(getProcessInfoLogXml((ProcessInfo) it.next()));
		}
		return processInfo.toString();
	}
	
	private StringBuffer getProcessInfoLogXml(ProcessInfo pInfo)
	{
		StringBuffer templite = new StringBuffer();

		// 环节信息
		templite.append("<ProcessInfo>");

		templite.append("<Info>");
		
		templite.append("<Status>" + pInfo.getProcessStatus() + "</Status>");

		// 内容
		if (pInfo.getDesc() != null)
		{
			templite.append("<Desc>" + pInfo.getDesc() + "</Desc>");
		}
		else
		{
			templite.append("<Desc> </Desc>");
		}

		// 执行人

		if (pInfo.getDealer() != null)
		{
			templite.append("<Dealer>" + pInfo.getDealer() + "</Dealer>");
			templite.append("<PreDealer></PreDealer>");
		}
		else
		{
			if(pInfo.getAssginee() != null)
			{
				templite.append("<Dealer>" + pInfo.getAssginee() + "</Dealer>");
				templite.append("<PreDealer></PreDealer>");
			}
			else if(pInfo.getGroup() != null)
			{
				List<UserModel> userList = new ArrayList<UserModel>();
				if(pInfo.getGroupID().length() > 6)
				{
					RoleUserHandler ruHandler = new RoleUserHandler();
					userList = ruHandler.getUsers(pInfo.getGroupID());
				}
				else
				{
					GroupUserInterfaceTmp guit = new GroupUserInterfaceTmp();
					userList = guit.getUserList(pInfo.getGroupID());
				}
				
				templite.append("<Dealer> </Dealer>");
				templite.append("<PreDealer>");
				for(Iterator<UserModel> it = userList.iterator(); it.hasNext();)
				{
					templite.append(it.next().getUserName() + ";");
				}

				templite.append("</PreDealer>");
			}
			else
			{
				templite.append("<Dealer> </Dealer>");
				templite.append("<PreDealer></PreDealer>");
			}
		}

		// 开始时间

		try
		{
			templite.append("<StDate>" + FormatTime.formatIntToDateString(pInfo.getStDate()) + "</StDate>");
		}
		catch (Exception e)
		{
			templite.append("<StDate> </StDate>");
		}

		// 处理时间
		try
		{
			templite.append("<DealDate>" + FormatTime.formatIntToDateString(pInfo.getBgDate()) + "</DealDate>");
		}
		catch (Exception e)
		{
			templite.append("<DealDate> </DealDate>");
		}

		// 完成时间
		try
		{
			templite.append("<FinishDate>" + FormatTime.formatIntToDateString(pInfo.getEdDate()) + "</FinishDate>");
		}
		catch (Exception e)
		{
			templite.append("<FinishDate> </FinishDate>");
		}
		templite.append("</Info>");

		// 日志信息
		templite.append("<Log>");

		// 遍历日志信息
		List pLogList = pInfo.getProcessLogs();
		for (Iterator it = pLogList.iterator(); it.hasNext();)
		{
			ProcessLogInfo pLogInfo = (ProcessLogInfo) it.next();
			templite.append("<LogInfo>");

			// Act
			templite.append("<Act>");
			templite.append(pLogInfo.getAct());
			templite.append("</Act>");

			// LogUser
			templite.append("<LogUser>");
			templite.append(pLogInfo.getLogUser());
			templite.append("</LogUser>");

			// StDate
			templite.append("<StDate>");
			templite.append(FormatTime.formatIntToDateString(pLogInfo.getStDate()));
			templite.append("</StDate>");

			// Result
			templite.append("<Result>");
			templite.append(pLogInfo.getResult().replace('\n', ' ').replace('\r', ' '));
			templite.append("</Result>");
			templite.append("</LogInfo>");
		}
		templite.append("</Log>");
		templite.append("</ProcessInfo>");
		return templite;
	}
}
