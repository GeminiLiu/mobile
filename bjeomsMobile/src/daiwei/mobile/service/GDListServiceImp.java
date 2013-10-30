package daiwei.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;


import daiwei.mobile.animal.ListModel;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;

public class GDListServiceImp implements GDListService{
	/**
	 * eoms系统手机工单改造
	 */
	public ListModel getWaitList(Context context,int isWait,int pageNum,int pageSize, String category, String queryCondition) {
		ListModel lm = new ListModel();
		String str = null;
		if("ALL".equalsIgnoreCase(category)){//工单分类为all时获取所有工单待办列表
			str = "";
		}else{
			str = category;
		}
		
		TestXmlCreat tc = new TestXmlCreat(context);
		HTTPConnection hc = new HTTPConnection(context);
		tc.addElement(tc.recordInfo, "isWait", String.valueOf(isWait));
		tc.addElement(tc.recordInfo, "queryCondition", queryCondition);
		String fields = "BaseID,BaseSchema,BaseSN,BaseStatus,TaskID,BaseSummary," +
				"CCH_ComplainType,CCH_ComplainDesc,CCH_IncidentStartTime," +
				"BaseDealOuttime,NetElement,BaseTaskType,INC_NE_Name," +
				"BaseDescrption,CCH_ComplainType,BaseCreateDate," +
				"ITMS_Event_Classify,ITMS_Event_Type,Approval1_Type,ITSM_DBUA_Change_Type";
		tc.addElement(tc.recordInfo, "queryFields",fields);
		tc.addElement(tc.recordInfo, "pageNum",String.valueOf(pageNum));
		tc.addElement(tc.recordInfo, "pageSize",String.valueOf(pageSize));
		//tc.addElement(tc.recordInfo, "category",categoryCode.getCode());
		tc.addElement(tc.recordInfo, "category",str);
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "G001");
		parmas.put("inputXml", tc.doc.asXML());
		String x = hc.Sent(parmas);
		
		XMLUtil xml = new XMLUtil(x.toString());
		if(xml.getIsLegal()){
		lm.setListInfo(xml.getList());
		lm.setBaseCount(xml.getBaseCount());
		}
		else{
			lm=null;
		}
		return lm;
	}
	/**
	 * eoms系统手机公告已办列表
	 */
	public ListModel getGgWaitList(Context context,int isWait,int pageNum,int pageSize, String category, String queryCondition) {
		ListModel lm = new ListModel();
		String str = null;
		if("ALL".equalsIgnoreCase(category)){//工单分类为all时获取所有工单待办列表
			str = "";
		}else{
			str = category;
		}
		
		TestXmlCreat tc = new TestXmlCreat(context);
		HTTPConnection hc = new HTTPConnection(context);
		tc.addElement(tc.recordInfo, "isWait", String.valueOf(isWait));
		tc.addElement(tc.recordInfo, "queryCondition", queryCondition);
		StringBuffer fieldsBuffer = new StringBuffer();
		fieldsBuffer.append("BaseID,BaseSchema,BaseSN,BaseStatus,TaskID,BaseSummary,");
		//建单人单位 、建单人部门、紧急程度、有效期 
		fieldsBuffer.append("BaseCreatorCorp,BaseCreatorDep,BasePriority,End_Time");
		String fields =  fieldsBuffer.toString();
		tc.addElement(tc.recordInfo, "queryFields",fields);
		tc.addElement(tc.recordInfo, "pageNum",String.valueOf(pageNum));
		tc.addElement(tc.recordInfo, "pageSize",String.valueOf(pageSize));
		//tc.addElement(tc.recordInfo, "category",categoryCode.getCode());
		tc.addElement(tc.recordInfo, "category",str);
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "G001");
		parmas.put("inputXml", tc.doc.asXML());
		String x = hc.Sent(parmas);
		
		XMLUtil xml = new XMLUtil(x.toString());
		if(xml.getIsLegal()){
		lm.setListInfo(xml.getList());
		lm.setBaseCount(xml.getBaseCount());
		}
		else{
			lm=null;
		}
		return lm;
	}
	/* (non-Javadoc)
	 * @see daiwei.mobile.service.GDListService#getWaitList(android.content.Context, int, int, int, int, java.lang.String)
	 */
	public ListModel getWaitListOld(Context context,int isWait,int pageNum,int pageSize, int category, String queryCondition) {
		ListModel lm = new ListModel();
		String str = null;
		if(category==2){	//故障
			str = "WF4:EL_AM_TTH";
		}
		else if(category==3)//发电
			str = "WF4:EL_AM_PS";
		else if(category==4){//任务
			str = "WF4:EL_AM_AT";
		}
		else if(category==1){//全部
			str="";
		}else if(category==5){//隐患上报
			str="WF4:EL_AM_ER";
		}else if(category==6){//公告
			str="WF4:EL_AM_BULT";
		}
		TestXmlCreat tc = new TestXmlCreat(context);
		HTTPConnection hc = new HTTPConnection(context);
		tc.addElement(tc.recordInfo, "isWait", String.valueOf(isWait));
		tc.addElement(tc.recordInfo, "queryCondition", queryCondition);
		tc.addElement(tc.recordInfo, "queryFields","BaseID,BaseSchema,BaseSN,BaseStatus,TaskID,BaseSummary,CCH_ComplainType,BaseDwItem,CCH_IncidentStartTime,BaseDealOuttime,NetElement,SiteName,INC_NE_Name,BaseDwItem,AffectBussType,BaseCreateDate");
		tc.addElement(tc.recordInfo, "pageNum",String.valueOf(pageNum));
		tc.addElement(tc.recordInfo, "pageSize",String.valueOf(pageSize));
		//tc.addElement(tc.recordInfo, "category",categoryCode.getCode());
		tc.addElement(tc.recordInfo, "category",str);
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "G001");
		parmas.put("inputXml", tc.doc.asXML());
		String x = hc.Sent(parmas);
		
		XMLUtil xml = new XMLUtil(x.toString());
		if(xml.getIsLegal()){
		lm.setListInfo(xml.getList());
		lm.setBaseCount(xml.getBaseCount());
		}
		else{
			lm=null;
		}
		return lm;
	}

	@Override
	public List<Map<String, String>> getFinishList(int pageNum) {
		StringBuffer idata = new StringBuffer("<opDetail  sp='1'  ep='1'  ps='5' pc='2'  rc='10'  rs='20'>");
		for(int i=0;i<pageNum;i++){
			idata.append("	<recordInfo>");
			idata.append("	<icon>"+(i+1)+"</icon>");
			idata.append("	<content1>请对相关基站内部空调设备进行厂家统计"+(i+1)+"</content1>");
			idata.append("	<content2>2012-12-11 21:34:41</content2>");
			idata.append("  <content3>未签收</content3>");
			idata.append("	</recordInfo>");
		}
		idata.append("</opDetail>");
		XMLUtil xml = new XMLUtil(idata.toString());
		return xml.getList();
	}

	
}