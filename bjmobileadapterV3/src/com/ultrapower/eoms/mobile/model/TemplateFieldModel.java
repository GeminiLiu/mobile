package com.ultrapower.eoms.mobile.model;

public class TemplateFieldModel
{
	private String code;
	private String type;
	private String name;
	private int row;
	private String dic;
	private String template;
	
	private String assignType;
	private int corp;
	private int center;
	private int station;
	private int team;
	private int person;
	private String select;
	private int multi;
	
	public String getXmlString()
	{
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<field");
		
		xmlBuilder.append(" code='" + this.code + "'");
		xmlBuilder.append(" type='" + this.type + "'");
		if(this.type.equals("SELECT"))
			xmlBuilder.append(" dic='" + this.dic + "'");
		else if(this.type.equals("TEXTAREA"))
			xmlBuilder.append(" row='" + this.row + "'");
		else if(this.type.equals("TREE"))
		{
			xmlBuilder.append(" assigntype='" + this.assignType + "'");
			xmlBuilder.append(" corp='" + this.corp + "'");
			xmlBuilder.append(" center='" + this.center + "'");
			xmlBuilder.append(" station='" + this.station + "'");
			xmlBuilder.append(" team='" + this.team + "'");
			xmlBuilder.append(" person='" + this.person + "'");
			xmlBuilder.append(" select='" + this.select + "'");
			xmlBuilder.append(" multi='" + this.multi + "'");
		}
		if(template != null && !template.equals(""))
			xmlBuilder.append(" template='" + this.template + "'");
		
		xmlBuilder.append(">");
		xmlBuilder.append(this.name);
		xmlBuilder.append("</field>");
		
		return xmlBuilder.toString();
	}

	public String getCode()
	{
		return code;
	}

	public String getTemplate()
	{
		return template;
	}

	public void setTemplate(String template)
	{
		this.template = template;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public String getDic()
	{
		return dic;
	}

	public void setDic(String dic)
	{
		this.dic = dic;
	}

	public String getAssignType()
	{
		return assignType;
	}

	public void setAssignType(String assignType)
	{
		this.assignType = assignType;
	}

	public int getCorp()
	{
		return corp;
	}

	public void setCorp(int corp)
	{
		this.corp = corp;
	}

	public int getCenter()
	{
		return center;
	}

	public void setCenter(int center)
	{
		this.center = center;
	}

	public int getStation()
	{
		return station;
	}

	public void setStation(int station)
	{
		this.station = station;
	}

	public int getTeam()
	{
		return team;
	}

	public void setTeam(int team)
	{
		this.team = team;
	}

	public int getPerson()
	{
		return person;
	}

	public void setPerson(int person)
	{
		this.person = person;
	}

	public String getSelect()
	{
		return select;
	}

	public void setSelect(String select)
	{
		this.select = select;
	}

	public int getMulti()
	{
		return multi;
	}

	public void setMulti(int multi)
	{
		this.multi = multi;
	}
	
}
