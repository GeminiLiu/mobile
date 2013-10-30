package com.ultrapower.eoms.mobile.interfaces.syncattach.model;

public class SysAttachModel  implements java.io.Serializable
{
	private String attachID;
	private String taskID;
	private String type;
	private String dbname;
	private String path;
	public String getAttachID() 
	{
		return attachID;
	}
	public void setAttachID(String attachID)
	{
		this.attachID = attachID;
	}
	public String getTaskID()
	{
		return taskID;
	}
	public void setTaskID(String taskID)
	{
		this.taskID = taskID;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getDbname()
	{
		return dbname;
	}
	public void setDbname(String dbname)
	{
		this.dbname = dbname;
	}
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
	
	
}
