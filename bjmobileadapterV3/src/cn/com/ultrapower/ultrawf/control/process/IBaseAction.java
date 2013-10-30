package cn.com.ultrapower.ultrawf.control.process;

import java.util.List;

public interface IBaseAction {	

//	//Remedy信息初始化//
//	public void getInstance();
	
	/**
	 * 工单动作：新建工单函数
	 * @param p_BaseSchema				工单类别的Schema
	 * @param p_OperateUserLoginName	操作工单动作的用户登陆名
	 * @param p_FieldListInfo			工单新建动作的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @return 返回工单的BaseID（C1字段）
	 */
	public String Action_New(String p_OperateUserLoginName, String p_BaseSchema, List p_FieldListInfo);

	/**
	 * 工单动作：分派工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_ToDealDesc					派发表述
	 * @param p_BaseToDealObject			派发对象的List(BaseToDealObject类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_ToDeal(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_ToDealDesc,List p_BaseToDealObject);
	
	/**
	 * 工单动作：组自动转派工单时通过代理人传递ProcessId和ProcessType转派工单	
	 * @param p_OperateUserName		操作工单动作的用户登陆名
	 * @param p_processId	                当前环节信息
	 * @param p_processType	                当前环节类型
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_ToDealDesc					派发表述
	 * @param p_BaseToDealObject			派发对象的List(BaseToDealObject类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_ToDeal_FormGivenUser(String p_OperateUserName,
			String p_processId,String p_processType,String p_BaseSchema, String p_BaseID, String p_GroupToDealDesc,List p_BaseToDealObject);
	
	/**
	 * 工单动作：受理处理工单函数	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @return 返回是否成功
	 */
	public boolean Action_Start(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID);
	
	/**
	 * 工单动作：阶段回复工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealPhaseNoticeDesc			该工单动作操作时的内容
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_PhaseNotice(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealPhaseNoticeDesc,List p_FieldListInfo) ;

	/**
	 * 工单动作：完成处理工单函数
	 * @param p_OperateUserLoginName        操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealFinishDesc				该工单动作操作时的内容
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_Finish(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealFinishDesc,List p_FieldListInfo);

	/**
	 * 描述：确认抄送工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealConfirmDesc				该工单动作操作时的内容
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_Confirm(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealConfirmDesc,List p_FieldListInfo);

	/**
	 * 描述：催办处理工单函数	
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealConfirmDesc				该工单动作操作时的内容
	 * @param p_BaseDealObject				崔办对象的List(BaseDealObject类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_DealHasten(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealHastenDesc,List p_BaseDealObject);

	/**
	 * 描述：退回处理工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealTurnDownDesc			该工单动作操作时的内容
	 * @param p_BaseDealObject				退回对象的List(BaseDealObject类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_DealTurnDown(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealTurnDownDesc,List p_BaseDealObject);

	/**
	 * 描述：驳回处理工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealTurnUpDesc				该工单动作操作时的内容
	 * @param p_BaseDealObject				驳回对象的List(BaseDealObject类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_DealTurnUp(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealTurnUpDesc,List p_BaseDealObject);

	/**
	 * 描述：追回处理工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealRecallDesc				该工单动作操作时的内容
     * @param _Auditor_rad_AuditorRecallNewOp 追回是否从派
	 * @param p_BaseDealObject				追回对象的List(BaseDealObject类的对象的数组)
	 * @return 返回是否成功
	 */
	
	public boolean Action_DealRecall(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealRecallDesc,int P_Others_Deal_rad_DealRecallNewOp,List p_BaseDealObject);

	/**
	 * 描述：关闭工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealCloseDesc				该工单动作操作时的内容
	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @return 返回是否成功
	 */
	public boolean Action_Close(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealCloseDesc,List p_FieldListInfo);

	/**
	 * 描述：作废工单函数
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号
	 * @param p_DealCancelDesc				该工单动作操作时的内容
	 * @return 返回是否成功
	 */
	public boolean Action_Cancel(String p_OperateUserLoginName,String p_BaseSchema, String p_BaseID, String p_DealCancelDesc);
}