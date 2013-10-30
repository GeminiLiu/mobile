package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SourceAttributeInfo
{
	//资源ID
	private String sourceid = "";
	//资源属性名
	private String sourceattname = "";
	//资源属性值
	private String attvalue = "";
	
	public String getAttvalue()
	{
		return attvalue;
	}

	public void setAttvalue(String attvalue)
	{
		this.attvalue = attvalue;
	}

	public String getSourceattname()
	{
		return sourceattname;
	}

	public void setSourceattname(String sourceattname)
	{
		this.sourceattname = sourceattname;
	}

	public String getSourceid()
	{
		return sourceid;
	}

	public void setSourceid(String sourceid)
	{
		this.sourceid = sourceid;
	}

}
