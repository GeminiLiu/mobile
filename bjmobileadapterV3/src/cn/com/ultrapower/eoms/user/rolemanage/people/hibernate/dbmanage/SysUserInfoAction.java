package cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.eoms.user.rolemanage.people.bean.SysUserBean;
import  cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysUserInfo;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.comm.function.Function;
public class SysUserInfoAction {
	static final Logger logger = (Logger) Logger.getLogger(SysUserInfoAction.class);
	/*
	 * 当传过主键为条件时进行查询
	 */
	public List sysUserInfoQuery(String struser){
		try{
			 String 		sql	 		 = "from SysUserInfo sg where sg.c680000001='"+struser+"'";
			 List 			SysUserInfo	 = HibernateDAO.queryObject(sql);
		     return SysUserInfo;
		 }catch(Exception e){
				return null;
		 }
	}
	/*
	 * 根椐id进行查询
	 */
	public SysUserBean sysUserInfoLoad(String UserInfoID){
		try{
			SysUserBean 			SysUserInfo	 = (SysUserBean)HibernateDAO.loadStringValue(SysUserInfo.class,UserInfoID);
		    return SysUserInfo;
		 }catch(Exception e){
				return null;
		 }
	}
	/*
	 * 进行修改
	 */
	public boolean sysUserInfoModify(SysUserInfo sysUserInfo){
		try{
			HibernateDAO.modify(sysUserInfo);
		    return true;
		 }catch(Exception e){
			return true;
		 }
	}
	/*
	 * 进行插入
	 */
	public synchronized boolean sysUserInfoInsert(SysUserInfo sysUserInfo){
		try{
				if(HibernateDAO.insert(sysUserInfo)){
					logger.error("进行插入成功");
				}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/*
	 * 传过来的Bean
	 */
	public synchronized boolean SysUserInfoInsert(SysUserBean sysUserBean){
		try{
			SysUserInfo	sysUserInfo=new SysUserInfo();
			sysUserInfo.setC680000001(sysUserBean.getUserInfo_ID());
			sysUserInfo.setC680000002(sysUserBean.getUserInfo_Gender())	;
			sysUserInfo.setC680000003(sysUserBean.getUserInfo_birthday())	;
			sysUserInfo.setC680000004(sysUserBean.getUserInfo_native_place())	;
			sysUserInfo.setC680000005(sysUserBean.getUserInfo_ID_Card())	;
			sysUserInfo.setC680000006(sysUserBean.getUserInfo_educational_level())	;
			sysUserInfo.setC680000007(sysUserBean.getUserInfo_alma_mater())	;
			sysUserInfo.setC680000008(sysUserBean.getUserInfo_current_post())	;
			sysUserInfo.setC680000009(sysUserBean.getUserInfo_current_job())	;
			sysUserInfo.setC680000010(sysUserBean.getUserInfo_room());
			sysUserInfo.setC680000011(sysUserBean.getUserInfo_remark());
			sysUserInfo.setC680000012(sysUserBean.getUserInfo_Dep_Name_1And2());
			
			sysUserInfo.setC680000013(sysUserBean.getUser_Mobie());
			sysUserInfo.setC680000014(sysUserBean.getUser_Phone());
			sysUserInfo.setC680000015(sysUserBean.getUser_Mail());
			sysUserInfo.setC680000016(sysUserBean.getUser_Mail());
			
			sysUserInfo.setC680000017(sysUserBean.getUser_Status());
			sysUserInfo.setC680000018(sysUserBean.getMemo1());
			sysUserInfo.setC680000019(sysUserBean.getMemo2());
			sysUserInfo.setC680000020(sysUserBean.getMemo3());
			sysUserInfo.setC680000021(sysUserBean.getMemo4());
			
			if (sysUserInfoInsert(sysUserInfo)){
				return true;
			}else{
				return false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	 GetFormTableName  tablename = new GetFormTableName();
	  String            driverurl = tablename.GetFormName("driverurl");
	  String                 user = tablename.GetFormName("user");
	  String             password = tablename.GetFormName("password");
	  int              serverport = Integer.parseInt(tablename.GetFormName("serverport"));
	  String 			  TBLName = tablename.GetFormName("SysUserInfo");

	  ArEdit Ar=new ArEdit(user,password,driverurl,serverport);
	
   public static ArInfo MenuBean(String strid,String strvalue,String strflag){
   	ArInfo Info = new ArInfo();
		Info.setFieldID(strid);
		Info.setValue(strvalue);
		Info.setFlag(strflag);
		return Info;
   }
   public ArrayList SysUserBeanInsert(SysUserBean sysUserBean){

		ArrayList menuInsertArray=new ArrayList();
		
		menuInsertArray.add(MenuBean("680000001",Function.nullString(sysUserBean.getUserInfo_LoginName()),"1"));
		menuInsertArray.add(MenuBean("680000002",Function.nullString(sysUserBean.getUserInfo_Gender()),"1"));
		menuInsertArray.add(MenuBean("680000003",Function.nullString(sysUserBean.getUserInfo_birthday()),"1"));
		menuInsertArray.add(MenuBean("680000004",Function.nullString(sysUserBean.getUserInfo_native_place()),"1"));
		menuInsertArray.add(MenuBean("680000005",Function.nullString(sysUserBean.getUserInfo_ID_Card()),"1"));
		
		menuInsertArray.add(MenuBean("680000006",Function.nullString(sysUserBean.getUserInfo_educational_level()),"1"));
		menuInsertArray.add(MenuBean("680000007",Function.nullString(sysUserBean.getUserInfo_alma_mater()),"1"));
		menuInsertArray.add(MenuBean("680000008",Function.nullString(sysUserBean.getUserInfo_current_post()),"1"));
		menuInsertArray.add(MenuBean("680000009",Function.nullString(sysUserBean.getUserInfo_current_job()),"1"));
	
		menuInsertArray.add(MenuBean("680000010",Function.nullString(sysUserBean.getUserInfo_room()),"1"));
		menuInsertArray.add(MenuBean("680000011",Function.nullString(sysUserBean.getUserInfo_remark()),"1"));
		menuInsertArray.add(MenuBean("680000012",Function.nullString(sysUserBean.getUserInfo_Dep_Name_1And2()),"1"));
		menuInsertArray.add(MenuBean("680000013",Function.nullString(sysUserBean.getUser_Mobie()),"1"));
		
		menuInsertArray.add(MenuBean("680000014",Function.nullString(sysUserBean.getUser_Phone()),"1"));
		menuInsertArray.add(MenuBean("680000015",Function.nullString(sysUserBean.getUser_Fax()),"1"));
		
		menuInsertArray.add(MenuBean("680000016",Function.nullString(sysUserBean.getUser_Mail()),"1"));
		menuInsertArray.add(MenuBean("680000017",Function.nullString(sysUserBean.getUser_Status()),"1"));
		menuInsertArray.add(MenuBean("680000018",Function.nullString(sysUserBean.getMemo1()),"1"));
		menuInsertArray.add(MenuBean("680000019",Function.nullString(sysUserBean.getMemo2()),"1"));
		menuInsertArray.add(MenuBean("680000020",Function.nullString(sysUserBean.getMemo3()),"1"));
		menuInsertArray.add(MenuBean("680000021",Function.nullString(sysUserBean.getMemo4()),"1"));
		
		return 	menuInsertArray;	
	}
	/*
	 * 用ar的进行修改
	 */
	public synchronized boolean menuModify(String recordID, SysUserBean sysUserBean){
		try{
			ArrayList menuModifyValue = new ArrayList();
			menuModifyValue = SysUserBeanInsert(sysUserBean);
			return Ar.ArModify(TBLName,recordID,menuModifyValue);
		}catch(Exception e){
			logger.error("361 ArMenu menuModify error:"+e.toString());
			return false;
		}
	}
	/*
	 * 用ar的进行修改
	 */
	public synchronized boolean menuInsertArray(SysUserBean sysUserBean){
		try{
			ArrayList menuModifyValue = new ArrayList();
			menuModifyValue = SysUserBeanInsert(sysUserBean);
			return Ar.ArInster(TBLName,menuModifyValue);
		}catch(Exception e){
			logger.error("362 ArMenu menuInsertArray error:"+e.toString());
			return false;
		}
	}
}
