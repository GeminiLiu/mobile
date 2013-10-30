package com.ultrapower.eoms.common.core.component.sla.monitor;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-16 上午09:47:52
 */
public class SlaDealRecordDao {

	private static QueryAdapter queryAdapter = new QueryAdapter();
	private static DataAdapter dataAdapter = new DataAdapter();
	
	/**
	 * 插入处理记录
	 * @param actionid
	 * @param taskid
	 * @param num
	 * @return
	 */
	public static boolean insert(String actionid,String taskid,int num){
		boolean flag = false;
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("PID", UUIDGenerator.getUUIDoffSpace());
		p_dtrow.put("RULEID", actionid);
		p_dtrow.put("TASKID", taskid);
		p_dtrow.put("DEALNUM", num);
		p_dtrow.put("DEALTIME", TimeUtils.getCurrentTime());
		int result = dataAdapter.putDataRow("bs_t_sm_sladealrecord", p_dtrow, "", null);
		if(result==1)
			flag = true;
		return flag;
	}
	
	/**
	 * 查询是否已经处理
	 * @param actionid
	 * @param taskpid
	 * @return
	 */
	public static boolean isDeal(String actionid,String taskpid){
		boolean flag = false;
		String sql = "select * from bs_t_sm_sladealrecord  where ruleid = ? and taskid = ?";
		Object[] values = {actionid,taskpid};
		DataTable dataTable = queryAdapter.executeQuery(sql, values);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		if(dataTableLen>0)//有数据记录
			flag = true;
		return flag;
	}
}
