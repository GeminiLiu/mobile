package cn.com.ultrapower.eoms.user.smsexport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class ExportSms
{

	/**
	 * 日期 2007-6-9
	 * @author xuquanxing 
	 * @param args void
	 */
	public static void main(String[] args)
	{
		ExportSms sms =  new ExportSms();
		//sms.exportDeal();

	}
	
	
	public int exportDateFromSmsmonitor()
	{
//		实例化一个类型为接口IDataBase类型的工厂类
        IDataBase     dataBase	    = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stmt		    = null;
		ResultSet     res		    = null;
		String        sqlstr        = "";
		int           count         = 0;
		StringBuffer  buf           = new StringBuffer();
		stmt	                    = dataBase.GetStatement();
		String        id            = "";
		String        currtime      = String.valueOf(DateOp.getLastMonth()/1000);
		System.out.println("一个月前的时间为="+currtime);
		String        sql           = "INSERT INTO SMSMONITORBACK (SELECT SMSMONITOR_ID ,SMSMONITOR_CONTENT,SMSMONITOR_TABLENAME,SMSMONITOR_TYPE,SMSMONITOR_GOAL,SMSMONITOR_SENDFLAG,SMSMONITOR_SENDNUM,SMSMONITOR_MAXNUM,SMSMONITOR_EXPFLAG,SMSMONITOR_SENDTIME,SMSMONITOR_MEMO,SMSMONITOR_MEMO1,SMSMONITOR_SENTO " +
				                      " FROM  SMSMONITOR WHERE SMSMONITOR_SENDTIME<'"+currtime+"')";
		List          list          = new ArrayList(1000000);
		System.out.println("sqlssssss="+sql);
		try
		{
			count  = dataBase.executeNonQuery(stmt,sql);
		}finally
		{
			Function.closeDataBaseSource(res,stmt,dataBase);
		}
      return count;
	}
 	
	public  int deleteSms()
	{
//		实例化一个类型为接口IDataBase类型的工厂类
        IDataBase     dataBase	    = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stmt		    = null;
		ResultSet     res		    = null;
		String        sqlstr        = "";
		boolean       issuc         = false;
		int           count         = 0;
		StringBuffer  buf           = new StringBuffer();
		stmt	                    = dataBase.GetStatement();
		String smsmonitor_id        ;
		String        currtime      = String.valueOf(DateOp.getLastTwoDay()/1000);
	    String     sql              = "DELETE FROM SMSMONITOR  S WHERE S.SMSMONITOR_SENDTIME<'"+currtime+"'";
	    count                       = dataBase.executeNonQuery(stmt,sql);
	    return count;
	    

	}
}
