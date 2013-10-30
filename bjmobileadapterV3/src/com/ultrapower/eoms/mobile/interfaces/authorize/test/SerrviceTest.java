package com.ultrapower.eoms.mobile.interfaces.authorize.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

//import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
//import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;
//import com.ultrapower.eoms.common.core.util.WebApplicationManager;
//import com.ultrapower.eoms.common.startup.Initialization;
import com.ultrapower.eoms.mobile.service.InterfaceService;

public class SerrviceTest extends AbstractTransactionalDataSourceSpringContextTests
{
	private InterfaceService mobileAuthorizeService;
	protected String[] getConfigLocations()
	{
		String[] config = new String[] { "/spring/applicationContext.xml", 
										 "/spring/applicationContext-allbean-hibernate.xml",
										 "/spring/common.xml",
										 "/spring/ultrasm.xml",
										 "/spring/portal.xml",
										 "/spring/dwcp.xml",
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

	public void testCall()
	{
		//期望值
		StringBuilder expectedXml = new StringBuilder();
		expectedXml.append("<opDetail>");
			expectedXml.append("<baseInfo>");
				expectedXml.append("<isLegal>1</isLegal>");
			expectedXml.append("</baseInfo>");
			expectedXml.append("<recordInfo>");
				expectedXml.append("<category type=\"workflow\">");
					expectedXml.append("<ver type=\"WF4:EL_AM_AT\" text=\"代维任务工单\">WF4_EL_AM_AT_20130308</ver>");
					expectedXml.append("<ver type=\"WF4:EL_AM_TTH\" text=\"代维故障处理工单\">WF4_EL_AM_TTH_20130308</ver>");
					expectedXml.append("<ver type=\"WF4:EL_AM_PS\" text=\"发电保障工单\">WF4_EL_AM_PS_20130307</ver>");
				expectedXml.append("</category>");
			expectedXml.append("</recordInfo>");
		expectedXml.append("</opDetail>");
		
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
				inputXml.append("<password>BjjDFuU0ges==</password>");
				inputXml.append("<machineCode>AndroidPhone</machineCode>");
				inputXml.append("<simNum>13900000000</simNum>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo />");
		inputXml.append("</opDetail>");
		String outputXml = mobileAuthorizeService.call(inputXml.toString(), "");
		
		assertEquals(expectedXml.toString(), outputXml);
	}

	public InterfaceService getMobileAuthorizeService()
	{
		return mobileAuthorizeService;
	}

	public void setMobileAuthorizeService(@Qualifier("mobileAuthorizeService") InterfaceService mobileAuthorizeService)
	{
		this.mobileAuthorizeService = mobileAuthorizeService;
	}

}
