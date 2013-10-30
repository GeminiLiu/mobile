package com.ultrapower.mobile.common.utils;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.ultrapower.biz.ItSysWsFacade;

public class WSUtil {

	public static ItSysWsFacade getItSysWsFacade(String itSysCode) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(com.ultrapower.biz.ItSysWsFacade.class);
		factory.setAddress(ItSysConfigUtil.getItSysAddress(itSysCode) + "ws/eoms4WsFacade");
		Object create = factory.create();
		return (ItSysWsFacade) create;
	}
}
