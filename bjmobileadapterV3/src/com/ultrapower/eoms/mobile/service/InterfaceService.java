package com.ultrapower.eoms.mobile.service;

/**
 * 具体的手机服务接口，每一个接口使用一个类
 * @author Haoyuan
 */
public interface InterfaceService
{
	/**
	 * 接口服务的调用方法，参数和返回的格式均为xml，格式由规范制定
	 * @param xml 服务调用时的参数xml
	 * @return 返回的xml
	 */
	public String call(String xml, String fileList);
}
