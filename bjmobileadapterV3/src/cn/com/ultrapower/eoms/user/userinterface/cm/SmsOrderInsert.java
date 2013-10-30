package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SmsOrderActionMessageBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SmsOrderMessageBean;

public class SmsOrderInsert 
{
	private final static String smsmonitor= "smsmonitor";
	private final static String smsid= "sms_id";
	/* (non-Javadoc)
	 * @see cn.com.ultrapower.interfacesms.ISmsOrder#insertSms(java.lang.Object)
	 */
    public synchronized int insertSms(Object ob)
    {
    	String smsmonitor_content = "";//短信内容
    	String smsmonitor_goal    = "";//发送对象
    	String smsmonitor_sendto  = "";//当是邮件时的抄送对象
    	String smsmonitor_type    = ""; //类型，0短信，1邮件  
    	Long smsmonitor_id        = null;//id
    	String monitorid          = "";
    	int j=0;
    	smsmonitor_id             = new Long(Function.getNewID(smsmonitor,"smsid"));
    	monitorid                 = smsmonitor_id.toString();
    	SmsOrderBean smsorderbean = (SmsOrderBean)ob;
      	smsmonitor_content  = smsorderbean.getSmsmonitor_content();
    	smsmonitor_goal     = smsorderbean.getSmsmonitor_goal();
    	smsmonitor_sendto   = smsorderbean.getSmsmonitor_sendto();
    	smsmonitor_type     = smsorderbean.getSmsmonitor_type();
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet res		= null;
		stm	= dataBase.GetStatement();
		//res	= dataBase.executeResultSet(stm,sql);
        String sql             = "insert into smsmonitor(smsmonitor_id,smsmonitor_content,smsmonitor_type,smsmonitor_goal,smsmonitor_sendflag,smsmonitor_maxnum,smsmonitor_tablename) " +
        		                 "values('"+monitorid+"','"+smsmonitor_content+"','"+smsmonitor_type+"','"+smsmonitor_goal+"',2,3,'资料管理短信订阅')";
        j = dataBase.executeNonQuery(stm,sql);
        return j;
    }
	/**
	 * date 2007-3-13
	 * author shigang
	 * @param args
	 * @return 根椐动做进行手机插入
	 * @throws HibernateException 
	 */
	//根椐登录id去查用户表的id在把相应的手机号密码发给用户
	public boolean insertsmsActionmessage(SmsOrderActionMessageBean smsOrderActionMessage) throws HibernateException{
		try{	
			 SmsOrderBean smsOrderBean =new SmsOrderBean();
			 smsOrderBean.setSmsmonitor_type("0");
			 //当传过来的登陆名为空时查询反之用id号查询
			 String sql="";
			 if (!smsOrderActionMessage.getSmsorder_username().equals("")){
				sql="from SysPeoplepo user where user.c630000001='"+smsOrderActionMessage.getSmsorder_username()+"'";
			 }else if(smsOrderActionMessage.getSmsorder_Id()!=null) {
				sql="from SysPeoplepo user where user.c1='"+Function.getStrZeroConvert(smsOrderActionMessage.getSmsorder_Id())+"'";
			 }
			 List 	   			 list	 = HibernateDAO.queryObject(sql);
			
			 if (list.size()>0){
				 Iterator it=list.iterator();
				 while(it.hasNext()){
					 SysPeoplepo 	user			= (SysPeoplepo)it.next();
					
					 smsOrderBean.setSmsmonitor_goal(user.getC630000008());
				 }
				 smsOrderBean.setSmsmonitor_content(smsOrderActionMessage.getSmsMessage());//传入信息
			 }else{
				 //如果当前用户不存在将返回空
				 return false;
			 }
			 if(insertSmsOrder(smsOrderBean)>0){
				 return true;
			 }else{
				 return false;
			 }
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * date 2007-3-13
	 * author shigang
	 * @param args
	 * @return void
	 * @throws HibernateException 
	 */
	//根椐登录id去查用户表的id在把相应的手机号密码发给用户
	public boolean insertsmsmessage(SmsOrderMessageBean smsOrderMessage) throws HibernateException{
		try{	
			 SmsOrderBean smsOrderBean =new SmsOrderBean();
			 smsOrderBean.setSmsmonitor_type("0");
			 //当传过来的登陆名为空时查询反之用id号查询
			 String sql="";
			 if (!smsOrderMessage.getSmsorder_username().equals("")){
				sql="from SysPeoplepo user where user.c630000001='"+smsOrderMessage.getSmsorder_username()+"'";
			 }else if(smsOrderMessage.getSmsorder_Id()!=null) {
//				sql="from SysPeoplepo user where user.c1='"+smsOrderMessage.getSmsorder_Id()+"'";
				sql="from SysPeoplepo user where user.c1='"+Function.getStrZeroConvert(smsOrderMessage.getSmsorder_Id())+"'";

			 }
			 List 	   			 list	 = HibernateDAO.queryObject(sql);
			
			 if (list.size()>0){
				 Iterator it=list.iterator();
				 while(it.hasNext()){
					 SysPeoplepo 	user			= (SysPeoplepo)it.next();
					
					 smsOrderBean.setSmsmonitor_goal(user.getC630000008());
				 }
				 smsOrderBean.setSmsmonitor_content(smsOrderMessage.getSmsMessage());//传入信息
			 }else{
				 //如果当前用户不存在将返回空
				 return false;
			 }
			 if(!smsOrderBean.getSmsmonitor_goal().equals("")){
				 if(insertSmsOrder(smsOrderBean)>0){
					 return true;
				 }else{
					 return false;
				 }
			 }else{
				 return false;
			 }
		}catch(Exception e){
			return false;
		}
	}
//	插入用户名和保存
	public int insertSmsOrder(SmsOrderBean smsOrderBean){
		SmsOrderInsert	smsOrderInsert=new SmsOrderInsert();
		return smsOrderInsert.insertSms(smsOrderBean);
	}
	public static void main(String[] args) throws HibernateException {
		SmsOrderMessageBean smsOrderMessage=new SmsOrderMessageBean();
		smsOrderMessage.setSmsorder_Id("00000000002");
//		smsOrderMessage.setSmsorder_username("Demo");
		smsOrderMessage.setSmsMessage("aaaaaaaaww");
		SmsOrderInsert SmsOrderInsert=new SmsOrderInsert();
		SmsOrderInsert.insertsmsmessage(smsOrderMessage);
	
	}
}
