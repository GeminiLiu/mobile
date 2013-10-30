package cn.com.ultrapower.ultrawf.control.process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Hashtable;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.system.remedyop.PublicFieldInfo;
import cn.com.ultrapower.ultrawf.models.config.BaseCategory;
import cn.com.ultrapower.ultrawf.models.config.BaseCategoryModel;
import cn.com.ultrapower.ultrawf.models.config.ParBaseCategoryModel;
import cn.com.ultrapower.ultrawf.models.process.Base;
import cn.com.ultrapower.ultrawf.models.process.DealProcess;
import cn.com.ultrapower.ultrawf.models.process.DealProcessLog;
import cn.com.ultrapower.ultrawf.models.process.DealProcessLink;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcess;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessLog;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessLink;

import cn.com.ultrapower.ultrawf.models.process.BaseModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessLogModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessLinkModel;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessModel;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessLogModel;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessLinkModel;
import cn.com.ultrapower.ultrawf.models.process.ParBaseModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcessLinkModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcessLogModel;
import cn.com.ultrapower.ultrawf.models.process.ParAuditingProcessLinkModel;
import cn.com.ultrapower.ultrawf.models.process.ParAuditingProcessLogModel;
import cn.com.ultrapower.ultrawf.share.constants.ConstantsManager;
import cn.com.ultrapower.system.remedyop.*;

public class DataArchiveManage {
	
	public String m_BaseArchiveRunMode;
	public boolean m_isRun;
	public DataArchiveManageThread Thread_Exp_One;
	 
	public DataArchiveManage() {
		this.m_BaseArchiveRunMode = Constants.BaseArchiveCycMode;
	}
	 
	public void DataArchiveManageStart() {
		Thread_Exp_One = new DataArchiveManageThread();
		Thread_Exp_One.isStart = true;
		Thread_Exp_One.start();
	}	

	public void DataArchiveManageStop() {
		Thread_Exp_One = new DataArchiveManageThread();
		Thread_Exp_One.isStart = false;
	}	
	public static void main(String args[]) {
		ConstantsManager m_ConstantsManager = new ConstantsManager(System.getProperty("user.dir")
				+ File.separator
				+ "WEB-INF");
		m_ConstantsManager.getConstantInstance();	
		if (Constants.BaseArchiveRunMode.equals("1"))
		{	
			System.out.println("启动工单表级分离后台程序");
			DataArchiveManage objMain = new DataArchiveManage();
			objMain.DataArchiveManageStart();			
		}
		else
		{
			System.out.println("启动工单表级分离后台程序——参数设置错误");
		}
	}
}
