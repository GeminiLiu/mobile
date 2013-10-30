package com.ultrapower.mobile.common.startup;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.ultrapower.mobile.common.model.WorkSheetCfgModel;
import com.ultrapower.mobile.common.tcp.TCPServer;
import com.ultrapower.mobile.common.threads.UserOnlineScanner;
import com.ultrapower.mobile.common.udp.UDPServer;
import com.ultrapower.mobile.common.utils.ItSysConfigUtil;

public class Init {
	
	/**
	 * mobile初始化加载
	 */
	public static void init() {
		
		//加载业务系统接口配置文件
		ItSysConfigUtil.loadBizConfig();
		
		//加载服务端配置文件
		ItSysConfigUtil.loadSysConfig();
		
		//加载工单详情配置页面的配置文件
		parseWorkflowInfoXml();
		
		//用户在线扫描线程
		//new UserOnlineScanner().start();
		
		//UDP监听启动
		/*
		new Thread() {
			@Override
			public void run() {
				UDPServer udpServer = new UDPServer();
				udpServer.listening();
			}
		}.start();
		
		//TCP监听启动
		new Thread() {
			@Override
			public void run() {
				TCPServer server = new TCPServer();
				server.listening();
			}
		}.start();
		*/
	}
	
	private static List workSheetInfos = null;
	private static void parseWorkflowInfoXml() {
		try {
			XStream xstream = new XStream(new DomDriver());
			xstream.alias("baseSchemas", ArrayList.class);
			xstream.alias("baseSchema", WorkSheetCfgModel.class);
			String path = Init.class.getResource("/").getPath() + File.separator +"config" + File.separator + "workSheetInfo.xml";
			FileInputStream fis = new FileInputStream(path);
			Object fromXML = xstream.fromXML(fis);
			if (fromXML != null && fromXML instanceof List) {
				workSheetInfos = (List) fromXML;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getWaitDeal(String baseSchema) {
		if (CollectionUtils.isNotEmpty(workSheetInfos) && StringUtils.isNotBlank(baseSchema)) {
			for (int i = 0; i < workSheetInfos.size(); i++) {
				WorkSheetCfgModel cfg = (WorkSheetCfgModel) workSheetInfos.get(i);
				String code = cfg.getCode();
				String waitDeal = cfg.getWaitDeal();
				if (baseSchema.equals(code)) {
					if (StringUtils.isNotBlank(waitDeal)) {
						if (waitDeal.endsWith(".jsp")) {
							return waitDeal.substring(0, waitDeal.length()-4);
						}
						return waitDeal;
					}
					break;
				}
			}
		}
		return null;
	}
	
	public static String getDealed(String baseSchema) {
		if (CollectionUtils.isNotEmpty(workSheetInfos) && StringUtils.isNotBlank(baseSchema)) {
			for (int i = 0; i < workSheetInfos.size(); i++) {
				WorkSheetCfgModel cfg = (WorkSheetCfgModel) workSheetInfos.get(i);
				String code = cfg.getCode();
				String dealed = cfg.getDealed();
				if (baseSchema.equals(code)) {
					if (StringUtils.isNotBlank(dealed)) {
						if (dealed.endsWith(".jsp")) {
							return dealed.substring(0, dealed.length()-4);
						}
						return dealed;
					}
					break;
				}
			}
		}
		return null;
	}
}
