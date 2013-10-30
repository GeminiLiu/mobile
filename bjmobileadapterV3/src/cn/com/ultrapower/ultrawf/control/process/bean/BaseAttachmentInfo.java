package cn.com.ultrapower.ultrawf.control.process.bean;

public class BaseAttachmentInfo {
	
	private  String strAttachmentPath;
	
	public BaseAttachmentInfo()
	{
		
	}
	
	public BaseAttachmentInfo(String p_strAttachmentPath)
	{
		this.strAttachmentPath = p_strAttachmentPath;
	}	

	public String getStrAttachmentPath() {
		return strAttachmentPath;
	}

	public void setStrAttachmentPath(String strAttachmentPath) {
		this.strAttachmentPath = strAttachmentPath;
	}
	
}
