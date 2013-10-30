package daiwei.mobile.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import daiwei.mobile.util.TempletUtil.TMPModel.ActionButton;
import daiwei.mobile.util.TempletUtil.TMPModel.BaseField;
import daiwei.mobile.util.TempletUtil.TMPModel.Dic;
import daiwei.mobile.util.TempletUtil.TMPModel.Field;
import daiwei.mobile.util.TempletUtil.TMPModel.FieldGroup;
import daiwei.mobile.util.TempletUtil.TMPModel.TemplateRT;
import daiwei.mobile.util.TempletUtil.TMPModel.Type;
import daiwei.mobile.util.TempletUtil.TMPModel.XJBaseField;
import daiwei.mobile.util.TempletUtil.TMPModel.XJField;
import daiwei.mobile.util.TempletUtil.TMPModel.XJFieldGroup;
import daiwei.mobile.util.TempletUtil.TMPModel.XJLineField;
import daiwei.mobile.util.TempletUtil.TMPModelXJ.Content;
import daiwei.mobile.util.TempletUtil.TMPModelXJ.RecordInfo;
import daiwei.mobile.util.TempletUtil.TMPModelXJ.XjItem;

/**
 * XML DOM 解析
 * 
 * @author admin
 * 
 */

/**
 * 
 * @author qicaihua
 * @time 2013-3-28 下午5:33:53
 */
public class XMLUtil {
	public Document doc;
	public static String type;
	public Element root;
	private Map<String, String> map, mapNum;// mapNum存放工单各种类别的消息个数

