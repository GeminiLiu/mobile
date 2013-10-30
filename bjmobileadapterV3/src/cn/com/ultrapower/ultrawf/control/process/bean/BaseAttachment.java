package cn.com.ultrapower.ultrawf.control.process.bean;

public class BaseAttachment {
	
	private  String strAttachmentPath;
	
	public BaseAttachment()
	{
		
	}
	
	public BaseAttachment(String p_strAttachmentPath)
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
