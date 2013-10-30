package com.ultrapower.mobile.common.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.mobile.service.BizFacade;

public class WorkThread implements Runnable {

	private static Logger log = LoggerFactory.getLogger(WorkThread.class);
	
	private Socket socket;
	private OutputStream outputStream;
	private InputStream inputStream;
	private byte[] buffer = new byte[8192];

	public WorkThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			InetAddress localAddress = socket.getLocalAddress();
			int localPort = socket.getLocalPort();
			InetAddress remoteAddress = socket.getInetAddress();
			int remotePort = socket.getPort();
			
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			
			String welcome = "客户端["+remoteAddress.getHostAddress()+":"+remotePort+"]服务器端["+localAddress.getHostAddress()+":"+localPort+"]开始通讯！";
			log.info(welcome);
			
			String content = receive();
			
			BizFacade bizFacade = (BizFacade) WebApplicationManager.getBean("bizFacade");
			String result = bizFacade.invoke(content, remoteAddress.getHostAddress(), remotePort);
			if (StringUtils.isNotBlank(result)) {
				send(result);
			}
			
			log.info("客户端["+remoteAddress.getHostAddress()+":"+remotePort+"]服务器端["+localAddress.getHostAddress()+":"+localPort+"]通讯结束！");;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 接收消息
	 * @return
	 */
	public String receive() {
		StringBuilder sb = new StringBuilder(); 
		int b = 0;
		try {
			while ((b = inputStream.read(buffer)) != -1) {
				String s = new String(buffer, 0, b);
				sb.append(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return sb.toString();
	}
	
	/**
	 * 发送消息
	 * @param msg
	 */
	public void send(String msg) {
		if (StringUtils.isNotBlank(msg)) {
			try {
				outputStream.write(msg.getBytes("utf-8"));
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
	}
}
