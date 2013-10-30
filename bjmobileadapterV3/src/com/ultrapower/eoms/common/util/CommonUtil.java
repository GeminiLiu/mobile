/**
 * Copyright (c) 2007 神州泰岳服务管理事业部应用组
 * All rights reserved.
 *
 * 文件名称: CommonUtil.java
 * 文件标示: CommonUtil.java
 * 摘   要:
 * 
 * 当前版本：1.0
 * 作   者:yechanglun
 * 完成日期: 2008-09-16
 */
package com.ultrapower.eoms.common.util;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.UUID;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

public class CommonUtil {
	/**
	 * 目前EOMS项目主要在国内使用，没有国际化问题，但在各个项目显示的信息不同;
	 */
	private static Locale loc = Locale.CHINA;

	/**
	 * 根据资源文件的关键字取资源信息，默认语言环境为简体中文
	 * 借用了Struts2的功能，资源文件在Struts2的配置文件struts.properties中
	 * 配置struts.custom.i18n.resources
	 * 
	 * @param key
	 *            关键字
	 * @return 资源信息
	 */
	public static String getText(String key) {
		return LocalizedTextUtil.findDefaultText(key, loc);
	}

	/**
	 * 返回32位的UUID
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 判断是否不为空
	 */
	public static boolean isNotNull(Object... objects) {
		if (null != objects && objects.length > 0) {
			for (Object obj : objects) {
				if (null != obj && !"".equals(obj)) {
					continue;
				}
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean isNull(Object... objects) {
		return !isNotNull(objects);
	}

	/**
	 * 将INT型的ID转换为AR的15位字符ID
	 * 
	 * @param number
	 * @return
	 */
	public static String getARID(int number) {
		DecimalFormat format = new DecimalFormat("000000000000000");
		return format.format(number);
	}
	/**
	 * 比较两个值是否有变化  不一样返回TRUE
	 * @author yinhongang
	 * @param oldValue
	 * @param newValue
	 * @return boolean
	 */
	public static boolean isSame(String oldValue , String newValue){
		String oldStr = "";
		String newStr = "";
		oldStr = isNotNull(oldValue) ? oldValue.trim() : oldStr;
		newStr = isNotNull(newValue) ? newValue.trim() : newStr;
		if(!oldStr.equals(newStr))
			return true;
		else
		    return false;
	}
	public static boolean isXuSame(String oldValue , String newValue){
		String oldStr = "";
		String newStr = "";
		if("请选择".equals(newValue)||"-1".equals(newValue)){
			newValue =""; 
		}
		oldStr = isNotNull(oldValue) ? oldValue.trim() : oldStr;
		newStr = isNotNull(newValue) ? newValue.trim() : newStr;
		if(!oldStr.equals(newStr))
			return true;
		else
		    return false;
	}
	
	public static void main(String [] args){
		System.out.println(isSame(" ",""));
	}
}
