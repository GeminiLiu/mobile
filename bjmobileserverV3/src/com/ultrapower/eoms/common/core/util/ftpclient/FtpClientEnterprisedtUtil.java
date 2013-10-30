package com.ultrapower.eoms.common.core.util.ftpclient;

import java.io.File;
import java.io.FileOutputStream;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * 使用enterprisedt组件实现的ftp客户端
 * @author 朱朝辉(zhuzhaohui@ultrapower.com.cn)
 * @version Apr 21, 2010 8:32:43 PM
 */
public class FtpClientEnterprisedtUtil {
	static FTPClient ftpClient;
	public String localpath; 
	public String ftppath; 
	public String remoteAddress;
	public int remotePort;
	public String userName;
	public String password;

	/**
	 * 构造函数,实现连接参数的赋值操作
	 * @param remoteAddress  ftp服务器的ip地址
	 * @param remotePort ftp服务器的端口
	 * @param userName ftp服务器的账号
	 * @param password ftp服务器的密码
	 * @param localpath  从ftp上下载到本机存放的路径
	 * @param ftppath ftp远程路径
	 */
	public FtpClientEnterprisedtUtil(String remoteAddress, int remotePort,
			String userName, String password, String localpath, String ftppath) {
		this.remoteAddress = remoteAddress;
		this.remotePort = remotePort;
		this.userName = userName;
		this.password = password;
		this.localpath = localpath;
		this.ftppath = ftppath;
	}

	/**
	 * 初始化连接信息
	 * @exception Exception
	 */
	private void init() throws Exception {
		try {
			// ftp初始化
			ftpClient = new FTPClient();// 实例化ftp客户端
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.setRemoteHost(this.remoteAddress);
			ftpClient.setRemotePort(this.remotePort);
			ftpClient.connect();// 连接主机
			ftpClient.login(this.userName, this.password);
			ftpClient.setConnectMode(FTPConnectMode.PASV);
			ftpClient.setType(FTPTransferType.ASCII);// 二进制
			System.out.println("state=" + remoteAddress);
			System.out.println("state=" + this.remotePort);
			System.out.println("state=" + this.userName);
			System.out.println("state=" + this.password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 从ftp上下载文件到本地
	 * @return 如果下载成功,则返回 true;否则返回 false。
	 * @throws Exception
	 */
	public boolean download() throws Exception {
		boolean flag = false;
		int count = 0;
		System.out.println("ftppath=" + ftppath);
		try {
			init();
			ftpClient.chdir(ftppath);
			String[] files = ftpClient.dir(".", true); // 列出当前目录下所有文件和目录
			System.out.println("download is  running.........");
			for (int i = 0; i < files.length; i++) {
				try {
					String[] arr = files[i].split(" ");
					for (int k = 0; k < arr.length; k++) {
						if (arr[k].endsWith("xml")) {
							count++;
							ftpClient.get(new FileOutputStream(localpath
									+ File.separator + arr[k]), arr[k]);
							ftpClient.delete(arr[k]);// 删除服务器上的文件
						} else if (arr[k].equalsIgnoreCase("<DIR>"))// 是目录
						{
							continue;
						}
					}
				} catch (Exception e) {
					flag = false;
					return flag;
				}
			}
			System.out.println("download is  finished!");
			System.out.println("the sum of the downloaded file is " + count);
			flag = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			clear(ftpClient);
		}
		return flag;
	}

	/**
	 * 退出ftp操作
	 * @param ftpClient
	 * @return 如果退出成功,则返回 true;否则返回 false。
	 * @exception Exception
	 */
	public boolean clear(FTPClient ftpClient) throws Exception {
		try {
			ftpClient.quit();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
