package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SmsOrderActionMessageBean
{
	String smsorder_username = "";//用户名
	String smsorder_Id   	 = "";//id
	String SmsMessage		 = "";//内容
	String SmssourceId		 = "";//资源id
	String SmsAction		 = "";//动作
	
	
	public String getSmsMessage() {
		return SmsMessage;
	}
	public void setSmsMessage(String smsMessage) {
		SmsMessage = smsMessage;
	}
	public String getSmsorder_Id() {
		return smsorder_Id;
	}
	public void setSmsorder_Id(String smsorder_Id) {
		this.smsorder_Id = smsorder_Id;
	}
	public String getSmsorder_username() {
		return smsorder_username;
	}
	public void setSmsorder_username(String smsorder_username) {
		this.smsorder_username = smsorder_username;
	}
	public String getSmsAction() {
		return SmsAction;
	}
	public void setSmsAction(String smsAction) {
		SmsAction = smsAction;
	}
	public String getSmssourceId() {
		return SmssourceId;
	}
	public void setSmssourceId(String smssourceId) {
		SmssourceId = smssourceId;
	}
	
	
}
