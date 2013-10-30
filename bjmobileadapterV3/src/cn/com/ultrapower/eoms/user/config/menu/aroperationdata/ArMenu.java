package cn.com.ultrapower.eoms.user.config.menu.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.config.menu.bean.MenuBean;

public class ArMenu {

	/**
	 * ���� 2006-10-20
	 * ���� shigang
	 * @param ����ݿ�?������ɾ�Ĳ���
	 * @return void
	 */
	  GetFormTableName  tablename = new GetFormTableName();
	  String            driverurl = tablename.GetFormName("driverurl");
	  String                 user = tablename.GetFormName("user");
	  String             password = tablename.GetFormName("password");
	  int              serverport = Integer.parseInt(tablename.GetFormName("serverport"));
	  String 			  TBLName = tablename.GetFormName("dropdownconfig");

	  ArEdit Ar=new ArEdit(user,password,driverurl,serverport);
	  static final Logger logger = (Logger) Logger.getLogger(ArMenu.class);
	
	//���������ݲ��롣	����menuInfo�ֶ���ϢBean��
    public static ArInfo MenuBean(String strid,String strvalue,String strflag){
    	ArInfo Info = new ArInfo();
		Info.setFieldID(strid);
		Info.setValue(strvalue);
		Info.setFlag(strflag);
		return Info;
    }
    
	public ArrayList menuInsert(MenuBean menuInfo){
		//String  dropDownConfID = menuInfo.getDropDownConf_ID();
		String  dropDownConfFieldID = menuInfo.getDropDownConf_FieldID();
		String  dropDownConfFieldValue = menuInfo.getDropDownConf_FieldValue();
		String  dropDownConfNumValue = menuInfo.getDropDownConf_NumValue();
		String  dropDownConfOrderBy = menuInfo.getDropDownConf_OrderBy();
		String  dropDownConf_TableName = menuInfo.getDropDownConf_TableName();
		
		ArrayList menuInsertArray=new ArrayList();
		
		//menuInsertArray.add(MenuBean("",dropDownConfID,"1"));
		menuInsertArray.add(MenuBean("620000015",dropDownConfFieldID,"1"));
		menuInsertArray.add(MenuBean("620000016",dropDownConfFieldValue,"1"));
		menuInsertArray.add(MenuBean("620000017",dropDownConfNumValue,"1"));
		menuInsertArray.add(MenuBean("620000018",dropDownConfOrderBy,"1"));
		menuInsertArray.add(MenuBean("620000019",dropDownConf_TableName,"1"));
		
		return 	menuInsertArray;	
	}
	//�����������޸�.����menuInfo�û���ϢBean��recordID����¼iD

	public synchronized boolean menuModify(String recordID, MenuBean menuInfo){
		try{
			ArrayList menuModifyValue = new ArrayList();
			menuModifyValue = menuInsert(menuInfo);
			return Ar.ArModify(TBLName,recordID,menuModifyValue);
		}catch(Exception e){
			logger.error("361 ArMenu menuModify error:"+e.toString());
			return false;
		}
	}
	public synchronized boolean menuInsertArray(MenuBean menuInfo){
		try{
			ArrayList menuModifyValue = new ArrayList();
			menuModifyValue = menuInsert(menuInfo);
			return Ar.ArInster(TBLName,menuModifyValue);
		}catch(Exception e){
			logger.error("362 ArMenu menuInsertArray error:"+e.toString());
			return false;
		}
	}
	public synchronized boolean menuDelete(String configId)
	 {
		try{
			return Ar.ArDelete(TBLName,configId);
		 }catch(Exception e){
			logger.error("363 ArMenu menuDelete error:"+e.toString());
			return false;
		}
	 }
	public static void main(String[] args) {
		MenuBean aa=new MenuBean();
		aa.setDropDownConf_FieldID("111");
		aa.setDropDownConf_FieldValue("shigang");
		aa.setDropDownConf_NumValue("shignag");
		aa.setDropDownConf_OrderBy("51");
		
		ArMenu bb=new ArMenu();
		bb.menuModify("000000000000002",aa);
	}
}
