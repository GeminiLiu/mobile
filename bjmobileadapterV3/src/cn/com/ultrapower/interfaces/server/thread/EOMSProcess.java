package cn.com.ultrapower.interfaces.server.thread;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import cn.com.ultrapower.eoms.processSheet.Contents;
import cn.com.ultrapower.eoms.processSheet.CustomProcessInterface;
import cn.com.ultrapower.eoms.processSheet.InterfaceCfg;
import cn.com.ultrapower.eoms.util.CalendarUtil;
import cn.com.ultrapower.interfaces.client.CrmProcessSheetPortType;
import cn.com.ultrapower.interfaces.util.AttachUtil;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.interfaces.util.*;
public class EOMSProcess {
	private static CustomProcessInterface customProcessInterface = new CustomProcessInterface();
	public static void callprocess(){
		//创建webservices调用服务
//		String isAlive = "0";//调试增加
		System.out.println("申告工单扫描开始");
		String url = customProcessInterface.getUrls();
		CrmProcessSheetPortType  localEoms = null;
        Service srvcModel = new ObjectServiceFactory().create(CrmProcessSheetPortType.class);
        XFireProxyFactory factory =new XFireProxyFactory(XFireFactory.newInstance().getXFire());
		String isAlive = "1";
        try {
			localEoms = (CrmProcessSheetPortType)factory.create(srvcModel, url);
			isAlive = localEoms.isAlive("56",CalendarUtil.getCurrentDateTime24());
		} catch (Exception e1) {
			System.out.println("isAlive=" + isAlive);
			System.out.println("e="+e1.getMessage());
			return;
		}		
		
		//如果返回值表明服务可以用，则开始调用接口
		if(isAlive.equals("0")){
			customProcessInterface = new CustomProcessInterface();
			RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
			String strTblName=m_RemedyDBOp.GetRemedyTableName(customProcessInterface.getPrefix()+ Contents.BMCSPLIT + customProcessInterface.getQuatzTable());
			String sqlCycleP = "select c1,c802043051,c802043006,c802043010,c802043012,c802043013,c802043011,c802043014 from " +strTblName+ " t where t.c802043009 = 0 order by c1 ";
			String sqlCycle = PageControl.GetSqlStringForPagination(sqlCycleP,"ORACLE",1, 10);
			System.out.println(sqlCycle);
			
			// 轮询
			AttachUtil attachUtil  = new AttachUtil();
			try {
				IDatabaseDAO db=new IDatabaseDAO();
				List list=db.executeQuery(sqlCycle);
				Iterator iter=list.iterator();
				while(iter.hasNext()){
					HashMap hm=(HashMap)iter.next();
					StringBuffer sb =  new StringBuffer("<opDetail>");
					String c1 = (String)hm.get("C1");
					String baseId = (String)hm.get("C802043051");
					String action = (String)hm.get("C802043006");
					String serialNo = (String)hm.get("C802043010");
					String opPerson = (String)hm.get("C802043012");
					String opDepart = (String)hm.get("C802043013");
					String opContact = (String)hm.get("C802043011");
					String opTime = (String)hm.get("C802043014");
					
					
					// 查询具体数据
					String selectSql = generateSelectSql(Contents.PERSONPROCESS,action,baseId);
					System.out.println(selectSql);
					List listdetail=db.executeQuery(selectSql);
					Iterator iterdetail=listdetail.iterator();
					while(iterdetail.hasNext()){
						HashMap hmdetail=(HashMap)iterdetail.next();
						InterfaceCfg interfaceCfg = new InterfaceCfg();
						List<Map<String,String>> listcfg = interfaceCfg.getElements(Contents.PERSONPROCESS, action);
						if(Contents.CONFIRMWORKSHEET.equals(action)){//如果是受理，则为固定的opDetail
							sb.append(Contents.ACCEPTDETAIL);
						}else{
							if(listcfg != null){
								sb.append("<recordInfo>");
								for(int i = 0 ; i < listcfg.size() ; i++){
									String column = (String)listcfg.get(i).get(Contents.FIELDCOLUMN);
									String enName = (String)listcfg.get(i).get(Contents.FIELDENNAME);
									String chName = (String)listcfg.get(i).get(Contents.FIELDCHNAME);
									
									sb.append("<fieldInfo>");
									sb.append("<fieldChName>");
									sb.append(chName);
									sb.append("</fieldChName>");
									
									sb.append("<fieldEnName>");
									sb.append(enName);
									sb.append("</fieldEnName>");
									
									sb.append("<fieldContent>");
									String value = "";
									if(hmdetail.get("C"+column)!=null){
										value = hmdetail.get("C"+column).toString();
									}
									if("isReplied".equals(enName)){//是否已经回复客户的转化
										if("1".equals(value)){
											sb.append("是");
										}else{
											sb.append("否");
										}
									}else{
										sb.append(value);
									}
									sb.append("</fieldContent>");
									
									sb.append("</fieldInfo>");
								}
								sb.append("</recordInfo>");
							}
						}
						sb.append("</opDetail>");
						System.out.println("opDetail" + sb.toString());
					}
					// 调用服务
					
			        try{
//			            String returnNumber = "0";//调试增加
			            String returnNumber = "1";
			            if(Contents.CONFIRMWORKSHEET.equals(action)){
			            	Map<String,String> map = customProcessInterface.getMethodMap(Contents.CONFIRMWORKSHEET);
			            	returnNumber = localEoms.confirmWorkSheet(56, 90, serialNo, map.get(Contents.SUPPLIER),map.get(Contents.CALLER), map.get(Contents.CALLERPWD), CalendarUtil.getCurrentDateTime24(), attachUtil.getFtpPath("WF:EL_TTM_CCH",baseId), opPerson, map.get(Contents.OPCORP),opDepart, opContact, opTime, sb.toString());
			            }else if(Contents.NOTIFYWORKSHEET.equals(action)){
			            	Map<String,String> map = customProcessInterface.getMethodMap(Contents.NOTIFYWORKSHEET);
			            	returnNumber = localEoms.notifyWorkSheet(56, 90, serialNo, map.get(Contents.SUPPLIER),map.get(Contents.CALLER), map.get(Contents.CALLERPWD), CalendarUtil.getCurrentDateTime24(), attachUtil.getFtpPath("WF:EL_TTM_CCH",baseId), opPerson, map.get(Contents.OPCORP),opDepart, opContact, opTime, sb.toString());
			            }else if(Contents.REPLYWORKSHEET.equals(action)){
			            	Map<String,String> map = customProcessInterface.getMethodMap(Contents.REPLYWORKSHEET);
			            	returnNumber = localEoms.replyWorkSheet(56, 90, serialNo, map.get(Contents.SUPPLIER),map.get(Contents.CALLER), map.get(Contents.CALLERPWD), CalendarUtil.getCurrentDateTime24(), attachUtil.getFtpPath("WF:EL_TTM_CCH",baseId), opPerson, map.get(Contents.OPCORP),opDepart,opContact,opTime , sb.toString());
			            }else if(Contents.WITHDROWWORKSHEET.equals(action)){
			            	Map<String,String> map = customProcessInterface.getMethodMap(Contents.WITHDROWWORKSHEET);
			            	returnNumber = localEoms.withdrawWorkSheet(56, 90, serialNo, map.get(Contents.SUPPLIER),map.get(Contents.CALLER), map.get(Contents.CALLERPWD), CalendarUtil.getCurrentDateTime24(), attachUtil.getFtpPath("WF:EL_TTM_CCH",baseId), opPerson, map.get(Contents.OPCORP),opDepart, opContact,opTime , sb.toString());
			            }

//			            System.out.println("sb="  + sb.toString());
//			            System.out.println("returnNumber="  + returnNumber);
			            if("0".equals(returnNumber)){//调用成功， 更新数据
			            	String updateSql = generateUpdateSql( Contents.PREFIX + Contents.BMCSPLIT + Contents.QUATZTABLE ,c1);
			            	int a = db.executeUpdate(updateSql);
							if(a>0){
								System.out.println("申告工单扫描处理完成--success！！");
							}
			            }
			        }
			        catch (Exception e){
			          e.printStackTrace();
			        }
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}		
		}
		
	}
	private static String generateSelectSql(String sheetName,String dealType,String strWhere){
		StringBuffer sb = new StringBuffer("SELECT ");
		InterfaceCfg incfg = new InterfaceCfg();
		List<Map<String,String>> cfgList = incfg.getElements(sheetName, dealType);
		if(cfgList != null){
			int flag = 0;
			for(int i=0 ; i< cfgList.size(); i ++){
				if(!Contents.DISPLAY.equals(cfgList.get(i).get(Contents.FIELDTYPE))){
					
					if(flag > 0){
						sb.append(" , ");
					}
					sb.append("C");
					sb.append((cfgList.get(i)).get(Contents.FIELDCOLUMN));
					flag ++;
				}
			}
		}
		sb.append(" FROM ");
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(customProcessInterface.getPrefix() + Contents.BMCSPLIT + customProcessInterface.getBmcTable());
		
		sb.append(strTblName);
		sb.append(" t WHERE  t.C1='"+strWhere+"'");
		return sb.toString();
	}
	
	
	
	/**
	 * 通过配置文件，生成更新的sql
	 * @param sheetName
	 * @param dealType
	 * @param serialNum
	 * @return
	 */
	private static String generateUpdateSql(String sheetName,String id){
		CustomProcessInterface customProcessInterface = new CustomProcessInterface();
		String sql = customProcessInterface.getQuatzUpdate();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(customProcessInterface.getPrefix() + Contents.BMCSPLIT + customProcessInterface.getQuatzTable());
		String sqltemp = sql.replace(Contents.QUATZTABLE,strTblName);
		return sqltemp.replace("#id#", id);
	}

}
