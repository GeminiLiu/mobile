package cn.com.ultrapower.ultrawf.models.process;

public class BaseAttachmentTplModel
{
	private String	attachmentTplID;
	private String	BaseName;
	private String	BaseSchema;
	private String	FileIdentifier;
	private String	FileName;
	private String	FileDesc;
	public String getBaseName() {
		return BaseName;
	}
	public void setBaseName(String baseName) {
		BaseName = baseName;
	}
	public String getBaseSchema() {
		return BaseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		BaseSchema = baseSchema;
	}
	public String getFileIdentifier() {
		return FileIdentifier;
	}
	public void setFileIdentifier(String fileIdentifier) {
		FileIdentifier = fileIdentifier;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFileDesc() {
		return FileDesc;
	}
	public void setFileDesc(String fileDesc) {
		FileDesc = fileDesc;
	}
	public void setAttachmentTplID(String attachmentTplID) {
		this.attachmentTplID = attachmentTplID;
	}
	public String getAttachmentTplID() {
		return attachmentTplID;
	}

}
