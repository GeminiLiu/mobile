package com.ultrapower.mobile.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class BrowserUtil {

	public static int getClientType(HttpServletRequest req) {
		if (req != null) {
			String header = req.getHeader( "User-Agent");
			if (StringUtils.isNotBlank(header)) {
				header = header.toLowerCase();
				if (header.indexOf("msie") > 0) {
					return 1;// ie
				} else if (header.indexOf("safari") > 0) {
					return 2;//chrome,android,iphone
				} else if (header.indexOf("ucweb") > 0) {
					return 3;//ucweb
				}
			}
		}
		return 0;//unknown
	}
}
