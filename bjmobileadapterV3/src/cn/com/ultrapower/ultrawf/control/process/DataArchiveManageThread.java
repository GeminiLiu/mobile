package cn.com.ultrapower.ultrawf.control.process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cn.com.ultrapower.ultrawf.share.constants.Constants;


public class DataArchiveManageThread extends Thread {
	
	public String m_BaseArchiveRunMode;
	public boolean isStart;
	
	public DataArchiveManageThread() {
		this.m_BaseArchiveRunMode = Constants.BaseArchiveCycMode;
	}
	 public int getWeek(Date _date) {
		  // 再转换为时间
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(_date);
		  return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}
	public void run() {
		System.out.println("工单表级分离线程：开始");
		DataArchive m_dataArchive = new DataArchive();
		String strInterval = Constants.BaseArchiveRuniInterval;
		Date date = null;
		Calendar calendar = new GregorianCalendar();		
		while(isStart)
		{
			try {
				int flag1 = 0;
				int flag2 = 0;
	//			<BaseArchiveCycMonthDay>1</BaseArchiveCycMonthDay><!--工单表级分离的运行周期为月时的 当月的哪一天 31>n>0 -->
	//			<BaseArchiveCycWeekDay>1</BaseArchiveCycWeekDay><!--工单表级分离的运行周期为周时的 当周的哪一天 8>n>0 -->
	//			<BaseArchiveCycDayDay>1</BaseArchiveCycDayDay><!--工单表级分离的运行周期为周时的 当周的哪一天 n=1 -->
	//			<BaseArchiveRunTimeHour>0</BaseArchiveRunTimeHour><!--工单表级分离的运行的时间的小时 24>n>-1 -->
				date = new Date(System.currentTimeMillis());
				calendar.setTime(date);			
				int date_MonthDay 	= calendar.get(Calendar.DAY_OF_MONTH);
				int date_WeekDay 	= getWeek(date);
				int time_Hour 		= calendar.get(Calendar.HOUR_OF_DAY);
				//描述：工单表级分离的运行周期 0：天 ；1：周 ；2：月
				if (m_BaseArchiveRunMode.equals("0")==true)//0：天 
				{
					if (Constants.BaseArchiveCycDayDay.equals("1"))
					{
						flag2 = 1;
					}	
				}
				if (m_BaseArchiveRunMode.equals("1")==true )//1：周
				{
					if (Constants.BaseArchiveCycWeekDay.equals((new Integer(date_WeekDay)).toString()))
					{
						flag2 = 1;
					}	
				}			
				if (m_BaseArchiveRunMode.equals("2")==true )//2：月
				{
					if (Constants.BaseArchiveCycMonthDay.equals((new Integer(date_MonthDay)).toString()))
					{
						flag2 = 1;
					}
				}	
				if (flag2 == 1)
				{
					if (Constants.BaseArchiveRunTimeHour.equals((new Integer(time_Hour)).toString()))
					{
						flag1 = 1;
					}	
				}			
				if (flag1 == 1)
				{
					GregorianCalendar cal = new GregorianCalendar(); 
					cal.setTime(date); 
					cal.add(GregorianCalendar.DAY_OF_MONTH,0 - Integer.parseInt(Constants.BaseArchiveActivityDataCapacity)); 					
					System.out.println("  |_工单表级分离开始");
					System.out.println("  |_工单表级传递的时间：" + cal.getTime().toLocaleString());
					m_dataArchive.operateArchive(cal.getTime());
					System.out.println("  |_工单表级分离结束");
				}

				Thread.sleep(1000 * 60 * (new Integer(strInterval)).intValue());
				//Thread.sleep(1000 * (new Integer(strInterval)).intValue());
			} catch (Exception e1) {
				ErrorFunction(e1);
			}	
			System.out.println("|_工单表级分离线程开始下一次循环");
		}
		
		System.out.println("工单表级分离线程：结束");
	}
	
	public void ErrorFunction(Exception e) {
		String strError = e.toString();
		ErrorFunction(strError);
	}

	public void ErrorFunction(String strError) {
		String strTmp = "";
		Timestamp TmpDataTime = new Timestamp(System.currentTimeMillis());
		strTmp = strTmp + "工单表级分离线程：" + "；";	
		strTmp = strTmp + "发生错误的时间为：" + TmpDataTime.toString() + "\r\n";
		strTmp = strTmp + "　错误的描述为：" + strError + "\r\n";
		strTmp = strTmp + "------------------------------------\r\n";
		if (WriteToFile(strTmp, "BaseArchiveErr.log") == false) {
			strTmp = strTmp + "	***********-写文件错误-***************\r\n";
			strTmp = strTmp + "------------------------------------\r\n";
		}		
	}

	public void WriteLog(String strBaseName, String strBaseID) {
		Timestamp TmpDataTime = new Timestamp(System.currentTimeMillis());
		String strTmp = "";

		strTmp = strTmp + "工单表级分离线程：" + "；";	
		strTmp = strTmp + "操作的时间为：" + TmpDataTime.toString() + "\r\n";
		strTmp = strTmp + "　操作工单为：" + strBaseName + "\r\n";
		strTmp = strTmp + "　操作工单号为：" + strBaseID + "\r\n";
		strTmp = strTmp + "　操作为：" + "分离成功" + "\r\n";
		strTmp = strTmp + "------------------------------------\r\n";
		if (WriteToFile(strTmp, "BaseArchive.log") == false) {
			strTmp = strTmp + "	***********-写文件错误-***************\r\n";
			strTmp = strTmp + "------------------------------------\r\n";
		}

	}	
	public boolean WriteToFile(String strTmp, String strFile) {
		try {
			File TmpFile = new File(System.getProperty("user.dir") + File.separator 
					+ strFile);
			if (TmpFile.exists() == false) {
				TmpFile.createNewFile();
			}
			FileWriter TmpFileWriter = new FileWriter(TmpFile, true);
			TmpFileWriter.write(strTmp);
			TmpFileWriter.close();

		} catch (IOException e) {
			System.out.println("	写文件异常！");
			return false;
		}
		return true;

	}
}
