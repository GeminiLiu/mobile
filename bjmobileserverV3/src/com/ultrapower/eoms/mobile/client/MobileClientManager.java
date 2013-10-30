package com.ultrapower.eoms.mobile.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import org.apache.tools.zip.*;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.fileutil.FileOperUtil;
import com.ultrapower.eoms.mobile.model.SyncAttachment;
import com.ultrapower.eoms.mobile.utils.MobileUtil;
import com.ultrapower.eoms.mobile.ws.MobileService;
import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;

public class MobileClientManager implements MobileClientService
{
	private IDao<SyncAttachment> syncAttachDao;
	
	public String invoke(String serviceCode, String inputXml, int hasPic, int hasRec, List<File> file, List<String> fileName) throws Exception
	{
		System.out.println(serviceCode);
		String serverName = "";
		if(serviceCode.indexOf("G") > -1)
		{
			serverName = "eomswf";
		}
		else if(serviceCode.indexOf("PT") > -1)
		{
			serverName = "mobilePatrolTask";
		}
		else if(serviceCode.equals("L003")){
			serverName = "positionTrack";
		}
		else if(serviceCode.indexOf("L") > -1 || serviceCode.indexOf("T") > -1 || serviceCode.indexOf("V") > -1)
		{
			serverName = "authorize";
		}
		else
		{
			serverName = "mams";
		}
		
		StringBuilder fileList = new StringBuilder();
		String fileStr = "";
		
		if(serviceCode.equals("G004"))
		{
			Document doc = DocumentHelper.parseText(inputXml);
			Node recordInfoNode = doc.selectSingleNode("//opDetail/recordInfo");
			String baseSchema = recordInfoNode.selectSingleNode("category").getText().replace(":", "_");
			String taskID = recordInfoNode.selectSingleNode("taskId").getText();
			String date = TimeUtils.getCurrentDate("yyyy-MM-dd");
			String sp = File.separator;
			String ctxPath = Constants.WORKSHEET_UPLOAD_PATH;
			String relaPath = "common" + sp + "workflow_attachment" + sp + baseSchema + sp + date;
			String dirPath = ctxPath + sp + relaPath + sp;
			RandomN random = new Random15();
			String fileSp = "|";
			if("".equals(taskID)){
				taskID = random.getRandom(System.currentTimeMillis());
				recordInfoNode.selectSingleNode("taskId").setText(taskID);
				inputXml = doc.asXML();
			}
			
			if(hasPic > 0)
			{
				for(int i = 0; i < hasPic; i++)
				{
					String picName = random.getRandom(System.currentTimeMillis());
					String picPath = dirPath + picName;
					fileList.append(fileSp + "照片上传中。。。.tmp" + "*" + picName + "*" + picPath + "*" + date);
					
					System.out.println("上传临时照片文件：" + picPath);
					
					File destFile = new File(picPath);
					FileUtils.touch(destFile);
					destFile.createNewFile();
					
					SyncAttachment sa = new SyncAttachment();
					sa.setTaskID(taskID);
					sa.setDbName(picName);
					sa.setType("PIC");
					sa.setPath(dirPath);
					syncAttachDao.save(sa);
				}
			}
			if(hasRec > 0)
			{
				for(int i = 0; i < hasRec; i++)
				{
					String recName = random.getRandom(System.currentTimeMillis());
					String recPath = dirPath + recName;
					fileList.append(fileSp + "录音上传中。。。.tmp" + "*" + recName + "*" + recPath + "*" + date);
	
					System.out.println("上传临时录音文件：" + recPath);
					
					File destFile = new File(recPath);
					FileUtils.touch(destFile);
					destFile.createNewFile();
	
					SyncAttachment sa = new SyncAttachment();
					sa.setTaskID(taskID);
					sa.setDbName(recName);
					sa.setType("REC");
					sa.setPath(dirPath);
					syncAttachDao.save(sa);
				}
			}
			
			if(fileList.length() > 0) fileStr = fileList.substring(1);
		}
		
		if(serviceCode.equals("G006"))
		{
			String result = "";
			if(fileName != null && !fileName.equals(""))
			{
				try
				{
					System.out.println("@@@@@FileName=" + fileName);
					String[] fileArr = fileName.get(0).split("_");
					String fileType = fileArr[0];
					String taskID = fileArr[1];
					String type = fileArr[2];
					String specialty = fileArr[3];
					String realName = fileArr[fileArr.length - 1];
					
					List<SyncAttachment> syncAttachList = syncAttachDao.find("from SyncAttachment where TaskID=? and Type=?", new Object[] {taskID, type});
					ZipFile zipFile = new ZipFile(file.get(0));
					Enumeration<? extends ZipEntry> entries = zipFile.getEntries();
					
					int i = 0;
					InputStream inputStream;
					String fileSp = "|";
					while(entries.hasMoreElements())
					{
						String dbName = "";
						String path ="";
						String relaPath = "";
						String savePath = "";
						String attachID = "";
						SyncAttachment syncAttach = null;
						if ("GD".equals(fileType)) {
							if(i < syncAttachList.size()){
								syncAttach = syncAttachList.get(i);
								dbName = syncAttach.getDbName();
								path = syncAttach.getPath();
								attachID = syncAttach.getAttachID();
							}
						} else {
							RandomN random = new Random15();
							dbName = random.getRandom(System.currentTimeMillis());
							String date = TimeUtils.getCurrentDate("yyyy-MM-dd");
							String sp = File.separator;
							relaPath = specialty + sp + date;
							path = Constants.PATROL_UPLOAD_PATH + sp + relaPath + sp;
							savePath = specialty + "/" + date;
						}
						
						System.out.println("从数据库中获取" + type + "文件路径：" + path + dbName);
						System.out.println("获取上传文件个数：" + file.size());
	
						ZipEntry zn = (ZipEntry) entries.nextElement();
						inputStream = zipFile.getInputStream(zn);
						File f = new File(path + dbName);
						File dir = f.getParentFile();
						if(dir!=null)
						{
							dir.mkdirs();
						}
						System.out.println(zn.getName() + "---" + zn.getSize());
						realName = zn.getName();

				        FileOutputStream outputStream = null;
				        String fileWholeName = path + dbName;
				        if(fileWholeName.length()>0)
				        {
				        	outputStream = new FileOutputStream(fileWholeName);  
				        	int len = 0;  
				        	byte bufer[] = new byte[1024];  
				        	while(-1 != (len=inputStream.read(bufer)))  
				        	{  
				        		outputStream.write(bufer, 0, len);  
				        	}  
				        	outputStream.close();  
				        }
						
						String size = zn.getSize()/1000 + "KB";
						System.out.println(">>>>>>realName：" + realName + ",realName=" + realName + ",realName.lastIndexOf(\".\") + 1=" + realName.lastIndexOf("\\.") + 1);
						String suffix = realName.substring(realName.lastIndexOf(".") + 1);
						if ("GD".equals(fileType)) {
							inputXml = "<opDetail><recordInfo><dbname>"+dbName+"</dbname><realname>"+realName+"</realname><size>"+size+"</size><suffix>"+attachID+"</suffix></recordInfo></opDetail>";
//							if (syncAttach != null)
//								syncAttachDao.remove(syncAttach);
						} else if ("XJ".equals(fileType)) {
							serverName = "mobilePatrolTask";
							serviceCode = "PT004";
							inputXml = "<opDetail><recordInfo><name>"
									+ realName + "</name><realname>" + dbName
									+ "</realname><relationcode>" + taskID
									+ "</relationcode><type>" + suffix
									+ "</type><path>" + savePath
									+ "</path><attsize>" + size
									+ "</attsize></recordInfo></opDetail>"; 
						}
						String date = TimeUtils.getCurrentDate("yyyy-MM-dd");
						fileList.append(fileSp + realName + "*" + dbName + "*" + path+dbName + "*" + date);
						MobileService itSysWsFacade = MobileUtil.getItSysWsFacade(serverName);
						result = itSysWsFacade.invoke(serviceCode, inputXml, fileList.toString());
						if ("GD".equals(fileType) && result.contains("<success>1</success>")) {
							if (syncAttach != null)
								syncAttachDao.remove(syncAttach);
						}
						i++;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return result;
		}
		
		String result = "";
		if(!serviceCode.equals("G006") && !serviceCode.equals("G007"))
		{
			MobileService itSysWsFacade = MobileUtil.getItSysWsFacade(serverName);
			System.out.println("serviceCode:>>>>>>" + serviceCode);
			System.out.println("inputXml:>>>>>>" + inputXml);
			System.out.println("fileList:>>>>>>" + fileStr);
			result = itSysWsFacade.invoke(serviceCode, inputXml, fileStr);
			System.out.println("RESULT:>>>>>>"+result);
			System.out.println("-------------------------------");
		}
		
		if(serviceCode.equals("G007"))
		{
			Document doc = DocumentHelper.parseText(inputXml);
			Node baseInfoNode = doc.selectSingleNode("//opDetail/baseInfo");
			String userName = baseInfoNode.selectSingleNode("userName").getText();
			
			Node recordInfoNode = doc.selectSingleNode("//opDetail/recordInfo");
			List<Node> itemNodes = recordInfoNode.selectNodes("item");
			
			String rootDir = Constants.OFFLINE_TEMP_PATH + File.separator + System.currentTimeMillis();
			
			FileOperUtil.newFolder(rootDir);
			
			for(Node item : itemNodes)
			{
				String taskID = item.valueOf("@taskid");
				
				String taskDir = rootDir + File.separator + "prezip" + File.separator + taskID;
				FileOperUtil.newFolder(taskDir);
				
				String[] inforstr = item.getText().split(",");
				String baseID = inforstr[0];
				String baseSchema = inforstr[1];
				String input_G002 = buildserviceXml(userName, baseID, baseSchema, taskID, null, null);
				MobileService itSysWsFacade = MobileUtil.getItSysWsFacade(serverName);
				String dataxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + itSysWsFacade.invoke("G002", input_G002, "");
				FileOperUtil.newXMLFile(taskDir + File.separator + "data.xml", DocumentHelper.parseText(dataxml));
				
				Document dataDoc = DocumentHelper.parseText(dataxml);
				List<Node> actionNodes = dataDoc.selectNodes("//opDetail/baseInfo/actionOps/actionop");
				for(Node actionNode : actionNodes)
				{
					String actionID = actionNode.valueOf("@id");
					String actionCode = actionNode.valueOf("@code");
					String input_G003 = buildserviceXml(userName, baseID, baseSchema, taskID, actionID, actionCode);
					String actionXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + itSysWsFacade.invoke("G003", input_G003, "");
					FileOperUtil.newXMLFile(taskDir + File.separator
							+ actionCode + "-" + actionID + ".xml",
							DocumentHelper.parseText(actionXml));
				}
			}
			
			String zipFileName = rootDir + "-offline.zip";
			File inFile = new File(rootDir + File.separator + "prezip");
			FileOutputStream fileOut = new FileOutputStream(zipFileName);
			ZipOutputStream zipOut = new ZipOutputStream(fileOut);
			zipOut.setComment("离线工单信息包");
			zip(zipOut, inFile, "");
			zipOut.close();
			
			FileOperUtil.delFolder(rootDir);
			
			result = zipFileName;
		}

		if(serviceCode.equals("G008"))
		{
			Document doc = DocumentHelper.parseText(result);
			Node baseInfoNode = doc.selectSingleNode("//opDetail/baseInfo");
			
			Node attachNode = baseInfoNode.selectSingleNode("attachs");
			List<Node> itemNodes = attachNode.selectNodes("attach");
			
			String rootDir = Constants.OFFLINE_TEMP_PATH + File.separator + System.currentTimeMillis();
			
			FileOperUtil.newFolder(rootDir);
			FileOperUtil.newFolder(rootDir + File.separator + "prezip");
			
			for(Node item : itemNodes)
			{
				String oldPath = item.getText();
				String name = item.valueOf("@name");
				String newPath = rootDir + File.separator + "prezip" + File.separator + name;
				FileOperUtil.copyFile(oldPath, newPath);
				FileOperUtil.deleteFile(oldPath);
			}
			
			String zipFileName = rootDir + "-attach.zip";
			File inFile = new File(rootDir + File.separator + "prezip");
			FileOutputStream fileOut = new FileOutputStream(zipFileName);
			ZipOutputStream zipOut = new ZipOutputStream(fileOut);
			zipOut.setComment("工单附件包");
			zip(zipOut, inFile, "");
			zipOut.close();
			
			FileOperUtil.delFolder(rootDir);
			
			result = zipFileName;
		}
		
		return result;
	}
	
	private String buildserviceXml(String userName, String baseID, String baseSchema, String taskID, String actionID, String actionCode)
	{
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<opDetail>");
		xmlBuilder.append("<baseInfo><userName>" + userName + "</userName></baseInfo>");
		xmlBuilder.append("<recordInfo>");
		xmlBuilder.append("<baseId>" + baseID + "</baseId>");
		xmlBuilder.append("<category>" + baseSchema + "</category>");
		xmlBuilder.append("<taskId>" + taskID + "</taskId>");
		if(actionID != null)
		{
			xmlBuilder.append("<actionId>" + actionID + "</actionId>");
			xmlBuilder.append("<actionCode>" + actionCode + "</actionCode>");
		}
		xmlBuilder.append("</recordInfo></opDetail>");
		
		return xmlBuilder.toString();
	}
	
	private void zip(ZipOutputStream out, File inFile, String root) throws Exception
	{
		if (inFile.isDirectory())
		{
			File[] files = inFile.listFiles();
			if (root.length() > 0)
			{
				out.putNextEntry(new ZipEntry(root + "/"));
			}
			root = root.length() == 0 ? "" : root + "/";
			for (int i = 0; i < files.length; i++)
			{
				zip(out, files[i], root + files[i].getName());
			}
		}
		else
		{
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile));
			out.putNextEntry(new ZipEntry(root));
			int c;
			while ((c = in.read()) != -1)
			{
				out.write(c);
			}
			in.close();
		}

	}

	public IDao<SyncAttachment> getSyncAttachDao()
	{
		return syncAttachDao;
	}

	public void setSyncAttachDao(IDao<SyncAttachment> syncAttachDao)
	{
		this.syncAttachDao = syncAttachDao;
	}
}

