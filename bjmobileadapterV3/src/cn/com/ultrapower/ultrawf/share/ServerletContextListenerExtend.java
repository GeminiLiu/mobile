package cn.com.ultrapower.ultrawf.share;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//import cn.com.ultrapower.ultrawf.control.config.DBPoolManage;

public class ServerletContextListenerExtend implements ServletContextListener{
	
    /**
     * 退出Tomcat
     */	
    public void contextDestroyed(ServletContextEvent ctxEvt)
    {
		//删除数据库连接
		//DBPoolManage m_DBPoolManage=new DBPoolManage();
		try{
			//m_DBPoolManage.unDbPool();
			System.out.println("数库连接关闭成功(200)!");
		}catch(Exception ex)
		{
			System.out.print("数库连接关闭失败(200)");
			System.out.println(ex.getMessage());
		}    	
    }


	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	
}
