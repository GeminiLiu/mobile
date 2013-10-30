package com.ultrapower.eoms.common.core.component.sla.util;

import java.util.ArrayList;
import java.util.List;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-14 下午03:35:52
 */
public class NoticeParaDeal {

	public static QueryAdapter queryAdapter = new QueryAdapter();
	
	/**
	 * 查询用户手机号码
	 * @param userid eg：97e39d1298150e8012981703b700002,4028940b2aa8255a012aa83ac02d0001,402894aa29eebd730129eed8e9e000d3
	 * @return
	 */
	public static List<String> getMobileByUserId(final String userid){
		if(userid=="")
			return null;
		List<String> list = new ArrayList<String>(); 
		
		StringBuffer p_sql = new StringBuffer();;
		p_sql.append("select mobile from bs_t_sm_user where mobile > 0 ");
		
		String sql = "";
		String[] useridArr = userid.split(",");
		for(int i=0;i<useridArr.length;i++){
			sql += " pid = '"+useridArr[i]+"' or";
		}
		
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		
		return list;
	}
	
	/**
	 * 查询用户手机号码
	 * @param groupid eg：97e39d1298150e8012981703b700002,4028940b2aa8255a012aa83ac02d0001,402894aa29eebd730129eed8e9e000d3
	 * @return
	 */
	public static List<String> getMobileByGroupId(final String groupid){
		if(groupid == "")
			return null;

		List<String> list = new ArrayList<String>();
		
		StringBuffer p_sql = new StringBuffer();
		p_sql.append("select sysuser.mobile");
		p_sql.append(" from bs_t_sm_user sysuser, bs_t_sm_userdep sysdep");
		p_sql.append(" where sysuser.pid = sysdep.userid");
		p_sql.append(" and sysuser.status = 1");
		p_sql.append(" and sysuser.mobile > 0");
		String sql = "";
		String[] groupidArr = groupid.split(",");
		for(int i=0;i<groupidArr.length;i++){
			sql += " sysdep.depid = '"+groupidArr[i]+"' or";
		}
		   
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		  
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		 
		return list;
	}
	
	/**
	 * 查询用户邮箱地址
	 * @param groupid
	 * @return
	 */
	public static List<String> getEmailByGroupId(final String groupid){
		if(groupid == "")
			return null;

		List<String> list = new ArrayList<String>();
		
		StringBuffer p_sql = new StringBuffer();
		p_sql.append("select sysuser.email");
		p_sql.append(" from bs_t_sm_user sysuser, bs_t_sm_userdep sysdep");
		p_sql.append(" where sysuser.pid = sysdep.userid");
		p_sql.append(" and sysuser.status = 1");
		p_sql.append(" and sysuser.email > '0'");
		String sql = "";
		String[] groupidArr = groupid.split(",");
		for(int i=0;i<groupidArr.length;i++){
			sql += " sysdep.depid = '"+groupidArr[i]+"' or";
		}
		   
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		  
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String email = StringUtils.checkNullString(dataRow.getString("email"));
			list.add(email);
		}
		 
		return list;
	}
	
	/**
	 * 查询用户邮箱
	 * @param userid
	 * @return
	 */
	public static List<String> getEmailByUserId(final String userid){
		if(userid=="")
			return null;
		List<String> list = new ArrayList<String>(); 
		
		StringBuffer p_sql = new StringBuffer();;
		p_sql.append("select email from bs_t_sm_user where email > '0' ");
		
		String sql = "";
		String[] useridArr = userid.split(",");
		for(int i=0;i<useridArr.length;i++){
			sql += " pid = '"+useridArr[i]+"' or";
		}
		
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String email = StringUtils.checkNullString(dataRow.getString("email"));
			list.add(email);
		}
		return list;
	}
}
