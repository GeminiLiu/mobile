package cn.com.ultrapower.eoms.user.userinterface;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserSourceGrandPram;

public class UserSourceGrandInterface
{
	static final Logger logger = (Logger) Logger.getLogger(UserSourceGrandInterface.class);
	/**
	 * 用户对某一资源是否有权限
	 * 日期 2007-2-1
	 * @author wangyanguang
	 */

	public boolean getUserGrand(UserSourceGrandPram pramInfo)
	{
		if(pramInfo!=null)
		{	
			String userLoginName = Function.nullString(pramInfo.getUserLoginName());
			String sourcename	 = Function.nullString(pramInfo.getSource_name());
			String sourceid		 = Function.nullString(pramInfo.getSource_id());
			if(!userLoginName.equals("")&&(!sourcename.equals("")||!sourceid.equals("")))
			{
				UserSourceGrandInterfaceSQL usergrandsql = new UserSourceGrandInterfaceSQL();
				boolean bl = usergrandsql.getUserGrandSQL(pramInfo);
				return  bl;
			}
			else
			{
				logger.error("用户名与资源名不能为空！");
				return false;
			}
		}else
		{
			logger.error("用户名与资源名不能为空！");
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		UserSourceGrandPram usrpram = new UserSourceGrandPram();
		usrpram.setSource_id("1");
		usrpram.setSource_name("systemmanage");
		usrpram.setUserLoginName("wangyanguang");
		usrpram.setActionValue("2,1000");
		UserSourceGrandInterface usrinterface = new UserSourceGrandInterface();
		System.out.println(usrinterface.getUserGrand(usrpram));
		
	}
}
