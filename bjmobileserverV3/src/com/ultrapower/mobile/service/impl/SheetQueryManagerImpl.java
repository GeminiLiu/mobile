package com.ultrapower.mobile.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.biz.ItSysWsFacade;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.plugin.datagrid.grid.Limit;
import com.ultrapower.mobile.common.constants.Constants;
import com.ultrapower.mobile.common.utils.WSUtil;
import com.ultrapower.mobile.common.utils.XMLParamParser;
import com.ultrapower.mobile.model.WfType;
import com.ultrapower.mobile.model.WorkSheet;
import com.ultrapower.mobile.model.xml.ActionModel;
import com.ultrapower.mobile.model.xml.FieldInfo;
import com.ultrapower.mobile.model.xml.XmlInfo;
import com.ultrapower.mobile.service.SheetQueryService;

public class SheetQueryManagerImpl implements SheetQueryService {

	public static Logger log = LoggerFactory.getLogger(SheetQueryManagerImpl.class);
	
	public List<WorkSheet> queryWaitDeal(String userName, String itSysName, String baseSchema) {
		PageLimit instance = PageLimit.getInstance();
		Limit limit = instance.getLimit();
		int page = limit.getPage();
		int pageSize = limit.getPageSize();
		List<WorkSheet> sheetList = null;
		
		try {
			String result = this.queryWaitDeal(userName, itSysName, page, pageSize, baseSchema);
			XmlInfo xmlInfo = XMLParamParser.convert(result);
			sheetList = XMLParamParser.conToWorkSheet(xmlInfo);
			instance.getLimit().setRowAttributes(NumberUtils.formatToInt(xmlInfo.getContent(Constants.WAITDEALTOTALCOUNT)), instance.getCURRENT_ROWS_SIZE());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new RuntimeException("查询待办列表出错！");
		}
//		if (CollectionUtils.isEmpty(sheetList)) {
//			sheetList = new ArrayList<WorkSheet>();
//		}
		return sheetList;
	}
	
