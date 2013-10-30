package cn.com.ultrapower.interfaces.util.attachment;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import cn.com.ultrapower.interfaces.util.ftp.FTPManager;
import cn.com.ultrapower.interfaces.util.http.HttpGet;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachmentInfo;
import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class AttachRefManage {

	public List getAttachInfoList(String p_StrAttachInfoXml) throws Exception
	{
		if(p_StrAttachInfoXml == null || p_StrAttachInfoXml.equals(""))
		{
			return new ArrayList();
		}
		SAXBuilder bu = new SAXBuilder();
		StringReader m_StringReader = new StringReader(p_StrAttachInfoXml);
		Document doc = bu.build(m_StringReader);
		Element rootElement = doc.getRootElement();

		List m_AttachInfoListXml = rootElement.getChildren("attachInfo");
		List m_AttachInfoList 	 = new ArrayList();
		for (Iterator it = m_AttachInfoListXml.iterator(); it.hasNext();)
		{
			Element ptypeElement = (Element) it.next();
			AttachInfoModel m_AttachInfoModel = new AttachInfoModel();
			m_AttachInfoModel.setAttachLength(ptypeElement.getChild("attachLength").getText());
			m_AttachInfoModel.setAttachName(ptypeElement.getChild("attachName").getText());
			m_AttachInfoModel.setAttachURL(ptypeElement.getChild("attachURL").getText());
			m_AttachInfoList.add(m_AttachInfoModel);
		}
		return m_AttachInfoList;
	}

	public List getAttachLocaList(String p_StrAttachInfoXml,String p_SaveFilePath) throws Exception
	{
		List m_AttachInfoList = getAttachInfoList(p_StrAttachInfoXml);
		List m_FilePathList   = new ArrayList();
		if (m_AttachInfoList.size()>0)
		{
			HttpGet oInstance = null;
			//String folderStr = InterfaceUtil.sysPath + File.separator + "attachments" + File.separator + System.currentTimeMillis()/1000 + File.separator;
			String folderStr="";
			if(p_SaveFilePath.trim().equals(""))
				folderStr= Constants.sysPath + File.separator + "attachments" + File.separator;
			else
				folderStr=p_SaveFilePath + File.separator;
			
			File f = new File(folderStr);
			f.mkdir();
			for (Iterator it = m_AttachInfoList.iterator(); it.hasNext();)
			{
				AttachInfoModel m_AttachInfoModel = (AttachInfoModel) it.next();
				System.out.println("A wait-download file:" + m_AttachInfoModel.getAttachURL());
				System.out.println("A wait-download file is a " + m_AttachInfoModel.getAttachURL().substring(0, 1) + " file.");
				if(m_AttachInfoModel.getAttachURL().substring(0, 1).equals("h") || m_AttachInfoModel.getAttachURL().substring(0, 1).equals("h"))
				{
					if(oInstance == null)
					{
						oInstance = new HttpGet();
					}
					System.out.println("A http file:" + m_AttachInfoModel.getAttachURL());
					oInstance.addItem(m_AttachInfoModel.getAttachURL(),folderStr + m_AttachInfoModel.getAttachName());
					BaseAttachmentInfo m_BaseAttachment = new BaseAttachmentInfo(folderStr + m_AttachInfoModel.getAttachName());
					m_FilePathList.add(m_BaseAttachment);
				}
				else if(m_AttachInfoModel.getAttachURL().substring(0, 1).equals("f") || m_AttachInfoModel.getAttachURL().substring(0, 1).equals("f"))
				{
					System.out.println("A ftp file:" + m_AttachInfoModel.getAttachURL());
					String path = m_AttachInfoModel.getAttachURL();
					//FileDownLoad m_FileDownLoad = new FileDownLoad();
					try
					{
						String ftpUrl = m_AttachInfoModel.getAttachURL();
						FTPManager fTPManager = new FTPManager(ftpUrl.substring(0,ftpUrl.lastIndexOf("/")));
						if(fTPManager.download(folderStr,m_AttachInfoModel.getAttachName()))
						{
							System.out.println("Downloaded ftp file");
							BaseAttachmentInfo m_BaseAttachment = new BaseAttachmentInfo(folderStr + m_AttachInfoModel.getAttachName());
							m_FilePathList.add(m_BaseAttachment);
						}
						else
						{
							System.out.println("Ftp file: download failed!");
						}						
					}
					catch(Throwable e)
					{
						e.printStackTrace();
						System.out.println("Ftp file: download ERROR!");
					}
					
				}
			}
			if(oInstance != null)
			{
				oInstance.downLoadByList_NewName();
				System.out.println("Downloaded all http files!");
			}
		}
		System.out.println("Downloaded " + m_FilePathList.size() + " files.");
		return m_FilePathList;
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//InterfaceUtil.sysPath = "d:\\WorkSpace\\QHEOMS\\WebRoot\\WEB-INF";
		AttachRefManage m_AttachRefManage = new AttachRefManage();
		m_AttachRefManage.getAttachLocaList("<attachRef><attachInfo><attachName>13519747560(三月语音业务清单).xls</attachName><attachURL>ftp://icd:icd@135.194.22.4/Deal/2007040700005764/13519747560(三月语音业务清单).xls</attachURL><attachLength>30720</attachLength></attachInfo></attachRef>","");

	}

}
