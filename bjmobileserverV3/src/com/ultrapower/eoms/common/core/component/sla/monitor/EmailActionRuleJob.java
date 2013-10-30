package com.ultrapower.eoms.common.core.component.sla.monitor;

import java.util.ArrayList;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.email.EmailBean;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.component.sla.util.IsHoliday;
import com.ultrapower.eoms.common.core.component.sla.util.NoticeParaDeal;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-15 上午10:43:09
 */
public class EmailActionRuleJob extends ActionRule implements Job{

	private static QueryAdapter queryAdapter = new QueryAdapter();
	private IActionHandle emailHandle = new EmailActionHandleImpl();
	private EmailBean emailBean;
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.execute();
	}

	public void execute() {
		List<Object> objList = getList();
		int objListLen = 0;
		if(objList!=null)
			objListLen = objList.size();
		RecordLog.printLog("邮件通知：过滤后,需要进行邮件通知处理的动作："+objListLen+"条!",RecordLog.LOG_LEVEL_INFO);
		DataRow dataRow;
		for(int row=0;row<objListLen;row++){
			dataRow = (DataRow)objList.get(row);
			RecordLog.printLog("邮件通知：过滤后,进行邮件通知动作第"+(row+1)+"条处理!---start",RecordLog.LOG_LEVEL_INFO);
			
			String pid = StringUtils.checkNullString(dataRow.getString("pid"));//动作pid
			String actionname = StringUtils.checkNullString(dataRow.getString("actionname"));//动作名称
			String ruletplid = StringUtils.checkNullString(dataRow.getString("ruletplid"));//模版pid
			String dealmode = StringUtils.checkNullString(dataRow.getString("dealmode"));//处理模式
			String isbusinessinformer = StringUtils.checkNullString(dataRow.getString("isbusinessinformer"));//任务通知人
			String noticeuserid = StringUtils.checkNullString(dataRow.getString("noticeuserid"));//通知人
			String noticegroupid = StringUtils.checkNullString(dataRow.getString("noticegroupid"));//通知组
			String copyuserid = StringUtils.checkNullString(dataRow.getString("copyuserid"));//超送人
			String copygroupid = StringUtils.checkNullString(dataRow.getString("copygroupid"));//抄送组
			String mailsubject = StringUtils.checkNullString(dataRow.getString("mailsubject"));//邮件主题
			String mailcontent = StringUtils.checkNullString(dataRow.getString("mailcontent"));//邮件内容
			
			IActionCondition actionCondition = new ActionConditionImpl();
			RecordLog.printLog("邮件通知："+actionname+",根据动作规则进行业务数据查询--start",RecordLog.LOG_LEVEL_INFO);
			List<DataRow> businessDataRow = super.getActionBusinessData(actionCondition, dealmode, pid, ruletplid);
			RecordLog.printLog("邮件通知："+actionname+",根据动作规则进行业务数据查询--end",RecordLog.LOG_LEVEL_INFO);
			int businessDataRowLen = 0;
			if(businessDataRow!=null)
				businessDataRowLen = businessDataRow.size();
			RecordLog.printLog("邮件通知："+actionname+",根据动作规则进行业务数据查询,获取的数据："+businessDataRowLen+"条",RecordLog.LOG_LEVEL_INFO);
			DataRow bDataRow;
			for(int bRow=0;bRow<businessDataRowLen;bRow++){
				RecordLog.printLog("邮件通知："+actionname+",第"+(bRow+1)+"条业务数据处理--start",RecordLog.LOG_LEVEL_INFO);
				bDataRow = businessDataRow.get(bRow);
				String businessPid = StringUtils.checkNullString(bDataRow.getString(SmsPara.taskPid));//业务数据pid
				
				//验证该条记录是否已经处理过
				if(SlaDealRecordDao.isDeal(pid, businessPid))//已经处理
					continue;
				
				//邮件主题
				mailsubject = SqlReplace.stringReplaceAllVar(mailsubject,bDataRow.getRowHashMap());
				//邮件内容
				mailcontent = SqlReplace.stringReplaceAllVar(mailcontent,bDataRow.getRowHashMap());
				
				List<String> taskUserEmail = new ArrayList<String>();
				List<String> taskGroupEmail = new ArrayList<String>();
				String businessNoticeUser = "";
				String businessNoticeGroup = "";
				if(isbusinessinformer.equals("1"))
				{//通知任务通知人/组
					businessNoticeUser = StringUtils.checkNullString(bDataRow.getString(SmsPara.taskNoticeUserid.toUpperCase()));
					businessNoticeGroup = StringUtils.checkNullString(bDataRow.getString(SmsPara.taskNoticeGroupid.toUpperCase()));
					taskUserEmail = NoticeParaDeal.getEmailByUserId(businessNoticeUser);
					taskGroupEmail = NoticeParaDeal.getEmailByGroupId(businessNoticeGroup);
				}
				
				//自定义通知人/组
				List<String> customUserEmail = new ArrayList<String>();
				if(!noticeuserid.equals(""))
					customUserEmail = NoticeParaDeal.getEmailByUserId(noticeuserid);
				List<String> customGroupEmail = new ArrayList<String>();
				if(!noticegroupid.equals(""))
					customGroupEmail = NoticeParaDeal.getEmailByGroupId(noticegroupid);
				List<String> copyUserEmail = new ArrayList<String>();
				if(!copyuserid.equals(""))
					copyUserEmail = NoticeParaDeal.getEmailByUserId(copyuserid);
				List<String> copyGroupEmail = new ArrayList<String>();
				if(!copygroupid.equals(""))
					copyGroupEmail = NoticeParaDeal.getEmailByGroupId(copygroupid);
				//发送人
				List<String> addressemail = new ArrayList<String>();
				try {
					addressemail = ArrayTransferUtils.copyListNoteTheSame(taskUserEmail,taskGroupEmail,customUserEmail,customGroupEmail);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//超送人
				List<String> copyAddressemail = new ArrayList<String>();
				try {
					copyAddressemail = ArrayTransferUtils.copyListNoteTheSame(copyUserEmail, copyGroupEmail);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				List<String> sendTo = new ArrayList<String>();
				try {
					sendTo = ArrayTransferUtils.copyListNoteTheSame(addressemail, copyAddressemail);
				} catch (Exception e) {
					e.printStackTrace();
				}
				emailBean = new EmailBean();
				emailBean.setMailTo(sendTo);
				emailBean.setSubject(mailsubject);
				emailBean.setMsgContent(mailcontent);
				emailHandle.execute(emailBean);
				RecordLog.printLog("邮件通知："+actionname+",第"+(bRow+1)+"条业务数据处理--end",RecordLog.LOG_LEVEL_INFO);
				
				//发送情况记录,避免重复发送
				boolean flag = SlaDealRecordDao.insert(pid, businessPid, 1);
				if(flag){
					RecordLog.printLog("邮件通知： 动作="+actionname+",业务数据="+businessPid+"。发送结果记录成功!", RecordLog.LOG_LEVEL_INFO);
				}else{
					RecordLog.printLog("邮件通知： 动作="+actionname+",业务数据="+businessPid+"。发送结果记录失败!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
			RecordLog.printLog("邮件通知：过滤后,进行邮件通知动作第"+(row+1)+"条处理!---end",RecordLog.LOG_LEVEL_INFO);
		}
	}

	/**
	 * 查询邮件动作表,并进行节假日过滤
	 */
	protected List<Object> getList() {
		List<Object> listData = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select action.pid, action.ruletplid, action.actionname, action.dealmode, action.pri,");
		sql.append(" action.isholiday, action.isbusinessinformer, action.noticeuserid, action.noticegroupid,");
		sql.append(" action.copyuserid, action.copygroupid, action.mailsubject, action.mailcontent");
		sql.append(" from bs_t_sm_slamailaction action where action.status = 1 order by action.pri desc");
		
		Object[] values = null;
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), values,2);
	    int dataTableLen = 0;
	    if(dataTable!=null)
	       dataTableLen = dataTable.length();
	    RecordLog.printLog("邮件通知：定制的邮件通知规则:"+dataTableLen+"条",RecordLog.LOG_LEVEL_INFO);
	    DataRow dataRow;
	    for(int row=0;row<dataTableLen;row++){
	    	dataRow = dataTable.getDataRow(row);
	    	RecordLog.printLog("邮件通知：进行邮件通知规则处理第:"+(row+1)+"条过滤",RecordLog.LOG_LEVEL_INFO);
	    	String actionname = StringUtils.checkNullString(dataRow.getString("actionname"));//动作名称
			String isholiday = StringUtils.checkNullString(dataRow.getString("isholiday"));//节假日除外
			if(isholiday.equals("1") && IsHoliday.nowIsHoliday()){//节假日不接收邮件通知
				RecordLog.printLog("邮件通知："+actionname+",今天是假日,不进行邮件通知处理!,第："+(row+1)+"条被过滤",RecordLog.LOG_LEVEL_ERROR);
				continue;
			}
			listData.add(dataRow);
	    }
		return listData;
	}
}
