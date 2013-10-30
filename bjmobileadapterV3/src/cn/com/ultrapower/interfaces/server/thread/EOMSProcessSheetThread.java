package cn.com.ultrapower.interfaces.server.thread;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ultrapower.eoms.common.util.CalendarUtils;

import cn.com.ultrapower.eoms.processSheet.Contents;
import cn.com.ultrapower.eoms.processSheet.CustomProcessInterface;
import cn.com.ultrapower.eoms.processSheet.InterfaceCfg;
import cn.com.ultrapower.eoms.util.CalendarUtil;
import cn.com.ultrapower.interfaces.client.CrmProcessSheetPortType;
import cn.com.ultrapower.interfaces.util.AttachUtil;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

public class EOMSProcessSheetThread  implements Job {
	
	private CustomProcessInterface customProcessInterface = new CustomProcessInterface();
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		//创建webservices调用服务
		String url = customProcessInterface.getUrls();
		CrmProcessSheetPortType  localEoms = null;
        Service srvcModel = new ObjectServiceFactory().create(CrmProcessSheetPortType.class);
        XFireProxyFactory factory =new XFireProxyFactory(XFireFactory.newInstance().getXFire());
		String isAlive = "1";
        try {
			localEoms = (CrmProcessSheetPortType)factory.create(srvcModel, url);
			isAlive = localEoms.isAlive("56",CalendarUtil.getCurrentDateTime24());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}finally{
			System.out.println("isAlive" + isAlive);
		}
		
		
		//如果返回值表明服务可以用，则开始调用接口
		if(isAlive.equals("0")){
			customProcessInterface = new CustomProcessInterface();
			System.out.println("申告工单扫描开始");
			RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
			String strTblName=m_RemedyDBOp.GetRemedyTableName(customProcessInterface.getPrefix()+ Contents.BMCSPLIT + customProcessInterface.getQuatzTable());
			String sqlCycleP = "select c1,c802043051,c802043006,c802043010,c802043012,c802043013,c802043011,c802043014 from " +strTblName+ " t where t.c802043009 = 0 order by c1 ";
			String sqlCycle = PageControl.GetSqlStringForPagination(sqlCycleP,"ORACLE",1, 10);
			System.out.println(sqlCycle);
			
			// 轮询
			IDataBase m_dbConsole = GetDataBase.createDataBase();
			Statement stm=null;
			ResultSet resultSet =null;
			Statement upStm=null;
			ResultSet upResultSet =null;
			ResultSet rs =null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			AttachUtil attachUtil  = new AttachUtil();
			try {
				stm = m_dbConsole.GetStatement();
				resultSet = m_dbConsole.executeResultSet(stm,sqlCycle);
				while(resultSet.next()) {
					StringBuffer sb =  new StringBuffer("<opDetail>");
					String c1 = FormatString.CheckNullString(resultSet.getString(1)).trim();
					String baseId = FormatString.CheckNullString(resultSet.getString(2)).trim();
					String action = FormatString.CheckNullString(resultSet.getString(3)).trim();
					String serialNo = FormatString.CheckNullString(resultSet.getString(4)).trim();
					String opPerson = FormatString.CheckNullString(resultSet.getString(5)).trim();
					String opDepart = FormatString.CheckNullString(resultSet.getString(6)).trim();
					String opContact = FormatString.CheckNullString(resultSet.getString(7)).trim();
					String opTime = FormatString.CheckNullString(resultSet.getString(8)).trim();
					
					
					// 查询具体数据
					conn = m_dbConsole.getConn();
					String selectSql = generateSelectSql(Contents.PERSONPROCESS,action);
					pstmt= conn.prepareStatement(selectSql);
					pstmt.setString(1, baseId);
					rs = pstmt.executeQuery();
					if(rs != null){
						if(rs.next()) {
							InterfaceCfg interfaceCfg = new InterfaceCfg();
							List<Map<String,String>> list = interfaceCfg.getElements(Contents.PERSONPROCESS, action);
							if(Contents.CONFIRMWORKSHEET.equals(action)){//如果是受理，则为固定的opDetail
								sb.append(Contents.ACCEPTDETAIL);
							}else{
								if(list != null){
									sb.append("<recordInfo>");
									for(int i = 0 ; i < list.size() ; i++){
										String column = (String)list.get(i).get(Contents.FIELDCOLUMN);
										String enName = (String)list.get(i).get(Contents.FIELDENNAME);
										String chName = (String)list.get(i).get(Contents.FIELDCHNAME);
										
										sb.append("<fieldInfo>");
										sb.append("<fieldChName>");
										sb.append(chName);
										sb.append("</fieldChName>");
										
										sb.append("<fieldEnName>");
										sb.append(enName);
										sb.append("</fieldEnName>");
										
										sb.append("<fieldContent>");
										String value = FormatString.CheckNullString(rs.getString("C"+column)).trim();
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
				            System.out.println("returnNumber"  + returnNumber);
				            if("0".equals(returnNumber)){//调用成功， 更新数据
				            	String updateSql = generateUpdateSql( Contents.PREFIX + Contents.BMCSPLIT + Contents.QUATZTABLE ,c1);
				            	upStm = m_dbConsole.GetStatement();
								int a = m_dbConsole.executeNonQuery(upStm,updateSql);
								if(a>0){
									System.out.println("success");
									conn.commit();
								}
				            }
				        }
				        catch (Exception e){
				          e.printStackTrace();
				      }
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (resultSet != null)
						resultSet.close();
					if (rs != null)
						rs.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				try {
					if (stm != null)
						stm.close();
					if (pstmt != null)
						pstmt.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				m_dbConsole.closeConn();
			}
		
		}
	}
		

	/**
	 * 通过配置文件，生成查询未发送出去的sql
	 * @param sheetName
	 * @param dealType
	 * @param serialNum
	 * @return 
	 */
	private String generateSelectSql(String sheetName,String dealType){
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
		sb.append(" t WHERE  t.C1=?");
		return sb.toString();
	}
	
	
	
	/**
	 * 通过配置文件，生成更新的sql
	 * @param sheetName
	 * @param dealType
	 * @param serialNum
	 * @return
	 */
	private String generateUpdateSql(String sheetName,String id){
		CustomProcessInterface customProcessInterface = new CustomProcessInterface();
		String sql = customProcessInterface.getQuatzUpdate();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(customProcessInterface.getPrefix() + Contents.BMCSPLIT + customProcessInterface.getQuatzTable());
		String sqltemp = sql.replace(Contents.QUATZTABLE,strTblName);
		return sqltemp.replace("#id#", id);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "<opDetail><recordInfo>" +
				"<fieldInfo><fieldChName>标题</fieldChName><fieldEnName>title</fieldEnName><fieldContent>this is 标题 1</fieldContent></fieldInfo>" +
				"<fieldInfo><fieldChName>受理时限</fieldChName><fieldEnName>dealTime1</fieldEnName><fieldContent>this 按时 受理 时限 1</fieldContent></fieldInfo></recordInfo>" +
				"<recordInfo><fieldInfo><fieldChName>标题</fieldChName><fieldEnName>title</fieldEnName><fieldContent>this is 标题 1</fieldContent></fieldInfo>" +
				"<fieldInfo><fieldChName>受理时限</fieldChName><fieldEnName>dealTime1</fieldEnName><fieldContent>this 按时 受理 时限 1</fieldContent></fieldInfo></recordInfo>" +
				"</opDetail>";

		/*XmlSplit incfg = new XmlSplit();
		List list = incfg.getDatatoList(str,"person", "newWorksheet");
		System.out.println(list.size());
		System.out.println();*/
		
		InterfaceCfg inte = new InterfaceCfg();
		inte.getBaseFieldInfo("person","newWorksheet");
		System.out.println();
		
	}
	
	}