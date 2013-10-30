package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import cn.com.ultrapower.eoms.user.userinterface.cm.SmsOrderBean;
import cn.com.ultrapower.eoms.user.userinterface.cm.SmsOrderInsert;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
public class FileSmsOrder
{

	/**
	 * 日期 2007-1-20
	 * @author xuquanxing 
	 * @param args void
	 */
	public static void main(String[] args)
	{
		//FileSmsOrder filemss = new FileSmsOrder();
		//filemss.fileSmsOrder("11","nihaoa");
		System.out.println("ok");
	}
	private static GetFormTableName getformtablename = new GetFormTableName();
	private static String sysusertable = "";
	static
	{
		sysusertable = getformtablename.GetFormName("RemedyTpeople");
	}
	/**
	 * 日期 2007-1-22
	 * @author xuquanxing 
	 * @param sourceid
	 * @param smscontent void
	 */
	public void  fileSmsOrder(String sourceid,String smscontent)
	{
		//如果传入的字符串为空
		if(sourceid.equals("")||String.valueOf(sourceid).equals("null"))
		{
			return;
		}
		if(smscontent.equals("")||String.valueOf(smscontent).equals("null"))
		{
			smscontent = "";
		}else
		{
			String foldercnname = "";
			foldercnname   = this.folderCnName(sourceid);//将与who ,time, foldercnname,fielname拼成内容发给用户
		//	smscontent = smscontent.replaceAll("${folder_name}",foldercnname);
			smscontent = this.strDeal(smscontent,foldercnname);
		}
		//T230对应的sysuser表,630000001用户登录名 630000008手机号
		String sql = "select b.c630000001,b.c630000008 from  file_subscribe a,"+sysusertable+"  b where b.c630000012='0' and a.filesms_userid =b.c1 and a.filesms_flag=1 and a.filesms_sourceid ='"+sourceid+"'";
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet res		= null;
		stm	= dataBase.GetStatement();
		res	= dataBase.executeResultSet(stm,sql);
		SmsOrderInsert  smsinsrt = new SmsOrderInsert();
		try
		{
			while(res.next())
			{
				try
				{
					String loginname   = res.getString("c630000001");//用户登录名
					String phonenumber = res.getString("c630000008");//用户手机号
					SmsOrderBean smsbean = new SmsOrderBean();
					smsbean.setSmsmonitor_content(smscontent);
					smsbean.setSmsmonitor_goal(phonenumber);
					smsbean.setSmsmonitor_sendto("");
					smsbean.setSmsmonitor_type("0");
					smsinsrt.insertSms(smsbean);
					//调用短信接口，将该记录插入smsorder表中
				}catch(Exception e)
				{
					System.out.println("发生异常");
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
	}
	
	private String  folderCnName(String sourceid)
	{
		//如果传入的字符串为空
		if(sourceid.equals("")||String.valueOf(sourceid).equals("null"))
		{
			return "";
		}
		String sql = "select s.source_cnname from sourceconfig s where s.source_id="+sourceid;
		String foldercnname    = "";
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet res		= null;
		stm	= dataBase.GetStatement();
		res	= dataBase.executeResultSet(stm,sql);
		try
		{
			while(res.next())
			{
				try
				{
				    foldercnname  = res.getString("source_cnname");//取得资源所对应的中文名
					//调用短信接口，将该记录插入smsorder表中
				}catch(Exception e)
				{
					System.out.println("发生异常");
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
		return foldercnname;
	}
	
	public String  strDeal(String str,String foldname)
	{
	    int s = str.indexOf("$");
	    int s1 = str.indexOf("}");
		String firststr = str.substring(0,s);
		String middstr  = str.substring(s1+1);
		String laststr  = firststr+foldname+middstr;
		return laststr;
   }

}
