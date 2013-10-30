package com.ultrapower.eoms.mobile.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.mobile.client.MobileClientService;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.fileutil.FileOperUtil;

public class MobileAction extends BaseAction
{
	private static final long serialVersionUID = -6701714649445281087L;
	
	private List<File> file;
	private List<String> fileFileName;
	private List<String> fileContentType;
	
	private String serviceCode;
	private String inputXml;
	
	private String outputXml;
	
	private String hasPic;
	private String hasRec;
	
	private MobileClientService mobileClient;

	public String call()
	{
		try
		{
			System.out.println("Time---------->"+"begin  serviceCode:"+serviceCode +"  "+TimeUtils.getCurrentDate()+" "+TimeUtils.getCurrentTime());
			System.out.println(inputXml);
			String xml = "";
			
			
			Object loginArthorize = this.getSession().getAttribute("LOGIN");
			if(!serviceCode.equals("L001") && (loginArthorize == null || !loginArthorize.toString().equals("LOGIN_SUCCESS")))
			{
				xml = "<opDetail><baseInfo><isLegal>0</isLegal></baseInfo></opDetail>";
			}
			if(serviceCode.equals("L000"))
			{
				this.getSession().removeAttribute("LOGIN");
			}
			else
			{
				if (hasPic == null || hasPic.equals("")) hasPic = "0";
				if (hasRec == null || hasRec.equals("")) hasRec = "0";
				xml = mobileClient.invoke(serviceCode, inputXml, Integer.valueOf(hasPic), Integer.valueOf(hasRec), file, fileFileName);
			}
			if(serviceCode.equals("L001") && xml.indexOf("<isLegal>1</isLegal>") > 0)
			{
				this.getSession().setAttribute("LOGIN", "LOGIN_SUCCESS");
			}
			
			if(serviceCode.equals("G007"))
			{
				System.out.println(xml);
				InputStream zipFile = new FileInputStream(xml);
				downing(zipFile, "offline.zip");
			}
			else if(serviceCode.equals("G008"))
			{
				System.out.println(xml);
				InputStream zipFile = new FileInputStream(xml);
				downing(zipFile, "template.zip");
				
				FileOperUtil.deleteFile(xml);
			}
			else if(serviceCode.equals("V002"))
			{
				Document doc = DocumentHelper.parseText(xml);
				Node recordInfoNode = doc.selectSingleNode("//opDetail");
				String path = recordInfoNode.selectSingleNode("url").getText();
				String filePath = Constants.APP_UPLOAD_PATH + (Constants.APP_UPLOAD_PATH.endsWith(File.separator) ? "" : File.separator) + path;
				InputStream appFile = new FileInputStream(filePath);
				downing(appFile, UUIDGenerator.getUUID() + ".apk");
			}
			else
			{
				try
				{
					PrintWriter out = getResponse().getWriter();
					System.out.println(xml);
					getResponse().setContentType("text/xml;charset=UTF-8");
					out.print(xml);
					out.close();
					out.flush();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
				outputXml = xml;
			}
			System.out.println("Time---------->"+"end  serviceCode:"+ serviceCode +"  "+ TimeUtils.getCurrentDate()+" "+TimeUtils.getCurrentTime());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;  
		}
		return null;
	}
	
	/**
	 * 真正的下载实现，输出流文件
	 * @param fileInputStream
	 */
	protected void downing(InputStream fileInputStream, String fileName)
	{
		if(fileInputStream==null){
			try {
				getResponse().getOutputStream().write("Sorry, file(s) can't be found!".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/octet-stream");
		try
		{
			//System.out.println("fileName1:"+fileName);
			fileName = URLEncoder.encode(fileName, "UTF-8");
			//System.out.println("fileName2:"+fileName);
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(e.getMessage(), e);
		}
		
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		BufferedInputStream bis = new BufferedInputStream(fileInputStream);
		BufferedOutputStream bos = null;
		try
		{
			response.setContentLength(fileInputStream.available());
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

	public String getOutputXml()
	{
		return outputXml;
	}

	public void setOutputXml(String outputXml)
	{
		this.outputXml = outputXml;
	}

	public MobileClientService getMobileClient()
	{
		return mobileClient;
	}

	public void setMobileClient(MobileClientService mobileClient)
	{
		this.mobileClient = mobileClient;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getServiceCode()
	{
		return serviceCode;
	}

	public void setServiceCode(String serviceCode)
	{
		this.serviceCode = serviceCode;
	}

	public String getInputXml()
	{
		return inputXml;
	}

	public void setInputXml(String inputXml)
	{
		this.inputXml = inputXml;
	}

	public String getHasPic()
	{
		return hasPic;
	}

	public void setHasPic(String hasPic)
	{
		this.hasPic = hasPic;
	}

	public String getHasRec()
	{
		return hasRec;
	}

	public void setHasRec(String hasRec)
	{
		this.hasRec = hasRec;
	}

	public List<File> getFile()
	{
		return file;
	}

	public void setFile(List<File> file)
	{
		this.file = file;
	}

	public List<String> getFileFileName()
	{
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName)
	{
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType)
	{
		this.fileContentType = fileContentType;
	}
}
