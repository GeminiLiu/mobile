package cn.com.ultrapower.ultrawf.control.flowmap;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.flowmap.*;

/**
 * 存放所有环节的Process的类
 * 
 * @author BigMouse
 */
public class ProcessList
{
	/**
	 * 存放ProcessModel的List
	 */
	private List processList = new ArrayList();

	public List getProcessList()
	{
		return processList;
	}

	private String beginPhaseNo;

	protected String getBeginPhaseNo()
	{
		return beginPhaseNo;
	}

	private List processLogInfoList = new ArrayList();

	public void setProcessLogInfoList(List processLogInfoList)
	{
		this.processLogInfoList = processLogInfoList;
	}
	
	private List tplLinkList = new ArrayList();
	
	public void setTplLinkList(List tplLinkList)
	{
		this.tplLinkList = tplLinkList;
	}
	
	private List fpList = new ArrayList();
	
	protected List getFpList()
	{
		return fpList;
	}

	/**
	 * 添加一个ProcessInfo
	 * 
	 * @param pInfo：一个环节的一条Process信息
	 */
	public void addProcessInfo(ProcessInfo pInfo)
	{
		// 获取该Process的Log信息，并存入ProcessInfo中
		List pLogInfoList = getProcesslogInfoModel(pInfo.getProcessID());
		for(Iterator it = pLogInfoList.iterator(); it.hasNext();)
		{
			pInfo.addProcessLog((ProcessLogInfo) it.next());
		}
		
		String prevPhaseNo = pInfo.getPrevPhaseNo();
		
		for(Iterator it = tplLinkList.iterator(); it.hasNext();)
		{
			LinkInfo lInfo = (LinkInfo)it.next();
			if(lInfo.getEndPhase().equals(pInfo.getPhaseNo()))
			{
				/*
				if(prevPhaseNo != null && !lInfo.getStartPhase().equals(prevPhaseNo) && lInfo.getFlagRequired() == 0 && pInfo.getEdDate() <= 0)
				{}
				else
				{
					prevPhaseNo = lInfo.getStartPhase();
				}
				*/
				if(prevPhaseNo == null || lInfo.getStartPhase().equals(prevPhaseNo))
				{
					prevPhaseNo = lInfo.getStartPhase();
				}
				else if(lInfo.getFlagRequired() == 1)
				{
					prevPhaseNo = lInfo.getStartPhase();
				}
			}
		}
		pInfo.setPrevPhaseNo(prevPhaseNo);

		// 找到环节号为传入ProcessInfo的环节号的ProcessModel的索引值
		int index = -1;
		if(pInfo.getFlagDuplicated() == 0)
		{
			index = getProcessModelIndex(pInfo.getPhaseNo());
			beginPhaseNo = pInfo.getBeginPhaseNo();
		}
		else
		{
			index = getProcessModelIndex(pInfo.getPhaseNo());
		}

		if(index < 0)
		{
			// 如果没有，则向List中添加一个ProcessModel，将ProcessInfo放进去
			ProcessModel pModel = new ProcessModel();
			pModel.addProcessInfo(pInfo);
			
			pModel.setPrevPhaseNo(prevPhaseNo);

			if(pModel.getFlagPredefined() == 0 && (pModel.getPrevPhaseNo().substring(1, 3).equals("p_") || pModel.getPrevPhaseNo().substring(0, 2).equals("p_")))
			{
				fpList.add(pModel.getPrevPhaseNo());
			}
			processList.add(pModel);
		}
		else
		{
			// 如果有，将ProcessModel取出，将ProcessInfo放进去，再将ProcessModel放回
			ProcessModel pModel = (ProcessModel) processList.get(index);
			pModel.addProcessInfo(pInfo);
			pModel.setPrevPhaseNo(prevPhaseNo);
			processList.set(index, pModel);
		}
	}
	
	protected void setTplProcessTip()
	{
		for(Iterator it1 = fpList.iterator(); it1.hasNext();)
		{
			String pNo = it1.next().toString();
			for(int i = 0; i < processList.size(); i++)
			{
				ProcessModel pModel = (ProcessModel)processList.get(i);
				if(pModel.getPhaseNo().equals(pNo))
				{
					pModel.setHasFreeProcess(true);
				}
				processList.set(i, pModel);
			}
		}
	}

	/**
	 * 获取ProcessModel的索引值
	 * 
	 * @param processID：环节号
	 * @return ProcessModel的索引值
	 */
	private int getProcessModelIndex(String phaseNo)
	{
		for(int i = 0; i < processList.size(); i++)
		{
			ProcessModel pModel = (ProcessModel) processList.get(i);
			if(pModel.getPhaseNo().equals(phaseNo))
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * 根据ProcessID获取该Process的Log信息
	 * 
	 * @param processID：ProcessID
	 * @return Log信息
	 */
	private List getProcesslogInfoModel(String processID)
	{
		List pLogInfoModelList = new ArrayList();
		for(Iterator it = processLogInfoList.iterator(); it.hasNext();)
		{
			ProcessLogInfo pLogInfo = (ProcessLogInfo) it.next();
			if(pLogInfo.getProcessID().equals(processID))
			{
				pLogInfoModelList.add(pLogInfo);
			}
		}
		return pLogInfoModelList;
	}

	/*
	 * 快速排序，暂时没有用处。 protected void processListSort() { sort(0,
	 * processList.size() - 1); } private void sort(int left, int right) { int i =
	 * left, j = right; ProcessModel middle, temp; middle = (ProcessModel)
	 * processList.get((i + j) / 2); do { ProcessModel pModelLeft =
	 * (ProcessModel) processList.get(i); ProcessModel pModelRight =
	 * (ProcessModel) processList.get(j); while (i < right &&
	 * (pModelLeft.getStDate().intValue() < middle.getStDate().intValue() ||
	 * pModelLeft.getStDate().intValue() == middle.getStDate().intValue() &&
	 * pModelLeft.getProcess().getPrevPhaseNo().equals("BEGIN"))) { i++; } while
	 * (j > left && (pModelRight.getStDate().intValue() >
	 * middle.getStDate().intValue() || pModelRight.getStDate().intValue() ==
	 * middle.getStDate().intValue() &&
	 * !pModelRight.getProcess().getPrevPhaseNo().equals("BEGIN"))) { j++; } if
	 * (i <= j) { processList.set(i, pModelRight); processList.set(j,
	 * pModelLeft); i++; j--; } } while (i <= j); if (left < j) { sort(left, j); }
	 * if (right > i) { sort(i, right); } }
	 */
}