	public XMLUtil(String idata) {
		try {
			/** 创建解析器 */
			doc = DocumentHelper.parseText(idata.toString());
			root = doc.getRootElement();

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取工单消息个数
	 * 
	 * @return
	 */
	public Map<String, String> getBaseCount() {
		mapNum = new HashMap<String, String>();
		Element ele = root.element("baseInfo");
		if (ele != null) {
			Iterator<Element> its = ele.element("baseCount")
					.elements("category").iterator();
			while (its.hasNext()) {
				Element rowInfo = its.next();
				mapNum.put(rowInfo.attributeValue("type"), rowInfo.getText());
			}
		}
		return mapNum;

	}

	/**
	 * 获取工单list列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getList() {
		List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
		if(root != null){
			Element ele = root.element("recordInfo");
			System.out.println("ele==========" + ele);
			if (ele != null) {
				Iterator<Element> its = root.elements("recordInfo").iterator();
				while (its.hasNext()) {
					Element rowInfo = its.next();
					Iterator<Element> rowIts = rowInfo.elements("field").iterator();
					map = new HashMap<String, String>();
					while (rowIts.hasNext()) {
						Element el = rowIts.next();
						map.put(el.attributeValue("code"), el.getText());
					}
					rsList.add(map);
				}
			}
		}
		return rsList;
	}
	/**
	 * 获取显示字段信息  带字段是否必填的属性
	 * @return
	 */
	public List<FieldVO> getFieldInfoList() {
		List<FieldVO> filedInfoList = new ArrayList<FieldVO>();
		Element ele = root.element("recordInfo");
		System.out.println("ele==========" + ele);
		if (ele != null) {
			Iterator<Element> its = root.elements("recordInfo").iterator();
			while (its.hasNext()) {
				Element rowInfo = its.next();
				Iterator<Element> rowIts = rowInfo.elements("field").iterator();
				Iterator<Element> rqRowIts = rowInfo.elements("requiredField").iterator();
				FieldVO fieldVO = new FieldVO();
				while (rowIts.hasNext()) {
					Element el = rowIts.next();
					Element rqel = rqRowIts.next();
					
					fieldVO.code = el.attributeValue("code");
					fieldVO.text = el.getText();
					fieldVO.isRequired = rqel.getText();
					filedInfoList.add(fieldVO);
				}
			}
		}
		return filedInfoList;
	}
	/**
	 * 字段的code及对应名称和是否必填属性
	 * @author Administrator
	 *
	 */
	public class FieldVO{
		String code = "";//字段code
		String text = "";//字段中文名称
		String isRequired= "0";//是否必填 0：非必填
	}
	/**
	 * 获取线路巡检list列表
	 * 
	 * @return
	 */
	public List<Map<String, String>> getXJLineList() {
		Iterator<Element> its = root.elements("recordInfo").iterator();
		List<Map<String, String>> xjList = new ArrayList<Map<String, String>>();
		while (its.hasNext()) {
			Map<String, String> xlMap = new HashMap<String, String>();
			Element el = its.next();
			xlMap.put("planNo", getElementNodeValue(el, "planNo"));// 计划编号
			xlMap.put("planDate", getElementNodeValue(el, "planDate"));// 计划日期
			xlMap.put("taskId", getElementNodeValue(el, "taskId"));// 完成率
			xlMap.put("planName", getElementNodeValue(el, "planName"));// 计划名称
			xjList.add(xlMap);
		}
		return xjList;
	}

	/**
	 * 获取巡检列表
	 * 
	 * @return
	 */
	public List<Map<String, String>> getXJList() {
		Iterator<Element> its = root.elements("recordInfo").iterator();
		List<Map<String, String>> xjList = new ArrayList<Map<String, String>>();
		while (its.hasNext()) {
			Map<String, String> xlMap = new HashMap<String, String>();
			Element el = its.next();
			xlMap.put("taskName", getElementNodeValue(el, "taskName"));
			xlMap.put("taskId", getElementNodeValue(el, "taskId"));
			xlMap.put("distance", getElementNodeValue(el, "eDistance"));
			xjList.add(xlMap);
		}
		System.out.println("xj" + xjList);
		return xjList;
	}

	/**
	 * 获取list列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getListDu() {
		List<Map<String, String>> rsLista = new ArrayList<Map<String, String>>();
		Iterator<Element> its = root.elements("recordInfo").iterator();
		while (its.hasNext()) {
			Element rowInfo = its.next();
			Iterator<Element> rowIts = rowInfo.elements("field").iterator();
			Map<String, String> mapa = new HashMap<String, String>();
			while (rowIts.hasNext()) {
				Element el = rowIts.next();
				mapa.put(el.attributeValue("code"), el.getText());
			}
			rsLista.add(mapa);
		}
		return rsLista;
	}

	/**
	 * 获取按钮个数
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getActionOps() {
		map = new HashMap<String, String>();
		Iterator<Element> its = root.element("baseInfo").element("actionOps")
				.elements("actionop").iterator();
		while (its.hasNext()) {
			Element rowInfo = its.next();
			map.put(rowInfo.attributeValue("id"), rowInfo.getText());
		}
		return map;
	}

	/**
	 * 获取登录时版本号
	 * 
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<Type> getType() {
		List<Type> types = new ArrayList<Type>();
		// 得到根节点
		// Element root = doc.getRootElement();
		// 查找所有的 recodeInfo节点
		Iterator<Element> categorys = root.element("recordInfo")
				.elements("category").iterator();
		while (categorys.hasNext()) {
			Element category = categorys.next();
			String categoryType = category.attributeValue("type");
			Iterator<Element> its = category.elements("ver").iterator();
			/** 遍历所有的recordInfo节点 */
			while (its.hasNext()) {
				Type tp = new Type();
				Element rowInfo = its.next();
				type = rowInfo.attributeValue("type");
				tp.setType(type);//工单类型编号  WF4:EL_TTM_TTH
				tp.setText(rowInfo.attributeValue("text")); //工单类型名称   故障工单
				tp.setType_id(rowInfo.getText());//WF4_EL_TTM_TTH_2013526_1372236901
				tp.setCategoryType(categoryType);//工单类别  workflow
//				if(categoryType.equals("workflow")){
					types.add(tp);
//				}
			}
		}

		return types;
	}

