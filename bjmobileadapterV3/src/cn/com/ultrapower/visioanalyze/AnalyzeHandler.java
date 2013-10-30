package cn.com.ultrapower.visioanalyze;

import java.util.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.ultrapower.system.util.FormatInt;
import cn.com.ultrapower.system.util.FormatTime;
import cn.com.ultrapower.ultrawf.control.design.TplBaseObj;
import cn.com.ultrapower.ultrawf.control.design.TplDesignManager;
import cn.com.ultrapower.ultrawf.control.design.TplLinkObj;
import cn.com.ultrapower.ultrawf.control.design.TplProcessObj;
import cn.com.ultrapower.ultrawf.control.design.TplStateObj;

public class AnalyzeHandler
{
	private Map<String, MasterModel> masterMap;
	private Map<String, ShapeModel> shapeMap;
	private List<ConnectModel> connectList;
	
	private int pxX = 0;
	private int pxY = 0;
	
	private int processcount = 0;
	
	public static void main(String[] args)
	{
		AnalyzeHandler ah = new AnalyzeHandler();
		ah.testAnalyze();
	}
	
	public void testAnalyze()
	{
		try
		{
			FileInputStream file = new FileInputStream ("E:\\电路调度工单_1.vdx");
			Map xmlMap = new HashMap();   
	        xmlMap.put("wfd","http://schemas.microsoft.com/visio/2003/core");
			SAXReader saxReader = new SAXReader();
			saxReader.getDocumentFactory().setXPathNamespaceURIs(xmlMap);
			InputStreamReader reader  = new InputStreamReader(file,"utf-8");
			Document doc = saxReader.read(reader);
			analyze(doc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	public void doAnalyze(HttpServletRequest request)
	{
		try
		{
			InputStream fileIS = null;
			
			DiskFileUpload fu = new DiskFileUpload();
			// 设置允许用户上传文件大小,单位:字节
			fu.setSizeMax(100000000);
			// 设置最多只允许在内存中存储的数据,单位:字节
			fu.setSizeThreshold(4096);
			List fileItems = fu.parseRequest(request);
			// 依次处理每个上传的文件
			Iterator iter = fileItems.iterator();
			while (iter.hasNext())
			{
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField())
				{
					fileIS = item.getInputStream();
				}
			}
			
			//FileInputStream file = new FileInputStream ("E:\\电路调度工单_1.vdx");
			Map xmlMap = new HashMap();   
            xmlMap.put("wfd","http://schemas.microsoft.com/visio/2003/core");
			SAXReader saxReader = new SAXReader();
			saxReader.getDocumentFactory().setXPathNamespaceURIs(xmlMap);
			InputStreamReader reader  = new InputStreamReader(fileIS,"utf-8");
			Document doc = saxReader.read(reader);
			analyze(doc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	public void analyze(Document doc)
	{
		//处理XML
		try
		{
			//处理Master标签
			List<Element> eleMasterList = doc.selectNodes("/wfd:VisioDocument/wfd:Masters/wfd:Master");
			masterAnalyze(eleMasterList);
			
			//处理Shape标签
			List<Element> eleShapeList = doc.selectNodes("/wfd:VisioDocument/wfd:Pages/wfd:Page/wfd:Shapes/wfd:Shape");
			shapeAnalyze(eleShapeList);
			
			//处理Connects标签
			List<Element> eleConnectList = doc.selectNodes("/wfd:VisioDocument/wfd:Pages/wfd:Page/wfd:Connects/wfd:Connect");
			connectAnalyze(eleConnectList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		
		//开始处理数据
		
		TplBaseObj tbObj = new TplBaseObj();
		
		List<ShapeModel> statusShapeList = new ArrayList<ShapeModel>();
		MasterModel statusMasterModel = null;
		List<ShapeModel> assistantShapeList = new ArrayList<ShapeModel>();
		MasterModel assistantStartMasterModel = null;
		MasterModel assistantEndMasterModel = null;
		List<ShapeModel> processShapeList = new ArrayList<ShapeModel>();
		MasterModel processMasterModel = null;
		List<ShapeModel> verdictShapeList = new ArrayList<ShapeModel>();
		MasterModel verdictMasterModel = null;
		List<ShapeModel> linkShapeList = new ArrayList<ShapeModel>();
		MasterModel linkMasterModel = null;
		
		//对数据进行分类
		for(ShapeModel shapeModel : shapeMap.values())
		{
			MasterModel masterModel = masterMap.get(shapeModel.getMaster());
			if(masterModel.getName().equals("流程模板"))
			{
				setBase(tbObj, shapeModel, masterModel);
			}
			else if(masterModel.getName().equals("状态"))
			{
				statusShapeList.add(shapeModel);
				if(statusMasterModel == null) statusMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("开始"))
			{
				assistantShapeList.add(shapeModel);
				if(assistantStartMasterModel == null) assistantStartMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("结束"))
			{
				assistantShapeList.add(shapeModel);
				if(assistantEndMasterModel == null) assistantEndMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("环节"))
			{
				processShapeList.add(shapeModel);
				if(processMasterModel == null) processMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("判断"))
			{
				verdictShapeList.add(shapeModel);
				if(verdictMasterModel == null) verdictMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("流转") || masterModel.getName().equals("确认流转") || masterModel.getName().equals("否认流转"))
			{
				linkShapeList.add(shapeModel);
				if(linkMasterModel == null) linkMasterModel = masterModel;
			}
		}
		
		setStatus(tbObj, statusShapeList, statusMasterModel);
		setAssistant(tbObj, assistantShapeList, assistantStartMasterModel, assistantEndMasterModel);
		setProcess(tbObj, processShapeList, processMasterModel);
		setVerdict(tbObj, verdictShapeList, verdictMasterModel);
		setLink(tbObj, linkShapeList, linkMasterModel);
		
		TplDesignManager tdm = new TplDesignManager();
		tdm.save("new", tbObj, null);
	}
	
	public void masterAnalyze(List<Element> eleMasterList)
	{
		masterMap = new HashMap<String, MasterModel>();
		
		for(Element eleMaster : eleMasterList)
		{
			MasterModel masterModel = new MasterModel();
			masterModel.setId(eleMaster.attributeValue("ID"));
			masterModel.setNameU(eleMaster.attributeValue("NameU"));
			masterModel.setName(eleMaster.attributeValue("Name"));
			
			List<Element> elePropList = eleMaster.selectNodes("wfd:Shapes/wfd:Shape/wfd:Prop");
			
			Map<String, PropModel> propModelMap = new HashMap<String, PropModel>();
			for(Element eleProp : elePropList)
			{
				PropModel propModel = new PropModel();
				propModel.setId(eleProp.attributeValue("ID"));
				propModel.setNameU(eleProp.attributeValue("NameU"));
				propModel.setLabel(eleProp.elementText("Label"));
				propModel.setValue(eleProp.elementText("Value"));
				propModelMap.put(propModel.getLabel(), propModel);
			}
			masterModel.setPropModelMap(propModelMap);
			masterMap.put(masterModel.getId(), masterModel);
		}
	}
	
	public void shapeAnalyze(List<Element> eleShapeList)
	{
		shapeMap = new HashMap<String, ShapeModel>();
		
		for(Element eleShape : eleShapeList)
		{
			ShapeModel shapeModel = new ShapeModel();
			shapeModel.setId(eleShape.attributeValue("ID"));
			shapeModel.setNameU(eleShape.attributeValue("NameU"));
			shapeModel.setName(eleShape.attributeValue("Name"));
			shapeModel.setMaster(eleShape.attributeValue("Master"));
			
			Element xForm = (Element)eleShape.selectSingleNode("wfd:XForm");
			shapeModel.setX(xForm.elementText("PinX"));
			Element elePinX = (Element)xForm.selectSingleNode("wfd:PinX");
			String attF = elePinX.attributeValue("F");
			if(attF != null && attF.indexOf("!") > 0)
			{
				shapeModel.setFormat(attF.substring(0, attF.indexOf("!")));
				
			}
			shapeModel.setY(xForm.elementText("PinY"));
			shapeModel.setWidth(xForm.elementText("Width"));
			
			List<Element> elePropList = eleShape.selectNodes("wfd:Prop");
			if(elePropList.size() > 0)
			{
				Map<String, PropModel> propModelMap = new HashMap<String, PropModel>();
				for(Element eleProp : elePropList)
				{
					PropModel propModel = new PropModel();
					propModel.setId(eleProp.attributeValue("ID"));
					propModel.setNameU(eleProp.attributeValue("NameU"));
					propModel.setLabel(eleProp.elementText("Label"));
					propModel.setValue(eleProp.elementText("Value"));
					propModelMap.put(propModel.getLabel(), propModel);
				}
				shapeModel.setPropModelMap(propModelMap);
			}
			
			String masterName = masterMap.get(shapeModel.getMaster()).getName();
			if(masterName.equals("开始") || masterName.equals("环节") || masterName.equals("判断") || masterName.equals("结束"))
			{
				List<Element> eleConnectionList = eleShape.selectNodes("wfd:Connection");
				if(eleConnectionList.size() > 0)
				{
					String[][] connArray = new String[4][2];
					double maxX = 0.0;
					double maxY = 0.0;
					for(int iConn = 0; iConn < eleConnectionList.size(); iConn++)
					{
						String x = eleConnectionList.get(iConn).elementText("X");
						String y = eleConnectionList.get(iConn).elementText("Y");
						connArray[iConn][0] = x;
						connArray[iConn][1] = y;
						double dx = Double.valueOf(x);
						double dy = Double.valueOf(y);
						if(maxX < dx) maxX = dx;
						if(maxY < dy) maxY = dy;
					}
					
					StringBuilder connectionPoint = new StringBuilder();
					
					for(String[] conn : connArray)
					{
						double dx = Double.valueOf(conn[0]);
						double dy = Double.valueOf(conn[1]);
						if(dx < maxX && dy == 0)
						{
							connectionPoint.append(",-1");
						}
						else if(dx < maxX && dy == maxY)
						{
							connectionPoint.append(",1");
						}
						else if(dx == 0 && dy < maxY)
						{
							connectionPoint.append(",-2");
						}
						else if(dx == maxX && dy < maxY)
						{
							connectionPoint.append(",2");
						}
					}
					
					shapeModel.setConnectionPoint(connectionPoint.toString().substring(1));
				}
			}
			shapeMap.put(shapeModel.getId(), shapeModel);
		}
	}
	
	public void connectAnalyze(List<Element> eleConnectList)
	{
		connectList = new ArrayList<ConnectModel>();
		
		for(Element eleConnect : eleConnectList)
		{
			ConnectModel connectModel = new ConnectModel();
			connectModel.setFromSheet(eleConnect.attributeValue("FromSheet"));
			connectModel.setFromCell(eleConnect.attributeValue("FromCell"));
			connectModel.setToSheet(eleConnect.attributeValue("ToSheet"));
			connectModel.setToCell(eleConnect.attributeValue("ToCell"));
			connectModel.setToPart(eleConnect.attributeValue("ToPart"));
			connectList.add(connectModel);
		}
	}
	
	private void setBase(TplBaseObj tbObj, ShapeModel shapeModel, MasterModel masterModel)
	{
		tbObj.getTbModel().setBaseTplName(masterModel.getPropModelMap().get("模板名称").getValue());
		tbObj.getTbModel().setBaseTplSchema(masterModel.getPropModelMap().get("流程类别").getValue());
		tbObj.getTbModel().setBaseTplSchemaName(masterModel.getPropModelMap().get("流程名称").getValue());
		tbObj.getTbModel().setBaseTplVersion(masterModel.getPropModelMap().get("模板版本").getValue());
		if(!masterModel.getPropModelMap().get("启用时间").getValue().equals("")) tbObj.getTbModel().setBaseTplStartDate(FormatTime.FormatDateStringToInt(masterModel.getPropModelMap().get("启用时间").getValue().replaceAll("T", " ")));
		if(!masterModel.getPropModelMap().get("启用时间").getValue().equals("")) tbObj.getTbModel().setBaseTplFlagStart(FormatTime.FormatDateStringToInt(masterModel.getPropModelMap().get("启用时间").getValue().replaceAll("T", " ")));
		if(!masterModel.getPropModelMap().get("停用时间").getValue().equals("")) tbObj.getTbModel().setBaseTplEndDate(FormatTime.FormatDateStringToInt(masterModel.getPropModelMap().get("停用时间").getValue().replaceAll("T", " ")));
		if(!masterModel.getPropModelMap().get("停用时间").getValue().equals("")) tbObj.getTbModel().setBaseTplFlagEnd(FormatTime.FormatDateStringToInt(masterModel.getPropModelMap().get("停用时间").getValue().replaceAll("T", " ")));
		tbObj.getTbModel().setBaseTplIsActive(masterModel.getPropModelMap().get("是否启用").getValue().equals("是") ? 1 : 0);
		tbObj.getTbModel().setBaseTplDesc(masterModel.getPropModelMap().get("备注").getValue());
		
		if(shapeModel.getPropModelMap().get("模板名称") != null) tbObj.getTbModel().setBaseTplName(shapeModel.getPropModelMap().get("模板名称").getValue());
		if(shapeModel.getPropModelMap().get("流程类别") != null) tbObj.getTbModel().setBaseTplSchema(shapeModel.getPropModelMap().get("流程类别").getValue());
		if(shapeModel.getPropModelMap().get("流程名称") != null) tbObj.getTbModel().setBaseTplSchemaName(shapeModel.getPropModelMap().get("流程名称").getValue());
		if(shapeModel.getPropModelMap().get("模板版本") != null) tbObj.getTbModel().setBaseTplVersion(shapeModel.getPropModelMap().get("模板版本").getValue());
		if(shapeModel.getPropModelMap().get("启用时间") != null) if(!shapeModel.getPropModelMap().get("启用时间").getValue().equals("")) tbObj.getTbModel().setBaseTplStartDate(FormatTime.FormatDateStringToInt(shapeModel.getPropModelMap().get("启用时间").getValue().replaceAll("T", " ")));
		if(shapeModel.getPropModelMap().get("启用时间") != null) if(!shapeModel.getPropModelMap().get("启用时间").getValue().equals("")) tbObj.getTbModel().setBaseTplFlagStart(FormatTime.FormatDateStringToInt(shapeModel.getPropModelMap().get("启用时间").getValue().replaceAll("T", " ")));
		if(shapeModel.getPropModelMap().get("停用时间") != null) if(!shapeModel.getPropModelMap().get("停用时间").getValue().equals("")) tbObj.getTbModel().setBaseTplEndDate(FormatTime.FormatDateStringToInt(shapeModel.getPropModelMap().get("停用时间").getValue().replaceAll("T", " ")));
		if(shapeModel.getPropModelMap().get("停用时间") != null) if(!shapeModel.getPropModelMap().get("停用时间").getValue().equals("")) tbObj.getTbModel().setBaseTplFlagEnd(FormatTime.FormatDateStringToInt(shapeModel.getPropModelMap().get("停用时间").getValue().replaceAll("T", " ")));
		if(shapeModel.getPropModelMap().get("是否启用") != null) tbObj.getTbModel().setBaseTplIsActive(shapeModel.getPropModelMap().get("是否启用").getValue().equals("是") ? 1 : 0);
		if(shapeModel.getPropModelMap().get("备注") != null) tbObj.getTbModel().setBaseTplDesc(shapeModel.getPropModelMap().get("备注").getValue());
		
		pxX = FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getX())*25.4))*5+100;
		pxY = FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getY())*25.4))*5-100;
	}
	
	
	private void setStatus(TplBaseObj tbObj, List<ShapeModel> shapeModelList, MasterModel masterModel)
	{
		//排列状态顺序
		List<ShapeModel> newShapeModelList = new ArrayList<ShapeModel>();
		while(shapeModelList.size() > 0)
		{
			double minX = 0.0;
			int index = 0;
			for(int i = 0; i < shapeModelList.size(); i++)
			{
				ShapeModel shapeModel = shapeModelList.get(i);
				double x = Double.valueOf(shapeModel.getX());
				if(minX == 0.0)
				{
					minX = x;
				}
				else if(x < minX)
				{
					index = i;
					minX = x;
				}
			}
			newShapeModelList.add(shapeModelList.get(index));
			shapeModelList.remove(index);
		}
		
		for(int i = 0; i < newShapeModelList.size(); i++)
		{
			ShapeModel shapeModel = newShapeModelList.get(i);
			TplStateObj tsObj = new TplStateObj();
			tsObj.tbfsModel.setBaseStateCode("s_" + (i+1));
			shapeModel.setCode("s_" + (i+1));
			tsObj.tbfsModel.setBaseStateIndex(i);
			tsObj.tbfsModel.setBaseStateColor("#CCCCCC");
			tsObj.tbfsModel.setBaseStateLength(0);

			tsObj.tbfsModel.setBaseStateName(shapeModel.getPropModelMap().get("状态名称").getValue());
			tsObj.tbfsModel.setBaseStateLength(FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getWidth())*25.4))*5);
			tbObj.addStateModel(tsObj);
		}
		
		shapeModelList = newShapeModelList;
	}

	private void setProcess(TplBaseObj tbObj, List<ShapeModel> shapeModelList, MasterModel masterModel)
	{
		for(int i = 0; i < shapeModelList.size(); i++)
		{
			ShapeModel shapeModel = shapeModelList.get(i);
			TplProcessObj tpObj = new TplProcessObj();
			tpObj.setProcessID("dp_" + (processcount+1));
			tpObj.setProcessType("STEP");
			tpObj.tdpModel.setPhaseNo("dp_" + (processcount+1));
			shapeModel.setCode("dp_" + (processcount+1));
			for(ShapeModel statusModel : shapeMap.values())
			{
				if(masterMap.get(statusModel.getMaster()).getName().equals("状态")
						&& (statusModel.getId().equals(shapeModel.getFormat().substring(shapeModel.getFormat().indexOf(".")+1))
								|| statusModel.getNameU() != null && statusModel.getNameU().equals(shapeModel.getFormat())))
				{
					String code = statusModel.getCode();
					TplStateObj statusObj = tbObj.getStateModel(code);
					tpObj.tdpModel.setBaseStateCode(statusObj.tbfsModel.getBaseStateCode());
					tpObj.tdpModel.setBaseStateName(statusObj.tbfsModel.getBaseStateName());
					break;
				}
			}
			tpObj.tdpModel.setRoleOnlyID(masterModel.getPropModelMap().get("操作角色ID").getValue());
			tpObj.tdpModel.setRoleName(masterModel.getPropModelMap().get("操作角色").getValue());
			tpObj.tdpModel.setActionName(masterModel.getPropModelMap().get("动作名称").getValue());
			tpObj.tdpModel.setActionPageID(masterModel.getPropModelMap().get("动作页面ID").getValue());
			tpObj.tdpModel.setActionPageName(masterModel.getPropModelMap().get("动作页面").getValue());
			tpObj.tdpModel.setDesc(masterModel.getPropModelMap().get("环节描述").getValue());
			tpObj.tdpModel.setAssignOverTimeDate_tmp(Integer.parseInt(masterModel.getPropModelMap().get("派单时限（秒）").getValue().equals("") ? "0" : masterModel.getPropModelMap().get("派单时限（秒）").getValue()));
			tpObj.tdpModel.setAcceptOverTimeDate_tmp(Integer.parseInt(masterModel.getPropModelMap().get("受理时限（秒）").getValue().equals("") ? "0" : masterModel.getPropModelMap().get("受理时限（秒）").getValue()));
			tpObj.tdpModel.setDealOverTimeDate_tmp(Integer.parseInt(masterModel.getPropModelMap().get("处理时限（秒）").getValue().equals("") ? "0" : masterModel.getPropModelMap().get("处理时限（秒）").getValue()));
			String flagType = masterModel.getPropModelMap().get("处理类型").getValue();
			tpObj.tdpModel.setFlagType(flagType.equals("协办") ? 1 : 
				flagType.equals("抄送") ? 2 : 
					flagType.equals("审批") ? 3 : 
						flagType.equals("质检") ? 4 : 
							flagType.equals("会审") ? 6 : 
								flagType.equals("复核") ? 7 : 0);
			tpObj.tdpModel.setFlag01Assign(masterModel.getPropModelMap().get("允许派单？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag02Copy(masterModel.getPropModelMap().get("允许抄送？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag03Assist(masterModel.getPropModelMap().get("允许协办？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag04Transfer(masterModel.getPropModelMap().get("允许转单？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag05TurnDown(0);
			tpObj.tdpModel.setFlag06TurnUp(masterModel.getPropModelMap().get("允许驳回？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag07Recall(masterModel.getPropModelMap().get("允许追单？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag08Cancel(0);
			tpObj.tdpModel.setFlag09Close(0);
			tpObj.tdpModel.setFlag15ToAuditing(masterModel.getPropModelMap().get("允许审批？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag20SideBySide(0);
			tpObj.tdpModel.setFlag35IsCanCreateBase(0);
			tpObj.tdpModel.setFlag16ToAssistAuditing(0);
			tpObj.tdpModel.setFlag37IsNeedStartInsideFlow(masterModel.getPropModelMap().get("启动内部流程").getValue().equals("否") ? 0 : 1);
			tpObj.tdpModel.setStartInsideFlowID(masterModel.getPropModelMap().get("内部流程ID").getValue());
			tpObj.tdpModel.setStartInsideFlowName(masterModel.getPropModelMap().get("内部流程名称").getValue());
			tpObj.tdpModel.setCustomActions(masterModel.getPropModelMap().get("客户化动作").getValue());
			tpObj.tdprdModel.setRoleOnlyID(masterModel.getPropModelMap().get("操作角色ID").getValue());
			tpObj.tdprdModel.setRoleName(masterModel.getPropModelMap().get("操作角色").getValue());
			String roleType = masterModel.getPropModelMap().get("操作角色类型").getValue();
			tpObj.tdprdModel.setRoleProcessRoleType(roleType.equals("固定的执行角色") ? 0 : 
				roleType.equals("流程中已经定义的执行角色") ? 1 : 
					roleType.equals("流程流转中关联的上下文") ? 2 : 
						roleType.equals("流程流转角色通过规则生成") ? 3 : 4);
			tpObj.tdprdModel.setRoleDesc(masterModel.getPropModelMap().get("操作角色描述").getValue());
			tpObj.tdprdModel.setRoleProcessPhaseNo("dp_" + (processcount+1));
			tpObj.tdprdModel.setAssignee(masterModel.getPropModelMap().get("操作人").getValue());
			tpObj.tdprdModel.setAssigneeID(masterModel.getPropModelMap().get("操作人ID").getValue());
			tpObj.tdprdModel.setGroup(masterModel.getPropModelMap().get("操作组").getValue());
			tpObj.tdprdModel.setGroupID(masterModel.getPropModelMap().get("操作组ID").getValue());
			tpObj.tdprdModel.setRoleKey(masterModel.getPropModelMap().get("关键字").getValue());
			tpObj.tdprdModel.setContextAssignee_FieldID(masterModel.getPropModelMap().get("操作人字段ID").getValue());
			tpObj.tdprdModel.setContextAssigneeID_FieldID(masterModel.getPropModelMap().get("操作人ID字段ID").getValue());
			tpObj.tdprdModel.setContextGroup_FieldID(masterModel.getPropModelMap().get("操作组字段ID").getValue());
			tpObj.tdprdModel.setContextGroupID_FieldID(masterModel.getPropModelMap().get("操作组ID字段ID").getValue());
			tpObj.tdprdModel.setTopRoleMatchConditionsDesc(masterModel.getPropModelMap().get("角色细分规则").getValue());
			
			if(shapeModel.getPropModelMap().get("操作角色ID") != null) tpObj.tdpModel.setRoleOnlyID(shapeModel.getPropModelMap().get("操作角色ID").getValue());
			if(shapeModel.getPropModelMap().get("操作角色") != null) tpObj.tdpModel.setRoleName(shapeModel.getPropModelMap().get("操作角色").getValue());
			tpObj.tdpModel.setPosX(FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getX())*25.4))*5-pxX);
			tpObj.tdpModel.setPosY(pxY-FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getY())*25.4))*5);
			if(shapeModel.getPropModelMap().get("动作名称") != null) tpObj.tdpModel.setActionName(shapeModel.getPropModelMap().get("动作名称").getValue());
			if(shapeModel.getPropModelMap().get("动作页面ID") != null) tpObj.tdpModel.setActionPageID(shapeModel.getPropModelMap().get("动作页面ID").getValue());
			if(shapeModel.getPropModelMap().get("动作页面") != null) tpObj.tdpModel.setActionPageName(shapeModel.getPropModelMap().get("动作页面").getValue());
			if(shapeModel.getPropModelMap().get("环节描述") != null) tpObj.tdpModel.setDesc(shapeModel.getPropModelMap().get("环节描述").getValue());
			if(shapeModel.getPropModelMap().get("环节描述") == null && shapeModel.getPropModelMap().get("环节简要描述") != null) tpObj.tdpModel.setDesc(shapeModel.getPropModelMap().get("环节简要描述").getValue());
			if(shapeModel.getPropModelMap().get("派单时限（秒）") != null) tpObj.tdpModel.setAssignOverTimeDate_tmp(Integer.parseInt(shapeModel.getPropModelMap().get("派单时限（秒）").getValue().equals("") ? "0" : shapeModel.getPropModelMap().get("派单时限（秒）").getValue()));
			if(shapeModel.getPropModelMap().get("受理时限（秒）") != null) tpObj.tdpModel.setAcceptOverTimeDate_tmp(Integer.parseInt(shapeModel.getPropModelMap().get("受理时限（秒）").getValue().equals("") ? "0" : shapeModel.getPropModelMap().get("受理时限（秒）").getValue()));
			if(shapeModel.getPropModelMap().get("处理时限（秒）") != null) tpObj.tdpModel.setDealOverTimeDate_tmp(Integer.parseInt(shapeModel.getPropModelMap().get("处理时限（秒）").getValue().equals("") ? "0" : shapeModel.getPropModelMap().get("处理时限（秒）").getValue()));
			if(shapeModel.getPropModelMap().get("处理类型") != null) flagType = shapeModel.getPropModelMap().get("处理类型").getValue();
			if(shapeModel.getPropModelMap().get("处理类型") != null) tpObj.tdpModel.setFlagType(flagType.equals("协办") ? 1 : 
					flagType.equals("抄送") ? 2 : 
						flagType.equals("审批") ? 3 : 
							flagType.equals("质检") ? 4 : 
								flagType.equals("会审") ? 6 : 
									flagType.equals("复核") ? 7 : 0);
			if(shapeModel.getPropModelMap().get("允许派单？") != null) tpObj.tdpModel.setFlag01Assign(shapeModel.getPropModelMap().get("允许派单？").getValue().equals("是") ? 1 : 0);
			if(shapeModel.getPropModelMap().get("允许抄送？") != null) tpObj.tdpModel.setFlag02Copy(shapeModel.getPropModelMap().get("允许抄送？").getValue().equals("是") ? 1 : 0);
			if(shapeModel.getPropModelMap().get("允许协办？") != null) tpObj.tdpModel.setFlag03Assist(shapeModel.getPropModelMap().get("允许协办？").getValue().equals("是") ? 1 : 0);
			if(shapeModel.getPropModelMap().get("允许转单？") != null) tpObj.tdpModel.setFlag04Transfer(shapeModel.getPropModelMap().get("允许转单？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag05TurnDown(0);
			if(shapeModel.getPropModelMap().get("允许驳回？") != null) tpObj.tdpModel.setFlag06TurnUp(shapeModel.getPropModelMap().get("允许驳回？").getValue().equals("是") ? 1 : 0);
			if(shapeModel.getPropModelMap().get("允许追单？") != null) tpObj.tdpModel.setFlag07Recall(shapeModel.getPropModelMap().get("允许追单？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag08Cancel(0);
			tpObj.tdpModel.setFlag09Close(0);
			if(shapeModel.getPropModelMap().get("允许审批？") != null) tpObj.tdpModel.setFlag15ToAuditing(shapeModel.getPropModelMap().get("允许审批？").getValue().equals("是") ? 1 : 0);
			tpObj.tdpModel.setFlag20SideBySide(0);
			tpObj.tdpModel.setFlag35IsCanCreateBase(0);
			tpObj.tdpModel.setFlag16ToAssistAuditing(0);
			if(shapeModel.getPropModelMap().get("启动内部流程") != null) tpObj.tdpModel.setFlag37IsNeedStartInsideFlow(shapeModel.getPropModelMap().get("启动内部流程").getValue().equals("否") ? 0 : 1);
			if(shapeModel.getPropModelMap().get("内部流程ID") != null) tpObj.tdpModel.setStartInsideFlowID(shapeModel.getPropModelMap().get("内部流程ID").getValue());
			if(shapeModel.getPropModelMap().get("内部流程名称") != null) tpObj.tdpModel.setStartInsideFlowName(shapeModel.getPropModelMap().get("内部流程名称").getValue());
			if(shapeModel.getPropModelMap().get("客户化动作") != null) tpObj.tdpModel.setCustomActions(shapeModel.getPropModelMap().get("客户化动作").getValue());
			if(shapeModel.getPropModelMap().get("操作角色ID") != null) tpObj.tdprdModel.setRoleOnlyID(shapeModel.getPropModelMap().get("操作角色ID").getValue());
			if(shapeModel.getPropModelMap().get("操作角色") != null) tpObj.tdprdModel.setRoleName(shapeModel.getPropModelMap().get("操作角色").getValue());
			if(shapeModel.getPropModelMap().get("操作角色类型") != null) roleType = shapeModel.getPropModelMap().get("操作角色类型").getValue();
			int intRoleType = roleType.equals("固定的执行角色") ? 0 : 
				roleType.equals("流程中已经定义的执行角色") ? 1 : 
					roleType.equals("流程流转中关联的上下文") ? 2 : 
						roleType.equals("流程流转角色通过规则生成") ? 3 : 4;
			tpObj.tdprdModel.setRoleProcessRoleType(intRoleType);
			if(shapeModel.getPropModelMap().get("操作角色描述") != null) tpObj.tdprdModel.setRoleDesc(shapeModel.getPropModelMap().get("操作角色描述").getValue());
			if(shapeModel.getPropModelMap().get("操作人") != null) tpObj.tdprdModel.setAssignee(shapeModel.getPropModelMap().get("操作人").getValue());
			if(shapeModel.getPropModelMap().get("操作人ID") != null) tpObj.tdprdModel.setAssigneeID(shapeModel.getPropModelMap().get("操作人ID").getValue());
			if(shapeModel.getPropModelMap().get("操作组") != null) tpObj.tdprdModel.setGroup(shapeModel.getPropModelMap().get("操作组").getValue());
			if(shapeModel.getPropModelMap().get("操作组ID") != null) tpObj.tdprdModel.setGroupID(shapeModel.getPropModelMap().get("操作组ID").getValue());
			if(shapeModel.getPropModelMap().get("关键字") != null) tpObj.tdprdModel.setRoleKey(shapeModel.getPropModelMap().get("关键字").getValue());
			if(shapeModel.getPropModelMap().get("操作人字段ID") != null) tpObj.tdprdModel.setContextAssignee_FieldID(shapeModel.getPropModelMap().get("操作人字段ID").getValue());
			if(shapeModel.getPropModelMap().get("操作人ID字段ID") != null) tpObj.tdprdModel.setContextAssigneeID_FieldID(shapeModel.getPropModelMap().get("操作人ID字段ID").getValue());
			if(shapeModel.getPropModelMap().get("操作组字段ID") != null) tpObj.tdprdModel.setContextGroup_FieldID(shapeModel.getPropModelMap().get("操作组字段ID").getValue());
			if(shapeModel.getPropModelMap().get("操作组ID字段ID") != null) tpObj.tdprdModel.setContextGroupID_FieldID(shapeModel.getPropModelMap().get("操作组ID字段ID").getValue());
			if(shapeModel.getPropModelMap().get("角色细分规则") != null) tpObj.tdprdModel.setTopRoleMatchConditionsDesc(shapeModel.getPropModelMap().get("角色细分规则").getValue());
			
			tbObj.addProcessModel(tpObj);
			processcount++;
		}
	}

	private void setVerdict(TplBaseObj tbObj, List<ShapeModel> shapeModelList, MasterModel masterModel)
	{
		for(int i = 0; i < shapeModelList.size(); i++)
		{
			ShapeModel shapeModel = shapeModelList.get(i);
			TplProcessObj tpObj = new TplProcessObj();
			tpObj.setProcessID("dp_" + (processcount+1));
			tpObj.setProcessType("CONDITION");
			tpObj.tdvModel.setVerdictPhaseNo("dp_" + (processcount+1));
			shapeModel.setCode("dp_" + (processcount+1));
			for(ShapeModel statusModel : shapeMap.values())
			{
				if(masterMap.get(statusModel.getMaster()).getName().equals("状态")
						&& (statusModel.getId().equals(shapeModel.getFormat().substring(shapeModel.getFormat().indexOf(".")+1))
								|| statusModel.getNameU() != null && statusModel.getNameU().equals(shapeModel.getFormat())))
				{
					String code = statusModel.getCode();
					TplStateObj statusObj = tbObj.getStateModel(code);
					tpObj.tdvModel.setBaseTplStateCode(statusObj.tbfsModel.getBaseStateCode());
					tpObj.tdvModel.setBaseTplStateName(statusObj.tbfsModel.getBaseStateName());
					break;
				}
			}
			tpObj.tdvModel.setVerdictPosX(FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getX())*25.4))*5-pxX);
			tpObj.tdvModel.setVerdictPosY(pxY-FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getY())*25.4))*5);
			tpObj.tdvModel.setVerdictDesc(shapeModel.getPropModelMap().get("条件描述").getValue());
			tpObj.tdvModel.setVerdictCondition(shapeModel.getPropModelMap().get("条件").getValue());
			
			tbObj.addProcessModel(tpObj);
			processcount++;
		}
	}
	
	private void setAssistant(TplBaseObj tbObj, List<ShapeModel> shapeModelList, MasterModel startMasterModel, MasterModel endMasterModel)
	{
		for(int i = 0; i < shapeModelList.size(); i++)
		{
			ShapeModel shapeModel = shapeModelList.get(i);
			TplProcessObj tpObj = new TplProcessObj();
			if(masterMap.get(shapeModel.getMaster()).name.equals("开始"))
			{
				tpObj.setProcessID("dp_BEGIN");
				tpObj.setProcessType("BEGIN");
				tpObj.tdapModel.setAssistantProcessPhaseNo("dp_BEGIN");
				for(ShapeModel statusModel : shapeMap.values())
				{
					if(masterMap.get(statusModel.getMaster()).getName().equals("状态")
							&& (statusModel.getId().equals(shapeModel.getFormat().substring(shapeModel.getFormat().indexOf(".")+1))
									|| statusModel.getNameU() != null && statusModel.getNameU().equals(shapeModel.getFormat())))
					{
						String code = statusModel.getCode();
						TplStateObj statusObj = tbObj.getStateModel(code);
						tpObj.tdapModel.setBaseTplStateCode(statusObj.tbfsModel.getBaseStateCode());
						tpObj.tdapModel.setBaseTplStateName(statusObj.tbfsModel.getBaseStateName());
						break;
					}
				}
				tpObj.tdapModel.setAssistantProcessPosX(FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getX())*25.4))*5-pxX);
				tpObj.tdapModel.setAssistantProcessPosY(pxY-FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getY())*25.4))*5);
				tpObj.tdapModel.setAssistantProcessPurpose(0);
			}
			else
			{
				tpObj.setProcessID("dp_" + (processcount+1));
				tpObj.setProcessType("END");
				tpObj.tdapModel.setAssistantProcessPhaseNo("dp_" + (processcount+1));
				for(ShapeModel statusModel : shapeMap.values())
				{
					if(masterMap.get(statusModel.getMaster()).getName().equals("状态")
							&& (statusModel.getId().equals(shapeModel.getFormat().substring(shapeModel.getFormat().indexOf(".")+1))
									|| statusModel.getNameU() != null && statusModel.getNameU().equals(shapeModel.getFormat())))
					{
						String code = statusModel.getCode();
						TplStateObj statusObj = tbObj.getStateModel(code);
						tpObj.tdapModel.setBaseTplStateCode(statusObj.tbfsModel.getBaseStateCode());
						tpObj.tdapModel.setBaseTplStateName(statusObj.tbfsModel.getBaseStateName());
						break;
					}
				}
				tpObj.tdapModel.setAssistantProcessPosX(FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getX())*25.4))*5-pxX);
				tpObj.tdapModel.setAssistantProcessPosY(pxY-FormatInt.FormatObjectToInt(Math.round(Double.valueOf(shapeModel.getY())*25.4))*5);
				tpObj.tdapModel.setAssistantProcessPurpose(1);
				
				processcount++;
			}
			shapeModel.setCode(tpObj.getProcessID());
			tbObj.addProcessModel(tpObj);
		}
	}

	private void setLink(TplBaseObj tbObj, List<ShapeModel> shapeModelList, MasterModel masterModel)
	{
		for(int i = 0; i < shapeModelList.size(); i++)
		{
			ShapeModel shapeModel = shapeModelList.get(i);
			
			String shapeType = masterMap.get(shapeModel.getMaster()).getName();
			
			if(shapeType.equals("流转") || shapeType.equals("确认流转") || shapeType.equals("否认流转"))
			{
				ConnectModel beginX = null;
				ConnectModel endX = null;
				
				for(ConnectModel connectModel : connectList)
				{
					if(connectModel.getFromSheet().equals(shapeModel.getId()) && connectModel.getFromCell().equals("BeginX"))
					{
						beginX = connectModel;
					}
					else if(connectModel.getFromSheet().equals(shapeModel.getId()) && connectModel.getFromCell().equals("EndX"))
					{
						endX = connectModel;
					}
					if(beginX != null && endX != null) break;
				}
				
				if(beginX != null && endX != null)
				{				
					ShapeModel beginModel = shapeMap.get(beginX.getToSheet());
					ShapeModel endModel = shapeMap.get(endX.getToSheet());
					String bpNo = beginModel.getCode();
					String epNo = endModel.getCode();
					
					TplLinkObj tlObj = new TplLinkObj();
					tlObj.setLinkID("l_" + (i+1));
					tlObj.setBeginProcess(bpNo);
					tlObj.setEndProcess(epNo);
					tlObj.tdlModel.setLinkPhaseNo("l_" + (i+1));
					tlObj.tdlModel.setStartPhaseNo(bpNo);
					tlObj.tdlModel.setEndPhaseNo(epNo);
					
					if(masterMap.get(beginModel.getMaster()).getName().equals("开始"))
						tlObj.tdlModel.setStartPort(0);
					else if(masterMap.get(beginModel.getMaster()).getName().equals("结束"))
						tlObj.tdlModel.setStartPort(1);
					else if(masterMap.get(beginModel.getMaster()).getName().equals("环节"))
						tlObj.tdlModel.setStartPort(2);
					else if(masterMap.get(beginModel.getMaster()).getName().equals("判断"))
						tlObj.tdlModel.setStartPort(3);
					
					if(masterMap.get(endModel.getMaster()).getName().equals("开始"))
						tlObj.tdlModel.setEndPort(0);
					else if(masterMap.get(endModel.getMaster()).getName().equals("结束"))
						tlObj.tdlModel.setEndPort(1);
					else if(masterMap.get(endModel.getMaster()).getName().equals("环节"))
						tlObj.tdlModel.setEndPort(2);
					else if(masterMap.get(endModel.getMaster()).getName().equals("判断"))
						tlObj.tdlModel.setEndPort(3);
					
					String beginPoint = beginModel.getConnectionPoint().split(",")[Integer.parseInt(beginX.getToPart().substring(2))];
					String endPoint = endModel.getConnectionPoint().split(",")[Integer.parseInt(endX.getToPart().substring(2))];
					
					tlObj.tdlModel.setStartPoint(Integer.valueOf(beginPoint));
					tlObj.tdlModel.setEndPoint(Integer.valueOf(endPoint));
					
					if(shapeType.equals("确认流转"))
					{
						tlObj.tdlModel.setLinkVerdictResult(1);
					}
					else if(shapeType.equals("否认流转"))
					{
						tlObj.tdlModel.setLinkVerdictResult(0);
					}
					else
					{
						tlObj.tdlModel.setLinkVerdictResult(2);
					}
					
					int linkNum = Integer.valueOf(masterMap.get(shapeModel.getMaster()).getPropModelMap().get("连线编号").getValue());
					
					if(shapeModel.getPropModelMap() != null)
					{
						PropModel prop = shapeModel.getPropModelMap().get("连线编号");
						linkNum = Integer.valueOf(prop.getValue());
					}
					
					tlObj.tdlModel.setLinkNum(linkNum);
					
					tbObj.getProcessModel(bpNo).addBeginLink("l_" + (i+1));
					tbObj.getProcessModel(epNo).addEndLink("l_" + (i+1));
					
					tbObj.addLinkModel(tlObj);
				}
			}
		}
	}
}
