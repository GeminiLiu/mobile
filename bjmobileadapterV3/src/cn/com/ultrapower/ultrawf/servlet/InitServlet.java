package cn.com.ultrapower.ultrawf.servlet;

import java.io.File;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import cn.com.ultrapower.uisp.cache.CacheManager;
import cn.com.ultrapower.ultrawf.control.config.BaseQueryXmlConfigManage;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.constants.ConstantsManager;
import cn.com.ultrapower.system.sqlquery.startup.Startup;

public class InitServlet
    extends HttpServlet {
 
private static final long serialVersionUID = 1L;

public void init(ServletConfig config) throws ServletException {
    super.init(config);
    //配置文件信息
    String file = config.getInitParameter("configPath");
    String filePath = this.getServletContext().getRealPath(file);
    Constants.filePath = filePath;
    
    //静态变量初始化
    System.out.println("--------------------------------------------------------");
    System.out.println("--流程引擎初始化信息（开始）");
    ConstantsManager constantsManager = new ConstantsManager(filePath);
    constantsManager.getConstantInstance();
    System.out.println("    --已成功初始化");
    System.out.println("--流程引擎初始化信息（结束）");
    System.out.println("--------------------------------------------------------");
    
    System.out.println("--SQL初始化信息（开始）");
    Startup startup=new Startup();
    startup.init(this.getServletContext().getRealPath(""));
    BaseQueryXmlConfigManage xmlManage = new BaseQueryXmlConfigManage();
    xmlManage.init();
    System.out.println("--SQL初始化信息（结束）");

    CacheManager.MAINPATH = filePath + File.separator + "config";
    try
    {
	    CacheManager.refreshCache();
    }
    catch (Exception e)
    {
		e.printStackTrace();
	}
}
}
