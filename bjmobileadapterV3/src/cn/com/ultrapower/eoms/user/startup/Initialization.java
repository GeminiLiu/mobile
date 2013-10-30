package cn.com.ultrapower.eoms.user.startup;

import java.rmi.RemoteException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import cn.com.ultrapower.ultrawf.share.constants.Constants;

import com.ultrapower.common.rmi.server.RMIServer;
import com.ultrapower.eoms.mobile.ws.MobileAuthorizeHandle;
import com.ultrapower.eoms.mobile.ws.MobileService;
import com.ultrapower.eoms.mobile.ws.MobileWorkflowHandler;

public class Initialization {

	public  void init()
	{
		//手机运维客户端支持的工单类型 
		try {
			Constants.mobileConfig = new PropertiesConfiguration("mobile.properties");
			Constants.mobileConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		
		//注册手机RMI服务
		try
		{
			RMIServer.createServer(Constants.MOBILE_RMI_SERVER_PORT);
			MobileService serviceWorkflow = new MobileWorkflowHandler();
			MobileService serviceAuthorize = new MobileAuthorizeHandle();
			RMIServer.appendService(Constants.MOBILE_RMI_SERVER_PORT, "WorkflowService", serviceWorkflow);
			RMIServer.appendService(Constants.MOBILE_RMI_SERVER_PORT, "AuthorizeService",serviceAuthorize);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}
