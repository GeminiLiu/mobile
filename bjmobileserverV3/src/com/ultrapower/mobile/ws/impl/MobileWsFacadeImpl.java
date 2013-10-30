package com.ultrapower.mobile.ws.impl;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.mobile.service.BizFacade;
import com.ultrapower.mobile.ws.MobileWsFacade;

@WebService(endpointInterface = "com.ultrapower.mobile.ws.MobileWsFacade")
public class MobileWsFacadeImpl implements MobileWsFacade {
	
	private static Logger log = LoggerFactory.getLogger(MobileWsFacadeImpl.class);

	public String invoke(String xmlParams) throws Exception {
		BizFacade bizFacade = (BizFacade) WebApplicationManager.getBean("bizFacade");
		return bizFacade.invoke(xmlParams, null, 0);
	}


}
