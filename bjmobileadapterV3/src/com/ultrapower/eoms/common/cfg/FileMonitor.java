package com.ultrapower.eoms.common.cfg;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
public class FileMonitor {
	private Log log = LogFactory.getLog(FileMonitor.class);
	private static FileMonitor instance = new FileMonitor();
	private Timer timer = new Timer(true);
	private Map timerEntries = new HashMap();

	public FileMonitor() {
	}

	public static FileMonitor getInstance() {
		return instance;
	}

	public void addFileChangeListener(FileChangeListener listener,
			String filename, long period) {
		this.removeFileChangeListener(filename);
		log.info("Watching " + filename);
		FileMonitorTask task = new FileMonitorTask(listener, filename);
		this.timerEntries.put(filename, task);
		this.timer.schedule(task, period, period);
	}

	public void removeFileChangeListener(String filename) {
		FileMonitorTask task = (FileMonitorTask) this.timerEntries
				.remove(filename);
		log.info("Stop watching " + filename);
		if (task != null) {
			log.info("Config file monitor task cancel!");
			task.cancel();
		}
	}

	private static class FileMonitorTask extends TimerTask {
		private FileChangeListener listener;
		private String filename;
		private File monitoredFile;
		private long lastModified;

		public FileMonitorTask(FileChangeListener listener, String filename) {
			this.listener = listener;
			this.filename = filename;

			this.monitoredFile = new File(filename);
			if (!this.monitoredFile.exists()) {
				return;
			}

			this.lastModified = this.monitoredFile.lastModified();
		}

		public void run() {
			long latestChange = this.monitoredFile.lastModified();
			if (this.lastModified != latestChange) {
				this.lastModified = latestChange;
				System.out.println("Config file has changed:" + filename);
				this.listener.fileChanged(this.filename);
			}
		}
	}
}
