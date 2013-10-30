package com.ultrapower.eoms.mobile.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ultrapower.common.rmi.RMIRemoteService;

/**
 * 手机服务端WS接口
 * @author Haoyuan
 */
@WebService
public interface MobileService extends RMIRemoteService
{
	/**
	 * 接口方法，通过serviceCode判断调用的具体服务实现类，xml为规范中规定的xml格式，返回格式也为xml
	 * @param serviceCode 调用的实际服务标识
	 * @param xml 调用服务时的参数xml
	 * @return 服务返回的数据xml
	 * @throws Exception
	 */
	public String invoke(
			@WebParam(name="serviceCode") String serviceCode,
			@WebParam(name="xml") String xml,
			@WebParam(name="fileList") String fileList
		) throws Exception;
}
