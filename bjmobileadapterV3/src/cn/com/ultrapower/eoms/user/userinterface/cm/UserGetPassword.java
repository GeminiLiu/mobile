package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import cn.com.ultrapower.eoms.user.comm.function.*;
import org.hibernate.HibernateException;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

public class UserGetPassword {

	/**
	 * date 2007-3-13
	 * author shigang
	 * @param args
	 * @return void
	 * @throws HibernateException 
	 */
	//根椐登录id去查用户表的id在把相应的手机号密码发给用户
	public String sendPassword(String username) throws HibernateException{
		 String smsgoal      = "";
		 String smscontent   = "";
		 String senddate     = "";
		 String feedmes      = "";
		 boolean  issuc      = false;   
		 Statement stmt      = null;
		 ResultSet res       = null;
		 IDataBase dataBase  = null;
		try{	
			 SmsOrderBean smsOrderBean =new SmsOrderBean();
			 smsOrderBean.setSmsmonitor_type("0");
			 String sql="from SysPeoplepo user where user.c630000001='"+username+"'";
			 List 	   			 list	 = HibernateDAO.queryObject(sql);
			 Iterator it=list.iterator();
			 while(it.hasNext()){
				 SysPeoplepo 	user			= (SysPeoplepo)it.next();
				 if (String.valueOf(user.getC630000002()).equals("null")||String.valueOf(user.getC630000002()).equals("")){
					 smsOrderBean.setSmsmonitor_content("您的密码为空,请及时设置密码");
					 smscontent = "您的密码为空,请及时设置密码";
				 }else{
					 smsOrderBean.setSmsmonitor_content("您的密码:"+user.getC630000002()+" 请妥善保管");
					 smscontent  = "您的密码:"+user.getC630000002()+" 请妥善保管";
				 }
				 smsgoal     = user.getC630000008();
				 smsOrderBean.setSmsmonitor_goal(smsgoal);
			 }
			 //------------start---------------xuquanxig add the password conditon, only one time for one day,2007-04-04-----------------------------------------------------------------------
			 String    passsql  = "select sms.smsmonitor_sendtime from smsmonitor  sms where sms.smsmonitor_goal='"+smsgoal+"' and ( sms.smsmonitor_content like '您的密码%' or sms.smsmonitor_content = '您的密码为空,请及时设置密码')";
			 dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			 stmt	= dataBase.GetStatement();
			 res     = stmt.executeQuery(passsql);
			 while(res.next())
			 {
				 senddate = res.getString("smsmonitor_sendtime")+"000";
				 if(Function.Unixto_date(senddate).equals(Function.NowDate()))//表明该条信息今天已被发送过
				 {
					 issuc   = true;//表明今天发送过
				 }
			 }
			 res.close();
			 if(issuc)
			 {
				 feedmes = "抱歉!您今天已经获取过一次密码";
			 }else
			 {
				 if(insertPassword(smsOrderBean)>0){
					 feedmes = "获取密码成功";
				 }else{
					 feedmes = "获取密码失败,请稍候再试";
				 }
			 }
			 //--------------end-----------2007-04-04-------------------------------------------------------------------------
		}catch(Exception e){
			return  feedmes = "获取密码失败,请稍候再试";
		}finally
		{
			Function.closeDataBaseSource(null,stmt,dataBase);
		}
		return feedmes;
	}
	//通过接口插入数据
	public int insertPassword(SmsOrderBean smsOrderBean){
		SmsOrderInsert	smsOrderInsert=new SmsOrderInsert();
		return smsOrderInsert.insertSms(smsOrderBean);
	}
	
	
	public static void main(String[] args) throws HibernateException {
		UserGetPassword UserGetPassword=new UserGetPassword();
		UserGetPassword.sendPassword("luohaibo");
	}

}
