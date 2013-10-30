package cn.com.ultrapower.ultrawf.share.constants;

import java.io.*;
import org.jdom.*;
import org.jdom.input.*;

import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.util.FormatInt;

public class ConstantsManager {
	private File file;
 
	private Document doc;
	public ConstantsManager(){}
	public ConstantsManager(String p_sysPath) {
		file = new File(p_sysPath + File.separator + "UltraConstants.xml");
		Constants.sysPath = p_sysPath;
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(file);
			doc.getRootElement();
		} catch (Exception e) {
			System.err.println("XML解析初始化失败");
			e.printStackTrace();
		}
	}

	public String getConfigContant(String _tagName) {
		String text = "";
		Element element = null;
		try{
		element = doc.getRootElement();
		String tags[] = _tagName.split("#");
		for (int i = 0; i < tags.length; i++) {
			element = element.getChild(tags[i]);
		}
		text = element.getText();
		//过滤回车符(edit by xfq 2007-03-13 begin)
		text=text.replaceAll("\n","");
		//过滤空格符		text=text.trim();
		//----end---
		}catch(Exception ex)
		{
			System.out.println("---读取Constants.xml出现错误-----");
			System.out.println("节点为："+_tagName);
			System.err.println(ex.getMessage());
		}
		return text;
	}

	public void getConstantInstance(){
		
		
		/*
		Constants.DBSMTYPE = this.getConfigContant("database#DBSMType");
		Constants.DBDRIVERNAME = this.getConfigContant("database#DBDrivername");
		Constants.DBDRIVERURL = this.getConfigContant("database#DBDriverurl");
		Constants.DBPASSWORD = this.getConfigContant("database#DBPassword");
		Constants.DBUSER = this.getConfigContant("database#DBUser");
		
		Constants.DBPoolName=this.getConfigContant("database#DBPoolName");
		Constants.DBPoolMinConnection=FormatInt.FormatStringToInt(getConfigContant("database#DBPoolMinConnection"));
		Constants.DBPoolMaxConnection=FormatInt.FormatStringToInt(getConfigContant("database#DBPoolMaxConnection"));
		Constants.DBPoolTimeoutValue=FormatInt.FormatStringToInt(getConfigContant("database#DBPoolTimeoutValue"));
		
		*/
		Constants.HTMLTITLE = this.getConfigContant("message#HTMLTitle");
		Constants.SESSIONNAME = this.getConfigContant("message#SessionName");
		
		Constants.REMEDY_CREATE_URL = this.getConfigContant("remedy#RemedyCreateUrl");
		Constants.REMEDY_DEMONAME = this.getConfigContant("remedy#RemedyDemoName");
		Constants.REMEDY_DEMOPASSWORD = this.getConfigContant("remedy#RemedyDemoPassword");
		Constants.REMEDY_QUERY_URL = this.getConfigContant("remedy#RemedyQueryUrl");
		Constants.REMEDY_SERVERNAME = this.getConfigContant("remedy#RemedyServerName");
		Constants.REMEDY_SERVERPORT = FormatInt.FormatStringToInt(getConfigContant("remedy#RemedyServerPort"));
		Constants.REMEDY_PROCESS_QUERY_URL = this.getConfigContant("remedy#RemedyQueryUrlProcess");
		Constants.REMEDY_BASETPL_CREATE_URL = this.getConfigContant("remedy#RemedyCreateUrlBaseTplID");
		Constants.REMEDY_Portal_URL = this.getConfigContant("remedy#RemedyPortalUrl");
		
		RemedyFormOp.REMEDY_SERVERNAME = Constants.REMEDY_SERVERNAME;
		RemedyFormOp.REMEDY_BASETPL_CREATE_URL = Constants.REMEDY_BASETPL_CREATE_URL;
		RemedyFormOp.REMEDY_CREATE_URL = Constants.REMEDY_CREATE_URL;
		RemedyFormOp.REMEDY_QUERY_URL = Constants.REMEDY_QUERY_URL;
		RemedyFormOp.REMEDY_PROCESS_QUERY_URL = Constants.REMEDY_PROCESS_QUERY_URL;
		
		cn.com.ultrapower.system.remedyop.RemedyFormOp.REMEDY_BASETPL_CREATE_URL=Constants.REMEDY_BASETPL_CREATE_URL ;
		cn.com.ultrapower.system.remedyop.RemedyFormOp.REMEDY_CREATE_URL=Constants.REMEDY_CREATE_URL;
		cn.com.ultrapower.system.remedyop.RemedyFormOp.REMEDY_PROCESS_QUERY_URL=Constants.REMEDY_PROCESS_QUERY_URL;
		cn.com.ultrapower.system.remedyop.RemedyFormOp.REMEDY_QUERY_URL=Constants.REMEDY_QUERY_URL;
		cn.com.ultrapower.system.remedyop.RemedyFormOp.REMEDY_SERVERNAME=Constants.REMEDY_SERVERNAME;
		
		Constants.TblBaseInfor = this.getConfigContant("remedy#table#TblBaseInfor");
		
		Constants.TblBaseFieldModifyLog = this.getConfigContant("remedy#table#TblBaseFieldModifyLog");
		Constants.TblBaseFieldModifyLog_H = this.getConfigContant("remedy#table#TblBaseFieldModifyLogArchive");
		
		Constants.TblAuditingLink = this.getConfigContant("remedy#table#TblAuditingLink");
		Constants.TblAuditingProcess = this.getConfigContant("remedy#table#TblAuditingProcess");
		Constants.TblAuditingProcessLog = this.getConfigContant("remedy#table#TblAuditingProcessLog");
		Constants.TblBaseCategory = this.getConfigContant("remedy#table#TblBaseCategory");
		Constants.TblBaseCategoryClass = this.getConfigContant("remedy#table#TblBaseCategoryClass");
		Constants.TblBaseState = this.getConfigContant("remedy#table#TblBaseState");
		Constants.TblDealLink = this.getConfigContant("remedy#table#TblDealLink");
		Constants.TblDealProcess = this.getConfigContant("remedy#table#TblDealProcess");
		Constants.TblDealProcessLog = this.getConfigContant("remedy#table#TblDealProcessLog");
		Constants.TblProcessState = this.getConfigContant("remedy#table#TblProcessState");
		Constants.TblBaseOwnFieldInfo = this.getConfigContant("remedy#table#TblBaseOwnFieldInfo");
        
		Constants.TblAuditingLink_H=getConfigContant("remedy#table#TblAuditingLinkArchive");
		Constants.TblAuditingProcess_H=getConfigContant("remedy#table#TblAuditingProcessArchive");
		Constants.TblAuditingProcessLog_H=getConfigContant("remedy#table#TblAuditingProcessLogArchive");
		Constants.TblDealLink_H=getConfigContant("remedy#table#TblDealLinkArchive");
		Constants.TblDealProcess_H=getConfigContant("remedy#table#TblDealProcessArchive");
		Constants.TblDealProcessLog_H=getConfigContant("remedy#table#TblDealProcessLogArchive");

		Constants.TblDimension=getConfigContant("remedy#table#TblDimension");
		
		Constants.TblBaseIncident=getConfigContant("remedy#table#TblBaseIncident");
		Constants.TblBaseImportIncident=getConfigContant("remedy#table#TblBaseImportIncident");
		
		Constants.TblNote = this.getConfigContant("remedy#table#TblNote");
		Constants.TblNoteAuditing = this.getConfigContant("remedy#table#TblNoteAuditing");
		Constants.TblNoteClass = this.getConfigContant("remedy#table#TblNoteClass");
		Constants.TblNoteRead = this.getConfigContant("remedy#table#TblNoteRead");
		
		Constants.TblUser = this.getConfigContant("remedy#table#TblUser");
		Constants.TblGroup = this.getConfigContant("remedy#table#TblGroup");
		Constants.TblSysGroup = this.getConfigContant("remedy#table#TblSysGroup");
		Constants.TblSysUser= this.getConfigContant("remedy#table#TblSysUser");
		
		Constants.TblBaseAttachment = this.getConfigContant("remedy#table#TblBaseAttachment");
		Constants.TblAppBaseInfor = this.getConfigContant("remedy#table#TblAppBaseInfor");
		Constants.TblAppDealAssistantProcess = this.getConfigContant("remedy#table#TblAppDealAssistantProcess");
		Constants.TblAppDealVerdict = this.getConfigContant("remedy#table#TblAppDealVerdict");
		
		Constants.TblUserCloseBaseGroup = this.getConfigContant("remedy#table#TblUserCloseBaseGroup");
		Constants.TblUserCommission = this.getConfigContant("remedy#table#TblUserCommission");
		Constants.TblGroupIsSnatch=this.getConfigContant("remedy#table#TblGroupIsSnatch"); 

		
		Constants.ULTRAPROCESS_LOGIN_URL = this.getConfigContant("ultraProcessurl#loginurl");
		Constants.ULTRAPROCESS_LOGOUT_URL = this.getConfigContant("ultraProcessurl#logouturl");
		
		Constants.TREEFLAG = this.getConfigContant("ultraProcessTemp#treeFlag");		
 

		
		
		Constants.pdaBaseSchema=this.getConfigContant("pda#BaseSchema");
		
		Constants.WaitDealAction=this.getConfigContant("processaction#waitdeal");
		Constants.WaitConfirmAction=this.getConfigContant("processaction#waitconfirm");
		Constants.WaitAuditing=this.getConfigContant("processaction#waitauditing");
		
		
		Constants.MMS_PATH=this.getConfigContant("mms#mmsPath");
		
    }
	
	public static void main(String [] args){
		System.out.println("sss");
	}
}
