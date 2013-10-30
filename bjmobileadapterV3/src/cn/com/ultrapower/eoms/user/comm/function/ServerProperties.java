package cn.com.ultrapower.eoms.user.comm.function;

import java.io.IOException;
import java.util.Properties;
import cn.com.ultrapower.eoms.user.comm.function.Log;
public class ServerProperties {
	public static String url;
	public static String user;
	public static String password;
	public static int serverport;
	public static Properties props = new Properties(); 
	/**
	 * 日期 2006-10-13
	 * 姓名 shigang
	 * author 得到Properties数据库库里的值
	 * @param args
	 * @return void
	 * @throws IOException 
	 */
	public ServerProperties(){
		try{
			props.load(ServerProperties.class.getClassLoader().getResourceAsStream("Db.properties"));
//			props.load(this.getClass().getResourceAsStream("Db.properties"));
			url=props.getProperty("driverurl"); 
			user=props.getProperty("user"); 
	    	password=props.getProperty("password"); 
	    	serverport=Integer.parseInt(props.getProperty("serverport"));
	    	System.out.println("driverurl"+url+"password"+password);
		}catch(Exception e){
			System.out.println(e);
			Log.writelog("301",e.toString());
		}
	}
	
	

}
