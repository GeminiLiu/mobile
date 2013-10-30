package com.ultrapower.eoms.mobile.interfaces.querydeallist.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

//import com.ultrapower.eoms.common.core.component.data.DataRow;
//import com.ultrapower.eoms.common.core.component.data.DataTable;
//import com.ultrapower.eoms.common.core.component.rquery.RQuery;
//import com.ultrapower.eoms.common.core.util.TimeUtils;
//import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;
import cn.com.ultrapower.eoms.util.CalendarUtil;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.ultrawf.control.config.BaseQueryXmlConfigManage;
import cn.com.ultrapower.ultrawf.models.config.User;
import cn.com.ultrapower.ultrawf.models.config.UserModel;

import com.ultrapower.eoms.common.service.impl.BaseManagerImpl;
import com.ultrapower.eoms.mobile.interfaces.querydeallist.model.QueryDealListInputModel;
import com.ultrapower.eoms.mobile.interfaces.querydeallist.model.QueryDealListOutputModel;
import com.ultrapower.eoms.mobile.service.InterfaceService;
import com.ultrapower.eoms.mobile.util.ConvertUtil;
//import com.ultrapower.eoms.ultrasm.model.UserInfo;
//import com.ultrapower.eoms.ultrasm.service.UserManagerService;
//import com.ultrapower.eoms.workflow.sheet.agent.model.Agency;
//import com.ultrapower.eoms.workflow.sheet.agent.service.AgencyService;
//import com.ultrapower.eoms.workflow.sheet.query.service.IsheetQueryService;
//import com.ultrapower.eoms.workflow.sheet.role.model.RoleUser;
//import com.ultrapower.eoms.workflow.sheet.role.service.IwfRoleManagerService;
import com.ultrapower.eoms.system.usermanager.model.UserManagerUserInfor;
import com.ultrapower.eoms.system.usermanager.service.ContractUserManagerUserInforImpl;
import cn.com.ultrapower.system.table.*;
/**
 * 查询工单列表的手机接口服务
 * @author Haoyuan
 */
public class QueryDealListManager implements InterfaceService
{
//	private UserManagerService userManagerService;
//	private AgencyService agencyService;
//	private IwfRoleManagerService wfRoleManager;
//	protected IsheetQueryService sheetQueryServiceImpl;
	private BaseManagerImpl contractUserManagerUserInforImpl;

	public String call(String xml, String fileList)
	{
		QueryDealListInputModel input = new QueryDealListInputModel();
		String outputXml;
		try
		{
			input.buildModel(xml);
			QueryDealListOutputModel output = handle(input);
			outputXml = output.buildXml();
		}
		catch (Exception e)
		{
			outputXml = QueryDealListOutputModel.buildExceptionXml();
			e.printStackTrace();
		}
		return outputXml;
	}

	private QueryDealListOutputModel handle(QueryDealListInputModel inputModel)
	{
		System.out.println("G001 Time------->begin  "+CalendarUtil.getCurrentDateTime());
		QueryDealListOutputModel outputModel = new QueryDealListOutputModel();
		
		Map<String, Integer> baseCount = new LinkedHashMap<String, Integer>();
		List<Map<String, String>> baseList = new ArrayList<Map<String,String>>();
		User user = new User();
		UserModel userModel = user.UserIsExist(inputModel.getUserName());
		
		//人员工单处理同组归档配置信息 
		String groupID_userClose = "";
		String p_Schema="WF:Config_UserCloseBaseGroup";
		IDataBase iDataBase = GetDataBase.createDataBase();
		Table table_UserClose = new Table(iDataBase,"");
		RemedyDBOp remedyDBOp = new RemedyDBOp();
		String tblSchema = remedyDBOp.GetRemedyTableName(p_Schema);
		String sql_UserClose = "select distinct c650000006 as  CloseBaseGroupID  from "+tblSchema + " where c650000004 = '" + userModel.GetLoginName() + "'";
		Object[] values = null;
		RowSet rowSet_UserClose = table_UserClose.executeQuery(sql_UserClose, values, 0, 0, 2);	
		if(rowSet_UserClose !=null && rowSet_UserClose.length()>0)
		{	
			for(int i=0; i<rowSet_UserClose.length();i++)
			{
				groupID_userClose = groupID_userClose + rowSet_UserClose.get(i).getString("CloseBaseGroupID")+";";
			}
		}
		
		
		//查询各工单待办总数
		String[] flows = ConvertUtil.mobileFlows().split(",");
		for(String flow : flows)
		{
			ReBuildSQL reBuildSQL =  this.buildRQuery(inputModel,userModel,flow,1,groupID_userClose);//客户端每次获取工单待办个数 1：待办 0：已办
			String sqlCount = reBuildSQL.getReBuildCountSQL();
			Table table = new Table(GetDataBase.createDataBase(),"");
			RowSet rowSetCount = table.executeQuery(sqlCount,null,0,0,0);
			int qrowcount = 0;
//			baseCount.put(flow, qrowcount);
			if(rowSetCount != null)
			{
				Row ROWS = rowSetCount.get(0); 
				if(ROWS != null)
			  		qrowcount = ROWS.getInt("ROWCOUNT");
				baseCount.put(flow, qrowcount);
			}
		}
		outputModel.setBaseCount(baseCount);
		
		ReBuildSQL reBuildSQL = this.buildRQuery(inputModel, userModel,inputModel.getCategory(),inputModel.getIsWait(),groupID_userClose);
		String sql = reBuildSQL.getReBuildSQL();
		Table table = new Table(GetDataBase.createDataBase(),"");
		//配置
		int pageNum = inputModel.getPageNum();
		int pageSize = inputModel.getPageSize();
		RowSet rowSet = table.executeQuery(sql,null,pageNum,pageSize,0);
		String[] fieldKeys = inputModel.getFields().split(",");
		int rowSet_Length = 0;
		if(rowSet!=null){
			rowSet_Length = rowSet.length();
		}
		if (rowSet != null)
		{
			for (int row = 0; row < rowSet_Length; row++)
			{
				Row rs = rowSet.get(row);
				Map<String, String> fieldMap = new LinkedHashMap<String, String>();
				long dealouttime = rs.getlong("BaseDealOuttime");
				long now = System.currentTimeMillis() / 1000;
				int isOut = (dealouttime > now) ? 0 : 1;
				for(String field : fieldKeys)
				{
					fieldMap.put(field, rs.getString(field));
				}
				fieldMap.put("SYS_ISOUT", String.valueOf(isOut));
				baseList.add(fieldMap);
			}
		}
		outputModel.setBaseList(baseList);
		System.out.println("G001 Time------->end  "+CalendarUtil.getCurrentDateTime());
		return outputModel; 
	}

