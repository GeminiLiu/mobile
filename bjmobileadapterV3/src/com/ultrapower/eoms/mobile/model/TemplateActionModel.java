package com.ultrapower.eoms.mobile.model;

import java.util.List;

public class TemplateActionModel
{
	private String id;
	private String code;
	private String text;
	private int radio;
	private int photo;
	private int isBeginAction;
	
	private List<TemplateFieldModel> fields;
	
	public String getXmlString()
	{
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<action");
		xmlBuilder.append(" id='" + id + "'");
		xmlBuilder.append(" code='" + code + "'");
		xmlBuilder.append(" text='" + text + "'");
		xmlBuilder.append(" radio='" + radio + "'");
		xmlBuilder.append(" photo='" + photo + "'");
		if(isBeginAction == 1)
			xmlBuilder.append(" createaction='" + isBeginAction + "'");
		xmlBuilder.append(">");
		
		for(TemplateFieldModel field : fields)
		{
			xmlBuilder.append(field.getXmlString());
		}
		
		xmlBuilder.append("</action>");
		
		return xmlBuilder.toString();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public int getRadio()
	{
		return radio;
	}

	public void setRadio(int radio)
	{
		this.radio = radio;
	}

	public int getPhoto()
	{
		return photo;
	}

	public void setPhoto(int photo)
	{
		this.photo = photo;
	}

	public List<TemplateFieldModel> getFields()
	{
		return fields;
	}

	public void setFields(List<TemplateFieldModel> fields)
	{
		this.fields = fields;
	}

	public int getIsBeginAction()
	{
		return isBeginAction;
	}

	public void setIsBeginAction(int isBeginAction)
	{
		this.isBeginAction = isBeginAction;
	}
}
