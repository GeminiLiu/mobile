package com.ultrapower.mobile.common.utils;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.mobile.model.WfType;
import com.ultrapower.mobile.model.WorkSheet;
import com.ultrapower.mobile.model.xml.ActionModel;
import com.ultrapower.mobile.model.xml.DictInfo;
import com.ultrapower.mobile.model.xml.FieldInfo;
import com.ultrapower.mobile.model.xml.XmlInfo;

public class XMLParamParser {

	private List<DictInfo> dicts = new ArrayList<DictInfo>();
	private Document doc = null;
	private Element root = null;
	private Element listElement = null;
	private Element actionsElement = null;
	private Element dictsElement = null;
	private static Logger log = LoggerFactory.getLogger(XMLParamParser.class);
	
	private XMLParamParser() {
		doc = DocumentHelper.createDocument();
		root = doc.addElement("info");
		listElement = root.addElement("list");
		actionsElement = root.addElement("actions");
		dictsElement = root.addElement("dicts");
	}
	
	private XMLParamParser(Document doc, Element root, Element listElement, Element actionsElement, Element dictsElement) {
		this.doc = doc;
		this.root = root;
		this.listElement = listElement;
		this.actionsElement = actionsElement;
		this.dictsElement = dictsElement;
	}
	
	public static XMLParamParser createInstance() {
		return new XMLParamParser();
	} 
	
	public static XMLParamParser parseXML(String xml) {
		XMLParamParser parser = null;
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			Element listElement = root.element("list");
			Element actionsElement = root.element("actions");
			Element dictsElement = root.element("dicts");
			parser =  new XMLParamParser(doc, root, listElement, actionsElement, dictsElement);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return parser;
	}
	
	/**
	 * 添加单个属性
	 * @param field
	 */
	public void addAttr(FieldInfo fieldInfo) {
		if (fieldInfo != null) {
			addField(root, fieldInfo);
		}
	}
	
	public void addAction(ActionModel ac) {
		addAction(actionsElement, ac);
	}
	
	public void addAction(String actionId, String actionName, String actionType) {
		addAction(new ActionModel(actionId, actionName, actionType));
	}
	
	public void addAction(String actionId, String actionName, String actionType, int order, boolean needActor, boolean isSingle) {
		addAction(new ActionModel(actionId, actionName, actionType, order, needActor, isSingle));
	}
	
	public void addAction(String actionId, String actionName, String actionType, int order, boolean needActor, boolean isSingle, String actorDesc) {
		addAction(new ActionModel(actionId, actionName, actionType, order, needActor, isSingle, actorDesc));
	}
	
	public void addAttr(String ename, String cname, int content) {
		this.addAttr(new FieldInfo(ename, cname, content));
	}
	
	public void addAttr(String ename, String cname, String content) {
		this.addAttr(new FieldInfo(ename, cname, content));
	}
	
	public void addAttr(String ename, String cname, String content, String id, String defVal) {
		this.addAttr(new FieldInfo(ename, cname, content, id, defVal));
	}
	
	public void addAttr(String ename, String cname, String content, String id, String defVal, boolean isHidden, boolean isRequired, String fieldType, int length, String meta,String infoDesc) {
		this.addAttr(new FieldInfo(ename, cname, content, id, defVal, isHidden, isRequired, fieldType, length, meta,infoDesc));
	}
	
	public DictInfo addDict(String id, String key, String value) {
		DictInfo dict = new DictInfo(id, key, value);
		dicts.add(dict);
		return dict;
	}
	
	/**
	 * 添加列表属性
	 * @param fields
	 */
	public void addListAttr(List<FieldInfo> fields) {
		if (CollectionUtils.isNotEmpty(fields)) {
			Element row = listElement.addElement("record");
			for (int i = 0; i < fields.size(); i++) {
				FieldInfo fieldInfo = fields.get(i);
				addField(row, fieldInfo);
			}
		}
	} 
	
	/**
	 * 删除属性
	 * @param key
	 */
	public void removeField(String key) {
		if (root != null) {
			List elements = root.elements("field");
			for (int i = 0; i < elements.size(); i++) {
				Element ele = (Element) elements.get(i);
				if (ele != null) {
					String txt = ele.element("ename").getText();
					if (StringUtils.isNotBlank(txt) && txt.equals(key)) {
						root.remove(ele);
						break;
					}
				}
			}
		}
		
	}
	
