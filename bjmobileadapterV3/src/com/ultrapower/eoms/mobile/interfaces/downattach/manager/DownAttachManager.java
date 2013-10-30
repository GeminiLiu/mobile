package com.ultrapower.eoms.mobile.interfaces.downattach.manager;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.ultrapower.eoms.common.constants.Constants;
//import com.ultrapower.eoms.common.core.component.data.DataRow;
//import com.ultrapower.eoms.common.core.component.data.DataTable;
//import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
//import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.ultrawf.control.process.BaseAttachmentManage;
import cn.com.ultrapower.ultrawf.models.process.BaseAttachmentModel;
import cn.com.ultrapower.ultrawf.share.constants.Constants;

import com.ultrapower.eoms.common.cfg.Config;
import com.ultrapower.eoms.common.cfg.ConfigKeys;
import com.ultrapower.eoms.mobile.interfaces.downattach.model.DownAttachInputModel;
import com.ultrapower.eoms.mobile.interfaces.downattach.model.DownAttachOutputModel;
import com.ultrapower.eoms.mobile.service.InterfaceService;
//import com.ultrapower.remedy4j.core.RemedySession;

public class DownAttachManager implements InterfaceService
{
	public String call(String xml, String fileList)
	{
		DownAttachInputModel input = new DownAttachInputModel();
		String outputXml = "";

		try
		{
			input.buildModel(xml);
			DownAttachOutputModel output = handle(input, fileList);
			outputXml = output.buildXml();
		}
		catch (Exception e)
		{
			outputXml = DownAttachOutputModel.buildExceptionXml(e.getMessage());
			e.printStackTrace();
		}
		return outputXml;
	}

	private DownAttachOutputModel handle(DownAttachInputModel inputModel, String fileList)
	{
		DownAttachOutputModel outputModel = new DownAttachOutputModel();

		BaseAttachmentManage baseAttachmentManage = new BaseAttachmentManage();
		RDParameter p_rDParameter = new RDParameter();
		p_rDParameter.addIndirectPar("baseid",inputModel.getBaseID(),4);
		p_rDParameter.addIndirectPar("baseschema",inputModel.getCategory(),4);
		List<BaseAttachmentModel> baseAttachmentModelList = baseAttachmentManage.getList(p_rDParameter);
		
		RemedyFormOp formOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME, 
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME, 
				Constants.REMEDY_DEMOPASSWORD); 
		formOp.RemedyLogin();
		String formName = "WF:App_Base_Attachment";
		
		Map<String, String> attachMap = new HashMap<String, String>();
		if(baseAttachmentModelList != null && baseAttachmentModelList.size() > 0)
		{
			int len_baseAttachmentModelList = baseAttachmentModelList.size();
			int sameNameIndex = 1;
			for(int row=0;row<len_baseAttachmentModelList;row++)
			{
				BaseAttachmentModel baseAttachmentModel = baseAttachmentModelList.get(row);
				String path = Config.getValue(ConfigKeys.ATTACHMENT_DISK_TEMPDOWNLOAD);
				String fileWholeName = path +"/"+ baseAttachmentModel.getAttachmentID();
				String name = baseAttachmentModel.getUpLoadFileName();
				if(attachMap.containsKey(name))
				{
					name += "(" + sameNameIndex + ")";
					sameNameIndex++;
				}
				attachMap.put(name,fileWholeName);
				//将存入数据库表中的附件保存在临时文件中，共server端提取，提取之后需要删除
				try{
					long startTime = System.currentTimeMillis();
					byte[] attStream = formOp.GetEntryAttachmentBytes(formName,"650000010",baseAttachmentModel.getAttachmentID());
					InputStream attIS = new ByteArrayInputStream(attStream);
					InputStream bfIS = new BufferedInputStream(attIS);
					
					FileOutputStream outputStream = null;
					File f = new File(fileWholeName);
					File dir = f.getParentFile();
					if(dir!=null)
					{
						dir.mkdirs();
					}
					outputStream = new FileOutputStream(fileWholeName);
					byte bufer[] = new byte[1024]; 
					int len = attStream.length;
					while(-1 != (len= bfIS.read(bufer)))  
		        	{  
		        		outputStream.write(bufer, 0, len);  
		        	}  
					outputStream.close();  
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
	        	
			}
		}
		outputModel.setSuccess(1);
		outputModel.setAttachPathMap(attachMap);
		
		return outputModel;
	}
}
