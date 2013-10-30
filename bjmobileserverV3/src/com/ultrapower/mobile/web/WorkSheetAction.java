package com.ultrapower.mobile.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;

import com.bmc.thirdparty.org.apache.commons.lang.ArrayUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.mobile.common.startup.Init;
import com.ultrapower.mobile.common.utils.XMLParamParser;
import com.ultrapower.mobile.model.xml.ActionModel;
import com.ultrapower.mobile.model.xml.DictInfo;
import com.ultrapower.mobile.model.xml.FieldInfo;
import com.ultrapower.mobile.model.xml.XmlInfo;
import com.ultrapower.mobile.service.JspExtendFunction;
import com.ultrapower.mobile.service.SheetQueryService;


public class WorkSheetAction extends BaseAction {
	
	private SheetQueryService sheetQueryService;
	private JspExtendFunction jspExtendFunction;
	
	private String itSysName;
	
	private List<FieldInfo> bizFields;
	private List<FieldInfo> dpLogFields;
	private List<ActionModel> actions;
	private String baseId;
	private String baseSchema;
	private String tplId;
	private String baseName;
	private String baseSn;
	private String baseStatus;
	private String dealTime;
	private String acceptTime;
	private String taskName;
	private String taskId;
	
	private String actionStr;
	private String dealActorStr;
	
	public String save() {
		UserSession userSession = super.getUserSession();
		if("WF4:EL_TTM_TTH".equals(baseSchema)&&actionStr.contains("内部转派"))
		{
			actionStr = actionStr.replace("NEXT", "ASSIGN");
			dealActorStr = dealActorStr.replace("NEXT", "ASSIGN");
		}
		String result = sheetQueryService.saveSheet(itSysName, baseId, baseSchema, userSession.getLoginName(), actionStr, dealActorStr, bizFields);
		if (StringUtils.isNotBlank(result)) {//处理成功
			getRequest().setAttribute("msg", result);
			return "close";
		} else {
			getRequest().setAttribute("error", "处理失败!");
			return "error";
		}
	}
	
	public String waitingSheetInfo() {
		bizFields = sheetQueryService.workSheetInfo(itSysName, baseId, baseSchema, tplId);
		baseName = XMLParamParser.getContent(bizFields, "BaseName");
		baseStatus = XMLParamParser.getContent(bizFields, "BaseStatus");
		baseSn = XMLParamParser.getContent(bizFields, "BaseSn");
		dpLogFields = sheetQueryService.dealInfo(itSysName, baseId, baseSchema, tplId);
		actions = sheetQueryService.getAvailableActions(itSysName, baseId, baseSchema, tplId, taskId, baseStatus);
		String waitDeal = Init.getWaitDeal(baseSchema);
		if (StringUtils.isNotBlank(waitDeal)) {
			return findForward(waitDeal);
		} else {
			jspExtendFunction.open(baseSchema, baseStatus, baseSn, bizFields, dpLogFields, actions);
			return findForward("waitingSheetInfo");
		}
	}
	
	public String dealedSheetInfo() {
		bizFields = sheetQueryService.workSheetInfo(itSysName, baseId, baseSchema, tplId);
		baseName = XMLParamParser.getContent(bizFields, "BaseName");
		baseStatus = XMLParamParser.getContent(bizFields, "BaseStatus");
		baseSn = XMLParamParser.getContent(bizFields, "BaseSn");
		dpLogFields = sheetQueryService.dealInfo(itSysName, baseId, baseSchema, tplId);
		String dealed = Init.getDealed(baseSchema);
		if (StringUtils.isNotBlank(dealed)) {
			return findForward(dealed);
		} else {
			return findForward("dealedSheetInfo");
		}
	}
	
