   package com.ultrapower.eoms.common.plugin.swfupload.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;

@SuppressWarnings("serial")
public class AttachmentUploadTag extends TagSupport
{
	private String uploadAction;
	private String queryAction;
	private String downloadAction;
	private String uploadDestination;
	private String deleteAction;
	
	private String attchmentGroupId; // 附件组id
	private String uploadBtnIsVisible = "true"; // 是否显示上传按钮
	private String downable = "true"; // 是否可下载
	private String uploadable = "true"; // 是否可上傳
	private String isMultiUpload = "true";//是否批量上传
	private String isMultiDownload = "true";//是否批量下载
	private String isAutoUpload = "false";//是否自动上传
	private String uploadTableVisible = "true";//是否显示上传文件table
	private String progressIsVisible = "true";//是否显示上传进度条
	private String isEdit = "true";// 是否可以编辑（上传以后是否可以删除）
	private String createDirByDate = "false";// 是否根据日期创建新的文件夹（默认创建）
	private String attachmentUser;
	// 上传文件类型及文件类型描述
	private String fileTypes = "*.*";
	private String fileTypesDescription = "project reference";
	// 按钮imageurl、flash url
	private String imageUrl;
	private String imagePath;
	private String flashUrl;
	//返回值格式：1-文件名；2-文件真实名；3-存储路径；4-文件大小；5-文件类型；6-关系ID；
	//比如您要返回文件名、存储路径、文件大小，您应该在标签中添加属性为returnValue="1,3,4"
	//返回值也将以这个顺序返回，如"卡农.mp3,D:\music\,3072KB"
	private String returnValue;
	
	@SuppressWarnings("static-access")
    public int doStartTag()
	{
		System.out.println(1);
		/**
		 * id : 组件标识
		 * uploadAction ：上传action
		 * queryAction ：查询下载action
		 * downloadAction ：下载action
		 * attachmentGroupId ：附件组id
		 * savePath ：上传/下载路径
		 * uploadable ：是否可上传
		 * downable ： 是否可下载
		 * uploadBtnable ：上传按钮是否可用
		 * btnImageUrl：选择附件按钮图片url
		 * flashUrl ：SWFUpload.swf url
		 * fileTypes ：上传文件类型
		 * fileTypesDescription ：文件类型描述
		 * isMultiUpload ：是否批量上传
		 * isMultiDownload ：是否批量下载
		 * isAutoUpload ：是否自动上传
		 * uploadTableVisible : 上传table是否可见
		 * progressIsVisible : 进度条是否显示
		 * imagePath : 样式图片路径
		 */
		try
		{
			// 标签显示开始
			StringBuffer uploadHtmlHodler = new StringBuffer();
			// 生成一个div
			uploadHtmlHodler.append("<div id='");
			uploadHtmlHodler.append(this.getId());
			uploadHtmlHodler.append(SwfuploadUtil.SWFUPLOAD_DIV);
			uploadHtmlHodler.append("' ></div>\n");
			// script
			uploadHtmlHodler.append("<script>\n");
			uploadHtmlHodler.append("var ");
			uploadHtmlHodler.append(this.getId());
			uploadHtmlHodler.append(" = new UltraSWFUpload('");
			uploadHtmlHodler.append(this.getId());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getUploadAction());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getQueryAction());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getDownloadAction());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getAttchmentGroupId());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getUploadDestination());
			uploadHtmlHodler.append("',");
			uploadHtmlHodler.append(this.getUploadable());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getDownable());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getUploadBtnIsVisible());
			uploadHtmlHodler.append(",'");
			uploadHtmlHodler.append(this.getImageUrl());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getFlashUrl());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getFileTypes());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getFileTypesDescription());
			uploadHtmlHodler.append("',");
			uploadHtmlHodler.append(this.getIsMultiUpload());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getIsMultiDownload());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getIsAutoUpload());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getProgressIsVisible());
			uploadHtmlHodler.append(",'");
			uploadHtmlHodler.append(this.getImagePath());
			uploadHtmlHodler.append("',");
			uploadHtmlHodler.append(this.getIsEdit());
			uploadHtmlHodler.append(",'");
			uploadHtmlHodler.append(this.getDeleteAction());
			uploadHtmlHodler.append("',");
			uploadHtmlHodler.append(this.getCreateDirByDate());
			uploadHtmlHodler.append(",'");
			uploadHtmlHodler.append(this.getAttachmentUser());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getReturnValue());
			uploadHtmlHodler.append("');\n");
			uploadHtmlHodler.append("</script>\n");
			pageContext.getOut().write(uploadHtmlHodler.toString());