	/**
	 * 返回xml内容
	 * @return
	 */
	public String getXmlContent() {
		if (CollectionUtils.isNotEmpty(dicts)) {
			for (int i = 0; i < dicts.size(); i++) {
				DictInfo di = dicts.get(i);
				String id = di.getId();
				List<DictInfo> dis = di.getDicts();
				Element dict = dictsElement.addElement("dict");
				dict.addAttribute("id", id);
				addDictEle(dict, dis);
			}
		}
		if (doc != null) {
			return doc.asXML();
		}
		return null;
	}
	
	private void addDictEle(Element parent, List<DictInfo> dis) {
		if (parent != null && CollectionUtils.isNotEmpty(dis)) {
			for (int j = 0; j < dis.size(); j++) {
				DictInfo di = dis.get(j);
				List<DictInfo> disSub = di.getDicts();
				Element dataEle = parent.addElement("data");
				dataEle.addAttribute("value", di.getValue());
				dataEle.addAttribute("key", di.getKey());
				addDictEle(dataEle, disSub);
			}
		}
	}
	
	private void addField(Element parent, FieldInfo field) {
		if (field != null && parent != null) {
			String fieldChName = field.getCname();
			String fieldEnName = field.getEname();
			String fieldContent = field.getContent();
			String id = field.getId();
			String defaultValue = field.getDefaultValue();
			boolean hidden = field.isHidden();
			boolean required = field.isRequired();
			String type = field.getType();
			String typeValue = field.getTypeValue();
			String dict = field.getDict();
			int length = field.getLength();
			String meta = field.getMeta();
			String singleFlag = field.getSingleFlag();
			String infoDesc = field.getInfoDesc();
			Element fieldEle = parent.addElement("field");
			fieldEle.addElement("cname").addText(fieldChName);
			fieldEle.addElement("ename").addText(fieldEnName);
			fieldEle.addElement("content").addText(fieldContent);
			fieldEle.addElement("id").addText(id);
			fieldEle.addElement("defaultValue").addText(defaultValue);
			fieldEle.addElement("singleFlag").addText(singleFlag);
			fieldEle.addElement("isHidden").addText(hidden + "");
			fieldEle.addElement("isRequired").addText(required + "");
			fieldEle.addElement("type").addText(type);
			fieldEle.addElement("typeValue").addText(typeValue);
			fieldEle.addElement("length").addText(length + "");
			fieldEle.addElement("dict").addText(dict);
			fieldEle.addElement("meta").addText(meta);
			fieldEle.addElement("infoDesc").addText(infoDesc);
		}
	}
	
	private void addAction(Element parent, ActionModel acModel) {
		if (acModel != null && parent != null) {
			String actionId = acModel.getActionId();
			String actionName = acModel.getActionName();
			String actionType = acModel.getActionType();
			String actorDesc = acModel.getActorDesc();
			int order = acModel.getOrder();
			boolean needActor = acModel.isNeedActor();
			boolean singleFlag = acModel.isSingleFlag();
			Element fieldEle = parent.addElement("action");
			fieldEle.addElement("actionId").addText(actionId);
			fieldEle.addElement("actionName").addText(actionName);
			fieldEle.addElement("actionType").addText(actionType);
			fieldEle.addElement("actorDesc").addText(actorDesc);
			fieldEle.addElement("order").addText(order+"");
			fieldEle.addElement("needActor").addText(needActor + "");
			fieldEle.addElement("singleFlag").addText(singleFlag + "");
		}
	}
	
	
	public static XmlInfo convert(String xml) throws DocumentException {
		XmlInfo xmlInfo = null;
		if (StringUtils.isNotBlank(xml)) {
			Document doc = DocumentHelper.parseText(xml);
			xmlInfo = new XmlInfo();
			Element rootElement = doc.getRootElement();
			List elements = rootElement.elements();
			for (int i = 0; i < elements.size(); i++) {
				Element ele = (Element) elements.get(i);
				String name = ele.getName();
				if ("field".equals(name)) {
					xmlInfo.getFields().add(readFieldInfo(ele));
				}
				
				if ("list".equals(name)) {
					List recs = ele.elements();
					for (int j = 0; j <recs.size(); j++) {
						List<FieldInfo> recList = new ArrayList<FieldInfo>();
						Element recEle = (Element) recs.get(j);
						if (recEle != null) {
							List fields = recEle.elements();
							for (int k = 0; k < fields.size(); k++) {
								Element fie = (Element) fields.get(k);
								recList.add(readFieldInfo(fie));
							}
						}
						xmlInfo.getListRecords().add(recList);
					}
				}
				
				if ("actions".equals(name)) {
					List acs = ele.elements();
					for (int j = 0; j <acs.size(); j++) {
						Element acEle = (Element) acs.get(j);
						if (acEle != null) {
							xmlInfo.getActions().add(readActionModel(acEle));
						}
					}
				}
				
				if ("dicts".equals(name)) {
					List dis = ele.elements();
					for (int j = 0; j <dis.size(); j++) {
						Element diEle = (Element) dis.get(j);
						if (diEle != null) {
							xmlInfo.getDicts().add(readDictModel(diEle));
						}
					}
				}
				
				
			}
		}
		return xmlInfo;
	}
	