	public String queryWaitDeal(String userName, String itSysName, int page, int pageSize, String baseSchema) {
		XMLParamParser parser = XMLParamParser.createInstance();
		parser.addAttr(Constants.INF_TYPE, "", Constants.INF_TYPE_WS_GETUSERWAITINGLIST);
		parser.addAttr(Constants.USERNAME, "", userName);
		parser.addAttr(Constants.CURRENT_PAGE, "", page);
		parser.addAttr(Constants.PAGE_SIZE, "", pageSize);
		parser.addAttr(Constants.BASESCHEMA, "", baseSchema);
		log.info("查询待办列表！userName=" + userName + ",page=" + page + ",pageSize=" + pageSize + ",baseSchema=" + baseSchema);
		
		try {
			ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysName);
			String result = itSysWsFacade.invoke(parser.getXmlContent());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new RuntimeException("查询待办列表出错！");
		}
	}

	public List<WorkSheet> dealedSheet(String userName, String itSysName, String baseSchema) {
		PageLimit instance = PageLimit.getInstance();
		Limit limit = instance.getLimit();
		int page = limit.getPage();
		int pageSize = limit.getPageSize();
		List<WorkSheet> sheetList = null;
		XMLParamParser parser = XMLParamParser.createInstance();
		parser.addAttr(Constants.INF_TYPE, "", Constants.INF_TYPE_WS_GETUSERDEALEDLIST);
		parser.addAttr(Constants.USERNAME, "", userName);
		parser.addAttr(Constants.CURRENT_PAGE, "", page);
		parser.addAttr(Constants.PAGE_SIZE, "", pageSize);
		parser.addAttr(Constants.BASESCHEMA, "", baseSchema);
		
		log.info("查询已办列表！userName=" + userName + ",page=" + page + ",pageSize=" + pageSize + ",baseSchema=" + baseSchema);
		
		try {
			ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysName);
			String result = itSysWsFacade.invoke(parser.getXmlContent());
			XmlInfo xmlInfo = XMLParamParser.convert(result);
			sheetList = XMLParamParser.conToWorkSheet(xmlInfo);
			instance.getLimit().setRowAttributes(NumberUtils.formatToInt(xmlInfo.getContent(Constants.WAITDEALTOTALCOUNT)), instance.getCURRENT_ROWS_SIZE());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new RuntimeException("查询已办列表出错！");
		}
		return sheetList;
	}
	
	public List<FieldInfo> workSheetInfo(String itSysName, String baseId, String baseSchema, String tplId) {
		XMLParamParser parser = XMLParamParser.createInstance();
		parser.addAttr(Constants.INF_TYPE, "", Constants.INF_TYPE_WS_GETALLBIZFIELDS);
		parser.addAttr("baseSchema", "", baseSchema);
		parser.addAttr("tplId", "", tplId);
		parser.addAttr("baseId", "", baseId);
		
		log.info("查询工单字段!baseSchema=" + baseSchema + ",baseId=" + baseId + ",tplId=" + tplId);
		ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysName);
		String result;
		try {
			result = itSysWsFacade.invoke(parser.getXmlContent());
			XmlInfo xmlInfo = XMLParamParser.convert(result);
			List<List<FieldInfo>> listRecords = xmlInfo.getListRecords();
			if (CollectionUtils.isNotEmpty(listRecords)) {
				return listRecords.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public List<ActionModel> getAvailableActions(String itSysName, String baseId, String baseSchema, String tplId, String taskId, String baseStatus) {
		XMLParamParser parser = XMLParamParser.createInstance();
		parser.addAttr(Constants.INF_TYPE, "", Constants.INF_TYPE_WS_GETAVAILABLEACTIONS);
		parser.addAttr("baseSchema", "", baseSchema);
		parser.addAttr("tplId", "", tplId);
		parser.addAttr("baseId", "", baseId);
		parser.addAttr("taskId", "", taskId);
		parser.addAttr("baseStatus", "", baseStatus);
		
		log.info("获取当前任务的可用动作！baseSchema=" + baseSchema + ",baseId=" + baseId + ",tplId=" + tplId + ",taskId=" + taskId);
		ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysName);
		String result;
		try {
			result = itSysWsFacade.invoke(parser.getXmlContent());
			XmlInfo xmlInfo = XMLParamParser.convert(result);
			List<ActionModel> actions = xmlInfo.getActions();
			return actions;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public String getEditFieldsByAction(String itSysName, String baseId, String baseSchema, String tplId, String taskId, String actionStr) {
		XMLParamParser parser = XMLParamParser.createInstance();
		parser.addAttr(Constants.INF_TYPE, "", Constants.INF_TYPE_WS_GETEDITFIELDSBYACTION);
		parser.addAttr("baseSchema", "", baseSchema);
		parser.addAttr("tplId", "", tplId);
		parser.addAttr("baseId", "", baseId);
		parser.addAttr("taskId", "", taskId);
		
		log.info("获取动作的可编辑字段！baseSchema=" + baseSchema + ",baseId=" + baseId + ",tplId=" + tplId + ",taskId=" + taskId + ",actionStr=" + actionStr);
		String[] s = actionStr.split("@");
		if (StringUtils.isNotBlank(s[0])) {
			actionStr = s[0];
		} else {
			actionStr = s[1];
		}
		parser.addAttr("actionStr", "", actionStr);
		
		ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysName);
		String result = null;
		try {
			result = itSysWsFacade.invoke(parser.getXmlContent());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	public List<FieldInfo> dealInfo(String itSysName, String baseId, String baseSchema, String tplId) {
		XMLParamParser parser = XMLParamParser.createInstance();
		parser.addAttr(Constants.INF_TYPE, "", Constants.INF_TYPE_WS_GETDEALINFO);
		parser.addAttr("baseSchema", "", baseSchema);
		parser.addAttr("tplId", "", tplId);
		parser.addAttr("baseId", "", baseId);
		
		log.info("获取处理信息！baseSchema=" + baseSchema + ",baseId=" + baseId + ",tplId=" + tplId);
		ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysName);
		String result;
		try {
			result = itSysWsFacade.invoke(parser.getXmlContent());
			XmlInfo xmlInfo = XMLParamParser.convert(result);
			if (xmlInfo != null) {
				List<List<FieldInfo>> listRecords = xmlInfo.getListRecords();
				if (CollectionUtils.isNotEmpty(listRecords)) {
					return listRecords.get(0);
//					for (int i = 0; i < listRecords.size(); i++) {
//						List<FieldInfo> list = listRecords.get(i);
//						if (CollectionUtils.isNotEmpty(list)) {
//							for (int j = 0; j < list.size(); j++) {
//								FieldInfo fie = list.get(j);
//								sb.append(fie.getContent());
//							}
//						}
//					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public String saveSheet(String itSysName, String baseId,
			String baseSchema, String userName, String actionStr, String dealActorStr, List<FieldInfo> bizFields) {
		XMLParamParser parser = XMLParamParser.createInstance();
		parser.addAttr(Constants.INF_TYPE, "", Constants.INF_TYPE_WS_TRANSFLOW);
		parser.addAttr("baseSchema", "", baseSchema);
		parser.addAttr("baseId", "", baseId);
		parser.addAttr("userName", "", userName);
		parser.addAttr("dealActorStr", "", com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(dealActorStr));
		parser.addListAttr(bizFields);
		log.info("工单流转！baseSchema=" + baseSchema + ",baseId=" + baseId + ",userName=" + userName + ",actionStr=" + actionStr);
		if (StringUtils.isNotBlank(actionStr)) {
			String[] ary = actionStr.split("@");
			String actionType = "";
			String actionText = "";
			if (!ArrayUtils.isEmpty(ary)) {
//				if (StringUtils.isNotBlank(ary[1])) {
					actionType = ary[1];
//				}
				if ("NEXT".equals(actionType)) {
					actionText = ary[2];
				}
				parser.addAttr("actionType", "", actionType);
				parser.addAttr("actionText", "", actionText);
				try {
					ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysName);
					String result = itSysWsFacade.invoke(parser.getXmlContent());
					XmlInfo xmlInfo = XMLParamParser.convert(result);
					return xmlInfo.getContent("result");
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(), e);
				}
			}
		}
		return null;
	}
	
	public List<WfType> getAllWfTypes(String itSysName) {
		XMLParamParser parser = XMLParamParser.createInstance();
		parser.addAttr(Constants.INF_TYPE, "", Constants.INF_TYPE_WS_GETWFTYPES);
		try {
			ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysName);
			String result = itSysWsFacade.invoke(parser.getXmlContent());
			XmlInfo xmlInfo = XMLParamParser.convert(result);
			return XMLParamParser.convertWfTypes(xmlInfo.getListRecords().get(0));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