	/**
	 * 获取模版字典
	 * 
	 * @return dic：字典
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Dic> getDicDefine() {
		Map<String, Dic> dicDefineMap = new HashMap<String, Dic>();
		Iterator<Element> its = null;
		if(root.element("recordInfo") != null && 
				root.element("recordInfo").element("dicDefine") != null
				&& root.element("recordInfo").element("dicDefine")
				.elements("dic")!= null){
			its = root.element("recordInfo").element("dicDefine")
					.elements("dic").iterator();
		}
		while (its!= null && its.hasNext()) {
			Element rowInfo = its.next();

			String dicId = rowInfo.attributeValue("id");
			String dicType = rowInfo.attributeValue("type");

			Iterator<Element> items = null;

			if (dicType.equals("COLLECT")) {
				items = rowInfo.elements("item").iterator();
			} else {
				items = getDicItems(rowInfo);
			}

			Dic dic = new Dic();
			LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
			while (items.hasNext()) {
				Element item = items.next();
				options.put(item.attributeValue("code"), item.getText());
			}
			dic.setOptions(options);
			dicDefineMap.put(dicId, dic);

		}

		return dicDefineMap;
	}

	public HashMap<String, HashMap<String, TemplateRT>> gettemplatestore() {
		HashMap<String, HashMap<String, TemplateRT>> templatestore = new HashMap<String, HashMap<String, TemplateRT>>();
		Iterator<Element> its = null;
		try {
			if(root.element("recordInfo")!= null && root.element("recordInfo").element("templatestore") != null
					&& root.element("recordInfo").element("templatestore")
					.elements("template") != null)
			its = root.element("recordInfo").element("templatestore")
					.elements("template").iterator();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (its!= null && its.hasNext()) {
			Element rowInfo = its.next();
			String tmpId = rowInfo.attributeValue("id");

			HashMap<String, TemplateRT> map = new HashMap<String, TemplateRT>();

			Iterator<Element> items = rowInfo.elements("part").iterator();
			while (items.hasNext()) {
				TemplateRT templateRT = new TemplateRT();

				Element item = items.next();
				String[] param = item.attributeValue("param").split("=");
				templateRT.setParamId(param[0]);
				templateRT.setParamCode(param[1]);

				ArrayList<String> list = new ArrayList<String>();

				Iterator<Element> contents = item.elements("content")
						.iterator();
				while (contents.hasNext()) {
					Element content = contents.next();
					list.add(content.getText());
				}

				templateRT.setContent(list);
				map.put(item.attributeValue("param"), templateRT);
			}

			templatestore.put(tmpId, map);
		}
		return templatestore;
	}

	/**
	 * 获取经纬度
	 * 
	 * @return
	 */
	public XjItem getTude() {
		Element xjElement = root.element("recordInfo");
		XjItem xjItem = new XjItem();
		xjItem.setTaskResult(getElementNodeValue(xjElement, "taskResult"));
		xjItem.setTaskNote(getElementNodeValue(xjElement, "taskNote"));
		xjItem.setLongitude(getElementNodeValue(xjElement, "longitude"));
		xjItem.setLatitude(getElementNodeValue(xjElement, "latitude"));
		return xjItem;
	}

	/**
	 * 巡检工单每个item请求数据
	 * 
	 * @return
	 */
	public List<XJBaseField> getBaseFileds() {
		List<XJBaseField> xjbaseFields = new ArrayList<XJBaseField>();
		Element xjElement = root.element("recordInfo");
		if (xjElement != null) {
			Iterator<Element> rowIts = xjElement.elements("item").iterator();
			while (rowIts.hasNext()) {
				Element el = rowIts.next();
				String objectId = el.element("objectId").getStringValue();
				XJBaseField xjbaseField = new XJBaseField();
				xjbaseField.setObjectId(objectId);
				xjbaseField.setXjFieldGroup(getXJFieldGroup(el));
				xjbaseFields.add(xjbaseField);
			}
		}
		return xjbaseFields;
	}

	/**
	 * 巡检页面
	 * 
	 * @param el
	 * @return
	 */
	public List<XJFieldGroup> getXJFieldGroup(Element el) {
		List<XJFieldGroup> xjList = new ArrayList<XJFieldGroup>();
		Iterator<Element> rowIts = el.element("contents").elementIterator();

		while (rowIts.hasNext()) {
			Element element = rowIts.next();
			XJFieldGroup xjFieldGroup = new XJFieldGroup();
			String taskContentId = element.element("taskContentId")
					.getStringValue();
			String dataValue = element.element("dataValue").getStringValue();
			String contentId = element.element("contentId").getStringValue();
			xjFieldGroup.setContentId(contentId);
			xjFieldGroup.setDataValue(dataValue);
			xjFieldGroup.setTaskContentId(taskContentId);
			xjList.add(xjFieldGroup);
		}
		return xjList;
	}
    /**
     * 线路巡检分段线路
     * @param el
     * @return
     */
	public List<XJLineField> getXJLineField() {
		List<XJLineField> xjLineList = new ArrayList<XJLineField>();
		if(root.element("recordInfo").element("reses")!=null){
		Iterator<Element> rowIts = root.element("recordInfo").element("reses").elements("res").iterator();		
		while (rowIts.hasNext()) {
			Element element = rowIts.next();
			XJLineField xjLineField = new XJLineField();
			String lineName = element.attributeValue("name");
			String minLongitude = element.attributeValue("minLongitude");
			String maxLongitude = element.attributeValue("maxLongitude");
			String minLatitude = element.attributeValue("minLatitude");
			String maxLatitude = element.attributeValue("maxLatitude");
			xjLineField.setResName(lineName);
			xjLineField.setMinLongitude(minLongitude);
			xjLineField.setMaxLongitude(maxLongitude);
			xjLineField.setMinLatitude(minLatitude);
			xjLineField.setMaxLatitude(maxLatitude);
			xjLineList.add(xjLineField);
		}
		}
		return xjLineList;
	}

