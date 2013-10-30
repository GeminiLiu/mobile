package com.ultrapower.eoms.mobile.utils;

import com.ultrapower.common.rmi.client.RMIClient;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.mobile.ws.MobileService;

public class MobileUtil
{
	public static MobileService getItSysWsFacade(String itSysCode) {
		/*
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(MobileService.class);
		factory.setAddress(ItSysConfigUtil.getItSysAddress(itSysCode) + "ws/" +itSysCode +"Service");
		Object create = factory.create();
		return (MobileService) create;
		*/
		MobileService service = null;
		String[] addressStr = PropertiesUtils.getProperty("mobile." + itSysCode + ".address").split(",");
		try 
		{
			String ip = addressStr[0];
			String name = addressStr[2];
			int port = NumberUtils.formatToInt(addressStr[1]);
			service = new RMIClient<MobileService>().getRemoteService(ip, port, name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return service;
	}
}
