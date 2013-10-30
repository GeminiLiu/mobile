package com.ultrapower.mobile.common.udp;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.mobile.common.constants.Constants;
import com.ultrapower.mobile.service.BizFacade;

public class CopyOfUDPServer {
	private DatagramSocket dataSocket;
	private byte buffer[] = new byte[8192];
	private static Logger log = LoggerFactory.getLogger(CopyOfUDPServer.class);
	
	public CopyOfUDPServer() {
		try {
			dataSocket = new DatagramSocket(55551, InetAddress.getByName("192.168.14.164"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public void listening() {
		log.info("开始通过udp开始监听,目标=192.168.14.164: " + 55551 );
		while (true) {
			String content = null;
			DatagramPacket receivePacket = null;
			try {
				receivePacket = new DatagramPacket(buffer, buffer.length);
				dataSocket.receive(receivePacket);
				byte[] data = receivePacket.getData();
				content = new String(data, 0, receivePacket.getLength(), "utf-8");
				log.info(content);
				
				int remotePort = receivePacket.getPort();
				String remoteIp = receivePacket.getAddress().getHostAddress();
				log.info("开始处理请求：" +  remoteIp + ":" + remotePort);
				
//				BizFacade bizFacade = (BizFacade) WebApplicationManager.getBean("bizFacade");
				String result = "this is a response from server!!!";
				if (StringUtils.isNotBlank(result)) {
					byte[] res = result.getBytes("utf-8");
					DatagramPacket sendPacket = new DatagramPacket(res, res.length, receivePacket.getAddress(), remotePort);
					dataSocket.send(sendPacket);
					log.info("响应成功！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			} 
		}
	}
	
	public static void main(String args[]) {
		CopyOfUDPServer server = new CopyOfUDPServer();
		server.listening();
	}
}
