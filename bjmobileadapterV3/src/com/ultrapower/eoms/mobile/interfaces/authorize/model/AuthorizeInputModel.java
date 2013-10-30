package com.ultrapower.eoms.mobile.interfaces.authorize.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

public class AuthorizeInputModel
{
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 机器码
	 */
	private String machineCode;
	/**
	 * SIM卡号
	 */
	private String simNum;
	
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
			
			this.userName = baseInfoNode.selectSingleNode("userName").getText();
			this.password = baseInfoNode.selectSingleNode("password").getText();
			this.machineCode = baseInfoNode.selectSingleNode("machineCode").getText();
			this.simNum = baseInfoNode.selectSingleNode("simNum").getText();
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getMachineCode()
	{
		return machineCode;
	}

	public void setMachineCode(String machineCode)
	{
		this.machineCode = machineCode;
	}

	public String getSimNum()
	{
		return simNum;
	}

	public void setSimNum(String simNum)
	{
		this.simNum = simNum;
	}
}
