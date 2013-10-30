package com.ultrapower.mobile.common.udp;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.mobile.common.constants.Constants;

public class CopyOfUDPClient {
	private DatagramSocket dataSocket;
	private DatagramPacket dataPacket;
	private static Logger log = LoggerFactory.getLogger(CopyOfUDPClient.class);
	private String ip;
	private int port;
	
	
	public CopyOfUDPClient(String serverIp, int serverPort) {
		try {
			dataSocket = new DatagramSocket(55551);
//			this.ip = "192.168.14.164";
			this.ip = "60.247.77.195";
			this.port = 55551;
		} catch (SocketException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}

	public void send(String content) {
		log.info("开始通过udp发送数据,目标：" + ip + ": " + port + ",内容：" + content);
		try {
			if (StringUtils.isNotBlank(content)) {
				byte[] res = content.getBytes("utf-8");
				dataPacket = new DatagramPacket(res, res.length, InetAddress.getByName(ip), port);
				dataSocket.send(dataPacket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} finally {
			dataSocket.close();
		}
	}
	
	public static void main(String[] args) {
		CopyOfUDPClient client = new CopyOfUDPClient(null, 0);
		client.send("test");
	}
}