	public String getEditFieldsByAction() {
		String dealActorType = "1000";//处理人字段类型
		String contextPath = getRequest().getContextPath();
		String xmlStr = sheetQueryService.getEditFieldsByAction(itSysName, baseId, baseSchema, tplId, taskId, actionStr);
		StringBuilder su = new StringBuilder();
		StringBuilder validate = new StringBuilder();
		StringBuilder json = new StringBuilder();
		try {
			actionStr = URLDecoder.decode(actionStr, "UTF-8"); 
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String[] actionAry = actionStr.split("@");//800010001@@T1完成处理@true@true  @AUDIT@提交审批@true@false
		String actionType = "NEXT";
		boolean needActor = Boolean.parseBoolean(actionAry[3]);
		boolean isSingle = Boolean.parseBoolean(actionAry[4]);
		String realActtorActionType = "";//处理固定下一步派发自由子流程的情况，调用动作"NEXT"，处理人串为"ASSIGN"
		String actorDesc = actionAry[5];
		try {
			su.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			XmlInfo xml = XMLParamParser.convert(xmlStr);
			if (xml != null) {
				List<List<FieldInfo>> listRecords = xml.getListRecords();
				if (CollectionUtils.isNotEmpty(listRecords)) {
					List<FieldInfo> fields = listRecords.get(0);
					if (CollectionUtils.isNotEmpty(fields)) {
						for (int i = 0; i < fields.size(); i++) {
							FieldInfo field = fields.get(i);
							String cname = field.getCname();
							String ename = field.getEname();
							String ctx = field.getContent();
							String type = field.getType();
							String typeValue = field.getTypeValue();
							String id = field.getId();
							boolean required = field.isRequired();
							boolean hidden = field.isHidden();
							String defaultValue = field.getDefaultValue();
							String singleFlag = field.getSingleFlag();
							String dict = field.getDict();
							String requireChar = "";
							String infoDesc = field.getInfoDesc();
							
							if (!dealActorType.equals(type) && !hidden) {
								if (required) {
									validate.append("bizFields[" + i + "];");
									requireChar = "*";
								}
								su.append("<tr>");
								su.append("<td class=\"worksheetFieldLabel\" style=\"padding-left:7px;\">" + cname + requireChar + "：</td>");
								su.append("<input type=\"hidden\" name=\"bizFields["+i+"].type\"  value=\""+type+"\"/>");
								su.append("<input type=\"hidden\" name=\"bizFields["+i+"].id\"  value=\""+id+"\"/>");
								su.append("<input type=\"hidden\" name=\"bizFields["+i+"].ename\"  value=\""+ename+"\"/>");
								su.append("<input type=\"hidden\" id=\"bizFields["+i+"].cname\"  value=\""+cname+"\"/>");
							}
							if (hidden) {
								//隐藏传值字段
								su.append("<input type=\"hidden\" name=\"bizFields["+i+"].type\"  value=\""+type+"\"/>");
								su.append("<input type=\"hidden\" name=\"bizFields["+i+"].id\"  value=\""+id+"\"/>");
								su.append("<input type=\"hidden\" name=\"bizFields["+i+"].ename\"  value=\""+ename+"\"/>");
								su.append("<input type=\"hidden\" name=\"bizFields["+i+"].cname\" id=\"bizFields["+i+"].cname\"  value=\""+cname+"\"/>");
								su.append("<input type=\"hidden\" name=\"bizFields["+i+"].content\" id=\"bizFields["+i+"].content\"  value=\""+defaultValue+"\"/>");
							} else if ("6".equals(type) || StringUtils.isNotBlank(typeValue)) {//选择
								su.append("<td class=\"worksheetFieldValue\">");
								su.append("		<select class=\"selectEnable\" name=\"bizFields["+i+"].content\" id=\"bizFields["+i+"].content\">");
								su.append("			<option value=''>请选择</option>");
								if (StringUtils.isNotBlank(typeValue)) {
									String[] arys = typeValue.split(";");
									if (!ArrayUtils.isEmpty(arys)) {
										for (int j = 0; j < arys.length; j++) {
											String[] split = arys[j].split("=");
											if (StringUtils.isNotBlank(defaultValue) && defaultValue.equals(split[0])) {
												su.append("<option value='"+split[0]+"' selected>"+split[1]+"</option>");
											} else {
												su.append("<option value='"+split[0]+"'>"+split[1]+"</option>");
											}
										}
									}
								}
								su.append("		</select>");
								su.append("</td>");
								su.append("</tr>");
							} else if(StringUtils.isNotBlank(dict)) {//级联下拉（字典数据）
								String[] dictArys = dict.split("#");
								String[] dicts = dictArys[1].split(",");
								
								XStream xstream = new XStream(new JettisonMappedXmlDriver());
						        xstream.setMode(XStream.NO_REFERENCES);
						        xstream.alias("dict", DictInfo.class);
						        StringBuilder lv1 = new StringBuilder(); 
						        List<DictInfo> dictInfos = xml.getDicts();
						        if (CollectionUtils.isNotEmpty(dictInfos)) {
									for (int j = 0; j < dictInfos.size(); j++) {
										DictInfo di = dictInfos.get(j);
										if (di.getId().equals(dict)) {
											String jsonStr = xstream.toXML(di);
											System.out.println(jsonStr);
											json.append("var "+ename+"=" +jsonStr+"; dictNames.push('"+ename+"');dictObjs.push("+ename+");");
											List<DictInfo> dictLv1 = di.getDicts();
											if (CollectionUtils.isNotEmpty(dictLv1)) {
												for (int k = 0; k < dictLv1.size(); k++) {
													DictInfo dictInfo = dictLv1.get(k);
													lv1.append("<option value=\""+dictInfo.getKey()+"\">"+dictInfo.getValue()+"</option>");
												}
											}
											break;
										}
									}
								}
								
								su.append("<td class=\"worksheetFieldValue\"><nobr>");
								su.append("		<textarea class=\"worksheetFieldValueInputOnelineEnableSelect\" name=\"bizFields["+i+"].content\" id=\"bizFields["+i+"].content\" readonly=\"true\" onclick=\"showOrhiddenDiv('selectDiv_" + ename + "')\">");
								su.append(		defaultValue);
								su.append(		"</textarea>");
								su.append(		"<input type=\"button\" value=\"\" style=\"background-image:url('"+contextPath+"/workflow/sheet/images/select.png');width:17px;height:18px;padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;border-width:0px\" onclick=\"showOrhiddenDiv('selectDiv_" + ename + "')\" />");
								su.append(		"</nobr><br/>");
								su.append(		"<div id=\"selectDiv_" + ename + "\" style=\"display:none;background-color:#FFFFFF;color:#000000;\" >");
								su.append("		<div id='treeData' style='background-color:#CCCCCC;color:#000000;padding:2px 2px 2px 2px;'>");
								su.append("		请选择：");
								su.append("		</div>");
								String dictStrIds = "";//拼字典值用到的所有级别的select的id
								if (!ArrayUtils.isEmpty(dicts)) {
									for (int j = 0; j < dicts.length; j++) {
										dictStrIds += ename + "_" + dictArys[0] + j;
										if (j != (dicts.length - 1)) {
											dictStrIds += ",";
										}
										String clearIds = "";//当前级别的子级的select的id
										for(int k = (dicts.length - 1); k > j;k--) {
											clearIds += ename + "_" + dictArys[0] + k;
											if ((k-1) != j) {
												clearIds += ",";
											}
										}
										su.append("&nbsp;&nbsp;第"+(j+1)+"级分类:" + "<select id=\""+ ename + "_" + dictArys[0] + j+"\" class=\"selectEnable\" onchange=\"changeSelect('"+ename+"','"+ ename + "_" + dictArys[0] + j+"', '"+ ename + "_" + dictArys[0] + (j +1) +"', '"+clearIds+"')\"><option value=\"\">请选择</option>"+(j==0?lv1.toString():"")+"</select><br>");
									}
								}
								su.append("		<div id='treeData' style='background-color:#CCCCCC;color:#000000;'>");
								su.append(		"	<input name='' type='button' value='确定'onclick=\"joinDictStr('bizFields["+i+"].content', '"+dictStrIds+"'); showOrhiddenDiv('selectDiv_" + ename + "')\">");
								su.append(		"	<input name='' type='button' value='取消' onclick=\"document.getElementById('bizFields["+i+"].content').value='';showOrhiddenDiv('selectDiv_" + ename + "')\">");
								su.append(		"</div>");
								su.append("</td>");
								su.append("</tr>");
							} else if ("7".equals(type)) {//时间
								if (StringUtils.isNotBlank(defaultValue)) {
									defaultValue = TimeUtils.formatIntToDateString(Long.parseLong(defaultValue));
								}
								su.append("<td class=\"worksheetFieldValue\"><nobr><textarea class=\"worksheetFieldValueInputOnelineEnableSelect\" onfocus=\"\"  name=\"bizFields["+i+"].content\" id=\"bizFields["+i+"].content\" readonly=\"true\" onclick=\"showOrHidCalendar('bizFields["+i+"].content_time_div','bizFields["+i+"].content')\">"+defaultValue+"</textarea>");
								su.append(		"<input type=\"button\" value=\"\" style=\"background-image:url('"+contextPath+"/workflow/sheet/images/select.png');width:17px;height:18px;padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;border-width:0px\" onclick=\"showOrHidCalendar('bizFields["+i+"].content_time_div','bizFields["+i+"].content')\"/>");
								su.append(		"</nobr><br/>");
								su.append(		"<div id='bizFields["+i+"].content_time_div'></div>");
								su.append("</td>");
								su.append("</tr>");
							} else if (dealActorType.equals(type)) {//固定流程处理人字段
								needActor = true;
								actorDesc = cname;
								isSingle = "0".equalsIgnoreCase(singleFlag);
								
								//手机客户端客户化：固定派到一个内部自由环节
								if(infoDesc.contains("action=assign")){
									defaultValue = "ASSIGN";
								}
								
								realActtorActionType = defaultValue;
							} else {//普通文本
								su.append("<td class=\"worksheetFieldValue\"><textarea class=\"worksheetFieldValueInputOnelineEnable\" name=\"bizFields["+i+"].content\" id=\"bizFields["+i+"].content\">"+defaultValue+"</textarea></td>");
								su.append("</tr>");
							}
						}
					}
				}
				if (needActor) {
					validate.append("dealActorStr;");
					json.append("isRadioPara='" + (isSingle ? 0 : 1) + "';");//1多选，0单选
					json.append("rearchUserOrDep();");//构建派发树的js
					if (StringUtils.isNotBlank(actionAry[1])) {
						if (StringUtils.isNotBlank(realActtorActionType)) {
							actionType = realActtorActionType;
						} else {
							actionType = actionAry[1]; 
						}
					}
					
					su.append("<tr>");
					su.append("<td class=\"worksheetFieldLabel\" style=\"padding-left:7px;\">"+actorDesc+"*：</td>");
					su.append("<td class=\"worksheetFieldValue\" style=\"vertical-align:top\"><nobr>");
					su.append("<input type=\"hidden\" id=\"dealActorStr.content\" name=\"dealActorStr\" />");
					su.append("<input type=\"hidden\" id=\"dealActorStr.cname\" value='"+actorDesc+"'/>");
					su.append("		<textarea class=\"worksheetFieldValueInputTwolineSelect\" id=\"dealActorStr.text\" readonly=\"true\" onclick=\"showOrhiddenDiv('selectDiv_dealActorStr')\" />");
					su.append(		"</textarea>");
					su.append(		"<input type=\"button\" value=\"\" style=\"background-image:url('"+contextPath+"/workflow/sheet/images/select.png');width:17px;height:18px;padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;border-width:0px\" onclick=\"showOrhiddenDiv('selectDiv_dealActorStr')\" />");
					su.append(		"</nobr><br/>");
					su.append(		"<div id='selectDiv_dealActorStr' style='display:none;background-color:#CCCCCC;color:#000000;width:100%;' >");
					su.append("			<div id='treeData' style='background-color:#CCCCCC;padding:2px 2px 2px 2px;'>请选择：</div>");
					su.append("			<div>");
					su.append("				<nobr>");
					su.append("			  		<input type=\"text\" name=\"reachUserOrDep\" id=\"researchTxt\" style=\"width:80%\" value=\"快速查找......\" onfocus=\"if (value =='快速查找......'){value =''}\" onblur=\"if (value ==''){value='快速查找......'}\">");
					su.append("			  		<input type=\"button\" onclick=\"rearchUserOrDep();\" value=\"\" style=\"background-image:url('"+contextPath+"/workflow/sheet/images/searchsml.png');width:17px;height:18px;padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;border-width:0px\">");
					su.append("				</nobr>");
					su.append("			</div>");
					su.append("			<div id='tree_loading' style='padding:1px 1px 1px 1px;'></div>");
					su.append("			<div id='treeboxbox_tree' style='clear:both;background:#ffffff;border:1px #d2e5fe solid;border-top:none;'></div>");
					su.append("			<div id='treeData' style='background-color:#EEEEEE;'>");
					su.append("					<div style='padding:1px 1px 1px 1px;'>已经选择：</div>");
					su.append("					<div id='inertData' style='padding:0px 4px 4px 4px;'></div>");
					su.append("			</div>");
					su.append("			<input name='' type='button' value='确定'onclick=\"addDealActor('"+actionType+"');showOrhiddenDiv('selectDiv_dealActorStr');\">");
					su.append("			<input name='' type='button' value='取消' onclick=\"clearActor();rearchUserOrDep();showOrhiddenDiv('selectDiv_dealActorStr');\">");
					su.append("			<br/>");
					su.append("		</div>");
					su.append("</td>");
					su.append("</tr>");
				}
				su.append("<input type=\"hidden\" id=\"valFields\"  value=\""+validate.toString()+"\"/>");
			}
			su.append("</table>");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		renderXML(su.toString() + "__AA__XX__" + json.toString());
		return null;
	}
	
//	public void recu(StringBuilder su, DictInfo dict, int t) {
//		su.append("<li>");
//		if (dict != null) {
//			List<DictInfo> dicts = dict.getDicts();
//			String value = dict.getValue();
//			String key = dict.getKey();
//			if (CollectionUtils.isNotEmpty(dicts)) {
//				su.append("<a href=\"javascript:\">"+value+"</a>");
//				su.append("<ul>");
//				for (int i = 0; i < dicts.size(); i++) {
//					DictInfo dictInfo = dicts.get(i);
//					recu(su, dictInfo, t);
//				}
//				su.append("</ul>");
//			} else {
//				su.append("<a href=\"javascript:\" name=\""+key+"\" onclick=\"document.getElementById('dropTitle').innerHTML=this.name;document.getElementById('bizFields["+t+"].content').value=this.name;\">"+value+"</a>");
//			}
//			su.append("</li>");
//		}
//	}
	
	public SheetQueryService getSheetQueryService() {
		return sheetQueryService;
	}
	
	public void setSheetQueryService(SheetQueryService sheetQueryService) {
		this.sheetQueryService = sheetQueryService;
	}

	public String getItSysName() {
		return itSysName;
	}

	public void setItSysName(String itSysName) {
		this.itSysName = itSysName;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}

	public List<FieldInfo> getBizFields() {
		return bizFields;
	}

	public void setBizFields(List<FieldInfo> bizFields) {
		this.bizFields = bizFields;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getBaseSn() {
		return baseSn;
	}

	public void setBaseSn(String baseSn) {
		this.baseSn = baseSn;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<ActionModel> getActions() {
		return actions;
	}

	public void setActions(List<ActionModel> actions) {
		this.actions = actions;
	}

	public String getBaseStatus() {
		return baseStatus;
	}

	public void setBaseStatus(String baseStatus) {
		this.baseStatus = baseStatus;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getActionStr() {
		return actionStr;
	}

	public void setActionStr(String actionStr) {
		this.actionStr = actionStr;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getTaskName() {
		if (StringUtils.isNotBlank(taskName)) {
			try {
				taskName = URLDecoder.decode(taskName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<FieldInfo> getDpLogFields() {
		return dpLogFields;
	}

	public void setDpLogFields(List<FieldInfo> dpLogFields) {
		this.dpLogFields = dpLogFields;
	}

	public String getDealActorStr() {
		return dealActorStr;
	}

	public void setDealActorStr(String dealActorStr) {
		this.dealActorStr = dealActorStr;
	}

	public JspExtendFunction getJspExtendFunction()
	{
		return jspExtendFunction;
	}

	public void setJspExtendFunction(JspExtendFunction jspExtendFunction)
	{
		this.jspExtendFunction = jspExtendFunction;
	}
	
}

