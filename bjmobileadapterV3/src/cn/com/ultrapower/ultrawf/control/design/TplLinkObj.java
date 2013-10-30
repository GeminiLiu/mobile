package cn.com.ultrapower.ultrawf.control.design;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.process.*;

public class TplLinkObj
{
	/**
	 * 存放的流转箭头
	 */
	public TplDealLinkModel tdlModel = new TplDealLinkModel();
	
	private String beginProcess = "";
	private String endProcess = "";
	
	private String linkID = "";
	
	private List wayList = new ArrayList();
	
	
	
	public List getWayList()
	{
		return wayList;
	}

	public void setWayList(List wayList)
	{
		this.wayList = wayList;
	}

	/**
	 * 获取流转箭头的起始端环节ID
	 * @return 流转箭头的起始端环节ID
	 */
	public String getBeginProcess()
	{
		return beginProcess;
	}
	
	/**
	 * 设置流转箭头的起始端环节ID
	 * @param processID 流转箭头的起始端环节ID
	 */
	public void setBeginProcess(String processID)
	{
		this.beginProcess = processID;
	}
	
	/**
	 * 获取流转箭头的结束端环节ID
	 * @return 流转箭头的结束端环节ID
	 */
	public String getEndProcess()
	{
		return endProcess;
	}
	
	/**
	 * 设置流转箭头的结束端环节ID
	 * @param processID 流转箭头的结束端环节ID
	 */
	public void setEndProcess(String processID)
	{
		this.endProcess = processID;
	}

	public String getLinkID()
	{
		return linkID;
	}

	public void setLinkID(String linkID)
	{
		this.linkID = linkID;
	}
	
	
}