	/**
	 * 巡检页面
	 * 
	 * @param el
	 * @return
	 */
	public XJField getXJField(Element el) {
		XJField xjfield = new XJField();
		xjfield.setTaskContendId(el.element("taskContentId").getStringValue());
		xjfield.setMaintainContent(el.element("maintainContent")
				.getStringValue());
		xjfield.setQualityStandard(el.element("qualityStandard")
				.getStringValue());
		xjfield.setWriteWay(el.element("writeWay").getStringValue());
		xjfield.setDataItem(el.element("dataItem").getStringValue());
		xjfield.setDefaultValue(el.element("defaultValue").getStringValue());
		xjfield.setDetaValue(el.element("dataValue").getStringValue());
		return xjfield;
	}

	/**
	 * 获取工单详情列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BaseField> getBaseField() {
		List<BaseField> baseFields = new ArrayList<BaseField>();
		Element baseFieldXML = root.element("recordInfo").element("baseField");
		if (baseFieldXML != null) {

			Iterator<Element> rowIts = baseFieldXML.elementIterator();
			while (rowIts.hasNext()) {
				Element el = rowIts.next();
				String elName = el.getName();

				BaseField baseField = new BaseField();
				if (elName.equals("field")) {
					baseField.setField(getField(el));
				} else {
					baseField.setFieldGroup(getFieldGroup(el));
				}
				// 预留
				baseField.setId(el.attributeValue(""));
				baseField.setType(elName);
				baseFields.add(baseField);
			}
		}
		return baseFields;
	}

	// 适配器字段方法
	public Field getField(Element el) {
		Field field = new Field();
		field.setId(el.attributeValue("code"));//字段名称
		field.setType(el.attributeValue("type"));//字段类型
		field.setRow(el.attributeValue("row"));//文本域的行数
		field.setDic(el.attributeValue("dic"));//数据字典
		field.setText(el.getText());//中文字段名称

		field.setAssignType(el.attributeValue("assignType"));
		field.setCorp(el.attributeValue("corp"));//公司名称
		field.setCenter(el.attributeValue("center"));//中心名称
		field.setStation(el.attributeValue("station"));//站点名称
		field.setTeam(el.attributeValue("team"));//小组名称
		field.setPerson(el.attributeValue("person"));//人员名称
		field.setSelect(el.attributeValue("select"));
		field.setMulti(el.attributeValue("multi"));//是否多选

		field.setTemplate(el.attributeValue("template"));//工单模板
		return field;
	}

	// 迭代增加组数据
	public FieldGroup getFieldGroup(Element el) {
		FieldGroup fieldGroup = new FieldGroup();

		Iterator<Element> rowIts = el.elements("field").iterator();
		List<Field> field = new ArrayList<Field>();
		while (rowIts.hasNext()) {
			field.add(getField(rowIts.next()));
		}

		fieldGroup.setText(el.attributeValue("text"));
		fieldGroup.setGroup(field);
		return fieldGroup;
	}

	// 解析按钮事件
	public List<ActionButton> getActionButton() {
		List<ActionButton> ActionButtons = new ArrayList<ActionButton>();

		Element actionFieldXML = root.element("recordInfo").element(
				"actionFields");
		System.out.println("actionFieldXML=======" + actionFieldXML);
		if (actionFieldXML != null) {
			Iterator<Element> rowIts = actionFieldXML.elements("action")
					.iterator();
			while (rowIts.hasNext()) {
				Element el = rowIts.next();
				ActionButtons.add(getAction(el));
			}
		}
		return ActionButtons;
	}

	public ActionButton getAction(Element el) {
		ActionButton act = new ActionButton();
		act.setId(el.attributeValue("id"));
		act.setCode(el.attributeValue("code"));
		act.setName(el.attributeValue("text"));
		act.setPhoto(el.attributeValue("photo"));
		act.setRadio(el.attributeValue("radio"));
		act.setCreateaction(el.attributeValue("createaction"));
		Iterator<Element> rowIts = el.elements("field").iterator();
		List<Field> field = new ArrayList<Field>();
		while (rowIts.hasNext()) {
			field.add(getField(rowIts.next()));
		}
		act.setField(field);
		return act;
	}

	public boolean getIsLegal() {
		boolean b = false;
		try {
			Element root = doc.getRootElement();
			Element its = root.element("baseInfo").element("isLegal");
			b = its.getText().equals("1") ? true : false;
		} catch (Exception e) {
		}
		return b;
	}

	/**
	 * 获取登录用户名
	 * 
	 * @return 用户名
	 */
	public String getUserName() {
		String userName = "";
		try {
			Element root = doc.getRootElement();
			Element its = root.element("baseInfo").element("userName");
			if (its != null) {
				userName = its.getText();
			}
		} catch (Exception e) {
		}
		return userName;
	}

