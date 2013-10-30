package com.ultrapower.eoms.mobile.interfaces.formcommit.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

public class FormCommitInputModel
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
	 * 要执行的动作的中文
	 */
	private String actionText;
	
	private Map<String, String> fields;
	
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
			this.actionCode = recordInfoNode.selectSingleNode("actionCode").getText();
			this.actionText = recordInfoNode.selectSingleNode("actionText").getText();
			
			fields = new LinkedHashMap<String, String>();
			List<Node> fieldNodes = recordInfoNode.selectNodes("field");
			if(fieldNodes != null)
			{
				for(Node fieldNode : fieldNodes)
				{
					fields.put(fieldNode.valueOf("@code"), fieldNode.getText());
				}
			}
			
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

	public String getActionText()
	{
		return actionText;
	}

	public void setActionText(String actionText)
	{
		this.actionText = actionText;
	}

	public Map<String, String> getFields()
	{
		return fields;
	}

	public void setFields(Map<String, String> fields)
	{
		this.fields = fields;
	}
}
