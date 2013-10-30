package cn.com.ultrapower.interfaces.util.ftp;

/**
 * ftp上传和下载

 * @author 徐发球

 * @Date 2008-01-15
 * @version 1.0
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import cn.com.ultrapower.ultrawf.share.FormatInt;
import cn.com.ultrapower.ultrawf.share.FormatString;
public class FTPManager {

	private FtpClient ftpClient; 
	private String romateDir="";
	private String userName="";
	private String password="";
	private String host="";
	private String port="21";
	 public FTPManager(String url)throws IOException
	 {
			//String url="ftp://root:gzl_SJZ1616@10.120.32.60:21/ftptest/d/dd";
			int len=url.indexOf("//");
			String strTemp=url.substring(len+2);
			len=strTemp.indexOf(":");
			userName=strTemp.substring(0,len);
			strTemp=strTemp.substring(len+1);
			
			len=strTemp.indexOf("@");
			password=strTemp.substring(0,len);
			strTemp=strTemp.substring(len+1);
			
			host="";
			len=strTemp.indexOf(":");
			
			
			if(len<0)//没有设置端口
			{
				len=strTemp.indexOf("/");
				host=strTemp.substring(0,len);
				strTemp=strTemp.substring(len+1);
			}else
			{
				host=strTemp.substring(0,len);
				strTemp=strTemp.substring(len+1);
				len=strTemp.indexOf("/");
				port=strTemp.substring(0,len);
				strTemp=strTemp.substring(len+1);
			}
			romateDir=strTemp;
			ftpClient = new FtpClient();
			ftpClient.openServer(host, FormatInt.FormatStringToInt(port));
			
	 }
	 /**
	  * 构造方法，新建一个FtpClient对象，并打开FTP服务器

	  * @param host FTP服务器地址；port FTP服务端口
	  * 
	  */
	 public FTPManager(String host,int port)throws IOException{
		 ftpClient = new FtpClient();
		 ftpClient.openServer(host, port);
	 }
	 
	 /**
	  * 登陆方法，通过用户名密码登陆到指定的FTP服务器上去，并返回欢迎信息

	  * @param username FTP用户名；password 密码
	  * 
	  */
	 public String login(String username,String password)throws IOException{
	  this.ftpClient.login(username, password);
	  if(!romateDir.equals(""))
		  ftpClient.cd(romateDir);	  
	  return this.ftpClient.welcomeMsg;
	 }
	 
	 public String login()throws IOException{
		  this.ftpClient.login(userName, password);
		  if(!romateDir.equals("")){
			  /*String a [] = romateDir.split("/");
			  for(int i = 0 ; i < a.length ; i ++){
				  if(a[i] != null && !"".equals(a[i])){
					  ftpClient.cd(a[i]);
				  }
			  }*/
			  ftpClient.cd(romateDir);
			  
		  }
		  return this.ftpClient.welcomeMsg;
		 }	 
	 
	 /**
	  * 此方法用来上传文件。

	  * @param pathname 本地路径；filename 要上传的文件名称
	  */
	 public void upload(String pathname,String filename)throws IOException{
		 
		 filename=FormatString.CheckNullString(filename);
		 if(filename.equals(""))
			 return;
	    if(!this.ftpClient.serverIsOpen()){
	     System.out.println("服务器连接不可用！");
	    }
	    this.ftpClient.binary();
	    TelnetOutputStream os = null;
	    FileInputStream is = null;
	    try {
	     //用ftp上传后的文件名与原文件名相同，同为filename变量内容
	     os = this.ftpClient.put(filename);
	     java.io.File file_in = new java.io.File(pathname+"\\"+filename);
	     if (file_in.length()==0) {
	      System.out.println("上传文件为空!");
	     }
	     is = new FileInputStream(file_in);
	     byte[] bytes = new byte[1024];
	     int c;
	     while ((c = is.read(bytes)) != -1) {
	      os.write(bytes, 0, c);
	     }
	    } finally {
	     if (is != null) {
	      is.close();
	     }
	     if (os != null) {
	      os.close();
	     }
	    }
	    System.out.println("上传文件成功!");
	    this.ftpClient.ascii();
	 }
	 
		/**
		 * 日期 2007-5-10
		 * @author xuquanxing 
		 * @return
		 * @throws Exception boolean
		 * 从ftp上下载到本地
		 */
		public boolean download(String localpath,String fileName) throws Exception
		{
			boolean flag = false;
			try
			{
				//ftpClient.cd(romateDir);
					try
					{	
						login();
						ftpClient.binary(); 
						TelnetInputStream is = ftpClient.get(fileName);  
						 File file_out = new File(localpath + File.separator + fileName);   
			                FileOutputStream os = new FileOutputStream(file_out);   
			                byte[] bytes = new byte[1024];   
			                int c;   
			                while ((c = is.read(bytes)) != -1) {   
			                    os.write(bytes, 0, c);   
			                }   
			                is.close();   
			                os.close();   
					} catch (Exception e)
					{
						flag = false;
						e.printStackTrace();
						return flag;
					}
				flag = true;
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				close();
			}
			return flag;
		}

	 public void close()
	 {
		 try
		 {
			 if(ftpClient!=null)
				 ftpClient.closeServer();
		 }catch(Exception ex)
		 {
			 
		 }
	 }
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FTPManager a = new FTPManager("ftp://eomsftp:eomsftp@10.223.3.116/0811180000001120");
			a.login();
			a.download("D:\\workpanal\\nx\\WebRoot\\WEB-INF\\attachments", "test.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
