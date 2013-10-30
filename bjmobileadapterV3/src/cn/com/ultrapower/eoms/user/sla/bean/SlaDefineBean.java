package cn.com.ultrapower.eoms.user.sla.bean;

/**
 *�����
 * @author xuquanxing
 * @version 1.0
 *该类是用于封装新建的sla定义的内容
 */
public class SlaDefineBean 
{
   private String sla_name;
   private String sla_startdatetime;
   private String sla_enddatetime;
   private String sla_status;
   private String sla_apptable;
   private String sla_appcommstartquery;
   private String sla_appcommendquery;
   private String sla_usercommquery;
   
    
    
	/**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ��徐全星
	 * @return String
	 */
	public String getSla_appcommendquery() {
		return sla_appcommendquery;
	}
	
	
	/**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ��徐全星 
	 * @param sla_appcommendquery void
	 */
	public void setSla_appcommendquery(String sla_appcommendquery)
	{
		this.sla_appcommendquery = sla_appcommendquery;
	}
	public String getSla_appcommstartquery()
	{
		return sla_appcommstartquery;
	}
	public void setSla_appcommstartquery(String sla_appcommstartquery)
	{
		this.sla_appcommstartquery = sla_appcommstartquery;
	}
	public String getSla_apptable()
	{
		return sla_apptable;
	}
	public void setSla_apptable(String sla_apptable) 
	{
		this.sla_apptable = sla_apptable;
	}
	public String getSla_enddatetime() 
	{
		return sla_enddatetime;
	}
	public void setSla_enddatetime(String sla_enddatetime)
	{
		this.sla_enddatetime = sla_enddatetime;
	}
	public String getSla_name()
	{
		return sla_name;
	}
	public void setSla_name(String sla_name) 
	{
		this.sla_name = sla_name;
	}
	public String getSla_startdatetime() 
	
	{
		return sla_startdatetime;
	}
	public void setSla_startdatetime(String sla_startdatetime)
	{
		this.sla_startdatetime = sla_startdatetime;
	}
	public String getSla_status() 
	{
		return sla_status;
	}
	public void setSla_status(String sla_status) 
	{
		this.sla_status = sla_status;
	}
	public String getSla_usercommquery()
	{
		return sla_usercommquery;
	}
	public void setSla_usercommquery(String sla_usercommquery) 
	{
		this.sla_usercommquery = sla_usercommquery;
	}
}
