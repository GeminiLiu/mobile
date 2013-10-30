package cn.com.ultrapower.eoms.user.sla.bean;

/**
 * @author xuquanxing/徐全星
 * 该类用于封装邮件动作的内容
 * */
public class ActionMailCreateBean 
{
	private String mail_slaid      = "";//mail所属的slaid
	private String mail_sendtouser = "";//发送对象���Ͷ���
	private String mail_content    = "";//发送内容����
	private String mail_sendquery  = "";//执行条件
	private String mail_copysendto = "";//抄送对象����Ͷ���
	private String mail_actionid   = "";//mail所属的动作类型id
	
	/**
	 * @return Returns the mail_content.
	 */
	public String getMail_content()
	{
		return mail_content;
	}
	
	/**
	 * @param mail_content The mail_content to set.
	 */
	public void setMail_content(String mail_content)
	{
		this.mail_content = mail_content;
	}
	
	/**
	 * @return Returns the mail_copysendto.
	 */
	public String getMail_copysendto() 
	{
		return mail_copysendto;
	}
	
	/**
	 * @param mail_copysendto The mail_copysendto to set.
	 */
	public void setMail_copysendto(String mail_copysendto) 
	{
		this.mail_copysendto = mail_copysendto;
	}
	
	/**
	 * @return Returns the mail_sendquery.
	 */
	public String getMail_sendquery()
	{
		return mail_sendquery;
	}
	
	/**
	 * @param mail_sendquery The mail_sendquery to set.
	 */
	public void setMail_sendquery(String mail_sendquery)
	{
		this.mail_sendquery = mail_sendquery;
	}
	
	/**
	 * @return Returns the mail_sendtouser.
	 */
	public String getMail_sendtouser() 
	{
		return mail_sendtouser;
	}
	
	/**
	 * @param mail_sendtouser The mail_sendtouser to set.
	 */
	public void setMail_sendtouser(String mail_sendtouser)
	{
		this.mail_sendtouser = mail_sendtouser;
	}
	
	/**
	 * @return Returns the mail_slaid.
	 */
	public String getMail_slaid()
	{
		return mail_slaid;
	}
	
	/**
	 * @param mail_slaid The mail_slaid to set.
	 */
	public void setMail_slaid(String mail_slaid) 
	{
		this.mail_slaid = mail_slaid;
	}

	/**
	 * @return Returns the mail_actionid.
	 */
	public String getMail_actionid() 
	{
		return mail_actionid;
	}

	/**
	 * @param mail_actionid The mail_actionid to set.
	 */
	public void setMail_actionid(String mail_actionid) 
	{
		this.mail_actionid = mail_actionid;
	}

}
