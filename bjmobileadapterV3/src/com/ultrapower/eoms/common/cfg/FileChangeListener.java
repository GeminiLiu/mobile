package com.ultrapower.eoms.common.cfg;

/**
 * 
 * Copyright (c) 2007 神州泰岳服务管理事业部应用组 All rights reserved.
 * 
 * 摘 要:
 * 
 * 作 者:YeChangLun
 */
public interface FileChangeListener {
	/**
	 * 文件路径名称
	 * 
	 * @param filename
	 */
	public void fileChanged(String filename);
}