//			System.out.println(uploadHtmlHodler.toString());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return this.SKIP_BODY;
	}

	private String getServicePath()
	{
		StringBuffer pathBuffer = new StringBuffer(this.pageContext.getRequest().getScheme());
		pathBuffer.append("://");
		pathBuffer.append(this.pageContext.getRequest().getServerName());
		pathBuffer.append(":");
		pathBuffer.append(this.pageContext.getRequest().getServerPort());
		pathBuffer.append(((HttpServletRequest)this.pageContext.getRequest()).getContextPath());
		return pathBuffer.toString();
	}

	public String getUploadAction()
	{
		if(this.uploadAction==null)
			uploadAction = getServicePath() + "/attachment/upload.action";
		return uploadAction;
	}

	public void setUploadAction(String uploadAction)
	{
		this.uploadAction = this.getServicePath()+ "/"+uploadAction;
	}

	public String getDownloadAction()
	{
		if(this.downloadAction==null)
			downloadAction = this.getServicePath() + "/attachment/download.action";
		return downloadAction;
	}

	public void setDownloadAction(String downloadAction)
	{
		this.downloadAction = this.getServicePath() + "/"+downloadAction;
	}

	public String getUploadDestination()
	{
		if(this.uploadDestination==null)
			uploadDestination = SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH.replace("\\", "\\"+"\\");
		return uploadDestination;
	}

	public void setUploadDestination(String uploadDestination)
	{
		String temp = uploadDestination.replace("\\", "\\"+"\\"); //反斜杠在JS脚本中也需要转义，这样可以适应JSP页面中的“\”和“/”
		if(temp.endsWith("\\") || temp.endsWith("/"))
		{
			temp = temp.substring(0,temp.length()-1);
		}
		this.uploadDestination = temp;
	}

	public String getUploadBtnIsVisible()
	{
		if("false".equalsIgnoreCase(uploadable) || "true".equalsIgnoreCase(isAutoUpload))
		{
			uploadBtnIsVisible = "false";
		}
		return uploadBtnIsVisible;
	}

	public void setUploadBtnIsVisible(String uploadBtnIsVisible)
	{
		if ("true".equalsIgnoreCase(uploadBtnIsVisible)
		        || "false".equalsIgnoreCase(uploadBtnIsVisible))
		{
			uploadBtnIsVisible = uploadBtnIsVisible.toLowerCase();
		}
		else
			uploadBtnIsVisible = "true";
		this.uploadBtnIsVisible = uploadBtnIsVisible;
	}

	public String getAttchmentGroupId()
	{
		if (StringUtils.isBlank(attchmentGroupId))
		{
			attchmentGroupId = UUIDGenerator.getUUID();
		}
		return attchmentGroupId;
	}

	public void setAttchmentGroupId(String attchmentGroupId)
	{
		this.attchmentGroupId = attchmentGroupId;
	}

	public String getDownable()
	{
		return downable;
	}
	
	public void setDownable(String downable)
	{
		if ("true".equalsIgnoreCase(downable)
		        || "false".equalsIgnoreCase(downable))
		{
			downable = downable.toLowerCase();
		}
		else
			downable = "true";
		this.downable = downable;
	}

	public String getFileTypes()
	{
		return fileTypes;
	}

	public void setFileTypes(String fileTypes)
	{
		this.fileTypes = fileTypes;
	}

	public String getFileTypesDescription()
	{
		return fileTypesDescription;
	}

	public void setFileTypesDescription(String fileTypesDescription)
	{
		this.fileTypesDescription = fileTypesDescription;
	}

	public String getImageUrl()
	{
		if (StringUtils.isBlank(imageUrl))
		{
			//imageUrl = "http://www.swfupload.org/button_sprite.png";
			imageUrl = "";
		}
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getFlashUrl()
	{
		if (StringUtils.isBlank(flashUrl))
		{
			//flashUrl = "http://www.swfupload.org/swfupload.swf";
			flashUrl=this.getServicePath()+"/common/plugin/swfupload/js/swfupload.swf";
		}
		return flashUrl;
	}

	public void setFlashUrl(String flashUrl)
	{
		this.flashUrl = flashUrl;
	}

	public String getQueryAction()
	{
		if(this.queryAction==null)
			queryAction = this.getServicePath() + "/attachment/queryDownList.action";
		return queryAction;
	}

	public void setQueryAction(String queryAction)
	{
		this.queryAction = getServicePath()+"/"+queryAction;
	}

	public String getIsMultiUpload()
    {
    	if("false".equalsIgnoreCase(uploadable))
    		isMultiUpload = "false";
		return isMultiUpload;
    }

	public void setIsMultiUpload(String isMultiUpload)
    {
		if ("true".equalsIgnoreCase(isMultiUpload)
		        || "false".equalsIgnoreCase(isMultiUpload))
		{
			isMultiUpload = isMultiUpload.toLowerCase();
		}
		else
			isMultiUpload = "true";
		this.isMultiUpload = isMultiUpload;
    }

	public String getUploadable()
    {
    	return uploadable;
    }

	public void setUploadable(String uploadable)
    {
		if ("true".equalsIgnoreCase(uploadable)
		        || "false".equalsIgnoreCase(uploadable))
		{
			uploadable = uploadable.toLowerCase();
		}
		else
			uploadable = "true";
		this.uploadable = uploadable;
    }

	public String getIsMultiDownload()
    {
    	if("false".equalsIgnoreCase(downable))
    		isMultiDownload = "false";
		return isMultiDownload;
    }

	public void setIsMultiDownload(String isMultiDownload)
    {
		if ("true".equalsIgnoreCase(isMultiDownload)
		        || "false".equalsIgnoreCase(isMultiDownload))
		{
			isMultiDownload = isMultiDownload.toLowerCase();
		}
		else
			isMultiDownload = "true";
    	this.isMultiDownload = isMultiDownload;
    }

	public String getIsAutoUpload()
    {
    	return isAutoUpload;
    }

	public void setIsAutoUpload(String isAutoUpload)
    {
		if ("true".equalsIgnoreCase(isAutoUpload)
		        || "false".equalsIgnoreCase(isAutoUpload))
		{
			isAutoUpload = isAutoUpload.toLowerCase();
		}
		else
			isAutoUpload = "false";
		this.isAutoUpload = isAutoUpload;
    }

	public String getUploadTableVisible()
    {
    	if("false".equals(this.uploadable))
    		uploadTableVisible = "false";
		return uploadTableVisible;
    }

	public void setUploadTableVisible(String uploadTableVisible)
    {
		if ("true".equalsIgnoreCase(uploadTableVisible)
		        || "false".equalsIgnoreCase(uploadTableVisible))
		{
			uploadTableVisible = uploadTableVisible.toLowerCase();
		}
		else
			isAutoUpload = "false";
		this.uploadTableVisible = uploadTableVisible;
    }

	public String getProgressIsVisible()
    {
    	return progressIsVisible;
    }

	public void setProgressIsVisible(String progressIsVisible)
    {
		if ("true".equalsIgnoreCase(progressIsVisible)
		        || "false".equalsIgnoreCase(progressIsVisible))
		{
			progressIsVisible = progressIsVisible.toLowerCase();
		}
		else
			progressIsVisible = "true";
		this.progressIsVisible = progressIsVisible;
    }

	public String getImagePath() {
		if(this.imagePath==null)
			imagePath = this.getServicePath()+"/common/style/blue/";
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		if ("true".equalsIgnoreCase(isEdit)
		        || "false".equalsIgnoreCase(isEdit))
		{
			isEdit = isEdit.toLowerCase();
		}
		else
			isEdit = "true";
		this.isEdit = isEdit;
	}

	public String getDeleteAction() {
		if(this.deleteAction==null)
			deleteAction = this.getServicePath() + "/attachment/deleteAttachment.action";
		return deleteAction;
	}

	public void setDeleteAction(String deleteAction) {
		this.deleteAction = this.getServicePath() + "/"+deleteAction;
	}

	public String getAttachmentUser() {
		if(StringUtils.isBlank(attachmentUser))
		{
			attachmentUser = "";
		}
		return attachmentUser;
	}

	public void setAttachmentUser(String attachmentUser) {
		this.attachmentUser = attachmentUser;
	}

	public String getCreateDirByDate() {
		return createDirByDate;
	}

	public void setCreateDirByDate(String createDirByDate) {
		if ("true".equalsIgnoreCase(createDirByDate)
		        || "false".equalsIgnoreCase(createDirByDate))
		{
			createDirByDate = createDirByDate.toLowerCase();
		}
		else
			createDirByDate = "false";
		this.createDirByDate = createDirByDate;
	}

	public String getReturnValue() {
		if(this.returnValue==null)
			return "";
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	
	
	
}
