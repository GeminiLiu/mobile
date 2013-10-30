package com.ultrapower.eoms.mobile.util;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class ConvertUtil {
	/**
	 * 是否是手机服务端接入的流程
	 * @param baseSchema
	 * @return
	 */
	public static boolean isMobileFlow(String baseSchema) {
		List flowList = Constants.mobileConfig.getList("flows");
		if (CollectionUtils.isNotEmpty(flowList) && flowList.contains(baseSchema)) {
			return true;
		}
		if (CollectionUtils.isEmpty(flowList)) {
			return true;
		}
		return false;
	}
	
	public static String mobileFlows() {
		String[] flows = Constants.mobileConfig.getStringArray("flows");
		if (flows!=null && flows.length>0) {
			return StringUtils.join(flows, ",");
		}
		return null;
	}

}
