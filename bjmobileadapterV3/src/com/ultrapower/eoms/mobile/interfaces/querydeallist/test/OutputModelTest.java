package com.ultrapower.eoms.mobile.interfaces.querydeallist.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.mobile.interfaces.querydeallist.model.QueryDealListOutputModel;

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
				expectedXml.append("<baseCount>");
					expectedXml.append("<category type=\"WF4:EL_TTM_TTH\">19</category>");
					expectedXml.append("<category type=\"WF4:EL_TTM_CCH\">3</category>");
					expectedXml.append("<category type=\"WF4:EL_UVS_TSK\">8</category>");
				expectedXml.append("</baseCount>");
			expectedXml.append("</baseInfo>");
			expectedXml.append("<recordInfo>");
				expectedXml.append("<field code=\"BaseID\">000000000000001</field>");
				expectedXml.append("<field code=\"BaseSchema\">WF4:EL_TTM_TTH</field>");
			expectedXml.append("</recordInfo>");
			expectedXml.append("<recordInfo>");
				expectedXml.append("<field code=\"BaseID\">000000000000002</field>");
				expectedXml.append("<field code=\"BaseSchema\">WF4:EL_TTM_TTH</field>");
			expectedXml.append("</recordInfo>");
		expectedXml.append("</opDetail>");
		
		
		//创建实际对象
		QueryDealListOutputModel outputModel = new QueryDealListOutputModel();
		Map<String, Integer> baseCount = new LinkedHashMap<String, Integer>();
		baseCount.put("WF4:EL_TTM_TTH", 19);
		baseCount.put("WF4:EL_TTM_CCH", 3);
		baseCount.put("WF4:EL_UVS_TSK", 8);
		outputModel.setBaseCount(baseCount);
		List<Map<String, String>> baseList = new ArrayList<Map<String,String>>();
		Map<String, String> item1 = new LinkedHashMap<String, String>();
		item1.put("BaseID", "000000000000001");
		item1.put("BaseSchema", "WF4:EL_TTM_TTH");
		baseList.add(item1);
		Map<String, String> item2 = new LinkedHashMap<String, String>();
		item2.put("BaseID", "000000000000002");
		item2.put("BaseSchema", "WF4:EL_TTM_TTH");
		baseList.add(item2);
		outputModel.setBaseList(baseList);
		String actualXml = outputModel.buildXml();
		
		//测试
		assertEquals(expectedXml.toString(), actualXml);
	}
	
	public void testBuildExceptionXml()
	{
		assertEquals("<opDetail><baseInfo><isLegal>0</isLegal></baseInfo></opDetail>", QueryDealListOutputModel.buildExceptionXml());
	}

}
