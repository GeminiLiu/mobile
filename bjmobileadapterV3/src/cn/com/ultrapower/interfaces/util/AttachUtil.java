package cn.com.ultrapower.interfaces.util;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import cn.com.ultrapower.interfaces.util.attachment.AttachRefManage;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachmentInfo;
import cn.com.ultrapower.ultrawf.share.OperationLogFile;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.util.FormatString;

public class AttachUtil {

	/**
	 * 讲规范中的字符串下载到本地，并转化为List
	 * 
	 * @param p_AttachString
	 * @return
	 */
	public List<BaseAttachmentInfo> ConvertAttachList(String p_AttachString) {
		List<BaseAttachmentInfo> m_AtchList = null;
		p_AttachString = FormatString.CheckNullString(p_AttachString);
		if (p_AttachString.equals(""))
			return null;
		AttachRefManage m_AttachRefManage = new AttachRefManage();
		String path = Constants.sysPath.replaceAll(File.separatorChar
				+ "WEB-INF", "");
		path += File.separator + "attachments";
		try {
			m_AtchList = m_AttachRefManage.getAttachLocaList(p_AttachString,
					path);
		} catch (Exception ex) {
			OperationLogFile.writeTxt("取附件错误:" + ex.getMessage());
			ex.printStackTrace();
		}
		return m_AtchList;
	}

	/**
	 * 
	 * @param formScheame
	 *            要查找的工单类型 故障工单eg
	 * @param formId
	 *            要查找的工单的ID
	 * @param p_strFieldID 附件在工单中的字段的id
	 * @param p_strEntryID 附件form的c1
	 * @param p_strFileName 文件的完整名字
	 * @return
	 */
	public String getFtpPath(String formScheame ,String formId) {
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName("WF:App_Base_Attachment");
		String sql = "select t.c1,t.c650000008 from  " + strTblName + " t  where t.c650000002='"+formScheame + "' and  t.c650000001= '" + formId +"'";
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		RemedyFormOp remedyFormOp = null;
		StringBuffer sb = new StringBuffer("<attachRef>");
		try {
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sql);
			while(resultSet.next()) {
				if(remedyFormOp == null){//如果为空，则创建  登陆
					remedyFormOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
							Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
							Constants.REMEDY_DEMOPASSWORD);
					remedyFormOp.RemedyLogin();
				}	
				String c1 = FormatString.CheckNullString(resultSet.getString(1)).trim();
				String fileName = FormatString.CheckNullString(resultSet.getString(2)).trim();
				String m_strFilePathName = remedyFormOp.GetEntryAttachment("WF:App_Base_Attachment", "650000010", c1, fileName);
				File file = new File(m_strFilePathName);
				long fileLength = file.length();
				sb.append("<attachInfo>");
				sb.append("<attachName>");
				sb.append(fileName);
				sb.append("</attachName>");
				sb.append("<attachURL>");
				sb.append("ftp://eomsftp:eomsftp@10.223.3.116/");
				sb.append(fileName);
				sb.append("</attachURL>");
				sb.append("<attachLength>");
				sb.append(fileLength);
				sb.append("</attachLength>");
				sb.append("</attachInfo>");
			}
			sb.append("</attachRef>");
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (resultSet != null)
						resultSet.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				try {
					if (stm != null)
						stm.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				m_dbConsole.closeConn();
				if(remedyFormOp != null){
					remedyFormOp.RemedyLogout();
				}	
			}	
			return sb.toString();
		}
	}