	public ReBuildSQL buildRQuery(QueryDealListInputModel inputModel,UserModel userModel,String baseschema,int isWait,String groupID_userClose){
		
//		int isWait = inputModel.getIsWait();
		List<RowSet> rowSetList = new ArrayList<RowSet>();
		//查询工单数据列表
		boolean allSchema = false;
		if(baseschema == null || baseschema.equals(""))
		{
			allSchema = true;
		}
		//sqlxml 标识 xmlName
		String xmlName = "";
		BaseQueryXmlConfigManage xmlManage = new BaseQueryXmlConfigManage();
		if(allSchema){
			if(isWait == 1)
			{
				xmlName = "query.MyWaitingDeal.user";
			}
			else{
				xmlName = "query.MyDealed.user";
			}
		}
		else{
			if(isWait == 1)
			{
				xmlName = xmlManage.getBaseQueryName("query.MyWaitingDeal.user", baseschema);
			}else{
				xmlName = xmlManage.getBaseQueryName("query.MyDealed.user", baseschema);
			}
		}
		
		String GroupID = userModel.GetGroupList().indexOf(";") > -1 ? userModel.GetGroupList() : userModel.GetGroupList() + ";";
		String RoleID = userModel.getRoleList().indexOf(";") > -1 ? userModel.getRoleList() : userModel.getRoleList() + ";";
		
		/*String groupID_userClose = "";
		String p_Schema="WF:Config_UserCloseBaseGroup";
		IDataBase iDataBase = GetDataBase.createDataBase();
		Table table_UserClose = new Table(iDataBase,"");
		RemedyDBOp remedyDBOp = new RemedyDBOp();
		String tblSchema = remedyDBOp.GetRemedyTableName(p_Schema);
		String sql_UserClose = "select distinct c650000006 as  CloseBaseGroupID  from "+tblSchema + " where c650000004 = '" + userModel.GetLoginName() + "'";
		Object[] values = null;
		RowSet rowSet_UserClose = table_UserClose.executeQuery(sql_UserClose, values, 0, 0, 2);	
		if(rowSet_UserClose !=null && rowSet_UserClose.length()>0)
		{	
			for(int i=0; i<rowSet_UserClose.length();i++)
			{
				groupID_userClose = groupID_userClose + rowSet_UserClose.get(i).getString("CloseBaseGroupID")+";";
			}
		}
		*/
		RDParameter rdp = new RDParameter();
		rdp.addIndirectPar("orderby", " baseid desc ", 4);
		rdp.addIndirectPar("RoleID", RoleID, 4);
		if(isWait == 1)
		{
			rdp.addIndirectPar("AssgineeID", userModel.GetLoginName(), 4);
			rdp.addIndirectPar("GroupID", GroupID, 4);
			rdp.addIndirectPar("CloseBaseSamenessGroupID", groupID_userClose, 4);
			rdp.addIndirectPar("CompanyID", "1;", 4);
			rdp.addIndirectPar("type", "user", 4);
		}else{
			rdp.addIndirectPar("loginname", userModel.GetLoginName(), 4);
		}
		if(allSchema)
		{
			
			String mobileFlows =  ConvertUtil.mobileFlows();
			if(mobileFlows!=null&&mobileFlows.length()>0)
			{
				StringBuffer baseC700000001Sql = new StringBuffer();
				for(String C700000001:mobileFlows.split(","))
				{
					baseC700000001Sql.append("or  base.C700000001 = '"+C700000001+"'  ");
				}
				String basesql =  baseC700000001Sql.toString().substring(2, baseC700000001Sql.length());
				rdp.addIndirectPar("conditionsql", basesql, 4);
				
			}
		}
		else{
			rdp.addIndirectPar("baseschema", baseschema, 4);
		}
		rdp.addIndirectPar("baseclass", "", 4);
		ReBuildSQL reBuildSQL = new ReBuildSQL(xmlName,rdp, "");
		return reBuildSQL;
	}

}
