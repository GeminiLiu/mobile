package cn.com.ultrapower.eoms.util.user;

import cn.com.ultrapower.eoms.common.basedao.GeneralDAO;
import cn.com.ultrapower.eoms.common.basedao.GeneralException;
import java.util.*;
import cn.com.ultrapower.eoms.util.*;
import cn.com.ultrapower.eoms.util.user.*;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.userinterface.cm.PersonalOperation;
import cn.com.ultrapower.eoms.user.userinterface.*;
/**本类为修改用户密码 
 * @author zhaoqi
 *
 */

public class UserPasswordFixDAO  extends GeneralDAO{
	//获得个人信息方法
	public Vector getPasswordById(String uid){
		StringBuffer stringbuffer=new StringBuffer();			
		String sqlstr =" from SysPeoplepo sp where sp.c1='"+uid+"'";	
		stringbuffer.append(sqlstr);		
		try {
			Vector vec=GeneralDAO.loadObjects(stringbuffer.toString());
			if(null!=vec || !"".equals(vec))
			return vec;
			
			return null;
		}
		 catch (GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//ArLog.insertLog(session.getAttribute("userLoginName").toString(),ArLogModule.DUTY_WORK_LIST,"info:" + stringbuffer.toString());
			return null;
		}
		
	}
	///更新个人信息方法
	public void updatePassword(UserInformation sp){
	try{
		PeopleInfo userInfo = new PeopleInfo();
        userInfo.setUserId(sp.getUserID());
        userInfo.setUserFullname(sp.getUserFullName());				
        userInfo.setUserBelongGroupId(sp.getUserBelongGroupId());
        userInfo.setUserCpid(sp.getUserCPID());
        userInfo.setUserCptype(sp.getUserCPType());
        userInfo.setUserCreateuser(sp.getUserCreateuser());
        userInfo.setUserDpid(sp.getUserDPID());
        userInfo.setUserFax(sp.getUserFax());
        userInfo.setUserIntId(new Long(sp.getUserIntID()).intValue());
        userInfo.setUserIsmanager(sp.getUserIsManager());
        userInfo.setUserLicensetype(sp.getUserLicenseType());
        userInfo.setUserLoginname(sp.getUserLoginName());
        userInfo.setUserMail(sp.getUserMail());
        userInfo.setUserMobie(sp.getUserMobie());
        userInfo.setUserOrderby(sp.getUserOrderBy());
        userInfo.setUserPassword(sp.getUserPassWord());
        userInfo.setUserPhone(sp.getUserPhone());
        userInfo.setUserPosition(sp.getUserPosition());
        userInfo.setUserStatus(sp.getUserStatus());
        userInfo.setUserType(sp.getUserType());
        PersonalModifyInterface pmi = new PersonalModifyInterface();
        pmi.ModifyPeopleInfo(userInfo);
        
//        GeneralDAO.updateObject(userInfo);	
        Log.logger.info(ArLogModule.INDEX+"个人信息修改成功!");
	}catch(Exception e){
		e.printStackTrace();
		Log.logger.error(ArLogModule.INDEX+"输入新密码和确认密码不符!");
	}
	}
	///将页面传来的各个参数进行判断，并调用更新个人信息方法，并返回信息到页面
	public String changePassword(String oldpass,String newpass,String conpass,UserInformation ui){
		String result="";
		String temppass="";
		SysPeoplepo  sp1 = null;
//		System.out.println(uid);		
		UserInformation userinfo = ui;
		UserPasswordFixDAO upf = new UserPasswordFixDAO();
		Vector vec  = upf.getPasswordById(ui.getUserID());
		if(vec.size()> 0){
			sp1 = (SysPeoplepo)vec.get(0);
			temppass = StringUtil.checkNull(sp1.getC630000002());
		}
		if(temppass.equals(oldpass)){
			if(!newpass.equals(conpass)){ 
				
				result="输入新密码和确认密码不符！";
				Log.logger.info(ArLogModule.INDEX+"输入新密码和确认密码不符!");
			}else{
				try{
				userinfo.setUserPassWord(newpass);
				upf.updatePassword(userinfo);
				result="修改密码成功！";
				Log.logger.info(ArLogModule.INDEX+"密码修改成功!");
				}catch(Exception e){
					e.printStackTrace();
					result="发生异常！";
					Log.logger.error(ArLogModule.INDEX+"发生异常!");
				}
			}
		}else{
			result = "输入旧密码错误！";		
			Log.logger.info(ArLogModule.INDEX+"输入旧密码错误!");
		}
		return result;
	}
	public static void main(String[] args) {
		UserPasswordFixDAO test = new UserPasswordFixDAO();
		Vector vec = test.getPasswordById("000000000000010");
//		System.out.println(list.size());
//		for(int i=0;i<list.size();i++){
//			Object[] results = (Object[])list.get(i);
			//for (int j=0;j<results.length;j++){
				System.out.println(vec.size());
				//break;
			//}
//		}
		// TODO Auto-generated method stub

	}
	
}
