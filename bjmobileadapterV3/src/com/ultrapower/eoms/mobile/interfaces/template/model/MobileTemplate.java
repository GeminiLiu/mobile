package com.ultrapower.eoms.mobile.interfaces.template.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BS_T_MOBILE_TEMPLATE")
public class MobileTemplate implements Serializable
{
	private String templateId;
	private String type;
	private String category;
	private String categoryName;
	private String version;
	private Integer sort;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="TEMPLATEID", unique=true, nullable=false, insertable=true, updatable=true, length=50)
	public String getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(String templateId)
	{
		this.templateId = templateId;
	}

	@Column(name="TYPE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	@Column(name="CATEGORY", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	@Column(name="VERSION", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	@Column(name="CATEGORYNAME", unique=false, nullable=true, insertable=true, updatable=true, length=200)
	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	@Column(name="SORT", unique=false, nullable=true, insertable=true, updatable=true)
	public Integer getSort()
	{
		return sort;
	}

	public void setSort(Integer sort)
	{
		this.sort = sort;
	}

}
