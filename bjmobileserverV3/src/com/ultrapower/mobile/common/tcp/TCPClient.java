package com.ultrapower.mobile.common.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.mobile.common.constants.Constants;



public class TCPClient {

	private static Logger log = LoggerFactory.getLogger(TCPClient.class);
	
	private Socket socket;

	private InetAddress remoteIP;
	
	private byte[] buffer = new byte[4096];
	
	private OutputStream outputStream;
	
	private InputStream inputStream;

	public TCPClient() {
		try {
			remoteIP = InetAddress.getByName(Constants.IP_ADDRESS);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void talk() {

		try {
			log.info("客户端开始运行！");
			socket = new Socket(remoteIP, Constants.TCP_PORT);
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();

			System.out.println(receive());
			
			send("r1");

			System.out.println(receive());
			
			send("r2");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
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
			while ((b = inputStream.read(buffer)) > 0) {
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
				outputStream.write(msg.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {

		new TCPClient().talk();

	}

}