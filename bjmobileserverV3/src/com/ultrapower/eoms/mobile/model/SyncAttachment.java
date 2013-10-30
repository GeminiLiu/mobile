package com.ultrapower.eoms.mobile.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BS_T_MOBILE_SYNCATTACH")
public class SyncAttachment implements Serializable
{
	private String attachID;
	private String taskID;
	private String type;
	private String dbName;
	private String path;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="ATTACHID", unique=true, nullable=false, insertable=true, updatable=true, length=50)
	public String getAttachID()
	{
		return attachID;
	}
	public void setAttachID(String attachID)
	{
		this.attachID = attachID;
	}
	
	@Column(name="TASKID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getTaskID()
	{
		return taskID;
	}
	public void setTaskID(String taskID)
	{
		this.taskID = taskID;
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
	
	@Column(name="DBNAME", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getDbName()
	{
		return dbName;
	}
	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}
	
	@Column(name="PATH", unique=false, nullable=true, insertable=true, updatable=true, length=500)
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
}
