package com.ultrapower.eoms.mobile.ws;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.jws.WebService;

import com.ultrapower.eoms.common.util.WebApplicationManager;
import com.ultrapower.eoms.mobile.service.InterfaceService;

/**
 * 手机服务端WS接口
 * @author Haoyuan
 */
@WebService(endpointInterface = "com.ultrapower.eoms.mobile.ws.MobileService")
public class MobileWorkflowHandler extends UnicastRemoteObject implements MobileService
{

	public MobileWorkflowHandler() throws RemoteException
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 接口方法，通过serviceCode判断调用的具体服务实现类，xml为规范中规定的xml格式，返回格式也为xml
	 * @param serviceCode 调用的实际服务标识
	 * @param xml 调用服务时的参数xml
	 * @return 服务返回的数据xml
	 * @throws Exception
	 */
	public String invoke(String serviceCode, String xml, String fileList) throws Exception
	{
		String output = "";
		InterfaceService service = null;
		if(serviceCode.equals("G001"))
		{
			service = (InterfaceService) WebApplicationManager.getBean("queryDealListService");
		}
		else if(serviceCode.equals("G002"))
		{
			service = (InterfaceService) WebApplicationManager.getBean("openFormService");
		}
		else if(serviceCode.equals("G003"))
		{
			service = (InterfaceService) WebApplicationManager.getBean("initActionService");
		}
		else if(serviceCode.equals("G004"))
		{
			service = (InterfaceService) WebApplicationManager.getBean("formCommitService");
		}
		else if(serviceCode.equals("G005"))
		{
			service = (InterfaceService) WebApplicationManager.getBean("dealDescService");
		}
		else if(serviceCode.equals("G006"))
		{
			service = (InterfaceService) WebApplicationManager.getBean("syncAttachService");
		}
		else if(serviceCode.equals("G008"))
		{
			service = (InterfaceService) WebApplicationManager.getBean("downAttachService");
		}
		if(service != null)
		{
			output = service.call(xml, fileList);
		}
		
		return output;
	}

	public void test() throws RemoteException
	{
		
	}
}
