package com.ultrapower.mobile.service;


/**
 * 业务操作接口
 * @author Administrator
 *
 */
public interface BizFacade {

	/**
	 * 业务调用方法，以xml内容为参数内部通过xml的不同值调用不同逻辑
	 * @param paramXml xml参数
	 * @param ip ip地址
	 * @param port 端口
	 * @return xml返回值
	 * @throws Exception
	 */
	public String invoke(String paramXml, String ip, int port) throws Exception;
}
