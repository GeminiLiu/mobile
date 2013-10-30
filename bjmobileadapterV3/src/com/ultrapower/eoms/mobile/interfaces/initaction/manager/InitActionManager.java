package com.ultrapower.eoms.mobile.interfaces.initaction.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.ultrawf.control.process.BaseManage;
import cn.com.ultrapower.ultrawf.control.process.BaseOwnFieldInfoManage;
import cn.com.ultrapower.ultrawf.models.process.BaseModel;
import cn.com.ultrapower.ultrawf.models.process.BaseOwnFieldInfoModel;

import com.ultrapower.eoms.mobile.interfaces.initaction.model.InitActionInputModel;
import com.ultrapower.eoms.mobile.interfaces.initaction.model.InitActionOutputModel;
import com.ultrapower.eoms.mobile.service.InterfaceService;

public class InitActionManager implements InterfaceService
{
	public String call(String xml, String fileList)
	{ 
		InitActionInputModel input = new InitActionInputModel();
		String outputXml;

		try
		{
			input.buildModel(xml);
			InitActionOutputModel output = handle(input);
			outputXml = output.buildXml();
		}
		catch (Exception e)
		{
			outputXml = InitActionOutputModel.buildExceptionXml();
			e.printStackTrace();
		}
		return outputXml;
	}

	private InitActionOutputModel handle(InitActionInputModel inputModel)
	{
		InitActionOutputModel outputModel = new InitActionOutputModel();
		Map<String, String> fields = new LinkedHashMap<String, String>();
		RDParameter p_ProRDParameter = new RDParameter();
		p_ProRDParameter.addIndirectPar("baseschema",inputModel.getCategory(),4);
		p_ProRDParameter.addIndirectPar("selectcondition"," AND C650042014 like '%=" +inputModel.getActionID() + ";%'",4);	
		
//		List<BaseOwnFieldInfoModel> baseOwnFieldList = (new BaseOwnFieldInfoManage()).getList(inputModel.getCategory(), null, "C650042014 like '%=" +inputModel.getActionID() + ";%'");
		List<BaseOwnFieldInfoModel> baseOwnFieldList = (new BaseOwnFieldInfoManage()).getList(p_ProRDParameter);
		long m_BFCount 	= baseOwnFieldList.size();
		String m_str_Select_BaseOwnField = "";
		for (int i=0;i<m_BFCount;i++)
		{
			BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)baseOwnFieldList.get(i);
			String m_Base_field_Type		= m_BaseOwnFieldInfoModel.getBase_field_Type();
			String m_Base_field_TypeValue	= m_BaseOwnFieldInfoModel.getBase_field_TypeValue();
			String m_Base_field_DBName		= m_BaseOwnFieldInfoModel.getBase_field_DBName();
			String m_Base_field_ID			= m_BaseOwnFieldInfoModel.getBase_field_ID();
			Long m_EntryMode =  m_BaseOwnFieldInfoModel.getEntryMode();
			// 字段类型 0：display only 1：Optional 2：required
			if(m_EntryMode != 0 )
			{
				m_str_Select_BaseOwnField 		= m_str_Select_BaseOwnField + " Base.C" + m_Base_field_ID + " as " + m_Base_field_DBName + ",";
			}
		}
		
		List<BaseOwnFieldInfoModel> dbfields = new ArrayList<BaseOwnFieldInfoModel>();
		for (BaseOwnFieldInfoModel field : baseOwnFieldList)
		{
			String code = field.getBase_field_DBName();
			String defaultValue = field.getDefVal();
			if(defaultValue.equals("$SelectDB$"))
			{
				dbfields.add(field);
			}
			fields.put(code, defaultValue);
		}
		if(m_str_Select_BaseOwnField.length() > 0)
		{
			p_ProRDParameter.addIndirectPar("baseschema",inputModel.getCategory(),4);
			p_ProRDParameter.addIndirectPar("baseid",inputModel.getBaseID(),4);
			p_ProRDParameter.addIndirectPar("formname",inputModel.getCategory(),4);
			p_ProRDParameter.addIndirectPar("selectrow",m_str_Select_BaseOwnField,4);
			
//			BaseModel baseModel = (new BaseManage()).getOneModel(inputModel.getBaseID(), inputModel.getCategory(), dbfields, "");
			BaseModel baseModel = (new BaseManage()).getOneModel(p_ProRDParameter);
			for(BaseOwnFieldInfoModel field : dbfields)
			{
				String code = field.getBase_field_DBName();
				String value = baseModel.getOneBaseFieldBean(code).getM_BaseFieldValue();
				fields.put(code, value);
			}
		}
		outputModel.setFields(fields);
		return outputModel;
	}
}
