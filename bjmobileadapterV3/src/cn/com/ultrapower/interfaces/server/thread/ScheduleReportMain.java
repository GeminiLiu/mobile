package cn.com.ultrapower.interfaces.server.thread;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map; 
import java.util.Properties; 
import java.util.Set;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * 接口调度程序
 * 功能：接口服务调度程序
 * 作者：杨洪
 * 版本：1.0
 * 时间：2009.5.20
 */
public class ScheduleReportMain extends Thread {
	public static boolean isPause = false;// 控制线程的停止运行状态
	public static Thread thread=null;
	public static String startType="";
	public static String timeSpanType="";
	public static String jobfilepath="";
	public static HashMap hm=new HashMap();
	public ScheduleReportMain(){
		hm=new HashMap();
	}
	public void run() {
		System.out.println("::接口服务启动......");
		try
		{
			long scantime=1;
			while(isPause==false){
				String strscantime=(String)ParseConfigIndex(jobfilepath).get("scantime");
				if(strscantime!=null&&!strscantime.equals("")){
					scantime=Long.parseLong(strscantime);
				}
				System.out.println(scantime);
				String strEOMSProcess=(String)ParseConfigIndex(jobfilepath).get("客服接口");
				if(strEOMSProcess!=null&&strEOMSProcess.equals("EOMSProcess")){
					EOMSProcess.callprocess();
				}
				String strAlarmProcess=(String)ParseConfigIndex(jobfilepath).get("告警接口");
				if(strAlarmProcess!=null&&strAlarmProcess.equals("AlarmProcess")){
					AlarmProcess.callprocess();
				}
				String strBulletinProcess=(String)ParseConfigIndex(jobfilepath).get("公告接口");
				if(strBulletinProcess!=null&&strBulletinProcess.equals("BulletinProcess")){
					BulletinProcess.callprocess();
				}
				long iseconds=scantime*1000;
				sleep(iseconds);
				System.out.println("......接口扫描开始......"+scantime++);
			}
		}			
		catch(Exception e){
			System.out.println("::接口服务异常 e="+e.getMessage());
		}
	}
	
	/**
	 * @return Returns the isPause. 设置
	 */
	public static boolean isPause()
	{
		return isPause;
	}

	/**
	 * @param isPause
	 * The isPause to set.
	 */
	public synchronized void setPause(boolean bPause)
	{
		this.isPause = bPause;
        try {
            this.join();
            System.out.println("Run Join");
        } catch (InterruptedException e) {
        	e.printStackTrace();          
        }

	}

	public static boolean startSchedule(String filepath){
		boolean bStart=false;
		try{
	        startType="1";
	        jobfilepath=filepath;
			if(thread==null){
	            System.out.println("......开始启动接口服务......!");
	            isPause=false;
				ScheduleReportMain plan=new ScheduleReportMain();
				thread=plan;
				plan.start();
//				thread.run();
				bStart=true;
			}else if(isPause==true){
	            System.out.println("接口服务重新启动......!");
				isPause = false;	
				thread.run();
				bStart=true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			bStart=false;
		}
		return bStart;
	}
	public static boolean stopSchedule(){
		boolean bStop=false;
		try{
	        startType="2";
			if(thread!=null){
				isPause = true;
				thread.interrupt();
				thread=null;
				bStop=true;
	            System.out.println("接口服务关闭......!");
			}
		}catch(Exception e){
			e.printStackTrace();
			bStop=false;
		}
		return bStop;
	}
	public static boolean getThread(){
		boolean bThread=false;
		if(thread!=null){
			bThread=true;
		}else{
			bThread=false;
		}
		return bThread;
	}

	public static HashMap ParseConfigIndex(String filepath){
		try{
			HashMap hmConfigIndex=new HashMap();
			String ChannelFilePath = filepath+"/jobconfig.xml";
			File file =new File(ChannelFilePath);
			SAXReader saxreader =new SAXReader();
			Document document = saxreader.read(file);
			List list = document.selectNodes("/config/param");
			Iterator iter =list.iterator();
			while(iter.hasNext()){
				Node node =(Node)iter.next();
				String paramName = (node.selectSingleNode("param-name")).getText();
				String paramValue = (node.selectSingleNode("param-value")).getText();
				hmConfigIndex.put(paramName,paramValue);
			}
			return hmConfigIndex;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScheduleReportMain plan=new ScheduleReportMain();
		startType="1";
		plan.start();
//		beginIndex();
	}

}
