package com.ultrapower.eoms.common.core.component.sla.monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;
import com.ultrapower.eoms.common.core.component.sla.model.SqlConditionExp;
import com.ultrapower.eoms.common.core.component.sla.service.SlaMatchDataSourceService;
import com.ultrapower.eoms.common.core.component.sla.util.DataExpDeal;
import com.ultrapower.eoms.common.core.component.xml.XmlParser;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;

/**
 * 动作规则实现
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-10 下午03:01:14
 */
public abstract class ActionRule implements IActionRule{
	
	public static QueryAdapter queryAdapter = new QueryAdapter();
	/**
	 * 获取规则条件
	 * @param id
	 * @param dealModel 匹配类型 1：数据源匹配 2：规则匹配
	 * @return
	 */
	protected String findSlaRules(String id,String dealModel){
		String ruleStr = "";
		StringBuffer p_sql = new StringBuffer();
		p_sql.append("select");
		p_sql.append("  slarule.pid,ruleproperty.fieldid,ruleproperty.inputvaluetype,slaruleproperty.operator,slaruleproperty.value value ");
		p_sql.append("from bs_t_sm_slarule         slarule,");//规则配置表
		p_sql.append("  bs_t_sm_slaruleproperty slaruleproperty,");//规则属性数据表
		p_sql.append("  bs_t_sm_ruletplproperty ruleproperty ");//模版属性配置表
		p_sql.append("where slarule.pid = slaruleproperty.ruleid");
		p_sql.append(" and slaruleproperty.propertyid = ruleproperty.pid ");
		p_sql.append(" and ruleproperty.status = 1 ");
		p_sql.append(" and slarule.status = 1 ");
		p_sql.append(" and slarule.actionid = '"+id+"'");
		Object[] values = null;
		DataTable dataTable = null;
		try{
			dataTable = queryAdapter.executeQuery(p_sql.toString(), values,2);
		}catch(Exception e){
			RecordLog.printLog("获取动作规则出错,动作id="+id, RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		HashMap<String,List<SqlConditionExp>> map = new HashMap<String,List<SqlConditionExp>>();
		List<SqlConditionExp> listdata  = null;
		SqlConditionExp sqlConditionExp;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			
			String pid = StringUtils.checkNullString(dataRow.getString("pid"));
			String fieldid = StringUtils.checkNullString(dataRow.getString("fieldid"));
			String inputvaluetype = StringUtils.checkNullString(dataRow.getString("inputvaluetype"));
			String operator = StringUtils.checkNullString(dataRow.getString("operator"));
			String value = StringUtils.checkNullString(dataRow.getString("value"));

			if(dealModel.equals("2"))	
				fieldid = "#" + fieldid + "#";
			if(map.get(pid)!=null){
				listdata = (List<SqlConditionExp>) map.get(pid);
				sqlConditionExp = new SqlConditionExp();
				sqlConditionExp.setFieldid(fieldid);
				sqlConditionExp.setInputvaluetype(inputvaluetype);
				sqlConditionExp.setOperator(operator);
				sqlConditionExp.setValue(value);
				listdata.add(sqlConditionExp);
			}else{
				listdata = new ArrayList<SqlConditionExp>();
				sqlConditionExp = new SqlConditionExp();
				sqlConditionExp.setFieldid(fieldid);
				sqlConditionExp.setInputvaluetype(inputvaluetype);
				sqlConditionExp.setOperator(operator);
				sqlConditionExp.setValue(value);
				listdata.add(sqlConditionExp);
				map.put(pid, listdata);
			}
		}
		
		int mapLen = 0;
		if(map!=null)
			mapLen = map.size();
		if(mapLen>0)//存在规则
			ruleStr = DataExpDeal.getWhereExpr(map,dealModel);
		return ruleStr;
	}
	
	/**
	 * 获取select部分
	 * @param id
	 * @return
	 */
	protected String findDataSourceSql(String id){
		String dataSourceSql = "";
		String p_sql = new String("select datasource from bs_t_sm_ruletpl where status = 1 and pid = ?");
		Object[] values = {id};
		DataTable dataTable = null;
		try{
			dataTable = queryAdapter.executeQuery(p_sql.toString(), values,2);
		}catch(Exception e){
			RecordLog.printLog("获取动作规则模版数据源文件名出错,模版pid="+id, RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		String dataSource = "";
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			dataSource = StringUtils.checkNullString(dataRow.getString("datasource"));
		}
		
		//RConstants.xmlPath = RConstants.xmlPath + File.separator + "sla";
		if(!dataSource.equals(""))
			dataSourceSql = getXmlSql(RConstants.xmlPath + File.separator + "sla",dataSource);
		return dataSourceSql;
	}
	
	/**
	 * 获取源数据集合
	 * @param id
	 * @return
	 */
	protected List<Object> findDataSourceObj(String id){
		List<Object> obj = new ArrayList<Object>();
		String p_sql = new String("select implclass from bs_t_sm_ruletpl where status = 1 and pid = ?");
		Object[] values = {id};
		DataTable dataTable = null;
		try{
			dataTable = queryAdapter.executeQuery(p_sql, values,2);
		}catch(Exception e){
			RecordLog.printLog("获取动作规则模版数据源实现类出错,模版pid="+id, RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		String impclass = "";
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			impclass = StringUtils.checkNullString(dataRow.getString("implclass"));
		}
		if(!impclass.equals("")){
			SlaMatchDataSourceService matchDataSourceService = null;
			if(impclass.contains(".")){
				try {
				   matchDataSourceService = (SlaMatchDataSourceService)Class.forName(impclass).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}else{
				   matchDataSourceService =(SlaMatchDataSourceService)WebApplicationManager.getBean(impclass);
			}
			obj = matchDataSourceService.getDataSource();
		}
		return obj;
	}

	/**
	 * 
	 * @param actionCondition
	 * @param dealModel 匹配类型 1：数据源匹配 2：规则匹配
	 * @param actionid 动作id
	 * @param tplid 模版id
	 * @return
	 */
	protected List<DataRow> getActionBusinessData(IActionCondition actionCondition,String dealModel,String actionid,String tplid){
		if(dealModel=="")
			return null;
		List<DataRow> dataRowList = new ArrayList<DataRow>();
		if(dealModel.equals("1")){//数据源匹配
			dataRowList = actionCondition.matchSqlResult(this.findDataSourceSql(tplid), this.findSlaRules(actionid,dealModel));
		}else if(dealModel.equals("2")){//数据规则匹配
			dataRowList = actionCondition.matchRuleResult(this.findDataSourceObj(tplid), this.findSlaRules(actionid,dealModel));
		}else{
			RecordLog.printLog("异常的处理模式,不予处理!", RecordLog.LOG_LEVEL_ERROR);
		}
		return dataRowList;
	}
	
	protected abstract List<Object> getList();
	
	
	private static String getXmlSql(String slaxmlFileName,String slafileName){
		String sourceDataSql = "";
		if(slafileName == "")
			return "";
		File initfolder=new File(slaxmlFileName);
		File list[]=initfolder.listFiles();  
		int initfolderLen=0;
		if(list!=null)
			initfolderLen=list.length;
		for(int i=0;i<initfolderLen;i++){
			if(list[i].getName().startsWith("SQL_") && list[i].getName().toLowerCase().endsWith(".xml")){
				if(list[i].getName().equals(slafileName)){
					XmlParser xmlParser  = new XmlParser(slaxmlFileName+File.separator+list[i].getName());
					sourceDataSql = xmlParser.getValue("sqlquery#select");
					break;
				}
			}
		}
		return sourceDataSql;
	}
}