	private static FieldInfo readFieldInfo(Element ele) {
		FieldInfo field = null;
		if (ele != null) {
			field = new FieldInfo();
			Element cnEle = ele.element("cname");
			if (cnEle != null) {
				field.setCname(cnEle.getText());
			}
			cnEle = ele.element("ename");
			if (cnEle != null) {
				field.setEname(cnEle.getText());
			}
			cnEle = ele.element("content");
			if (cnEle != null) {
				field.setContent(cnEle.getText());
			}
			cnEle = ele.element("id");
			if (cnEle != null) {
				field.setId(cnEle.getText());
			}
			cnEle = ele.element("defaultValue");
			if (cnEle != null) {
				field.setDefaultValue(cnEle.getText());
			}
			cnEle = ele.element("singleFlag");
			if (cnEle != null) {
				field.setSingleFlag(cnEle.getText());
			}
			cnEle = ele.element("type");
			if (cnEle != null) {
				field.setType(cnEle.getText());
			}
			cnEle = ele.element("typeValue");
			if (cnEle != null) {
				field.setTypeValue(cnEle.getText());
			}
			cnEle = ele.element("dict");
			if (cnEle != null) {
				field.setDict(cnEle.getText());
			}
			cnEle = ele.element("meta");
			if (cnEle != null) {
				field.setMeta(cnEle.getText());
			}
			cnEle = ele.element("isHidden");
			if (cnEle != null) {
				field.setHidden(Boolean.parseBoolean(cnEle.getText()));
			}
			cnEle = ele.element("isRequired");
			if (cnEle != null) {
				field.setRequired(Boolean.parseBoolean(cnEle.getText()));
			}
			cnEle = ele.element("length");
			if (cnEle != null) {
				field.setLength(NumberUtils.formatToInt(cnEle.getText()));
			}
			cnEle = ele.element("infoDesc");
			if (cnEle != null) {
				field.setInfoDesc(cnEle.getText());
			}
		}
		return field;
	}
	
	private static ActionModel readActionModel(Element ele) {
		ActionModel ac = null;
		if (ele != null) {
			ac = new ActionModel();
			Element cnEle = ele.element("actionId");
			if (cnEle != null) {
				ac.setActionId(cnEle.getText());
			}
			cnEle = ele.element("actionType");
			if (cnEle != null) {
				ac.setActionType(cnEle.getText());
			}
			cnEle = ele.element("actionName");
			if (cnEle != null) {
				ac.setActionName(cnEle.getText());
			}
			cnEle = ele.element("actorDesc");
			if (cnEle != null) {
				ac.setActorDesc(cnEle.getText());
			}
			cnEle = ele.element("needActor");
			if (cnEle != null) {
				ac.setNeedActor(BooleanUtils.toBoolean(cnEle.getText()));
			}
			cnEle = ele.element("order");
			if (cnEle != null) {
				ac.setOrder(NumberUtils.formatToInt(cnEle.getText()));
			}
			cnEle = ele.element("singleFlag");
			if (cnEle != null) {
				ac.setSingleFlag(BooleanUtils.toBoolean(cnEle.getText()));
			}
		}
		return ac;
	}
	
