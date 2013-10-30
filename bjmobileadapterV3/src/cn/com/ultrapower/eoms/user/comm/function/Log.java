package cn.com.ultrapower.eoms.user.comm.function;

public class Log {
	/**
	 * 日期 2006-10-13
	 * 姓名 shigang
	 * author 处理异常
	 * @param args
	 * @return void
	 * logid是id号，loginfo是传过来的参数
	 */
	public static void writelog(String logId,String logInfo){
		System.out.print("logId"+logId+"logInfo"+logInfo);
	}
	public static void writeinfo(String logId,String logInfo){
		System.out.print("logId"+logId+"logInfo"+logInfo);
	}
}