	public String getDwSpecialtyId() {
		String dwSpecialtyId = "";
		try {
			Element root = doc.getRootElement();
			Element its = root.element("baseInfo").element("dwSpecialtyId");
			if (its != null) {
				dwSpecialtyId = its.getText();
				System.out.println("dwSpecialtyId!!!!!!!!!!!" + dwSpecialtyId);
			}
		} catch (Exception e) {
		}
		return dwSpecialtyId;
	}

	public boolean getSuccess() {
		boolean b = false;
		try {
			Element root = doc.getRootElement();
			Element its = root.element("baseInfo").element("success");
			b = its.getText().equals("1");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}
	/**
	 * 获取服务器返回错误信息
	 * @return
	 */
	public String getErrorMessage(){
		String errorMessage=null;
		try {
			Element root = doc.getRootElement();
			Element its = root.element("baseInfo");
			errorMessage = getElementNodeValue(its,"errorMessage");;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorMessage;
	}

	/** 新建隐患上报 返回成功时得到taskId */
	public String getTaskId() {
		String taskId = null;
		try {
			Element root = doc.getRootElement();
			Element its = root.element("baseInfo").element("taskId");
			if (its != null) {
				taskId = its.getText();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskId;
	}

	public void addMap() {
	}

	public Element getXmlRow() {
		return null;
	}

	private Iterator<Element> getDicItems(Element xml) {
		return null;
	}

	/**
	 * 解析方法 获取巡检模版内容
	 * 
	 * @return
	 */
	public HashMap<String, RecordInfo> analycXML() {
		HashMap<String, RecordInfo> rcdMap = new HashMap<String, RecordInfo>();
		Element disElement = root.element("recordInfo");
		Iterator<Element> items = disElement.elements("item").iterator();
		while (items.hasNext()) {
			RecordInfo recordInfo = new RecordInfo();
			HashMap<String, Content> contents = new HashMap<String, Content>();
			Element ele = items.next();
			Iterator<Element> contentIts = ele.element("contents")
					.elements("content").iterator();
			while (contentIts.hasNext()) {
				Content content = new Content();
				Element el = contentIts.next();
				content.setContentId(getElementNodeValue(el, "contentId"));
				content.setDataItem(getElementNodeValue(el, "dataItem"));
				content.setMaintainContent(getElementNodeValue(el,
						"maintainContent"));

				content.setQualityStandard(getElementNodeValue(el,
						"qualityStandard"));
				content.setWriteWayid(getElementNodeValue(el, "writeWayid"));
				content.setDefaultValue(getElementNodeValue(el, "defaultValue"));
				content.setDataType(getElementNodeValue(el, "dataType"));
				contents.put(content.getContentId(), content);
				recordInfo.setContents(contents);
			}
			recordInfo.setObjectId(getElementNodeValue(ele, "objectId"));
			recordInfo
					.setPatrolObject(getElementNodeValue(ele, "patrolObject"));
			recordInfo
					.setDistance(getElementNodeValue(disElement, "eDistance"));

			rcdMap.put(recordInfo.getObjectId(), recordInfo);
		}
		return rcdMap;
	}

	private String getElementNodeValue(Element el, String subNode) {
		String result = "";
		if (subNode != null && el != null) {
			Element elSub = el.element(subNode);
			if (elSub != null) {
				result = elSub.getText();
			}
		}
		return result;
	}
}