	private static DictInfo readDictModel(Element ele) {
		DictInfo di = null;
		if (ele != null) {
			di = new DictInfo();
			String id = ele.attributeValue("id");
			String key = ele.attributeValue("key");
			String value = ele.attributeValue("value");
			di.setId(id);
			di.setKey(key);
			di.setValue(value);
			List list = ele.elements();
			if (CollectionUtils.isNotEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					Element de = (Element) list.get(i);
					if (de != null) {
						readDictData(de, di);
					}
				}
			}
		}
		return di;
	}
	
	private static DictInfo readDictData(Element ele, DictInfo dictInfo) {
		DictInfo di = null;
		if (ele != null) {
			di = new DictInfo();
			String id = ele.attributeValue("id");
			String key = ele.attributeValue("key");
			String value = ele.attributeValue("value");
			di.setId(id);
			di.setKey(key);
			di.setValue(value);
			dictInfo.getDicts().add(di);
			List eleList = ele.elements("data");
			if (CollectionUtils.isNotEmpty(eleList)) {
				for (int i = 0; i < eleList.size(); i++) {
					Element cnEle = (Element) eleList.get(i);
					if (cnEle != null) {
						readDictData(cnEle, di);
					}
				}
			}
		}
		return di;
	}
	
	public static List<WorkSheet> conToWorkSheet(XmlInfo xml) {
		List<WorkSheet> workSheetList = null;
		if (xml != null) {
			List<List<FieldInfo>> listRecords = xml.getListRecords();
			if (CollectionUtils.isNotEmpty(listRecords)) {
				workSheetList = new ArrayList<WorkSheet>();
				for (int i = 0; i < listRecords.size(); i++) {
					WorkSheet workSheet = new WorkSheet();
					List<FieldInfo> list = listRecords.get(i);
					if (CollectionUtils.isNotEmpty(list)) {
						for (int j = 0; j < list.size(); j++) {
							FieldInfo fie = list.get(j);
							String fieldEnName = fie.getEname();
							String fieldContent = fie.getContent();
							if (StringUtils.isNotBlank(fieldEnName)) {
								if (fieldEnName.equals("BaseSN")) {
									workSheet.setBaseSn(fieldContent);
								} else if (fieldEnName.equals("BaseType")) {
									workSheet.setBaseType(fieldContent);
								} else if (fieldEnName.equals("BaseStatus")) {
									workSheet.setBaseStatus(fieldContent);
								} else if (fieldEnName.equals("Title")) {
									workSheet.setTitle(fieldContent);
								} else if (fieldEnName.equals("Creater")) {
									workSheet.setCreator(fieldContent);
								} else if (fieldEnName.equals("Assigner")) {
									workSheet.setAssigner(fieldContent);
								} else if (fieldEnName.equals("CreateTime")) {
									workSheet.setCreateTime(NumberUtils.formatToInt(fieldContent));
								} else if (fieldEnName.equals("AssigneTime")) {
									workSheet.setAssigneTime(NumberUtils.formatToInt(fieldContent));
								} else if (fieldEnName.equals("DealTime")) {
									workSheet.setDealTime(NumberUtils.formatToInt(fieldContent));
								} else if (fieldEnName.equals("AcceptTime")) {
									workSheet.setAcceptTime(NumberUtils.formatToInt(fieldContent));
								} else if (fieldEnName.equals("DefName")) {
									workSheet.setTplId(fieldContent);
								} else if (fieldEnName.equals("BaseId")) {
									workSheet.setBaseId(fieldContent);
								} else if (fieldEnName.equals("BaseSchema")) {
									workSheet.setBaseSchema(fieldContent);
								} else if (fieldEnName.equals("TaskId")) {
									workSheet.setTaskId(fieldContent);
								} else if (fieldEnName.equals("TaskName")) {
									workSheet.setTaskName(fieldContent);
								} 
							}
						}
					}
					workSheetList.add(workSheet);
				}
			}
		}
		return workSheetList;
	}
	
	public static String getContent(List<FieldInfo> fields, String key) {
		String res = "";
		if (CollectionUtils.isNotEmpty(fields)) {
			for (int i = 0; i < fields.size(); i++) {
				FieldInfo fie = fields.get(i);
				if (fie.getEname().equalsIgnoreCase(key)) {
					res = fie.getContent();
					break;
				}
			}
		}
		return res;
	}
	
	public static List<WfType> convertWfTypes(List<FieldInfo> fields) {
		List<WfType> types = new ArrayList<WfType>();
		if (CollectionUtils.isNotEmpty(fields)) {
			for (int i = 0; i < fields.size(); i++) {
				FieldInfo f = fields.get(i);
				String cname = f.getCname();
				String ename = f.getEname();
				types.add(new WfType(ename, cname));
			}
		}
		return types;
	}
}
