package com.ultrapower.eoms.mobile.interfaces.initaction.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

public class InitActionInputModel
{
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 工单ID
	 */
	private String baseID;
	
	/**
	 * 工单类型
	 */
	private String category;
	
	/**
	 * 任务ID
	 */
	private String taskID;
	
	/**
	 * 要执行的动作
	 */
	private String actionCode;
	
	/**
	 * 要执行的动作ID
	 */
	private String actionID;
	
	/**
	 * 从输入xml转化为model对象
	 * @param xml 输入xml
	 */
	public void buildModel(String xml) throws InvalidXmlFormatException
	{
		try
		{
			Document doc = DocumentHelper.parseText(xml);
			Node baseInfoNode = doc.selectSingleNode("//opDetail/baseInfo");
			Node recordInfoNode = doc.selectSingleNode("//opDetail/recordInfo");
			
			this.userName = baseInfoNode.selectSingleNode("userName").getText();
			
			this.baseID = recordInfoNode.selectSingleNode("baseId").getText();
			this.category = recordInfoNode.selectSingleNode("category").getText();
			this.taskID = recordInfoNode.selectSingleNode("taskId").getText();
			this.actionID = recordInfoNode.selectSingleNode("actionId").getText();
			this.actionCode = recordInfoNode.selectSingleNode("actionCode").getText();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
			throw new InvalidXmlFormatException(InvalidXmlFormatException.XML_FORMAT, null);
		}
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getBaseID()
	{
		return baseID;
	}

	public void setBaseID(String baseID)
	{
		this.baseID = baseID;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getTaskID()
	{
		return taskID;
	}

	public void setTaskID(String taskID)
	{
		this.taskID = taskID;
	}

	public String getActionCode()
	{
		return actionCode;
	}

	public void setActionCode(String actionCode)
	{
		this.actionCode = actionCode;
	}

	public String getActionID()
	{
		return actionID;
	}

	public void setActionID(String actionID)
	{
		this.actionID = actionID;
	}
}
