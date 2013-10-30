package com.ultrapower.eoms.mobile.interfaces.querydeallist.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.ultrapower.eoms.mobile.service.InterfaceService;

public class ServiceTest extends AbstractTransactionalDataSourceSpringContextTests
{
	private InterfaceService queryDealListService;

	protected String[] getConfigLocations()
	{
		String[] config = new String[] { "/spring/applicationContext.xml", 
										 "/spring/applicationContext-allbean-hibernate.xml",
										 "/spring/common.xml",
										 "/spring/ultrasm.xml",
										 "/spring/ultraworkflow.xml",
										 "/spring/mobile.xml"};
		return config;

	}
	
//	public void onSetUp() 
//	{
//		if(WebApplicationManager.applicationContext == null)
//		{
//			WebApplicationManager.applicationContext = applicationContext;
//			Initialization init = new Initialization();
//			init.init();
//			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath(); //System.getProperty("user.dir");
//			RConstants.xmlPath = path + "sqlconfig";
//			StartUp.loadFile(RConstants.xmlPath);
//		}
//	}
	
	public void testWaitingDealCall()
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
				expectedXml.append("<field code=\"BaseID\">000000000043767</field>");
				expectedXml.append("<field code=\"BaseSchema\">WF4:EL_TTM_CCH</field>");
				expectedXml.append("<field code=\"BaseSN\">ID-056-20120903-00001</field>");
				expectedXml.append("<field code=\"BaseStatus\">一级处理中</field>");
				expectedXml.append("<field code=\"TaskID\">504471450526332</field>");
				expectedXml.append("<field code=\"BaseSummary\">qweqweqwe</field>");
				expectedXml.append("<field code=\"CCH_ComplainType\">业务响应.业务与服务提醒.催缴及话费短信提醒</field>");
				expectedXml.append("<field code=\"CCH_IncidentStartTime\">1346601600</field>");
			expectedXml.append("</recordInfo>");
			expectedXml.append("<recordInfo>");
				expectedXml.append("<field code=\"BaseID\">000000000043765</field>");
				expectedXml.append("<field code=\"BaseSchema\">WF4:EL_TTM_CCH</field>");
				expectedXml.append("<field code=\"BaseSN\">ID-056-20120413-00001</field>");
				expectedXml.append("<field code=\"BaseStatus\">待归档</field>");
				expectedXml.append("<field code=\"TaskID\">4fb4936d0520104</field>");
				expectedXml.append("<field code=\"BaseSummary\">123123123</field>");
				expectedXml.append("<field code=\"CCH_ComplainType\">业务响应.业务与服务提醒.催缴及话费短信提醒</field>");
				expectedXml.append("<field code=\"CCH_IncidentStartTime\">1334298020</field>");
			expectedXml.append("</recordInfo>");
		expectedXml.append("</opDetail>");
		
		//测试
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo>");
				inputXml.append("<isWait>1</isWait>");
				inputXml.append("<queryCondition>紧急</queryCondition>");
				inputXml.append("<category>WF4:EL_TTM_CCH</category>");
				inputXml.append("<queryFields>BaseID,BaseSchema,BaseSN,BaseStatus,TaskID,BaseSummary,CCH_ComplainType,CCH_IncidentStartTime</queryFields>");
				inputXml.append("<pageNum>1</pageNum>");
				inputXml.append("<pageSize>2</pageSize>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		
		String actualXml = queryDealListService.call(inputXml.toString(), "");
		
		assertEquals(expectedXml.toString(), actualXml);
	}

	public void testDealedCall()
	{
		//创建期望对象
		StringBuilder expectedXml = new StringBuilder();
		expectedXml.append("<opDetail>");
			expectedXml.append("<baseInfo>");
				expectedXml.append("<isLegal>1</isLegal>");
				expectedXml.append("<baseCount>");
					expectedXml.append("<caetgory type=\"WF4:EL_TTM_TTH\">19</category>");
					expectedXml.append("<caetgory type=\"WF4:EL_TTM_CCH\">3</category>");
					expectedXml.append("<caetgory type=\"WF4:EL_UVS_TSK\">8</category>");
				expectedXml.append("</baseCount>");
			expectedXml.append("</baseInfo>");
			expectedXml.append("<recordInfo>");
				expectedXml.append("<field code=\"BaseID\">000000000043768</field>");
				expectedXml.append("<field code=\"BaseSchema\">WF4:EL_TTM_CCH</field>");
				expectedXml.append("<field code=\"BaseSN\">ID-056-20121213-00001</field>");
				expectedXml.append("<field code=\"BaseStatus\">一级处理中</field>");
				expectedXml.append("<field code=\"BaseSummary\">test_wf</field>");
				expectedXml.append("<field code=\"CCH_ComplainType\">服务质量.10086热线.业务办理差错</field>");
			expectedXml.append("</recordInfo>");
			expectedXml.append("<recordInfo>");
				expectedXml.append("<field code=\"BaseID\">000000000043767</field>");
				expectedXml.append("<field code=\"BaseSchema\">WF4:EL_TTM_CCH</field>");
				expectedXml.append("<field code=\"BaseSN\">ID-056-20120903-00001</field>");
				expectedXml.append("<field code=\"BaseStatus\">一级处理中</field>");
				expectedXml.append("<field code=\"BaseSummary\">qweqweqwe</field>");
				expectedXml.append("<field code=\"CCH_ComplainType\">业务响应.业务与服务提醒.催缴及话费短信提醒</field>");
			expectedXml.append("</recordInfo>");
		expectedXml.append("</opDetail>");
		
		//测试
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo>");
				inputXml.append("<isWait>0</isWait>");
				inputXml.append("<queryCondition>紧急</queryCondition>");
				inputXml.append("<category>WF4:EL_TTM_CCH</category>");
				inputXml.append("<queryFields>BaseID,BaseSchema,BaseSN,BaseStatus,BaseSummary,CCH_ComplainType</queryFields>");
				inputXml.append("<pageNum>1</pageNum>");
				inputXml.append("<pageSize>2</pageSize>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		
		String actualXml = queryDealListService.call(inputXml.toString(), "");
		
		assertEquals(expectedXml.toString(), actualXml);
	}
	
	public InterfaceService getQueryDealListService()
	{
		return queryDealListService;
	}

	public void setQueryDealListService(@Qualifier("queryDealListService") InterfaceService queryDealListService)
	{
		this.queryDealListService = queryDealListService;
	}
}
