package com.ultrapower.eoms.mobile.interfaces.syncattach.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.ultrawf.control.process.BaseAttachmentManage;
import cn.com.ultrapower.ultrawf.models.process.BaseAttachmentModel;
import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.ultrawf.share.constants.Constants;

import com.ultrapower.eoms.mobile.interfaces.syncattach.model.BaseAttachmentInfo;
import com.ultrapower.eoms.mobile.interfaces.syncattach.model.SyncAttachInputModel;
import com.ultrapower.eoms.mobile.interfaces.syncattach.model.SysAttachModel;
import com.ultrapower.eoms.mobile.service.InterfaceService;
import com.ultrapower.randomutil.Random15;

/**
 * 更新附件信息的手机接口服务
 * @author Haoyuan
 */
public class SyncAttachManager implements InterfaceService
{
	
	public String call(String xml, String fileList)
	{
		SyncAttachInputModel input = new SyncAttachInputModel();
		String outputXml;
		try
		{
			input.buildModel(xml);
			boolean result = handle(input,fileList);
			if(result)
			{
				outputXml = "<opDetail><baseInfo><isLegal>1</isLegal><success>1</success><errorMessage></errorMessage></baseInfo></opDetail>";
			}
			else
			{
				outputXml = "<opDetail><baseInfo><isLegal>0</isLegal><success>0</success><errorMessage>无此附件</errorMessage></baseInfo></opDetail>";
			}
		}
		catch (Exception e)
		{
			outputXml = "<opDetail><baseInfo><isLegal>1</isLegal><success>0</success><errorMessage>" + e.getMessage() + "</errorMessage></baseInfo></opDetail>";
			e.printStackTrace();
		}
		return outputXml;
	}

	private boolean handle(SyncAttachInputModel inputModel,String fileList)
	{
		BaseAttachmentManage baseAttachmentManage = new BaseAttachmentManage();
		RDParameter p_rDParameter = new RDParameter();
		p_rDParameter.addIndirectPar("attachmentid",inputModel.getSuffix(),4);
		BaseAttachmentModel baseAttachmentModel = baseAttachmentManage.getOneModel(p_rDParameter);
		List<BaseAttachmentInfo> attachList = null;
		if(fileList != null && !fileList.equals("")) 
		{
			attachList = new ArrayList<BaseAttachmentInfo>();
			String[] attArr = fileList.split("\\|");
			for(String att : attArr)
			{
				if(att.length()>0)
				{
					String[] attinfo = att.split("\\*");
					BaseAttachmentInfo attModel = new BaseAttachmentInfo(attinfo[2], attinfo[0]);
					attModel.setName(attinfo[0]);
					attModel.setDbname(attinfo[1]);
					attModel.setUploaddate(attinfo[3]);
					attachList.add(attModel);
				}
			}
		
			if (attachList!=null&&attachList.size()>0) 
			{
				String p_BaseID = baseAttachmentModel.getBaseID();
				String p_BaseSchema = baseAttachmentModel.getBaseSchema();
				String p_ProcessID = baseAttachmentModel.getProcessID();
				String p_ProcessType = baseAttachmentModel.getProcessType();
				String p_PhaseNo = baseAttachmentModel.getPhaseNo();;
				String p_ProcessLogID = "" ;
				String p_upLoadUser = baseAttachmentModel.getUpLoadUser();
				String p_upLoadUserID = baseAttachmentModel.getUpLoadUserID();
				for (int i = 0; i < attachList.size(); i++) {
					BaseAttachmentInfo atta = attachList.get(i);
					String fileName = atta.getPath();
					Random15 random = new Random15();
					String guid = random.getRandom(System.currentTimeMillis());
					BaseAttachmentManage att = new BaseAttachmentManage();
				 	String attachID = att.baseAttachmentPush(p_BaseID,p_BaseSchema,p_ProcessID,
				 							p_ProcessType,p_PhaseNo,p_ProcessLogID,"1",p_upLoadUser,p_upLoadUserID,
				 							Init_Get_TIMESTAMP().toString(),inputModel.getRealName(),
				 							p_upLoadUser+"在"+FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"上传！",
				 							fileName,guid);
				 	//当将附件同步到数据库成功后，需要删除硬盘上的临时附件。
				 	File file = new File(fileName);
					if(file.isFile()){
						file.delete();
					}
					//临时文件删除成功
				 	
				}
			}
			
			String formName = "WF:App_Base_Attachment"; 
			String attID 	= inputModel.getSuffix(); 
	      	RemedyFormOp formOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME, 
						Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME, 
						Constants.REMEDY_DEMOPASSWORD); 
			formOp.RemedyLogin();			 
			formOp.FormDataDelete(formName,attID); 
			formOp.RemedyLogout();
		}
		
		return true;
	}
	private Long Init_Get_TIMESTAMP() { 
		Long m_obj_Long_TIMESTAMP = new Long(System.currentTimeMillis()/1000);
		return m_obj_Long_TIMESTAMP;
	}
}
