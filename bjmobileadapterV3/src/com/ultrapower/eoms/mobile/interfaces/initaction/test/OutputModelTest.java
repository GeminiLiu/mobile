package com.ultrapower.eoms.mobile.interfaces.initaction.test;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ultrapower.eoms.mobile.interfaces.initaction.model.InitActionOutputModel;

import junit.framework.TestCase;

public class OutputModelTest extends TestCase
{

	public void testBuildXml()
	{
		//创建期望对象
		StringBuilder expectedXml = new StringBuilder();
		expectedXml.append("<opDetail>");
			expectedXml.append("<baseInfo>");
				expectedXml.append("<isLegal>1</isLegal>");
			expectedXml.append("</baseInfo>");
			expectedXml.append("<recordInfo>");
				expectedXml.append("<field code=\"CCH_T1Result\">完成处理</field>");
				expectedXml.append("<field code=\"CCH_Finish_Phase\">一级处理完成</field>");
				expectedXml.append("<field code=\"CCH_ONE_dealResult\"></field>");
			expectedXml.append("</recordInfo>");
		expectedXml.append("</opDetail>");
		
		//创建实际对象
		InitActionOutputModel outputModel = new InitActionOutputModel();
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("CCH_T1Result", "完成处理");
		fields.put("CCH_Finish_Phase", "一级处理完成");
		fields.put("CCH_ONE_dealResult", "");
		outputModel.setFields(fields);
		String actualXml = outputModel.buildXml();
		
		//测试
		assertEquals(expectedXml.toString(), actualXml);
	}
	
	public void testBuildExceptionXml()
	{
		assertEquals("<opDetail><baseInfo><isLegal>0</isLegal></baseInfo></opDetail>", InitActionOutputModel.buildExceptionXml());
	}
}
