package com.ultrapower.eoms.common.plugin.swfupload.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

@SuppressWarnings("serial")
public class AttachmentManagerAction extends BaseAction
{
	private AttachmentManagerService attachmentManagerService;
	private UserManagerService userManagerService;
	private ArrayList<String> downablelist = new ArrayList<String>(); // 批量下載文件列表
	// upload file parameters
	private String fileFileName;
	private String fileNewName;
	private String fileTypes;
	private File file;
	private String fileContentType;
	private String fileTypesDescription;//文件描述
	private String savePath;
	private String createDirByDate;
	// biz parameters
	private String attachmentUser; //附件人，由谁定制的附件（如果为空，则从session中去）
	private String attachmentGroupId;
	private String attachmentId;
	private boolean isBatchDown; // 是否批量下载
	private List<Map<String, String>> items;
	private String hasDownFile; //下载文件是否存在
	private String sessionId; // current sessionId
	//返回值格式：1-文件名；2-文件真实名；3-存储路径；4-文件大小；5-文件类型；6-关系ID；
	private String returnValue;
	

	/**
	 * 上传附件
	 */
	@SuppressWarnings( { "deprecation", "static-access" })
	public String upload() throws Exception
	{
		String repositoryPath = null;
		// 存放附件路径
		if (file != null)
		{
			if(savePath!=null)
			{
				if(SwfuploadUtil.isAbsolutePath(savePath))
				{//绝对路径
					if("true".equals(getCreateDirByDate()))
					{
						repositoryPath = this.savePath+System.getProperty("file.separator")
											+SwfuploadUtil.getFileName_yearMonth();
					}
					else //if("false".equals(getCreateDirByDate()))
					{
						repositoryPath = this.savePath;
					}
				}
				else
				{//相对路径
					if("true".equals(getCreateDirByDate()))
					{
						repositoryPath = this.getSession().getServletContext().getRealPath(this.savePath)
											+System.getProperty("file.separator")+SwfuploadUtil.getFileName_yearMonth();
					}
					else if("false".equals(getCreateDirByDate()))
					{
						repositoryPath = this.getSession().getServletContext().getRealPath(this.savePath);
					}
					
				}
			}
			File diskFile = enSureDir(repositoryPath, reName(getFileFileName())); //建立附件文件
			FileUtils.copyFile(file, diskFile);
		}

		// 保存附件的相关信息到数据库
		UserSession userSession = this.getUserSession();
		String userid = (attachmentUser==null || "".equals(attachmentUser))?userSession.getPid():attachmentUser;
		long createtime = TimeUtils.getCurrentTime();

		Attachment attachment = new Attachment();
		attachment.setCreater(userid);
		attachment.setLastmodifier(userid);
		attachment.setCreatetime(createtime);
		attachment.setName(getFileFileName());
		attachment.setRealname(getFileNewName());
		String fileType = getFileFileName().substring(getFileFileName().lastIndexOf(".")+1);
		attachment.setType(fileType);
		attachment.setPath("true".equalsIgnoreCase(getCreateDirByDate())?getSavePath()
				+System.getProperty("file.separator")+SwfuploadUtil.getFileName_yearMonth():getSavePath());
		attachment.setRelationcode(getAttachmentGroupId());
		String fileSize = ""+BigDecimal.valueOf(file.length()).divide(BigDecimal.valueOf(1024L),1,BigDecimal.ROUND_CEILING)+"KB";
		attachment.setAttsize(fileSize);
		// 入库操作
		attachmentManagerService.addAttachment(attachment);
		// 返回记录id
		attachmentId = getAttachmentGroupId();
		returnValue = parseReturnValue(repositoryPath,fileSize,attachmentId,fileType);
		return SUCCESS;
	}

