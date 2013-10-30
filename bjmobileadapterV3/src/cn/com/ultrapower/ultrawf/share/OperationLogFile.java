package cn.com.ultrapower.ultrawf.share;
import java.io.*; 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.util.FormatString;
import cn.com.ultrapower.system.util.FormatTime;
public class OperationLogFile {

	private static String errFilePrefix="";
	private static String logFilePrefix="";
	
    public static void writeErrLog(String logMessage) {
    	String logFilePath="";
    	// writeTxt方法，需要传递两个参数，log为你需要写到txt文本的内容，logfile为你要输出txt文本的路径和文件名

    	logFilePath=Constants.sysPath;
    	logFilePath=logFilePath.replaceAll(File.separator+"WEB-INF","");
    	logFilePath+=File.separator+"logs";
    	String fileName="";
    		try{
    			
    			Date d1=new Date();
    			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    			d1.setTime(System.currentTimeMillis());
    			if(errFilePrefix.equals(""))
    				fileName="Err"+format.format(d1)+".log";
    			else
    				fileName=errFilePrefix+format.format(d1)+".log";
    		}catch(Exception ex)
    		{
    			ex.printStackTrace();
    			fileName="Errother.log";
    		}
		try { // 捕捉异常
			File   file=new   File(logFilePath);   
			if(!file.isDirectory())//是否目录
			{
				file.mkdir();
			}
			logFilePath=logFilePath+File.separator+fileName;
			FileWriter write = new FileWriter(logFilePath, true); // 构造FileWriter类(输出流)
			write.write(logMessage); // 将log内容写道FileWriter流中
			write.close(); // 关闭输出流

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); // 如有异常打印到控制台
		}
		//System.out.print("success!"); // 成功在控制台输出seccess
	}		
	
    public static void writelnTxt(String logMessage) 
    {
    	writeTxt("\r\n"+logMessage);
    }
    public static void writeTxt(String logMessage) {
    	String logFilePath="";
    	// writeTxt方法，需要传递两个参数，log为你需要写到txt文本的内容，logfile为你要输出txt文本的路径和文件名
    	//String sysPath = "E:\\WorkSpace\\UltraProcess\\WEB-INF";
    	//logFilePath=sysPath;
    	logFilePath=Constants.sysPath;
    	
    	logFilePath=logFilePath.replaceAll(File.separator+"WEB-INF","");
    	logFilePath+=File.separator+"logs"+File.separator;
    	String fileName="";
    		try{
    			
    			Date d1=new Date();
    			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    			d1.setTime(System.currentTimeMillis());
    			fileName=format.format(d1); 
    			fileName=logFilePrefix+fileName+".log";
    		}catch(Exception ex)
    		{
    			ex.printStackTrace();
    			fileName="other.log";
    		}
		try { // 捕捉异常
			
			File   file=new   File(logFilePath);   
			if(!file.isDirectory())//是否目录
			{
				file.mkdir();
			}			
			logFilePath+=fileName;
			
			
			FileWriter write = new FileWriter(logFilePath, true); // 构造FileWriter类(输出流)
			write.write(logMessage); // 将log内容写道FileWriter流中
			write.close(); // 关闭输出流
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); // 如有异常打印到控制台
		}
		//System.out.print("success!"); // 成功在控制台输出seccess
	}	

    public static void writeTxt(String logMessage, String logFilePath,String logFileName) {
		// writeTxt方法，需要传递两个参数，log为你需要写到txt文本的内容，logfile为你要输出txt文本的路径和文件名

    	logFilePath=FormatString.CheckNullString(logFilePath);
    	if(logFilePath.trim().equals(""))
    	{
    		try{
    			logFilePath=String.valueOf(FormatTime.formatIntToDateString(System.currentTimeMillis()));
    			//logFilePath+=".log";
    		}catch(Exception ex)
    		{
    			logFilePath="other.log";
    		}
    	}	
		try { // 捕捉异常
			
			File   file=new   File(logFilePath);   
			if(!file.isDirectory())//是否目录
			{
				file.mkdir();
			}				
			
			FileWriter write = new FileWriter(logFilePath+File.separatorChar+logFileName, true); // 构造FileWriter类(输出流)
			write.write(logMessage); // 将log内容写道FileWriter流中
			write.close(); // 关闭输出流

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); // 如有异常打印到控制台
		}
		//System.out.print("success!"); // 成功在控制台输出seccess
	}	
	
    /**
     * 错误日志文件名前缀
     * @param strPrefix
     */
    public static void setErrFilePrefix(String strPrefix)
    {
    	errFilePrefix=FormatString.CheckNullString(strPrefix);
    }
    /**
     * 正确日志的文件名前缀
     * @param strPrefix
     */
    public static void setFilePrefix(String strPrefix)
    {
    	logFilePrefix=FormatString.CheckNullString(strPrefix);
    }
    
    public static void writeTxt(String logMessage, String logFilePath) {
		// writeTxt方法，需要传递两个参数，log为你需要写到txt文本的内容，logfile为你要输出txt文本的路径和文件名
    	logFilePath=FormatString.CheckNullString(logFilePath);
    	if(logFilePath.trim().equals(""))
    	{
    		try{
    			logFilePath=String.valueOf(FormatTime.formatIntToDateString(System.currentTimeMillis()));
    			logFilePath+=".log";
    		}catch(Exception ex)
    		{
    			logFilePath="other.log";
    		}
    	}	
		try { // 捕捉异常
			
			FileWriter write = new FileWriter(logFilePath, true); // 构造FileWriter类(输出流)
			write.write(logMessage); // 将log内容写道FileWriter流中
			write.close(); // 关闭输出流
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); // 如有异常打印到控制台
		}
		//System.out.print("success!"); // 成功在控制台输出seccess
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//OperationLogFile m_OperationLogFile=new OperationLogFile();
		OperationLogFile.writeTxt("\r\n成功在控制台输出","c:\\testDir","text.txt");
	}

}
