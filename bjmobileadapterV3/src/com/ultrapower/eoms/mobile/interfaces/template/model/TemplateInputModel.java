package com.ultrapower.eoms.mobile.interfaces.template.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

public class TemplateInputModel
{
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 模板分类
	 */
	private String category;
	
	/**
	 * 模板分类下的某一类型
	 */
	private String version;
	
	/**
	 * 从输入xml转化为model对象
	 * @param xml 输入xml
	 */
	public void buildModel(String xml) throws InvalidXmlFormatException
	{
		try
		{
			System.out.println(xml);
			Document doc = DocumentHelper.parseText(xml);
			Node baseInfoNode = doc.selectSingleNode("//opDetail/baseInfo");
			Node recordInfoNode = doc.selectSingleNode("//opDetail/recordInfo");
			
			this.userName = baseInfoNode.selectSingleNode("userName").getText();
			
			this.category = recordInfoNode.selectSingleNode("category").getText();
			this.version = recordInfoNode.selectSingleNode("version").getText();
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

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}
	
	
}
