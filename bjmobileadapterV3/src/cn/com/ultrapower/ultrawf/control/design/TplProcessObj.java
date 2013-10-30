package cn.com.ultrapower.ultrawf.control.design;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.process.*;

public class TplProcessObj
{
	/**
	 * 存放的环节
	 */
	public TplDealProcessModel tdpModel = new TplDealProcessModel();
	public TplDealAssistantProcessModel tdapModel = new TplDealAssistantProcessModel();
	public TplDealVerdictModel tdvModel = new TplDealVerdictModel();
	
	public TplDealProcessRoleDefineModel tdprdModel = new TplDealProcessRoleDefineModel();
	public List tcbdList = new ArrayList();
	
	private List beginLinkList = new ArrayList();
	private List endLinkList = new ArrayList();
	private String processType;
	
	private String processID = "";
	
	private List wayList = new ArrayList();
	
	public List getWayList()
	{
		return wayList;
	}

	public void setWayList(List wayList)
	{
		this.wayList = wayList;
	}
	
	public String getProcessType()
	{
		return processType;
	}

	public void setProcessType(String processType)
	{
		this.processType = processType;
	}

	/**
	 * 向环节中添加流出的流转箭头
	 * @param linkID 流转箭头ID
	 */
	public void addBeginLink(String linkID)
	{
		beginLinkList.add(linkID);
	}
	
	/**
	 * 从环节中删除流入的流转箭头
	 * @param linkID 流转箭头ID
	 */
	public void deleteBeginLink(String linkID)
	{
		for(int i = 0; i < beginLinkList.size(); i++)
		{
			if(((String)beginLinkList.get(i)).equals(linkID))
			{
				beginLinkList.remove(i);
				break;
			}
		}
	}
	
	/**
	 * 向环节中添加流出的流转箭头
	 * @param linkID 流转箭头ID
	 */
	public void addEndLink(String linkID)
	{
		endLinkList.add(linkID);
	}
	
	/**
	 * 从环节中删除流出的流转箭头
	 * @param linkID 流转箭头ID
	 */
	public void deleteEndLink(String linkID)
	{
		for(int i = 0; i < endLinkList.size(); i++)
		{
			if(((String)endLinkList.get(i)).equals(linkID))
			{
				endLinkList.remove(i);
				break;
			}
		}
	}

	public String getProcessID()
	{
		return processID;
	}

	public void setProcessID(String processID)
	{
		this.processID = processID;
	}

	public List getBeginLinkList()
	{
		return beginLinkList;
	}

	public List getEndLinkList()
	{
		return endLinkList;
	}
}
