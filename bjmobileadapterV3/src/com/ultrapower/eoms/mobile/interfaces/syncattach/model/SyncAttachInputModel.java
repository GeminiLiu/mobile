package com.ultrapower.eoms.mobile.interfaces.syncattach.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

public class SyncAttachInputModel
{
	
	/**
	 * 物理文件名
	 */
	private String dbName;
	/**
	 * 真实文件名
	 */
	private String realName;
	/**
	 * 真实文件大小
	 */
	private String size;
	/**
	 * 真实文件后缀名
	 */
	private String suffix;
	
	/**
	 * 从输入xml转化为model对象
	 * 
	 * @param xml
	 * 输入xml
	 */
	public void buildModel(String xml) throws InvalidXmlFormatException
	{
		try
		{
			Document doc = DocumentHelper.parseText(xml);
			Node recordInfoNode = doc.selectSingleNode("//opDetail/recordInfo");
			this.dbName = recordInfoNode.selectSingleNode("dbname").getText();
			this.realName = recordInfoNode.selectSingleNode("realname").getText();
			this.size = recordInfoNode.selectSingleNode("size").getText();
			this.suffix = recordInfoNode.selectSingleNode("suffix").getText();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
			throw new InvalidXmlFormatException(InvalidXmlFormatException.XML_FORMAT, null);
		}
	}
	
	public String getDbName()
	{
		return dbName;
	}
	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}
	public String getRealName()
	{
		return realName;
	}
	public void setRealName(String realName)
	{
		this.realName = realName;
	}
	public String getSize()
	{
		return size;
	}
	public void setSize(String size)
	{
		this.size = size;
	}
	public String getSuffix()
	{
		return suffix;
	}
	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}
}
