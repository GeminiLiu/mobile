package com.ultrapower.eoms.mobile.interfaces.syncattach.model;

public class BaseAttachmentInfo {
	
	private String path;
	private String name;
	private String dbname;
	private String uploaddate;
	
	public BaseAttachmentInfo() {
		
	}
	
	public BaseAttachmentInfo(String path, String name) {
		this.path = path;
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDbname()
	{
		return dbname;
	}

	public void setDbname(String dbname)
	{
		this.dbname = dbname;
	}

	public String getUploaddate()
	{
		return uploaddate;
	}

	public void setUploaddate(String uploaddate)
	{
		this.uploaddate = uploaddate;
	}

	
}
