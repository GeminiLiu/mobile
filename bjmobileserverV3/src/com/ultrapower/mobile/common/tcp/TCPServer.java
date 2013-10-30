package com.ultrapower.mobile.common.tcp;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.mobile.common.constants.Constants;


public class TCPServer {

	private static Logger log = LoggerFactory.getLogger(TCPServer.class);
	
	private ServerSocket serverSocket;

	private Socket socket;
	
	private InetAddress ipAddress;

	private ExecutorService executorServce = Executors.newFixedThreadPool(Constants.TCP_THREAD_COUNT);

	public TCPServer() {

		try {
			this.ipAddress = InetAddress.getByName(Constants.IP_ADDRESS);
			log.info("tcp监听在[" + ipAddress.getHostAddress() + ":" + Constants.TCP_PORT + "]");
			serverSocket = new ServerSocket(Constants.TCP_PORT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	public void listening() {
		while (true) {
			try {
				socket = serverSocket.accept();
				executorServce.execute(new WorkThread(socket));
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}

		}
	}

	public static void main(String[] args) throws Exception {
		TCPServer server = new TCPServer();
		server.listening();
	}

}