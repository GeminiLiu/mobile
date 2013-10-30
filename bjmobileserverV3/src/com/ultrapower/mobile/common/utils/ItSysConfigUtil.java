package com.ultrapower.mobile.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.mobile.common.constants.Constants;
import com.ultrapower.mobile.common.model.ItSysConfig;

public class ItSysConfigUtil {
	
	private static Logger log = LoggerFactory.getLogger(ItSysConfigUtil.class);
	
	private static String bizPath = ItSysConfigUtil.class.getResource("/").getPath() +  "/config/bizsys.xml";
	private static String configPath = ItSysConfigUtil.class.getResource("/").getPath() +  "/config/config.xml";
	
	private static long configFileLastModified = 0;
	
	private static List<ItSysConfig> cache = null;
	
	/**
	 * 加载业务系统配置信息
	 */
	public static void loadBizConfig() {
		cache = new ArrayList<ItSysConfig> ();
		SAXReader reader = new SAXReader();
        try {
        	File cfgFile = new File(bizPath);
        	configFileLastModified = cfgFile.lastModified();
			Document doc = reader.read(bizPath);
			Element root = doc.getRootElement();
			List elements = root.elements("ITSysInterfaceConfigOne");
			if (CollectionUtils.isNotEmpty(elements)) {
				for (int i = 0; i < elements.size(); i++) {
					Element cfgOne = (Element) elements.get(i);
					Element itSysNameEle = cfgOne.element("ITSysName");
					Element itSysCodeEle = cfgOne.element("ITSysCode");
					Element itSysAddressEle = cfgOne.element("ITSysAddress");
					String itSysName = itSysNameEle.getText();
					String itSysCode = itSysCodeEle.getText();
					String itSysAddress = itSysAddressEle.getText();
					ItSysConfig config = new ItSysConfig(itSysName, itSysCode, itSysAddress);
					cache.add(config);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 加载服务端配置信息
	 */
	public static void loadSysConfig() {
		SAXReader reader = new SAXReader();
        try {
			Document doc = reader.read(configPath);
			Element root = doc.getRootElement();
			List elements = root.elements("configitem");
			if (CollectionUtils.isNotEmpty(elements)) {
				for (int i = 0; i < elements.size(); i++) {
					Element cfg = (Element) elements.get(i);
					String name = cfg.attributeValue("name");
					String value = cfg.getText();
					if ("IP_ADDRESS".equalsIgnoreCase(name)) {
						Constants.IP_ADDRESS = value;
					} else if ("TCP_PORT".equalsIgnoreCase(name)) {
						Constants.TCP_PORT = NumberUtils.formatToInt(value);
					} else if ("UDP_PORT".equalsIgnoreCase(name)) {
						Constants.UDP_PORT = NumberUtils.formatToInt(value);
					} else if ("TCP_THREAD_COUNT".equalsIgnoreCase(name)) {
						Constants.TCP_THREAD_COUNT = NumberUtils.formatToInt(value);
					} else if ("USERONLINE_SCAN_INTERVAL".equalsIgnoreCase(name)) {
						Constants.USERONLINE_SCAN_INTERVAL = NumberUtils.formatToInt(value);
					} else if ("USERONLINE_TIMEOUT".equalsIgnoreCase(name)) {
						Constants.USERONLINE_TIMEOUT = NumberUtils.formatToInt(value);
					}  else if ("UDP_SEND_PORT".equalsIgnoreCase(name)) {
						Constants.UDP_SEND_PORT = NumberUtils.formatToInt(value);
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	
	public static ItSysConfig getItSysConfig(String code) {
		ItSysConfig cfg = null;
		checkModifyConfig();
		if (CollectionUtils.isNotEmpty(cache) && StringUtils.isNotBlank(code)) {
			for (int i = 0; i < cache.size(); i++) {
				ItSysConfig itSys = cache.get(i);
				String itSysCode = itSys.getCode();
				if (code.equalsIgnoreCase(itSysCode)) {
					return itSys;
				}
			}
		}
		return cfg;
	}
	
	public static String getItSysAddress(String code) {
		ItSysConfig config = getItSysConfig(code);
		if (config != null) {
			return config.getAddress();
		}
		throw new RuntimeException("标识为[" + code + "]的业务系统没有配置地址！");
	}
	
	private static void checkModifyConfig() {
		File cfgFile = new File(bizPath);
		if (configFileLastModified != cfgFile.lastModified()) {
			loadBizConfig();
		} 
	}
}
