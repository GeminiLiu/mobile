package com.ultrapower.eoms.common.core.component.sms.web;

import com.ultrapower.eoms.common.core.component.sms.manager.Send;
import com.ultrapower.eoms.common.core.web.BaseAction;

/**
 * 短信发送主函数入口
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-2 下午04:24:15
 */
public class SmSendServerMainAction extends BaseAction{
	
		public String status;//启动状态
		public static Send sendsm = null;
		
		/**
		 * 短信发送查询页面
		 * @return
		 */
		public String SmQueryList(){
			return SUCCESS;
		}
		
		/**
		 * 调用短信管理页面
		 * @return
		 */
		public String smsManager(){
			if(sendsm!=null)
				status = "0";
			else
				status = "1";
			return SUCCESS;
		}
		
		
		/**
		 * 启动
		 */
		public String startSm(){
			if(sendsm!=null){//重启
				sendsm.setRun(false);
				sendsm=null;
			}
			sendsm = new Send();
			sendsm.start();
			status = "0";//启动
			return SUCCESS;
		}
		
		/**
		 * 停止
		 */
		public String stopSm(){
			if(sendsm!=null){
				sendsm.setRun(false);
				sendsm=null;
			}
			status = "1";//停止
			return SUCCESS;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
}