	/**
	 * 下载附件，批量和单个附件下载
	 */
	public void downloadAttachment()
	{
		if (isBatchDown()) // 批量下载
		{
			//将客户端传回的多文件名参树按照一定格式进行分解
			//例如 客户端参数："picture1.jpg:xxxx.jpg,picture2.jpg:yyyy.jpg" 分解后数组：{picture1.jpg:xxxx.jpg,picture2.jpg:yyyy.jpg}
			String[] fileNamePairs = null;
			try {
				fileNamePairs = SwfuploadUtil.parseFileNamePair(URLDecoder.decode(getFileNewName(), "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String rootPath = null;
			try {
				rootPath = URLDecoder.decode(savePath, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			if(rootPath==null || "".equals(rootPath))
			{
				RecordLog.printLog("附件保存路径没有设置，无法下载文件！",RecordLog.LOG_LEVEL_ERROR);
				return;
			}
			if(!SwfuploadUtil.isAbsolutePath(rootPath))
			{
				rootPath = this.getSession().getServletContext().getRealPath(rootPath);
			}
			String tempDir = SwfuploadUtil.batchCopyFile(rootPath, fileNamePairs);
			try
			{
				String zipfilename = SwfuploadUtil.packZip(tempDir);
				InputStream fileInputStream = new FileInputStream(new File(
				        zipfilename));
				setFileFileName(SwfuploadUtil.SWFUPLOAD_BATCH_FILENAME);
				downing(fileInputStream);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally //删除临时目录
			{
				File dir = new File(tempDir);
				if (dir.exists())
				{
					try
					{
						SwfuploadUtil.deleteDirectory(tempDir);
					}
					catch (IOException e)
					{
						System.out.println(tempDir + "\n删除失败,请手动删除!");
					}
				}
			}
		}
		else //单个文件下载
		{
			try{
				File f = null;
				if(savePath==null || getFileNewName()==null || getFileFileName()==null)
					return;
				String tempPath = URLDecoder.decode(savePath,"UTF-8");
				String realName = URLDecoder.decode(getFileNewName(),"UTF-8");
				if(SwfuploadUtil.isAbsolutePath(tempPath))
				{
					f = this.enSureDir(tempPath, realName);
				}
				else
				{
					f = new File(this.getSession().getServletContext().getRealPath(tempPath),realName);
				}
				if(f.exists())
				{
					InputStream fileInputStream = new FileInputStream(f);
					downing(fileInputStream);
				}	
				else
				{
					HttpServletRequest request = this.getRequest();
					RequestDispatcher dispatch = getSession().getServletContext().getRequestDispatcher("/common/plugin/swfupload/attachProblem.jsp");
					if(this.getFileFileName()!=null)
						getRequest().setAttribute("message", "该文件不存在，或被删除："+URLDecoder.decode(this.getFileFileName(), "UTF-8")+"!");
					else
						getRequest().setAttribute("message", "文件不存在!");
					dispatch.forward(request, getResponse());
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

	}

	/**
	 * 真正的下载实现，输出流文件
	 * @param fileInputStream
	 */
	protected void downing(InputStream fileInputStream)
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/octet-stream");
		String fileName = getFileFileName();
		try
		{
			fileName = URLDecoder.decode(fileName, "UTF-8");
//			fileName = new String(fileName.getBytes("iso-8859-1"), "utf-8");
			fileName = new String(fileName.getBytes(), "iso8859-1");
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(e.getMessage(), e);
		}
		
		response.setHeader("Content-disposition", "attachment;filename="
		        + fileName);
		BufferedInputStream bis = new BufferedInputStream(fileInputStream);
		BufferedOutputStream bos = null;
		try
		{
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
			{
				bos.write(buff, 0, bytesRead);
			}

		}
		catch (Exception e)
		{
			if("ClientAbortException".equals(e.getClass().getSimpleName()))
			{
				System.out.println("----->Socket异常，可能原因是客户端中断了附件下载。");
			}
			else
				log.error(e.getMessage(), e);
		}
		finally
		{
			try
			{
				if (bos != null)
				{
					bos.close();
				}
			}
			catch (IOException e)
			{
				log.error(e.getMessage(), e);
			}

			try
			{
				if (bis != null)
				{
					bis.close();
				}
			}
			catch (IOException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 根据附件ID删除附件
	 */
	public void deleteAttachment()
	{
		boolean b = false;
		String attachmentId = this.getRequest().getParameter("attachmentId");
		if(attachmentId!=null)
		{
			Attachment attach = attachmentManagerService.getAttachmentById(attachmentId);
			if(attach!=null)
			{
				this.setSavePath(attach.getPath());
				this.setFileNewName(attach.getRealname());
				try{
					File f = null;
					if(SwfuploadUtil.isAbsolutePath(savePath))
					{
						f = this.enSureDir(getSavePath(), getFileNewName());
					}
					else
					{
						f = new File(this.getSession().getServletContext().getRealPath(savePath),getFileNewName());
					}
					if(f.exists())
					{
						f.delete();
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				attachmentManagerService.deleteAttachmentById(attachmentId);
				b = true;
			}
		}
		try {
			this.getResponse().getWriter().print(String.valueOf(b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断是否有此文件可以下载
	 * @return
	 */
	public String hasDownFile()
	{
		String temp_path = savePath;
		if(!SwfuploadUtil.isAbsolutePath(temp_path))
		{
			temp_path = this.getSession().getServletContext().getRealPath(temp_path);
		}
		String downloadFile = temp_path + File.separator + getFileNewName();
		InputStream is = ServletActionContext.getServletContext()
		        .getResourceAsStream(downloadFile);
		if (is == null)
		{
			this.hasDownFile = "false";
		}

		return SUCCESS;
	}

	/**
	 * 根据attachGroupId获得下载列表
	 * @return
	 */
	public String queryDownList()
	{
		items = new ArrayList<Map<String, String>>();
		List<Attachment> attachments = attachmentManagerService
		        .getAttachmentByRelation(getAttachmentGroupId());
		if (attachments != null && !attachments.isEmpty())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Attachment attachment : attachments)
			{
				Map<String, String> item = new HashMap<String, String>();
				item.put("fileFileName",attachment.getName());
				item.put("fileNewName", attachment.getRealname());
				item.put("savePath", attachment.getPath());
				item.put("attachmentId", attachment.getPid());
				item.put("attSize", attachment.getAttsize());
				item.put("uploadTime", sdf.format(new Date(attachment.getCreatetime()*1000)));
				item.put("attUser", userManagerService.getUserNameByID(attachment.getCreater()));
				// 可下载附件项
				items.add(item);
			}
		}
		//item将会以JSON数组的格式返回：[{"fileFileName":"xxx","fileNewName":"yyy","savePath":"www","attachmentId":"ididid"},{...},{...}]
		return SUCCESS;
	}

	/**
	 * 创建文件
	 * @param filePath 父路径
	 * @param fileName 文件名
	 * @return 文件
	 */
	protected File enSureDir(String filePath, String fileName)
	{
		File file = new File(filePath);
		if (!file.exists())
			file.mkdirs();
		file = new File(filePath, fileName);
		return file;
	}

	/**
	 * 重命名文件名
	 * @param oldName
	 * @return
	 */
	protected String reName(String oldName)
	{

		if (StringUtils.isNotBlank(oldName))
		{
			String suffix = StringUtils.substring(oldName, oldName
			        .lastIndexOf("."));
			String currentUUID = UUIDGenerator.getUUIDoffSpace();
			setFileNewName(currentUUID + suffix);
		}
		return getFileNewName();
	}

	/*
	 * 一下为属性的GET/SET方法
	 */
	public void setAttachmentManagerService(
	        AttachmentManagerService attachmentManagerService)
	{
		this.attachmentManagerService = attachmentManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public String getFileFileName()
	{
		return fileFileName;
	}

	public void setFileFileName(String fileFileName)
	        throws UnsupportedEncodingException
	{
		this.fileFileName = fileFileName;
	}

	public String getFileNewName()
	{
		return fileNewName;
	}

	public void setFileNewName(String fileNewName)
	{
		this.fileNewName = fileNewName;
	}

	public String getFileTypes()
	{
		return fileTypes;
	}

	public void setFileTypes(String fileTypes)
	{
		this.fileTypes = fileTypes;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	public String getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}

	public String getSavePath()
	{
		return savePath;
	}

	public void setSavePath(String savePath)
	{
		
		this.savePath = SwfuploadUtil.pathProcess(savePath);
	}

	public String getAttachmentGroupId() 
	{
		return attachmentGroupId;
	}

	public void setAttachmentGroupId(String attachmentGroupId)
	{
		this.attachmentGroupId = attachmentGroupId;
	}

	public String getAttachmentId()
	{
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId)
	{
		this.attachmentId = attachmentId;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public List<Map<String, String>> getItems()
	{
		if (items == null)
			items = new ArrayList<Map<String, String>>();
		return items;
	}

	public void setItems(List<Map<String, String>> items)
	{
		this.items = items;
	}

	public String getHasDownFile()
	{
		return hasDownFile;
	}

	public void setHasDownFile(String hasDownFile)
	{
		this.hasDownFile = hasDownFile;
	}

	public ArrayList<String> getDownablelist()
	{
		return downablelist;
	}

	public void setDownablelist(ArrayList<String> downablelist)
	{
		this.downablelist = downablelist;
	}

	public boolean isBatchDown()
	{
		return isBatchDown;
	}

	public void setIsBatchDown(boolean isBatchDown)
	{
		this.isBatchDown = isBatchDown;
	}
	
	public String getAttachmentUser() {
		return attachmentUser;
	}

	public void setAttachmentUser(String attachmentUser) {
		this.attachmentUser = attachmentUser;
	}
	
	public String getFileTypesDescription() {
		return fileTypesDescription;
	}

	public void setFileTypesDescription(String fileTypesDescription) {
		this.fileTypesDescription = fileTypesDescription;
	}

	public String getCreateDirByDate() {
		return createDirByDate;
	}

	public void setCreateDirByDate(String createDirByDate) {
		this.createDirByDate = createDirByDate;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	
	public String parseReturnValue(String repositoryPath,String fileSize,String relationId,String fileType)
	{
		String formatStr = this.returnValue;
		if(formatStr==null || "".equals(formatStr))
			return "";
		String temp[] = formatStr.split(",");
		StringBuffer result = new StringBuffer();
		for(int i=0;i<temp.length;i++)
		{
			String tag = temp[i];
			result.append(",");
			if("1".equals(tag))
			{
				result.append(getFileFileName());
			}
			else if("2".equals(tag))
			{
				result.append(getFileNewName());
			}
			else if("3".equals(tag))
			{
				result.append(repositoryPath);
			}
			else if("4".equals(tag))
			{
				result.append(fileSize);
			}
			else if("5".equals(tag))
			{
				result.append(fileType);
			}
			else if("6".equals(tag))
			{
				result.append(relationId);
			}
		}
		if(result.length()>0)
			return result.substring(1);
		else
			return result.toString();
	}
	
	private String getServicePath()
	{
		StringBuffer pathBuffer = new StringBuffer(getRequest().getScheme());
		pathBuffer.append("://");
		pathBuffer.append(getRequest().getServerName());
		pathBuffer.append(":");
		pathBuffer.append(getRequest().getServerPort());
		pathBuffer.append(getRequest().getContextPath());
		pathBuffer.append("/");

		return pathBuffer.toString();
	}

	public AttachmentManagerService getAttachmentManagerService() {
		return attachmentManagerService;
	}

}