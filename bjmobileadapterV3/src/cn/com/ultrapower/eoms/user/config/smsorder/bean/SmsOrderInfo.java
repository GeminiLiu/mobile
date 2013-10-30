package cn.com.ultrapower.eoms.user.config.smsorder.bean;

/**
 * <p>Description:将工单短信订阅信息封装在javabean中</p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-18
 */
public class SmsOrderInfo {
	
	private String smsOrderId;
	private String smsOrderAction;
	private String smsOrderUserName;
	private String smsOrderFormSchema;
	private String smsOrderUrgent;
	private Long smsOrderStartTime;
	private Long smsOrderEndTime;
	private String smsOrderMemo;
	
	public String getSmsOrderMemo()
	{
		return smsOrderMemo;
	}

	public void setSmsOrderMemo(String smsOrderMemo)
	{
		this.smsOrderMemo = smsOrderMemo;
	}

	public String getSmsOrderAction()
	{
		return smsOrderAction;
	}
	
	public void setSmsOrderAction(String smsOrderAction)
	{
		this.smsOrderAction = smsOrderAction;
	}
	
	public String getSmsOrderFormSchema()
	{
		return smsOrderFormSchema;
	}
	
	public void setSmsOrderFormSchema(String smsOrderFormSchema)
	{
		this.smsOrderFormSchema = smsOrderFormSchema;
	}
	
	public String getSmsOrderId()
	{
		return smsOrderId;
	}
	
	public void setSmsOrderId(String smsOrderId)
	{
		this.smsOrderId = smsOrderId;
	}
	
	public String getSmsOrderUrgent()
	{
		return smsOrderUrgent;
	}
	
	public void setSmsOrderUrgent(String smsOrderUrgent)
	{
		this.smsOrderUrgent = smsOrderUrgent;
	}
	
	public String getSmsOrderUserName()
	{
		return smsOrderUserName;
	}
	
	public void setSmsOrderUserName(String smsOrderUserName)
	{
		this.smsOrderUserName = smsOrderUserName;
	}

	public Long getSmsOrderEndTime()
	{
		return smsOrderEndTime;
	}

	public void setSmsOrderEndTime(Long smsOrderEndTime)
	{
		this.smsOrderEndTime = smsOrderEndTime;
	}

	public Long getSmsOrderStartTime()
	{
		return smsOrderStartTime;
	}

	public void setSmsOrderStartTime(Long smsOrderStartTime)
	{
		this.smsOrderStartTime = smsOrderStartTime;
	}

}
