package cn.com.ultrapower.interfaces.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import cn.com.ultrapower.eoms.processSheet.XmlSplit;
import cn.com.ultrapower.interfaces.util.AttachUtil;
import cn.com.ultrapower.ultrawf.control.design.TplDesignManager;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachmentInfo;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_New;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_NextBaseCustom;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_NextCustomHavaMatchRole;
import cn.com.ultrapower.ultrawf.models.process.TplBaseModel;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.ultrawf.share.OperationLogFile;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

/**
 * 投诉所提供的服务接口
 * 
 * @author liangyang
 * 
 */
public class EOMSProcessSheet {

	private List prepareDate(int sheetType, int serviceType, String serialNo,
			String serSupplier, String serCaller, String callerPwd,
			String callTime, String attachRef, String opPerson, String opCorp,
			String opDepart, String opContact, String opTime, String opDetail,String whichSheet,String whichAction) {
		// 公共信息
		OperationLogFile.writeTxt("\r\n工单类型(sheetType):" + sheetType);
		OperationLogFile.writeTxt("\r\n产品类型(sheetType):" + serviceType);
		OperationLogFile.writeTxt("\r\n工单流水号(serialNo):" + serialNo);
		OperationLogFile.writeTxt("\r\n服务提供方(serSupplier):" + serSupplier);
		OperationLogFile.writeTxt("\r\n服务调用方(serCaller):" + serCaller);
		OperationLogFile.writeTxt("\r\n服务调用方口令/密码(callerPwd):" + callerPwd);
		OperationLogFile.writeTxt("\r\n服务调用时间(callTime):" + callTime);
		OperationLogFile.writeTxt("\r\n附件信息列表(attachRef):" + attachRef);
		OperationLogFile.writeTxt("\r\n操作人(opPerson):" + opPerson);
		OperationLogFile.writeTxt("\r\n操作人单位(opCorp):" + opCorp);
		OperationLogFile.writeTxt("\r\n操作人所属部门(opDepart):" + opDepart);
		OperationLogFile.writeTxt("\r\n操作人联系方式(opContact):" + opContact);
		OperationLogFile.writeTxt("\r\n操作时间(opTime):" + opTime);
		OperationLogFile.writeTxt("\r\n操作信息列表(opDetail):" + opDetail);
		OperationLogFile.writeTxt("\r\n--------------END(建单参数)---------------");
		XmlSplit xmlSplit = new XmlSplit();
		List list = xmlSplit.getDatatoList(opDetail, whichSheet, whichAction);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				List<BaseFieldInfo> listBean = (List) list.get(i);
				BaseFieldInfo serialNoBean = new BaseFieldInfo("CCH_serialNo","800020028",serialNo,4);
				BaseFieldInfo opPersonBean = new BaseFieldInfo("tmp_UserLoginName","1000101",opPerson,4);
				BaseFieldInfo opDepartBean = new BaseFieldInfo("tmp_UserDep","1000103",opDepart,4);
				BaseFieldInfo opContactBean = new BaseFieldInfo("tmp_UserConnection","1000113",opContact,4);
				//BaseFieldInfo opTimeBean = new BaseFieldInfo("opTime","800020028",opTime,4);
				listBean.add(serialNoBean);
				listBean.add(opPersonBean);
				listBean.add(opDepartBean);
				listBean.add(opContactBean);
			}
			return list;
		}
		// 没有正确的数据或者未成功解析，返回null
		OperationLogFile.writeTxt("\r\n------没有正确的数据或者未成功解析，返回null-----");
		return null;
	}

	/**
	 * A系统在调用B系统相应服务模块中其他接口方法之前， 可利用此服务调用判断对方当前服务是否已经部署完毕， 以确定所调用的方法当前是否可以正常服务
	 * 
	 * @param serSupplier
	 *            服务提供方的系统代码
	 * @param callTime
	 *            服务调用时间
	 * @return String类型。为0表示相应服务可用；非0表示该服务当前出现问题，则应用相应明文进行说明
	 */
	public String isAlive(String serSupplier, String callTime) {
		return "0";
	}

	/**
	 * 该接口适用于新建工单的派发。对现有工单的再次发送需调用相应的“重派”服务接口。
	 * 
	 * @param sheetType
	 *            工单类型（代码规则参见“附录A.2”）
	 * @param serviceType
	 *            产品类型（代码规则参见“附录A.4”）
	 * @param serialNo
	 *            CRM工单编号
	 * @param serSupplier
	 *            服务提供方，格式为：省份编码_系统名称
	 * @param serCaller
	 *            服务调用方，格式为：省份编码_系统名称
	 * @param callerPwd
	 *            服务调用方密码(可选)
	 * @param callTime
	 *            服务调用时间
	 * @param attachRef
	 *            附件信息列表，参见“附件信息约定”
	 * @param opPerson
	 *            操作人
	 * @param opCorp
	 *            操作人单位
	 * @param opDepart
	 *            操作人所属部门（内容为操作人所属部门，例如客服中心、计费中心等）
	 * @param opContact
	 *            操作人联系方式（可以为空）
	 * @param opTime
	 *            操作时间
	 * @param opDetail
	 *            详细信息，参见“详细信息约定”
	 * @return
	 */
	public String newWorkSheet(int sheetType, int serviceType, String serialNo,
			String serSupplier, String serCaller, String callerPwd,
			String callTime, String attachRef, String opPerson, String opCorp,
			String opDepart, String opContact, String opTime, String opDetail) {
		OperationLogFile.writeTxt("\r\n");
		OperationLogFile.writeTxt("\r\n--个人投诉工单建单动作"
				+ FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss") + "--");
		System.out.println(serSupplier + "调用派单服务接口开始");
		OperationLogFile.writeTxt("\r\n-------------BEGIN(建单参数)--------------");

		if (null != opDetail && opDetail.length() > 0) {// 将opDetail解析出来
			List<List<BaseFieldInfo>> list = prepareDate(sheetType, serviceType, serialNo,
					serSupplier, serCaller, callerPwd, callTime, attachRef,
					opPerson, opCorp, opDepart, opContact, opTime, opDetail,"person","newWorksheet");
			if(list != null){
				try {
					for (int i = 0; i < list.size(); i++) {
						list.get(i).add(new BaseFieldInfo("BaseCreatorCorp", "700000101",opCorp, 4));
						list.get(i).add(new BaseFieldInfo("BaseCreatorDep", "700000103",opDepart, 4));
						//新建
						Action_New action_New = new Action_New();
						TplDesignManager tdDesignManager = new TplDesignManager();
						List<TplBaseModel> tplBaseModelList = tdDesignManager.getTplBaseListBySchema("WF:EL_TTM_CCH");
						AttachUtil attachUtil  = new AttachUtil();
						List<BaseAttachmentInfo> regList  = attachUtil.ConvertAttachList(attachRef);
						boolean isSuccess = action_New.do_Action("kf10086", "WF:EL_TTM_CCH",tplBaseModelList.get(0).getBaseTplID(), list.get(i), regList);
						
						
						if (isSuccess) {//新建成功派发工单
							Action_NextCustomHavaMatchRole m_Action_NextCustomHavaMatchRole = new Action_NextCustomHavaMatchRole();
							/*TplDesignManager tdManager = new TplDesignManager();
							List<TplBaseModel> tbList = tdManager
									.getTplBaseListBySchema("WF:EL_TTM_CCH");*/
							if(m_Action_NextCustomHavaMatchRole.do_Action(
																		"kf10086",
																		"WF:EL_TTM_CCH",
																		action_New.getM_BaseID(), 
																		null,
																		null, 
																		"新建派发",
																		//"dp_2",
																		//new BaseFieldInfo("tmp_CCH_T0_ToUserID", "800045504","", 4),
																		//new BaseFieldInfo("tmp_CCH_T0_ToUser", "800045503","", 4), 
																		//new BaseFieldInfo("tmp_CCH_T0_ToGroupID","800045506", "", 4),
																		//new BaseFieldInfo("tmp_CCH_T0_ToGroup","800045505", "", 4), 
																		list.get(i),
																		null)){
								return "0";
							}else{
								return "error";
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			}
		}
		return "error";
	}

	/**
	 * 该接口适用于CRM系统接收到EOMS系统的驳回工单后，可调用该接口重新派发工单。
	 * 
	 * @param sheetType
	 *            工单类型（代码规则参见“附录A.2”）
	 * @param serviceType
	 *            产品类型（代码规则参见“附录A.4”）
	 * @param serialNo
	 *            CRM工单编号
	 * @param serSupplier
	 *            服务提供方，格式为：省份编码_系统名称
	 * @param serCaller
	 *            服务调用方，格式为：省份编码_系统名称
	 * @param callerPwd
	 *            服务调用方密码(可选)
	 * @param callTime
	 *            服务调用时间
	 * @param attachRef
	 *            附件信息列表，参见“附件信息约定”
	 * @param opPerson
	 *            操作人
	 * @param opCorp
	 *            操作人单位
	 * @param opDepart
	 *            操作人所属部门（内容为操作人所属部门，例如客服中心、计费中心等）
	 * @param opContact
	 *            操作人联系方式（可以为空）
	 * @param opTime
	 *            操作时间
	 * @param opDetail
	 *            详细信息，参见“详细信息约定”
	 * @return
	 */
	public String renewWorkSheet(int sheetType, int serviceType,
			String serialNo, String serSupplier, String serCaller,
			String callerPwd, String callTime, String attachRef,
			String opPerson, String opCorp, String opDepart, String opContact,
			String opTime, String opDetail) {
		OperationLogFile.writeTxt("\r\n");
		OperationLogFile.writeTxt("\r\n--个人投诉工单重新派发工单动作"
				+ FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss") + "--");
		System.out.println(serCaller + "调用重新派发服务接口开始");
		OperationLogFile.writeTxt("\r\n-------------BEGIN(建单参数)--------------");

		if (null != opDetail && opDetail.length() > 0) {// 将opDetail解析出来
			List<List<BaseFieldInfo>> list = prepareDate(sheetType, serviceType, serialNo,
					serSupplier, serCaller, callerPwd, callTime, attachRef,
					opPerson, opCorp, opDepart, opContact, opTime, opDetail,"person","newWorksheet");
			if(list != null){
				try {
					for (int i = 0; i < list.size(); i++) {
						list.get(i).add(new BaseFieldInfo("BaseCreatorCorp", "700000101",opCorp, 4));
						list.get(i).add(new BaseFieldInfo("BaseCreatorDep", "700000103",opDepart, 4));
						AttachUtil attachUtil  = new AttachUtil();
						List<BaseAttachmentInfo> regList  = attachUtil.ConvertAttachList(attachRef);
						String baseId = getBaseIdByserialNo(serialNo);
						OperationLogFile.writeTxt("\r\n--CRM的serialNo" + serialNo +"对应EOMS工单号是" + baseId);
						Action_NextCustomHavaMatchRole m_Action_NextCustomHavaMatchRole = new Action_NextCustomHavaMatchRole();
						if(m_Action_NextCustomHavaMatchRole.do_Action(
																	"kf10086",
																	"WF:EL_TTM_CCH",
																	baseId, 
																	null,
																	null, 
																	"新建派发",
																	list.get(i),
																	regList)){
							return "0";
						}else{
							return "error";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			}
		}
		return "error";
	}

	/**
	 * 该接口适用于CRM系统在处理流程未完成（即EOMS系统未发出工单回复消息）之前，向EOMS系统发出阶段通知消息，或追加工单处理要求
	 * 
	 * @param sheetType
	 *            工单类型（代码规则参见“附录A.2”）
	 * @param serviceType
	 *            产品类型（代码规则参见“附录A.4”）
	 * @param serialNo
	 *            CRM工单编号
	 * @param serSupplier
	 *            服务提供方，格式为：省份编码_系统名称
	 * @param serCaller
	 *            服务调用方，格式为：省份编码_系统名称
	 * @param callerPwd
	 *            服务调用方密码(可选)
	 * @param callTime
	 *            服务调用时间
	 * @param attachRef
	 *            附件信息列表，参见“附件信息约定”
	 * @param opPerson
	 *            操作人
	 * @param opCorp
	 *            操作人单位
	 * @param opDepart
	 *            操作人所属部门（内容为操作人所属部门，例如客服中心、计费中心等）
	 * @param opContact
	 *            操作人联系方式（可以为空）
	 * @param opTime
	 *            操作时间
	 * @param opDetail
	 *            详细信息，参见“详细信息约定”
	 * @return
	 */
	public String suggestWorkSheet(int sheetType, int serviceType,
			String serialNo, String serSupplier, String serCaller,
			String callerPwd, String callTime, String attachRef,
			String opPerson, String opCorp, String opDepart, String opContact,
			String opTime, String opDetail) {
		OperationLogFile.writeTxt("\r\n");
		OperationLogFile.writeTxt("\r\n--个人投诉工单阶段通知工单动作"
				+ FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss") + "--");
		System.out.println(serSupplier + "调用阶段通知服务接口开始");
		OperationLogFile.writeTxt("\r\n-------------BEGIN(建单参数)--------------");
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName("WF:EL_TTM_CCH");
		String sql = "select C800020014,c1 from " + strTblName + " t where t.c800020028 ='"+ serialNo +"'";
		Connection conn = null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		Statement upStm=null;
		try {
			conn = m_dbConsole.getConn();
			m_dbConsole.setTransfer(true);
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sql);
			if(resultSet != null){
				if(resultSet.next()) {
					String complainDesc = FormatString.CheckNullString(resultSet.getString(1)).trim();
					String baseId = FormatString.CheckNullString(resultSet.getString(2)).trim();
					XmlSplit xmlSplit = new XmlSplit();
					List<List<BaseFieldInfo>> list = xmlSplit.getDatatoList(opDetail, "person","suggestWorkSheet");
					
					for (int i = 0; i < list.size(); i++) {
						for (int j = 0; j < list.get(i).size(); j++) {
							if("BaseSummary".equals(list.get(i).get(j).getStrFieldName())){
								complainDesc += list.get(i).get(j).getStrFieldValue();
							}
						}
						
					}
					String updateSql = "update "+ strTblName + " t set t.C800020014 = '" + complainDesc + "' where t.c1 = '" + baseId + "'";
					upStm = m_dbConsole.GetStatement();
					m_dbConsole.executeNonQuery(upStm,updateSql);
					m_dbConsole.commit();
				}
			}
			return "0";
		}catch (Exception ex) {
				ex.printStackTrace();
				m_dbConsole.rollback();
				return "error";
		}finally{
				try {
					if (resultSet != null)
						resultSet.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				try {
					if (stm != null)
						stm.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				m_dbConsole.closeConn();
		}
	}

	/**
	 * 该接口适用于CRM系统接收到EOMS系统的回复工单后，对工单处理结果不满意时，将工单退回。
	 * 
	 * @param sheetType
	 *            工单类型（代码规则参见“附录A.2”）
	 * @param serviceType
	 *            产品类型（代码规则参见“附录A.4”）
	 * @param serialNo
	 *            CRM工单编号
	 * @param serSupplier
	 *            服务提供方，格式为：省份编码_系统名称
	 * @param serCaller
	 *            服务调用方，格式为：省份编码_系统名称
	 * @param callerPwd
	 *            服务调用方密码(可选)
	 * @param callTime
	 *            服务调用时间
	 * @param attachRef
	 *            附件信息列表，参见“附件信息约定”
	 * @param opPerson
	 *            操作人
	 * @param opCorp
	 *            操作人单位
	 * @param opDepart
	 *            操作人所属部门（内容为操作人所属部门，例如客服中心、计费中心等）
	 * @param opContact
	 *            操作人联系方式（可以为空）
	 * @param opTime
	 *            操作时间
	 * @param opDetail
	 *            详细信息，参见“详细信息约定”
	 * @return
	 */
	public String untreadWorkSheet(int sheetType, int serviceType,
			String serialNo, String serSupplier, String serCaller,
			String callerPwd, String callTime, String attachRef,
			String opPerson, String opCorp, String opDepart, String opContact,
			String opTime, String opDetail) {
		OperationLogFile.writeTxt("\r\n");
		OperationLogFile.writeTxt("\r\n--个人投诉工单退回动作"
				+ FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss") + "--");
		System.out.println(serSupplier + "调用退回服务接口开始");
		OperationLogFile.writeTxt("\r\n-------------BEGIN(建单参数)--------------");

		if (null != opDetail && opDetail.length() > 0) {// 将opDetail解析出来
			List<List<BaseFieldInfo>> list = prepareDate(sheetType, serviceType, serialNo,
					serSupplier, serCaller, callerPwd, callTime, attachRef,
					opPerson, opCorp, opDepart, opContact, opTime, opDetail,"person","untreadWorkSheet");
			String baseId = getBaseIdByserialNo(serialNo);
			String [] para = getProcessIdAndProcessType("WF:EL_TTM_CCH", baseId);
			String va = getSpcialValue(list ,"CCH_GoBackDesc");
			BaseFieldInfo opPersonBean = new BaseFieldInfo("tmp_CCH_GoBackDesc","800045301",va,4);
			BaseFieldInfo opPersonBeanq = new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200",va,4);
			Action_NextBaseCustom nextCustom = new Action_NextBaseCustom();
			list.get(0).add(opPersonBean);
			list.get(0).add(opPersonBeanq);
			if(nextCustom.do_Action("kf10086", "WF:EL_TTM_CCH",baseId , para[0], para[1], "退回处理", list.get(0), null)){
				return "0";
			}else{
				return "error";
			}
			
		}
		return "error";
	}

	/**
	 * 该接口适用于CRM系统在收到EOMS系统发来的回复工单后，进行工单归档。
	 * 
	 * @param sheetType
	 *            工单类型（代码规则参见“附录A.2”）
	 * @param serviceType
	 *            产品类型（代码规则参见“附录A.4”）
	 * @param serialNo
	 *            CRM工单编号
	 * @param serSupplier
	 *            服务提供方，格式为：省份编码_系统名称
	 * @param serCaller
	 *            服务调用方，格式为：省份编码_系统名称
	 * @param callerPwd
	 *            服务调用方密码(可选)
	 * @param callTime
	 *            服务调用时间
	 * @param attachRef
	 *            附件信息列表，参见“附件信息约定”
	 * @param opPerson
	 *            操作人
	 * @param opCorp
	 *            操作人单位
	 * @param opDepart
	 *            操作人所属部门（内容为操作人所属部门，例如客服中心、计费中心等）
	 * @param opContact
	 *            操作人联系方式（可以为空）
	 * @param opTime
	 *            操作时间
	 * @param opDetail
	 *            详细信息，参见“详细信息约定”
	 * @return
	 */
	public String checkinWorkSheet(int sheetType, int serviceType,
			String serialNo, String serSupplier, String serCaller,
			String callerPwd, String callTime, String attachRef,
			String opPerson, String opCorp, String opDepart, String opContact,
			String opTime, String opDetail) {
		OperationLogFile.writeTxt("\r\n");
		OperationLogFile.writeTxt("\r\n--个人投诉工单归档动作"
				+ FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss") + "--");
		System.out.println(serSupplier + "调用归档服务接口开始");
		OperationLogFile.writeTxt("\r\n-------------BEGIN(建单参数)--------------");

		if (null != opDetail && opDetail.length() > 0) {// 将opDetail解析出来
			List<List<BaseFieldInfo>> list = prepareDate(sheetType, serviceType, serialNo,
					serSupplier, serCaller, callerPwd, callTime, attachRef,
					opPerson, opCorp, opDepart, opContact, opTime, opDetail,"person","checkinWorkSheet");
			Action_NextBaseCustom nextCustom = new Action_NextBaseCustom();
			String baseId = getBaseIdByserialNo(serialNo);
			//String [] para = getProcessIdAndProcessType("WF:EL_TTM_CCH", baseId);
			//String val = getSpcialValue(list ,"P_OpNext_Close_CloseOpSatisfaction");//归档满意度
			//String va = getSpcialValue(list ,"CCH_EndDealDesc");//归档描述
			
			
			
			//归档
			BaseFieldInfo opPersonBean = new BaseFieldInfo("CCH_CloseAuditingDealResult","800021003","",4);
			BaseFieldInfo opPersonBeanq = new BaseFieldInfo("P_OpNext_Close_CloseOpSatisfaction","800030401","",4);
			
			
			//归档意见
			BaseFieldInfo checkinBean = new BaseFieldInfo("tmp_CCH_EndDealDesc","800045402","",4);
			//归档意见下一步
			BaseFieldInfo checkinBeanq = new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200","",4);
			list.get(0).add(opPersonBean);
			list.get(0).add(opPersonBeanq);
			list.get(0).add(checkinBean);
			list.get(0).add(checkinBeanq);
			
			list.get(0).add(new BaseFieldInfo("BaseCreatorCorp", "700000101",opCorp, 4));
			list.get(0).add(new BaseFieldInfo("BaseCreatorDep", "700000103",opDepart, 4));
			
			
			
			//用于归档环节操作时的流程走向的判断字段
			list.get(0).add(new BaseFieldInfo("CCH_CloseAuditingDealResult","800021003","归档工单",4));
			
			if(nextCustom.do_Action("kf10086", "WF:EL_TTM_CCH",baseId , null,null, "归档", list.get(0), null)){
				return "0";
			}
		}
		return "error";
	}
	
	
	
	private String getSpcialValue(List<List<BaseFieldInfo>> list , String fieldNameInRemedy){
		String va = "";
		if(list.get(0) != null){
			for (int i = 0; i < list.get(0).size() ; i++) {
				if(fieldNameInRemedy.equals(list.get(0).get(i).getStrFieldName())){
					va = list.get(0).get(i).getStrFieldValue();
					break;
				}
			}
		}
		return va;
	}
	
	
	/**
	 * 根据客服工单号 ，得到remedy的工单号
	 * @param serialNo
	 * @return
	 */
	private String getBaseIdByserialNo(String serialNo){
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName("WF:EL_TTM_CCH");
		String sql = "select c1 from " + strTblName + " t where t.c800020028 ='"+ serialNo +"'";
		Connection conn = null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		String baseId="";
		try {
			conn = m_dbConsole.getConn();
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sql);
			if(resultSet != null){
				if(resultSet.next()) {
					baseId = FormatString.CheckNullString(resultSet.getString(1)).trim();
				}
			}
		}catch (Exception ex) {
				ex.printStackTrace();
		}finally{
				try {
					if (resultSet != null)
						resultSet.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				try {
					if (stm != null)
						stm.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				m_dbConsole.closeConn();
		}
		return baseId;
	}
	
	
	/**
	 * 得到当前的环节号
	 * @param scheame
	 * @param baseId
	 * @return
	 */
	private String[] getProcessIdAndProcessType(String scheame,String baseId){
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName("WF:App_DealProcess");
		String sql = "select c1,c700020043 from " + strTblName + " t where t.c700020001 ='"+ baseId +"' and t.c700020002 ='"+ scheame +"' and t.c700020020 ='1'";
		Connection conn = null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		String processId="";
		String processType="";
		try {
			conn = m_dbConsole.getConn();
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sql);
			if(resultSet != null){
				if(resultSet.next()) {
					processId = FormatString.CheckNullString(resultSet.getString(1)).trim();
					processType = FormatString.CheckNullString(resultSet.getString(2)).trim();
				}
			}
		}catch (Exception ex) {
				ex.printStackTrace();
		}finally{
				try {
					if (resultSet != null)
						resultSet.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				try {
					if (stm != null)
						stm.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
				m_dbConsole.closeConn();
		}
		return new String[]{processId,processType}; 
	}
}
