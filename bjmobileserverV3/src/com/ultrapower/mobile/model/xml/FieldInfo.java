package com.ultrapower.mobile.model.xml;

public class FieldInfo
{

	private String ename = "";
	private String cname = "";
	private String content = "";
	private String id = "";
	private String defaultValue = "";
	private boolean isHidden;
	private boolean isRequired;
	private String singleFlag = "";
	private String type = "";
	private String typeValue = "";//字典类型值
	private String dict = "";
	private int length;
	private String meta = "";
	private String infoDesc = "";

	public FieldInfo(String ename, String cname, String content, String id, String defaultValue, boolean isHidden, boolean isRequired, String type, int length, String meta,String infoDesc)
	{
		this.ename = ename;
		this.cname = cname;
		this.content = content;
		this.id = id;
		this.defaultValue = defaultValue;
		this.isHidden = isHidden;
		this.isRequired = isRequired;
		this.type = type;
		this.length = length;
		this.meta = meta;
		this.infoDesc = infoDesc;
	}

	public FieldInfo(String ename, String cname, String content, String id, String defaultValue)
	{
		this.ename = ename;
		this.cname = cname;
		this.content = content;
		this.id = id;
		this.defaultValue = defaultValue;
	}

	public FieldInfo()
	{

	}

	public FieldInfo(String ename, String cname, String content)
	{
		this.ename = ename;
		this.cname = cname;
		this.content = content;
	}

	public FieldInfo(String ename, String cname, int content)
	{
		this.ename = ename;
		this.cname = cname;
		this.content = content + "";
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public boolean isHidden()
	{
		return isHidden;
	}

	public void setHidden(boolean isHidden)
	{
		this.isHidden = isHidden;
	}

	public boolean isRequired()
	{
		return isRequired;
	}

	public void setRequired(boolean isRequired)
	{
		this.isRequired = isRequired;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public String getMeta()
	{
		return meta;
	}

	public void setMeta(String meta)
	{
		this.meta = meta;
	}

	public String getEname()
	{
		return ename;
	}

	public void setEname(String ename)
	{
		this.ename = ename;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getTypeValue()
	{
		return typeValue;
	}

	public void setTypeValue(String typeValue)
	{
		this.typeValue = typeValue;
	}

	public String getDict()
	{
		return dict;
	}

	public void setDict(String dict)
	{
		this.dict = dict;
	}

	public String getSingleFlag()
	{
		return singleFlag;
	}

	public void setSingleFlag(String singleFlag)
	{
		this.singleFlag = singleFlag;
	}

	public String getInfoDesc()
	{
		return infoDesc;
	}

	public void setInfoDesc(String infoDesc)
	{
		this.infoDesc = infoDesc;
	}

}
