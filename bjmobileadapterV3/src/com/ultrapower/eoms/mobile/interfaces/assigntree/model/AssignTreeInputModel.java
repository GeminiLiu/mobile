package com.ultrapower.eoms.mobile.interfaces.assigntree.model;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

public class AssignTreeInputModel
{
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 是否显示公司，0为不显示，1为当前，2为所有
	 */
	private int showCorp;
	
	/**
	 * 是否显示中心，0为不显示，1为当前，2为所有
	 */
	private int showCenter;
	
	/**
	 * 是否显示驻点，0为不显示，1为当前，2为所有
	 */
	private int showStation;
	
	/**
	 * 是否显示小组，0为不显示，1为当前，2为所有
	 */
	private int showTeam;
	
	/**
	 * 是否显示人员，0为不显示，1为显示
	 */
	private int showPerson;
	
	private int multiSelect;
	
	private String selectObjs;
	
	private String cityID;
	
	private String specialtyID;
	
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
			
			this.showCorp = Integer.parseInt(recordInfoNode.selectSingleNode("showCorp").getText());
			this.showCenter = Integer.parseInt(recordInfoNode.selectSingleNode("showCenter").getText());
			this.showStation = Integer.parseInt(recordInfoNode.selectSingleNode("showStation").getText());
			this.showTeam = Integer.parseInt(recordInfoNode.selectSingleNode("showTeam").getText());
			this.showPerson = Integer.parseInt(recordInfoNode.selectSingleNode("showPerson").getText());
			this.multiSelect = Integer.parseInt(recordInfoNode.selectSingleNode("multi").getText());
			this.selectObjs = recordInfoNode.selectSingleNode("selectObjs").getText();
			this.cityID = recordInfoNode.selectSingleNode("cityID").getText();
			this.specialtyID = recordInfoNode.selectSingleNode("specialtyID").getText();
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

	public int getShowCorp()
	{
		return showCorp;
	}

	public void setShowCorp(int showCorp)
	{
		this.showCorp = showCorp;
	}

	public int getShowCenter()
	{
		return showCenter;
	}

	public void setShowCenter(int showCenter)
	{
		this.showCenter = showCenter;
	}

	public int getShowStation()
	{
		return showStation;
	}

	public void setShowStation(int showStation)
	{
		this.showStation = showStation;
	}

	public int getShowPerson()
	{
		return showPerson;
	}

	public void setShowPerson(int showPerson)
	{
		this.showPerson = showPerson;
	}

	public int getMultiSelect()
	{
		return multiSelect;
	}

	public void setMultiSelect(int multiSelect)
	{
		this.multiSelect = multiSelect;
	}

	public String getSelectObjs()
	{
		return selectObjs;
	}

	public void setSelectObjs(String selectObjs)
	{
		this.selectObjs = selectObjs;
	}

	public String getCityID()
	{
		return cityID;
	}

	public void setCityID(String cityID)
	{
		this.cityID = cityID;
	}

	public String getSpecialtyID()
	{
		return specialtyID;
	}

	public void setSpecialtyID(String specialtyID)
	{
		this.specialtyID = specialtyID;
	}

	public int getShowTeam()
	{
		return showTeam;
	}

	public void setShowTeam(int showTeam)
	{
		this.showTeam = showTeam;
	}
}
