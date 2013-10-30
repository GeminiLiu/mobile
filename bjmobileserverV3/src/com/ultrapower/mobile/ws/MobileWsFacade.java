package com.ultrapower.mobile.ws;

import javax.jws.WebService;

@WebService
public interface MobileWsFacade {

	/**
	 * 手机端web service统一调用方法，通过xml内容中的不同值调用不同逻辑
	 * @param xmlParams xml参数
	 * @return 返回值xml结果
	 * @throws Exception
	 */
	public String invoke(String xmlParams) throws Exception;
}
