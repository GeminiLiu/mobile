/*
 * @(#)Server.java 2006-09-11
 * 
 * Copyright 2006 Ultrapower,Inc. All rights revserved. 
 * 版权2006归北京神州泰岳股份有限公司所有。
 */

package cn.com.ultrapower.eoms.user.startup;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.ultrapower.eoms.common.cfg.ConfigKeys;
import com.ultrapower.eoms.common.cfg.ConfigLoader;
import com.ultrapower.eoms.common.util.WebApplicationManager;

import cn.com.ultrapower.eoms.user.startup.Initialization;

import cn.com.ultrapower.eoms.user.comm.function.ConfigContents;
import cn.com.ultrapower.eoms.user.comm.function.Function;

/**
 * 时间：2006-09-11
 * 
 * @author wuwenlong
 * 
 *         应用程序入口类，需要servlet2.3的支持。
 */
public final class Server implements ServletContextListener {
	static final Log log = LogFactory.getLog(Server.class);

	/**
	 * 时间：2006-09-11
	 * 
	 * @Author：wuwenlong
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent ctxEvt) {
		ServletContext ctx = ctxEvt.getServletContext();
		String webroot = ctx.getRealPath("/");
		ConfigKeys.ROOT = webroot;
		ConfigContents.ROOT = webroot;
		String newpath = Function.getProjectPath();
		String log4jFile = newpath + "WEB-INF" + File.separator + "cfg"
				+ File.separator + "log4j.properties";
		// String log4jFile = Path.CONF + "log4j.properties";
		String log4jHome = newpath + "WEB-INF" + File.separator + "logs";
		// String log4jHome = Path.CONF + "logs";
		System.setProperty("log_home", log4jHome);
		PropertyConfigurator.configure(log4jFile);
		try {
			ConfigLoader.initConfig();
			ConfigLoader.updateHibernateMappingFile();
			ConfigLoader.startCacheEngine();
		} catch (Exception e) {
			log.error("加载配置文件失败");
		}
		//初始化数据 fanying
		ServletContext servletContext= ctxEvt.getServletContext();
		WebApplicationManager.servletContext = servletContext;
		new Initialization().init();
	}

	/**
	 * 时间：2006-09-11
	 * 
	 * @Author：wuwenlong
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent ctxEvt) {
		log.info("应用程序退出");
		// 删除数据库连接
		ConfigLoader.stopCacheEngine();
		System.gc();
		log.info("回收内存");
	}
}