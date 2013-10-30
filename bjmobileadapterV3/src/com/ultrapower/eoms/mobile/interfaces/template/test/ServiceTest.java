package com.ultrapower.eoms.mobile.interfaces.template.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.ultrapower.eoms.mobile.service.InterfaceService;

public class ServiceTest extends AbstractTransactionalDataSourceSpringContextTests
{
	private InterfaceService mobileTemplateService;
	
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
	
	public void testCall()
	{
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo>");
				inputXml.append("<category>workflow</category>");
				inputXml.append("<version>WF4:EL_AM_PS</version>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		
		String outputXml = mobileTemplateService.call(inputXml.toString(), "");
		
		System.out.println(outputXml);
	}

	public InterfaceService getMobileTemplateService()
	{
		return mobileTemplateService;
	}

	public void setMobileTemplateService(@Qualifier("mobileTemplateService") InterfaceService mobileTemplateService)
	{
		this.mobileTemplateService = mobileTemplateService;
	}

}
