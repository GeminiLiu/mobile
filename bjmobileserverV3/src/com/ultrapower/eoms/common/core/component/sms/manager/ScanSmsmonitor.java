package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmonitor;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * 操作短信信息表
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-7-29 下午05:26:11
 */
public class ScanSmsmonitor{
	private static DataAdapter dataAdapter  = new DataAdapter();
	public static QueryAdapter queryAdapter = new QueryAdapter();
	private static final int initQueryCount = 100;//初始化查询的条数
	/**
	 * 获取待发送短信息
	 * @return 短信息的集合
	 */
	public List<Smsmonitor> getWaitingSendSm(){
		List<Smsmonitor> smList =null;
		
		long nowtime = System.currentTimeMillis()/1000;
		int overtime = 1800;//定时过期时间
		StringBuffer p_sql = new StringBuffer(1024);
		p_sql.append(" select pid, content, systemtype, sendnum, goal, relateid, pri");
		p_sql.append(" from bs_t_sm_smsmonitor");
		p_sql.append(" where (sendflag = 0");
		p_sql.append(" and abs(presendtime - "+nowtime+") <= 180 ");//定时时间的前后三分钟可以扫描发送
		p_sql.append(" or abs("+nowtime+"-(presendtime+"+overtime+"))<=180) ");//异常未发送的定时短信,过期时间30分钟内的也可进行发送
		p_sql.append(" or");
		p_sql.append(" (sendflag = 0");
		p_sql.append(" and presendtime = 0) ");
		p_sql.append(" order by pri desc");
		Object[] values = null;
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString(), values, 1, initQueryCount, 2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		Smsmonitor smsmonitor;
		if(dataTableLen>0)
			smList=new ArrayList<Smsmonitor>();
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			smsmonitor = new Smsmonitor();
			smsmonitor.setPid(StringUtils.checkNullString(dataRow.getString("pid")));
			smsmonitor.setContent(StringUtils.checkNullString(dataRow.getString("content")));
			smsmonitor.setSystemtype(StringUtils.checkNullString(dataRow.getString("systemtype")));
			smsmonitor.setGoal(StringUtils.checkNullString(dataRow.getString("goal")));
			smsmonitor.setRelateid(StringUtils.checkNullString(dataRow.getString("relateid")));
			smList.add(smsmonitor);
		}
		return smList;
	}
	
	/**
	 * 修改短信息发送状态
	 * @param pid 短信唯一标识
	 * @param sendflag 发送状态  0:未发送;1:已发送;2:发送失败
	 * @param sendtime 发送时间
	 * @return 返回 true or false
	 */
	public boolean updateSm(String pid,int sendflag){
		boolean flag = false;
		String tblName = "BS_T_SM_SMSMONITOR";
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("SENDFLAG", sendflag);
		p_dtrow.put("SENDNUM", 1);
		p_dtrow.put("FIRSTSENDTIME", System.currentTimeMillis()/1000);
		p_dtrow.put("SENDTIME", System.currentTimeMillis()/1000);
		String conditions = "PID=?";
		Object[] values = {pid};
		int result = dataAdapter.putDataRow(tblName, p_dtrow, conditions, values);
		if(result>0)
			flag = true;
		return flag;
	} 
	
	/**
	 * 修改短信息发送状态
	 * @param pid 短信唯一标识
	 * @param sendflag 发送状态  0:未发送;1:已发送;2:发送失败
	 * @param sendtime 发送时间
	 * @param gatewaycode 网关回复标识代码
	 * @param gatewaycribe 网关反馈描述
	 * @return 返回 true or false
	 */
	public boolean updateSm(String pid,int sendflag,int sendnum,long firstsendtime,String gatewaycode,String gatewaycribe){
		boolean flag = false;
		String tblName = "BS_T_SM_SMSMONITOR";
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("SENDFLAG", sendflag);
		p_dtrow.put("SENDNUM", sendnum);
		p_dtrow.put("FIRSTSENDTIME", firstsendtime);
		p_dtrow.put("GATEWAYCODE", gatewaycode);
		p_dtrow.put("GATEWAYCRIBE", gatewaycribe);
		p_dtrow.put("SENDTIME", System.currentTimeMillis()/1000);
		String conditions = "PID=?";
		Object[] values = {pid};
		int result = dataAdapter.putDataRow(tblName, p_dtrow, conditions, values);
		if(result>0)
			flag = true;
		return flag;
	}
}
