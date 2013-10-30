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
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.component.sla.util.IsHoliday;
import com.ultrapower.eoms.common.core.component.sla.util.NoticeParaDeal;
import com.ultrapower.eoms.common.core.component.sla.util.TimeDeal;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmonitor;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * 
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-10 下午04:55:31
 */
public class SmActionRuleJob extends ActionRule implements Job
{

	private static QueryAdapter queryAdapter = new QueryAdapter();
	private IActionHandle smsHandle = new SmActionHandleImpl();
	private Smsmonitor sms;
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.execute();
	}
	
	public void execute()
	{
		List<Object> objList = getList();
		int  objListLen = 0;
		if(objList!=null)
			objListLen = objList.size();
		RecordLog.printLog("短信通知：过滤后,需要进行短信通知处理的动作："+objListLen+"条!",RecordLog.LOG_LEVEL_INFO);
		DataRow dataRow;
		for(int row=0;row<objListLen;row++){
			dataRow = (DataRow) objList.get(row);
			RecordLog.printLog("短信通知：过滤后,进行短信通知动作第"+(row+1)+"条处理!---start",RecordLog.LOG_LEVEL_INFO);
			
			String pid = StringUtils.checkNullString(dataRow.getString("pid"));//动作pid
			String actionname = StringUtils.checkNullString(dataRow.getString("actionname"));//动作名称
			String ruletplid = StringUtils.checkNullString(dataRow.getString("ruletplid"));//模版pid
			String dealmode = StringUtils.checkNullString(dataRow.getString("dealmode"));//处理模式
			String isbusinessinformer = StringUtils.checkNullString(dataRow.getString("isbusinessinformer"));//任务通知人
			String noticeuserid = StringUtils.checkNullString(dataRow.getString("noticeuserid"));//通知人
			String noticegroupid = StringUtils.checkNullString(dataRow.getString("noticegroupid"));//通知组
			String smcontent = StringUtils.checkNullString(dataRow.getString("smcontent"));//短信内容
			String pri = StringUtils.checkNullString(dataRow.getString("pri"));
			
			IActionCondition actionCondition = new ActionConditionImpl();
			RecordLog.printLog("短信通知："+actionname+",根据动作规则进行业务数据查询--start",RecordLog.LOG_LEVEL_INFO);
			List<DataRow> businessDataRow = super.getActionBusinessData(actionCondition, dealmode, pid, ruletplid);
			RecordLog.printLog("短信通知："+actionname+",根据动作规则进行业务数据查询--end",RecordLog.LOG_LEVEL_INFO);
			int businessDataRowLen = 0;
			if(businessDataRow!=null)
				businessDataRowLen = businessDataRow.size();
			RecordLog.printLog("短信通知："+actionname+",根据动作规则进行业务数据查询,获取的数据："+(businessDataRowLen+1)+"条",RecordLog.LOG_LEVEL_INFO);
			DataRow bDataRow;
			for(int brow=0;brow<businessDataRowLen;brow++)
			{
				RecordLog.printLog("短信通知："+actionname+",第"+(brow+1)+"条业务数据处理--start",RecordLog.LOG_LEVEL_INFO);
				bDataRow = businessDataRow.get(brow);
				String businessPid = StringUtils.checkNullString(bDataRow.getString(SmsPara.taskPid));//业务数据pid
				
				//验证该条记录是否已经处理过
				if(SlaDealRecordDao.isDeal(pid, businessPid))//已经处理
					continue;
				
				//短信内容
				smcontent = SqlReplace.stringReplaceAllVar(smcontent,bDataRow.getRowHashMap());
				
				List<String> taskUserMobile = new ArrayList<String>();
				List<String> taskGroupMobile = new ArrayList<String>();
				String businessNoticeUser = "";
				String businessNoticeGroup = "";
				if(isbusinessinformer.equals("1"))
				{//通知任务通知人
					businessNoticeUser = StringUtils.checkNullString(bDataRow.getString(SmsPara.taskNoticeUserid.toUpperCase()));
					businessNoticeGroup = StringUtils.checkNullString(bDataRow.getString(SmsPara.taskNoticeGroupid.toUpperCase()));
					taskUserMobile = NoticeParaDeal.getMobileByUserId(businessNoticeUser);
					taskGroupMobile = NoticeParaDeal.getMobileByGroupId(businessNoticeGroup);
				}
				//自定义通知人的手机号码
				List<String> customUserMobile = new ArrayList<String>();
				if(!noticeuserid.equals(""))
					customUserMobile = NoticeParaDeal.getMobileByUserId(noticeuserid);
				List<String> customGroupMobile = new ArrayList<String>();
				if(!noticegroupid.equals(""))
				    customGroupMobile = NoticeParaDeal.getMobileByGroupId(noticegroupid);
				//该业务需要通知人手机号集合
				List<String> goal = new ArrayList<String>();
				try {
					goal = ArrayTransferUtils.copyListNoteTheSame(taskUserMobile,taskGroupMobile,customUserMobile,customGroupMobile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				int goalLen = 0;
				if(goal!=null)
					goalLen = goal.size();
				RecordLog.printLog("短信通知:动作="+actionname+",业务数据pid="+businessPid+".短信发送的用户数："+goalLen, RecordLog.LOG_LEVEL_INFO);
				RecordLog.printLog("短信通知:动作="+actionname+",业务数据pid="+businessPid+".进行短信发送--start", RecordLog.LOG_LEVEL_INFO);
				for(int g=0;g<goalLen;g++){
					RecordLog.printLog("短信通知: 业务数据pid="+businessPid+".进行第"+(g+1)+"条发送--start", RecordLog.LOG_LEVEL_INFO);
					sms = new Smsmonitor();
					sms.setContent(smcontent);
					sms.setGoal(goal.get(g));
					sms.setRelateid(pid);
					sms.setPri(Integer.parseInt(pri));
					smsHandle.execute(sms);
					RecordLog.printLog("短信通知: 业务数据pid="+businessPid+".进行第"+(g+1)+"条发送--end", RecordLog.LOG_LEVEL_INFO);
				}
				//发送情况记录,避免重复发送
				boolean flag = SlaDealRecordDao.insert(pid, businessPid, 1);
				if(flag){
					RecordLog.printLog("短信通知： 动作="+actionname+",业务数据="+businessPid+"。发送结果记录成功!", RecordLog.LOG_LEVEL_INFO);
				}else{
					RecordLog.printLog("短信通知： 动作="+actionname+",业务数据="+businessPid+"。发送结果记录失败!", RecordLog.LOG_LEVEL_ERROR);
				}
				
				RecordLog.printLog("短信通知:动作"+actionname+",业务数据pid="+businessPid+".进行短信发送--end", RecordLog.LOG_LEVEL_INFO);
				RecordLog.printLog("短信通知："+actionname+",第"+(brow+1)+"条业务数据处理--end",RecordLog.LOG_LEVEL_INFO);
			}
			RecordLog.printLog("短信通知：过滤后,进行短信通知动作第"+(row+1)+"条处理!---end",RecordLog.LOG_LEVEL_INFO);
		}
	}

	/**
	 * 扫描短信通知表,并进行节假日、时间(开始时间、结束时间)过滤
	 */
	protected List<Object> getList() 
	{
		List<Object> dataList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select action.pid,action.ruletplid,action.actionname,action.dealmode,action.isholiday,action.starttime,");
		sql.append(" action.endtime,action.isbusinessinformer,action.noticeuserid,action.noticegroupid,action.smcontent,action.pri");
		sql.append(" from bs_t_sm_slasmaction action ");
		sql.append(" where action.status = 1 ");
		sql.append(" order by action.pri desc");
		 
		Object[] values = null;  
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(),values,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		RecordLog.printLog("短信通知：定制的短信通知规则:"+dataTableLen+"条",RecordLog.LOG_LEVEL_INFO);
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++)
		{
			RecordLog.printLog("短信通知：进行短信通知规则处理第:"+(row+1)+"条过滤",RecordLog.LOG_LEVEL_INFO);
			dataRow = dataTable.getDataRow(row);
			String actionname = StringUtils.checkNullString(dataRow.getString("actionname"));//动作名称
			String isholiday = StringUtils.checkNullString(dataRow.getString("isholiday"));//节假日除外
			String starttime = StringUtils.checkNullString(dataRow.getString("starttime"));//开始时间
			String endtime = StringUtils.checkNullString(dataRow.getString("endtime"));//结束时间
			if(isholiday.equals("1") && IsHoliday.nowIsHoliday()){//节假日不接收短信通知
				RecordLog.printLog("短信通知："+actionname+",今天是假日,不进行短信通知处理!,第："+(row+1)+"条被过滤",RecordLog.LOG_LEVEL_ERROR);
				continue;
			}
			if(!TimeDeal.nowIsScanTime(starttime,endtime))
			{
				RecordLog.printLog("短信通知："+actionname+",当前时间不满足处理的开始、结束时间,不进行短信通知处理!,第："+(row+1)+"条被过滤",RecordLog.LOG_LEVEL_ERROR);
				continue;
			}
			dataList.add(dataRow);
		}
		return dataList;
	}
}
