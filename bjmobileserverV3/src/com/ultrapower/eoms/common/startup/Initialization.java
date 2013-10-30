package com.ultrapower.eoms.common.startup;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.email.EmailPara;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.ultrarepository.util.RepositoryLoadXML;
import com.ultrapower.mobile.common.startup.Init;
import com.ultrapower.mobile.common.threads.UserOnlineScanner;
import com.ultrapower.mobile.common.udp.UDPServer;
import com.ultrapower.remedy4j.core.RemedySession;

public class Initialization {
	
	/**
	 * 系统初始化
	 */
	public static void initMoblie() {
		Constants.WORKSHEET_UPLOAD_PATH = PropertiesUtils.getProperty("worksheet.attachment.path");
		Constants.PATROL_UPLOAD_PATH = PropertiesUtils.getProperty("patrol.attachment.path");
		Constants.APP_UPLOAD_PATH = PropertiesUtils.getProperty("app.attachment.path");
		Constants.OFFLINE_TEMP_PATH = PropertiesUtils.getProperty("offline.temp.path");
		Init.init();
	}
}
