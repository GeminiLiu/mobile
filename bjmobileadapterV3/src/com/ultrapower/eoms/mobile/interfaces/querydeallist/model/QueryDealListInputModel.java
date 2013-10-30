package com.ultrapower.eoms.mobile.interfaces.querydeallist.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

/**
 * 查询工单列表的输入参数对象
 * 
 * @author Haoyuan
 */
public class QueryDealListInputModel
{
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 是否为待办列表
	 */
	private int isWait;
	/**
	 * 查询的工单类型
	 */
	private String category;
	/**
	 * 查询条件
	 */
	private String condition;
	/**
	 * 要查询的字段
	 */
	private String fields;
	/**
	 * 当前页
	 */
	private int pageNum;
	/**
	 * 每页条数
	 */
	private int pageSize;

	/**
	 * 从输入xml转化为model对象
	 * 
	 * @param xml
	 *            输入xml
	 */
	public void buildModel(String xml) throws InvalidXmlFormatException
	{
		try
		{
			Document doc = DocumentHelper.parseText(xml);
			Node baseInfoNode = doc.selectSingleNode("//opDetail/baseInfo");
			Node recordInfoNode = doc.selectSingleNode("//opDetail/recordInfo");

			this.userName = baseInfoNode.selectSingleNode("userName").getText();

			this.isWait = Integer.parseInt(recordInfoNode.selectSingleNode("isWait").getText());
			this.category = recordInfoNode.selectSingleNode("category").getText();
			this.condition = recordInfoNode.selectSingleNode("queryCondition").getText();
			this.fields = recordInfoNode.selectSingleNode("queryFields").getText();
			this.pageNum = Integer.parseInt(recordInfoNode.selectSingleNode("pageNum").getText());
			this.pageSize = Integer.parseInt(recordInfoNode.selectSingleNode("pageSize").getText());
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

	public int getIsWait()
	{
		return isWait;
	}

	public void setIsWait(int isWait)
	{
		this.isWait = isWait;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getCondition()
	{
		return condition;
	}

	public void setCondition(String condition)
	{
		this.condition = condition;
	}

	public String getFields()
	{
		return fields;
	}

	public void setFields(String fields)
	{
		this.fields = fields;
	}

	public int getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(int pageNum)
	{
		this.pageNum = pageNum;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
}
