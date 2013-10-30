package com.ultrapower.eoms.mobile.interfaces.assigntree.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

//import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
//import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;
//import com.ultrapower.eoms.common.core.util.WebApplicationManager;
//import com.ultrapower.eoms.common.startup.Initialization;
//import com.ultrapower.eoms.mobile.service.InterfaceService;

public class ServiceTest extends AbstractTransactionalDataSourceSpringContextTests
{
//	private InterfaceService mobileAssignTreeService;
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
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo>");
				inputXml.append("<showCorp>2</showCorp>");
				inputXml.append("<showCenter>2</showCenter>");
				inputXml.append("<showStation>1</showStation>");
				inputXml.append("<showTeam>1</showTeam>");
				inputXml.append("<showPerson>1</showPerson>");
				inputXml.append("<multi>1</multi>");
				inputXml.append("<selectObjs>team</selectObjs>");
				inputXml.append("<cityID>dalian</cityID>");
				inputXml.append("<specialtyID>basestation</specialtyID>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		
//		String outputXml = mobileAssignTreeService.call(inputXml.toString(), "");
//		
//		System.out.println("");
//		System.out.println(outputXml);
	}

//	public InterfaceService getMobileAssignTreeService()
//	{
//		return mobileAssignTreeService;
//	}
//
//	public void setMobileAssignTreeService(@Qualifier("mobileAssignTreeService") InterfaceService mobileAssignTreeService)
//	{
//		this.mobileAssignTreeService = mobileAssignTreeService;
//	}
}
