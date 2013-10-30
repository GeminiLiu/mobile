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
public class MobileAuthorizeHandle extends UnicastRemoteObject implements MobileService
{

	public MobileAuthorizeHandle() throws RemoteException
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public String invoke(String serviceCode, String xml, String fileList) throws Exception
	{
		String output = "";
		InterfaceService service = null;
		if(serviceCode.equals("L001"))
		{
			service = (InterfaceService) WebApplicationManager.getBean("mobileAuthorizeService");
		}else if(serviceCode.equals("L002")){
			service = (InterfaceService) WebApplicationManager.getBean("mobileTemplateService");
		}else if(serviceCode.equals("T001")){
			service = (InterfaceService) WebApplicationManager.getBean("mobileAssignTreeService");
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
