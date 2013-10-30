package cn.com.ultrapower.ultrawf.control.process.baseaction;

import cn.com.ultrapower.system.err.OperationLogFile;


public class BaseWrite_Log {
	
	public static void writeLog(String p_ErrorTitle,int p_Flag,String p_Memo) {
		System.out.println("-----------------------------------------");
		System.out.println(p_ErrorTitle);
		String str_p_Flag = "";
		if (p_Flag == 1)
		{
			str_p_Flag = "错误！";
			OperationLogFile.writeTxt("\r\n"+p_ErrorTitle +" 发生错误");
			OperationLogFile.writeTxt("\r\n信息描述：" + p_Memo);
		}
		else
		{
			str_p_Flag = "成功！";
		}
		System.out.println("信息表示：" + str_p_Flag);
		System.out.println("信息描述：" + p_Memo);
	}
	
	public static void writeExceptionLog(String p_ErrorTitle,Exception p_Exception) {
		System.out.println("-----------------------------------------");
		p_Exception.printStackTrace();
		System.out.println(p_ErrorTitle);
		String str_p_Flag = "";
		str_p_Flag = "错误！";
		OperationLogFile.writeTxt("\r\n"+p_ErrorTitle +" 发生错误");
		OperationLogFile.writeTxt("\r\n信息描述：" + p_Exception.toString());
		System.out.println("\r\n信息表示：" + str_p_Flag);
		System.out.println("信息描述：" + p_Exception.toString());
	}

	public static void writeActionLog(String p_BaseAction,int p_Flag,String p_Memo) {
		System.out.println("-----------------------------------------");
		System.out.println("工单动作：" + p_BaseAction);
		String str_p_Flag = "";
		if (p_Flag == 1)
		{
			str_p_Flag = "错误！";
			OperationLogFile.writeTxt("\r\n工单动作:"+p_BaseAction +" 发生错误");
			OperationLogFile.writeTxt("\r\n信息描述：" + p_Memo);
		}
		else
		{
			str_p_Flag = "成功！";
		}
		System.out.println("信息表示：" + str_p_Flag);
		System.out.println("信息描述：" + p_Memo);
	}
	
	public static void writeActionExceptionLog(String p_BaseAction,Exception p_Exception) {
		
		System.out.println("-----------------------------------------");
		p_Exception.printStackTrace();
		System.out.println("工单动作：" + p_BaseAction);
		String str_p_Flag = "";
		str_p_Flag = "错误！";
		OperationLogFile.writeTxt("\r\n工单动作:"+p_BaseAction +" 发生错误");
		OperationLogFile.writeTxt("\r\n信息描述：" + p_Exception.toString());
		System.out.println("\r\n信息表示：" + str_p_Flag);
		System.out.println("信息描述：" + p_Exception.toString());
	}
}
