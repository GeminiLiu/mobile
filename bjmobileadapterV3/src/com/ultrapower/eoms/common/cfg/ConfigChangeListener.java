package com.ultrapower.eoms.common.cfg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Copyright (c) 2007 神州泰岳服务管理事业部应用组 All rights reserved.
 * 
 * 摘 要:
 * 
 * 作 者:YeChangLun
 */
public class ConfigChangeListener implements FileChangeListener {
	private static Log log = LogFactory.getLog(ConfigChangeListener.class);

	public void fileChanged(String filename) {
		log.info("Reload " + filename);
		Config.init(Config.getValue(ConfigKeys.APPLICATION_PATH));
	}

}
