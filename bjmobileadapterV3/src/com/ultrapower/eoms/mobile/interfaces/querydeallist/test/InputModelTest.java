package com.ultrapower.eoms.mobile.interfaces.querydeallist.test;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;
import com.ultrapower.eoms.mobile.interfaces.querydeallist.model.QueryDealListInputModel;

import junit.framework.TestCase;

public class InputModelTest extends TestCase
{

	public void testBuildModel()
	{
		//创建期望对象
		QueryDealListInputModel expectedModel = new QueryDealListInputModel();
		expectedModel.setUserName("Demo");
		expectedModel.setIsWait(1);
		expectedModel.setCondition("紧急");
		expectedModel.setCategory("WF4:EL_TTM_CCH");
		expectedModel.setFields("BaseID,BaseSchema,BaseSN,BaseStatus,TaskID,BaseSummary,CCH_ComplainType,CCH_IncidentStartTime");
		expectedModel.setPageNum(1);
		expectedModel.setPageSize(10);

		//创建实际对象
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
				inputXml.append("<password>Lfn1NnqGG0KCCvX6uFWOBg==</password>");
				inputXml.append("<machineCode>AndroidPhone</machineCode>");
				inputXml.append("<simNum>13900000000</simNum>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo>");
				inputXml.append("<isWait>1</isWait>");
				inputXml.append("<queryCondition>紧急</queryCondition>");
				inputXml.append("<category>WF4:EL_TTM_CCH</category>");
				inputXml.append("<queryFields>BaseID,BaseSchema,BaseSN,BaseStatus,TaskID,BaseSummary,CCH_ComplainType,CCH_IncidentStartTime</queryFields>");
				inputXml.append("<pageNum>1</pageNum>");
				inputXml.append("<pageSize>10</pageSize>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		
		QueryDealListInputModel actualModel = new QueryDealListInputModel();
		try
		{
			actualModel.buildModel(inputXml.toString());
		}
		catch (InvalidXmlFormatException e)
		{
			fail(e.getMessage());
		}
		
		//测试
		assertEquals(expectedModel.getUserName(), actualModel.getUserName());
		assertEquals(expectedModel.getIsWait(), actualModel.getIsWait());
		assertEquals(expectedModel.getCondition(), actualModel.getCondition());
		assertEquals(expectedModel.getCategory(), actualModel.getCategory());
		assertEquals(expectedModel.getFields(), actualModel.getFields());
		assertEquals(expectedModel.getPageNum(), actualModel.getPageNum());
		assertEquals(expectedModel.getPageSize(), actualModel.getPageSize());
	}

}
