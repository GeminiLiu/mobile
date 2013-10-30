package com.ultrapower.mobile.common.udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.mobile.common.constants.Constants;

public class UDPClient {
	private DatagramPacket dataPacket;
	private static Logger log = LoggerFactory.getLogger(UDPClient.class);
	private String ip;
	private int port;
	
	public UDPClient(String serverIp, int serverPort) {
			this.ip = serverIp;
			this.port = serverPort;
	}

	public void send(String content) {
		log.info("开始通过udp发送数据,目标：" + ip + ": " + port + ",内容：" + content);
		try {
			if (StringUtils.isNotBlank(content)) {
				byte[] res = content.getBytes("utf-8");
				dataPacket = new DatagramPacket(res, res.length, InetAddress.getByName(ip), port);
				UDPServer.dataSocket.send(dataPacket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	
	public void send(DatagramPacket packet) {
		try {
			UDPServer.dataSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
