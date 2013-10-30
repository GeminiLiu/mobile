package com.ultrapower.eoms.mobile.interfaces.openform.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

//import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
//import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;
//import com.ultrapower.eoms.common.core.util.WebApplicationManager;
//import com.ultrapower.eoms.common.startup.Initialization;
import com.ultrapower.eoms.mobile.service.InterfaceService;

public class ServiceTest extends AbstractTransactionalDataSourceSpringContextTests
{
	private InterfaceService openFormService;
	
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
//	
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
				inputXml.append("<baseId>000000000043767</baseId>");
				inputXml.append("<category>WF4:EL_TTM_CCH</category>");
				inputXml.append("<taskId>504471450526332</taskId>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		String outputXml = openFormService.call(inputXml.toString(), "");
		System.out.println(outputXml);
	}

	public InterfaceService getOpenFormService()
	{
		return openFormService;
	}

	public void setOpenFormService(@Qualifier("openFormService") InterfaceService openFormService)
	{
		this.openFormService = openFormService;
	}

}
