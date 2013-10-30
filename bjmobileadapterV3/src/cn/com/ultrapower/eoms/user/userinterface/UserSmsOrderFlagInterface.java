package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

/**
 * 用户短信是否生效修改接口
 * @author fangqun
 * @CreatTime 2008-5-21
 * 
 */
public class UserSmsOrderFlagInterface {

	static final Logger logger = (Logger) Logger.getLogger(UserSmsOrderFlagInterface.class);
	GetFormTableName getTableProperty	= new GetFormTableName();
	String usertablename = "";
		
	public UserSmsOrderFlagInterface(){
		
		try
		{
			usertablename = getTableProperty.GetFormName("RemedyTpeople");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
		
	}
	
	/**
	 * 根据用户id修改SysUser表中的用户短信是否生效字段
	 * @param userid
	 * @param smsorderflag:0否,1是
	 * @return
	 */
	public boolean changeFlagbyUserId(String userid, String smsorderflag)
	{
		
		IDataBase dataBase 	= null;
		Statement stm		= null;
		int issuccess		= 0;
	  	
	  	if(smsorderflag.equals("0") || smsorderflag.equals("1"))
	  	{	  		
	  		try
	  		{
	  			dataBase = DataBaseFactory.createDataBaseClassFromProp();
		        stm	= dataBase.GetStatement();
		        String sql = " update "+usertablename+" set c639900004='"+smsorderflag+"' where c1="+userid;
		        
		        System.out.println(sql);
		        issuccess = stm.executeUpdate(sql);
		        if(issuccess>0)
		        {
		        	System.out.println("修改成功");
		        	return true;
		        }
		        else
		        {
		        	System.out.println("修改失败");
		        	return false;
		        }

	  		}
	  		catch(Exception e)
	  		{
	  			e.printStackTrace();
	  			return false;
	  		}finally
	  		{
	  			Function.closeDataBaseSource(null, stm, dataBase);
	  		}
	  		
	  	}
	  	else
	  	{
	  		System.out.println("标示格式不正确，应为0或1");
	  		return false;
	  	}

	}
	
	/**
	 * 根据用户登录名修改SysUser表中的用户短信是否生效字段
	 * @param userloginname
	 * @param smsorderflag:0否,1是
	 * @return
	 */
    public boolean changeFlagbyUserLoginname(String userloginname, String smsorderflag){
		
    	IDataBase dataBase 	= null;
		Statement stm		= null;
		int issuccess		= 0;
	  	
	  	if(smsorderflag.equals("0") || smsorderflag.equals("1"))
	  	{	  		
	  		try
	  		{
	  			dataBase = DataBaseFactory.createDataBaseClassFromProp();
		        stm	= dataBase.GetStatement();
		        String sql = " update "+usertablename+" set c639900004='"+smsorderflag+"' where c630000001='"+userloginname+"'";
		        
		        System.out.println(sql);
		        issuccess = stm.executeUpdate(sql);
		        if(issuccess>0)
		        {
		        	System.out.println("修改成功");
		        	return true;
		        }
		        else
		        {
		        	System.out.println("修改失败");
		        	return false;
		        }

	  		}
	  		catch(Exception e)
	  		{
	  			e.printStackTrace();
	  			return false;
	  		}finally
	  		{
	  			Function.closeDataBaseSource(null, stm, dataBase);
	  		}
	  		
	  	}
	  	else
	  	{
	  		System.out.println("标示格式不正确，应为0或1");
	  		return false;
	  	}
	}
    
    public static void main(String[] args){
    	
    	UserSmsOrderFlagInterface test = new UserSmsOrderFlagInterface();
    	//test.changeFlagbyUserId("000000000000627", "1");
    	test.changeFlagbyUserLoginname("bd_wangq", "0");
    }
}
