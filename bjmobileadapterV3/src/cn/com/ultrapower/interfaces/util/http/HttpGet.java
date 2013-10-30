package cn.com.ultrapower.interfaces.util.http;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @see: ��ָ����HTTP������Դ�ڱ������ļ���ʽ���
 **/
public class HttpGet {

	public final static boolean DEBUG = true; //������

	private static int BUFFER_SIZE = 8096; //�������С

	private Vector vDownLoad = new Vector(); //URL�б�

	private Vector vFileList = new Vector(); //���غ�ı����ļ����б�

	/**
	 * @see: ���췽��
	 */
	public HttpGet() {
	}

	/**
	 * @see: ��������б�
	 */
	public void resetList() {
		vDownLoad.clear();
		vFileList.clear();
	}

	/**
	 * @see: ��������б���
	 * @param url String
	 * @param filename String
	 */

	public void addItem(String url, String filename) {
		vDownLoad.add(url);
		vFileList.add(filename);
	}

	/**
	 * @throws IOException 
	 * @see: ����б�������Դ
	 */
	public void downLoadByList_NewName() throws IOException {
		String url = null;
		String filename = null;

		//���б�˳�򱣴���Դ
		for (int i = 0; i < vDownLoad.size(); i++) 
		{
			url = (String) vDownLoad.get(i);
			filename = (String) vFileList.get(i);

			try 
			{
				saveToFile_NewName(url, filename);
			} 
			catch (IOException err) 
			{
				if (DEBUG) 
				{
					System.out.println("��Դ[" + url + "]����ʧ��!!!");
				}
				throw err;
			}
		}

		if (DEBUG) 
		{
			System.out.println("�������!!!");
		}
	}
	
	/**
	 * @throws IOException 
	 * @see: ����б�������Դ
	 */

	/**
	 * @see: ��HTTP��Դ���Ϊ�ļ�
	 *
	 * @param destUrl String
	 * @param fileName String
	 * @throws Exception
	 */
	public void saveToFile_NewName(String destUrl, String fileName) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;

		//��bt��
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		//l��ָ������Դ
		httpUrl.connect();
		//��ȡ����������
		bis = new BufferedInputStream(httpUrl.getInputStream());
		//��b�ļ�
		fos = new FileOutputStream(fileName);

		if (this.DEBUG)
		{
			System.out.println("���ڻ�ȡt��[" + destUrl + "]������...\n���䱣��Ϊ�ļ�[" + fileName + "]");
		}
		//�����ļ�
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);

		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	/**
	 * @see: ��HTTP��Դ���Ϊ�ļ�
	 * @param destUrl String
	 * @param fileName String
	 * @throws Exception
	 */
	public void saveToFile_NewName_NeedAuthorization(String destUrl, String fileName, String p_UserName,String p_PassWord) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;

		//��bt��
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();

		// String authString = "username" + ":" + "password";
		String authString = p_UserName + ":" + p_PassWord;
		String auth = "Basic "
				+ new sun.misc.BASE64Encoder().encode(authString.getBytes());
		httpUrl.setRequestProperty("Proxy-Authorization", auth);

		//l��ָ������Դ
		httpUrl.connect();
		//��ȡ����������
		bis = new BufferedInputStream(httpUrl.getInputStream());
		//��b�ļ�
		fos = new FileOutputStream(fileName);

		if (this.DEBUG)
		{
			System.out.println("���ڻ�ȡt��[" + destUrl + "]������...\n���䱣��Ϊ�ļ�[" + fileName + "]");
		}
		//�����ļ�
		while ((size = bis.read(buf)) != -1)
		{
			fos.write(buf, 0, size);
		}

		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	/**
	 * @see: ���ô��������
	 *
	 * @param proxy String
	 * @param proxyPort String
	 */
	public void setProxyServer(String proxy, String proxyPort) {
		//���ô��������
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", proxy);
		System.getProperties().put("proxyPort", proxyPort);
	}

	/**
	 * @see: ���(���ڲ���)
	 *
	 * @param argv String[]
	 */
	public static void main(String argv[]) {
		HttpGet oInstance = new HttpGet();
		try {
//			//��������б?�˴��û�����д���Լ�����4��������б?
//			oInstance.addItem("http://www.ebook.com/java/������001.zip","./������1.zip");//
//			oInstance.addItem("http://www.ebook.com/java/������002.zip","./������2.zip");
//			oInstance.addItem("http://www.ebook.com/java/������003.zip","./������3.zip");
//			//��ʼ����
//			oInstance.downLoadByList();
			System.out.println("��ʼ");
			oInstance.saveToFile_NewName(
					"http://tjtt1.it.com.cn/34382/software/network/sharing/20073/IT_kugoo3.243.zip",
					"C:\\haha.zip");
			System.out.println("����");
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}
	}
}
