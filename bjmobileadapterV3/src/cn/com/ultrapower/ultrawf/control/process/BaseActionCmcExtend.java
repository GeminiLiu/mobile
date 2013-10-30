package cn.com.ultrapower.ultrawf.control.process;

import java.util.List;

public class BaseActionCmcExtend  extends BaseAction{
	
	/**
	 * 自动质检
	 * @param p_OperateUserLoginName		操作工单动作的用户登陆名
	 * @param p_BaseSchema					工单类别的Schema
	 * @param p_BaseID						操作工单的工单号

	 * @param p_FieldListInfo				该工单动作需要填写的字段信息的List(BaseFieldInfo类的对象的数组)
	 * @param p_BaseAttachmentList		附件对象的List(BaseAttachment类的对象的数组)
	 * @return
	 */
	public boolean Action_AutoCheck(String p_OperateUserLoginName,
			String p_BaseSchema, String p_BaseID,List p_FieldListInfo,List p_BaseAttachmentList)
	{
		//操作工单信息初始化


		return true;
	}
	
	public static void main(String[] args) {
		BaseAction m_BaseAction=new BaseAction();
		//m_BaseAction.Action_AutoCheck("hanmei","UltraProcess:Cmc_Eoms_Complain","000000000000066",null,null);
	}
	

}
