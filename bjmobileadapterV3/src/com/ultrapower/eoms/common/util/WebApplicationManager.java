package com.ultrapower.eoms.common.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 通过webApplicationContext的方式获取注入的spring对象
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 22, 2010 6:32:37 PM
 */
public class WebApplicationManager {

	public static WebApplicationContext webApplicationContext; 
	public static ServletContext servletContext;
	public static ApplicationContext applicationContext;
	
	/**
	 * 获取注入的对象实例
	 * @param beanid
	 * @return
	 */
	public static Object getBean(String beanid){
		if(webApplicationContext==null && servletContext!=null)
			webApplicationContext= WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if(webApplicationContext!=null)
			return webApplicationContext.getBean(beanid);
		if(applicationContext != null)
		{
			return applicationContext.getBean(beanid);
		}
		return null;
	}
}
