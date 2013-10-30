package com.ultrapower.eoms.mobile.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.ultrapower.eoms.common.core.component.data.DataRow;
//import com.ultrapower.eoms.common.core.component.data.DataTable;
//import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
//import com.ultrapower.eoms.common.core.util.NumberUtils;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;
import cn.com.ultrapower.ultrawf.share.FormatInt;

import com.ultrapower.eoms.mobile.model.BaseOwnFieldModel;
import com.ultrapower.eoms.mobile.model.ContentTemplateModel;
import com.ultrapower.eoms.mobile.model.TemplateActionModel;
import com.ultrapower.eoms.mobile.model.TemplateFieldModel;
import com.ultrapower.eoms.mobile.service.TemplateProviderService;
//import com.ultrapower.remedy4j.core.RemedySession;

public class TemplateProvider implements TemplateProviderService
{
	public String buildWorkflowTemplate(String baseSchema, String versionName)
	{
		List<BaseOwnFieldModel> fieldList = getOwnFieldList(baseSchema);
		
		List<TemplateFieldModel> systemFieldList = new ArrayList<TemplateFieldModel>();
		Map<String, List<TemplateFieldModel>> basefieldMap = new HashMap<String, List<TemplateFieldModel>>();
		Map<String, TemplateActionModel> actionMap = new HashMap<String, TemplateActionModel>();
		
		String beginActionID = "";
		int dicindex = 1;
		Map<String, List<String[]>> dicMap = new HashMap<String, List<String[]>>();
//		Map<String, Map<String, List<ContentTemplateModel>>> contentTemplateMap = getContentTemplateList(baseSchema);
		
		for(BaseOwnFieldModel ownfield : fieldList)
		{
			if(!ownfield.getCollectDic().equals(""))
			{
				String dicName = "dic_" + dicindex;
				List<String[]> dicList = buildDic(1, ownfield.getCollectDic());
				ownfield.setDicName(dicName);
				dicMap.put(dicName, dicList);
				dicindex++;
			}
			else if(!ownfield.getTableDic().equals(""))
			{

				String dicName = "dic_" + dicindex;
				List<String[]> dicList = buildDic(2, ownfield.getTableDic());
				ownfield.setDicName(dicName);
				dicMap.put(dicName, dicList);
				dicindex++;
			}
			
//			if(contentTemplateMap.containsKey(ownfield.getFieldCode()))
//				ownfield.setTemplateName("temp_" + ownfield.getFieldCode());
			
			if(ownfield.getFieldCategory() == 2)
				systemFieldList.add(ownfield.toTemplateField());
			else if(ownfield.getFieldCategory() == 0)
			{
				if(basefieldMap.containsKey(ownfield.getFieldContainer()))
				{
					basefieldMap.get(ownfield.getFieldContainer()).add(ownfield.toTemplateField());
				}
				else
				{
					List<TemplateFieldModel> fieldGroup = new ArrayList<TemplateFieldModel>();
					fieldGroup.add(ownfield.toTemplateField());
					basefieldMap.put(ownfield.getFieldContainer(), fieldGroup);
				}
				if(beginActionID.equals(""))
				{
					String actionStr = ownfield.getEditActions();
					if(actionStr == null || actionStr.equals("")) continue;
					actionStr = actionStr.substring(1, actionStr.length() - 1);
					String[] actionList = actionStr.split(";");
					for(String actionItem : actionList)
					{
						String[] actionArr = actionItem.split("=");
						if(!actionArr[1].equals("SAVE"))
						{
							beginActionID = actionArr[1];
						}
					}
				}
			}
			else
			{
				String actionStr = ownfield.getEditActions();
				if(actionStr == null || actionStr.equals("")) continue;
				actionStr = actionStr.substring(1, actionStr.length() - 1);
				String[] actionList = actionStr.split(";");
				for(String actionItem : actionList)
				{
					String[] actionArr = actionItem.split("=");
					TemplateActionModel actionModel = null;
					if(actionArr.length>1&&!actionMap.containsKey(actionArr[1])) 
					{
						actionModel = new TemplateActionModel();
						actionModel.setId(actionArr[1]);
						actionModel.setText(actionArr[0]);
						if(actionModel.getId().substring(0, 1).matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"))
							actionModel.setCode("NEXT");
						else
							actionModel.setCode(actionModel.getId());
						actionModel.setFields(new ArrayList<TemplateFieldModel>());
						actionMap.put(actionArr[1], actionModel);
					}
					else
					{
						actionModel = actionMap.get(actionArr[1]);
					}
					if(actionModel.getPhoto() == 0)
						actionModel.setPhoto(ownfield.getUploadPic());
					if(actionModel.getRadio() == 0)
						actionModel.setRadio(ownfield.getUploadRec());
					actionMap.get(actionArr[1]).getFields().add(ownfield.toTemplateField());
				}
			}
		}
		
		String newVersionName = versionName;
		if(versionName == null)
		{
			Calendar cal = Calendar.getInstance();
			newVersionName = baseSchema.replace(":", "_") + "_"
												+ cal.get(Calendar.YEAR)
												+ cal.get(Calendar.MONTH)
												+ cal.get(Calendar.DAY_OF_MONTH)
												+ "_" + (System.currentTimeMillis()/1000);
		}
		
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<opDetail><baseInfo><isLegal>1</isLegal>");
		xmlBuilder.append("<category>" + baseSchema + "</category>");
		xmlBuilder.append("<version>" + newVersionName + "</version>");
		xmlBuilder.append("</baseInfo>");
		xmlBuilder.append("<recordInfo>");
		
		//回复模板  Map<String, List<ContentTemplateModel>>
		xmlBuilder.append("<templatestore>");
//		for(String templateFieldCode : contentTemplateMap.keySet())
//		{
//			Map<String, List<ContentTemplateModel>> templateMap = contentTemplateMap.get(templateFieldCode);
//			xmlBuilder.append("<template id='temp_" + templateFieldCode + "'>");
//			for(String templatekey : templateMap.keySet())
//			{
//				List<ContentTemplateModel> templateList = templateMap.get(templatekey);
//				xmlBuilder.append("<part param='" + templatekey + "'>");
//				for(ContentTemplateModel ctModel : templateList)
//				{
//					xmlBuilder.append("<content><![CDATA[");
//					xmlBuilder.append(ctModel.getContent());
//					xmlBuilder.append("]]></content>");
//				}
//				xmlBuilder.append("</part>");
//				
//			}
//			xmlBuilder.append("</template>");
//		}
		xmlBuilder.append("</templatestore>");
		
		//字典
		xmlBuilder.append("<dicDefine>");
		for(String dicKey : dicMap.keySet())
		{
			xmlBuilder.append("<dic id='" + dicKey + "' type='COLLECT'>");
			List<String[]> dicList = dicMap.get(dicKey);
			for(String[] dicItem : dicList)
			{
				xmlBuilder.append("<item code='" + dicItem[0] + "'>" + dicItem[1] + "</item>");
			}
			xmlBuilder.append("</dic>");
		}
		xmlBuilder.append("</dicDefine>");
		
		//系统字段
		xmlBuilder.append("<baseField>");
		xmlBuilder.append("<field code='DealProcessDesc' type='LABEL'>流程信息</field>");
		for(TemplateFieldModel sysField : systemFieldList)
		{
			xmlBuilder.append("<field code='" + sysField.getCode() + "' type='LABEL'>" + sysField.getName() + "</field>");
		}
		//xmlBuilder.append("<field code='BaseAcceptOutTime' type='LABEL'>受理时限</field>");
		//xmlBuilder.append("<field code='BaseDealOutTime' type='LABEL'>处理时限</field>");
		
		//主表单字段
		for(String groupKey : basefieldMap.keySet())
		{
			xmlBuilder.append("<fieldGroup text='" + groupKey + "'>");
			for(TemplateFieldModel sysField : basefieldMap.get(groupKey))
			{
				xmlBuilder.append(sysField.getXmlString());
			}
			xmlBuilder.append("</fieldGroup>");
		}
		
		//基本信息
		xmlBuilder.append("<fieldGroup text='基本信息'>");
		xmlBuilder.append("<field code='BaseCreatorFullName' type='STRING'>建单人</field>");
		xmlBuilder.append("<field code='BaseCreatorConnectWay' type='STRING'>建单人联系方式</field>");
		xmlBuilder.append("<field code='BaseCreatorCorp' type='STRING'>建单人单位</field>");
		xmlBuilder.append("<field code='BaseCreatorDep' type='STRING'>建单人部门</field>");
		xmlBuilder.append("<field code='BaseCreateDate' type='TIME'>建单时间</field>");
		xmlBuilder.append("<field code='BaseCloseDate' type='TIME'>关闭时间</field>");
		xmlBuilder.append("</fieldGroup>");
		
		xmlBuilder.append("</baseField>");
		
		//动作字段
		xmlBuilder.append("<actionFields>");
		xmlBuilder.append("<action id='ACCEPT' code='ACCEPT' text='受理' radio='0' photo='0'></action>");
		for(TemplateActionModel actionModel : actionMap.values())
		{
			if(actionModel.getId().equals(beginActionID))
				actionModel.setIsBeginAction(1);
			xmlBuilder.append(actionModel.getXmlString());
		}
		xmlBuilder.append("</actionFields>");
		
		xmlBuilder.append("</recordInfo>");
		xmlBuilder.append("</opDetail>");
		
		return xmlBuilder.toString();
	}
	
	private List<String[]> buildDic(int dicType, String dicResource)
	{
		//[value, text]
		List<String[]> dicList = new ArrayList<String[]>();
		if(dicType == 1)
		{
			String fenhao = dicResource.substring(dicResource.length()-1, dicResource.length());
			String[] items = null;
			if(";".equals(fenhao))
			{
				items = dicResource.substring(0, dicResource.length() - 1).split(";");
			}else
			{
				items = dicResource.substring(0, dicResource.length()).split(";");
			}
			for(String item : items)
			{
				String[] itemArr = item.split("=");
				dicList.add(new String[] {itemArr[0], itemArr[1]});
			}
		}
		else
		{
			String[] tableParams = dicResource.split("#");
			RemedyDBOp remedyDBOp = new RemedyDBOp();
			String tableName = remedyDBOp.GetRemedyTableName(tableParams[0]);
			if(tableName != null)
			{
			String querySql = "select C" + tableParams[1] + " as dicval from " + tableName + "  ORDER BY NLSSORT(C"+tableParams[1]+",'NLS_SORT = SCHINESE_PINYIN_M') ";//order by C" + tableParams[1] + " ";
//			String querySql = "select C650000001 as dicval from T168  ORDER BY NLSSORT(C650000001,'NLS_SORT = SCHINESE_PINYIN_M') " ;
			IDataBase iDataBase = GetDataBase.createDataBase();
			Table table_DicDatas = new Table(iDataBase,"");
			Object[] values = null;
			RowSet rowSet_DicDatas = table_DicDatas.executeQuery(querySql, values, 0, 0, 2);	
			if(rowSet_DicDatas != null && rowSet_DicDatas.length() > 0)
			{
				for(int i=0; i<rowSet_DicDatas.length();i++)
				{
					String val = rowSet_DicDatas.get(i).getString("dicval");
					dicList.add(new String[] {val, val});
				}
			}
			}
		}
		return dicList;
	}
	
	private Map<String, Map<String, List<ContentTemplateModel>>> getContentTemplateList(String baseSchema)
	{
		Map<String, Map<String, List<ContentTemplateModel>>> contentTemplateMap = new HashMap<String, Map<String, List<ContentTemplateModel>>>();
		RemedyDBOp remedyDBOp = new RemedyDBOp();
		String tableName = remedyDBOp.GetRemedyTableName("WF:EL_AM_Template_Form");
//		String tableName = RemedySession.UtilInfor.getTableName("WF4:EL_AM_Template_Form");
		String querySql = "select C1 as contentid" +
							", C650000007 as baseschema" +
							", C650000001 as title" +
							", C650000009 as targetfield" +
							", C650000012 as matchrule" +
							", C650000003 as content" +
							" from " + tableName + " where C650000007='" + baseSchema + "'";
		IDataBase iDataBase = GetDataBase.createDataBase();
		Table table_TemplateTable = new Table(iDataBase,"");
		RowSet rowSet_TemplateTable = table_TemplateTable.executeQuery(querySql, null, 0, 0, 2);	
		int templateTable_length = 0;
		if(rowSet_TemplateTable != null && rowSet_TemplateTable.length() > 0)
		{
			templateTable_length = rowSet_TemplateTable.length();
			for(int i=0; i<templateTable_length;i++)
			{
				Row row = rowSet_TemplateTable.get(i);
				String contentid = row.getString("contentid");
				String baseschema = row.getString("baseschema");
				String title = row.getString("title");
				String targetfield = row.getString("targetfield");
				String matchrule = row.getString("matchrule");
				String content = row.getString("content");
				
				Map<String, List<ContentTemplateModel>> ctModelMap = new HashMap<String, List<ContentTemplateModel>>();
				List<ContentTemplateModel> ctModelList = new ArrayList<ContentTemplateModel>();
				
				if(contentTemplateMap.containsKey(targetfield))
					ctModelMap = contentTemplateMap.get(targetfield);
				else
					contentTemplateMap.put(targetfield, ctModelMap);
				
				if(ctModelMap.containsKey(matchrule))
					ctModelList = ctModelMap.get(matchrule);
				else
					ctModelMap.put(matchrule, ctModelList);
				
				ContentTemplateModel ctModel = new ContentTemplateModel();
				ctModel.setContentTemplateID(contentid);
				ctModel.setBaseSchema(baseschema);
				ctModel.setTitle(title);
				ctModel.setTargetField(targetfield);
				ctModel.setMatchRule(matchrule);
				ctModel.setContent(content);
				
				ctModelList.add(ctModel);
			}
		}
		
		return contentTemplateMap;
	}
	
	private List<BaseOwnFieldModel> getOwnFieldList(String baseSchema)
	{
		List<BaseOwnFieldModel> ownFieldList = new ArrayList<BaseOwnFieldModel>();
		RemedyDBOp remedyDBOp = new RemedyDBOp();
		String tableName = remedyDBOp.GetRemedyTableName("WF:Config_BaseOwnFieldInfo_Mobile");
//		String tableName = RemedySession.UtilInfor.getTableName("WF4:Config_BaseOwnFieldInfo");
		String querySql = "select C650000002 as baseschema" +
							", C650000004 as fieldcode" +
							", C650000005 as fieldname" +
							", C650000006 as fieldtype" +
							", C650030021 as fieldcategory" +
							", C650030025 as fieldcontainer" +
							", C650030020 as rowindex" +
							", C650000024 as hiddenfield" +
							", C650000007 as collectdic" +
							", C650042020 as tabledic" +
							", C650000013 as orderindex" +
							", C650030023 as uploadrec" +
							", C650030024 as uploadpic" +
							", C650030022 as tree_assigntype" +
							", C650000025 as tree_multiselect" +
							", C650000052 as tree_showcorp" +
							", C650000053 as tree_showcenter" +
							", C650000050 as tree_showstation" +
							", C650000056 as tree_showteam" +
							", C650000051 as tree_showperson" +
							", C650000054 as tree_selectobject" +
							", C650042014 as editactions" +
							" from " + tableName + " where C650000002='" + baseSchema + "' order by C650000013 asc";
		
		IDataBase iDataBase = GetDataBase.createDataBase();
		Table table_OwnFields = new Table(iDataBase,"");
		RowSet rowSet_OwnFields = table_OwnFields.executeQuery(querySql, null, 0, 0, 2);	
		int ownFields_length = 0;
		if(rowSet_OwnFields != null && rowSet_OwnFields.length() > 0)
		{
			ownFields_length = rowSet_OwnFields.length();
			for(int i=0; i<ownFields_length;i++)
			{
				Row row = rowSet_OwnFields.get(i);
				BaseOwnFieldModel fieldModel = new BaseOwnFieldModel();
				fieldModel.setBaseSchema(row.getString("baseschema"));
				fieldModel.setFieldCode(row.getString("fieldcode"));
				fieldModel.setFieldName(row.getString("fieldname"));
				fieldModel.setFieldType(row.getString("fieldtype"));
				fieldModel.setFieldCategory(row.getInt("fieldcategory"));
				fieldModel.setFieldContainer(row.getString("fieldcontainer"));
				fieldModel.setRows(row.getInt("rowindex"));
				fieldModel.setHiddenField(row.getInt("hiddenfield"));
				fieldModel.setCollectDic(row.getString("collectdic"));
				fieldModel.setTableDic(row.getString("tabledic"));
				fieldModel.setOrder(row.getInt("orderindex"));
				fieldModel.setUploadRec(row.getInt("uploadrec"));
				fieldModel.setUploadPic(row.getInt("uploadpic"));
				fieldModel.setTree_assignType(row.getString("tree_assigntype"));
				fieldModel.setTree_multiSelect(row.getInt("tree_multiselect"));
				fieldModel.setTree_showCorp(FormatInt.FormatStringToInt(row.getString("tree_showcorp")));
				fieldModel.setTree_showCenter(FormatInt.FormatStringToInt(row.getString("tree_showcenter")));
				fieldModel.setTree_showStation(FormatInt.FormatStringToInt(row.getString("tree_showstation")));
				fieldModel.setTree_showTeam(FormatInt.FormatStringToInt(row.getString("tree_showteam")));
				fieldModel.setTree_showPerson(FormatInt.FormatStringToInt(row.getString("tree_showperson")));
				fieldModel.setTree_selectObject(row.getString("tree_selectobject"));
				fieldModel.setEditActions(row.getString("editactions"));
				
				ownFieldList.add(fieldModel);
			}
		}
		return ownFieldList;
	}
}
