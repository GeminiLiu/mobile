package com.ultrapower.eoms.mobile.model;

public class ContentTemplateModel
{
	private String contentTemplateID;
	private String baseSchema;
	private String title;
	private String targetField;
	private String matchRule;
	private String content;
	
	public String getContentTemplateID()
	{
		return contentTemplateID;
	}
	public void setContentTemplateID(String contentTemplateID)
	{
		this.contentTemplateID = contentTemplateID;
	}
	public String getBaseSchema()
	{
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTargetField()
	{
		return targetField;
	}
	public void setTargetField(String targetField)
	{
		this.targetField = targetField;
	}
	public String getMatchRule()
	{
		return matchRule;
	}
	public void setMatchRule(String matchRule)
	{
		this.matchRule = matchRule;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
}
