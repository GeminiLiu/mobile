package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.List;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.manager.ScanSmsmonitor;
import com.ultrapower.eoms.common.core.component.sms.manager.SendSmByCMPP;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmonitor;
import com.ultrapower.eoms.common.core.component.sms.service.SendService;
import com.ultrapower.eoms.common.core.util.StringUtils;
/**
 * 查询短信表,调用短信网关进行信息发送
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-2 下午02:46:20
 */
public class Send extends Thread{

	private int maxSendDegree = 3;//最大发送次数(如果调用网关失败,最大再发送n-1次)
	private SendService sendsm = new SendSmByCMPP();
	private ScanSmsmonitor scanSmsmonitor = new ScanSmsmonitor();
	private int count=0;
	private boolean isRun=true;

	public void run() 
	{
		while(isRun)
		{
			count++;
			RecordLog.printLog("短信发送线程：----> 第"+count+" 次调用", RecordLog.LOG_LEVEL_INFO);
			sendSm();
 			try {
				sleep(5000);//睡眠5s
			} catch (InterruptedException e) {
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			} catch(Exception e){
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
 		}
	}
	
	/**
	 * 查询并调用网关协议进行短信发送
	 */
	public void sendSm(){
		List<Smsmonitor> smList = scanSmsmonitor.getWaitingSendSm();
		int smListLen = 0;
		if(smList!=null)
			smListLen = smList.size();
		RecordLog.printLog("扫描短信监控表,获取到待发的短信条数:"+smListLen,RecordLog.LOG_LEVEL_INFO);
		for(int i=0;i<smListLen;i++){
			Smsmonitor smsmonitor = smList.get(i);
			RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",发送---begin",RecordLog.LOG_LEVEL_INFO);
			String result = sendsm.SendSm(StringUtils.checkNullString(smsmonitor.getPid()), StringUtils.checkNullString(smsmonitor.getGoal()), StringUtils.checkNullString(smsmonitor.getContent()));
			if("true".equals(result)){//发送成功
				RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",发送成功,更新短信监控表状态---begin",RecordLog.LOG_LEVEL_INFO);
				boolean updateflag = scanSmsmonitor.updateSm(StringUtils.checkNullString(smsmonitor.getPid()), 1);
				if(updateflag){
					RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",更新短信监控表状态---succeed", RecordLog.LOG_LEVEL_INFO);
				}else{
					RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",更新短信监控表状态---failing", RecordLog.LOG_LEVEL_ERROR);
				}
				RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",更新短信监控表状态---end",RecordLog.LOG_LEVEL_INFO);
			}else{//发送失败
				long firstsendtime = System.currentTimeMillis()/1000;//第一次发送时间
				for(int d=1;d<maxSendDegree;d++){
					RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",尝试进行第"+(d+1)+"发送---begin",RecordLog.LOG_LEVEL_ERROR);
					String resultd = sendsm.SendSm(StringUtils.checkNullString(smsmonitor.getPid()), StringUtils.checkNullString(smsmonitor.getGoal()), StringUtils.checkNullString(smsmonitor.getContent()));
					if("true".equals(resultd)){
						RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",尝试进行第"+(d+1)+"发送成功,更新短信监控表状态---begin",RecordLog.LOG_LEVEL_ERROR);
						boolean updateflag = scanSmsmonitor.updateSm(StringUtils.checkNullString(smsmonitor.getPid()), 1, (d+1), firstsendtime, "", "");
						if(updateflag){
							RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",更新短信监控表状态---succeed", RecordLog.LOG_LEVEL_INFO);
						}else{
							RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",更新短信监控表状态---failing", RecordLog.LOG_LEVEL_ERROR);
						}
						RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",尝试进行第"+(d+1)+"发送成功,更新短信监控表状态---end",RecordLog.LOG_LEVEL_ERROR);
						break;
					}else{
						if(d+1==maxSendDegree){
							RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",尝试进行第"+(d+1)+"发送失败,更新短信监控表状态---begin",RecordLog.LOG_LEVEL_ERROR);
							boolean updateflag = scanSmsmonitor.updateSm(StringUtils.checkNullString(smsmonitor.getPid()), 2, (d+1), firstsendtime, "", "");
							if(updateflag){
								RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",更新短信监控表状态---succeed", RecordLog.LOG_LEVEL_INFO);
							}else{
								RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",更新短信监控表状态---failing", RecordLog.LOG_LEVEL_ERROR);
							}
							RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",尝试进行第"+(d+1)+"发送失败,更新短信监控表状态---end",RecordLog.LOG_LEVEL_ERROR);
						}else{
							RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",尝试进行第"+(d+1)+"发送失败",RecordLog.LOG_LEVEL_ERROR);
						}
					}
					RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",尝试进行第"+(d+1)+"发送---end",RecordLog.LOG_LEVEL_ERROR);
				}
			}
			RecordLog.printLog("短信数据:"+StringUtils.checkNullString(smsmonitor.getPid())+",发送---end",RecordLog.LOG_LEVEL_INFO);
		}
	}
	

	public boolean isRun() 
	{
		return isRun;
	}

	public void setRun(boolean isRun) 
	{
		this.isRun = isRun;
	}
}
